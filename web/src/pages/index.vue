<template>
  <div class="index-wrap">
    <div class="index-left">
      <div class="index-left-block">
        <h2>常用项</h2>
        <template v-for="product in productList">
          <h3>{{ product.title}}</h3>
          <ul>
            <li v-for="item in product.list" class="mt10">
              <a :href="item.url">{{ item.name }}</a>
              <span v-if="item.hot" class="hot-tag">HOT</span>
            </li>
          </ul>
          <div v-if="!product.last" class="hr"></div>
        </template>
      </div>
      <div class="index-left-block lastest-news">
        <h2>最新消息</h2>
        <ul>
          <li v-for="item in newsList" class="mt10">
            <a :href="item.url" class="new-item">{{ item.title }}</a>
          </li>
        </ul>
      </div>
    </div>
    <div class="index-right">
      <slide-show :slides="slides" :inv="invTime"></slide-show>
      <div class="index-board-list">
        <div
          class="index-board-item"
          v-for="(item, index) in boardList"
          :class="[{'line-last' : index % 2 !== 0}, 'index-board-' + item.id]">
          <div class="index-board-item-inner">
            <h2>{{ item.title }}</h2>
            <p>{{ item.description }}</p>
            <div class="index-board-button">
              <!--router-link是vue-router组件提供的标签，to属性就是要跳转的路径-->
              <router-link class="button" :to="{path: 'detail/' + item.toKey}">立即跳转</router-link>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script type="text/ecmascript-6">
  import slideShow from '../components/slideShow'
  export default {
    components:{
      slideShow
    },
    data() {
      return {
        invTime: 2000,
        slides: [
          {
            src: require('../assets/slideShow/M.jpg'),
            title: 'M',
            href: 'detail/momShopCar'
          },
          {
            src: require('../assets/slideShow/ocean.jpg'),
            title: 'ocean',
            href: 'detail/dadyShopCar'
          },
          {
            src: require('../assets/slideShow/timg.jpg'),
            title: 'stars',
            href: 'detail/myShopCar'
          },
          {
            src: require('../assets/slideShow/kid.jpg'),
            title: 'baby',
            href: 'detail/studyPlan'
          }
        ],
        productList: {
          pc: {
            title: '个人相关',
            list: [
              {
                name: '记账本',
                url: 'http://assistant.youshang.com/html/accountreg/acc.jsp'
              },
              {
                name: '日记',
                url: 'http://note.youdao.com/semdl/writing.html'
              },
              {
                name: '他人生日',
                url: 'http://note.youdao.com/semdl/writing.html',
              }
            ]
          },
          app: {
            title: '我的家人',
            last: true,
            list: [
              {
                name: '待办项',
                url: 'https://wx.qq.com'
              },
              {
                name: '健康助手',
                url: 'https://www.nike.com/cn/zh_cn',
              },
              {
                name: '教爸妈玩手机教程',
                url: 'https://wenku.baidu.com/view/1bf5d48859f5f61fb7360b4c2e3f5727a5e9243f.html'
              },
              {
                name: '家庭视频',
                url: 'https://pan.baidu.com/disk/home'
              }
            ]
          }
        },
        newsList: [
          {
            title:"老妈的减肥计划",
            url:"https://www.baidu.com"
          },
          {
            title:"老爸的抽烟计划",
            url:"https://www.baidu.com"
          },
          {
            title:"前端大牛成长之路",
            url:"https://www.baidu.com"
          }
        ],
        boardList: [
          {
            title: '老妈的购物车',
            description: '集美貌和温柔的妈妈',
            id: 'car',
            toKey: 'momShopCar',
          },
          {
            title: '老爸的购物车',
            description: '帅气稳重的老爸',
            id: 'earth',
            toKey: 'dadyShopCar',
          },
          {
            title: '我的购物车',
            description: '努力赚钱ing',
            id: 'loud',
            toKey: 'myShopCar',
          },
          {
            title: '我的学习计划',
            description: '致力成为前端大神',
            id: 'hill',
            toKey: 'studyPlan',
          }
        ],
      }
    }
  }
</script>
<style scoped>
  .mt10{
    margin-top:10px;
  }
  .index-wrap {
    width: 1200px;
    margin: 0 auto;
    overflow: hidden;
  }
  .index-left {
    float: left;
    width: 300px;
    text-align: left;
  }
  .index-right {
    float: left;
    width: 900px;
  }
  .index-left-block {
    margin: 15px;
    background: #fff;
    box-shadow: 0 0 1px #b7c0d3;
  }
  .index-left-block .hr {
    margin-bottom: 20px;
    border: 1px solid #c0c0c0;
  }
  .index-left-block h2 {
    background: #addce3;
    color: #fff;
    padding: 10px 15px;
    margin-bottom: 20px;
  }
  .index-left-block h3 {
    padding: 0 15px 5px 15px;
    font-weight: bold;
    color: #222;
  }
  .index-left-block ul {
    padding: 10px 15px;
  }
  .index-left-block li {
    padding: 5px;
  }

  .index-board-list {
    overflow: hidden;
  }
  .index-board-item {
    float: left;
    width: 400px;
    background: #fff;
    box-shadow: 0 0 1px #ddd;
    padding: 20px;
    margin-right: 20px;
    margin-bottom: 20px;
  }
  .index-board-item-inner {
    min-height: 125px;
    padding-left: 120px;
  }
  .index-board-car .index-board-item-inner {
    background: url(../assets/images/1.gif) no-repeat;
  }

  .index-board-loud .index-board-item-inner {
    background: url(../assets/images/2.gif) no-repeat;
  }
  .index-board-earth .index-board-item-inner {
    background: url(../assets/images/3.gif) no-repeat;
  }
  .index-board-hill .index-board-item-inner {
    background: url(../assets/images/4.gif) no-repeat;
  }
  .index-board-item h2 {
    font-size: 18px;
    font-weight: bold;
    color: #000;
    margin-bottom: 15px;
  }
  .line-last {
    margin-right: 0;
  }

  .index-board-button {
    margin-top: 20px;
    height: 30px;
    width: 120px;
    text-align: center;
    line-height: 30px;
    background-color: #a4d4f6;
  }
  .index-board-button a{
    color:white;
  }
  .lastest-news {
    min-height: 512px;
  }
  .hot-tag {
    background: red;
    color: #fff;
  }
  .new-item {
    display: inline-block;
    width: 230px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
</style>
