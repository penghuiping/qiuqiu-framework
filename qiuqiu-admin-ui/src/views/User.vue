<template>
  <div>
    <!--搜索框-->
    <el-row justify="start" type="flex">
      <div class="searchInput">
        <el-input
          v-model="username"
          clearable
          placeholder="请输入用户名">
        </el-input>
      </div>
      <el-button id="searchBtn" icon="el-icon-search" type="primary" @click="handleSearch">搜索</el-button>
    </el-row>
    <!--操作栏-->
    <el-button-group>
      <el-button type="primary" @click="handleCreateUser" v-if="permissionExists(permissions.USER_ADD)">新增</el-button>
    </el-button-group>
    <!--数据表格-->
    <el-table
      v-loading="loading"
      element-loading-text="拼命加载中"
      element-loading-spinner="el-icon-loading"
      :data="tableData">
      <el-table-column
        fixed
        label="ID"
        prop="id"
        width="50">
      </el-table-column>
      <el-table-column
        label="用户名"
        prop="username"
        width="150">
      </el-table-column>
      <el-table-column
        label="昵称"
        prop="nickname"
        width="150">
      </el-table-column>
      <el-table-column
        label="创建时间"
        prop="createTime"
        width="150">
      </el-table-column>
      <el-table-column
        label="更新时间"
        prop="lastModifiedTime"
        width="150">
      </el-table-column>
      <el-table-column
        label="是否有效"
        prop="enable"
        width="50">
        <template slot-scope="scope">
          <span>{{ scope.row.enable === 1 ? '有效' : '无效' }}</span>
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        width="200">
        <template slot-scope="scope">
          <el-button
            size="small"
            type="text"
            v-if="permissionExists(permissions.USER_DETAIL)" @click="detailInfo(scope.row)">
            查看
          </el-button>
          <el-button
            size="small"
            type="text"
            v-if="permissionExists(permissions.USER_UPDATE)" @click="updateRow(scope.row)">
            编辑
          </el-button>
          <el-button
            size="small"
            type="text"
            @click.native.prevent="deleteRow(scope.$index, tableData)" v-if="permissionExists(permissions.USER_DELETE)">
            删除
          </el-button>
          <el-button
            size="small"
            type="text"
            v-if="permissionExists(permissions.USER_UPDATE)"
            @click.native.prevent="toggleEnable(scope.$index, tableData)">
            {{ scope.row.enable === '1' ? '使无效' : '使有效' }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!---分页--->
    <el-row justify="end" type="flex" id="pagination">
      <el-pagination
        background
        :hide-on-single-page="hideOnSinglePage"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[5, 10, 20, 50]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </el-row>

    <!--查看详情表单-->
    <el-dialog title="用户详情" :visible.sync="userDetailDialogVisible">
      <el-form :model="userDetail" id="userDetailForm">
        <el-form-item :label-width="dialogFormLabelWidth" label="用户名:">
          {{ userDetail.username }}
        </el-form-item>
        <el-form-item :label-width="dialogFormLabelWidth" label="昵称:">
          {{ userDetail.nickname }}
        </el-form-item>
        <el-form-item :label-width="dialogFormLabelWidth" label="角色名:">
          {{ userDetail.roles }}
        </el-form-item>
        <el-form-item :label-width="dialogFormLabelWidth" label="部门名:">
          {{ userDetail.groupName }}
        </el-form-item>
        <el-form-item :label-width="dialogFormLabelWidth" label="创建时间:">
          {{ userDetail.createTime }}
        </el-form-item>
        <el-form-item :label-width="dialogFormLabelWidth" label="修改时间:">
          {{ userDetail.lastModifiedTime }}
        </el-form-item>
        <el-form-item :label-width="dialogFormLabelWidth" label="是否有效:">
          {{ userDetail.enable === 1 ? '有效' : '无效' }}
        </el-form-item>
      </el-form>
    </el-dialog>

    <!--新增用户信息表单-->
    <el-dialog title="用户新增" :visible.sync="userAddDialogVisible">
      <el-form ref="userAddForm" :model="userCreateVo" :rules="rules">
        <el-form-item label="用户名:" :label-width="dialogFormLabelWidth" prop="username">
          <el-input v-model="userCreateVo.username"></el-input>
        </el-form-item>
        <el-form-item label="用户昵称:" :label-width="dialogFormLabelWidth" prop="nickname">
          <el-input v-model="userCreateVo.nickname"></el-input>
        </el-form-item>
        <el-form-item :label-width="dialogFormLabelWidth" label="用户密码:" prop="password">
          <el-input v-model="userCreateVo.password"></el-input>
        </el-form-item>
        <el-form-item :label-width="dialogFormLabelWidth" label="角色名:" prop="roleIds">
          <el-select v-model="userCreateVo.roleIds" multiple placeholder="请选择角色">
            <el-option v-for="item in roles" :key=item.id :label=item.description :value=item.id></el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label-width="dialogFormLabelWidth" label="用户组:" prop="groupId">
          <el-cascader
            v-model="userCreateVo.groupId"
            :options="groups"
            :show-all-levels="false"
            clearable>
          </el-cascader>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="userAddDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleCreateUserConfirm">确 定</el-button>
      </div>
    </el-dialog>

    <!--更新用户信息表单-->
    <el-dialog title="用户更新" :visible.sync="userUpdateDialogVisible">
      <el-form :model="userDetail" ref="userAddForm" :rules="rules">
        <el-form-item label="用户名:" :label-width="dialogFormLabelWidth" prop="username">
          <el-input v-model="userDetail.username"></el-input>
        </el-form-item>
        <el-form-item label="用户昵称:" :label-width="dialogFormLabelWidth" prop="nickname">
          <el-input v-model="userDetail.nickname"></el-input>
        </el-form-item>
        <el-form-item label="角色名:" :label-width="dialogFormLabelWidth" prop="roles">
          <el-select v-model="userDetail.roles" multiple placeholder="请选择角色">
            <el-option v-for="item in roles" :key=item.id :label=item.description :value=item.name></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否有效:" :label-width="dialogFormLabelWidth" prop="enable">
          <el-switch
            v-model="userDetail.enable"
            active-color="#13ce66"
            inactive-color="#ff4949">
          </el-switch>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="userUpdateDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleUpdateUserConfirm">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script lang="ts">

import { Component } from 'vue-property-decorator'
import { BaseVue } from '@/BaseVue'
import { UserApi } from '@/api/user'
import { ElementUiTreeVo, RoleVo, UserCreateVo, UserListVo } from '@/api/vo/'
import { ElForm } from 'element-ui/types/form'
import { RoleApi } from '@/api/role'
import { GroupApi } from '@/api/group'

@Component
export default class User extends BaseVue {
  private username = ''
  private mobile = ''
  private currentPage = 1
  private total = 1
  private pageSize = 5
  private tableData: UserListVo[] = []
  private userDetailDialogVisible = false
  private userAddDialogVisible = false
  private userUpdateDialogVisible = false
  private hideOnSinglePage = false
  private loading = true
  private dialogFormLabelWidth = '120px'
  private roles: RoleVo[] = []
  private userDetail = {
    id: '',
    username: '',
    nickname: '',
    roles: [],
    groupName: '',
    createTime: '',
    lastModifiedTime: '',
    enable: ''
  }

  private groups: ElementUiTreeVo[] = []
  private treeProps = {
    children: 'children',
    label: 'label'
  }

  private userCreateVo = {
    username: '',
    nickname: '',
    password: '',
    groupId: '',
    roleIds: ''
  }

  private rules = {
    username: [
      { required: true, message: '请输入用户名', trigger: 'blur' }
    ],
    nickname: [
      { required: true, message: '请输入昵称', trigger: 'blur' }
    ],
    password: [
      { required: true, message: '请输入密码', trigger: 'blur' },
      { min: 6, message: '买长度至少6位', trigger: 'blur' }
    ],
    roleIds: [
      { required: true, message: '请选择角色', trigger: 'blur' }
    ],
    groupId: [
      { required: true, message: '请选择组', trigger: 'blur' }
    ]
  }

  mounted () {
    this.goToPage('', '', this.currentPage, this.pageSize)
    this.getRoles()
    this.getGroups()
  }

  async getRoles () {
    const res = await RoleApi.getAll()
    console.log(res)
    this.roles = res.data.data
  }

  async getGroups () {
    const res = await GroupApi.getAll()
    this.groups.push(res.data.data)
  }

  // 状态有效/无效开关操作
  toggleEnable (index: number, rows: UserListVo[]) {
    const userDto = rows[index]
    let message = ''
    let enable = ''
    if (userDto.enable === '1') {
      message = '失效'
      enable = '0'
    } else {
      message = '生效'
      enable = '1'
    }

    this.$confirm('使这条记录' + message + ', 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(() => {
      userDto.enable = enable
      this.$message({
        type: 'success',
        message: '成功!'
      })
    }).catch(() => {
      this.$message({
        type: 'info',
        message: '已取消'
      })
    })
  }

  // 物理删除按钮操作
  deleteRow (index: number, rows: UserListVo[]) {
    this.$confirm('此操作将永久删除该条数据, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(async () => {
      const user = rows[index]
      const res = await UserApi.delete(user.id)
      if (res && res.data.data) {
        rows.splice(index, 1)
        this.$message({
          type: 'success',
          message: '删除成功!'
        })
      } else {
        this.$message({
          type: 'info',
          message: '删除失败'
        })
      }
    }).catch(() => {
      this.$message({
        type: 'info',
        message: '已取消删除'
      })
    })
  }

  async updateRow (row: UserListVo) {
    const res = await UserApi.detail(parseInt(row.id))
    Object.assign(this.userDetail, res.data.data)
    this.userUpdateDialogVisible = true
  }

  // 查看详情按钮操作
  async detailInfo (row: UserListVo) {
    const res = await UserApi.detail(parseInt(row.id))
    Object.assign(this.userDetail, res.data.data)
    this.userDetailDialogVisible = true
  }

  // 分页器每页大小改变时候的回调方法
  handleSizeChange (pageSize: number) {
    this.pageSize = pageSize
    this.goToPage(this.username, this.mobile, this.currentPage, this.pageSize)
  }

  // 分页器当前页改变时候的回调方法
  handleCurrentChange (pageNum: number) {
    this.currentPage = pageNum
    this.goToPage(this.username, this.mobile, this.currentPage, this.pageSize)
  }

  // 处理搜索按钮
  handleSearch () {
    this.currentPage = 1
    this.goToPage(this.username, this.mobile, this.currentPage, this.pageSize)
  }

  // 创建新用户
  async handleCreateUser () {
    this.userAddDialogVisible = true
  }

  handleCreateUserConfirm () {
    (this.$refs.userAddForm as ElForm).validate(async valid => {
      if (valid) {
        console.log('create user:', this.userCreateVo)
        const userVo = new UserCreateVo('', '', '', -1, [])
        Object.assign(userVo, this.userCreateVo)
        console.log('create user:', userVo)
        await UserApi.create(userVo)
        this.userAddDialogVisible = false
      }
    })
  }

  handleUpdateUserConfirm () {
    (this.$refs.userAddForm as ElForm).validate(async valid => {
      if (valid) {
        this.userUpdateDialogVisible = false
      }
    })
  }

  // 跳去某页操作
  async goToPage (username: string, mobile: string, pageNum: number, pageSize: number) {
    this.loading = true
    const res = await UserApi.page(username, mobile, pageNum, pageSize)
    this.loading = false
    this.tableData = res.data.data.data
    this.currentPage = res.data.data.currentPage
    this.total = res.data.data.total

    if ((this.total / this.pageSize) === 0) {
      this.hideOnSinglePage = true
    }
  }
}

</script>

<style lang="scss" scoped>
#userDetailForm .el-form-item {
  color: black !important;
}

.el-table {
  width: 100%;
  margin-top: 1em;
}

.searchInput {
  width: 200px !important;
  margin-top: 1em;
  margin-right: 1em;
}

.el-button-group, #pagination, #searchBtn {
  margin-top: 1em;
}

</style>
