<template>
  <div>系统监控</div>
</template>

<script lang="ts">
import { Component } from 'vue-property-decorator'
import { BaseVue } from '@/BaseVue'

@Component
export default class SysMonitor extends BaseVue {
}
</script>

<style lang="scss" scoped>
#content {
  font-size: 2em;
  height: 80vh;
}
</style>
