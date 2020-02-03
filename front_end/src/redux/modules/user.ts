import { createAction, handleActions } from "redux-actions";

import { Map } from "immutable";
import * as AuthAPI from "lib/api/UserApi";
import { pender } from "redux-pender";

const SET_SESSION_ID = "user/SET_SESSION_ID"; // 로그인 정보 설정
const SET_VALIDATED = "user/SET_VALIDATED"; // validated 값 설정
const LOGOUT = "user/LOGOUT"; // 로그아웃
const CHECK_STATUS = "user/CHECK_STATUS"; // 현재 로그인상태 확인

export const setLoggedInfo = createAction(SET_SESSION_ID); // loggedInfo
export const setValidated = createAction(SET_VALIDATED); // validated
export const logout = createAction(LOGOUT, AuthAPI.logout);
export const checkStatus = createAction(CHECK_STATUS, AuthAPI.checkStatus);
interface initialStateParams {
  setIn: any;
  set: any;
  loggedInfo: {
    // 현재 로그인중인 유저의 정보
    thumbnail: null;
    username: null;
  };
  logged: false; // 현재 로그인중인지 알려준다
  validated: false; // 이 값은 현재 로그인중인지 아닌지 한번 서버측에 검증했음을 의미
}
const initialState = Map({
  loggedInfo: Map({
    // 현재 로그인중인 유저의 정보
    sessionId: null
  }),
  logged: false, // 현재 로그인중인지 알려준다
  validated: false // 이 값은 현재 로그인중인지 아닌지 한번 서버측에 검증했음을 의미
});

export default handleActions<any>(
  {
    [SET_SESSION_ID]: (state, action) => {
      const { sessionId } = action.payload;
      return state
        .set("logged", true)
        .setIn(["loggedInfo", "sessionId"], sessionId);
    },
    [SET_VALIDATED]: (state, action) => state.set("validated", action.payload),
    ...pender({
      type: CHECK_STATUS,
      onSuccess: (state, action) =>
        state
          .set("loggedInfo", Map(action.payload.data))
          .set("validated", true),
      onFailure: (state, action) => initialState
    })
  },
  initialState
);
