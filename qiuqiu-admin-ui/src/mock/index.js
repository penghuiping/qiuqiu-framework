import Mock from 'better-mock'

Mock.setup({
  timeout: '200-600' // 表示响应时间介于 200 和 600 毫秒之间，默认值是'10-100'。
})

require('./user')
require('./role')
require('./department')
