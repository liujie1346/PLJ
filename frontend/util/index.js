import axios from './http'

let request = {}

let accessToken = localStorage.getItem("accessToken");
console.log("accessToken==",accessToken);

request.get = function (url, params) {
  return axios({
    method: 'get',
    url: url,
    params: params,
    headers: {
      "accessToken": accessToken
    }
  });
};

request.post = function (url, params) {
  return axios({
    method: 'post',
    url:  url,
    data: params,
    transformRequest: [function (data) {
      let ret = '';
      for (let it in data) {
        ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&';
      }
      return ret;
    }],
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded',
      "accessToken": accessToken
    }
  });
};

export default () => {
  window.request = request;
};
