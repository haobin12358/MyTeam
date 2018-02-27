<template>
  <div class="index-wrap">
    <!--页面左侧内容-->
    <div class="index-left">
      <div class="index-left-block">
        <template v-for="(menu, index) in menuList">
          <h3 class="menuTitle" @click="toggle(index)">{{menu.title}}</h3>
          <transition name="fade">
            <ul v-show="menu.isToggle">
              <router-link v-for="i in menu.list" :to="{ path: i.path }" tag="li" active-class="active">
                {{ i.name }}
              </router-link>
            </ul>
          </transition>
        </template>
      </div>
    </div>

    <!--页面右侧内容-->
    <div class="index-right">
      <!--路由填充区域-->
      <keep-alive>
        <router-view></router-view>
      </keep-alive>
    </div>
  </div>
</template>
<script type="text/ecmascript-6">
export default {
  components: {},
  methods: {
    toggle: function(index) {
      this.menuList[index].isToggle = !this.menuList[index].isToggle;
    }
  },
  data() {
    return {
      menuList: [
        {
          name: "Info",
          title: "信息",
          list: [
            {
              name: "个人信息",
              path: ""
            },
            {
              name: "学生信息",
              path: ""
            },
            {
              name: "教师信息",
              path: ""
            },
            {
              name: "竞赛信息",
              path: ""
            }
          ],
          isToggle: true
        },
        {
          name: "Team",
          title: "团队",
          list: [
            {
              name: "团队查询",
              path: ""
            },
            {
              name: "创建团队",
              path: ""
            }
          ],
          isToggle: true
        },
        {
          name: "MyProfile",
          title: "我的",
          list: [
            {
              name: "我创建的团队",
              path: ""
            },
            {
              name: "我参与的团队",
              path: ""
            }
          ],
          isToggle: true
        }
      ]
    };
  }
};
</script>
<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.2s;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
}
.menuTitle {
  background-color: #b2c5d5;
}

.mt10 {
  margin-top: 10px;
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

.index-board-button a {
  color: white;
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
