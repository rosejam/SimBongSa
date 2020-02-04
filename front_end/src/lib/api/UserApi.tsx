import axios, { AxiosResponse } from "axios";

const restBaseApi = "http://70.12.247.87:8080/"; // 신호꺼
// const restBaseApi = "http://70.12.247.34:8080/"; // 박정환
export const checkEmailExists = (email: string) => {
  try {
    console.log("API email check : ", email);
    return axios.get(restBaseApi + "rest/CheckEmail/" + email);
  } catch (error) {
    console.log(error)
    return false;
  }
};
export const checkUsernameExists = (userid: string) => {
  // axios
  //   .get(restBaseApi + "CheckId/" + userid)
  //   .then(response => {
  //     console.log("ax response:", response);

  //     return true;
  //   })
  //   .catch(error => {
  //     console.log("ax error:", error);
  //     return false;
  //   });
  // };
  try {
    return axios.get(restBaseApi + "rest/CheckId/" + userid);
  } catch (error) {
    return true;
  }
};
interface Iregister {
  email: string
  password: string
  userid: string
}
export const localRegister: ({ email, password, userid }: Iregister) => false | Promise<AxiosResponse<any>> = ({ email, password, userid }: Iregister) => {
  let data = {
    m_email: email,
    m_password: password,
    m_userid: userid,
  };
  try {
    console.log("체크 : ", data);
    return axios.post(restBaseApi + "register", data);
  } catch (error) {
    return false;
  }
  // try {
  //   return axios.post(restBaseApi + "Member", data);
  // } catch (error) {
  //   console.log(error);
  //   return true;
  // }
};
interface Ilogin {
  email: string
  password: string
}
export const localLogin: ({ email, password }: Ilogin) => false | Promise<AxiosResponse<any>> = ({ email, password }: Ilogin) => {
  let data = {
    password: password,
    username: email
  };
  try {
    return axios.post(restBaseApi + "authenticate", data);
  } catch (error) {
    return false;
  }
};

export const checkStatus = (data: { email: string; password: string }) => {
  try {
    return axios.post(restBaseApi + "authenticate", {
      password: data.password,
      username: data.email
    });
  } catch (error) {
    return true;
  }
};
export const logout = () => {
  try {
    return axios.post("/api/auth/logout");
  } catch (error) {
    return true;
  }
};
