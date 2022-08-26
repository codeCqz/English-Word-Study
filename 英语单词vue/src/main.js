import Vue from 'vue'
import App from './App.vue'


import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css';
import VueRouter from 'vue-router'
import router from './router'
import store from './store'
import * as echarts from 'echarts';
import * as qiniu from "qiniu-js"

Vue.prototype.$echarts = echarts 


Vue.config.productionTip = false

Vue.use(VueRouter)




Vue.use(ElementUI);
new Vue({
  render: h => h(App),
  router,
  store,
  echarts,
  qiniu 
}).$mount('#app')
