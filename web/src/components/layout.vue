<template>
<div>
  <div class="app-header">
    <div class="header-left">
      <div class="header-title">竞赛系统</div>
    </div>
    <div class="header-right">
      <span @click="showAboutDialog">登录</span>|
      <span>注册</span>|
      <span>帮助</span>
    </div>
  </div>
  <div class="app-content">
    <!--<keep-alive>用来缓存组件,避免多次加载相应的组件,减少性能消耗-->
    <keep-alive>
      <router-view></router-view>
    </keep-alive>
  </div>
  <div class="app-footer">@2017 author 魏亚津</div>
  <my-dialog :is-show="isShowAboutDialog" @on-close="closeAboutDialog">请登录哈哈</my-dialog>
</div>
</template>

<script type="text/ecmascript-6">
  import Dialog from './base/dialog'
  export default {
    components:{
      MyDialog:Dialog
    },
    methods:{
      showAboutDialog(){
        this.isShowAboutDialog=true;
      },
      closeAboutDialog(){
        this.isShowAboutDialog=false;
      }
    },
    data () {
      return {
        isShowAboutDialog:false
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
  /*初始化css--start*/
  body,ol,ul,h1,h2,h3,h4,h5,h6,p,th,td,dl,dd,form,fieldset,legend,input,textarea,select{margin:0;padding:0}
  body{font:14px"宋体","Arial Narrow",HELVETICA;background:#fff;-webkit-text-size-adjust:100%;}
  a{color:black;text-decoration:none}
  a:hover{color:#cd0200;text-decoration:underline}
  em{font-style:normal}
  li{list-style:none}
  img{border:0;vertical-align:middle}
  table{border-collapse:collapse;border-spacing:0}
  p{word-wrap:break-word}
  /*初始化css--end*/


  /*业务css--start*/
  .app-header{
    height: 60px;
    background-color: #97CBFF;
  }
  .header-right{
    float: right;
    position: relative;
    top: 38%;
    right: 36px;
  }
  .app-footer{
    position: fixed;
    height: 30px;
    background: #c0c0c0;
    width: 100%;
    bottom: 0px;
    text-align: center;
    line-height: 30px;
  }
  /*业务css--end*/
</style>
