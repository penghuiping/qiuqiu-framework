class DictVo {
  constructor (id, key, value, description, enable) {
    this.id = id
    this.key = key
    this.value = value
    this.description = description
    this.enable = enable
  }

  static newInstant () {
    return new DictVo(-1, '', '', '', false)
  }
}
class DictCreateVo {
  constructor (key, value, description) {
    this.key = key
    this.value = value
    this.description = description
  }

  static newInstant () {
    return new DictCreateVo('', '', '')
  }
}
export { DictVo, DictCreateVo }
// # sourceMappingURL=dict.js.map
