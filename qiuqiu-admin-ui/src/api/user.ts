import axios, { AxiosResponse } from 'axios'
import { JsonResponse, PageVo, TokenVo, UserDetailVo, UserListVo } from '@/api/vo'
import { ApiConstant } from '@/api/index'

class UserApi {
  public static login (username: string, password: string): Promise<AxiosResponse<JsonResponse<TokenVo>>> {
    return axios.post(ApiConstant.LOGIN, {
      username: username,
      password: password
    })
  }

  public static info (): Promise<AxiosResponse<JsonResponse<UserDetailVo>>> {
    return axios.post(ApiConstant.USER_INFO)
  }

  public static page (username: string, mobile: string, pageNum: number, pageSize: number): Promise<AxiosResponse<JsonResponse<PageVo<UserListVo>>>> {
    return axios.post(ApiConstant.USER_PAGE, {
      username: username,
      mobile: mobile,
      pageNum: pageNum,
      pageSize: pageSize
    })
  }

  public static delete (id: string): Promise<AxiosResponse<JsonResponse<boolean>>> {
    return axios.post(ApiConstant.USER_DELETE, {
      id: id
    })
  }
}

export { UserApi }