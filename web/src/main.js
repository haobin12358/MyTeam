import Vue from 'vue'
import  VueRouter from 'vue-router'
import  VueResource from 'vue-resource'
import layout from './components/layout'
import IndexPage from './pages/index'
import DetailPage from './pages/detail'
import analysis from './pages/detail/analysis'
import count from './pages/detail/count'
import forecast from './pages/detail/forecast'
import publish from './pages/detail/publish'
import './lib/bootstrap/css/bootstrap.min.css'
import './lib/bootstrap/script/bootstrap.min.js'

//Vue.config.productionTip = false;
Vue.use(VueRouter);
Vue.use(VueResource);
let router = new VueRouter({
  mode: 'history',
  routes: [
    {
      path: '/',
      component: IndexPage,
      //redirect:'/detail/count',// 默认路由
      children: [
        {
          path: 'momShopCar',
          component: analysis
        },
        {
          path: 'personalInfo',
          component: count
        },
        {
          path: 'personalInfo',
          component: forecast
        },
        {
          path: 'personalInfo',
          component: publish
        },
        {
          path: 'personalInfo',
          component: analysis
        },
        {
          path: 'personalInfo',
          component: count
        },
        {
          path: 'personalInfo',
          component: forecast
        },
        {
          path: 'personalInfo',
          component: publish
        }
      ]
    },
    {
      path: '/detail',
      component: DetailPage,
      redirect:'/detail/count',// 默认路由
      children: [
        {
          path: 'momShopCar',
          component: analysis
        },
        {
          path: 'dadyShopCar',
          component: count
        },
        {
          path: 'myShopCar',
          component: forecast
        },
        {
          path: 'studyPlan',
          component: publish
        }
      ]
    }
  ]
});

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  template: '<layout/>',
  components: {layout}
});
