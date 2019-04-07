<template xmlns:v-on="http://www.w3.org/1999/xhtml">
  <div>
    <div style="background-color: #ffffff;height: 200px;">
      <a href="javascript:;" v-on:click="toUserData">
      <img :src="getAvatar" height="110px" width="110px" style="border-radius:50%;margin-top: 40px;margin-left: 30px;float: left">
      </a>
      <div style="width: 200px;float: left">
        <div style="font-size: 20px;font-weight: bolder;padding-top: 60px;margin-left: 30px">
          您好，{{user.name}}
        </div>
        <div style="margin-left: 30px;padding-top: 10px;color: #6f7180;font-weight: bold">
          Yummy祝您点餐愉快
        </div>
      </div>
      <div style="float:left;width: 1px;height: 70px; background: #eee;margin-top: 50px"></div>
      <div style="width: 220px;float: left">
        <div style="font-size: 15px;color: #e6a23c;padding-top: 50px;margin-left: 60px">
          您的会员等级
        </div>
        <div style="padding-top: 25px;margin-left: 65px;color: red;font-size: 20px;font-weight: bolder">
          {{level}}
        </div>
      </div>
      <div style="float:left;width: 1px;height: 70px; background: #eee;margin-top: 50px"></div>
      <div style="float: left;width: 220px">
        <div style="font-size: 15px;color: #7e8c8d;padding-top: 50px;margin-left: 60px">
          您的优惠折扣
        </div>
        <div style="padding-top: 25px;margin-left: 73px;color: green;font-size: 20px;font-weight: bolder">
          {{discount}}%
        </div>
      </div>
      <div style="float:left;width: 1px;height: 70px; background: #eee;margin-top: 50px"></div>
      <div style="width: 200px;float: left">
        <div style="font-size: 15px;color: #7e8c8d;padding-top: 50px;margin-left: 60px">
          账户余额
        </div>
        <div style="padding-top: 25px;margin-left: 60px;">
          <div style="font-size: 25px;font-weight: bolder;color: #8cc5ff;float: left">{{account}}</div>
          <div style="font-weight: lighter;padding-top: 7px">元</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {mapMutations, mapGetters} from 'vuex'

    export default {
      name: "",
      data () {
        return{
          user:{},
          level:'一级会员',
          discount:100,
          account:0.00
        }
      },
      computed: {
        ...mapGetters([
          'username',
          'avatar',
          'id'
        ]),
        getAvatar: function () {
          if (this.$store.getters.avatar === 'default_avatar.png') {
            return '/static/image/default_avatar.png'
          } else {
            return '/show/avatar/' + this.$store.getters.id + '/' + this.$store.getters.avatar
          }
        }
      },
      mounted() {
        let userId = this.$store.getters.id
        console.log(userId)
        this.$ajax.get('/user/info/' + userId).then((res) => {
          let result = res.data
          if (result.code === 0) {
            this.user = result.data
            this.getLevelAndDiscount()
            this.$ajax.get('/user/'+userId+'/getAccount').then((res1) => {
              let result1 = res1.data
              if(result1.code === 0){
                this.account = (result1.data).toFixed(2)
              }else{
                this.$message.error('获取账户失败！')
              }
            })
          } else {
            this.$message.error('查看个人信息失败！')
          }
        })
      },
      methods: {
        toUserData: function () {
          this.$router.push('/userData')
        },
        getLevelAndDiscount: function () {
          console.log(this.user.userLevel)
          switch (this.user.userLevel){
            case 'FIRSTCLASS':
              this.level = '一级会员'
              this.discount = 98
              break
            case 'SECONDCLASS':
              this.level = '二级会员'
              this.discount = 96
              break
            case 'THREECLASS':
              this.level = '三级会员'
              this.discount = 94
              break
            case 'FOURCLASS':
              this.level = '四级会员'
              this.discount = 92
              break
            case 'FIVECLASS':
              this.level = '五级会员'
              this.discount = 90
              break
          }
        }
      }
    }
</script>

<style scoped>

</style>
