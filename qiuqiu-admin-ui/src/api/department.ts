import axios, { AxiosResponse } from 'axios'
import { ElementUiTreeVo, JsonResponse } from '@/api/vo'
import { ApiConstant } from '@/api/index'

class DepartmentApi {
  public static getAll (): Promise<AxiosResponse<JsonResponse<ElementUiTreeVo>>> {
    return axios.post(ApiConstant.DEPARTMENT_GET_ALL)
  }
}

export {
  DepartmentApi
}
