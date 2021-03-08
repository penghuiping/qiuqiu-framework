import Mock from 'better-mock'
import { JsonResponse } from '@/api/vo'
import { UserDetailVo } from '@/api/vo/user'
import { ApiConstant } from '@/api'
import { MockData } from '@/mock/data'

Mock.mock(ApiConstant.LOGIN, 'post', function (options) {
  console.log('mock options', options)
  const requestBody = JSON.parse(options.body)
  console.log('requestBody:', requestBody)
  const username = requestBody.username
  const password = requestBody.password

  if (username === 'admin' && password === '123456') {
    return {
      errorCode: 0,
      returnObject: {
        expireTime: '7200',
        token: 'b569f67c34ae869d6c544823d26a7966'
      },
      message: null
    }
  } else if (username === 'jack' && password === '123456') {
    return {
      errorCode: 0,
      returnObject: {
        expireTime: '7200',
        token: 'a569f67c34ae869d6c544823d26a7966'
      },
      message: null
    }
  } else {
    return {
      errorCode: 10001,
      returnObject: null,
      message: '用户名与密码错误'
    }
  }
})

Mock.mock(ApiConstant.USER_INFO, 'post', function (options) {
  const token = options.headers.token
  console.log('headers:', token)
  const requestBody = JSON.parse(options.body)
  console.log('requestBody:', requestBody)

  if (token === 'b569f67c34ae869d6c544823d26a7966') {
    const userDto = Object.assign(new UserDetailVo(), MockData.users.admin)
    userDto.permissions = [
      MockData.permissions.home.name,
      MockData.permissions.permissionManagement.name,
      MockData.permissions.mediaManagement.name,
      MockData.permissions.reportManagement.name,
      MockData.permissions.userPageList.name,
      MockData.permissions.userAdd.name,
      MockData.permissions.userDetail.name,
      MockData.permissions.userUpdate.name,
      MockData.permissions.userDelete.name,
      MockData.permissions.rolePageList.name,
      MockData.permissions.roleAdd.name,
      MockData.permissions.roleDetail.name,
      MockData.permissions.roleUpdate.name,
      MockData.permissions.roleDelete.name,
      MockData.permissions.departmentPageList.name,
      MockData.permissions.mediaPageList.name,
      MockData.permissions.reportPageList.name
    ]
    return new JsonResponse(0, userDto, null)
  } else if (token === 'a569f67c34ae869d6c544823d26a7966') {
    const userDto = Object.assign(new UserDetailVo(), MockData.users.jack)
    userDto.permissions = [
      MockData.permissions.home.name,
      MockData.permissions.departmentPageList.name,
      MockData.permissions.mediaPageList.name,
      MockData.permissions.reportPageList.name
    ]
    return new JsonResponse(0, userDto, null)
  } else {
    return new JsonResponse(1, null, '未登录...')
  }
})

Mock.mock(ApiConstant.USER_PAGE, 'post', function (options) {
  const requestBody = JSON.parse(options.body)
  console.log('requestBody:', requestBody)

  const username = requestBody.username
  const mobile = requestBody.mobile
  const pageNum = requestBody.pageNum
  const pageSize = requestBody.pageSize

  const start = (pageNum - 1) * pageSize
  const offset = pageSize

  const totalData = [
    MockData.users.admin,
    MockData.users.jack,
    MockData.users.alice,
    MockData.users.mary,
    MockData.users.ted,
    MockData.users.tom
  ]

  let total = []

  for (let i = 0; i < totalData.length; i++) {
    const item = totalData[i]
    let choose = item
    if (username && username !== '') {
      if (item.username !== username) {
        choose = null
      }
    }

    if (mobile && mobile !== '') {
      if (item.mobile !== mobile) {
        choose = null
      }
    }

    if (choose !== null) {
      total = total.concat(choose)
    }
  }
  total = total.sort((a, b) => {
    return -(b.id - a.id)
  })
  const res = total.slice(start, start + offset)

  return {
    errorCode: 0,
    returnObject: {
      total: total.length,
      currentPage: pageNum,
      data: res
    },
    message: null
  }
})

Mock.mock(ApiConstant.USER_DELETE, 'post', function (options) {
  const requestBody = JSON.parse(options.body)
  console.log('requestBody:', requestBody)
  return new JsonResponse(0, true, null)
})
