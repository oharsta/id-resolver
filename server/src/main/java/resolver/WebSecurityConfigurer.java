package resolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.yaml.snakeyaml.Yaml;
import resolver.api.APIAuthenticationManager;
import resolver.api.APIUserConfiguration;
import resolver.api.APIUserHandlerMethodArgumentResolver;

import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigurer {

    @Value("${security.api_users_config_path}")
    private String configApiUsersFileLocation;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private Environment environment;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        APIUserConfiguration apiUserConfiguration = new Yaml()
            .loadAs(resourceLoader.getResource(configApiUsersFileLocation).getInputStream(),
                APIUserConfiguration.class);
        APIAuthenticationManager authenticationManager = new APIAuthenticationManager(apiUserConfiguration,
            passwordEncoder());
        auth.parentAuthenticationManager(authenticationManager);
    }

    @Order
    @Configuration
    public class SecurityConfigurationAdapter extends WebSecurityConfigurerAdapter implements CommonWebSecurityConfigurerAdapter{
        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/actuator/health", "/actuator/info");
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            doConfigure(http, "/api/**", SessionCreationPolicy.STATELESS, authenticationManagerBean(), environment);
        }
    }

    @Order(1)
    @Configuration
    public class UserSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter implements CommonWebSecurityConfigurerAdapter{
        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/client/users/encodePassword/**", "/client/users/error", "/client/users/config");
        }
        @Override
        public void configure(HttpSecurity http) throws Exception {
            doConfigure(http, "/client/**", SessionCreationPolicy.IF_REQUIRED, authenticationManagerBean(), environment);
        }
    }

    @Configuration
    public class MvcConfig implements WebMvcConfigurer {
        @Override
        public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
            argumentResolvers.add(new APIUserHandlerMethodArgumentResolver());
        }
    }

    interface CommonWebSecurityConfigurerAdapter {
        default void doConfigure(HttpSecurity http, String pattern, SessionCreationPolicy sessionCreationPolicy,
                                        AuthenticationManager authenticationManager, Environment environment) throws Exception {
            http
                .requestMatchers()
                .antMatchers(pattern)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(sessionCreationPolicy)
                .and()
                .csrf()
                .disable()
                .addFilterBefore(new BasicAuthenticationFilter(authenticationManager),
                    BasicAuthenticationFilter.class
                )
                .authorizeRequests()
                .anyRequest()
                .hasRole("READ");
            if (environment.acceptsProfiles("dev")) {
                http.addFilterBefore(new MockUserFilter(), AbstractPreAuthenticatedProcessingFilter.class);
            }
        }

    }
}
