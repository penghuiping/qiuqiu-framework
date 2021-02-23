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
  }
}
</script>
