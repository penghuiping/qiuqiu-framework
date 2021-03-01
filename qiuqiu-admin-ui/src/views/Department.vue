<template>
  <div>
    <!--操作栏-->
    <el-button-group>
      <el-button type="primary" v-if="permissionExists(permissions.USER_ADD)">编辑</el-button>
    </el-button-group>
    <el-row id="content" justify="left"   type="flex">
      <el-tree node-key="id" :data="departmentTree" :props="treeProps" :default-expand-all="true"></el-tree>
    </el-row>
  </div>
</template>

<script lang="ts">

import { Component } from 'vue-property-decorator'
import { BaseVue } from '@/BaseVue'
import { ElementUiTreeVo } from '@/api/vo'
import { DepartmentApi } from '@/api/department'

@Component
export default class Department extends BaseVue {
  private departmentTree: ElementUiTreeVo[] = []
  private treeProps = {
    children: 'children',
    label: 'label'
  }

  async mounted () {
    const loading = this.showLoading()
    const res = await DepartmentApi.getAll()
    this.departmentTree.push(res.data.returnObject)
    loading.close()
  }
}

</script>

<style lang="scss">
  #content {
    margin-top: 1em;
  }
</style>
