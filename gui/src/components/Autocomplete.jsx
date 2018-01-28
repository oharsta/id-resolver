import React from "react";
import PropTypes from "prop-types";

import "./Autocomplete.css";
import {isEmpty} from "../utils/Utils";

export default class Autocomplete extends React.PureComponent {

    partName = (item, query, property) => {
        const name = item[property];
        if (isEmpty(name)) {
            return "";
        }
        const nameToLower = name.toLowerCase();
        const indexOf = nameToLower.indexOf(query.toLowerCase());
        if (indexOf === -1) {
            return <span>{name}</span>;
        }
        const first = name.substring(0, indexOf);
        const middle = name.substring(indexOf, indexOf + query.length);
        const last = name.substring(indexOf + query.length);
        return <span>{first}<span className="matched">{middle}</span>{last}</span>;

    };

    render() {
        const {suggestions, query, itemSelected} = this.props;
        if (isEmpty(suggestions)) {
            return null;
        }
        const properties = ["name", "email", "organisation", "organisationUid"];
        return (
            <section className="autocomplete">
                <table className="result">
                    <tbody>
                    {suggestions
                        .map((item, index) => (
                                <tr key={index}
                                    onClick={() => itemSelected(item)}>
                                    {properties.map((prop, index) =>
                                        <td key={index}>{this.partName(item, query, prop)}</td>
                                    )}
                                </tr>
                            )
                        )}
                    </tbody>
                </table>
            </section>
        );
    }

}

Autocomplete.propTypes = {
    suggestions: PropTypes.array.isRequired,
    query: PropTypes.string.isRequired,
    itemSelected: PropTypes.func.isRequired
};


