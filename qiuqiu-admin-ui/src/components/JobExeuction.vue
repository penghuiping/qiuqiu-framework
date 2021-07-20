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
      <el-button type="primary" @click="create" v-if="permissionExists(resources.JOB_EXECUTION,permissions.ADD)">新增</el-button>
      <el-button type="primary" @click="refreshAll" v-if="permissionExists(resources.JOB_EXECUTION,permissions.REFRESH_ALL)">整体刷新</el-button>
      <el-button type="primary" @click="statistic" v-if="permissionExists(resources.JOB_EXECUTION,permissions.STATISTIC)">统计执行任务加载情况</el-button>
    </el-button-group>
    <!--数据表格-->
    <el-table
      v-loading="loading"
      element-loading-text="拼命加载中"
      element-loading-spinner="el-icon-loading"
      :data="tableData">
      <el-table-column
        label="执行id"
        prop="id"
        width="150">
      </el-table-column>
      <el-table-column
        label="cron表达式"
        prop="cron"
        width="150">
      </el-table-column>
      <el-table-column
        label="任务名"
        prop="jobName"
        width="150">
      </el-table-column>
      <el-table-column
        label="任务参数"
        prop="params"
        width="150">
      </el-table-column>
      <el-table-column
        label="载入定时器数"
        prop="timerLoadedNumber"
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
                     v-if="permissionExists(resources.JOB_EXECUTION,permissions.UPDATE)">
            编辑
          </el-button>
          <el-button
            size="small"
            type="text"
            @click.native.prevent="deleteConfirm(scope.$index, tableData)"
            v-if="permissionExists(resources.JOB_EXECUTION,permissions.DELETE)">
            删除
          </el-button>
          <el-button
            size="small"
            type="text"
            @click.native.prevent="refreshConfirm(scope.$index, tableData)"
            v-if="permissionExists(resources.JOB_EXECUTION,permissions.REFRESH)">
            刷新
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

    <!--执行计划创建-->
    <el-dialog title="执行计划创建" :visible.sync="createDialogVisible">
      <el-form ref="createForm" :model="jobExecutionCreateVo" :rules="rules">
        <el-form-item label="cron表达式:" :label-width="dialogFormLabelWidth" prop="cron">
          <el-input v-model="jobExecutionCreateVo.cron"></el-input>
        </el-form-item>
        <el-form-item label="任务参数:" :label-width="dialogFormLabelWidth" prop="params">
          <el-input v-model="jobExecutionCreateVo.params"></el-input>
        </el-form-item>
        <el-form-item label="任务:" :label-width="dialogFormLabelWidth" prop="jobId">
          <el-select v-model="jobExecutionCreateVo.jobId" placeholder="请选择任务">
            <el-option v-for="item in jobs" :key=item.id :label=item.description :value=item.id></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="createDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="createConfirm">确 定</el-button>
      </div>
    </el-dialog>

    <!--执行计划更新-->
    <el-dialog title="执行计划更新" :visible.sync="updateDialogVisible">
      <el-form ref="updateForm" :model="jobExecutionUpdateVo" :rules="rules">
        <el-form-item :label-width="dialogFormLabelWidth" label="id:" prop="id">
          <el-input v-model="jobExecutionUpdateVo.id" disabled></el-input>
        </el-form-item>
        <el-form-item label="cron表达式:" :label-width="dialogFormLabelWidth" prop="cron">
          <el-input v-model="jobExecutionUpdateVo.cron"></el-input>
        </el-form-item>
        <el-form-item label="任务参数:" :label-width="dialogFormLabelWidth" prop="params">
          <el-input v-model="jobExecutionUpdateVo.params"></el-input>
        </el-form-item>
        <el-form-item label="任务:" :label-width="dialogFormLabelWidth" prop="jobId">
          <el-input v-model="jobExecutionUpdateVo.jobId"></el-input>
        </el-form-item>
        <el-form-item label="是否有效:" :label-width="dialogFormLabelWidth" prop="enable">
          <el-switch
            v-model="jobExecutionUpdateVo.enable"
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
import { JobExecutionCreateVo, JobExecutionUpdateVo, JobExecutionVo, JobVo } from '@/api/vo/job'
import { JobApi } from '@/api/job'

@Component
export default class JobExecution extends BaseVue {
  private tableData: JobExecutionVo[] = []
  private loading = false
  private updateDialogVisible = false
  private createDialogVisible = false
  private dialogFormLabelWidth = '120px'
  private jobExecutionUpdateVo = JobExecutionUpdateVo.newInstant()
  private jobExecutionCreateVo = JobExecutionCreateVo.newInstant()
  private hideOnSinglePage = false
  private currentPage = 1
  private pageSize = 5
  private total = 1
  private searchKey = ''
  private jobs: JobVo[] = []

  private rules = {
    jobId: [
      { required: true, message: '请输入任务名', trigger: 'blur' }
    ],
    cron: [
      { required: true, message: '请输入cron表达式', trigger: 'blur' }
    ],
    enable: [
      { required: true, message: '请输入是否有效', trigger: 'blur' }
    ]
  }

  async mounted () {
    const res = await JobApi.findAll()
    this.jobs = res.data.data
    this.goToPage('', this.currentPage, this.pageSize)
  }

  // 跳去某页操作
  async goToPage (key: string, pageNum: number, pageSize: number) {
    this.loading = true
    const res = await JobApi.executionPage(key, pageNum, pageSize)
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

  deleteConfirm (index: number, rows: JobExecutionVo[]) {
    this.$confirm('此操作将永久删除该条数据, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(async () => {
      const executionVo = rows[index]
      const res = await JobApi.executionDelete(executionVo.id)
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

  statistic () {
    this.$confirm('统计执行任务加载情况, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(async () => {
      const res = await JobApi.statistic()
      if (res && res.data.data) {
        this.goToPage(this.searchKey, this.currentPage, this.pageSize)
        this.$message({
          type: 'success',
          message: '重新加载成功!'
        })
      } else {
        this.$message({
          type: 'info',
          message: '重新加载失败'
        })
      }
    }).catch(() => {
      this.$message({
        type: 'info',
        message: '已取消重新加载'
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
        this.goToPage(this.searchKey, this.currentPage, this.pageSize)
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

  refreshConfirm (index: number, rows: JobExecutionVo[]) {
    this.$confirm('刷新此条数据, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(async () => {
      const job = rows[index]
      const res = await JobApi.refresh(job.id)
      if (res && res.data.data) {
        this.goToPage(this.searchKey, this.currentPage, this.pageSize)
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

  update (row: JobExecutionVo) {
    this.jobExecutionUpdateVo = row
    this.updateDialogVisible = true
  }

  updateConfirm () {
    (this.$refs.updateForm as ElForm).validate(async valid => {
      if (valid) {
        const res = await JobApi.executionUpdate(this.jobExecutionUpdateVo)
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
    this.jobExecutionCreateVo = JobExecutionCreateVo.newInstant()
    this.createDialogVisible = true
  }

  createConfirm () {
    (this.$refs.createForm as ElForm).validate(async valid => {
      if (valid) {
        const res = await JobApi.executionCreate(this.jobExecutionCreateVo)
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
