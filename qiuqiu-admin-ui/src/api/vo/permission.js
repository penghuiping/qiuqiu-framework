class PermissionVo {
  constructor (id, name, uri, description, enable) {
    this.id = id
    this.name = name
    this.uri = uri
    this.description = description
    this.enable = enable
  }

  static newInstant () {
    return new PermissionVo(-1, '', '', '', false)
  }
}
class PermissionUpdateVo {
  constructor (id, name, description, uri, enable) {
    this.id = id
    this.name = name
    this.description = description
    this.uri = uri
    this.enable = enable
  }

  static newInstant () {
    return new PermissionUpdateVo(-1, '', '', '', false)
  }
}
class PermissionCreateVo {
  constructor (name, description, uri) {
    this.name = name
    this.description = description
    this.uri = uri
  }

  static newInstant () {
    return new PermissionCreateVo('', '', '')
  }
}
export { PermissionVo, PermissionUpdateVo, PermissionCreateVo }
// # sourceMappingURL=permission.js.map
