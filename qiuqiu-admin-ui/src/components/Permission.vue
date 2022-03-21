<template>
  <div>
    <!--操作栏-->
    <el-button-group>
      <el-button type="primary" @click="create" v-if="permissionExists(resources.PERMISSION,permissions.ADD)">新增
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
        label="权限名"
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
                     v-if="permissionExists(resources.PERMISSION,permissions.UPDATE)">
            编辑
          </el-button>
          <el-button
            size="small"
            type="text"
            @click.native.prevent="deleteConfirm(scope.$index, tableData)"
            v-if="permissionExists(resources.PERMISSION,permissions.DELETE)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--新增权限信息表单-->
    <el-dialog title="权限创建" :visible.sync="createDialogVisible">
      <el-form ref="createForm" :model="permissionCreateVo" :rules="rules">
        <el-form-item label="权限名:" :label-width="dialogFormLabelWidth" prop="name">
          <el-input v-model="permissionCreateVo.name"></el-input>
        </el-form-item>
        <el-form-item label="描述:" :label-width="dialogFormLabelWidth" prop="description">
          <el-input v-model="permissionCreateVo.description"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="createDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="createConfirm">确 定</el-button>
      </div>
    </el-dialog>

    <!--更新权限信息表单-->
    <el-dialog title="权限更新" :visible.sync="updateDialogVisible">
      <el-form ref="updateForm" :model="permissionUpdateVo" :rules="rules">
        <el-form-item label="权限名:" :label-width="dialogFormLabelWidth" prop="name">
          <el-input v-model="permissionUpdateVo.name" disabled></el-input>
        </el-form-item>
        <el-form-item label="描述:" :label-width="dialogFormLabelWidth" prop="description">
          <el-input v-model="permissionUpdateVo.description"></el-input>
        </el-form-item>
        <el-form-item label="是否有效:" :label-width="dialogFormLabelWidth" prop="enable">
          <el-switch
            v-model="permissionUpdateVo.enable"
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
import { PermissionCreateVo, PermissionUpdateVo, PermissionVo } from '@/api/vo/permission'
import { PermissionApi } from '@/api/permission'

@Component
export default class Permission extends BaseVue {
  private tableData: PermissionVo[] = []
  private loading = false
  private updateDialogVisible = false
  private createDialogVisible = false
  private dialogFormLabelWidth = '120px'
  private permissionUpdateVo = PermissionUpdateVo.newInstant()
  private permissionCreateVo = PermissionCreateVo.newInstant()

  private rules = {
    name: [
      { required: true, message: '请输入权限名', trigger: 'blur' }
    ],
    description: [
      { required: true, message: '请输入权限描述', trigger: 'blur' }
    ],
    uri: [
      { required: true, message: '请输入权限接口地址', trigger: 'blur' }
    ],
    enable: [
      { required: true, message: '请输入权限是否有效', trigger: 'blur' }
    ]
  }

  mounted () {
    this.goToPage()
  }

  async goToPage () {
    this.loading = true
    const res = await PermissionApi.page()
    this.tableData = res.data.data
    this.loading = false
  }

  deleteConfirm (index: number, rows: PermissionVo[]) {
    this.$confirm('此操作将永久删除该条数据, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(async () => {
      const permission = rows[index]
      const res = await PermissionApi.delete(permission.name)
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

  async update (row: PermissionVo) {
    this.permissionUpdateVo = row
    this.updateDialogVisible = true
  }

  updateConfirm () {
    (this.$refs.updateForm as ElForm).validate(async valid => {
      if (valid) {
        const res = await PermissionApi.update(this.permissionUpdateVo)
        if (res.data.data) {
          this.updateDialogVisible = false
          this.goToPage()
        }
      }
    })
  }

  async create () {
    this.permissionCreateVo = PermissionCreateVo.newInstant()
    this.createDialogVisible = true
  }

  createConfirm () {
    (this.$refs.createForm as ElForm).validate(async valid => {
      if (valid) {
        const res = await PermissionApi.create(this.permissionCreateVo)
        if (res.data.data) {
          this.createDialogVisible = false
          this.goToPage()
        }
      }
    })
  }

  toggleEnable () {
    console.log('.')
  }
}
</script>

<style lang="scss" scoped>
#content {
  font-size: 2em;
  height: 80vh;
}

.el-table {
  width: 100%;
  margin-top: 1em;
}

.el-button-group, #pagination, #searchBtn {
  margin-top: 1em;
}
</style>
