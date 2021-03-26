import store from '@/store'
import { ResourcePermissionsVo } from '@/api/vo/resouce'

/**
 * 权限配置相关，如果有新的需求页面功能，都需要在此类中新增或者修改系统内置权限
 */
class Permission {
  // 以下为系统内置资源
  static resources = {
    HOME: 'home',
    USER: 'user',
    ROLE: 'role',
    PERMISSION: 'permission',
    GROUP: 'group',
    DICT: 'dict',
    AUDIT_LOG: 'audit_log',
    JOB: 'job',
    JOB_EXECUTION: 'job_execution',
    JOB_LOG: 'job_log',
    REPORT: 'report',
    MEDIA: 'media'
  }

  // 下为系统内置权限
  static permissions = {
    ADD: 'add',
    DETAIL: 'detail',
    UPDATE: 'update',
    DELETE: 'delete',
    PAGE: 'page',
    REFRESH: 'refresh',
    REFRESH_ALL: 'refresh_all',
    STATISTIC: 'statistic'
  }

  // 用于把服务端的权限名称映射为本地定义的权限名称
  public static mapToLocalPermissions (resourcePermissionsList: ResourcePermissionsVo[]) {
    const userPermissions = new Map<string, Set<string>>()
    for (let i = 0; i < resourcePermissionsList.length; i++) {
      const resourcePermissions = resourcePermissionsList[i]
      userPermissions.set(resourcePermissions.resource, new Set<string>(resourcePermissions.permissions))
    }
    store.commit('loginUserPermissions', userPermissions)
  }

  // 用于判断登入用户是否是有此权限功能
  public static exists (resource: string, permission: string): boolean {
    const userPermissions = store.state.userPermissions
    const permission0s = userPermissions.get(resource)
    if (!permission0s) {
      return false
    }
    return permission0s.has(permission)
  }
}

export { Permission }
