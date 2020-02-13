import { combineReducers } from "redux";
import auth from "./auth";
import user from "./user";
import volunteer from "./volunteer"
import vol from "./vol";
import search from './search'
import base from './base'
// import temp from "./temp";

import calendar from "./calendar";
import { penderReducer } from "redux-pender";

export default combineReducers({
  calendar,
  auth,
  user,
  volunteer,
  vol,
  search,
  pender: penderReducer,

  // temp,
  base,
});

