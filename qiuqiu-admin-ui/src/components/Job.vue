<template>
  <div>
    <!--搜索框-->
    <el-row justify="start" type="flex">
      <div class="searchInput">
        <el-input
          v-model="searchKey"
          clearable
          placeholder="请输入任务名">
        </el-input>
      </div>
      <el-button id="searchBtn" icon="el-icon-search" type="primary" @click="handleSearch">搜索</el-button>
    </el-row>
    <!--操作栏-->
    <el-button-group>
      <el-button type="primary" @click="create" v-if="permissionExists(permissions.JOB_ADD)">新增</el-button>
      <el-button type="primary" @click="refreshAll" v-if="permissionExists(permissions.JOB_REFRESH_ALL)">整体刷新</el-button>
    </el-button-group>
    <!--数据表格-->
    <el-table
      v-loading="loading"
      element-loading-text="拼命加载中"
      element-loading-spinner="el-icon-loading"
      :data="tableData">
      <el-table-column
        label="任务名"
        prop="name"
        width="150">
      </el-table-column>
      <el-table-column
        label="描述"
        prop="description"
        width="150">
      </el-table-column>
      <el-table-column
        label="cron表达式"
        prop="cron"
        width="150">
      </el-table-column>
      <el-table-column
        label="对应代码类"
        prop="className"
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
                     v-if="permissionExists(permissions.JOB_UPDATE)">
            编辑
          </el-button>
          <el-button
            size="small"
            type="text"
            @click.native.prevent="deleteConfirm(scope.$index, tableData)"
            v-if="permissionExists(permissions.JOB_DELETE)">
            删除
          </el-button>
          <el-button
            size="small"
            type="text"
            @click.native.prevent="refreshConfirm(scope.$index, tableData)"
            v-if="permissionExists(permissions.JOB_REFRESH)">
            刷新
          </el-button>
          <el-button
            size="small"
            type="text"
            @click.native.prevent="toggleEnable(scope.$index, tableData)"
            v-if="permissionExists(permissions.JOB_UPDATE)">
            {{ scope.row.enable ? '使无效' : '使有效' }}
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

    <!--定时任务创建-->
    <el-dialog title="定时任务创建" :visible.sync="createDialogVisible">
      <el-form ref="createForm" :model="jobCreateVo" :rules="rules">
        <el-form-item label="任务名:" :label-width="dialogFormLabelWidth" prop="name">
          <el-input v-model="jobCreateVo.name"></el-input>
        </el-form-item>
        <el-form-item label="描述:" :label-width="dialogFormLabelWidth" prop="description">
          <el-input v-model="jobCreateVo.description"></el-input>
        </el-form-item>
        <el-form-item label="cron表达式:" :label-width="dialogFormLabelWidth" prop="cron">
          <el-input v-model="jobCreateVo.cron"></el-input>
        </el-form-item>
        <el-form-item label="对应代码类:" :label-width="dialogFormLabelWidth" prop="className">
          <el-input v-model="jobCreateVo.className"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="createDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="createConfirm">确 定</el-button>
      </div>
    </el-dialog>

    <!--定时任务更新-->
    <el-dialog title="定时任务更新" :visible.sync="updateDialogVisible">
      <el-form ref="updateForm" :model="jobUpdateVo" :rules="rules">
        <el-form-item :label-width="dialogFormLabelWidth" label="id:" prop="id">
          <el-input v-model="jobUpdateVo.id" disabled></el-input>
        </el-form-item>
        <el-form-item label="任务名:" :label-width="dialogFormLabelWidth" prop="name">
          <el-input v-model="jobUpdateVo.name"></el-input>
        </el-form-item>
        <el-form-item label="描述:" :label-width="dialogFormLabelWidth" prop="description">
          <el-input v-model="jobUpdateVo.description"></el-input>
        </el-form-item>
        <el-form-item label="cron表达式:" :label-width="dialogFormLabelWidth" prop="cron">
          <el-input v-model="jobUpdateVo.cron"></el-input>
        </el-form-item>
        <el-form-item label="对应代码类:" :label-width="dialogFormLabelWidth" prop="className">
          <el-input v-model="jobUpdateVo.className"></el-input>
        </el-form-item>
        <el-form-item label="是否有效:" :label-width="dialogFormLabelWidth" prop="enable">
          <el-switch
            v-model="jobUpdateVo.enable"
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
import { JobCreateVo, JobVo } from '@/api/vo/job'
import { JobApi } from '@/api/job'

@Component
export default class Job extends BaseVue {
  private tableData: JobVo[] = []
  private loading = false
  private updateDialogVisible = false
  private createDialogVisible = false
  private dialogFormLabelWidth = '120px'
  private jobUpdateVo = JobVo.newInstant()
  private jobCreateVo = JobCreateVo.newInstant()
  private hideOnSinglePage = false
  private currentPage = 1
  private pageSize = 5
  private total = 1
  private searchKey = ''

  private rules = {
    name: [
      { required: true, message: '请输入任务名', trigger: 'blur' }
    ],
    cron: [
      { required: true, message: '请输入cron表达式', trigger: 'blur' }
    ],
    description: [
      { required: true, message: '请输入描述', trigger: 'blur' }
    ],
    className: [
      { required: true, message: '请输入任务代码对应的类名', trigger: 'blur' }
    ],
    enable: [
      { required: true, message: '请输入是否有效', trigger: 'blur' }
    ]
  }

  mounted () {
    this.goToPage('', this.currentPage, this.pageSize)
  }

  // 跳去某页操作
  async goToPage (key: string, pageNum: number, pageSize: number) {
    this.loading = true
    const res = await JobApi.page(key, pageNum, pageSize)
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

  deleteConfirm (index: number, rows: JobVo[]) {
    this.$confirm('此操作将永久删除该条数据, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(async () => {
      const job = rows[index]
      const res = await JobApi.delete(job.id)
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

  refreshAll () {
    this.$confirm('刷新全部任务状态, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(async () => {
      const res = await JobApi.refreshAll()
      if (res && res.data.data) {
        this.$message({
          type: 'success',
          message: '刷新成功!'
        })
      } else {
        this.$message({
          type: 'info',
          message: '刷新失败'
        })
      }
    }).catch(() => {
      this.$message({
        type: 'info',
        message: '已取消刷新'
      })
    })
  }

  refreshConfirm (index: number, rows: JobVo[]) {
    this.$confirm('刷新此条数据, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(async () => {
      const job = rows[index]
      const res = await JobApi.refresh(job.id)
      if (res && res.data.data) {
        this.$message({
          type: 'success',
          message: '刷新成功!'
        })
      } else {
        this.$message({
          type: 'info',
          message: '刷新失败'
        })
      }
    }).catch(() => {
      this.$message({
        type: 'info',
        message: '已取消刷新'
      })
    })
  }

  update (row: JobVo) {
    this.jobUpdateVo = row
    this.updateDialogVisible = true
  }

  updateConfirm () {
    (this.$refs.updateForm as ElForm).validate(async valid => {
      if (valid) {
        const res = await JobApi.update(this.jobUpdateVo)
        if (res && res.data.data) {
          this.goToPage(this.searchKey, 1, this.pageSize)
          this.$message({
            type: 'success',
            message: '更新成功!'
          })
        } else {
          this.$message({
            type: 'info',
            message: '更新失败'
          })
        }
        this.updateDialogVisible = false
      }
    })
  }

  create () {
    this.jobCreateVo = JobCreateVo.newInstant()
    this.createDialogVisible = true
  }

  createConfirm () {
    (this.$refs.createForm as ElForm).validate(async valid => {
      if (valid) {
        const res = await JobApi.create(this.jobCreateVo)
        if (res && res.data.data) {
          this.goToPage(this.searchKey, 1, this.pageSize)
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

  // 处理搜索按钮
  handleSearch () {
    this.currentPage = 1
    this.goToPage(this.searchKey, this.currentPage, this.pageSize)
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

#searchBtn {
  margin-left: 1em;
}

.el-button-group {
  margin-top: 1em;
}
</style>
