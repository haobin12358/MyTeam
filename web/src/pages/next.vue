<template>
  <div class="index-wrap">
    123
  </div>
</template>

<script type="text/ecmascript-6">
  export default {
    data() {
      return {
        productList: {
          pc: {
            title: 'PC产品',
            list: [
              {
                name: '数据统计',
                url: 'http://starcraft.com'
              },
              {
                name: '数据预测',
                url: 'http://warcraft.com'
              },
              {
                name: '流量分析',
                url: 'http://overwatch.com',
                hot: true
              },
              {
                name: '广告发布',
                url: 'http://hearstone.com',
              }
            ]
          },
          app: {
            title: '手机应用类',
            last: true,
            list: [
              {
                name: '91助手',
                url: 'http://weixin.com'
              },
              {
                name: '产品助手',
                url: 'http://twitter.com',
                hot: true
              },
              {
                name: '智能地图',
                url: 'http://maps.com'
              },
              {
                name: '团队语音',
                url: 'http://phone.com'
              }
            ]
          }
        },
        newsList: [
          {
            title:"复仇者联盟5上映",
            url:"https://www.baidu.com"
          },
          {
            title:"正义联盟2上映",
            url:"https://www.baidu.com"
          },
          {
            title:"我是传奇",
            url:"https://www.baidu.com"
          }
        ],
        boardList: []
      }
    }
  }
</script>
<style scoped>

</style>
