module.exports = {
  configureWebpack: {
    plugins: []
  },
  devServer: {
    proxy: 'http://localhost:8081/qiuqiu_admin/'
  }
}
