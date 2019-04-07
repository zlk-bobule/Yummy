<template>
  <div style="padding: 70px 0 100px 30px">
    <i style="color: #888888">当前位置：</i>
    <a style="color: #000000">上海万达瑞华酒店</a>
    <a href="" style="margin-left: 10px">[切换位置]</a><br>
    <div style="float: left;margin-top: 20px;margin-left: 30px">
      <el-menu router default-active="2" default-openeds="1" style="background-color: #f6f6f7;width: 250px">
        <el-menu-item index="/userCenter">
          <router-link to="/userCenter"><i class="el-icon-document">个人中心</i></router-link>
        </el-menu-item>
        <el-submenu index="1">
          <template slot="title"><i class="el-icon-document">我的资料</i> </template>
          <el-menu-item-group style="background-color:#f6f6f7">
            <el-menu-item index="/userData" style="color: #7e8c8d;font-weight: bolder">个人资料</el-menu-item>
            <el-menu-item index="/userAddress" style="color: #7e8c8d;font-weight: bolder">地址管理</el-menu-item>
          </el-menu-item-group>
        </el-submenu>
      </el-menu>
    </div>
    <div style="margin-top: 20px;margin-left:300px;width: 1000px;">
      <router-view ref="child">
      </router-view>
    </div>
  </div>
</template>

<script>

    export default {
        data() {
            return {msg: 'vue模板页'}
        }
    }

</script>

<style>

</style>
