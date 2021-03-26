import axios from 'axios'
import { ApiConstant } from '@/api/index'
class AuditLogApi {
  static page (searchKey, pageNum, pageSize) {
    return axios.post(ApiConstant.AUDIT_LOG_PAGE, {
      username: searchKey,
      pageNum: pageNum,
      pageSize: pageSize
    })
  }
}
export { AuditLogApi }
// # sourceMappingURL=auditlog.js.map
