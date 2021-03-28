<template>
  <div>
    <div id="container">
      <div id="content">
        <el-tree node-key="id" :data="groupTree"
                 :props="treeProps"
                 :default-expand-all="true"
                 :expand-on-click-node="false">
          <span class="custom-tree-node" slot-scope="{ node, data }">
            <span>{{ node.label }}</span>
            <span>
              <el-button
                type="text"
                size="mini"
                @click="() => create(data)" v-if="permissionExists(resources.GROUP,permissions.ADD)">
                添加
              </el-button>
               <el-button
                 type="text"
                 size="mini"
                 @click="() => update(data)" v-if="permissionExists(resources.GROUP,permissions.UPDATE) && node.value !== 'root'">
                修改
              </el-button>
              <el-button
                type="text"
                size="mini"
                @click="() => remove(node, data)" v-if="permissionExists(resources.GROUP,permissions.DELETE)">
                删除
              </el-button>
            </span>
          </span>
        </el-tree>
      </div>
    </div>
    <!--新增用户组信息表单-->
    <el-dialog title="用户组创建" :visible.sync="createDialogVisible">
      <el-form ref="createForm" :model="groupCreateVo" :rules="rules">
        <el-form-item label="组名:" :label-width="dialogFormLabelWidth" prop="name">
          <el-input v-model="groupCreateVo.name"></el-input>
        </el-form-item>
        <el-form-item label="描述:" :label-width="dialogFormLabelWidth" prop="description">
          <el-input v-model="groupCreateVo.description"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="createDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="createConfirm">确 定</el-button>
      </div>
    </el-dialog>
    <!--更新用户组信息表单-->
    <el-dialog title="用户组更新" :visible.sync="updateDialogVisible">
      <el-form ref="updateForm" :model="groupUpdateVo" :rules="rules">
        <el-form-item label="组名:" :label-width="dialogFormLabelWidth" prop="name">
          <el-input v-model="groupUpdateVo.name" disabled></el-input>
        </el-form-item>
        <el-form-item label="描述:" :label-width="dialogFormLabelWidth" prop="description">
          <el-input v-model="groupUpdateVo.description"></el-input>
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
import { ElementUiTreeVo } from '@/api/vo'
import { GroupApi } from '@/api/group'
import { TreeNode } from 'element-ui/types/tree'
import { GroupCreateVo, GroupUpdateVo } from '@/api/vo/group'
import { ElForm } from 'element-ui/types/form'

@Component
export default class Department extends BaseVue {
  private groupTree: ElementUiTreeVo[] = []
  private createDialogVisible = false
  private updateDialogVisible = false
  private dialogFormLabelWidth = '120px'
  private groupCreateVo = GroupCreateVo.newInstant()
  private groupUpdateVo = GroupUpdateVo.newInstant()
  private treeProps = {
    children: 'children',
    label: 'label'
  }

  private parentNode = new ElementUiTreeVo(-1, '', '', [])

  private rules = {
    name: [
      { required: true, message: '请输入组名', trigger: 'blur' }
    ],
    description: [
      { required: true, message: '请输入组描述', trigger: 'blur' }
    ]
  }

  private id = 1000

  mounted () {
    this.reload()
  }

  async reload () {
    const loading = this.showLoading()
    const res = await GroupApi.getAll()
    this.groupTree = []
    this.groupTree.push(res.data.data)
    loading.close()
  }

  create (data: ElementUiTreeVo) {
    this.parentNode = data
    this.createDialogVisible = true
  }

  async createConfirm () {
    this.groupCreateVo.parentId = this.parentNode.id;
    (this.$refs.createForm as ElForm).validate(async valid => {
      if (valid) {
        const res0 = await GroupApi.create(this.groupCreateVo)
        if (res0.data.data) {
          this.createDialogVisible = false
          this.reload()
        }
      }
    })
  }

  update (data: ElementUiTreeVo) {
    this.groupUpdateVo.name = data.value
    this.groupUpdateVo.description = data.label
    this.groupUpdateVo.id = data.id
    this.updateDialogVisible = true
  }

  updateConfirm () {
    (this.$refs.updateForm as ElForm).validate(async valid => {
      if (valid) {
        const res0 = await GroupApi.update(this.groupUpdateVo)
        if (res0.data.data) {
          this.updateDialogVisible = false
          this.reload()
        }
      }
    })
  }

  remove (node: TreeNode<number, ElementUiTreeVo>, data: ElementUiTreeVo) {
    this.$confirm('此操作将永久删除该条数据, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(async () => {
      const res = await GroupApi.delete(data.id)
      if (res && res.data.data) {
        this.reload()
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
}

</script>

<style lang="scss" scoped>
  #container {
    display: flex;
    flex-direction: row;
    justify-content: center;
  }

  #content {
    margin-top: 2em;
    width: 20vw;
  }

  .custom-tree-node {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 14px;
    padding-right: 8px;
  }
</style>
