import React from 'react';
import { RouteComponentProps } from 'react-router-dom';
import axios from 'axios';

import GoBackButton from 'components/button/GoBackButton';




interface IProps {
    v_id?: string;
    // v_id 가 있으면 같이 저장
}

class PostingForm extends React.Component<IProps, {}> {
    state = {
        p_content: "",
        selectedFile: new File([""], "", {type: ""}),
        imagePreview: "",
        v_id: null
    }

    handleChange = (e: any) => {
        this.setState({
            [e.target.name]: e.target.value
        })
    }

    handleFileChange = (e: any) => {
        var file = e.target.files[0];
        var reader = new FileReader();
        if (file && file.type.match('image.*')) {
            reader.readAsDataURL(file);
            this.setState({selectedFile: file})
        }
        reader.onloadend = () => {
            this.setState({imagePreview: reader.result as string })
        }
    }

    handleSubmit = (e: any) => {
        e.preventDefault();

        const fd = new FormData();
        fd.append("image", this.state.selectedFile);
        fd.set("data", this.state.p_content)
        if (this.props.v_id) {
            fd.append("v_id", this.props.v_id as string)
        }

        axios.post("http://localhost:3002/post", fd)
        .then(res => {
            console.log(res)
            console.log(fd.get("image"))
            console.log(fd.get("data"))
            console.log(fd.get("v_id"))
        })
        this.setState({
            p_content: "",
            selectedFile: new File([""], "", {type: ""}),
            imagePreview: "",
            v_id: null
        })
    }

    render() {
        let $imagePreview = (
            <div></div>
        );
        if (this.state.imagePreview) {
            $imagePreview = (
                <div>
                    <img src={this.state.imagePreview} alt="uploaded image" width="200" />
                </div>
            )
        }

        return (
            <div className="wrapC">
            <form
                onSubmit={this.handleSubmit}
                className="posting-form">
                <input
                    className="posting"
                    type="textarea"
                    name="content"
                    placeholder="내용을 입력하세요."
                    onChange={this.handleChange} />
                <input
                    type="file"
                    id="file"
                    onChange={this.handleFileChange}
                />
                <label htmlFor="file" className="btn-1">이미지 업로드</label>
                    {$imagePreview}
                <button className="my--btn" onClick={this.handleSubmit}>게시글 등록하기</button>
                <GoBackButton
                    text="취소하기"
                />
             </form>
             </div>
        );
    }
};


export default PostingForm;