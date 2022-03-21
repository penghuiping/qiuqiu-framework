<template>
  <div>
    <!--搜索框-->
    <el-row justify="start" type="flex">
      <div class="searchInput">
        <el-input
          v-model="searchKey"
          clearable
          placeholder="请输入key">
        </el-input>
      </div>
      <el-button id="searchBtn" icon="el-icon-search" type="primary" @click="handleSearch">搜索</el-button>
    </el-row>
    <!--操作栏-->
    <el-button-group>
      <el-button type="primary" @click="create" v-if="permissionExists(resources.DICT,permissions.ADD)">新增
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
        width="50">
      </el-table-column>
      <el-table-column
        label="键"
        prop="key"
        width="150">
      </el-table-column>
      <el-table-column
        label="值"
        prop="value"
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
                     v-if="permissionExists(resources.DICT,permissions.UPDATE)">
            编辑
          </el-button>
          <el-button
            size="small"
            type="text"
            @click.native.prevent="deleteConfirm(scope.$index, tableData)"
            v-if="permissionExists(resources.DICT,permissions.DELETE)">
            删除
          </el-button>
          <el-button
            size="small"
            type="text"
            @click.native.prevent="refreshConfirm(scope.$index, tableData)"
            v-if="permissionExists(resources.DICT,permissions.REFRESH)">
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

    <!--新增字典记录表单-->
    <el-dialog title="字典记录创建" :visible.sync="createDialogVisible">
      <el-form ref="createForm" :model="dictCreateVo" :rules="rules">
        <el-form-item label="键:" :label-width="dialogFormLabelWidth" prop="key">
          <el-input v-model="dictCreateVo.key"></el-input>
        </el-form-item>
        <el-form-item label="值:" :label-width="dialogFormLabelWidth" prop="value">
          <el-input v-model="dictCreateVo.value"></el-input>
        </el-form-item>
        <el-form-item label="描述:" :label-width="dialogFormLabelWidth" prop="description">
          <el-input v-model="dictCreateVo.description"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="createDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="createConfirm">确 定</el-button>
      </div>
    </el-dialog>

    <!--更新字典记录表单-->
    <el-dialog title="字典记录更新" :visible.sync="updateDialogVisible">
      <el-form ref="updateForm" :model="dictUpdateVo" :rules="rules">
        <el-form-item :label-width="dialogFormLabelWidth" label="id:" prop="id">
          <el-input v-model="dictUpdateVo.id" disabled></el-input>
        </el-form-item>
        <el-form-item label="键:" :label-width="dialogFormLabelWidth" prop="key">
          <el-input v-model="dictUpdateVo.key"></el-input>
        </el-form-item>
        <el-form-item label="值:" :label-width="dialogFormLabelWidth" prop="value">
          <el-input v-model="dictUpdateVo.value"></el-input>
        </el-form-item>
        <el-form-item label="描述:" :label-width="dialogFormLabelWidth" prop="description">
          <el-input v-model="dictUpdateVo.description"></el-input>
        </el-form-item>
        <el-form-item label="是否有效:" :label-width="dialogFormLabelWidth" prop="enable">
          <el-switch
            v-model="dictUpdateVo.enable"
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
import { DictCreateVo, DictVo } from '@/api/vo/dict'
import { DictApi } from '@/api/dict'

@Component
export default class Dict extends BaseVue {
  private tableData: DictVo[] = []
  private loading = false
  private updateDialogVisible = false
  private createDialogVisible = false
  private dialogFormLabelWidth = '120px'
  private dictUpdateVo = DictVo.newInstant()
  private dictCreateVo = DictCreateVo.newInstant()

  private hideOnSinglePage = false
  private currentPage = 1
  private pageSize = 5
  private total = 1
  private searchKey = ''

  private rules = {
    key: [
      { required: true, message: '请输入键', trigger: 'blur' }
    ],
    value: [
      { required: true, message: '请输入值', trigger: 'blur' }
    ],
    description: [
      { required: true, message: '请输入描述', trigger: 'blur' }
    ],
    enable: [
      { required: true, message: '请输入字典记录是否有效', trigger: 'blur' }
    ]
  }

  mounted () {
    this.goToPage('', this.currentPage, this.pageSize)
  }

  // 跳去某页操作
  async goToPage (key: string, pageNum: number, pageSize: number) {
    this.loading = true
    const res = await DictApi.page(key, pageNum, pageSize)
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

  deleteConfirm (index: number, rows: DictVo[]) {
    this.$confirm('此操作将永久删除该条数据, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(async () => {
      const dict = rows[index]
      const res = await DictApi.delete(dict.key)
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

  refreshConfirm (index: number, rows: DictVo[]) {
    this.$confirm('刷新此条数据, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(async () => {
      const dict = rows[index]
      const res = await DictApi.refresh(dict.key)
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

  update (row: DictVo) {
    this.dictUpdateVo = row
    this.updateDialogVisible = true
  }

  updateConfirm () {
    (this.$refs.updateForm as ElForm).validate(async valid => {
      if (valid) {
        const res = await DictApi.update(this.dictUpdateVo)
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
    this.dictCreateVo = DictCreateVo.newInstant()
    this.createDialogVisible = true
  }

  createConfirm () {
    (this.$refs.createForm as ElForm).validate(async valid => {
      if (valid) {
        const res = await DictApi.create(this.dictCreateVo)
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

  toggleEnable () {
    console.log('.')
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

.el-button-group, #pagination, #searchBtn {
  margin-top: 1em;
}

#searchBtn {
  margin-left: 1em;
}

.el-button-group {
  margin-top: 1em;
}
</style>
