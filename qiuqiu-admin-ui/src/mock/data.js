/**
 * 权限相关mock数据
 */
import { GroupVo, UserListVo } from '@/api/vo/user'
import { RoleListVo } from '@/api/vo/role'
import { PermissionVo } from '@/api/vo/permission'
const home = new PermissionVo(1, '_home', '', '首页', false)
const permissionManagement = new PermissionVo(2, '_permission_management', '', '权限管理', false)
const mediaManagement = new PermissionVo(3, '_media_management', '', '媒体管理', false)
const reportManagement = new PermissionVo(4, '_report_management', '', '报表', false)
const userPageList = new PermissionVo(5, '_user_page_list', '', '用户列表', false)
const userDetail = new PermissionVo(6, '_user_detail', '', '用户详情', false)
const userAdd = new PermissionVo(7, '_user_add', '', '用户新增', false)
const userDelete = new PermissionVo(8, '_user_delete', '', '用户删除', false)
const userUpdate = new PermissionVo(9, '_user_update', '', '用户更新', false)
const rolePageList = new PermissionVo(10, '_role_page_list', '', '角色列表', false)
const roleDetail = new PermissionVo(11, '_role_detail', '', '角色详情', false)
const roleDelete = new PermissionVo(12, '_role_delete', '', '角色删除', false)
const roleAdd = new PermissionVo(13, '_role_add', '', '角色新增', false)
const roleUpdate = new PermissionVo(14, '_role_update', '', '角色更新', false)
const departmentPageList = new PermissionVo(15, '_department_page_list', '', '部门列表', false)
const mediaPageList = new PermissionVo(16, '_media_page_list', '', '媒体列表', false)
const reportPageList = new PermissionVo(17, '_report_page_list', '', '报表列表', false)
/**
 * 角色相关mock数据
 */
const adminRole = new RoleListVo(1, 'admin', '超级管理员', true)
const commonUserRole = new RoleListVo(2, 'common_user', '普通用户', true)
/**
 * 部门相关mock数据
 */
const rootDepartment = new GroupVo(1, '总部', '总部', 0)
const shangHaiDepartment = new GroupVo(2, '上海分部', '上海分部', 1)
const beiJinDepartment = new GroupVo(3, '北京分部', '北京分部', 1)
const minHangDepartment = new GroupVo(4, '上海闵行分部', '上海闵行分部', 2)
const xuHuiDepartment = new GroupVo(5, '上海徐汇分部', '上海徐汇分部', 2)
/**
 * 用户相关mock数据
 */
const admin = new UserListVo(1, 'Admin', '超级管理员', '2020-11-1 10:00:00', '2020-11-1 10:00:00', true)
const mary = new UserListVo(2, 'Mary', '玛丽', '2020-11-1 10:00:00', '2020-11-1 10:00:00', true)
const tom = new UserListVo(3, 'Tom', '汤姆', '2020-11-1 10:00:00', '2020-11-1 10:00:00', true)
const ted = new UserListVo(4, 'Ted', '泰德', '2020-11-1 10:00:00', '2020-11-1 10:00:00', true)
const jack = new UserListVo(5, 'Jack', '杰克', '2020-11-1 10:00:00', '2020-11-1 10:00:00', true)
const alice = new UserListVo(6, 'Alice', '爱丽丝', '2020-11-1 10:00:00', '2020-11-1 10:00:00', true)
class MockData {
}
MockData.permissions = {
  home: home,
  permissionManagement: permissionManagement,
  mediaManagement: mediaManagement,
  reportManagement: reportManagement,
  userPageList: userPageList,
  userDetail: userDetail,
  userAdd: userAdd,
  userDelete: userDelete,
  userUpdate: userUpdate,
  rolePageList: rolePageList,
  roleDetail: roleDetail,
  roleDelete: roleDelete,
  roleAdd: roleAdd,
  roleUpdate: roleUpdate,
  departmentPageList: departmentPageList,
  mediaPageList: mediaPageList,
  reportPageList: reportPageList
}
MockData.users = {
  admin: admin,
  mary: mary,
  tom: tom,
  ted: ted,
  jack: jack,
  alice: alice
}
MockData.roles = {
  adminRole: adminRole,
  commonUserRole: commonUserRole
}
MockData.departments = {
  rootDepartment: rootDepartment,
  shangHaiDepartment: shangHaiDepartment,
  beiJinDepartment: beiJinDepartment,
  minHangDepartment: minHangDepartment,
  xuHuiDepartment: xuHuiDepartment
}
export { MockData }
// # sourceMappingURL=data.js.map
