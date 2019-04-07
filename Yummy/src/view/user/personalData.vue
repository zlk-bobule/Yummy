<template>
  <div>
    <div class="user-back">
      <a style="color: #000000;font-size:30px">个人资料</a>
      <hr />
      <div class="user-content">
        <div>
          <a style="color: #000000;font-size:18px">头像</a>
          <div class="head_img">
            <img :src="userInfo.avatar"/>
            <!--图片地址动态绑定-->
          </div>
        </div>
        <div>
          <a style="color: #000000;font-size:18px">用户名</a><br>
          <a style="color: #000000;font-size:18px">名字</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
    export default {
        name: "",
      data() {
        return {
          userInfo: {
            avatar: 'https://gss0.bdstatic.com/-4o3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike92%2C5%2C5%2C92%2C30/sign=62d46c39067b020818c437b303b099b6/d4628535e5dde7119c3d076aabefce1b9c1661ba.jpg'
          }
        }
      }
    }
</script>

<style scoped>
  .user-back{
    /*display: flex;*/
    /*align-items: center;*/
    justify-content: space-between;
    height:700px;
    width: 1000px;
    border-bottom: 1px solid #f7f7f7;
    background: #fff;
  }
  .user-content{
    padding-left: 50px;
    padding-top: 20px;
  }
  .head_img{
    height: 90px;
    padding-top: 20px;
    padding-left: 30px;
  }
  .head_img img{
    width:90px;
    height:90px;
    border-radius:50px
  }

</style>
