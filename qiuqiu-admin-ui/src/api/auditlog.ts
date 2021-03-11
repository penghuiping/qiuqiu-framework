import axios, { AxiosResponse } from 'axios'
import { JsonResponse, PageVo } from '@/api/vo'
import { ApiConstant } from '@/api/index'
import { AuditLogVo } from '@/api/vo/AuditLog'

class AuditLogApi {
  public static page (pageNum: number, pageSize: number): Promise<AxiosResponse<JsonResponse<PageVo<AuditLogVo>>>> {
    return axios.post(ApiConstant.AUDIT_LOG_PAGE, {
      pageNum: pageNum,
      pageSize: pageSize
    })
  }
}

export {
  AuditLogApi
}
