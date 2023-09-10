class AuditLogVo {
  id: number
  username: string
  groupName: string
  uri: string
  params: string
  createTime: string

  constructor (id: number, username: string, groupName: string, uri: string, params: string, createTime: string) {
    this.id = id
    this.username = username
    this.groupName = groupName
    this.uri = uri
    this.params = params
    this.createTime = createTime
  }

  static newInstant (): AuditLogVo {
    return new AuditLogVo(-1, '', '', '', '', '')
  }
}

export {
  AuditLogVo
}
