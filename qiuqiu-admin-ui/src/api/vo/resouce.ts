class ResourceVo {
  name: string
  description: string
  enable: boolean

  constructor (name: string, description: string, enable: boolean) {
    this.name = name
    this.description = description
    this.enable = enable
  }

  static newInstant (): ResourceVo {
    return new ResourceVo('', '', false)
  }
}

class ResourcePermissionVo {
  permission: string
  uri: string

  constructor (permission: string, uri: string) {
    this.permission = permission
    this.uri = uri
  }

  static newInstant (): ResourcePermissionVo {
    return new ResourcePermissionVo('', '')
  }
}

class ResourceCreateVo {
  name: string
  description: string
  resourcePermissions: ResourcePermissionVo[]

  constructor (name: string, description: string, resourcePermissions: ResourcePermissionVo[]) {
    this.name = name
    this.description = description
    this.resourcePermissions = resourcePermissions
  }

  static newInstant (): ResourceCreateVo {
    return new ResourceCreateVo('', '', [])
  }
}

class ResourceUpdateVo {
  name: string
  description: string
  resourcePermissions: ResourcePermissionVo[]
  enable: boolean

  constructor (name: string, description: string, resourcePermissions: ResourcePermissionVo[], enable: boolean) {
    this.name = name
    this.description = description
    this.resourcePermissions = resourcePermissions
    this.enable = enable
  }

  static newInstant (): ResourceUpdateVo {
    return new ResourceUpdateVo('', '', [], false)
  }
}

class ResourcePermissionsVo {
  resource: string
  permissions: string[]

  constructor (resource: string, permissions: string[]) {
    this.resource = resource
    this.permissions = permissions
  }
}

export {
  ResourceVo,
  ResourcePermissionVo,
  ResourceCreateVo,
  ResourceUpdateVo,
  ResourcePermissionsVo
}
