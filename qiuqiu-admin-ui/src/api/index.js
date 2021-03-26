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
axios.interceptors.response.use(function (response) {
  // 对响应数据做点什么
  console.log('response:', response)
  const jsonResponse = new JsonResponse('', '', '')
  Object.assign(jsonResponse, response.data)
  const code = jsonResponse.code
  if (code === '10001' || code === '10002') {
    store.commit('logout')
  }
  return response
}, function (error) {
  // 对响应错误做点什么
  return Promise.reject(error)
})
class ApiConstant {
}
ApiConstant.LOGIN = '/user/login'
ApiConstant.LOGOUT = '/user/logout'
ApiConstant.USER_INFO = '/user/info'
ApiConstant.USER_DETAIL = '/user/detail'
ApiConstant.USER_PAGE = '/user/page'
ApiConstant.USER_DELETE = '/user/delete'
ApiConstant.USER_CREATE = '/user/create'
ApiConstant.USER_UPDATE = '/user/update'
ApiConstant.ROLE_GET_ALL = '/role/getAll'
ApiConstant.ROLE_DETAIL = '/role/detail'
ApiConstant.ROLE_PAGE = '/role/page'
ApiConstant.ROLE_DELETE = '/role/delete'
ApiConstant.ROLE_UPDATE = '/role/update'
ApiConstant.ROLE_CREATE = '/role/create'
ApiConstant.PERMISSION_PAGE = '/permission/page'
ApiConstant.PERMISSION_CREATE = '/permission/create'
ApiConstant.PERMISSION_UPDATE = '/permission/update'
ApiConstant.PERMISSION_DELETE = '/permission/delete'
ApiConstant.PERMISSION_GET_ALL = '/permission/getAll'
ApiConstant.PERMISSION_GET_BY_ROLE_ID = '/permission/getByRoleId'
ApiConstant.RESOURCE_PAGE = '/resource/page'
ApiConstant.RESOURCE_CREATE = '/resource/create'
ApiConstant.RESOURCE_UPDATE = '/resource/update'
ApiConstant.RESOURCE_DELETE = '/resource/delete'
ApiConstant.GROUP_GET_ALL = '/group/getAll'
ApiConstant.GROUP_CREATE = '/group/create'
ApiConstant.GROUP_DELETE = '/group/delete'
ApiConstant.AUDIT_LOG_PAGE = '/audit_log/page'
ApiConstant.DICT_PAGE = '/dict/page'
ApiConstant.DICT_CREATE = '/dict/create'
ApiConstant.DICT_UPDATE = '/dict/update'
ApiConstant.DICT_DELETE = '/dict/delete'
ApiConstant.DICT_REFRESH = '/dict/refresh'
ApiConstant.JOB_PAGE = '/job/page'
ApiConstant.JOB_find_all = '/job/find_all'
ApiConstant.JOB_CREATE = '/job/create'
ApiConstant.JOB_UPDATE = '/job/update'
ApiConstant.JOB_DELETE = '/job/delete'
ApiConstant.JOB_LOG_PAGE = '/job/log/page'
ApiConstant.JOB_EXECUTION_PAGE = '/job/execution/page'
ApiConstant.JOB_EXECUTION_CREATE = '/job/execution/create'
ApiConstant.JOB_EXECUTION_UPDATE = '/job/execution/update'
ApiConstant.JOB_EXECUTION_DELETE = '/job/execution/delete'
ApiConstant.JOB_EXECUTION_REFRESH = '/job/execution/refresh'
ApiConstant.JOB_EXECUTION_REFRESH_ALL = '/job/execution/refresh_all'
ApiConstant.JOB_EXECUTION_STATISTIC = '/job/execution/statistic'
export { ApiConstant }
// # sourceMappingURL=index.js.map
