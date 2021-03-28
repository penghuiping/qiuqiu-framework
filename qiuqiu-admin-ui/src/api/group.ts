import axios, { AxiosResponse } from 'axios'
import { ElementUiTreeVo, JsonResponse } from '@/api/vo'
import { ApiConstant } from '@/api/index'
import { GroupCreateVo, GroupUpdateVo } from '@/api/vo/group'

class GroupApi {
  public static getAll (): Promise<AxiosResponse<JsonResponse<ElementUiTreeVo>>> {
    return axios.post(ApiConstant.GROUP_GET_ALL)
  }

  public static create (group: GroupCreateVo): Promise<AxiosResponse<JsonResponse<boolean>>> {
    return axios.post(ApiConstant.GROUP_CREATE, group)
  }

  public static update (group: GroupUpdateVo): Promise<AxiosResponse<JsonResponse<boolean>>> {
    return axios.post(ApiConstant.GROUP_UPDATE, group)
  }

  public static delete (id: number): Promise<AxiosResponse<JsonResponse<boolean>>> {
    return axios.post(ApiConstant.GROUP_DELETE, {
      groupId: id
    })
  }
}

export {
  GroupApi
}
