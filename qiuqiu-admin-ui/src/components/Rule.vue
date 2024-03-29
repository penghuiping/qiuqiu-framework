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
        fixed
        label="ID"
        prop="id"
        width="20">
      </el-table-column>
      <el-table-column
        label="权限名"
        prop="name"
        width="20">
      </el-table-column>
      <el-table-column
        label="描述"
        prop="description"
        width="20">
      </el-table-column>
      <el-table-column
        label="权限接口地址"
        prop="uri"
        width="20">
      </el-table-column>
      <el-table-column
        label="是否有效"
        prop="enable"
        width="20">
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
          <el-button
            size="small"
            type="text"
            @click.native.prevent="toggleEnable(scope.$index, tableData)"
            v-if="permissionExists(resources.PERMISSION,permissions.UPDATE)">
            {{ scope.row.enable ? '使无效' : '使有效' }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--新增权限信息表单-->
    <el-dialog title="权限创建" :visible.sync="createDialogVisible" :fullscreen="true">
      <iframe id="dmn" src="dmn/index.html" frameborder="0" scrolling="no"></iframe>
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
    this.createDialogVisible = true
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

#dmn {
  width: 100%;
  height:85vh;
}
</style>
