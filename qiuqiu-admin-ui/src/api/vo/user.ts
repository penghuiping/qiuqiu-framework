import { ResourcePermissionsVo } from '@/api/vo/resouce'

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

  static newInstant (): UserCreateVo {
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
  enable: boolean

  constructor (
    id: number,
    username: string,
    nickname: string,
    password: string,
    groupId: number,
    roleIds: number[],
    enable: boolean) {
    this.id = id
    this.username = username
    this.nickname = nickname
    this.password = password
    this.groupId = groupId
    this.roleIds = roleIds
    this.enable = enable
  }

  static newInstant (): UserUpdateVo {
    return new UserUpdateVo(-1, '', '', '', -1, [], false)
  }
}

class UserListVo {
  id: number
  username: string
  nickname: string
  createTime: string
  lastModifiedTime: string
  enable: boolean

  constructor (
    id: number,
    username: string,
    nickname: string,
    createTime: string,
    lastModifiedTime: string,
    enable: boolean) {
    this.id = id
    this.username = username
    this.nickname = nickname
    this.createTime = createTime
    this.lastModifiedTime = lastModifiedTime
    this.enable = enable
  }

  static newInstant (): UserListVo {
    return new UserListVo(-1, '', '', '', '', false)
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
  enable: boolean
  resourcePermissions: ResourcePermissionsVo[]

  constructor (
    id: number,
    username: string,
    nickname: string,
    groupId: number,
    groupName: string,
    roles: string[],
    roleIds: number[],
    createTime: string,
    lastModifiedTime: string,
    enable: boolean,
    resourcePermissions: ResourcePermissionsVo[]) {
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
    this.resourcePermissions = resourcePermissions
  }

  static newInstant (): UserDetailVo {
    return new UserDetailVo(-1, '', '', -1, '', [], [], '', '', false, [])
  }
}

class GroupVo {
  id: number
  name: string
  description: string
  parentId: number

  constructor (id: number,
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
  GroupVo,
  UserUpdateVo
}
