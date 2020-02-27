import React, { Component } from 'react'
import { bindActionCreators } from "redux";
import { connect } from "react-redux";
import * as UserApi from 'lib/api/UserApi'
import * as userActions from "redux/modules/user";
import UserProfile from 'components/user/profile/UserProfile';
import { List } from 'semantic-ui-react';
interface Props {
    match : any;
    loginUserId : any;
    userProfileMap : any;
}
interface State {
    followingList : string[];
}

class FollowingList extends Component<Props, State> {
    state = { followingList : []}
    componentDidMount(){
        const userId = this.props.match.params.id;
        let returnAxios = UserApi.getUserFollowing(userId);
        let followingList = [];
        returnAxios.then(
            (response : any)=>{
                followingList = response.data.data.map((item:any)=>item.m_userid)
                this.setState({followingList : followingList})
            }
        )
    }
    getProfileList(list : string[]){
        return list.map((userId:string)=>{
            return <List.Item style={{height: "70px"}}>
                <UserProfile profileSize="mini" profileUserId={userId} />
            </List.Item>
        })
    }
    render() {
        const { followingList } = this.state;
        const { getProfileList } = this;
        const userId = this.props.match.params.id;
    // const followingList = userProfileMap.get(profileUserId).get('followingList');
    // let followerList = [], followingList = [],  isProfileUserFollowedByLoginUser = false;
    return (
        <div>
        <UserProfile profileUserId={userId} />
          <span style={{ marginLeft: "1rem" }}>{userId} 님의 팔로잉</span> <span style={{ fontWeight: "bold" }}>
              <List celled relaxed>
                {getProfileList(followingList)}
              </List>
              </span>
        </div>
    );
    }
}

export default connect(
    ({ user }: any, ownProps: any) => {
      return {
        loginUserId: user.getIn(["loggedInfo", "userId"]),
      };
    },
    dispatch => ({
      UserActions: bindActionCreators(userActions, dispatch)
    })
  )(FollowingList);
  