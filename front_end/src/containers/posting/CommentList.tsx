import React from 'react';
import axios from 'axios';
import Comment from './Comment';
import storage from 'lib/storage'

const restBaseApi = process.env.REACT_APP_REST_BASE_API!;
let token = storage.get("token")

interface Props {
    inP_id: number;
}

interface States {
    comments: Array<any>
}


class CommentList extends React.Component<Props, States> {
    constructor(props: Props) {
        super(props)
        this.state = {
            comments: []
        }

        axios.get(restBaseApi + `/rest/Comment/${this.props.inP_id}/`,
        { headers: { Authorization: "Bearer " + token }})

        .then( res => {
            if (res.data.data.length > 0) {
                const data = res.data.data.map( (d: any) => {
                        return {c_id: d.c_id, p_id: d.p_id, c_content: d.c_content, m_id: d.m_id, userId: d.m_userid}
                })
                this.setState({
                    comments: data
                })
            }
        })

    }

    render() {
        const prints = this.state.comments.map( (comment, i) => {
                return (
                    <Comment
                        comment={comment}
                        inP_id={comment.p_id}
                        key={i}
                    />
                )
        })

        return (
            <div>
                {this.state.comments.length > 0 ? 
                (prints)
                : (<div style={{color: 'rgb(185, 185, 185)'}}>첫번째 댓글을 작성해보세요.</div> )}
            </div>
    )}
};

export default CommentList;