class RoleCreateVo {
  constructor (name, description, permissionIds) {
    this.name = name
    this.description = description
    this.permissionIds = permissionIds
  }

  static newInstant () {
    return new RoleCreateVo('', '', [])
  }
}
class RoleVo {
  constructor (id, name, description) {
    this.id = id
    this.name = name
    this.description = description
  }

  static newInstant () {
    return new RoleVo(-1, '', '')
  }
}
class RoleListVo {
  constructor (id, name, description, enable) {
    this.id = id
    this.name = name
    this.description = description
    this.enable = enable
  }

  static newInstant () {
    return new RoleListVo(-1, '', '', false)
  }
}
class RoleUpdateVo {
  constructor (id, name, description, permissionIds, enable) {
    this.id = id
    this.name = name
    this.description = description
    this.permissionIds = permissionIds
    this.enable = enable
  }

  static newInstant () {
    return new RoleUpdateVo(-1, '', '', [], false)
  }
}
class RoleDetailVo {
  constructor (id, name, description, permissions, permissionIds, enable) {
    this.id = id
    this.name = name
    this.description = description
    this.permissions = permissions
    this.permissionIds = permissionIds
    this.enable = enable
  }

  static newInstant () {
    return new RoleDetailVo(-1, '', '', [], [], false)
  }
}
export { RoleVo, RoleListVo, RoleDetailVo, RoleUpdateVo, RoleCreateVo }
// # sourceMappingURL=role.js.map
