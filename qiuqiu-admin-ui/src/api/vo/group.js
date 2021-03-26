class GroupCreateVo {
  constructor (parentId, name, description) {
    this.parentId = parentId
    this.name = name
    this.description = description
  }

  static newInstant () {
    return new GroupCreateVo(-1, '', '')
  }
}
export { GroupCreateVo }
// # sourceMappingURL=group.js.map
