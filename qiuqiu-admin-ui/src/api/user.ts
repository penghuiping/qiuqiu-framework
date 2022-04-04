import axios, { AxiosResponse } from 'axios'
import { JsonResponse, PageVo } from '@/api/vo'
import { ApiConstant } from '@/api/index'
import { TokenVo, UserCreateVo, UserDetailVo, UserListVo, UserUpdateVo } from '@/api/vo/user'
import { DictVo } from '@/api/vo/dict'

class UserApi {
  public static login (username: string, password: string, code: string, imgCodeId: string): Promise<AxiosResponse<JsonResponse<TokenVo>>> {
    return axios.post(ApiConstant.LOGIN, {
      username: username,
      password: password,
      code: code,
      imgCodeId: imgCodeId
    })
  }

  public static logout (): Promise<AxiosResponse<JsonResponse<boolean>>> {
    return axios.post(ApiConstant.LOGOUT)
  }

  public static info (): Promise<AxiosResponse<JsonResponse<UserDetailVo>>> {
    return axios.post(ApiConstant.USER_INFO)
  }

  public static getInitConfig (): Promise<AxiosResponse<JsonResponse<DictVo>>> {
    return axios.post(ApiConstant.GET_INIT_CONFIG)
  }

  public static detail (userId: number): Promise<AxiosResponse<JsonResponse<UserDetailVo>>> {
    return axios.post(ApiConstant.USER_DETAIL, {
      userId: userId
    })
  }

  public static page (username: string, pageNum: number, pageSize: number): Promise<AxiosResponse<JsonResponse<PageVo<UserListVo>>>> {
    return axios.post(ApiConstant.USER_PAGE, {
      username: username,
      pageNum: pageNum,
      pageSize: pageSize
    })
  }

  public static delete (id: number): Promise<AxiosResponse<JsonResponse<boolean>>> {
    return axios.post(ApiConstant.USER_DELETE, {
      userIds: [id]
    })
  }

  public static create (userCreateVo: UserCreateVo): Promise<AxiosResponse<JsonResponse<boolean>>> {
    return axios.post(ApiConstant.USER_CREATE, userCreateVo)
  }

  public static update (userUpdateVo: UserUpdateVo): Promise<AxiosResponse<JsonResponse<boolean>>> {
    return axios.post(ApiConstant.USER_UPDATE, userUpdateVo)
  }
}

export { UserApi }
