import React, { Component } from "react";
import { Container, Button, Image, Form } from "semantic-ui-react";
import { connect } from "react-redux";
import { bindActionCreators } from "redux";
import { List } from "immutable";
import * as PostingApi from "lib/api/PostingApi";
import * as UsergApi from "lib/api/UserApi";
import FloatingMessage from "components/message/FloatingMessage"
interface Props {
  mId: string;
  userId: string;
}
interface State {
  selectedFiles: any[];
  isSubmit: boolean
}

class Profile extends Component<Props, State> {
  state = { selectedFiles: [], isSubmit: false };
  componentDidMount() {
    const { userId } = this.props;
    // let data = UsergApi.getUserInfo(userId);
  }
  componentWillUnmount(){
    this.setState({isSubmit:false})
  }
  handleFileSelect = (e: any) => {
    var id = e.target.id;
    var value = e.target.files;
    this.setState({ selectedFiles: [value[0]] });
    // this.setState({ selectedFiles: newFileList });
  };

  handleSubmit = async (e: any) => {
    e.preventDefault();
    const { selectedFiles } = this.state;
    const { mId } = this.props;
    const file = new FormData();
    if (selectedFiles.length > 0) {
      file.append("file", selectedFiles[0]);
    }
    let data = await PostingApi.uploadProfileImage(mId, file);
    this.setState({isSubmit: true})
    // this.props.history.push(`/${v_id}/postinglist`);
    // this.goListPage();
  };
  render() {
    let { isSubmit } = this.state
    return (
      <div>
        <Container>
          프로필 사진 선택<br></br>
          가급적 정사각형 이미지를 사용해주세요!<br/>
          <Form>
            <input
              type="file"
              id="files"
              multiple
              accept="image/*"
              onChange={this.handleFileSelect}
              style={{marginBottom : '10px'}}
            />
            <Button onClick={this.handleSubmit}>프로필 사진 등록하기</Button>
            {isSubmit && <FloatingMessage/>}
          </Form>
        </Container>
      </div>
    );
  }
}

export default connect(
  ({ user }: any) => ({
    mId: user.get("loggedInfo").get("m_id"),
    userId: user.getIn(["loggedInfo", "userId"])
  }),
  dispatch => ({})
)(Profile);
