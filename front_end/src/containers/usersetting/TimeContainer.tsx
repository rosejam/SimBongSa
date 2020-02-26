import React, { Component, Fragment } from "react";
import { connect } from "react-redux";
import { bindActionCreators } from "redux";
import * as searchActions from "redux/modules/search";
import { Checkbox } from 'semantic-ui-react'
import "./TimeContainer.css"
interface Props {
    times: any
    SearchActions: any
}
interface State { }
class TimeContainer extends Component<Props, State> {
    state = {};

    handleToggle = (timeName: string) => {
        const { SearchActions } = this.props;
        SearchActions.toggle({ id: "times", value: timeName });
    };
    render() {
        const { handleToggle } = this;
        const { times } = this.props;
        const { morning, afternoon } = times.toJS();
        return (
            <Fragment>
                <Checkbox
                    label='오전'
                    checked={morning}
                    onChange={() => handleToggle('morning')}
                />
                <Checkbox
                    label='오후'
                    checked={afternoon}
                    onClick={() => handleToggle('afternoon')}
                />
            </Fragment>
        );
    };
}



export default connect(
    ({ search }: any) => ({
        times: search.get('times'),
    }),
    dispatch => ({
        SearchActions: bindActionCreators(searchActions, dispatch)
    })
)(TimeContainer);
