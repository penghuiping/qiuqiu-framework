<template>
  <el-container>
    <el-aside>
      <el-menu :collapse="isCollapse" class="el-menu-vertical" default-active="1-4-1" @close="handleClose"
               @open="handleOpen">
        <el-menu-item index="1" @click="menuClick('index')">
          <i class="el-icon-s-home"></i>
          <span slot="title">首页</span>
        </el-menu-item>
        <el-submenu index="2" v-if="permissionExists(resources.USER,permissions.PAGE)
        || permissionExists(resources.ROLE,permissions.PAGE)
        || permissionExists(resources.RESOURCE,permissions.PAGE)
        || permissionExists(resources.PERMISSION,permissions.PAGE)
        || permissionExists(resources.GROUP,permissions.PAGE)">
          <template slot="title">
            <i class="el-icon-user"></i>
            <span slot="title">用户权限</span>
          </template>
          <el-menu-item index="2-1" @click="menuClick('user')" v-if="permissionExists(resources.USER,permissions.PAGE)">用户管理</el-menu-item>
          <el-menu-item index="2-2" @click="menuClick('role')" v-if="permissionExists(resources.ROLE,permissions.PAGE)">角色管理</el-menu-item>
          <el-menu-item index="2-3" @click="menuClick('resource')" v-if="permissionExists(resources.RESOURCE,permissions.PAGE)">资源管理</el-menu-item>
          <el-menu-item index="2-4" @click="menuClick('permission')" v-if="permissionExists(resources.PERMISSION,permissions.PAGE)">权限管理</el-menu-item>
          <el-menu-item index="2-5" @click="menuClick('group')" v-if="permissionExists(resources.GROUP,permissions.PAGE)">用户组管理</el-menu-item>
        </el-submenu>

        <el-submenu index="3" v-if="permissionExists(resources.AUDIT_LOG,permissions.PAGE)
        || permissionExists(resources.DICT,permissions.PAGE)
        || permissionExists(resources.SYS_MONITOR,permissions.PAGE)
        || permissionExists(resources.TRACING,permissions.PAGE)">
          <template slot="title">
            <i class="el-icon-s-tools"></i>
            <span slot="title">系统管理</span>
          </template>
          <el-menu-item index="3-1" @click="menuClick('auditLog')" v-if="permissionExists(resources.AUDIT_LOG,permissions.PAGE)">审计日志</el-menu-item>
          <el-menu-item index="3-2" @click="menuClick('dict')" v-if="permissionExists(resources.DICT,permissions.PAGE)">数据字典</el-menu-item>
          <el-menu-item index="3-3" @click="menuClick('sysMonitor')" v-if="permissionExists(resources.SYS_MONITOR,permissions.PAGE)">系统监控</el-menu-item>
          <el-menu-item index="3-4" @click="menuClick('tracing')" v-if="permissionExists(resources.TRACING,permissions.PAGE)">调用链监控</el-menu-item>
          <el-menu-item index="3-5" @click="menuClick('log')" v-if="permissionExists(resources.TRACING,permissions.PAGE)">集中日志</el-menu-item>
        </el-submenu>

        <el-submenu index="4" v-if="permissionExists(resources.JOB,permissions.PAGE)
                || permissionExists(resources.JOB_EXECUTION,permissions.PAGE)
                || permissionExists(resources.JOB_LOG,permissions.PAGE)
       ">
          <template slot="title">
            <i class="el-icon-timer"></i>
            <span slot="title">任务管理</span>
          </template>
          <el-menu-item index="4-1" @click="menuClick('job')" v-if="permissionExists(resources.JOB,permissions.PAGE)">任务列表</el-menu-item>
          <el-menu-item index="4-2" @click="menuClick('jobExecution')" v-if="permissionExists(resources.JOB_EXECUTION,permissions.PAGE)">执行列表</el-menu-item>
          <el-menu-item index="4-3" @click="menuClick('jobLog')" v-if="permissionExists(resources.JOB_LOG,permissions.PAGE)">执行日志</el-menu-item>
        </el-submenu>
        <el-menu-item index="5" @click="menuClick('rule')" v-if="permissionExists(resources.RULE,permissions.PAGE)">
          <i class="el-icon-menu"></i>
          <span slot="title">规则管理</span>
        </el-menu-item>
        <el-menu-item index="6" @click="menuClick('media')" v-if="permissionExists(resources.MEDIA,permissions.PAGE)">
          <i class="el-icon-menu"></i>
          <span slot="title">媒体管理</span>
        </el-menu-item>
        <el-menu-item index="7" disabled>
          <i class="el-icon-setting"></i>
          <span slot="title">其他</span>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header>
        <div id="collapseBtn" @click="toggleCollapse()">
          <el-icon id="collapseFold" name="s-fold"></el-icon>
          <el-icon id="collapseUnFold" name="s-unfold"></el-icon>
        </div>
        <span id="username">欢迎您！{{nickname}}</span>
        <el-dropdown>
          <i class="el-icon-setting"></i>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>
              <div @click="logout()">退出登入</div>
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-header>
      <el-main>
        <el-tabs v-model="editableTabsValue" type="card" closable @tab-remove="removeTab" @tab-click="clickTab">
          <el-tab-pane
            v-for="(item) in editableTabs"
            :key="item.name"
            :label="item.title"
            :name="item.name"
          >
            <IndexView v-if="item.key==='index'"/>
            <UserView v-if="item.key==='user'"/>
            <RoleView v-if="item.key==='role'"/>
            <GroupView v-if="item.key==='group'"/>
            <ResourceView v-if="item.key==='resource'"/>
            <PermissionView v-if="item.key==='permission'"/>
            <AuditLogView v-if="item.key==='auditLog'"/>
            <DictView v-if="item.key==='dict'"/>
            <SysMonitorView v-if="item.key==='sysMonitor'"/>
            <TracingView v-if="item.key==='tracing'"/>
            <LogView v-if="item.key==='log'"/>
            <JobView v-if="item.key==='job'"/>
            <JobLogView v-if="item.key==='jobLog'"/>
            <JobExecutionView v-if="item.key==='jobExecution'"/>
            <RuleView v-if="item.key==='rule'"/>
          </el-tab-pane>
        </el-tabs>
      </el-main>
    </el-container>
  </el-container>
</template>

<script lang="ts">
import { Component } from 'vue-property-decorator'
import { BaseVue } from '@/BaseVue'
import { UserApi } from '@/api/user'
import { Permission } from '@/permission'
import { WebSocket0 } from '@/ws/ws'
import { NotifyTextHandler } from '@/ws'

import IndexView from '@/components/Index.vue'
import UserView from '@/components/User.vue'
import RoleView from '@/components/Role.vue'
import GroupView from '@/components/Group.vue'
import ResourceView from '@/components/Resource.vue'
import PermissionView from '@/components/Permission.vue'
import AuditLogView from '@/components/AuditLog.vue'
import DictView from '@/components/Dict.vue'
import JobView from '@/components/Job.vue'
import JobLogView from '@/components/JobLog.vue'
import JobExecutionView from '@/components/JobExeuction.vue'
import RuleView from '@/components/Rule.vue'
import SysMonitorView from '@/components/SysMonitor.vue'
import TracingView from '@/components/Tracing.vue'
import LogView from '@/components/Log.vue'

class TabItem {
  title: string
  name: string
  key: string

  constructor (title: string, name: string, key: string) {
    this.title = title
    this.name = name
    this.key = key
  }
}

@Component({
  components: {
    TracingView,
    IndexView,
    UserView,
    RoleView,
    GroupView,
    ResourceView,
    PermissionView,
    AuditLogView,
    DictView,
    SysMonitorView,
    JobView,
    JobLogView,
    JobExecutionView,
    RuleView,
    LogView
  }
})
export default class Home extends BaseVue {
  private isCollapse = false;
  private editableTabsValue = '';
  private editableTabs: Array<TabItem>= new Array<TabItem>();
  private tabIndex = -1;
  private nickname = ''

  async mounted () {
    const res = await UserApi.info()
    this.nickname = res.data.data.nickname
    Permission.mapToLocalPermissions(res.data.data.resourcePermissions)
    this.isCollapse = true
    this.addTab('index', '首页')
    this.toggleCollapse()
    this.initWS()
  }

  addTab (key: string, title: string) {
    // 判断是否已经有相同key的tab
    let newTab: TabItem
    const tabs = this.editableTabs
    let index
    for (let i = 0; i < tabs.length; i++) {
      const tab = tabs[i]
      if (key === tab.key) {
        newTab = tab
        index = i
        break
      }
    }

    // 不存在的相同key的tab就可以新增
    newTab = new TabItem(title, '', key)
    const newTabName = ++this.tabIndex + ''
    newTab.name = newTabName
    if (index !== undefined && index > -1) {
      // 存在值,说明已经存在相同tab,直接替换
      this.editableTabs[index] = newTab
    } else {
      this.editableTabs.push(newTab)
    }
    this.editableTabsValue = newTabName
  }

  clickTab (target: TabItem) {
    const tabs = this.editableTabs
    let tab0: TabItem = new TabItem('', '', '')
    tabs.forEach((tab) => {
      if (tab.name === target.name) {
        tab0 = tab
      }
    })
    this.addTab(tab0.key, tab0.title)
  }

  removeTab (targetName: string) {
    const tabs = this.editableTabs
    let activeName = this.editableTabsValue
    if (activeName === targetName) {
      tabs.forEach((tab, index) => {
        if (tab.name === targetName) {
          const nextTab = tabs[index + 1] || tabs[index - 1]
          if (nextTab) {
            activeName = nextTab.name
          }
        }
      })
    }
    this.editableTabsValue = activeName
    this.editableTabs = tabs.filter(tab => tab.name !== targetName)
  }

  menuClick (key: string) {
    switch (key) {
      case 'index': {
        this.addTab('index', '首页')
        break
      }
      case 'user': {
        this.addTab('user', '用户管理')
        break
      }
      case 'role': {
        this.addTab('role', '角色管理')
        break
      }
      case 'resource': {
        this.addTab('resource', '资源管理')
        break
      }
      case 'permission': {
        this.addTab('permission', '权限管理')
        break
      }
      case 'group': {
        this.addTab('group', '用户组管理')
        break
      }
      case 'auditLog': {
        this.addTab('auditLog', '审计日志')
        break
      }
      case 'dict': {
        this.addTab('dict', '数据字典')
        break
      }
      case 'sysMonitor': {
        this.addTab('sysMonitor', '系统监控')
        break
      }
      case 'tracing': {
        this.addTab('tracing', '调用链监控')
        break
      }
      case 'log': {
        this.addTab('log', '集中日志')
        break
      }
      case 'job': {
        this.addTab('job', '任务列表')
        break
      }
      case 'jobLog': {
        this.addTab('jobLog', '执行日志')
        break
      }
      case 'jobExecution': {
        this.addTab('jobExecution', '执行列表')
        break
      }
      case 'media': {
        this.addTab('media', '媒体管理')
        break
      }
      case 'rule': {
        this.addTab('rule', '规则管理')
        break
      }
      case 'report': {
        this.addTab('report', '报表管理')
        break
      }
      default: {
        // this.addTab('index', '首页')
        break
      }
    }
  }

  handleOpen () {
    console.log('onOpen')
  }

  handleClose () {
    console.log('onClose')
  }

  async logout () {
    const res = await UserApi.logout()
    if (res.data.data) {
      this.$store.commit('logout')
      this.$router.push('/')
    }
  }

  toggleCollapse () {
    this.isCollapse = !this.isCollapse
    const element0 = document.getElementById('collapseFold')
    const element1 = document.getElementById('collapseUnFold')
    if (element0 != null && element1 != null) {
      if (this.isCollapse) {
        element0.setAttribute('style', 'display:none')
        element1.setAttribute('style', 'display:inline-block')
      } else {
        element0.setAttribute('style', 'display:inline-block')
        element1.setAttribute('style', 'display:none')
      }
    }
  }

  initWS () {
    const token = this.$store.state.token
    const url = 'ws://localhost:8081/qiuqiu_admin/websocket'
    const ws0 = new WebSocket0(token, url, 5000)
    ws0.registerHandler(new NotifyTextHandler(this))
    this.$store.state.ws = ws0
  }
}
</script>

<style lang="scss" scoped>
.el-container {
  height: 100vh;
  border: 0
}

.el-menu-vertical:not(.el-menu--collapse) {
  width: 200px;
  min-height: 600px;
}

.el-aside {
  background-color: gainsboro;
  width: auto !important;
  color: #333;
}

.el-header {
  background-color: dodgerblue;
  color: #333;
  line-height: 60px;
  text-align: right;
  font-size: 12px
}

.el-main {
  width: 100%;
}

#collapseBtn {
  float: left;
  display: inline-block;
  font-size: 2em;
  line-height: 50px !important;
  padding-top: 0 !important;
  padding-bottom: 0 !important;
  border: 0;
  margin-top: 5px;
}

.el-icon-setting {
  font-size: 1.5em;
  color: white;
}

#username {
  font-size: 1.5em;
  color: white;
  margin-right: 1em;
}

#collapseFold {
  color: white;
}

#collapseUnFold {
  color: white;
}
</style>
