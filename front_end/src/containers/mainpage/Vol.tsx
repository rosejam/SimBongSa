import React, { Component } from 'react';
import CertLabel from '../../components/label/CertLabel'

interface Props {
    volunteer: {
        id: number;
        status: number;
        many: number;
        bgnTm: number;
        endTm: number;
        location: string;
        adult: number;
        young: number;
        mBgnD: number;
        mEndD: number;
        pBgnD: number;
        pEndD: number;
        title: string;
        url: string;
    }
}

interface State {
}


export default class Vol extends Component<Props, State> {

    render() {
        return (
            <div>

                이름: {this.props.volunteer.title}<br/>
                staus: {this.props.volunteer.status} <br /><br/>
            </div>
        )
    }
}