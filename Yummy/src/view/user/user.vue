<template> 
    <div>
      <div class="item_bock head_p">
        <div class="head_img">
          <img :src="userInfo.avatar"/>
          <!--图片地址动态绑定-->
        </div>
        <div class="setting_right" @click.stop="uploadHeadImg">
          <div class="caption">更改头像</div>
        </div>
        <input type="file" accept="image/*" @change="handleFile" class="hiddenInput"/>
        <a style="color: #000000">您好，名字</a>
        <a style="color: #000000">****会员</a>
      </div>
    </div>

</template>

<script>

    export default {
        data() {
          return {
            userInfo: {
              avatar: 'https://gss0.bdstatic.com/-4o3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike92%2C5%2C5%2C92%2C30/sign=62d46c39067b020818c437b303b099b6/d4628535e5dde7119c3d076aabefce1b9c1661ba.jpg'
            }
          }
        },
      methods: {
        // 打开图片上传
        uploadHeadImg: function () {
          this.$el.querySelector('.hiddenInput').click()
        },
        // 将头像显示
        handleFile: function (e) {
          let $target = e.target || e.srcElement
          let file = $target.files[0]
          var reader = new FileReader()
          reader.onload = (data) => {
            let res = data.target || data.srcElement
            this.userInfo.avatar = res.result
          }
          reader.readAsDataURL(file)
        }
      }
    }

</script>

<style>
  .item_bock {
    display: flex;
    align-items: center;
    justify-content: space-between;
    height:94px;
    width: 500px;
    padding:0px 24px 0px 38px;
    border-bottom: 1px solid #f7f7f7;
    background: #fff;
  }
  .head_p {
    height:132px;
  }
  .head_img{
    height: 90px;
  }
  .head_img img{
    width:90px;
    height:90px;
    border-radius:50px
  }
  .setting_right{
    display: flex;
    height: 37px;
    justify-content: flex-end;
    align-items: center;
  }
  .hiddenInput{
    display: none;
  }
  .caption {
    color: #8F8F8F;
    font-size: 26px;
    height: 37px;
  }
</style>
