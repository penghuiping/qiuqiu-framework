import axios, { AxiosResponse } from 'axios'
import { JsonResponse, PageVo } from '@/api/vo'
import { ApiConstant } from '@/api/index'
import { AuditLogVo } from '@/api/vo/AuditLog'

class AuditLogApi {
  public static page (searchKey: string, pageNum: number, pageSize: number): Promise<AxiosResponse<JsonResponse<PageVo<AuditLogVo>>>> {
    return axios.post(ApiConstant.AUDIT_LOG_PAGE, {
      username: searchKey,
      pageNum: pageNum,
      pageSize: pageSize
    })
  }
}

export {
  AuditLogApi
}
