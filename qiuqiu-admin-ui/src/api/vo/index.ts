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

export {
  JsonResponse,
  ElementUiTreeVo,
  PageVo
}
