import React from "react";
import I18n from "i18n-js";
import {emitter, stop} from "../utils/Utils";
import PropTypes from "prop-types";
import {me} from "../api"
import "./Login.css";

export default class Login extends React.PureComponent {

    constructor(props) {
        super(props);
        this.state = {
            username: "",
            password: "",
            error: false
        };
    }

    componentDidMount = () => this.username.focus();

    handleKeyPress = e => {
        if (e.key === "Enter") {
            this.login(e);
        }
    };

    login = e => {
        stop(e);
        const {username, password} = this.state;
        me(username, password)
            .then(res => {
                emitter.emit("login", res);
                this.setState({error: false});
                this.props.history.push("/");
            })
            .catch(e => this.setState({error: true}))
    };

    render() {
        const {username, password, error} = this.state;
        return (
            <div className="mod-login">
                <label htmlFor="username">{I18n.t("login.username")}</label>
                <input ref={ref => this.username = ref} type="text" value={username} onChange={e => this.setState({"username": e.target.value})}/>
                <label htmlFor="password">{I18n.t("login.password")}</label>
                <input type="password" value={password} onChange={e => this.setState({"password": e.target.value})}
                       onKeyPress={this.handleKeyPress}/>
                {error && <em className="error">{"Wrong username or password"}</em>}
                <section className="actions">
                    <button onClick={this.login} className="button blue">{I18n.t("login.signIn")}</button>
                </section>
            </div>
        );
    }
}

Login.propTypes = {
    history: PropTypes.object.isRequired
};
