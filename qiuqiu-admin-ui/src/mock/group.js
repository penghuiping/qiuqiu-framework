import { ApiConstant } from '@/api'
import Mock from 'better-mock'
import { ElementUiTreeVo, JsonResponse } from '@/api/vo'
import { MockData } from '@/mock/data'

Mock.mock(ApiConstant.GROUP_GET_ALL, 'POST', function () {
  const rootDepartment = new ElementUiTreeVo(
    MockData.departments.rootDepartment.id,
    MockData.departments.rootDepartment.name,
    [])

  const shangHaiDepartment = new ElementUiTreeVo(
    MockData.departments.shangHaiDepartment.id,
    MockData.departments.shangHaiDepartment.name,
    [])

  const beiJinDepartment = new ElementUiTreeVo(
    MockData.departments.beiJinDepartment.id,
    MockData.departments.beiJinDepartment.name,
    [])

  const minHangDepartment = new ElementUiTreeVo(
    MockData.departments.minHangDepartment.id,
    MockData.departments.minHangDepartment.name,
    [])

  const xuHuiDepartment = new ElementUiTreeVo(
    MockData.departments.xuHuiDepartment.id,
    MockData.departments.xuHuiDepartment.name,
    [])

  rootDepartment.children = [shangHaiDepartment, beiJinDepartment]
  shangHaiDepartment.children = [minHangDepartment, xuHuiDepartment]

  return new JsonResponse(0, rootDepartment, '')
})
