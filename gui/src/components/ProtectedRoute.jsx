import React from "react";
import {Redirect, Route} from "react-router-dom";
import PropTypes from "prop-types";

export default function ProtectedRoute({path, currentUser, render}) {
    if (currentUser) {
        return <Route path={path} render={render}/>;
    }
    return <Redirect to={"/login"}/>;
}

ProtectedRoute.propTypes = {
    path: PropTypes.string.isRequired,
    currentUser: PropTypes.object.isRequired,
    render: PropTypes.func.isRequired
};
