<template>
  <div>
    <!--搜索框-->
    <el-row justify="start" type="flex">
      <div class="searchInput">
        <el-input
          v-model="searchKey"
          clearable
          placeholder="请输入用户名">
        </el-input>
      </div>
      <el-button id="searchBtn" icon="el-icon-search" type="primary" @click="handleSearch">搜索</el-button>
    </el-row>
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
        width="50">
      </el-table-column>
      <el-table-column
        label="用户名"
        prop="username"
        width="150">
      </el-table-column>
      <el-table-column
        label="用户组"
        prop="groupName"
        width="150">
      </el-table-column>
      <el-table-column
        label="接口地址"
        prop="uri"
        width="250">
      </el-table-column>
      <el-table-column
        label="请求参数"
        prop="params"
        width="450">
      </el-table-column>
      <el-table-column
        label="创建时间"
        prop="createTime"
        width="0">
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
  </div>
</template>

<script lang="ts">
import { Component } from 'vue-property-decorator'
import { BaseVue } from '@/BaseVue'
import { AuditLogVo } from '@/api/vo/AuditLog'
import { AuditLogApi } from '@/api/auditlog'

@Component
export default class AuditLog extends BaseVue {
  private tableData: AuditLogVo[] = []
  private loading = false
  private hideOnSinglePage = false
  private currentPage = 1
  private pageSize = 5
  private total = 1
  private searchKey = ''

  mounted () {
    this.goToPage(this.searchKey, this.currentPage, this.pageSize)
  }

  // 跳去某页操作
  async goToPage (searchKey: string, pageNum: number, pageSize: number) {
    this.loading = true
    const res = await AuditLogApi.page(searchKey, pageNum, pageSize)
    this.loading = false
    this.tableData = res.data.data.data
    this.currentPage = res.data.data.currentPage
    this.total = res.data.data.total

    if ((this.total / this.pageSize) === 0) {
      this.hideOnSinglePage = true
    }
  }

  // 分页器每页大小改变时候的回调方法
  handleSizeChange (pageSize: number) {
    this.pageSize = pageSize
    this.goToPage(this.searchKey, this.currentPage, this.pageSize)
  }

  // 分页器当前页改变时候的回调方法
  handleCurrentChange (pageNum: number) {
    this.currentPage = pageNum
    this.goToPage(this.searchKey, this.currentPage, this.pageSize)
  }

  // 处理搜索按钮
  handleSearch () {
    this.currentPage = 1
    this.goToPage(this.searchKey, this.currentPage, this.pageSize)
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

.el-button-group, #pagination {
  margin-top: 1em;
}

#searchBtn {
  margin-left: 1em;
}
</style>
