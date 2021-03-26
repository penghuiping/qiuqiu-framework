import axios, { AxiosResponse } from 'axios'
import { JsonResponse } from '@/api/vo'
import { ApiConstant } from '@/api/index'
import { ResourceCreateVo, ResourceUpdateVo, ResourceVo } from '@/api/vo/resouce'

class ResourceApi {
  public static create (resource: ResourceCreateVo): Promise<AxiosResponse<JsonResponse<boolean>>> {
    return axios.post(ApiConstant.RESOURCE_CREATE, resource)
  }

  public static update (resource: ResourceUpdateVo): Promise<AxiosResponse<JsonResponse<boolean>>> {
    return axios.post(ApiConstant.RESOURCE_UPDATE, resource)
  }

  public static delete (name: string): Promise<AxiosResponse<JsonResponse<boolean>>> {
    return axios.post(ApiConstant.RESOURCE_DELETE, {
      resources: [name]
    })
  }

  public static page (): Promise<AxiosResponse<JsonResponse<ResourceVo[]>>> {
    return axios.post(ApiConstant.RESOURCE_PAGE)
  }
}

export {
  ResourceApi
}
