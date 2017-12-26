// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
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

Vue.config.productionTip = false;
Vue.use(VueRouter);
Vue.use(VueResource);
let router = new VueRouter({
  mode: 'history',
  routes: [
    {
      path: '/',
      component: IndexPage
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
