package resolver;

import resolver.api.APIAuthenticationManager;
import resolver.api.APIUserConfiguration;
import resolver.api.APIUserHandlerMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.yaml.snakeyaml.Yaml;

import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigurer {

    @Value("${security.api_users_config_path}")
    private String configApiUsersFileLocation;

    @Autowired
    private ResourceLoader resourceLoader;

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

    @Configuration
    public static class SecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {


        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/actuator/health", "/actuator/info", "/users/encodePassword/**");
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()
                .csrf()
                .disable()
                .addFilterBefore(new BasicAuthenticationFilter(authenticationManagerBean()),
                    BasicAuthenticationFilter.class
                )
                .authorizeRequests()
                .anyRequest()
                .hasRole("READ");
        }

    }

    @Configuration
    public class MvcConfig implements WebMvcConfigurer {

        @Override
        public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
            argumentResolvers.add(new APIUserHandlerMethodArgumentResolver());
        }

    }

}
