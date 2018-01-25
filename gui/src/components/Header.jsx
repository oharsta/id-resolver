import React from "react";
import I18n from "i18n-js";
import PropTypes from "prop-types";
import {unmountComponentAtNode} from "react-dom";
import {Link} from "react-router-dom";
import logo from "../images/logo.png";
import "./Header.css";
import {emitter, stop} from "../utils/Utils";

export default class Header extends React.PureComponent {

    stop = e => {
        stop(e);
        const node = document.getElementById("app");
        unmountComponentAtNode(node);
        emitter.emit("login", undefined);
        window.location.href = "/";
    };

    renderExitLogout = () =>
        <li className="border-left">
            <a onClick={this.stop}>{I18n.t("header.links.logout")}</a>
        </li>;

    renderProfileLink(currentUser) {
        return (
            <p className="welcome-link">
                <i className="fa fa-user-circle-o"></i>
                {currentUser ? currentUser.name : ""}
            </p>
        );
    }

    render() {
        const currentUser = this.props.currentUser;
        return (
            <div className="header-container">
                <div className="header">
                    <Link to="/" className="logo"><img src={logo} alt=""/></Link>
                    <span className="title">{I18n.t("header.title")}</span>
                    {currentUser &&
                    <ul className="links">
                        <li className="profile"
                            tabIndex="1" onBlur={() => this.setState({dropDownActive: false})}>
                            {this.renderProfileLink(currentUser)}
                        </li>
                        < li dangerouslySetInnerHTML={{__html: I18n.t("header.links.help_html")}}></li>
                        {this.renderExitLogout()}
                    </ul>}
                </div>
            </div>
        );
    }

}

Header.propTypes = {
    currentUser: PropTypes.object
};
