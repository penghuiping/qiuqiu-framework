class AuditLogVo {
  constructor (id, username, uri, params, createTime) {
    this.id = id
    this.username = username
    this.uri = uri
    this.params = params
    this.createTime = createTime
  }

  static newInstant () {
    return new AuditLogVo(-1, '', '', '', '')
  }
}
export { AuditLogVo }
// # sourceMappingURL=AuditLog.js.map
