class JsonResponse {
  constructor (code, data, message) {
    this.code = code
    this.data = data
    this.message = message
  }
}
class PageVo {
  constructor (total, currentPage, data) {
    this.total = total
    this.currentPage = currentPage
    this.data = data
  }
}
class ElementUiTreeVo {
  constructor (id, value, label, children) {
    this.disabled = true
    this.id = id
    this.value = value
    this.label = label
    this.children = children
  }
}
export { JsonResponse, ElementUiTreeVo, PageVo }
// # sourceMappingURL=index.js.map
