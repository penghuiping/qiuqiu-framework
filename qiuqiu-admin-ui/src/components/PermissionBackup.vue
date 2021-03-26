<template>
  <div>
    <!--操作栏-->
    <el-button-group>
      <el-button type="primary" @click="create" v-if="permissionExists(permissions.PERMISSION_ADD)">新增
      </el-button>
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
        label="权限名"
        prop="name"
        width="150">
      </el-table-column>
      <el-table-column
        label="描述"
        prop="description"
        width="150">
      </el-table-column>
      <el-table-column
        label="权限接口地址"
        prop="uri"
        width="150">
      </el-table-column>
      <el-table-column
        label="是否有效"
        prop="enable"
        width="50">
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
                     v-if="permissionExists(permissions.PERMISSION_UPDATE)">
            编辑
          </el-button>
          <el-button
            size="small"
            type="text"
            @click.native.prevent="deleteConfirm(scope.$index, tableData)"
            v-if="permissionExists(permissions.PERMISSION_DELETE)">
            删除
          </el-button>
          <el-button
            size="small"
            type="text"
            @click.native.prevent="toggleEnable(scope.$index, tableData)"
            v-if="permissionExists(permissions.PERMISSION_UPDATE)">
            {{ scope.row.enable ? '使无效' : '使有效' }}
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
        <el-form-item label="uri:" :label-width="dialogFormLabelWidth" prop="uri">
          <el-input v-model="permissionCreateVo.uri"></el-input>
        </el-form-item>
        <el-form-item label="是否有效:" :label-width="dialogFormLabelWidth" prop="enable">
          <el-switch
            v-model="permissionCreateVo.enable"
            active-color="#13ce66"
            inactive-color="#ff4949">
          </el-switch>
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
        <el-form-item :label-width="dialogFormLabelWidth" label="id:" prop="id">
          <el-input v-model="permissionUpdateVo.id" disabled></el-input>
        </el-form-item>
        <el-form-item label="权限名:" :label-width="dialogFormLabelWidth" prop="name">
          <el-input v-model="permissionUpdateVo.name"></el-input>
        </el-form-item>
        <el-form-item label="描述:" :label-width="dialogFormLabelWidth" prop="description">
          <el-input v-model="permissionUpdateVo.description"></el-input>
        </el-form-item>
        <el-form-item label="接口地址:" :label-width="dialogFormLabelWidth" prop="uri">
          <el-input v-model="permissionUpdateVo.uri"></el-input>
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
import { PermissionVo } from '@/api/vo/permission'
import { PermissionApi } from '@/api/permission'

@Component
export default class Permission extends BaseVue {
  private tableData: PermissionVo[] = []
  private loading = false
  private updateDialogVisible = false
  private createDialogVisible = false
  private dialogFormLabelWidth = '120px'
  private permissionUpdateVo = PermissionVo.newInstant()
  private permissionCreateVo = PermissionVo.newInstant()

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

  deleteConfirm (index: number, rows: PermissionVo[]) {
    this.$confirm('此操作将永久删除该条数据, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(async () => {
      const permissionVo = rows[index]
      const res = await PermissionApi.delete(permissionVo.name)
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
    console.log('update:', row)
  }

  updateConfirm () {
    (this.$refs.updateForm as ElForm).validate(async valid => {
      if (valid) {
        console.log('update validate...')
      }
    })
  }

  async create () {
    console.log('createPermission..')
  }

  createConfirm () {
    (this.$refs.createForm as ElForm).validate(async valid => {
      if (valid) {
        console.log('create validate...')
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
</style>
