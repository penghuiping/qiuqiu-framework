import axios, { AxiosResponse } from 'axios'
import { JsonResponse, PageVo } from '@/api/vo'
import { ApiConstant } from '@/api/index'
import { DictCreateVo, DictVo } from '@/api/vo/dict'

class DictApi {
  public static page (key: string, pageNum: number, pageSize: number): Promise<AxiosResponse<JsonResponse<PageVo<DictVo>>>> {
    return axios.post(ApiConstant.DICT_PAGE, {
      key: key,
      pageNum: pageNum,
      pageSize: pageSize
    })
  }

  public static create (dict: DictCreateVo): Promise<AxiosResponse<JsonResponse<boolean>>> {
    return axios.post(ApiConstant.DICT_CREATE, dict)
  }

  public static update (dict: DictVo): Promise<AxiosResponse<JsonResponse<boolean>>> {
    return axios.post(ApiConstant.DICT_UPDATE, dict)
  }

  public static delete (key: string): Promise<AxiosResponse<JsonResponse<boolean>>> {
    return axios.post(ApiConstant.DICT_DELETE, {
      key: key
    })
  }

  public static refresh (key: string): Promise<AxiosResponse<JsonResponse<boolean>>> {
    return axios.post(ApiConstant.DICT_REFRESH, {
      key: key
    })
  }
}

export {
  DictApi
}
