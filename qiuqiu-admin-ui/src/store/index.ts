import Vue from 'vue'
import Vuex from 'vuex'
import { WebSocket0 } from '@/utils/ws'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: '',
    user: {},
    userPermissions: new Map<string, Set<string>>(),
    ws: {}
  },
  mutations: {
    login (state, payload) {
      state.token = payload.token
    },
    logout (state) {
      state.token = ''
      sessionStorage.clear();
      (state.ws as WebSocket0).clear()
    },
    loginUserPermissions (state, payload) {
      state.userPermissions = payload
    }
  },
  actions: {},
  modules: {}
})
