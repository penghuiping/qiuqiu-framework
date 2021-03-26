import axios, { AxiosResponse } from 'axios'
import { ElementUiTreeVo, JsonResponse, PageVo } from '@/api/vo'
import { ApiConstant } from '@/api/index'
import { RoleCreateVo, RoleDetailVo, RoleListVo, RoleUpdateVo, RoleVo } from '@/api/vo/role'
import { ResourcePermissionsVo } from '@/api/vo/resouce'

class RoleApi {
  public static getAll (): Promise<AxiosResponse<JsonResponse<RoleVo[]>>> {
    return axios.post(ApiConstant.ROLE_GET_ALL)
  }

  public static detail (id: number): Promise<AxiosResponse<JsonResponse<RoleDetailVo>>> {
    return axios.post(ApiConstant.ROLE_DETAIL, {
      roleId: id
    })
  }

  public static delete (id: number): Promise<AxiosResponse<JsonResponse<boolean>>> {
    return axios.post(ApiConstant.ROLE_DELETE, {
      roleIds: [id]
    })
  }

  public static update (role: RoleUpdateVo): Promise<AxiosResponse<JsonResponse<boolean>>> {
    return axios.post(ApiConstant.ROLE_UPDATE, role)
  }

  public static create (role: RoleCreateVo): Promise<AxiosResponse<JsonResponse<boolean>>> {
    return axios.post(ApiConstant.ROLE_CREATE, role)
  }

  public static page (pageNum: number, pageSize: number): Promise<AxiosResponse<JsonResponse<PageVo<RoleListVo>>>> {
    return axios.post(ApiConstant.ROLE_PAGE, {
      pageNum: pageNum,
      pageSize: pageSize
    })
  }

  public static getAllSystemPermissions (): Promise<AxiosResponse<JsonResponse<ResourcePermissionsVo[]>>> {
    return axios.post(ApiConstant.RESOURCE_GET_ALL)
  }

  public static getPermissionsByRoleID (roleId: string): Promise<AxiosResponse<JsonResponse<ElementUiTreeVo[]>>> {
    return axios.post(ApiConstant.PERMISSION_GET_BY_ROLE_ID, {
      roleId: roleId
    })
  }
}

export { RoleApi }
