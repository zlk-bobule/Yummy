// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import Vue from 'vue'
import App from './App'
import router from './router'
import axios from 'axios'

import 'vue-area-linkage/dist/index.css'; // v2 or higher
import VueAreaLinkage from 'vue-area-linkage';

import AMap from 'vue-amap';
import echarts from 'echarts'
Vue.prototype.$echarts = echarts
Vue.use(AMap);

// 初始化vue-amap
AMap.initAMapApiLoader({
  // 高德key
  key: 'a657ba14afb565ef11e98f3d5ceb89fc',
  // 插件集合 （插件按需引入）
  plugin: ['AMap.Scale', 'AMap.OverView', 'AMap.ToolBar', 'AMap.MapType', 'AMap.PlaceSearch', 'AMap.Geolocation'],
  uiVersion: '1.0.11' // 版本号
});


Vue.use(VueAreaLinkage)

Vue.use(ElementUI)
Vue.prototype.$ajax = axios

Vue.config.productionTip = false


/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})


