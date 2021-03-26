class ResourceVo {
  constructor (name, description, enable) {
    this.name = name
    this.description = description
    this.enable = enable
  }

  static newInstant () {
    return new ResourceVo('', '', false)
  }
}
class ResourcePermissionVo {
  constructor (permission, uri) {
    this.permission = permission
    this.uri = uri
  }

  static newInstant () {
    return new ResourcePermissionVo('', '')
  }
}
class ResourceCreateVo {
  constructor (name, description, resourcePermissions) {
    this.name = name
    this.description = description
    this.resourcePermissions = resourcePermissions
  }

  static newInstant () {
    return new ResourceCreateVo('', '', [])
  }
}
class ResourceUpdateVo {
  constructor (name, description, resourcePermissions, enable) {
    this.name = name
    this.description = description
    this.resourcePermissions = resourcePermissions
    this.enable = enable
  }

  static newInstant () {
    return new ResourceCreateVo('', '', [], false)
  }
}
export { ResourceVo, ResourcePermissionVo, ResourceCreateVo }
// # sourceMappingURL=resouce.js.map
