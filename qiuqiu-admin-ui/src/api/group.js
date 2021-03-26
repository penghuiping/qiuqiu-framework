import axios from 'axios'
import { ApiConstant } from '@/api/index'
class GroupApi {
  static getAll () {
    return axios.post(ApiConstant.GROUP_GET_ALL)
  }

  static create (group) {
    return axios.post(ApiConstant.GROUP_CREATE, group)
  }

  static delete (id) {
    return axios.post(ApiConstant.GROUP_DELETE, {
      groupId: id
    })
  }
}
export { GroupApi }
// # sourceMappingURL=group.js.map
