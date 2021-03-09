<template>
  <el-container>
    <el-aside>
      <el-menu :collapse="isCollapse" class="el-menu-vertical" default-active="1-4-1" @close="handleClose"
               @open="handleOpen">
        <el-menu-item index="1" @click="menuClick('')" v-if="permissionExists(permissions.HOME)">
          <i class="el-icon-s-home"></i>
          <span slot="title">首页</span>
        </el-menu-item>
        <el-submenu index="2" v-if="permissionExists(permissions.USER_LIST_SEARCH)
        || permissionExists(permissions.ROLE_LIST_SEARCH)
        || permissionExists(permissions.DEPARTMENT_LIST_SEARCH)">
          <template slot="title">
            <i class="el-icon-user"></i>
            <span slot="title">权限管理</span>
          </template>
          <el-menu-item index="2-1" @click="menuClick('user')" v-if="permissionExists(permissions.USER_LIST_SEARCH)">用户管理</el-menu-item>
          <el-menu-item index="2-2" @click="menuClick('role')" v-if="permissionExists(permissions.ROLE_LIST_SEARCH)">角色管理</el-menu-item>
          <el-menu-item index="2-3" @click="menuClick('department')" v-if="permissionExists(permissions.DEPARTMENT_LIST_SEARCH)">部门管理</el-menu-item>
        </el-submenu>
        <el-menu-item index="3" @click="menuClick('media')" v-if="permissionExists(permissions.MEDIA_LIST_SEARCH)">
          <i class="el-icon-menu"></i>
          <span slot="title">媒体管理</span>
        </el-menu-item>
        <el-menu-item index="4" @click="menuClick('report')" v-if="permissionExists(permissions.REPORT_LIST_SEARCH)">
          <i class="el-icon-document"></i>
          <span slot="title">报表</span>
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
            <router-view/>
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

@Component
export default class Home extends BaseVue {
  private isCollapse = false;
  private editableTabsValue = '';
  private editableTabs: Array<TabItem>= new Array<TabItem>();
  private tabIndex = -1;
  private nickname = ''

  data () {
    return {
      editableTabsValue: '1',
      editableTabs: [new TabItem('首页', '1', '')],
      tabIndex: 1
    }
  }

  addTab (key: string, title: string) {
    // 判断是否已经有相同key的tab
    let isExist = false
    let existTab: TabItem = new TabItem('', '', '')
    const tabs = this.editableTabs
    for (let i = 0; i < tabs.length; i++) {
      const tab = tabs[i]
      if (key === tab.key) {
        isExist = true
        existTab = tab
        break
      }
    }

    if (isExist && existTab) {
      // 已存在tab，这直接激活此tab
      this.editableTabsValue = existTab.name
      this.refreshTabContent()
      return
    }

    // 不存在的相同key的tab就可以新增
    const newTabName = ++this.tabIndex + ''
    this.editableTabs.push({
      title: title,
      name: newTabName,
      key: key
    })
    this.editableTabsValue = newTabName
    this.routePage('/home' + '/' + key)
  }

  clickTab (targetName: TabItem) {
    this.editableTabsValue = targetName.name
    this.refreshTabContent()
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
    this.refreshTabContent()
  }

  refreshTabContent () {
    const tabs = this.editableTabs
    const activeName = this.editableTabsValue
    tabs.forEach((tab, index) => {
      if (tab.name === activeName) {
        const key = tabs[index].key
        this.routePage('/home' + '/' + key)
      }
    })
  }

  async mounted () {
    const res = await UserApi.info()
    this.nickname = res.data.data.nickname
    Permission.mapToLocalPermissions(res.data.data.permissions)
    this.isCollapse = true
    this.refreshTabContent()
    this.toggleCollapse()

    this.initWS()
  }

  menuClick (key: string) {
    switch (key) {
      case 'user': {
        this.addTab('user', '用户管理')
        break
      }
      case 'role': {
        this.addTab('role', '角色管理')

        break
      }
      case 'department': {
        this.addTab('department', '部门管理')
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
        this.addTab('', '首页')
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

  logout () {
    this.$store.commit('logout')
    this.$router.push('/')
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
    const ws = new WebSocket('ws://localhost:8081/qiuqiu_admin/websocket')
    ws.onopen = function () {
      console.log('ws connection open')
      // 发送ws心跳包 5秒一次
      setInterval(function () {
        ws.send(JSON.stringify({
          // eslint-disable-next-line @typescript-eslint/camelcase
          msg_id: Home.uuid(),
          action: 'ping'
        }))
      }, 5000)
    }

    ws.onmessage = function (e: MessageEvent) {
      const obj = JSON.parse(e.data)
      console.log('ws receive message:{}', obj)
      if (obj.action === 'request_auth_info') {
        ws.send(JSON.stringify({
          // eslint-disable-next-line @typescript-eslint/camelcase
          msg_id: obj.msg_id,
          action: 'submit_auth_info',
          token: token,
          timestamp: new Date().getTime()
        }))
      } else if (obj.action === 'reply_auth_info') {
        ws.send(JSON.stringify({
          // eslint-disable-next-line @typescript-eslint/camelcase
          msg_id: obj.msg_id,
          action: 'ack',
          // eslint-disable-next-line @typescript-eslint/camelcase
          reply_action: 'reply_auth_info',
          timestamp: new Date().getTime()
        }))
      } else if (obj.action === 'notify_text') {
        Home.prototype.$message({
          type: 'success',
          message: obj.content
        })
      }
    }

    ws.onclose = function (e) {
      console.log('ws connection close...', e)
    }
    ws.onerror = function (e) {
      console.log('ws connection error:', e)
    }
  }

  static uuid () {
    const s: string[] = []
    const hexDigits = '0123456789abcdef'
    for (let i = 0; i < 36; i++) {
      s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1)
    }
    s[14] = '4' // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((parseInt(s[19]) & 0x3) | 0x8, 1) // bits 6-7 of the clock_seq_hi_and_reserved to 01
    s[8] = s[13] = s[18] = s[23] = '-'

    const uuid = s.join('')
    return uuid
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
