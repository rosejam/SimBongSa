import { createAction, handleActions } from "redux-actions";
import { Record, List, Map } from "immutable";
import { pender } from "redux-pender";
import * as UserAPI from "lib/api/UserApi";
const CHANGE_INPUT = "searchlocation/CHANGE_INPUT";
const INSERT = "searchlocation/INSERT";
const TOGGLE = "searchlocation/TOGGLE";
const REMOVE = "searchlocation/REMOVE";
const PREFER_REGISTER = "searchlocation/PREFER_REGISTER";
const INITIAL_INSERT = "searchlocation/INTIALIZE_FORM"
export const changeInput = createAction(CHANGE_INPUT);
export const insert = createAction(INSERT);
export const toggle = createAction(TOGGLE);
export const remove = createAction(REMOVE);
export const preferRegister = createAction(PREFER_REGISTER, UserAPI.localPreferRegister)
export const initialInsert = createAction(INITIAL_INSERT)
let idLocation = 0; // 아이템에 들어갈 고유 값 입니다
let idCategory = 0; // 아이템에 들어갈 고유 값 입니다
// Record 함수는 Record 형태 데이터를 만드는 함수를 반환합니다.
// 따라서, 만든 다음에 뒤에 () 를 붙여줘야 데이터가 생성됩니다.
const initialState = Record({
  input: "",
  key: "",
  locations: List(),
  categorys: List(),
  placeholder: "지역을 입력해주세요.",
  times: Map({
    morning: false,
    afternoon: false
  }),
  ages: Map({
    youth: false,
    adult: false
  }),
  error: null,
})();
// Todo 아이템의 형식을 정합니다.
const SearchLocationRecord = Record({
  id: idLocation++,
  text: "",
  key: ""
});
const SearchCategoryRecord = Record({
  id: idCategory++,
  text: "",
  key: ""
});
export default handleActions<any, any>(
  {
    [INITIAL_INSERT]: (state, action) => {
      const { form, key, value } = action.payload
      console.log("initail_insert", form, key, value)
      return state.setIn([form, key], value);
    },
    [CHANGE_INPUT]: (state, action) => {
      console.log("change input", action.payload);
      const { input, key } = action.payload
      return state.setIn(['input'], input).setIn(['key'], key);
      // .set("placeholder", "지역을 입력해주세요.");
    },
    [INSERT]: (state, action) => {
      // Record 를 사용해야 아이템도 Record 형식으로 조회 가능합니다.
      // 빠져있는 값은, 기본값을 사용하게 됩니다 (checked: false)
      console.log("INSERT", state, action.payload);
      const { form, text, key } = action.payload
      console.log('form', form, 'text', text, 'key', key)
      if (form === 'location') {
        const item = SearchLocationRecord({ id: idLocation++, text: text, key: key });
        return state.update("locations", (locations: any) => locations.push(item));
      }
      else if (form === 'category') {
        const item = SearchCategoryRecord({ id: idCategory++, text: text, key: key });
        return state.update("categorys", (categorys: any) => categorys.push(item));
      }
      // .set("placeholder", "지역을 입력해주세요.");
    },
    [TOGGLE]: (state, action) => {
      const { id, value, othervalue } = action.payload
      console.log(id, value, othervalue)
      // console.log("timeName", timeName)
      // console.log(state)
      // console.log(state.times.get(timeName))
      if (typeof (othervalue) === 'string') {
        return state.setIn(
          [id, othervalue], state.get(id).get(value)
        ).setIn([id, value], !state.get(id).get(value));
      }
      else {
        return state.setIn(
          [id, value], !state.get(id).get(value))
      }
    },
    [REMOVE]: (state, action) => {
      const { form, id } = action.payload
      if (form === 'location') {
        const index = state
          .get("locations")
          .findIndex((item: any) => item.get("id") === id);
        return state.deleteIn(["locations", index]);
      }
      else if (form === 'category') {
        const index = state
          .get("categorys")
          .findIndex((item: any) => item.get("id") === id);
        return state.deleteIn(["categorys", index]);
      }
    },
    ...pender({
      type: PREFER_REGISTER,
      onSuccess: (state, action) => {
        return state.set("result", Map(action.payload.data))
      }
    })
  },
  initialState
);
