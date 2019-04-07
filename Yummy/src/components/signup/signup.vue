<template xmlns:v-on="http://www.w3.org/1999/xhtml">
  <div class="sign-background">
    <div class="sign-form">
      <div style="margin:30px">
        <p class="sign-title">注册</p>
        <div class="line"></div>
        <form class="sign-container">
          <!--<input type="email" placeholder="邮箱地址" class="sign-input" name="mail">-->
          <div style="position: absolute">
            <el-input v-model="mail" class="sign-input"  placeholder="邮箱"></el-input>
            <a href="javascript:;" v-on:click="getVerCode">获取验证码</a>
          </div>
          <el-input v-model="verCode" style="margin-top: 60px; width: 300px;height: 30px;" placeholder="输入验证码"></el-input>
          <el-button @click="sign" style="background-color: #67c23a;color: white;font-weight: bolder;margin-top: 20px;margin-left: 90px">邮箱验证</el-button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
  import {mapMutations} from 'vuex'

  export default {
    name:'signup',
    data() {
      return {
        mail:'',
        verCode:'',
        trueVerCode:''
      }
    },
    methods:{
      ...mapMutations({
        userLogin: 'login'
      }),
      getVerCode: function () {
        this.$ajax.get('/user/getVerCode', {
          params: {
            mail: this.mail
          }
        }).then((response) => {
          let res = response.data
          if (res.code === 0) {
            this.$message.success("已成功发送邮件")
            this.trueVerCode = res.data
          } else {
            this.$message.error(res.message)
          }
        })
      },
      sign: function (){
        if(this.trueVerCode === this.verCode){
          this.$ajax.get('/user/sign',{
            params: {
              mail:this.mail
            }
          }).then((res) =>{
            let result = res.data;
            if(result.code === 0){
              let user = result.data
              let payload = {
                userId: user.id,
                username: user.name,
                userType: 1,
                avatar: user.avatar,
              }
              this.userLogin(payload)
              this.$router.push('/home')
            } else {
              this.$message.error('注册登录失败')
            }
          })
        }else{
          this.$message.error('输入验证码错误')
        }
      }
    }
  }

</script>

<style>
  .sign-background {
    width: 100%;
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
    background: url('signupBack.jpg') no-repeat;
    background-size: 100% 100%;
  }
  .sign-form{
    width: 500px;
    background-color: white;
    opacity: 0.85;
    border-radius: 10px;
  }
  .sign-title{
    font-size: 30px;
    font-family: "Avenir";
    margin-bottom: 15px;
  }
  .line{
    border-bottom: black 1px solid;
    width: 400px;
  }
  .sign-container{
    margin:50px;
  }
  .sign-input{
    border-radius: 10px;
    width: 300px;
    height: 30px;
  }
  .sign-button{
    background-color: green;
    width: 100px;
    height: 40px;
    border-radius: 10px;
    color: white;
    font-size: 18px;
    opacity: 0.7;
    margin-top: 20px;
    margin-bottom: 20px;
    margin-left: 100px;
  }


</style>

