import store from '@/store'
/**
 * 权限配置相关，如果有新的需求页面功能，都需要在此类中新增或者修改系统内置权限
 */
class Permission {
  // 用于把服务端的权限名称映射为本地定义的权限名称
  static mapToLocalPermissions (remotePermissions) {
    const remoteToLocalPermission = new Map()
    remoteToLocalPermission.set('user_page', Permission.permissions.USER_LIST_SEARCH)
    remoteToLocalPermission.set('user_create', Permission.permissions.USER_ADD)
    remoteToLocalPermission.set('user_info', Permission.permissions.USER_DETAIL)
    remoteToLocalPermission.set('user_update', Permission.permissions.USER_UPDATE)
    remoteToLocalPermission.set('user_delete', Permission.permissions.USER_DELETE)
    remoteToLocalPermission.set('role_page', Permission.permissions.ROLE_LIST_SEARCH)
    remoteToLocalPermission.set('role_create', Permission.permissions.ROLE_ADD)
    remoteToLocalPermission.set('role_detail', Permission.permissions.ROLE_DETAIL)
    remoteToLocalPermission.set('role_update', Permission.permissions.ROLE_UPDATE)
    remoteToLocalPermission.set('role_delete', Permission.permissions.ROLE_DELETE)
    remoteToLocalPermission.set('permission_page', Permission.permissions.PERMISSION_LIST_SEARCH)
    remoteToLocalPermission.set('permission_create', Permission.permissions.PERMISSION_ADD)
    remoteToLocalPermission.set('permission_update', Permission.permissions.PERMISSION_UPDATE)
    remoteToLocalPermission.set('permission_delete', Permission.permissions.PERMISSION_DELETE)
    remoteToLocalPermission.set('group_page', Permission.permissions.GROUP_LIST_SEARCH)
    remoteToLocalPermission.set('group_create', Permission.permissions.GROUP_ADD)
    remoteToLocalPermission.set('group_update', Permission.permissions.GROUP_UPDATE)
    remoteToLocalPermission.set('group_delete', Permission.permissions.GROUP_DELETE)
    remoteToLocalPermission.set('audit_log_page', Permission.permissions.AUDIT_LOG_LIST_SEARCH)
    remoteToLocalPermission.set('dict_page', Permission.permissions.DICT_LIST_SEARCH)
    remoteToLocalPermission.set('dict_create', Permission.permissions.DICT_ADD)
    remoteToLocalPermission.set('dict_update', Permission.permissions.DICT_UPDATE)
    remoteToLocalPermission.set('dict_delete', Permission.permissions.DICT_DELETE)
    remoteToLocalPermission.set('dict_refresh', Permission.permissions.DICT_REFRESH)
    remoteToLocalPermission.set('job_page', Permission.permissions.JOB_LIST_SEARCH)
    remoteToLocalPermission.set('job_create', Permission.permissions.JOB_ADD)
    remoteToLocalPermission.set('job_update', Permission.permissions.JOB_UPDATE)
    remoteToLocalPermission.set('job_delete', Permission.permissions.JOB_DELETE)
    remoteToLocalPermission.set('job_log_page', Permission.permissions.JOB_LOG_LIST_SEARCH)
    remoteToLocalPermission.set('job_execution_page', Permission.permissions.JOB_EXECUTION_LIST_SEARCH)
    remoteToLocalPermission.set('job_execution_create', Permission.permissions.JOB_EXECUTION_ADD)
    remoteToLocalPermission.set('job_execution_update', Permission.permissions.JOB_EXECUTION_UPDATE)
    remoteToLocalPermission.set('job_execution_delete', Permission.permissions.JOB_EXECUTION_DELETE)
    remoteToLocalPermission.set('job_execution_refresh', Permission.permissions.JOB_EXECUTION_REFRESH)
    remoteToLocalPermission.set('job_execution_refresh_all', Permission.permissions.JOB_EXECUTION_REFRESH_ALL)
    remoteToLocalPermission.set('job_execution_statistic', Permission.permissions.JOB_EXECUTION_STATISTIC)
    // remoteToLocalPermission.set('_department_page_list', Permission.permissions.DEPARTMENT_LIST_SEARCH)
    // remoteToLocalPermission.set('_media_page_list', Permission.permissions.MEDIA_LIST_SEARCH)
    // remoteToLocalPermission.set('_report_page_list', Permission.permissions.REPORT_LIST_SEARCH)
    const userPermissions = new Set()
    for (let i = 0; i < remotePermissions.length; i++) {
      const remotePermission = remotePermissions[i]
      const localPermission = remoteToLocalPermission.get(remotePermission)
      if (localPermission != null) {
        userPermissions.add(localPermission)
      }
    }
    userPermissions.add(Permission.permissions.HOME)
    store.commit('loginUserPermissions', userPermissions)
  }

  // 用于判断登入用户是否是有此权限功能
  static exists (resource, permission) {
    const userPermissions = store.state.userPermissions
    return userPermissions.has(permission)
  }
}
// 以下为系统内置资源
Permission.resources = {
  HOME: 'home',
  USER: 'user',
  ROLE: 'role',
  PERMISSION: 'permission',
  GROUP: 'group',
  DICT: 'dict',
  AUDIT_LOG: 'audit_log',
  JOB: 'job',
  JOB_EXECUTION: 'job_execution',
  JOB_LOG: 'job_log'
}
// 下为系统内置权限
Permission.permissions = {
  ADD: 'add',
  DETAIL: 'detail',
  UPDATE: 'update',
  DELETE: 'delete',
  PAGE: 'page',
  REFRESH: 'refresh',
  REFRESH_ALL: 'refresh_all',
  STATISTIC: 'statistic'
}
export { Permission }
// # sourceMappingURL=index.js.map
