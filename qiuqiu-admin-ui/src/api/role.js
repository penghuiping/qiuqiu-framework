import axios from 'axios'
import { ApiConstant } from '@/api/index'
class RoleApi {
  static getAll () {
    return axios.post(ApiConstant.ROLE_GET_ALL)
  }

  static detail (id) {
    return axios.post(ApiConstant.ROLE_DETAIL, {
      roleId: id
    })
  }

  static delete (id) {
    return axios.post(ApiConstant.ROLE_DELETE, {
      roleIds: [id]
    })
  }

  static update (role) {
    return axios.post(ApiConstant.ROLE_UPDATE, role)
  }

  static create (role) {
    return axios.post(ApiConstant.ROLE_CREATE, role)
  }

  static page (pageNum, pageSize) {
    return axios.post(ApiConstant.ROLE_PAGE, {
      pageNum: pageNum,
      pageSize: pageSize
    })
  }

  static getAllSystemPermissions () {
    return axios.post(ApiConstant.PERMISSION_GET_ALL)
  }

  static getPermissionsByRoleID (roleId) {
    return axios.post(ApiConstant.PERMISSION_GET_BY_ROLE_ID, {
      roleId: roleId
    })
  }
}
export { RoleApi }
// # sourceMappingURL=role.js.map
