import axios from 'axios'
let baseURL="http://localhost:8083/";
import Cookies from 'js-cookie';


const instance = axios.create({
  headers: {
    // 'accessToken': accessToken
  },
  baseURL: baseURL,
  timeout: 10000,
  withCredentials: true////设置cross跨域 并设置访问权限 允许跨域携带cookie信息
})

// 添加请求拦截器
instance.interceptors.request.use(config => {
  // 在发送请求之前做某事，比如说 设置token
  // config.headers['accessToken'] = accessToken;
  return config;
}, error => {
  // 请求错误时做些事
  return Promise.reject(error);
});

// 添加响应拦截器
instance.interceptors.response.use(response => {
  // 对响应数据做些事
  if (response.status === 200) {
    console.log(response,response.data.code)
    if (response.data && response.data.code === '200') {
      console.log('成功')
      // response.data.data.value = '我是返回成功' // 在请求成功后可以对返回的数据进行处理，再返回到前台
    } else {
      console.log('返回到登录...')
    }
  }
  return response;
}, error => {
  return Promise.reject(error.response.data); // 返回接口返回的错误信息
})

export default instance;
