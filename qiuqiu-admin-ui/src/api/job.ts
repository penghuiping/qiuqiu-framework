import axios, { AxiosResponse } from 'axios'
import { JsonResponse, PageVo } from '@/api/vo'
import { ApiConstant } from '@/api/index'
import { JobCreateVo, JobExecutionCreateVo, JobExecutionUpdateVo, JobExecutionVo, JobLogVo, JobVo } from '@/api/vo/job'

class JobApi {
  public static page (key: string, pageNum: number, pageSize: number): Promise<AxiosResponse<JsonResponse<PageVo<JobVo>>>> {
    return axios.post(ApiConstant.JOB_PAGE, {
      jobName: key,
      pageNum: pageNum,
      pageSize: pageSize
    })
  }

  public static findAll (): Promise<AxiosResponse<JsonResponse<JobVo[]>>> {
    return axios.post(ApiConstant.JOB_find_all)
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

  public static logPage (key: string, pageNum: number, pageSize: number): Promise<AxiosResponse<JsonResponse<PageVo<JobLogVo>>>> {
    return axios.post(ApiConstant.JOB_LOG_PAGE, {
      jobName: key,
      pageNum: pageNum,
      pageSize: pageSize
    })
  }

  public static executionPage (key: string, pageNum: number, pageSize: number): Promise<AxiosResponse<JsonResponse<PageVo<JobExecutionVo>>>> {
    return axios.post(ApiConstant.JOB_EXECUTION_PAGE, {
      jobName: key,
      pageNum: pageNum,
      pageSize: pageSize
    })
  }

  public static executionCreate (execution: JobExecutionCreateVo): Promise<AxiosResponse<JsonResponse<boolean>>> {
    return axios.post(ApiConstant.JOB_EXECUTION_CREATE, execution)
  }

  public static executionUpdate (execution: JobExecutionUpdateVo): Promise<AxiosResponse<JsonResponse<boolean>>> {
    return axios.post(ApiConstant.JOB_EXECUTION_UPDATE, execution)
  }

  public static executionDelete (id: string): Promise<AxiosResponse<JsonResponse<boolean>>> {
    return axios.post(ApiConstant.JOB_EXECUTION_DELETE, {
      id: id
    })
  }

  public static refresh (id: string): Promise<AxiosResponse<JsonResponse<boolean>>> {
    return axios.post(ApiConstant.JOB_EXECUTION_REFRESH, {
      jobId: id
    })
  }

  public static refreshAll (): Promise<AxiosResponse<JsonResponse<boolean>>> {
    return axios.post(ApiConstant.JOB_EXECUTION_REFRESH_ALL)
  }
}

export {
  JobApi
}
