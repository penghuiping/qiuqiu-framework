import axios from 'axios'
import { ApiConstant } from '@/api/index'
class UserApi {
  static login (username, password) {
    return axios.post(ApiConstant.LOGIN, {
      username: username,
      password: password
    })
  }

  static logout () {
    return axios.post(ApiConstant.LOGOUT)
  }

  static info () {
    return axios.post(ApiConstant.USER_INFO)
  }

  static detail (userId) {
    return axios.post(ApiConstant.USER_DETAIL, {
      userId: userId
    })
  }

  static page (username, pageNum, pageSize) {
    return axios.post(ApiConstant.USER_PAGE, {
      username: username,
      pageNum: pageNum,
      pageSize: pageSize
    })
  }

  static delete (id) {
    return axios.post(ApiConstant.USER_DELETE, {
      userIds: [id]
    })
  }

  static create (userCreateVo) {
    return axios.post(ApiConstant.USER_CREATE, userCreateVo)
  }

  static update (userUpdateVo) {
    return axios.post(ApiConstant.USER_UPDATE, userUpdateVo)
  }
}
export { UserApi }
// # sourceMappingURL=user.js.map
