import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: '',
    user: {},
    userPermissions: new Set<string>()
  },
  mutations: {
    login (state, payload) {
      state.token = payload.token
    },
    logout (state) {
      state.token = ''
      sessionStorage.clear()
    },
    loginUserPermissions (state, payload) {
      state.userPermissions = payload
    }
  },
  actions: {},
  modules: {}
})
