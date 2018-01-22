import React from "react";
import I18n from "i18n-js";
import PropTypes from "prop-types";
import {unmountComponentAtNode} from "react-dom";
import {Link} from "react-router-dom";
import logo from "../images/logo@2x.png";
import "./Header.css";
import {stop} from "../utils/Utils";

export default class Header extends React.PureComponent {

    stop = e => {
        stop(e);
        const node = document.getElementById("app");
        unmountComponentAtNode(node);
        window.location.href = "/";
    };

    handleToggle = e => {
        stop(e);
        this.setState({dropDownActive: !this.state.dropDownActive});
    };

    renderExitLogout = () =>
        <li className="border-left"><a onClick={this.stop}>{I18n.t("header.links.logout")}</a>
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
                    <ul className="links">
                        <li className="title"><span>{I18n.t("header.title")}</span></li>
                        <li className="profile"
                            tabIndex="1" onBlur={() => this.setState({dropDownActive: false})}>
                            {this.renderProfileLink(currentUser)}
                        </li>
                        <li dangerouslySetInnerHTML={{__html: I18n.t("header.links.help_html")}}></li>
                        {this.renderExitLogout()}
                    </ul>
                </div>
            </div>
        );
    }

}

Header.propTypes = {
    currentUser: PropTypes.object.isRequired
};
