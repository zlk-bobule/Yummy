<template>
  <div class="sign-background">
  <el-form class="login-form">
    <el-form-item>
      <el-input v-model="identifyCode" class="username-input" placeholder="编码（7位识别码）">
        <i class="iconfont" slot="prefix">&#xe65a;</i>
      </el-input>
    </el-form-item>
    <el-form-item>
      <el-input v-model="password" class="password-input" placeholder="密码" type="password" @keyup.enter.native="login">
        <i class="iconfont" slot="prefix">&#xe62a;</i>
      </el-input>
    </el-form-item>
    <el-form-item>
      <el-button class="login-btn" @click="login" type="primary" round>登录</el-button>
    </el-form-item>
  </el-form>
  </div>

</template>

<script>
  import {mapMutations} from 'vuex'

  export default {
    name: 'signin',
    data () {
      return {
        identifyCode: '',
        password: ''
      }
    },
    methods: {
      ...mapMutations({
        resLogin: 'login'
      }),
      login: function () {
        if(this.identifyCode === 'admin'){
          this.$ajax.get('/admin/login', {
            params: {
              name: this.identifyCode,
              password: this.password
            }
          }).then((response) => {
            response = response.data
            if(response.code === 0){
              this.$message.success("登录成功")
              this.$router.push('/adminHome')
            }
          })
        }else {
          this.$ajax.get('/restaurant/login', {
            params: {
              identifyCode: this.identifyCode,
              password: this.password
            }
          }).then((response) => {
            response = response.data
            if (response.code === 0) {
              this.$message.success("登录成功")
              let res = response.data
              let payload = {
                userId: res.id,
                username: res.resName,
                userType: 2,
                avatar: res.logo,
              }
              this.resLogin(payload)
              this.$router.push('/resHome')
            } else if (response.code === 1) {
              this.$message.error("密码输入错误!")
            } else {
              this.$message.error("该餐厅不存在")
            }
          })
        }

      }
    }
  }

</script>

<style>
  .login-form{
    width: 25%;
    margin:0 auto;
    padding: 5% 5%;
    background: #ffffff;
    opacity: 0.9;
    border-radius: 10px;
  }
  .login-btn{
    width: 100%;
  }
  .sign-background{
    width: 100%;
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
    background: url('signinBack.jpg') no-repeat;
    background-size: 100% 100%;
  }

</style>

