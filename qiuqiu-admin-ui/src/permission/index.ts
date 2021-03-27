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
    RESOURCE: 'resource',
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
    ADD: 'create',
    DETAIL: 'detail',
    GET_ALL: 'get_all',
    UPDATE: 'update',
    DELETE: 'delete',
    PAGE: 'page',
    REFRESH: 'refresh',
    REFRESH_ALL: 'refresh_all',
    STATISTIC: 'statistic'
  }

  static translateMap = new Map<string, string>([
    [Permission.resources.USER, '用户管理'],
    [Permission.resources.ROLE, '角色管理'],
    [Permission.resources.PERMISSION, '权限管理'],
    [Permission.resources.RESOURCE, '资源管理'],
    [Permission.resources.GROUP, '用户组管理'],
    [Permission.resources.DICT, '数据字典'],
    [Permission.resources.AUDIT_LOG, '审计日志'],
    [Permission.resources.JOB, '定时任务'],
    [Permission.resources.JOB_EXECUTION, '执行计划'],
    [Permission.resources.JOB_LOG, '执行日志'],
    [Permission.permissions.ADD, '新增'],
    [Permission.permissions.DETAIL, '详情'],
    [Permission.permissions.GET_ALL, '获取所有'],
    [Permission.permissions.UPDATE, '更新'],
    [Permission.permissions.DELETE, '删除'],
    [Permission.permissions.PAGE, '分页'],
    [Permission.permissions.REFRESH, '刷新'],
    [Permission.permissions.REFRESH_ALL, '全部刷新'],
    [Permission.permissions.STATISTIC, '统计']
  ])

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

  public static has (permission: string, permissions: string[]): boolean {
    const set = new Set<string>(permissions)
    return set.has(permission)
  }

  public static translate (key: string): string {
    const res = Permission.translateMap.get(key)
    if (res) {
      return res
    }
    return ''
  }
}

export { Permission }
