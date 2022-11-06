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
  config.headers.version = 'v1'

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
    if (code === 'A1101' || code === 'A1102' || code === 'A1103') {
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
  static BASE = '/api'
  static LOGIN = ApiConstant.BASE + '/other/login'
  static LOGOUT = ApiConstant.BASE + '/other/logout'
  static GET_INIT_CONFIG = ApiConstant.BASE + '/other/get_init_config'
  static USER_INFO = ApiConstant.BASE + '/user/info'
  static USER_DETAIL = ApiConstant.BASE + '/user/detail'
  static USER_PAGE = ApiConstant.BASE + '/user/page'
  static USER_DELETE = ApiConstant.BASE + '/user/delete'
  static USER_CREATE = ApiConstant.BASE + '/user/create'
  static USER_UPDATE = ApiConstant.BASE + '/user/update'
  static ROLE_GET_ALL = ApiConstant.BASE + '/role/get_all'
  static ROLE_DETAIL = ApiConstant.BASE + '/role/detail'
  static ROLE_PAGE = ApiConstant.BASE + '/role/page'
  static ROLE_DELETE = ApiConstant.BASE + '/role/delete'
  static ROLE_UPDATE = ApiConstant.BASE + '/role/update'
  static ROLE_CREATE = ApiConstant.BASE + '/role/create'
  static PERMISSION_PAGE = ApiConstant.BASE + '/permission/page'
  static PERMISSION_GET_ALL = ApiConstant.BASE + '/permission/get_all'
  static PERMISSION_CREATE = ApiConstant.BASE + '/permission/create'
  static PERMISSION_UPDATE = ApiConstant.BASE + '/permission/update'
  static PERMISSION_DELETE = ApiConstant.BASE + '/permission/delete'
  static PERMISSION_GET_BY_ROLE_ID = ApiConstant.BASE + '/permission/getByRoleId'
  static RESOURCE_PAGE = ApiConstant.BASE + '/resource/page'
  static RESOURCE_CREATE = ApiConstant.BASE + '/resource/create'
  static RESOURCE_UPDATE = ApiConstant.BASE + '/resource/update'
  static RESOURCE_DELETE = ApiConstant.BASE + '/resource/delete'
  static RESOURCE_GET_ALL = ApiConstant.BASE + '/resource/get_all'
  static RESOURCE_DETAIL = ApiConstant.BASE + '/resource/detail'
  static GROUP_GET_ALL = ApiConstant.BASE + '/group/get_all'
  static GROUP_CREATE = ApiConstant.BASE + '/group/create'
  static GROUP_UPDATE = ApiConstant.BASE + '/group/update'
  static GROUP_DELETE = ApiConstant.BASE + '/group/delete'
  static AUDIT_LOG_PAGE = ApiConstant.BASE + '/audit_log/page'
  static DICT_PAGE = ApiConstant.BASE + '/dict/page'
  static DICT_CREATE = ApiConstant.BASE + '/dict/create'
  static DICT_UPDATE = ApiConstant.BASE + '/dict/update'
  static DICT_DELETE = ApiConstant.BASE + '/dict/delete'
  static DICT_REFRESH = ApiConstant.BASE + '/dict/refresh'
  static JOB_PAGE = ApiConstant.BASE + '/job/page'
  static JOB_FIND_ALL = ApiConstant.BASE + '/job/get_all'
  static JOB_CREATE = ApiConstant.BASE + '/job/create'
  static JOB_UPDATE = ApiConstant.BASE + '/job/update'
  static JOB_DELETE = ApiConstant.BASE + '/job/delete'
  static JOB_LOG_PAGE = ApiConstant.BASE + '/job_log/page'
  static JOB_EXECUTION_PAGE = ApiConstant.BASE + '/job_execution/page'
  static JOB_EXECUTION_CREATE = ApiConstant.BASE + '/job_execution/create'
  static JOB_EXECUTION_UPDATE = ApiConstant.BASE + '/job_execution/update'
  static JOB_EXECUTION_DELETE = ApiConstant.BASE + '/job_execution/delete'
  static JOB_EXECUTION_REFRESH = ApiConstant.BASE + '/job_execution/refresh'
  static JOB_EXECUTION_REFRESH_ALL = ApiConstant.BASE + '/job_execution/refresh_all'
  static JOB_EXECUTION_STATISTIC = ApiConstant.BASE + '/job_execution/statistic'
}

export { ApiConstant }
