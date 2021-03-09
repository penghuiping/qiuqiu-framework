class PermissionVo {
  id: number
  name: string
  uri: string
  description: string
  enable: boolean

  constructor (id: number, name: string, uri: string, description: string, enable: boolean) {
    this.id = id
    this.name = name
    this.uri = uri
    this.description = description
    this.enable = enable
  }

  static newInstant (): PermissionVo {
    return new PermissionVo(-1, '', '', '', false)
  }
}

class PermissionUpdateVo {
  id: number
  name: string
  description: string
  uri: string
  enable: boolean

  constructor (id: number, name: string, description: string, uri: string, enable: boolean) {
    this.id = id
    this.name = name
    this.description = description
    this.uri = uri
    this.enable = enable
  }

  static newInstant (): PermissionUpdateVo {
    return new PermissionUpdateVo(-1, '', '', '', false)
  }
}

class PermissionCreateVo {
  name: string
  description: string
  uri: string

  constructor (name: string, description: string, uri: string) {
    this.name = name
    this.description = description
    this.uri = uri
  }

  static newInstant (): PermissionCreateVo {
    return new PermissionCreateVo('', '', '')
  }
}

export {
  PermissionVo,
  PermissionUpdateVo,
  PermissionCreateVo
}
