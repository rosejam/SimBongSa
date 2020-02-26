import React, { Component } from "react";
import ListUp from "components/intro/ListUp";
import { Container, Button } from "semantic-ui-react";
import { connect } from "react-redux";
import { Link } from "react-router-dom";
import './Intro.css'
interface Props {
  loginCheck: boolean;
}
interface State { }
class Intro extends Component<Props & any, State> {
  scrollBottom() {
    window.scrollTo(0, window.innerHeight)
  }
  render() {
    const { loginCheck } = this.props;
    return (
      <div>
        <Container>
          <ListUp />
          {!loginCheck &&
            <div className="loginbutton" >
              <Link to="/login" ><Button as="a" className="loginbutton">로그인</Button></Link>
            </div>}
        </Container>
      </div>
    );
  }
}

export default connect(
  ({ auth }: any) => ({
    loginCheck: auth.get("loginCheck")
  }),
  dispatch => ({
  })
)(Intro);
