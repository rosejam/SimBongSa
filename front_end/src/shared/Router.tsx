import React, { Component } from "react";
import { Route, BrowserRouter } from "react-router-dom";

// auth
import Join from "containers/join/Join";
import JoinComplete from "containers/join/JoinComplete";
import EmailComplete from "containers/join/EmailComplete";

import MailReSend from "containers/mailresend/MailReSend";

import Login from "containers/login/Login";

import FindPassword from "containers/findPassword/FindPassword";
import FindPasswordMailSend from "containers/findPassword/FindPasswordMailSend";

// user
import Mypage from "containers/mypage/Mypage";
import UserProfile from "containers/temp/temp";
import ChangePassword from "containers/findPassword/FindPasswordMailRecieve";

// curation
import Intro from "containers/intro/Intro";
import MainPage from "containers/mainpage/MainPage";
import SearchContainer from "containers/usersetting/SearchContainer";
import Location from "containers/location/Location";
import CalendarContainer from "containers/calendar/CalendarContainer";

import VolDetail from "components/vol/VolDetail";

import Feed from "containers/feed/Feed";
import PostingList from "containers/posting/PostingList";
import PostingForm from "containers/posting/PostForm";

class Router extends Component {
  render() {
    return (
        <BrowserRouter>
            {/* auth */}
            <Route exact path="/join" component={Join} />
            <Route path="/join/complete" component={JoinComplete} />
            <Route path="/email/:email/:key" component={EmailComplete} />

            <Route path="/mailresend" component={MailReSend} />

            <Route exact path="/" component={Login} />

            <Route path="/findpassword" component={FindPassword} />
            <Route path="/findpasswordmailsend" component={FindPasswordMailSend} />


            {/* // user */}
            <Route path="/mypage" component={Mypage} />
            <Route exact path="/userprofile" component={UserProfile} />
            <Route path="/changepassword/:token" component={ChangePassword} />

            {/* curation */}
            <Route path="/intro" component={Intro} />
            <Route exact path="/mainpage" component={MainPage} />
            <Route exact path="/usersetting" component={SearchContainer} />
            <Route path="/location" component={Location} />
            <Route path="/calendar" component={CalendarContainer} />

            <Route exact path="/vol/:id/detail" component={VolDetail} />


            <Route path="/feed" component={Feed} />
            <Route exact path="/:id/list" component={PostingList} />
            <Route exact path="/vol/:id/write" component={PostingForm} />
      </BrowserRouter>
    );
  }
}

export default Router;
