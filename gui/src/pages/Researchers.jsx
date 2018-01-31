import React from "react";
import PropTypes from "prop-types";
import debounce from "lodash/debounce";
import "./Researchers.css";
import {config, researcherById, search} from "../api";
import {isEmpty, stop} from "../utils/Utils";
import I18n from "i18n-js";
import Autocomplete from "../components/Autocomplete";
import ReactJson from 'react-json-view'

export default class Researchers extends React.PureComponent {

    constructor(props) {
        super(props);
        this.state = {
            researchers: [],
            researcher: null,
            query: ""
        };
    }

    componentDidMount = () => config();

    zoomIn = id => e => {
        stop(e);
        this.showResearcherDetail({id: id});
    };

    showResearcherDetail = researcher => researcherById(researcher.id).then(res => this.setState({
        researchers: [],
        researcher: res,
        query: ""
    }));

    search = e => {
        const query = e.target.value;
        this.setState({query: query});
        if (!isEmpty(query) && query.length > 2) {
            this.delayedSearch(query);
        } else {
            this.setState({researchers: []});
        }
    };

    delayedSearch = debounce(query => search(query).then(res => this.setState({
        researcher: null,
        researchers: res
    })), 250);

    formatChildParent = researcher => `${researcher.name || '?'} - ${researcher.email || '?'} - ${researcher.organisation || '?'} - ${researcher.organisationUid || '?'}`

    renderResearchDetails = researcher => {
        const children = researcher.children;
        const parents = researcher.parents;
        return <section className="details">
            <section className="children">
                <p>Children</p>
                {
                    children.map((child, index) => <div className="child">
                        <a href={`Details ${child.name}`} key={index} onClick={this.zoomIn(child.id)}>{this.formatChildParent(child)}</a>
                    </div>)
                }
            </section>
            <section className="parents">
                <p>Parents</p>
                {
                    parents.map((parent, index) => <div className="parent">
                        <a href={`Details ${parent.name}`} key={index} onClick={this.zoomIn(parent.id)}>{this.formatChildParent(parent)}</a>
                    </div>)
                }
            </section>
            {<ReactJson src={researcher} name="Researcher" displayObjectSize={false} displayDataTypes={false} />}
        </section>
    };

    render() {
        const {query, researchers, researcher} = this.state;
        return <div className="mod-researchers card">
            <section className="search">
                <input className="allowed"
                       placeholder={I18n.t("researchers.placeholder")}
                       type="text"
                       onChange={this.search}
                       value={query}/>
                <i className="fa fa-search"></i>
                <Autocomplete suggestions={researchers} query={query} itemSelected={this.showResearcherDetail}/>
            </section>
            {!isEmpty(researcher) && this.renderResearchDetails(researcher)}
        </div>
    }
}

Researchers.propTypes = {
    history: PropTypes.object.isRequired,
    currentUser: PropTypes.object.isRequired
};

