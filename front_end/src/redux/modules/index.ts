import { combineReducers } from "redux";
import auth from "./auth";
import user from "./user";
import vol from "./vol";
import posting from "./posting"
import search from './search'
import base from './base'
// import temp from "./temp";

import calendar from "./calendar";
import { penderReducer } from "redux-pender";

export default combineReducers({
  calendar,
  auth,
  user,
  vol,
  search,
  pender: penderReducer,

  // temp,
  posting,
  base,
});

