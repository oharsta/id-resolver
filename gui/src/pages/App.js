import React from "react";
import {BrowserRouter as Router, Redirect, Route, Switch} from "react-router-dom";
import "./App.css";
import ErrorDialog from "../components/ErrorDialog";
import Flash from "../components/Flash";
import ProtectedRoute from "../components/ProtectedRoute";
import NotFound from "../pages/NotFound";
import ServerError from "../pages/ServerError";
import Header from "../components/Header";
import Navigation from "../components/Navigation";
import "../locale/en";
import {reportError} from "../api";
import Login from "./Login";
import Researchers from "./Researchers";
import Stats from "./Stats";

class App extends React.PureComponent {

    constructor(props, context) {
        super(props, context);
        this.state = {
            loading: true,
            error: false,
            errorDialogOpen: false,
            errorDialogAction: () => {
                this.setState({errorDialogOpen: false});
            },
            currentUser: undefined
        };
        window.onerror = (msg, url, line, col, err) => {
            if (err && err.response && err.response.status === 401) {
                this.componentDidMount();
                return;
            }
            this.setState({errorDialogOpen: true});
            const info = err || {};
            const response = info.response || {};
            const error = {
                userAgent: navigator.userAgent,
                message: msg,
                url: url,
                line: line,
                col: col,
                error: info.message,
                stack: info.stack,
                targetUrl: response.url,
                status: response.status
            };
            reportError(error);
        };
    }

    componentDidMount() {
        this.setState({loading: false})
    }

    render() {
        const {loading, errorDialogAction, errorDialogOpen} = this.state;

        if (loading) {
            return null; // render null when app is not ready yet for static spinner
        }

        const {currentUser} = this.state;

        return (
            <Router>
                <div>
                    <div>
                        <Flash/>
                        <Header currentUser={currentUser}/>
                        <Navigation currentUser={currentUser} {...this.props}/>
                        <ErrorDialog isOpen={errorDialogOpen}
                                     close={errorDialogAction}/>
                    </div>
                    <Switch>
                        <Route exact path="/" render={() => <Redirect to="/researchers"/>}/>
                        <ProtectedRoute path="/researchers"
                                        currentUser={currentUser}
                                        render={props => <Researchers currentUser={currentUser} {...props}/>}/>
                        <ProtectedRoute path="/stats"
                                        currentUser={currentUser}
                                        render={props => <Stats {...props}/>}/>
                        <Route path="/login"
                               render={props => <Login {...props}/>}/>
                        <Route path="/error"
                               render={props => <ServerError {...props}/>}/>
                        <Route component={NotFound}/>
                    </Switch>
                </div>
            </Router>

        );
    }
}

export default App;