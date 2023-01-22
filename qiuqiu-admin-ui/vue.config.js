module.exports = {
  configureWebpack: {
    plugins: []
  },
  devServer: {
    // proxy: 'http://localhost:8081/qiuqiu_admin/'
    proxy: {
      '/qiuqiu_admin': { // 匹配访问路径中含有 '/api' 的路径
        target: 'http://localhost:8081/qiuqiu_admin/', // 测试地址、目标地址
        changeOrigin: true,
        ws: true, // 是否开启 webSocket 代理
        pathRewrite: { // 请求路径重写
          '^/qiuqiu_admin': '' // 重写请求路径
        }
      }
    }
  }
}
