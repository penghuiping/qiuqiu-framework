import { ResourcePermissionsVo } from '@/api/vo/resouce'

class RoleCreateVo {
  name: string
  description: string
  resourcePermissions: ResourcePermissionsVo[]

  constructor (name: string, description: string, resourcePermissions: ResourcePermissionsVo[]) {
    this.name = name
    this.description = description
    this.resourcePermissions = resourcePermissions
  }

  static newInstant (): RoleCreateVo {
    return new RoleCreateVo('', '', [])
  }
}

class RoleVo {
  id: number
  name: string
  description: string

  constructor (id: number,
    name: string,
    description: string) {
    this.id = id
    this.name = name
    this.description = description
  }

  static newInstant (): RoleVo {
    return new RoleVo(-1, '', '')
  }
}

class RoleListVo {
  id: number
  name: string
  description: string
  enable: boolean

  constructor (id: number,
    name: string,
    description: string,
    enable: boolean) {
    this.id = id
    this.name = name
    this.description = description
    this.enable = enable
  }

  static newInstant (): RoleListVo {
    return new RoleListVo(-1, '', '', false)
  }
}

class RoleUpdateVo {
  id: number
  name: string
  description: string
  resourcePermissions: ResourcePermissionsVo[]
  enable: boolean

  constructor (id: number, name: string, description: string, resourcePermissions: ResourcePermissionsVo[], enable: boolean) {
    this.id = id
    this.name = name
    this.description = description
    this.resourcePermissions = resourcePermissions
    this.enable = enable
  }

  static newInstant (): RoleUpdateVo {
    return new RoleUpdateVo(-1, '', '', [], false)
  }
}

class RoleDetailVo {
  id: number
  name: string
  description: string
  resourcePermissions: ResourcePermissionsVo[]
  enable: boolean

  constructor (id: number, name: string, description: string, resourcePermissions: ResourcePermissionsVo[], enable: boolean) {
    this.id = id
    this.name = name
    this.description = description
    this.resourcePermissions = resourcePermissions
    this.enable = enable
  }

  static newInstant (): RoleDetailVo {
    return new RoleDetailVo(-1, '', '', [], false)
  }
}

export {
  RoleVo,
  RoleListVo,
  RoleDetailVo,
  RoleUpdateVo,
  RoleCreateVo
}
