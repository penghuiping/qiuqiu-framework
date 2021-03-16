import store from '@/store'

/**
 * 权限配置相关，如果有新的需求页面功能，都需要在此类中新增或者修改系统内置权限
 */
class Permission {
  // 以下为系统内置权限功能
  static permissions = {
    HOME: 'home',
    USER_LIST_SEARCH: 'user_list_search',
    USER_ADD: 'user_add',
    USER_DETAIL: 'user_detail',
    USER_UPDATE: 'user_update',
    USER_DELETE: 'user_delete',
    ROLE_LIST_SEARCH: 'role_list_search',
    ROLE_ADD: 'role_add',
    ROLE_DETAIL: 'role_detail',
    ROLE_UPDATE: 'role_update',
    ROLE_DELETE: 'role_delete',
    PERMISSION_LIST_SEARCH: 'permission_list_search',
    PERMISSION_ADD: 'permission_add',
    PERMISSION_UPDATE: 'permission_update',
    PERMISSION_DELETE: 'permission_delete',
    GROUP_LIST_SEARCH: 'group_list_search',
    GROUP_ADD: 'group_add',
    GROUP_UPDATE: 'group_update',
    GROUP_DELETE: 'group_delete',
    AUDIT_LOG_LIST_SEARCH: 'audit_log_list_search',
    DICT_LIST_SEARCH: 'dict_list_search',
    DICT_ADD: 'dict_add',
    DICT_UPDATE: 'dict_update',
    DICT_DELETE: 'dict_delete',
    DICT_REFRESH: 'dict_refresh',
    JOB_LIST_SEARCH: 'job_list_search',
    JOB_ADD: 'job_add',
    JOB_UPDATE: 'job_update',
    JOB_DELETE: 'job_delete',
    JOB_EXECUTION_LIST_SEARCH: 'job_execution_list_search',
    JOB_EXECUTION_ADD: 'job_execution_add',
    JOB_EXECUTION_UPDATE: 'job_execution_update',
    JOB_EXECUTION_DELETE: 'job_execution_delete',
    JOB_EXECUTION_REFRESH: 'job_execution_refresh',
    JOB_EXECUTION_REFRESH_ALL: 'job_execution_refresh_all',
    JOB_LOG_LIST_SEARCH: 'job_log_page',
    MEDIA_LIST_SEARCH: 'media_list_search',
    REPORT_LIST_SEARCH: 'report_list_search'
  }

  // 用于把服务端的权限名称映射为本地定义的权限名称
  public static mapToLocalPermissions (remotePermissions: string[]) {
    const remoteToLocalPermission = new Map<string, string>()
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

    // remoteToLocalPermission.set('_department_page_list', Permission.permissions.DEPARTMENT_LIST_SEARCH)
    // remoteToLocalPermission.set('_media_page_list', Permission.permissions.MEDIA_LIST_SEARCH)
    // remoteToLocalPermission.set('_report_page_list', Permission.permissions.REPORT_LIST_SEARCH)

    const userPermissions = new Set<string>()
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
  public static exists (permission: string): boolean {
    const userPermissions = store.state.userPermissions
    return userPermissions.has(permission)
  }
}

export { Permission }
