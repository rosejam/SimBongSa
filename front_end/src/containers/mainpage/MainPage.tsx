import React, { Component } from "react";
import VolList from "./VolList";

import "assets/mycss";

export default class MainPage extends Component {
  render() {
    return (
      <div className="user" id="login">
        <div className="wrapC">
          <div>
            <img className="titleimg" src="/images/volunteer.png" />
            <h1 className="title titleheader">최신 봉사활동 정보</h1>
          </div>
          <div>
            <VolList />
          </div>
        </div>
      </div>
    );
  }
}
