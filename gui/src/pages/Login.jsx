import React from "react";
import I18n from "i18n-js";
import "./Login.css";

export default class Login extends React.PureComponent {

    constructor(props) {
        super(props);
        this.state = {
            username: "",
            password: ""
        };
    }

    render() {
        const {username, password} = this.state;
        return (
            <div className="mod-login">
                <h1>{I18n.t("login.title")}</h1>
                <label htmlFor="username">{I18n.t("login.username")}</label>
                <input type="text" value={username} onChange={e => this.setState({"username": e.target.value})}/>
                <label htmlFor="password">{I18n.t("login.password")}</label>
                <input type="text" value={password} onChange={e => this.setState({"password": e.target.value})}/>
            </div>
        );
    }
}
