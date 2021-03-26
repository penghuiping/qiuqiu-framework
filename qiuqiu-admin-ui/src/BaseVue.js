import { __decorate } from 'tslib'
import { Vue, Watch } from 'vue-property-decorator'
import { Loading } from 'element-ui'
import { Permission } from '@/permission'
import Component from 'vue-class-component'
let BaseVue = class BaseVue extends Vue {
  constructor () {
    super(...arguments)
    this.permissions = Permission.permissions
  }

  get token () {
    let token1 = this.$store.state.token
    if (token1) {
      return token1
    }
    token1 = sessionStorage.getItem('token')
    if (token1) {
      return token1
    }
    return ''
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  onChildChanged (val, oldVal) {
    if ((val === '' || val == null) && this.$router.currentRoute.path !== '/') {
      this.$confirm('由于您长时间没有操作,请重新登入', '提示', {
        confirmButtonText: '确定',
        type: 'warning'
      }).then(() => {
        this.routePage('/')
      }).catch(() => {
        this.routePage('/')
      })
    }
  }

  // 页面跳转
  routePage (path) {
    if (path.length > 1) {
      path = path.endsWith('/') ? path.substr(0, path.length - 1) : path
    }
    if (this.$router.currentRoute.path !== path) {
      this.$router.push(path)
    }
  }

  // 显示加载
  showLoading () {
    return Loading.service({
      text: '加载中...',
      spinner: 'el-icon-loading',
      background: 'rgba(0, 0, 0, 0.7)'
    })
  }

  // 关闭加载
  closeLoading (loading) {
    loading.close()
  }

  permissionExists (permission) {
    return Permission.exists(permission)
  }
}
__decorate([
  Watch('token', {
    immediate: true
  })
], BaseVue.prototype, 'onChildChanged', null)
BaseVue = __decorate([
  Component
], BaseVue)
export { BaseVue }
// # sourceMappingURL=BaseVue.js.map
