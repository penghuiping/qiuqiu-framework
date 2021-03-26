import { Vue, Watch } from 'vue-property-decorator'
import { Loading } from 'element-ui'
import { ElLoadingComponent } from 'element-ui/types/loading'
import { Permission } from '@/permission'
import Component from 'vue-class-component'

@Component
class BaseVue extends Vue {
  private permissions = Permission.permissions
  private resources = Permission.resources

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

  @Watch('token', {
    immediate: true
  })
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  onChildChanged (val: string, oldVal: string) {
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
  routePage (path: string) {
    if (path.length > 1) {
      path = path.endsWith('/') ? path.substr(0, path.length - 1) : path
    }
    if (this.$router.currentRoute.path !== path) {
      this.$router.push(path)
    }
  }

  // 显示加载
  showLoading (): ElLoadingComponent {
    return Loading.service({
      text: '加载中...',
      spinner: 'el-icon-loading',
      background: 'rgba(0, 0, 0, 0.7)'
    })
  }

  // 关闭加载
  closeLoading (loading: ElLoadingComponent) {
    loading.close()
  }

  permissionExists (resource: string, permission: string): boolean {
    return Permission.exists(resource, permission)
  }
}

export { BaseVue }
