import { Vue } from 'vue-property-decorator'
import { Loading } from 'element-ui'
import { ElLoadingComponent } from 'element-ui/types/loading'
import { Permission } from '@/permission'

class BaseVue extends Vue {
  private permissions = Permission.permissions

  // 页面跳转
  routePage (path: string) {
    path = path.endsWith('/') ? path.substr(0, path.length - 1) : path
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

  permissionExists (permission: string): boolean {
    return Permission.exists(permission)
  }
}

export { BaseVue }
