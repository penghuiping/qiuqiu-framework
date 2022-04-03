<template>
  <div style="height:auto;">
    <iframe :src="iframeSrc" ref="iframe" width="100%" height="auto" frameborder='0' scrolling='auto' id="bdIframe"></iframe>
  </div>
</template>

<script lang="ts">
import { Component } from 'vue-property-decorator'
import { BaseVue } from '@/BaseVue'

@Component
export default class SysMonitor extends BaseVue {
  private iframeSrc='http://localhost:3000/d/QUKIKSynz/jvm-micrometer?orgId=1&refresh=30s&kiosk=tv'

  mounted () {
    const oIframe = document.getElementById('bdIframe')
    const deviceHeight = document.documentElement.clientHeight
    if (oIframe) {
      oIframe.style.height = (Number(deviceHeight) - 140) + 'px' // 数字是页面布局高度差
    }
  }
}
</script>

<style lang="scss" scoped>
#content {
  font-size: 2em;
  height: 80vh;
}
</style>
