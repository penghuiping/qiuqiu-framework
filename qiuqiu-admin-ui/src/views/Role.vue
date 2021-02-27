<template>
  <div>
    <!--操作栏-->
    <el-button-group>
      <el-button type="primary" @click="handleCreateRole" v-if="permissionExists(permissions.USER_ADD)">新增</el-button>
    </el-button-group>
    <!--数据表格-->
    <el-table
      v-loading="loading"
      element-loading-text="拼命加载中"
      element-loading-spinner="el-icon-loading"
      :data= "tableData">
      <el-table-column
        fixed
        label="ID"
        prop="id"
        width="50">
      </el-table-column>
      <el-table-column
        label="角色名"
        prop="name"
        width="150">
      </el-table-column>
      <el-table-column
        label="描述"
        prop="description"
        width="150">
      </el-table-column>
      <el-table-column
        label="创建时间"
        prop="createTime"
        width="180">
      </el-table-column>
      <el-table-column
        label="更新时间"
        prop="lastModifiedTime"
        width="180">
      </el-table-column>
      <el-table-column
        label="是否有效"
        prop="enable"
        width="50">
        <template slot-scope="scope">
          <span >{{ scope.row.enable==='1'?'有效':'无效' }}</span>
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        width="150">
        <template slot-scope="scope">
          <el-button @click="detailInfo(scope.row)" type="text" size="small" v-if="permissionExists(permissions.ROLE_DETAIL)">查看</el-button>
          <el-button
            size="small"
            type="text"
            @click.native.prevent="deleteRow(scope.$index, tableData)" v-if="permissionExists(permissions.ROLE_DELETE)">
            删除
          </el-button>
          <el-button
            size="small"
            type="text"
            @click.native.prevent="toggleEnable(scope.$index, tableData)" v-if="permissionExists(permissions.ROLE_UPDATE)">
            {{scope.row.enable==='1'?'使无效':'使有效'}}
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
    <el-dialog title="角色详情" :visible.sync="roleDetailDialogVisible">
      <el-form :model="roleDetail" id="roleDetailForm">
          <el-form-item label="角色名:" :label-width="dialogFormLabelWidth">
            {{roleDetail.name}}
          </el-form-item>
          <el-form-item label="角色描述:" :label-width="dialogFormLabelWidth">
            {{roleDetail.description}}
          </el-form-item>
          <el-form-item label="创建时间:" :label-width="dialogFormLabelWidth">
            {{roleDetail.createTime}}
          </el-form-item>
          <el-form-item label="修改时间:" :label-width="dialogFormLabelWidth">
            {{roleDetail.lastModifiedTime}}
          </el-form-item>
          <el-form-item label="是否有效:" :label-width="dialogFormLabelWidth">
            {{roleDetail.enable==='1'?'有效':'无效'}}
          </el-form-item>
        <el-form-item label="权限:" :label-width="dialogFormLabelWidth">
          <el-tree show-checkbox
                   node-key="id"
                   :default-checked-keys="permissionChecked"
                   :default-expand-all="true"
                   :props="treeProps"
                   :data="permissionTree"></el-tree>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script lang="ts">
import { Component } from 'vue-property-decorator'
import { BaseVue } from '@/BaseVue'
import { RoleApi } from '@/api/role'
import { ElementUiTreeVo, RoleDetailVo, RoleListVo } from '@/api/vo'

@Component
export default class Role extends BaseVue {
  private tableData: RoleListVo[] = []
  private loading = false
  private currentPage =1
  private total = 1
  private pageSize = 5
  private hideOnSinglePage = false
  private roleDetailDialogVisible = false
  private dialogFormLabelWidth = '120px'
  private roleDetail = RoleDetailVo.newInstant()
  private permissionTree: ElementUiTreeVo[] = []
  private treeProps = {
    children: 'children',
    label: 'label'
  }

  private permissionChecked: string[] = []

  mounted () {
    this.goToPage(1, this.pageSize)
  }

  async detailInfo (row: RoleListVo) {
    const loading = this.showLoading()
    console.log('row:', row)
    const res = await RoleApi.detail(row.id)
    this.roleDetail = res.data.returnObject
    this.roleDetailDialogVisible = true

    const checkedPermissions = this.roleDetail.permissions
    this.permissionChecked = []
    for (let i = 0; i < checkedPermissions.length; i++) {
      const permission = checkedPermissions[i]
      this.permissionChecked.push(permission.id)
    }

    const res1 = await RoleApi.getAllSystemPermissions()
    this.permissionTree = res1.data.returnObject
    loading.close()
  }

  deleteRow () {
    console.log('.')
  }

  toggleEnable () {
    console.log('.')
  }

  handleCreateRole () {
    console.log('.')
  }

  handleSizeChange (pageSize: number) {
    this.pageSize = pageSize
    this.goToPage(this.currentPage, this.pageSize)
  }

  handleCurrentChange (pageNum: number) {
    this.currentPage = pageNum
    this.goToPage(this.currentPage, this.pageSize)
  }

  // 跳去某页操作
  async goToPage (pageNum: number, pageSize: number) {
    this.loading = true
    const res = await RoleApi.page(pageNum, pageSize)
    this.loading = false
    this.tableData = res.data.returnObject.data
    this.currentPage = res.data.returnObject.currentPage
    this.total = res.data.returnObject.total

    if ((this.total / this.pageSize) === 0) {
      this.hideOnSinglePage = true
    }
  }
}
</script>

<style lang="scss" scoped>
.el-button-group , #pagination{
  margin-top: 1em;
}

#roleDetailForm .el-form-item{
  color: black !important;
}
</style>
