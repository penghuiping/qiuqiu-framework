class DictVo {
  id: number
  key: string
  value: string
  description: string
  enable: boolean

  static newInstant (): DictVo {
    return new DictVo(-1, '', '', '', false)
  }

  constructor (id: number, key: string, value: string, description: string, enable: boolean) {
    this.id = id
    this.key = key
    this.value = value
    this.description = description
    this.enable = enable
  }
}

class DictCreateVo {
  key: string
  value: string
  description: string

  static newInstant (): DictCreateVo {
    return new DictCreateVo('', '', '')
  }

  constructor (key: string, value: string, description: string) {
    this.key = key
    this.value = value
    this.description = description
  }
}

export {
  DictVo,
  DictCreateVo
}
