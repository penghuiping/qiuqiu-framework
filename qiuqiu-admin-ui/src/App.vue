<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<style lang="scss">

</style>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'

@Component
export default class App extends Vue {
  mounted () {
    // 在页面加载时读取sessionStorage里的状态信息
    const token = sessionStorage.getItem('token')
    if (token) {
      this.$store.commit('login', {
        token: token
      })
    }

    // 在页面刷新时将vuex里的信息保存到sessionStorage里
    window.addEventListener('beforeunload', () => {
      sessionStorage.setItem('token', this.$store.state.token)
    })

    this.initWS()
  }

  initWS () {
    const ws = new WebSocket('ws://localhost:8081/qiuqiu_admin/websocket')
    ws.onopen = function () {
      console.log('ws connection open')
      setInterval(function () {
        ws.send(JSON.stringify({
          msgId: App.uuid(),
          action: 'ping'
        }))
      }, 5000)
    }

    ws.onmessage = function (e: MessageEvent) {
      console.log('ws receive message')
      const obj = JSON.parse(e.data)
      if (obj.action === 'request_auth_info') {
        console.log('发送submit_auth_info消息')
        ws.send(JSON.stringify({
          msgId: obj.msg_id,
          action: 'submit_auth_info',
          token: 'token_test',
          timestamp: new Date().getTime()
        }))
      } else if (obj.action === 'reply_auth_info') {
        console.log(e.data)
        ws.send(JSON.stringify({
          msgId: obj.msg_id,
          action: 'ack',
          replyAction: 'reply_auth_info',
          timestamp: new Date().getTime()
        }))
      } else if (obj.action === 'notify_answer_info') {
        console.log(e.data)
        ws.send(JSON.stringify({
          msgId: obj.msg_id,
          action: 'ack',
          replyAction: 'notify_answer_info',
          timestamp: new Date().getTime()
        }))
      }
    }

    ws.onclose = function (e) {
      console.log('ws connection close...')
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
