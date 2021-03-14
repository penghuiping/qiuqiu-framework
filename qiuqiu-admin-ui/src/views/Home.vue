<template>
  <el-container>
    <el-aside>
      <el-menu :collapse="isCollapse" class="el-menu-vertical" default-active="1-4-1" @close="handleClose"
               @open="handleOpen">
        <el-menu-item index="1" @click="menuClick('index')" v-if="permissionExists(permissions.HOME)">
          <i class="el-icon-s-home"></i>
          <span slot="title">首页</span>
        </el-menu-item>
        <el-submenu index="2" v-if="permissionExists(permissions.USER_LIST_SEARCH)
        || permissionExists(permissions.ROLE_LIST_SEARCH)
        || permissionExists(permissions.PERMISSION_LIST_SEARCH)
        || permissionExists(permissions.GROUP_LIST_SEARCH)">
          <template slot="title">
            <i class="el-icon-user"></i>
            <span slot="title">权限管理</span>
          </template>
          <el-menu-item index="2-1" @click="menuClick('user')" v-if="permissionExists(permissions.USER_LIST_SEARCH)">用户管理</el-menu-item>
          <el-menu-item index="2-2" @click="menuClick('role')" v-if="permissionExists(permissions.ROLE_LIST_SEARCH)">角色管理</el-menu-item>
          <el-menu-item index="2-3" @click="menuClick('permission')" v-if="permissionExists(permissions.PERMISSION_LIST_SEARCH)">权限管理</el-menu-item>
          <el-menu-item index="2-4" @click="menuClick('group')" v-if="permissionExists(permissions.GROUP_LIST_SEARCH)">用户组管理</el-menu-item>
        </el-submenu>

        <el-submenu index="3" v-if="permissionExists(permissions.AUDIT_LOG_LIST_SEARCH)
        || permissionExists(permissions.REPORT_LIST_SEARCH)
        || permissionExists(permissions.JOB_LIST_SEARCH)
        || permissionExists(permissions.DICT_LIST_SEARCH)">
          <template slot="title">
            <i class="el-icon-s-tools"></i>
            <span slot="title">系统管理</span>
          </template>
          <el-menu-item index="3-1" @click="menuClick('auditLog')" v-if="permissionExists(permissions.AUDIT_LOG_LIST_SEARCH)">审计日志</el-menu-item>
          <el-menu-item index="3-2" @click="menuClick('dict')" v-if="permissionExists(permissions.DICT_LIST_SEARCH)">数据字典</el-menu-item>
          <el-menu-item index="3-3" @click="menuClick('job')" v-if="permissionExists(permissions.JOB_LIST_SEARCH)">定时任务</el-menu-item>
          <el-menu-item index="3-4" @click="menuClick('report')" v-if="permissionExists(permissions.REPORT_LIST_SEARCH)">表报</el-menu-item>
        </el-submenu>
        <el-menu-item index="4" @click="menuClick('media')" v-if="permissionExists(permissions.MEDIA_LIST_SEARCH)">
          <i class="el-icon-menu"></i>
          <span slot="title">媒体管理</span>
        </el-menu-item>
        <el-menu-item index="5" disabled>
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
            <PermissionView v-if="item.key==='permission'"/>
            <AuditLogView v-if="item.key==='auditLog'"/>
            <DictView v-if="item.key==='dict'"/>
            <JobView v-if="item.key==='job'"/>
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
import { WebSocket0 } from '@/utils/ws'
import { NotifyTextHandler } from '@/ws'

import IndexView from '@/components/Index.vue'
import UserView from '@/components/User.vue'
import RoleView from '@/components/Role.vue'
import GroupView from '@/components/Group.vue'
import PermissionView from '@/components/Permission.vue'
import AuditLogView from '@/components/AuditLog.vue'
import DictView from '@/components/Dict.vue'
import JobView from '@/components/Job.vue'

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
    IndexView,
    UserView,
    RoleView,
    GroupView,
    PermissionView,
    AuditLogView,
    DictView,
    JobView
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
    Permission.mapToLocalPermissions(res.data.data.permissions)
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
      case 'job': {
        this.addTab('job', '定时任务')
        break
      }
      case 'media': {
        this.addTab('media', '媒体管理')
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
