class PermissionVo {
  name: string
  description: string
  enable: boolean

  constructor (name: string, description: string, enable: boolean) {
    this.name = name
    this.description = description
    this.enable = enable
  }

  static newInstant (): PermissionVo {
    return new PermissionVo('', '', false)
  }
}

class PermissionUpdateVo {
  name: string
  description: string
  enable: boolean

  constructor (name: string, description: string, enable: boolean) {
    this.name = name
    this.description = description
    this.enable = enable
  }

  static newInstant (): PermissionUpdateVo {
    return new PermissionUpdateVo('', '', false)
  }
}

class PermissionCreateVo {
  name: string
  description: string

  constructor (name: string, description: string) {
    this.name = name
    this.description = description
  }

  static newInstant (): PermissionCreateVo {
    return new PermissionCreateVo('', '')
  }
}

export {
  PermissionVo,
  PermissionUpdateVo,
  PermissionCreateVo
}
