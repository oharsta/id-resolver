import React from "react";
import PropTypes from "prop-types";

import "./Researchers.css";

export default class Researchers extends React.PureComponent {

    // constructor(props) {
    //     super(props);
    //
    //     this.state = {
    //         researchers: [],
    //         query: ""
    //     };
    // }
    //
    // showProcess = process => () => {
    //     clearInterval(this.interval);
    //     this.props.history.push("/process/" + process.id);
    // };
    //
    // search = e => {
    //     const query = e.target.value;
    //     this.setState({query: query});
    //     this.delayedSearch(query);
    // };
    //
    // delayedSearch = debounce(query => {
    //     const processes = [...this.state.processes];
    //     const {filterAttributesAssignee} = this.state;
    //     const {filterAttributesStatus} = this.state;
    //
    //
    //     this.setState({
    //         query: query,
    //         filteredProcesses: this.doSearchAndSortAndFilter(query, processes, this.state.sorted, filterAttributesAssignee, filterAttributesStatus)
    //     });
    // }, 250);
    //
    // renderProcessesTable(processes, actions, sorted) {
    //     const columns = ["assignee", "step", "status", "customer", "product", "workflow_name", "started", "last_modified", "actions"];
    //     const th = index => {
    //         const name = columns[index];
    //         return <th key={index} className={name} onClick={this.sort(name)}>
    //             <span>{I18n.t(`processes.${name}`)}</span>
    //             {this.sortColumnIcon(name, sorted)}
    //         </th>
    //     };
    //
    //     if (processes.length !== 0) {
    //         return (
    //             <table className="processes">
    //                 <thead>
    //                 <tr>{columns.map((column, index) => th(index))}</tr>
    //                 </thead>
    //                 <tbody>
    //                 {processes.map((process, index) =>
    //                     <tr key={`${process.id}_${index}`} onClick={this.showProcess(process)}
    //                         className={process.status}>
    //                         <td data-label={I18n.t("processes.assignee")} className="assignee">{process.assignee}</td>
    //                         <td data-label={I18n.t("processes.step")} className="step">{process.step}</td>
    //                         <td data-label={I18n.t("processes.status")} className="status">{process.status}</td>
    //                         <td data-label={I18n.t("processes.customer")}
    //                             className="customer">{process.customer_name}</td>
    //                         <td data-label={I18n.t("processes.product")} className="product">{process.product_name}</td>
    //                         <td data-label={I18n.t("processes.workflow_name")}
    //                             className="workflow_name">{process.workflow_name}</td>
    //                         <td data-label={I18n.t("processes.started")}
    //                             className="started">{renderDateTime(process.started)}</td>
    //                         <td data-label={I18n.t("processes.last_modified")}
    //                             className="last_modified">{renderDateTime(process.last_modified)}</td>
    //                         <td data-label={I18n.t("processes.actions")} className="actions"
    //                             onClick={this.toggleActions(process, actions)}
    //                             tabIndex="1" onBlur={() => this.setState({actions: {show: false, id: ""}})}>
    //                             <i className="fa fa-ellipsis-h"></i>
    //                             {this.renderActions(process, actions)}
    //                         </td>
    //                     </tr>
    //                 )}
    //                 </tbody>
    //             </table>
    //         );
    //     }
    //     return <div><em>{I18n.t("processes.no_found")}</em></div>;
    // }
    //
    // render() {
    //     const {
    //         filteredProcesses, actions, query, confirmationDialogOpen, confirmationDialogAction,
    //         confirmationDialogQuestion, sorted, filterAttributesAssignee, filterAttributesStatus, refresh
    //     } = this.state;
    //     const {organisations} = this.props;
    //     return (
    //         <div className="mod-processes">
    //             <ConfirmationDialog isOpen={confirmationDialogOpen}
    //                                 cancel={this.cancelConfirmation}
    //                                 confirm={confirmationDialogAction}
    //                                 question={confirmationDialogQuestion}/>
    //             <div className="card">
    //                 <div className="options">
    //                     <FilterDropDown items={filterAttributesAssignee}
    //                                     filterBy={this.filter}
    //                                     label={I18n.t("processes.assignee")}/>
    //                     <FilterDropDown items={filterAttributesStatus}
    //                                     filterBy={this.filter}
    //                                     label={I18n.t("processes.status")}/>
    //                     <section className="search">
    //                         <input className="allowed"
    //                                placeholder={I18n.t("processes.searchPlaceHolder")}
    //                                type="text"
    //                                onChange={this.search}
    //                                value={query}/>
    //                         <i className="fa fa-search"></i>
    //                     </section>
    //                     <a className="new button green" onClick={this.newProcess}>
    //                         {I18n.t("processes.new")}<i className="fa fa-plus"></i>
    //                     </a>
    //                 </div>
    //             </div>
    //             <section className="refresh">
    //                 <CheckBox name="refresh" info={I18n.t("processes.refresh")} value={refresh}
    //                           onChange={this.toggleRefresh}/>
    //             </section>
    //             <section className="processes">
    //                 {this.renderProcessesTable(filteredProcesses, actions, sorted, organisations)}
    //             </section>
    //         </div>
    //     );
    // }
    render() {
        return <div>hoi</div>
    }
}

Researchers.propTypes = {
    history: PropTypes.object.isRequired,
    currentUser: PropTypes.object.isRequired
};

