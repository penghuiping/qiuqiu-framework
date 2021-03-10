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
                @click="() => append(data)" v-if="permissionExists(permissions.GROUP_ADD)">
                添加
              </el-button>
               <el-button
                 type="text"
                 size="mini"
                 @click="() => update(data)" v-if="permissionExists(permissions.GROUP_UPDATE) && node.value !== 'root'">
                修改
              </el-button>
              <el-button
                type="text"
                size="mini"
                @click="() => remove(node, data)" v-if="permissionExists(permissions.GROUP_DELETE)">
                删除
              </el-button>
            </span>
          </span>
        </el-tree>
      </div>
    </div>

  </div>
</template>

<script lang="ts">

import { Component } from 'vue-property-decorator'
import { BaseVue } from '@/BaseVue'
import { ElementUiTreeVo } from '@/api/vo'
import { GroupApi } from '@/api/group'
import { TreeNode } from 'element-ui/types/tree'

@Component
export default class Department extends BaseVue {
  private groupTree: ElementUiTreeVo[] = []
  private treeProps = {
    children: 'children',
    label: 'label'
  }

  private id = 1000

  async mounted () {
    const loading = this.showLoading()
    const res = await GroupApi.getAll()
    this.groupTree.push(res.data.data)
    loading.close()
  }

  append (data: ElementUiTreeVo) {
    const newChild = new ElementUiTreeVo(++this.id, this.id + '', 'test' + this.id, [])
    if (!data.children) {
      // 不存在子节点情况
      this.$set(data, 'children', [])
    }
    data.children.push(newChild)
  }

  update (data: ElementUiTreeVo) {
    console.log('update:', data)
  }

  remove (node: TreeNode<number, ElementUiTreeVo>, data: ElementUiTreeVo) {
    const parent = node.parent
    if (parent) {
      const children = parent.data.children || parent.data
      const index = children.findIndex((d: ElementUiTreeVo) => d.id === data.id)
      children.splice(index, 1)
    }
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
