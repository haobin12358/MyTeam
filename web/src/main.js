import Vue from 'vue'
import VueRouter from 'vue-router'
import VueResource from 'vue-resource'
import './lib/bootstrap/css/bootstrap.min.css'
import './lib/bootstrap/script/bootstrap.min.js'
import layout from './components/layout'
import IndexPage from './pages/index'
import studentPage from './pages/student'

Vue.use(VueRouter);
Vue.use(VueResource);

let router = new VueRouter({
  mode: 'history',
  routes: [
    {
      path: '/index',
      component: IndexPage,
      name: 'index',
      //redirect:'/detail/count',// 默认路由
      children: [
        {
          path: 'student',
          component: studentPage,
          name: 'student',
        }
      ]
    }
  ]
});

new Vue({
  el: '#app',
  router,
  template: '<layout/>',
  components: { layout }
});
