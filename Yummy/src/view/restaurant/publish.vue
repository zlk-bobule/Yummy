<template>
  <div class="publish">
    <div class="article">
      <div class="title">发布信息</div>
      <div class="tab-head" slot="head">
        <router-link to="/productPublish" class="tab">单品</router-link>
        <router-link to="/packagePublish" class="tab">套餐</router-link>
        <router-link to="/discountPublish" class="tab">优惠</router-link>
      </div>
      <router-view class="body"></router-view>
    </div>
  </div>
</template>

<script>
    export default {
        name: "publish"
    }
</script>

<style scoped>
  .publish{
    padding: 70px 0 100px 0
  }
  .article{
    position: relative;
    margin: 0 auto;
    width: 800px;
    padding: 50px 100px 100px 100px;
    background: #ffffff;
  }
  .title{
    margin-bottom: 50px;
    padding-left: 15px;
    border-left: 3px solid #409eff;
    color: #000;
    font-size: 20px;
  }
  .tab-head{
    padding: 2% 4% 0 4%;
    margin: 0 auto 15px auto;
    text-align: center;
  }
  .body{

  }
  .tab{
    font-size: 22px;
    margin-right: 40px;
  }
</style>
