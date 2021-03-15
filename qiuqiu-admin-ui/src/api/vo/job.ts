class JobVo {
  id: string
  name: string
  description: string
  className: string
  cron: string
  enable: boolean

  constructor (id: string, name: string, description: string, className: string, cron: string, enable: boolean) {
    this.id = id
    this.name = name
    this.description = description
    this.className = className
    this.cron = cron
    this.enable = enable
  }

  static newInstant (): JobVo {
    return new JobVo('', '', '', '', '', false)
  }
}

class JobCreateVo {
  className: string
  name: string
  description: string
  cron: string

  constructor (className: string, name: string, description: string, cron: string) {
    this.className = className
    this.name = name
    this.description = description
    this.cron = cron
  }

  static newInstant (): JobCreateVo {
    return new JobCreateVo('', '', '', '')
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

export {
  JobVo,
  JobCreateVo,
  JobLogVo
}
