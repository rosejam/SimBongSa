import React, { Component } from "react";
import { Route } from "react-router-dom";
import Login from "containers/login/Login";
import Join from "containers/join/Join";
import FindPassword from "containers/findPassword/FindPassword";
import JoinComplete from "containers/join/JoinComplete";
import MailReSend from "containers/mailresend/MailReSend";
import FindPasswordMailSend from "containers/findPassword/FindPasswordMailSend";
import MainPage from "containers/mainpage/MainPage";
import VolDetail from "containers/mainpage/VolDetail";
import PostingForm from "containers/posting/PostingForm";
import PostingItem from "containers/posting/PostingItem";
import CalendarContainer from "containers/calendar/CalendarContainer";

class App extends Component {
  render() {
    return (
      <div>
        <Route exact path="/" component={Login} />
        <Route exact path="/join" component={Join} />
        <Route path="/findpassword" component={FindPassword} />
        <Route path="/findpasswordmailsend" component={FindPasswordMailSend} />
        <Route path="/join/complete" component={JoinComplete} />
        <Route path="/mailresend" component={MailReSend} />
        <Route path="/calendar" component={CalendarContainer} />
        <Route exact path="/mainpage" component={MainPage} />
        <Route exact path="/vol/detail/:id" component={VolDetail} />
        <Route exact path="/write" component={PostingForm} />
        <Route exact path="/list" component={PostingItem} />
      </div>
    );
  }
}

export default App;
