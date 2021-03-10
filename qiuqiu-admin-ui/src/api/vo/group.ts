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

export {
  GroupCreateVo
}
