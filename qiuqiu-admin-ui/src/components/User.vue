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
      <el-button type="primary" @click="handleCreateUser" v-if="permissionExists(resources.USER,permissions.ADD)">新增</el-button>
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
          <span>{{ scope.row.enable? '有效' : '无效' }}</span>
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
            v-if="permissionExists(resources.USER,permissions.DETAIL)" @click="detailInfo(scope.row)">
            查看
          </el-button>
          <el-button
            size="small"
            type="text"
            v-if="permissionExists(resources.USER,permissions.UPDATE)" @click="updateRow(scope.row)">
            编辑
          </el-button>
          <el-button
            size="small"
            type="text"
            @click.native.prevent="deleteRow(scope.$index, tableData)" v-if="permissionExists(resources.USER,permissions.DELETE)">
            删除
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
        <el-form-item :label-width="dialogFormLabelWidth" label="用户组名:">
          {{ userDetail.groupName }}
        </el-form-item>
        <el-form-item :label-width="dialogFormLabelWidth" label="创建时间:">
          {{ userDetail.createTime }}
        </el-form-item>
        <el-form-item :label-width="dialogFormLabelWidth" label="修改时间:">
          {{ userDetail.lastModifiedTime }}
        </el-form-item>
        <el-form-item :label-width="dialogFormLabelWidth" label="是否有效:">
          {{ userDetail.enable? '有效' : '无效' }}
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
          <el-input v-model="userCreateVo.groupId" hidden style="display: none"></el-input>
          <el-cascader
            ref="groupCascader"
            v-model="groupsChecked"
            :options="groups"
            :show-all-levels="false"
            :props="{ checkStrictly: true }"
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
      <el-form ref="userUpdateForm" :model="userUpdateVo" :rules="rules">
        <el-form-item :label-width="dialogFormLabelWidth" label="id:" prop="id">
          <el-input v-model="userUpdateVo.id" disabled></el-input>
        </el-form-item>
        <el-form-item label="用户名:" :label-width="dialogFormLabelWidth" prop="username">
          <el-input v-model="userUpdateVo.username"></el-input>
        </el-form-item>
        <el-form-item label="用户昵称:" :label-width="dialogFormLabelWidth" prop="nickname">
          <el-input v-model="userUpdateVo.nickname"></el-input>
        </el-form-item>
        <el-form-item :label-width="dialogFormLabelWidth" label="角色名:" prop="roleIds">
          <el-select v-model="userUpdateVo.roleIds" multiple placeholder="请选择角色">
            <el-option v-for="item in roles" :key=item.id :label=item.description :value=item.id></el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label-width="dialogFormLabelWidth" label="用户组:" prop="groupId">
          <el-input v-model="userUpdateVo.groupId" hidden style="display: none"></el-input>
          <el-cascader
            ref="groupCascaderUpdate"
            v-model="groupsChecked"
            :options="groups"
            :props="{checkStrictly: true}"
            :show-all-levels="false"
            clearable>
          </el-cascader>
        </el-form-item>
        <el-form-item label="是否有效:" :label-width="dialogFormLabelWidth" prop="enable">
          <el-switch
            v-model="userUpdateVo.enable"
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
import { ElForm } from 'element-ui/types/form'
import { RoleApi } from '@/api/role'
import { GroupApi } from '@/api/group'
import { UserCreateVo, UserDetailVo, UserListVo, UserUpdateVo } from '@/api/vo/user'
import { RoleVo } from '@/api/vo/role'
import { ElementUiTreeVo } from '@/api/vo'

@Component
export default class User extends BaseVue {
  private username = ''
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
  private userDetail: UserDetailVo = UserDetailVo.newInstant()
  private userCreateVo: UserCreateVo = UserCreateVo.newInstant()
  private userUpdateVo: UserUpdateVo = UserUpdateVo.newInstant()
  private groups: ElementUiTreeVo[] = []
  private groupsChecked: string[] = []
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
    this.goToPage('', this.currentPage, this.pageSize)
    this.getRoles()
    this.getGroups()
  }

  async getRoles () {
    const res = await RoleApi.getAll()
    this.roles = res.data.data
  }

  async getGroups () {
    const res = await GroupApi.getAll()
    this.groups = []
    this.groups.push(res.data.data)
    console.log('groups:', this.groups)
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
        this.goToPage(this.username, 1, this.pageSize)
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
    const res = await UserApi.detail(row.id)
    this.userUpdateVo.id = res.data.data.id
    this.userUpdateVo.username = res.data.data.username
    this.userUpdateVo.nickname = res.data.data.nickname
    this.userUpdateVo.groupId = res.data.data.groupId
    this.groupsChecked = this.findGroupPath(res.data.data.groupId)
    console.log('this.groupsChecked:', this.groupsChecked)
    this.userUpdateVo.roleIds = res.data.data.roleIds
    this.userUpdateVo.enable = res.data.data.enable
    this.userUpdateDialogVisible = true
  }

  findGroup (value: string): ElementUiTreeVo {
    const set = new Array<ElementUiTreeVo>()
    this.findChildGroup(value, this.groups, set)
    return set[0]
  }

  findChildGroup (value: string, children: ElementUiTreeVo[], result: Array<ElementUiTreeVo>) {
    children.forEach(child => {
      if (child.value === value) {
        result.push(child)
        return
      }
      if (child.children && child.children.length > 0) {
        this.findChildGroup(value, child.children, result)
      }
    })
  }

  findGroupPath (groupId: number): string[] {
    this.getGroups()
    const path: string[] = []
    const node = this.groups[0]
    this.findGroupPath0(groupId, node, path)
    return path
  }

  findGroupPath0 (nodeId: number, node: ElementUiTreeVo, path: string[]): boolean {
    path.push(node.value)
    if (node.id + '' === nodeId + '') {
      return true
    }

    if (node.children && node.children.length > 0) {
      for (let i = 0; i < node.children.length; i++) {
        const childNode = node.children[i]
        const res0 = this.findGroupPath0(nodeId, childNode, path)
        if (res0) {
          return true
        }
      }
    }
    path.pop()
    return false
  }

  // 查看详情按钮操作
  async detailInfo (row: UserListVo) {
    const res = await UserApi.detail(row.id)
    Object.assign(this.userDetail, res.data.data)
    this.userDetailDialogVisible = true
  }

  // 分页器每页大小改变时候的回调方法
  handleSizeChange (pageSize: number) {
    this.pageSize = pageSize
    this.goToPage(this.username, this.currentPage, this.pageSize)
  }

  // 分页器当前页改变时候的回调方法
  handleCurrentChange (pageNum: number) {
    this.currentPage = pageNum
    this.goToPage(this.username, this.currentPage, this.pageSize)
  }

  // 处理搜索按钮
  handleSearch () {
    this.currentPage = 1
    this.goToPage(this.username, this.currentPage, this.pageSize)
  }

  // 创建新用户
  async handleCreateUser () {
    this.userCreateVo = UserCreateVo.newInstant()
    this.userAddDialogVisible = true
  }

  handleCreateUserConfirm () {
    const groupName = this.groupsChecked.reverse()[0]
    const tmp = this.findGroup(groupName)
    this.userCreateVo.groupId = tmp.id;
    (this.$refs.userAddForm as ElForm).validate(async valid => {
      if (valid) {
        const userVo = new UserCreateVo(
          this.userCreateVo.username,
          this.userCreateVo.nickname,
          this.userCreateVo.password,
          this.userCreateVo.groupId,
          this.userCreateVo.roleIds)
        const res = await UserApi.create(userVo)
        if (res && res.data.data) {
          this.goToPage(this.username, 1, this.pageSize)
          this.$message({
            type: 'success',
            message: '新增成功!'
          })
        } else {
          this.$message({
            type: 'info',
            message: '新增失败'
          })
        }
        this.userAddDialogVisible = false
      }
    })
  }

  handleUpdateUserConfirm () {
    const groupName = this.groupsChecked.reverse()[0]
    const tmp = this.findGroup(groupName)
    this.userUpdateVo.groupId = tmp.id;
    (this.$refs.userUpdateForm as ElForm).validate(async valid => {
      if (valid) {
        this.userUpdateDialogVisible = false
        const userVo = new UserUpdateVo(
          this.userUpdateVo.id,
          this.userUpdateVo.username,
          this.userUpdateVo.nickname,
          this.userUpdateVo.password,
          this.userUpdateVo.groupId,
          this.userUpdateVo.roleIds,
          this.userUpdateVo.enable)
        const res = await UserApi.update(userVo)
        if (res && res.data.data) {
          this.goToPage(this.username, 1, this.pageSize)
          this.$message({
            type: 'success',
            message: '更新成功!'
          })
        } else {
          this.$message({
            type: 'info',
            message: '更新失败'
          })
        }
      }
    })
  }

  // 跳去某页操作
  async goToPage (username: string, pageNum: number, pageSize: number) {
    this.loading = true
    const res = await UserApi.page(username, pageNum, pageSize)
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
