import axios, { AxiosResponse } from 'axios'
import { JsonResponse, PageVo } from '@/api/vo'
import { ApiConstant } from '@/api/index'
import { PermissionVo } from '@/api/vo/user'

class PermissionApi {
  public static delete (id: number): Promise<AxiosResponse<JsonResponse<boolean>>> {
    return axios.post(ApiConstant.PERMISSION_DELETE, {
      id: [id]
    })
  }

  public static page (): Promise<AxiosResponse<JsonResponse<PageVo<PermissionVo>>>> {
    return axios.post(ApiConstant.PERMISSION_PAGE)
  }
}

export {
  PermissionApi
}
