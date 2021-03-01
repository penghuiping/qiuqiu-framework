import Mock from 'better-mock'
import { ElementUiTreeVo, JsonResponse, PageVo, RoleDetailVo, RoleVo } from '@/api/vo'
import { ApiConstant } from '@/api'
import { MockData } from '@/mock/data'

Mock.mock(ApiConstant.ROLE_GET_ALL, 'post', function () {
  const role1 = Object.assign(new RoleVo(), MockData.roles.adminRole)
  const role2 = Object.assign(new RoleVo(), MockData.roles.commonUserRole)
  return new JsonResponse(0, [role1, role2], null)
})

Mock.mock(ApiConstant.ROLE_DETAIL, 'post', function (options) {
  const requestBody = JSON.parse(options.body)
  console.log('requestBody:', requestBody)
  let role = null
  if (requestBody.id === '1') {
    role = Object.assign(new RoleDetailVo('', '', '', [
      MockData.permissions.home,
      MockData.permissions.permissionManagement,
      MockData.permissions.mediaManagement,
      MockData.permissions.reportManagement,
      MockData.permissions.userPageList,
      MockData.permissions.userDetail,
      MockData.permissions.userAdd,
      MockData.permissions.userDelete,
      MockData.permissions.userUpdate,
      MockData.permissions.rolePageList,
      MockData.permissions.roleDetail,
      MockData.permissions.roleDelete,
      MockData.permissions.roleAdd,
      MockData.permissions.roleUpdate,
      MockData.permissions.departmentPageList,
      MockData.permissions.mediaPageList,
      MockData.permissions.reportPageList
    ], '', '', ''), MockData.roles.adminRole)
  } else if (requestBody.id === '2') {
    role = Object.assign(RoleDetailVo.newInstant(), MockData.roles.commonUserRole)
  }
  return new JsonResponse(0, role, null)
})

Mock.mock(ApiConstant.ROLE_PAGE, 'post', function (options) {
  const requestBody = JSON.parse(options.body)
  console.log('requestBody:', requestBody)
  const role1 = MockData.roles.adminRole
  const role2 = MockData.roles.commonUserRole
  const page = new PageVo(2, 1, [role1, role2])
  return new JsonResponse(0, page, null)
})

Mock.mock(ApiConstant.PERMISSION_GET_ALL, 'POST', function () {
  const home = new ElementUiTreeVo(
    MockData.permissions.home.id,
    MockData.permissions.home.description,
    [])
  const permissionManagement = new ElementUiTreeVo(
    MockData.permissions.permissionManagement.id,
    MockData.permissions.permissionManagement.description,
    [])
  const userPageList = new ElementUiTreeVo(
    MockData.permissions.userPageList.id,
    MockData.permissions.userPageList.description,
    [])
  const userDetail = new ElementUiTreeVo(
    MockData.permissions.userDetail.id,
    MockData.permissions.userDetail.description,
    [])
  const userAdd = new ElementUiTreeVo(
    MockData.permissions.userAdd.id,
    MockData.permissions.userAdd.description,
    [])
  const userDelete = new ElementUiTreeVo(
    MockData.permissions.userDelete.id,
    MockData.permissions.userDelete.description,
    [])
  const userUpdate = new ElementUiTreeVo(
    MockData.permissions.userUpdate.id,
    MockData.permissions.userUpdate.description,
    [])
  const rolePageList = new ElementUiTreeVo(
    MockData.permissions.rolePageList.id,
    MockData.permissions.rolePageList.description,
    [])
  const roleDetail = new ElementUiTreeVo(
    MockData.permissions.roleDetail.id,
    MockData.permissions.roleDetail.description,
    [])
  const roleDelete = new ElementUiTreeVo(
    MockData.permissions.roleDelete.id,
    MockData.permissions.roleDelete.description,
    [])
  const roleAdd = new ElementUiTreeVo(
    MockData.permissions.roleAdd.id,
    MockData.permissions.roleAdd.description,
    [])
  const roleUpdate = new ElementUiTreeVo(
    MockData.permissions.roleUpdate.id,
    MockData.permissions.roleUpdate.description,
    [])
  const departmentPageList = new ElementUiTreeVo(
    MockData.permissions.departmentPageList.id,
    MockData.permissions.departmentPageList.description,
    [])
  const mediaPageList = new ElementUiTreeVo(
    MockData.permissions.mediaPageList.id,
    MockData.permissions.mediaPageList.description,
    [])
  const reportPageList = new ElementUiTreeVo(
    MockData.permissions.reportPageList.id,
    MockData.permissions.reportPageList.description,
    [])

  userPageList.children = [userAdd, userUpdate, userDelete, userDetail]
  rolePageList.children = [roleAdd, roleUpdate, roleDelete, roleDetail]
  permissionManagement.children = [userPageList, rolePageList, departmentPageList]

  return new JsonResponse(0, [
    home,
    permissionManagement,
    mediaPageList,
    reportPageList
  ], null)
})

Mock.mock(ApiConstant.PERMISSION_GET_BY_ROLE_ID, 'POST', function (options) {
  const requestBody = JSON.parse(options.body)
  console.log('requestBody:', requestBody)
  if (requestBody.roleId === '1') {
    // admin
    return new JsonResponse(0, [
      MockData.permissions.home,
      MockData.permissions.permissionManagement,
      MockData.permissions.mediaManagement,
      MockData.permissions.reportManagement,
      MockData.permissions.userPageList,
      MockData.permissions.userAdd,
      MockData.permissions.userDetail,
      MockData.permissions.userUpdate,
      MockData.permissions.userDelete,
      MockData.permissions.rolePageList,
      MockData.permissions.roleAdd,
      MockData.permissions.roleDetail,
      MockData.permissions.roleUpdate,
      MockData.permissions.roleDelete,
      MockData.permissions.departmentPageList,
      MockData.permissions.mediaPageList,
      MockData.permissions.reportPageList
    ], null)
  } else if (requestBody.roleId === '2') {
    // common_role
    return new JsonResponse(0, [
      MockData.permissions.home,
      MockData.permissions.departmentPageList,
      MockData.permissions.mediaPageList,
      MockData.permissions.reportPageList
    ], null)
  }
})

Mock.mock(ApiConstant.ROLE_DELETE, 'POST', function (options) {
  const requestBody = JSON.parse(options.body)
  console.log('requestBody:', requestBody)
  return new JsonResponse(0, true, '')
})
