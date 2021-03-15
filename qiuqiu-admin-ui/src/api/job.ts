import axios, { AxiosResponse } from 'axios'
import { JsonResponse, PageVo } from '@/api/vo'
import { ApiConstant } from '@/api/index'
import { JobCreateVo, JobVo } from '@/api/vo/job'

class JobApi {
  public static page (key: string, pageNum: number, pageSize: number): Promise<AxiosResponse<JsonResponse<PageVo<JobVo>>>> {
    return axios.post(ApiConstant.JOB_PAGE, {
      jobName: key,
      pageNum: pageNum,
      pageSize: pageSize
    })
  }

  public static create (job: JobCreateVo): Promise<AxiosResponse<JsonResponse<boolean>>> {
    return axios.post(ApiConstant.JOB_CREATE, job)
  }

  public static update (job: JobVo): Promise<AxiosResponse<JsonResponse<boolean>>> {
    return axios.post(ApiConstant.JOB_UPDATE, job)
  }

  public static delete (id: string): Promise<AxiosResponse<JsonResponse<boolean>>> {
    return axios.post(ApiConstant.JOB_DELETE, {
      jobId: id
    })
  }

  public static refresh (id: string): Promise<AxiosResponse<JsonResponse<boolean>>> {
    return axios.post(ApiConstant.JOB_REFRESH, {
      jobId: id
    })
  }
}

export {
  JobApi
}
