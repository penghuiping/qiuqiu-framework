class TokenVo {
  expireTime: string
  token: string

  constructor(expireTime: string, token: string) {
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

  constructor(username: string, nickname: string, password: string, groupId: number, roleIds: number[]) {
    this.username = username
    this.nickname = nickname
    this.password = password
    this.groupId = groupId
    this.roleIds = roleIds
  }

  static newInstant(): UserCreateVo {
    return new UserCreateVo('', '', '', -1, [])
  }
}

class UserUpdateVo {
  id: number
  username: string
  nickname: string
  password: string
  groupId: number
  roleIds: number[]
  enable: number

  constructor(
    id: number,
    username: string,
    nickname: string,
    password: string,
    groupId: number,
    roleIds: number[],
    enable: number) {
    this.id = id
    this.username = username
    this.nickname = nickname
    this.password = password
    this.groupId = groupId
    this.roleIds = roleIds
    this.enable = enable
  }

  static newInstant(): UserUpdateVo {
    return new UserUpdateVo(-1, '', '', '', -1, [], -1)
  }
}

class UserListVo {
  id: number
  username: string
  nickname: string
  createTime: string
  lastModifiedTime: string
  enable: number

  constructor(
    id: number,
    username: string,
    nickname: string,
    createTime: string,
    lastModifiedTime: string,
    enable: number) {
    this.id = id
    this.username = username
    this.nickname = nickname
    this.createTime = createTime
    this.lastModifiedTime = lastModifiedTime
    this.enable = enable
  }

  static newInstant(): UserListVo {
    return new UserListVo(-1, '', '', '', '', -1)
  }
}

class UserDetailVo {
  id: number
  username: string
  nickname: string
  groupId: number
  groupName: string
  roles: string[]
  roleIds: number[]
  createTime: string
  lastModifiedTime: string
  enable: number
  permissions: string[]

  constructor(
    id: number,
    username: string,
    nickname: string,
    groupId: number,
    groupName: string,
    roles: string[],
    roleIds: number[],
    createTime: string,
    lastModifiedTime: string,
    enable: number,
    permissions: string[]) {
    this.id = id
    this.username = username
    this.nickname = nickname
    this.groupId = groupId
    this.groupName = groupName
    this.roles = roles
    this.roleIds = roleIds
    this.createTime = createTime
    this.lastModifiedTime = lastModifiedTime
    this.enable = enable
    this.permissions = permissions
  }

  static newInstant(): UserDetailVo {
    return new UserDetailVo(-1, '', '', -1, '', [], [], '', '', -1, [])
  }
}

class RoleVo {
  id: number
  name: string
  description: string

  constructor(id: number,
              name: string,
              description: string) {
    this.id = id
    this.name = name
    this.description = description
  }

  static newInstant(): RoleVo {
    return new RoleVo(-1, '', '')
  }
}

class RoleListVo {
  id: number
  name: string
  description: string
  createTime: string
  lastModifiedTime: string
  enable: number

  constructor(id: number,
              name: string,
              description: string,
              createTime: string,
              lastModifiedTime: string,
              enable: number) {
    this.id = id
    this.name = name
    this.description = description
    this.createTime = createTime
    this.lastModifiedTime = lastModifiedTime
    this.enable = enable
  }

  static newInstant(): RoleListVo {
    return new RoleListVo(-1, '', '', '', '', -1)
  }
}

class RoleDetailVo {
  id: number
  name: string
  description: string
  permissions: PermissionVo[]
  createTime: string
  lastModifiedTime: string
  enable: number

  constructor(
    id: number,
    name: string,
    description: string,
    permissions: PermissionVo[],
    createTime: string,
    lastModifiedTime: string,
    enable: number) {
    this.id = id
    this.name = name
    this.description = description
    this.permissions = permissions
    this.createTime = createTime
    this.lastModifiedTime = lastModifiedTime
    this.enable = enable
  }

  static newInstant(): RoleDetailVo {
    return new RoleDetailVo(-1, '', '', [], '', '', -1)
  }
}

class PermissionVo {
  id: number
  name: string
  description: string

  constructor(id: number,
              name: string,
              description: string) {
    this.id = id
    this.name = name
    this.description = description
  }
}

class GroupVo {
  id: number
  name: string
  description: string
  parentId: number

  constructor(id: number,
              name: string,
              description: string,
              parentId: number) {
    this.id = id
    this.name = name
    this.description = description
    this.parentId = parentId
  }
}

export {
  TokenVo,
  UserListVo,
  UserDetailVo,
  UserCreateVo,
  RoleVo,
  RoleListVo,
  RoleDetailVo,
  PermissionVo,
  GroupVo,
  UserUpdateVo
}
