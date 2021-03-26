import axios from 'axios'
import { ApiConstant } from '@/api/index'
class ResourceApi {
  static create (resource) {
    return axios.post(ApiConstant.RESOURCE_CREATE, permission)
  }

  static update (resource) {
    return axios.post(ApiConstant.RESOURCE_UPDATE, permission)
  }

  static delete (name) {
    return axios.post(ApiConstant.RESOURCE_DELETE, {
      permissionIds: [name]
    })
  }

  static page () {
    return axios.post(ApiConstant.RESOURCE_PAGE)
  }
}
export { ResourceApi }
// # sourceMappingURL=resource.js.map
