import React from "react";
import "./Login.css";
import {stats} from "../api";
import {isEmpty} from "../utils/Utils";
import "./Stats.css";

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
        return <div className="mod-stats card">
            <label># Researchers</label>
            <input type="text" disabled={true} value={stats.researchers}/>
            <label># Organisations</label>
            <input type="text" disabled={true} value={stats.organisations}/>
            <label># Unique identities</label>
            <input type="text" disabled={true} value={stats.identities}/>
            <label># Relations</label>
            <input type="text" disabled={true} value={stats.relations}/>
                {Object.keys(stats.weights).map((weight, index) =>
                    <div key={index} className="weights">
                        <label>Weight {weight}</label>
                        <input type="text" disabled={true}
                               value={Math.round(stats.weights[weight] / stats.researchers * 100)+"%"}/>
                    </div>
                )}
        </div>
    }
}
