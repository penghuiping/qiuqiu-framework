import axios, { AxiosResponse } from 'axios'
import { ElementUiTreeVo, JsonResponse, PageVo, RoleDetailVo, RoleListVo, RoleVo } from '@/api/vo'
import { ApiConstant } from '@/api/index'

class RoleApi {
  public static getAll (): Promise<AxiosResponse<JsonResponse<RoleVo[]>>> {
    return axios.post(ApiConstant.ROLE_GET_ALL)
  }

  public static detail (id: string): Promise<AxiosResponse<JsonResponse<RoleDetailVo>>> {
    return axios.post(ApiConstant.ROLE_DETAIL, {
      id: id
    })
  }

  public static delete (id: string): Promise<AxiosResponse<JsonResponse<boolean>>> {
    return axios.post(ApiConstant.ROLE_DELETE, {
      id: id
    })
  }

  public static page (pageNum: number, pageSize: number): Promise<AxiosResponse<JsonResponse<PageVo<RoleListVo>>>> {
    return axios.post(ApiConstant.ROLE_PAGE, {
      pageNum: pageNum,
      pageSize: pageSize
    })
  }

  public static getAllSystemPermissions (): Promise<AxiosResponse<JsonResponse<ElementUiTreeVo[]>>> {
    return axios.post(ApiConstant.PERMISSION_GET_ALL)
  }

  public static getPermissionsByRoleID (roleId: string): Promise<AxiosResponse<JsonResponse<ElementUiTreeVo[]>>> {
    return axios.post(ApiConstant.PERMISSION_GET_BY_ROLE_ID, {
      roleId: roleId
    })
  }
}

export { RoleApi }
