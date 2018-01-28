import React from "react";
import PropTypes from "prop-types";
import debounce from "lodash/debounce";
import "./Researchers.css";
import {researcherById, search} from "../api";
import {isEmpty} from "../utils/Utils";
import I18n from "i18n-js";
import Autocomplete from "../components/Autocomplete";

export default class Researchers extends React.PureComponent {

    constructor(props) {
        super(props);
        this.state = {
            researchers: [],
            researcher: null,
            query: ""
        };
    }


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
        }
    };

    delayedSearch = debounce(query => search(query).then(res => this.setState({researcher: null, researchers: res})), 250);

    render() {
        const {query, researchers, researcher} = this.state;
        return <div className="mod-researchers">
            <section className="search">
                <input className="allowed"
                       placeholder={I18n.t("researchers.placeholder")}
                       type="text"
                       onChange={this.search}
                       value={query}/>
                <i className="fa fa-search"></i>
                <Autocomplete suggestions={researchers} query={query} itemSelected={this.showResearcherDetail}/>
            </section>
            {!isEmpty(researcher) && <div className="details">
                {JSON.stringify(researcher)}
            </div>}
        </div>
    }
}

Researchers.propTypes = {
    history: PropTypes.object.isRequired,
    currentUser: PropTypes.object.isRequired
};

