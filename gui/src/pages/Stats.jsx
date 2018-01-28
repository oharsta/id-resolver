import React from "react";
import "./Login.css";
import {stats} from "../api";
import {isEmpty} from "../utils/Utils";

export default class Login extends React.PureComponent {

    constructor(props) {
        super(props);
        this.state = {stats: {}};
    }


    componentDidMount() {
        stats().then(res => this.setState({stats: res}))
    }

    render() {
        const {stats} = this.state;
        if (isEmpty(stats)) {
            return null;
        }
        return <div>{JSON.stringify(stats)}</div>
    }
}
