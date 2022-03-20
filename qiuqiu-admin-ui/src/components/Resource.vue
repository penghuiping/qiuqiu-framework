<template>
  <div>
    <!--操作栏-->
    <el-button-group>
      <el-button type="primary" @click="create" v-if="permissionExists(resources.RESOURCE,permissions.ADD)">新增
      </el-button>
    </el-button-group>
    <!--数据表格-->
    <el-table
      v-loading="loading"
      element-loading-text="拼命加载中"
      element-loading-spinner="el-icon-loading"
      border
      :data="tableData">
      <el-table-column
        label="资源名"
        prop="name"
        width="150">
      </el-table-column>
      <el-table-column
        label="描述"
        prop="description"
        width="0">
      </el-table-column>
      <el-table-column
        label="是否有效"
        prop="enable"
        width="80">
        <template slot-scope="scope">
          <span>{{ scope.row.enable ? '有效' : '无效' }}</span>
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        width="200">
        <template slot-scope="scope">
          <el-button @click="update(scope.row)" type="text" size="small"
                     v-if="permissionExists(resources.RESOURCE,permissions.UPDATE)">
            编辑
          </el-button>
          <el-button
            size="small"
            type="text"
            @click.native.prevent="deleteConfirm(scope.$index, tableData)"
            v-if="permissionExists(resources.RESOURCE,permissions.DELETE)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--新增资源信息表单-->
    <el-dialog title="资源创建" :visible.sync="createDialogVisible">
      <el-form ref="createForm" :model="resourceCreateVo" :rules="rules">
        <el-form-item label="资源名:" :label-width="dialogFormLabelWidth" prop="name">
          <el-input v-model="resourceCreateVo.name"></el-input>
        </el-form-item>
        <el-form-item label="描述:" :label-width="dialogFormLabelWidth" prop="description">
          <el-input v-model="resourceCreateVo.description"></el-input>
        </el-form-item>

        <el-form-item label="对应权限:" :label-width="dialogFormLabelWidth" prop="permissions">
          <el-select v-model="resourceCreateVo.permissions" multiple placeholder="请选择资源对应权限">
            <el-option v-for="item in permission0s" :key=item.name :label=item.description :value=item.name></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="createDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="createConfirm">确 定</el-button>
      </div>
    </el-dialog>

    <!--更新资源信息表单-->
    <el-dialog title="资源更新" :visible.sync="updateDialogVisible">
      <el-form ref="updateForm" :model="resourceUpdateVo" :rules="rules">
        <el-form-item label="资源名:" :label-width="dialogFormLabelWidth" prop="name">
          <el-input v-model="resourceUpdateVo.name" disabled></el-input>
        </el-form-item>
        <el-form-item label="描述:" :label-width="dialogFormLabelWidth" prop="description">
          <el-input v-model="resourceUpdateVo.description"></el-input>
        </el-form-item>
        <el-form-item label="对应权限:" :label-width="dialogFormLabelWidth" prop="permissions">
          <el-select v-model="resourceUpdateVo.permissions" multiple placeholder="请选择资源对应权限">
            <el-option v-for="item in permission0s" :key=item.name :label=item.description :value=item.name></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否有效:" :label-width="dialogFormLabelWidth" prop="enable">
          <el-switch
            v-model="resourceUpdateVo.enable"
            active-color="#13ce66"
            inactive-color="#ff4949">
          </el-switch>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="updateDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateConfirm">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script lang="ts">
import { Component } from 'vue-property-decorator'
import { BaseVue } from '@/BaseVue'
import { ElForm } from 'element-ui/types/form'
import { ResourceCreateVo, ResourceUpdateVo, ResourceVo } from '@/api/vo/resouce'
import { ResourceApi } from '@/api/resource'
import { PermissionVo } from '@/api/vo/permission'
import { PermissionApi } from '@/api/permission'

@Component
export default class Resource extends BaseVue {
  private tableData: ResourceVo[] = []
  private loading = false
  private updateDialogVisible = false
  private createDialogVisible = false
  private dialogFormLabelWidth = '120px'
  private resourceUpdateVo = ResourceUpdateVo.newInstant()
  private resourceCreateVo = ResourceCreateVo.newInstant()
  private permission0s: PermissionVo[] = []

  private rules = {
    name: [
      { required: true, message: '请输入资源名', trigger: 'blur' }
    ],
    description: [
      { required: true, message: '请输入资源描述', trigger: 'blur' }
    ],
    uri: [
      { required: true, message: '请输入资源接口地址', trigger: 'blur' }
    ],
    permissions: [
      { required: true, message: '请输入资源对应的权限', trigger: 'blur' }
    ],
    enable: [
      { required: true, message: '请输入资源是否有效', trigger: 'blur' }
    ]
  }

  mounted () {
    this.gotoPage()
  }

  async gotoPage () {
    this.loading = true
    const res = await ResourceApi.page()
    this.tableData = res.data.data
    const res0 = await PermissionApi.getAll()
    this.permission0s = res0.data.data
    this.loading = false
  }

  deleteConfirm (index: number, rows: ResourceVo[]) {
    this.$confirm('此操作将永久删除该条数据, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(async () => {
      const resourceVo = rows[index]
      const res = await ResourceApi.delete(resourceVo.name)
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

  async update (row: ResourceVo) {
    console.log('update:', row)
    const res = await ResourceApi.detail(row.name)
    if (res && res.data.data) {
      this.resourceUpdateVo = res.data.data
      this.updateDialogVisible = true
    }
  }

  updateConfirm () {
    (this.$refs.updateForm as ElForm).validate(async valid => {
      if (valid) {
        const res = await ResourceApi.update(this.resourceUpdateVo)
        if (res && res.data.data) {
          this.gotoPage()
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
        this.updateDialogVisible = false
      }
    })
  }

  create () {
    this.createDialogVisible = true
  }

  createConfirm () {
    (this.$refs.createForm as ElForm).validate(async valid => {
      if (valid) {
        const res = await ResourceApi.create(this.resourceCreateVo)
        if (res && res.data.data) {
          this.gotoPage()
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
        this.createDialogVisible = false
      }
    })
  }
}
</script>

<style lang="scss" scoped>
#content {
  font-size: 2em;
  height: 80vh;
}
</style>
