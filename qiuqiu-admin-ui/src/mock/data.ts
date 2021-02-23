import { PermissionVo, RoleListVo } from '@/api/vo'

const home = new PermissionVo('1', '_home', '首页', '')
const permissionManagement = new PermissionVo('2', '_permission_management', '权限管理', '')
const mediaManagement = new PermissionVo('3', '_media_management', '媒体管理', '')
const reportManagement = new PermissionVo('4', '_report_management', '报表', '')

const userPageList = new PermissionVo('5', '_user_page_list', '用户列表', '2')
const userDetail = new PermissionVo('6', '_user_detail', '用户详情', '2')
const userAdd = new PermissionVo('7', '_user_add', '用户新增', '2')
const userDelete = new PermissionVo('8', '_user_delete', '用户删除', '2')
const userUpdate = new PermissionVo('9', '_user_update', '用户更新', '2')

const rolePageList = new PermissionVo('10', '_role_page_list', '角色列表', '2')
const roleDetail = new PermissionVo('11', '_role_detail', '角色详情', '2')
const roleDelete = new PermissionVo('12', '_role_delete', '角色删除', '2')
const roleAdd = new PermissionVo('13', '_role_add', '角色新增', '2')
const roleUpdate = new PermissionVo('14', '_role_update', '角色更新', '2')

const departmentPageList = new PermissionVo('15', '_department_page_list', '部门列表', '2')

const mediaPageList = new PermissionVo('16', '_media_page_list', '媒体列表', '3')
const reportPageList = new PermissionVo('17', '_report_page_list', '报表列表', '4')

const adminRole = new RoleListVo('1', 'admin', '超级管理员', '2021-01-01 10:00:00', '2021-01-01 10:00:00', '1')
const commonUserRole = new RoleListVo('2', 'common_user', '普通用户', '2021-01-01 10:00:00', '2021-01-01 10:00:00', '1')

const admin =
  {
    id: '1',
    username: 'Admin',
    nickname: '超级管理员',
    mobile: '18812345678',
    roles: '超级管理员',
    createTime: '2020-11-1 10:00:00',
    lastModifiedTime: '2020-11-1 10:00:00',
    enable: '1'
  }

const mary = {
  id: '2',
  username: 'Mary',
  nickname: '玛丽',
  mobile: '18812345677',
  roles: '普通用户',
  createTime: '2020-11-1 10:00:00',
  lastModifiedTime: '2020-11-1 10:00:00',
  enable: '1'
}

const tom = {
  id: '3',
  username: 'Tom',
  nickname: '汤姆',
  mobile: '18812345676',
  roles: '普通用户',
  createTime: '2020-11-1 10:00:00',
  lastModifiedTime: '2020-11-1 10:00:00',
  enable: '1'
}

const ted = {
  id: '4',
  username: 'Ted',
  nickname: '泰德',
  mobile: '18812345675',
  roles: '普通用户',
  createTime: '2020-11-1 10:00:00',
  lastModifiedTime: '2020-11-1 10:00:00',
  enable: '1'
}

const jack = {
  id: '5',
  username: 'Jack',
  nickname: '杰克',
  mobile: '18812345674',
  roles: '普通用户',
  createTime: '2020-11-1 10:00:00',
  lastModifiedTime: '2020-11-1 10:00:00',
  enable: '1'
}

const alice = {
  id: '6',
  username: 'Alice',
  nickname: '爱丽丝',
  mobile: '18812345673',
  roles: '普通用户',
  createTime: '2020-11-1 10:00:00',
  lastModifiedTime: '2020-11-1 10:00:00',
  enable: '1'
}

class MockData {
  static permissions = {
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

  static users = {
    admin: admin,
    mary: mary,
    tom: tom,
    ted: ted,
    jack: jack,
    alice: alice
  }

  static roles = {
    adminRole: adminRole,
    commonUserRole: commonUserRole
  }
}

export { MockData }
