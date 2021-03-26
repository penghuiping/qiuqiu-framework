class JobVo {
  constructor (id, name, description, className) {
    this.id = id
    this.name = name
    this.description = description
    this.className = className
  }

  static newInstant () {
    return new JobVo('', '', '', '')
  }
}
class JobCreateVo {
  constructor (className, name, description) {
    this.className = className
    this.name = name
    this.description = description
  }

  static newInstant () {
    return new JobCreateVo('', '', '')
  }
}
class JobLogVo {
  constructor (id, jobName, executeTime, resultCode, resultMessage) {
    this.id = id
    this.jobName = jobName
    this.executeTime = executeTime
    this.resultCode = resultCode
    this.resultMessage = resultMessage
  }

  static newInstant () {
    return new JobLogVo(-1, '', '', -1, '')
  }
}
class JobExecutionVo {
  constructor (id, cron, jobId, jobName, timerLoadedNumber, enable) {
    this.id = id
    this.cron = cron
    this.jobId = jobId
    this.jobName = jobName
    this.timerLoadedNumber = timerLoadedNumber
    this.enable = enable
  }

  static newInstant () {
    return new JobExecutionVo('', '', '', '', 0, false)
  }
}
class JobExecutionCreateVo {
  constructor (cron, jobId) {
    this.cron = cron
    this.jobId = jobId
  }

  static newInstant () {
    return new JobExecutionCreateVo('', '')
  }
}
class JobExecutionUpdateVo {
  constructor (id, cron, jobId, enable) {
    this.id = id
    this.cron = cron
    this.jobId = jobId
    this.enable = enable
  }

  static newInstant () {
    return new JobExecutionUpdateVo('', '', '', false)
  }
}
export { JobVo, JobCreateVo, JobLogVo, JobExecutionVo, JobExecutionCreateVo, JobExecutionUpdateVo }
// # sourceMappingURL=job.js.map
