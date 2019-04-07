<template>
    <div class="home">
      <header>
        <nav class="head-nav">
          <div class="fl">
            <a class="brand" href="/#/resHome">
              <img src="/static/image/logo.png" width="169" height="44" alt="">
            </a>
            <div class="links">
              <router-link to="/publish" class="link">发布信息</router-link>
              <router-link to="/deliverList" class="link">送餐记录</router-link>
              <router-link to="/resOrderList" class="link">统计信息</router-link>
            </div>
          </div>
          <div class="fr">
            <div class="user-wrapper">
              <img class="avatar" :src="getAvatar" width="30px" height="30px">
              <el-dropdown class="username" size="medium" placement="bottom" @command="handleCommand">
                <span class="el-dropdown-link">
                {{ username }}<i class="el-icon-arrow-down el-icon--right"></i>
              </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item></el-dropdown-item>
                  <el-dropdown-item command="restaurant">餐厅主页</el-dropdown-item>
                  <el-dropdown-item divided command="logout" v-show="this.$store.getters.isLogin">退出登录</el-dropdown-item>
                  <el-dropdown-item divided command="login" v-show="!this.$store.getters.isLogin">登录</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </div>
          </div>
        </nav>
      </header>
      <div class="home-view">
        <router-view ref="child">
        </router-view>
      </div>
    </div>
</template>

<script>
  import {mapMutations, mapGetters} from 'vuex'

    export default {
        name: "resHome",
        data(){
           return{

           }
        },
      computed: {
        ...mapGetters([
          'username',
          'avater',
          'userType'
        ]),
        getAvatar: function () {
          if (this.$store.getters.avatar === '') {
            return '/static/image/default_avatar.png'
          } else {
            return '/show/logo/' + this.$store.getters.id + '/' + this.$store.getters.avatar
          }
        }
      },
      methods: {
        ...mapMutations({
          userLogout: 'logout'
        }),
          handleCommand: function (command){
            switch (command){
              case 'logout':
                this.userLogout()
                this.$router.push('/login')
                break
              case 'restaurant':
                this.$router.push("/resInfo")
                break
              case 'login':
                this.$router.push('/login')
            }
          }
      }
    }
</script>

<style scoped>
  .home{
    background-color: #f6f6f7;
  }
  .head-nav{
    position: fixed;
    left: 0;
    top: 0;
    z-index: 1000;
    box-sizing: border-box;
    width: 100%;
    height: 65px;
    font-size: 16px;
    color: #888;
    background-color: #ffffff;
    box-shadow: 2px 0 5px #888888;
  }
  .fl{
    float: left;
  }
  .brand-img{
    margin-left: 20px;
    padding: 10px 0;
  }
  .links{
    display: inline-block;
    vertical-align: top;
    margin-right: 15px;
    padding-top: 25px;
  }
  .link{
    margin-right: 15px;
  }
  .fr{
    float: right;
    padding-top: 18px;
    padding-right: 20px;
  }
  .user-wrapper{
    display: inline-block;
  }
  .avatar{
    border-radius: 50%;
    margin-right: 5px;
  }
  .username{
    display: inline-block;
    vertical-align: top;
    font-size: 16px;
    padding-top: 8px;
  }
  .home-view{
    padding-top: 33px;
    background-color: #f6f6f7;
  }
</style>
