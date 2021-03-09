import axios from 'axios'
import store from '@/store'
import { JsonResponse } from '@/api/vo'

// 添加请求拦截器
axios.interceptors.request.use(function (config) {
  // 在发送请求之前做些什么
  if (store.state.token) { // 判断是否存在token，如果存在的话，则每个http header都加上token
    config.headers.jwt = `${store.state.token}`
  }
  return config
}, function (error) {
  // 对请求错误做些什么
  return Promise.reject(error)
})

// 添加响应拦截器
axios.interceptors.response.use(
  function (response) {
    // 对响应数据做点什么
    console.log('response:', response)
    const jsonResponse = new JsonResponse('', '', '')
    Object.assign(jsonResponse, response.data)
    const code = jsonResponse.code
    if (code === '10001' || code === '10002') {
      store.commit('logout')
    }
    return response
  },
  function (error) {
    // 对响应错误做点什么
    return Promise.reject(error)
  }
)

class ApiConstant {
  static LOGIN = '/user/login'
  static USER_INFO = '/user/info'
  static USER_DETAIL = '/user/detail'
  static USER_PAGE = '/user/page'
  static USER_DELETE = '/user/delete'
  static USER_CREATE = '/user/create'
  static USER_UPDATE = '/user/update'
  static ROLE_GET_ALL = '/role/getAll'
  static ROLE_DETAIL = '/role/detail'
  static ROLE_PAGE = '/role/page'
  static ROLE_DELETE = '/role/delete'
  static ROLE_UPDATE = '/role/update'
  static ROLE_CREATE = '/role/create'
  static PERMISSION_PAGE = '/permission/page'
  static PERMISSION_DELETE = '/permission/delete'
  static PERMISSION_GET_ALL = '/permission/getAll'
  static PERMISSION_GET_BY_ROLE_ID = '/permission/getByRoleId'
  static GROUP_GET_ALL = '/group/getAll'
}

export { ApiConstant }
