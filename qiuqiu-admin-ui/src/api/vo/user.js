class TokenVo {
  constructor (expireTime, token) {
    this.expireTime = expireTime
    this.token = token
  }
}
class UserCreateVo {
  constructor (username, nickname, password, groupId, roleIds) {
    this.username = username
    this.nickname = nickname
    this.password = password
    this.groupId = groupId
    this.roleIds = roleIds
  }

  static newInstant () {
    return new UserCreateVo('', '', '', -1, [])
  }
}
class UserUpdateVo {
  constructor (id, username, nickname, password, groupId, roleIds, enable) {
    this.id = id
    this.username = username
    this.nickname = nickname
    this.password = password
    this.groupId = groupId
    this.roleIds = roleIds
    this.enable = enable
  }

  static newInstant () {
    return new UserUpdateVo(-1, '', '', '', -1, [], false)
  }
}
class UserListVo {
  constructor (id, username, nickname, createTime, lastModifiedTime, enable) {
    this.id = id
    this.username = username
    this.nickname = nickname
    this.createTime = createTime
    this.lastModifiedTime = lastModifiedTime
    this.enable = enable
  }

  static newInstant () {
    return new UserListVo(-1, '', '', '', '', false)
  }
}
class UserDetailVo {
  constructor (id, username, nickname, groupId, groupName, roles, roleIds, createTime, lastModifiedTime, enable, permissions) {
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

  static newInstant () {
    return new UserDetailVo(-1, '', '', -1, '', [], [], '', '', false, [])
  }
}
class GroupVo {
  constructor (id, name, description, parentId) {
    this.id = id
    this.name = name
    this.description = description
    this.parentId = parentId
  }
}
export { TokenVo, UserListVo, UserDetailVo, UserCreateVo, GroupVo, UserUpdateVo }
// # sourceMappingURL=user.js.map
