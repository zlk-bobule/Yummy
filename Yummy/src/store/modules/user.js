import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const state = {
  isLogin: false,
  id: 2,
  userType: 2,
  username: '游客',
  avatar: 'default_avatar.png'
}

const getters = {
  isLogin: state => state.isLogin,
  id: state => state.id,
  userType: state => state.userType,
  username: state => state.username,
  avatar: state => state.avatar
}

const actions = {}

const mutations = {
  login (state, payload) {
    state.isLogin = true
    state.id = payload.userId
    state.userType = payload.userType
    state.username = payload.username
    state.avatar = payload.avatar
  },
  logout (state) {
    state.isLogin = false
  },
  changeAvatar (state, avatar) {
    state.avatar = avatar
  },
  changeName (state,username) {
    state.username = username
  }
  // setId (state, id) {
  //   state.id = id
  // }
}

export default {
  state,
  getters,
  actions,
  mutations
}
