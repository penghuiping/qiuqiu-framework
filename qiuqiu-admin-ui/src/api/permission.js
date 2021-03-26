import axios from 'axios'
import { ApiConstant } from '@/api/index'
class PermissionApi {
  static create (permission) {
    return axios.post(ApiConstant.PERMISSION_CREATE, permission)
  }

  static update (permission) {
    return axios.post(ApiConstant.PERMISSION_UPDATE, permission)
  }

  static delete (id) {
    return axios.post(ApiConstant.PERMISSION_DELETE, {
      permissionIds: [id]
    })
  }

  static page () {
    return axios.post(ApiConstant.PERMISSION_PAGE)
  }
}
export { PermissionApi }
// # sourceMappingURL=permission.js.map
