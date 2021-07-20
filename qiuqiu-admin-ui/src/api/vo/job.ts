class JobVo {
  id: string
  name: string
  description: string
  className: string

  constructor (id: string, name: string, description: string, className: string) {
    this.id = id
    this.name = name
    this.description = description
    this.className = className
  }

  static newInstant (): JobVo {
    return new JobVo('', '', '', '')
  }
}

class JobCreateVo {
  className: string
  name: string
  description: string

  constructor (className: string, name: string, description: string) {
    this.className = className
    this.name = name
    this.description = description
  }

  static newInstant (): JobCreateVo {
    return new JobCreateVo('', '', '')
  }
}

class JobLogVo {
  id: number
  jobName: string
  executeTime: string
  resultCode: number
  resultMessage: string

  constructor (id: number, jobName: string, executeTime: string, resultCode: number, resultMessage: string) {
    this.id = id
    this.jobName = jobName
    this.executeTime = executeTime
    this.resultCode = resultCode
    this.resultMessage = resultMessage
  }

  static newInstant (): JobLogVo {
    return new JobLogVo(-1, '', '', -1, '')
  }
}

class JobExecutionVo {
  id: string
  cron: string
  jobId: string
  jobName: string
  params: string
  timerLoadedNumber: number
  enable: boolean

  constructor (id: string, cron: string, jobId: string, jobName: string,
    params: string,
    timerLoadedNumber: number, enable: boolean) {
    this.id = id
    this.cron = cron
    this.jobId = jobId
    this.jobName = jobName
    this.params = params
    this.timerLoadedNumber = timerLoadedNumber
    this.enable = enable
  }

  static newInstant (): JobExecutionVo {
    return new JobExecutionVo('', '', '', '', '', 0, false)
  }
}

class JobExecutionCreateVo {
  cron: string
  jobId: string
  params: string

  constructor (cron: string, jobId: string, params: string) {
    this.cron = cron
    this.jobId = jobId
    this.params = params
  }

  static newInstant (): JobExecutionCreateVo {
    return new JobExecutionCreateVo('', '', '')
  }
}

class JobExecutionUpdateVo {
  id: string
  cron: string
  jobId: string
  params: string
  enable: boolean

  constructor (id: string, cron: string, jobId: string, params: string, enable: boolean) {
    this.id = id
    this.cron = cron
    this.jobId = jobId
    this.params = params
    this.enable = enable
  }

  static newInstant (): JobExecutionUpdateVo {
    return new JobExecutionUpdateVo('', '', '', '', false)
  }
}

export {
  JobVo,
  JobCreateVo,
  JobLogVo,
  JobExecutionVo,
  JobExecutionCreateVo,
  JobExecutionUpdateVo
}
