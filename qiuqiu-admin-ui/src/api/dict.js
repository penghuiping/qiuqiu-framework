import axios from 'axios'
import { ApiConstant } from '@/api/index'
class DictApi {
  static page (key, pageNum, pageSize) {
    return axios.post(ApiConstant.DICT_PAGE, {
      key: key,
      pageNum: pageNum,
      pageSize: pageSize
    })
  }

  static create (dict) {
    return axios.post(ApiConstant.DICT_CREATE, dict)
  }

  static update (dict) {
    return axios.post(ApiConstant.DICT_UPDATE, dict)
  }

  static delete (key) {
    return axios.post(ApiConstant.DICT_DELETE, {
      key: key
    })
  }

  static refresh (key) {
    return axios.post(ApiConstant.DICT_REFRESH, {
      key: key
    })
  }
}
export { DictApi }
// # sourceMappingURL=dict.js.map
