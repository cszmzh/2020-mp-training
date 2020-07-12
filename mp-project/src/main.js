import Vue from 'vue'
import store from './store/store'
import App from './App'

import '../static/colorui/animation.wxss'
import '../static/colorui/main.wxss'
import '../static/colorui/icon.wxss'

Vue.config.productionTip = false
App.mpType = 'app'

//将store对象放置在vue原型上，每个实例都可以使用
Vue.prototype.$store=store

//使用flyio
var Fly=require("flyio/dist/npm/wx")
let fly = new Fly
Vue.prototype.$fly = fly

const app = new Vue(App)
//挂载应用
app.$mount()

//挂载后端服务器地址
Vue.prototype.globalData = {backgroundURL:"https://api.515code.com/shopping/"}
//Vue.prototype.globalData = {backgroundURL:"http://localhost:8080/"}

wx.getSystemInfo({
    success: e => {
        Vue.prototype.globalData.StatusBar = e.statusBarHeight;
        let custom = wx.getMenuButtonBoundingClientRect();
        Vue.prototype.globalData.Custom = custom;  
        Vue.prototype.globalData.CustomBar = custom.bottom + custom.top - e.statusBarHeight;
    }
  })
