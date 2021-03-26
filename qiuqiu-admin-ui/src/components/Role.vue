<template>
  <div>
    <!--操作栏-->
    <el-button-group>
      <el-button type="primary" @click="handleCreateRole" v-if="permissionExists(resources.ROLE,permissions.ADD)">新增</el-button>
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
        label="是否有效"
        prop="enable"
        width="50">
        <template slot-scope="scope">
          <span >{{ scope.row.enable?'有效':'无效' }}</span>
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        width="200">
        <template slot-scope="scope">
          <el-button @click="detailInfo(scope.row)" type="text" size="small" v-if="permissionExists(resources.ROLE,permissions.DETAIL)">
            查看
          </el-button>
          <el-button @click="update(scope.row)" type="text" size="small" v-if="permissionExists(resources.ROLE,permissions.UPDATE)">
            编辑
          </el-button>
          <el-button
            size="small"
            type="text"
            @click.native.prevent="deleteRow(scope.$index, tableData)" v-if="permissionExists(resources.ROLE,permissions.DELETE)">
            删除
          </el-button>
          <el-button
            size="small"
            type="text"
            @click.native.prevent="toggleEnable(scope.$index, tableData)" v-if="permissionExists(resources.ROLE,permissions.UPDATE)">
            {{scope.row.enable?'使无效':'使有效'}}
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
          <el-form-item label="是否有效:" :label-width="dialogFormLabelWidth">
            {{roleDetail.enable?'有效':'无效'}}
          </el-form-item>
        <el-form-item label="权限:" :label-width="dialogFormLabelWidth">
          <el-tree show-checkbox
                   node-key="id"
                   :default-checked-keys="roleDetail.permissionIds"
                   :default-expand-all="true"
                   :props="treeProps"
                   :data="permissionTree" disabled></el-tree>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!--新增角色信息表单-->
    <el-dialog title="角色创建" :visible.sync="roleCreateDialogVisible">
      <el-form ref="roleCreateForm" :model="roleCreateVo" :rules="rules">
        <el-form-item label="角色名:" :label-width="dialogFormLabelWidth" prop="name">
          <el-input v-model="roleCreateVo.name"></el-input>
        </el-form-item>
        <el-form-item label="角色描述:" :label-width="dialogFormLabelWidth" prop="description">
          <el-input v-model="roleCreateVo.description"></el-input>
        </el-form-item>
        <el-form-item label="是否有效:" :label-width="dialogFormLabelWidth" prop="enable">
          <el-switch
            v-model="roleCreateVo.enable"
            active-color="#13ce66"
            inactive-color="#ff4949">
          </el-switch>
        </el-form-item>
        <el-form-item label="权限:" :label-width="dialogFormLabelWidth" prop="permissionIds">
          <el-tree show-checkbox
                   ref="permissionTree"
                   node-key="id"
                   :default-checked-keys="roleCreateVo.permissionIds"
                   :default-expand-all="true"
                   :props="treeProps"
                   :data="permissionTree"></el-tree>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="roleCreateDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="createConfirm">确 定</el-button>
      </div>
    </el-dialog>

    <!--更新角色信息表单-->
    <el-dialog title="角色更新" :visible.sync="roleUpdateDialogVisible">
      <el-form ref="roleUpdateForm" :model="roleUpdateVo" :rules="rules">
        <el-form-item :label-width="dialogFormLabelWidth" label="id:" prop="id">
          <el-input v-model="roleUpdateVo.id" disabled></el-input>
        </el-form-item>
        <el-form-item label="角色名:" :label-width="dialogFormLabelWidth" prop="name">
          <el-input v-model="roleUpdateVo.name"></el-input>
        </el-form-item>
        <el-form-item label="角色描述:" :label-width="dialogFormLabelWidth" prop="description">
          <el-input v-model="roleUpdateVo.description"></el-input>
        </el-form-item>
        <el-form-item label="是否有效:" :label-width="dialogFormLabelWidth" prop="enable">
          <el-switch
            v-model="roleUpdateVo.enable"
            active-color="#13ce66"
            inactive-color="#ff4949">
          </el-switch>
        </el-form-item>
        <el-form-item label="权限:" :label-width="dialogFormLabelWidth" prop="permissionIds">
          <el-tree show-checkbox
                   ref="permissionTree"
                   node-key="id"
                   :default-checked-keys="roleUpdateVo.permissionIds"
                   :default-expand-all="true"
                   :props="treeProps"
                   :data="permissionTree"></el-tree>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="roleUpdateDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateConfirm">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script lang="ts">
import { Component } from 'vue-property-decorator'
import { BaseVue } from '@/BaseVue'
import { RoleApi } from '@/api/role'
import { ElementUiTreeVo } from '@/api/vo'
import { RoleCreateVo, RoleDetailVo, RoleListVo, RoleUpdateVo } from '@/api/vo/role'
import { ElForm } from 'element-ui/types/form'
import { ElTree } from 'element-ui/types/tree'

@Component
export default class Role extends BaseVue {
  private tableData: RoleListVo[] = []
  private loading = false
  private currentPage = 1
  private total = 1
  private pageSize = 5
  private hideOnSinglePage = false
  private roleDetailDialogVisible = false
  private roleUpdateDialogVisible =false
  private roleCreateDialogVisible =false
  private dialogFormLabelWidth = '120px'
  private roleDetail = RoleDetailVo.newInstant()
  private permissionTree: ElementUiTreeVo[] = []
  private roleUpdateVo = RoleUpdateVo.newInstant()
  private roleCreateVo = RoleCreateVo.newInstant()
  private treeProps = {
    children: 'children',
    label: 'label'
  }

  private rules = {
    name: [
      { required: true, message: '请输入角色名', trigger: 'blur' }
    ],
    description: [
      { required: true, message: '请输入角色描述', trigger: 'blur' }
    ],
    enable: [
      { required: true, message: '请输入角色是否有效', trigger: 'blur' }
    ],
    permissionIds: [
      { required: true, message: '请选择权限', trigger: 'blur' }
    ]
  }

  mounted () {
    this.goToPage(1, this.pageSize)
  }

  async detailInfo (row: RoleListVo) {
    const loading = this.showLoading()
    const res = await RoleApi.detail(row.id)
    this.roleDetail = res.data.data
    this.roleDetailDialogVisible = true

    const res1 = await RoleApi.getAllSystemPermissions()
    this.permissionTree = res1.data.data
    loading.close()
  }

  deleteRow (index: number, rows: RoleListVo[]) {
    this.$confirm('此操作将永久删除该条数据, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(async () => {
      const role = rows[index]
      const res = await RoleApi.delete(role.id)
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

  async update (row: RoleListVo) {
    const res = await RoleApi.detail(row.id)
    this.roleUpdateVo.id = res.data.data.id
    this.roleUpdateVo.name = res.data.data.name
    this.roleUpdateVo.description = res.data.data.description
    this.roleUpdateVo.permissionIds = res.data.data.permissionIds
    this.roleUpdateVo.enable = res.data.data.enable
    this.roleUpdateDialogVisible = true

    const res1 = await RoleApi.getAllSystemPermissions()
    this.permissionTree = res1.data.data
  }

  updateConfirm () {
    this.roleUpdateVo.permissionIds = (this.$refs.permissionTree as ElTree<number, ElementUiTreeVo>).getCheckedKeys();
    (this.$refs.roleUpdateForm as ElForm).validate(async valid => {
      if (valid) {
        const res = await RoleApi.update(this.roleUpdateVo)
        if (res.data.data) {
          this.roleUpdateDialogVisible = false
          this.goToPage(1, this.pageSize)
        }
      }
    })
  }

  createConfirm () {
    this.roleCreateVo.permissionIds = (this.$refs.permissionTree as ElTree<number, ElementUiTreeVo>).getCheckedKeys();
    (this.$refs.roleCreateForm as ElForm).validate(async valid => {
      if (valid) {
        const res = await RoleApi.create(this.roleCreateVo)
        if (res.data.data) {
          this.roleCreateDialogVisible = false
          this.goToPage(1, this.pageSize)
        }
      }
    })
  }

  toggleEnable () {
    console.log('.')
  }

  async handleCreateRole () {
    const res1 = await RoleApi.getAllSystemPermissions()
    this.permissionTree = res1.data.data
    this.roleCreateDialogVisible = true
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
.el-button-group , #pagination{
  margin-top: 1em;
}

#roleDetailForm .el-form-item{
  color: black !important;
}
</style>
