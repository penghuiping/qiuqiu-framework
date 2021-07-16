import axios from 'axios'
import store from '@/store'
import { JsonResponse } from '@/api/vo'

axios.defaults.withCredentials = true

// 添加请求拦截器
axios.interceptors.request.use(function (config) {
  // 在发送请求之前做些什么
  if (store.state.token) { // 判断是否存在token，如果存在的话，则每个http header都加上token
    config.headers.jwt = `${store.state.token}`
  }

  console.log('header:', config.headers)

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
    if (code === '10001' || code === '10002' || code === '10003') {
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
  static LOGOUT = '/user/logout'
  static USER_INFO = '/user/info'
  static USER_DETAIL = '/user/detail'
  static USER_PAGE = '/user/page'
  static USER_DELETE = '/user/delete'
  static USER_CREATE = '/user/create'
  static USER_UPDATE = '/user/update'
  static ROLE_GET_ALL = '/role/get_all'
  static ROLE_DETAIL = '/role/detail'
  static ROLE_PAGE = '/role/page'
  static ROLE_DELETE = '/role/delete'
  static ROLE_UPDATE = '/role/update'
  static ROLE_CREATE = '/role/create'
  static PERMISSION_PAGE = '/permission/page'
  static PERMISSION_CREATE = '/permission/create'
  static PERMISSION_UPDATE = '/permission/update'
  static PERMISSION_DELETE = '/permission/delete'
  static PERMISSION_GET_BY_ROLE_ID = '/permission/getByRoleId'
  static RESOURCE_PAGE = '/resource/page'
  static RESOURCE_CREATE = '/resource/create'
  static RESOURCE_UPDATE = '/resource/update'
  static RESOURCE_DELETE = '/resource/delete'
  static RESOURCE_GET_ALL = '/resource/get_all'
  static RESOURCE_DETAIL = '/resource/detail'
  static GROUP_GET_ALL = '/group/get_all'
  static GROUP_CREATE = '/group/create'
  static GROUP_UPDATE = '/group/update'
  static GROUP_DELETE = '/group/delete'
  static AUDIT_LOG_PAGE = '/audit_log/page'
  static DICT_PAGE = '/dict/page'
  static DICT_CREATE = '/dict/create'
  static DICT_UPDATE = '/dict/update'
  static DICT_DELETE = '/dict/delete'
  static DICT_REFRESH = '/dict/refresh'
  static JOB_PAGE = '/job/page'
  static JOB_FIND_ALL = '/job/get_all'
  static JOB_CREATE = '/job/create'
  static JOB_UPDATE = '/job/update'
  static JOB_DELETE = '/job/delete'
  static JOB_LOG_PAGE = '/job_log/page'
  static JOB_EXECUTION_PAGE = '/job_execution/page'
  static JOB_EXECUTION_CREATE = '/job_execution/create'
  static JOB_EXECUTION_UPDATE = '/job_execution/update'
  static JOB_EXECUTION_DELETE = '/job_execution/delete'
  static JOB_EXECUTION_REFRESH = '/job_execution/refresh'
  static JOB_EXECUTION_REFRESH_ALL = '/job_execution/refresh_all'
  static JOB_EXECUTION_STATISTIC = '/job_execution/statistic'
}

export { ApiConstant }
