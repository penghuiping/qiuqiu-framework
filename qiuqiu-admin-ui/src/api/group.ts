import axios, {AxiosResponse} from 'axios'
import {ElementUiTreeVo, JsonResponse} from '@/api/vo'
import {ApiConstant} from '@/api/index'

class GroupApi {
  public static getAll(): Promise<AxiosResponse<JsonResponse<ElementUiTreeVo>>> {
    return axios.post(ApiConstant.GROUP_GET_ALL)
  }
}

export {
  GroupApi
}
