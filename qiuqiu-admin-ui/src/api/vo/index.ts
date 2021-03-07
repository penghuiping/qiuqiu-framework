class JsonResponse<T> {
  code: string
  data: T
  message: string

  constructor (code: string, data: T, message: string) {
    this.code = code
    this.data = data
    this.message = message
  }
}

class PageVo<T> {
  total: number
  currentPage: number
  data: T[]

  constructor (total: number, currentPage: number, data: T[]) {
    this.total = total
    this.currentPage = currentPage
    this.data = data
  }
}

class ElementUiTreeVo {
  id: string
  value: string
  label: string
  children: ElementUiTreeVo[]
  disabled = true

  constructor (id: string, value: string, label: string, children: ElementUiTreeVo[]) {
    this.id = id
    this.value = value
    this.label = label
    this.children = children
  }
}

class TokenVo {
  expireTime: string
  token: string

  constructor (expireTime: string, token: string) {
    this.expireTime = expireTime
    this.token = token
  }
}

class UserCreateVo {
  username: string
  nickname: string
  password: string
  groupId: number
  roleIds: number[]

  constructor (username: string, nickname: string, password: string, groupId: number, roleIds: number[]) {
    this.username = username
    this.nickname = nickname
    this.password = password
    this.groupId = groupId
    this.roleIds = roleIds
  }
}

class UserListVo {
  id: string
  username: string
  nickname: string
  createTime: string
  lastModifiedTime: string
  enable: string

  constructor (id: string,
    username: string,
    nickname: string,
    createTime: string,
    lastModifiedTime: string,
    enable: string) {
    this.id = id
    this.username = username
    this.nickname = nickname
    this.createTime = createTime
    this.lastModifiedTime = lastModifiedTime
    this.enable = enable
  }
}

class UserDetailVo {
  id: string
  username: string
  nickname: string
  groupName: string
  roles: string[]
  createTime: string
  lastModifiedTime: string
  enable: string
  permissions: string[]

  constructor (id: string,
    username: string,
    nickname: string,
    groupName: string,
    roles: string[],
    createTime: string,
    lastModifiedTime: string,
    enable: string,
    permissions: string[]) {
    this.id = id
    this.username = username
    this.nickname = nickname
    this.groupName = groupName
    this.roles = roles
    this.createTime = createTime
    this.lastModifiedTime = lastModifiedTime
    this.enable = enable
    this.permissions = permissions
  }
}

class RoleVo {
  id: string
  name: string
  description: string

  constructor (id: string,
    name: string,
    description: string) {
    this.id = id
    this.name = name
    this.description = description
  }
}

class RoleListVo {
  id: string
  name: string
  description: string
  createTime: string
  lastModifiedTime: string
  enable: string

  constructor (id: string,
    name: string,
    description: string,
    createTime: string,
    lastModifiedTime: string,
    enable: string) {
    this.id = id
    this.name = name
    this.description = description
    this.createTime = createTime
    this.lastModifiedTime = lastModifiedTime
    this.enable = enable
  }
}

class RoleDetailVo {
  id: string
  name: string
  description: string
  permissions: PermissionVo[]
  createTime: string
  lastModifiedTime: string
  enable: string

  static newInstant (): RoleDetailVo {
    return new RoleDetailVo('', '', '', [], '', '', '')
  }

  constructor (id: string,
    name: string,
    description: string,
    permissions: PermissionVo[],
    createTime: string,
    lastModifiedTime: string,
    enable: string) {
    this.id = id
    this.name = name
    this.description = description
    this.permissions = permissions
    this.createTime = createTime
    this.lastModifiedTime = lastModifiedTime
    this.enable = enable
  }
}

class PermissionVo {
  id: string
  name: string
  description: string
  parentId: string

  constructor (id: string,
    name: string,
    description: string,
    parentId: string) {
    this.id = id
    this.name = name
    this.description = description
    this.parentId = parentId
  }
}

class GroupVo {
  id: string
  name: string
  description: string
  parentId: string

  constructor (id: string,
    name: string,
    description: string,
    parentId: string) {
    this.id = id
    this.name = name
    this.description = description
    this.parentId = parentId
  }
}

export {
  JsonResponse,
  ElementUiTreeVo,
  TokenVo,
  PageVo,
  UserListVo,
  UserDetailVo,
  UserCreateVo,
  RoleVo,
  RoleListVo,
  RoleDetailVo,
  PermissionVo,
  GroupVo
}
