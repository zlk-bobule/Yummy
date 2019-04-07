<template>
  <div class="start-background">
    <div class="map-header">
      订餐系统
    </div>
    <div class="map-main">
      <p style="color: #ffffff">Yummy</p>
      <p class="linkTo">
        <el-button class="RestauantButton" @click="userSign">我要点餐</el-button>
        <router-link to="/login" class="linkTo">餐厅/经理 登录</router-link>
        <span>|</span>
        <router-link to="/restaurantSign" class="linkTo">餐厅注册</router-link>
      </p>
    </div>
  </div>

</template>

<script>
  export default {
    name:'',
    data() {
      return {
      }
    },
    methods:{
      userSign: function () {
        this.$router.push('/sign')
      }
    }
  }
</script>

<style>
  .start-background{
    width: 100%;
    height: 100vh;
    background: url('back.jpg') no-repeat;
    background-size: 100% 100%;
  }
  .map-header {
    width: 100%;
    height: 50px;
    padding-top: 25px;
    color: #fff;
    line-height: 45px;
    font-size: 20px;
    margin-left: 20px;
  }
  .map-main{
    width: 100%;
    top: 27%;
    text-align: center;
    position: relative;
    font-size: 50px;
  }
  .linkTo{
    color: #FFF;
    font-size: 20px;
    margin-top: 70px;
  }
  .RestauantButton{
    background-color: #5db423;
    opacity: 0.7;
    border-radius: 10px;
    font-size: 15px;
    margin-left: 20px;
    margin-right: 50px;
    color: #ffffff;
  }
</style>
