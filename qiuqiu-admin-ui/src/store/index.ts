import Vue from 'vue'
import Vuex from 'vuex'
import { WebSocket0 } from '@/ws/ws'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: '',
    user: {},
    userPermissions: new Map<string, Set<string>>(),
    ws: {},
    config: []
  },
  mutations: {
    login (state, payload) {
      state.token = payload.token
    },
    logout (state) {
      state.token = ''
      sessionStorage.clear()
      if (state.ws) {
        (state.ws as WebSocket0).clear()
      }
    },
    loginUserPermissions (state, payload) {
      state.userPermissions = payload
    },
    setInitConfig (state, payload) {
      state.config = payload
    }
  },
  actions: {},
  modules: {}
})
