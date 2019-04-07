<template>
  <div class="home">
  <el-menu router mode="horizontal" class="menu-head" >
    <el-menu-item index="1">
      <router-link to="/login"><i class="el-icon-document menu-spec">首页</i></router-link>
    </el-menu-item>
    <el-menu-item index="2">
      <router-link to="/sign"><i class="el-icon-document menu-spec">我的订单</i></router-link>
    </el-menu-item>
    <el-submenu index="3">
      <template slot="title"><i class="el-icon-document menu-spec">名字</i> </template>
      <el-menu-item-group>
        <el-menu-item index="/personalCenter">个人中心</el-menu-item>
        <el-menu-item index="/assets/accessory">配件列表</el-menu-item>
      </el-menu-item-group>
    </el-submenu>
  </el-menu>
    <router-view class="home-view">
    </router-view>
    </div>
</template>

<script>
    export default {
        name: ""
    }
</script>

<style scoped>
  .home{
    background-color: #f6f6f7;
  }
  .menu-head{
    position: fixed;
    left: 0;
    top: 0;
    z-index: 100;
    width: 100%;
    box-shadow: 2px 0 5px #888888;
    background-color: #545c64;
    font-size: 18px;
  }
  .menu-spec{
    width: 100px;
  }
  .Icon{
    font-size: 18px;
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #ffffff;

  }
  .home-view{
    padding-top: 80px;
  }
</style>
