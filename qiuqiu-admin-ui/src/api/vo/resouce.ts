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

class ResourceCreateVo {
  name: string
  description: string
  permissions: string[]

  constructor (name: string, description: string, permissions: string[]) {
    this.name = name
    this.description = description
    this.permissions = permissions
  }

  static newInstant (): ResourceCreateVo {
    return new ResourceCreateVo('', '', [])
  }
}

class ResourceUpdateVo {
  name: string
  description: string
  permissions: string[]
  enable: boolean

  constructor (name: string, description: string, permissions: string[], enable: boolean) {
    this.name = name
    this.description = description
    this.permissions = permissions
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
  ResourceCreateVo,
  ResourceUpdateVo,
  ResourcePermissionsVo
}
