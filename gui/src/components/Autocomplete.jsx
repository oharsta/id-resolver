import React from "react";
import PropTypes from "prop-types";

import "./Autocomplete.css";
import {isEmpty} from "../utils/Utils";

export default class Autocomplete extends React.PureComponent {

    itemName = (item, query) => {
        const name = item.name;
        const nameToLower = name.toLowerCase();
        const indexOf = nameToLower.indexOf(query.toLowerCase());
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
        return (
            <section className="autocomplete">
                <table className="result">
                    <tbody>
                    {suggestions
                        .map((item, index) => (
                                <tr key={index}
                                    onClick={() => itemSelected(item)}>
                                    <td>{this.itemName(item, query)}</td>
                                    <td>{item.email || ""}</td>
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


