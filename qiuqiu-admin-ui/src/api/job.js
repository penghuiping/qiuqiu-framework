import axios from 'axios'
import { ApiConstant } from '@/api/index'
class JobApi {
  static page (key, pageNum, pageSize) {
    return axios.post(ApiConstant.JOB_PAGE, {
      jobName: key,
      pageNum: pageNum,
      pageSize: pageSize
    })
  }

  static findAll () {
    return axios.post(ApiConstant.JOB_find_all)
  }

  static create (job) {
    return axios.post(ApiConstant.JOB_CREATE, job)
  }

  static update (job) {
    return axios.post(ApiConstant.JOB_UPDATE, job)
  }

  static delete (id) {
    return axios.post(ApiConstant.JOB_DELETE, {
      jobId: id
    })
  }

  static logPage (key, pageNum, pageSize) {
    return axios.post(ApiConstant.JOB_LOG_PAGE, {
      jobName: key,
      pageNum: pageNum,
      pageSize: pageSize
    })
  }

  static executionPage (key, pageNum, pageSize) {
    return axios.post(ApiConstant.JOB_EXECUTION_PAGE, {
      jobName: key,
      pageNum: pageNum,
      pageSize: pageSize
    })
  }

  static executionCreate (execution) {
    return axios.post(ApiConstant.JOB_EXECUTION_CREATE, execution)
  }

  static executionUpdate (execution) {
    return axios.post(ApiConstant.JOB_EXECUTION_UPDATE, execution)
  }

  static executionDelete (id) {
    return axios.post(ApiConstant.JOB_EXECUTION_DELETE, {
      id: id
    })
  }

  static refresh (id) {
    return axios.post(ApiConstant.JOB_EXECUTION_REFRESH, {
      jobId: id
    })
  }

  static refreshAll () {
    return axios.post(ApiConstant.JOB_EXECUTION_REFRESH_ALL)
  }

  static statistic () {
    return axios.post(ApiConstant.JOB_EXECUTION_STATISTIC)
  }
}
export { JobApi }
// # sourceMappingURL=job.js.map
