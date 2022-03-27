<template>
  <div style="height:auto;">
    <iframe :src="iframeSrc" ref="iframe" width="100%" height="auto" frameborder='0' scrolling='auto' id="logIFrame"></iframe>
  </div>
</template>

<script lang="ts">
import { Component } from 'vue-property-decorator'
import { BaseVue } from '@/BaseVue'

@Component
export default class Tracing extends BaseVue {
  private iframeSrc='http://localhost:5601/goto/1768dac7a3341d76b56a7d602f8469e8'

  mounted () {
    const oIframe = document.getElementById('logIFrame')
    const deviceHeight = document.documentElement.clientHeight
    oIframe.style.height = (Number(deviceHeight) - 140) + 'px' // 数字是页面布局高度差
  }
}
</script>

<style lang="scss" scoped>
#content {
  font-size: 2em;
  height: 80vh;
}
</style>
