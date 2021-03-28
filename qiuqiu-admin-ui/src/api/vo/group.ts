class GroupCreateVo {
   parentId: number
   name: string
   description: string

   static newInstant (): GroupCreateVo {
     return new GroupCreateVo(-1, '', '')
   }

   constructor (parentId: number, name: string, description: string) {
     this.parentId = parentId
     this.name = name
     this.description = description
   }
}
class GroupUpdateVo {
  id: number
  name: string
  description: string

  static newInstant (): GroupUpdateVo {
    return new GroupUpdateVo(-1, '', '')
  }

  constructor (id: number, name: string, description: string) {
    this.id = id
    this.name = name
    this.description = description
  }
}

export {
  GroupCreateVo,
  GroupUpdateVo
}
