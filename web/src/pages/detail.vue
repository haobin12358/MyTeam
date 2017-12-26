<template>
  <div class="detail-wrap">
    <div class="detail-left">
      <div class="product-board">
        <img :src="productIcon">
        <ul>
          <router-link v-for="item in products" :to="{ path: item.path }" tag="li" active-class="active">
            {{ item.name }}
          </router-link>
        </ul>
      </div>
    </div>
    <!--面包屑导航栏-->
    <div class="font14">
      <router-link class="crumbs" :to="{path:'/'}"  tag="li">{{ "Home < "}}</router-link>
      <span>{{$route.path.replace('/','')}}</span>
    </div>
    <div class="detail-right">
      <keep-alive>
        <router-view></router-view>
      </keep-alive>
    </div>
  </div>
</template>

<script scoped>
  export default {
    data () {
    return {
      products: [
        {
          name: '老妈的购物车',
          path: 'momShopCar',
          icon: require('../assets/images/1.gif'),
          active: false
        },
        {
          name: '老爸的购物车',
          path: 'dadyShopCar',
          active: false
        },
        {
          name: '我的购物车',
          path: 'myShopCar',
          active: false
        },
        {
          name: '我的学习计划',
          path: 'studyPlan',
          active: false
        }
      ],
      imgMap: {
        '/detail/momShopCar': require("../assets/images/1.gif"),
        '/detail/dadyShopCar': require("../assets/images/2.gif"),
        '/detail/myShopCar': require("../assets/images/3.gif"),
        '/detail/studyPlan': require("../assets/images/4.gif")
      }
    }
  },
  computed: {
    productIcon()
    {
      return this.imgMap[this.$route.path]
    }
  }
  }
</script>

<style>
  .font15{
    font-size: 15px;
  }
  .crumbs{
    cursor: pointer;
    color: blue;
    text-decoration: none;
    float: left;
    margin-left: 23px;
  }
  .crumbs:hover{
    text-decoration: underline ;
  }
  .detail-wrap {
    width: 1200px;
    margin: 0 auto;
    overflow: hidden;
    padding-top: 20px;
  }

  .detail-left {
    float: left;
    width: 200px;
    text-align: center;
  }

  .detail-right {
    float: left;
    width: 980px;
    margin-left: 20px;
  }

  .product-board {
    background: #f7fcff;
    padding: 20px 0;
  }

  .product-board ul {
    margin-top: 20px;
  }

  .product-board li {
    text-align: left;
    padding: 10px 15px;
    cursor: pointer;
  }

  .product-board li.active,
  .product-board li:hover {
    background: #4fc08d;
    color: #fff;
  }

  .product-board li a {
    display: block;
  }

  .sales-board {
    background: #fff;
  }

  .sales-board-form {

  }

  .sales-board-intro h2 {
    font-size: 20px;
    padding: 20px;
  }

  .sales-board-intro p {
    background: #f7fcff;
    padding: 10px 20px;
    color: #999;
    line-height: 1.8;
  }

  .sales-board-form {
    padding: 30px 20px;
    font-size: 14px;
  }

  .sales-board-line {
    clear: both;
    padding-bottom: 20px;
  }

  .sales-board-line-left {
    display: inline-block;
    width: 100px;
  }

  .sales-board-line-right {
    display: inline-block;
    width: 75%;
  }

  .sales-board-des {
    border-top: 20px solid #f0f2f5;
    padding: 15px 20px;
    margin:0px 0px 50px 0px;
  }

  .sales-board-des p {
    line-height: 1.6;
  }

  .sales-board-des h2 {
    font-size: 20px;
    padding-bottom: 15px;
  }

  .sales-board-des h3 {
    font-size: 18px;
    font-weight: bold;
    padding: 20px 0 10px 0;
  }

  .sales-board-des li {
    padding: 5px 0;
  }

  .sales-board-table {
    width: 100%;
    margin-top: 20px;
  }

  .sales-board-table th {
    background: #4fc08d;
    color: #fff;
  }

  .sales-board-table td {
    border: 1px solid #f0f2f5;
    padding: 15px;
  }
</style>
