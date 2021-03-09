import axios, { AxiosResponse } from 'axios'
import { JsonResponse } from '@/api/vo'
import { ApiConstant } from '@/api/index'
import { PermissionCreateVo, PermissionUpdateVo, PermissionVo } from '@/api/vo/permission'

class PermissionApi {
  public static create (permission: PermissionCreateVo): Promise<AxiosResponse<JsonResponse<boolean>>> {
    return axios.post(ApiConstant.PERMISSION_CREATE, permission)
  }

  public static update (permission: PermissionUpdateVo): Promise<AxiosResponse<JsonResponse<boolean>>> {
    return axios.post(ApiConstant.PERMISSION_UPDATE, permission)
  }

  public static delete (id: number): Promise<AxiosResponse<JsonResponse<boolean>>> {
    return axios.post(ApiConstant.PERMISSION_DELETE, {
      permissionIds: [id]
    })
  }

  public static page (): Promise<AxiosResponse<JsonResponse<PermissionVo[]>>> {
    return axios.post(ApiConstant.PERMISSION_PAGE)
  }
}

export {
  PermissionApi
}
