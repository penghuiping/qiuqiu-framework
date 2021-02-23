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
    DEPARTMENT_LIST_SEARCH: 'department_list_search',
    MEDIA_LIST_SEARCH: 'media_list_search',
    REPORT_LIST_SEARCH: 'report_list_search'
  }

  // 用于把服务端的权限名称映射为本地定义的权限名称
  public static mapToLocalPermissions (remotePermissions: string[]) {
    const remoteToLocalPermission = new Map<string, string>()
    remoteToLocalPermission.set('_home', Permission.permissions.HOME)
    remoteToLocalPermission.set('_user_page_list', Permission.permissions.USER_LIST_SEARCH)
    remoteToLocalPermission.set('_user_add', Permission.permissions.USER_ADD)
    remoteToLocalPermission.set('_user_detail', Permission.permissions.USER_DETAIL)
    remoteToLocalPermission.set('_user_update', Permission.permissions.USER_UPDATE)
    remoteToLocalPermission.set('_user_delete', Permission.permissions.USER_DELETE)
    remoteToLocalPermission.set('_role_page_list', Permission.permissions.ROLE_LIST_SEARCH)
    remoteToLocalPermission.set('_role_add', Permission.permissions.ROLE_ADD)
    remoteToLocalPermission.set('_role_detail', Permission.permissions.ROLE_DETAIL)
    remoteToLocalPermission.set('_role_update', Permission.permissions.ROLE_UPDATE)
    remoteToLocalPermission.set('_role_delete', Permission.permissions.ROLE_DELETE)
    remoteToLocalPermission.set('_department_page_list', Permission.permissions.DEPARTMENT_LIST_SEARCH)
    remoteToLocalPermission.set('_media_page_list', Permission.permissions.MEDIA_LIST_SEARCH)
    remoteToLocalPermission.set('_report_page_list', Permission.permissions.REPORT_LIST_SEARCH)

    const userPermissions = new Set<string>()
    for (let i = 0; i < remotePermissions.length; i++) {
      const remotePermission = remotePermissions[i]
      const localPermission = remoteToLocalPermission.get(remotePermission)
      if (localPermission != null) {
        userPermissions.add(localPermission)
      }
    }
    store.commit('loginUserPermissions', userPermissions)
  }

  // 用于判断登入用户是否是有此权限功能
  public static exists (permission: string): boolean {
    const userPermissions = store.state.userPermissions
    return userPermissions.has(permission)
  }
}

export { Permission }
