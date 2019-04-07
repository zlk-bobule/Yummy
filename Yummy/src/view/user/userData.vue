<template xmlns:v-on="http://www.w3.org/1999/xhtml">
  <div style="height: 700px;background-color: #ffffff">
    <div style="padding-top: 20px;padding-left: 20px">
      <div style="font-size: 20px;border-left: 3px solid #409eff;padding-left: 10px;font-weight: normal;float: left">个人资料</div>
      <el-button style="margin-left: 30px;background-color: red;color: #ffffff;font-weight: bolder" @click="deleteUser" v-if="user.userState==='VALID'">注销账户</el-button>
      <!--<div style="margin-left: 60px;color: red;" v-if="user.userState==='NOTVALID'">账户已注销</div>-->
      <el-tag style="margin-left: 60px;color: red;font-weight: bolder" v-if="user.userState==='NOTVALID'">账户已注销</el-tag>
    </div>
    <div style="padding-top: 60px;padding-left: 50px">
      <div>
        <div style="color: #6f7180;font-size: 15px;float: left;margin-top: 8px">会员名</div>
        <div>
          <el-input v-model="name" style="width: 300px;padding-left: 30px"></el-input>
          <a href="javascript:;" v-on:click="saveName" v-if="user.userState==='VALID'">保存</a>
        </div>
      </div>
      <div style="padding-top: 30px">
        <div style="color: #6f7180;font-size: 15px;float: left;margin-top: 8px">头像</div>
        <div v-show="!showUpload" @mouseover="toggleChangeAvatarBtn" @mouseout="toggleChangeAvatarBtn" style="
          height:150px; width:150px;float: left;margin-left: 45px">
          <img :src="getAvatar" width="150px" height="150px">
          <div v-show="showChangeAvatarBtn" class="change-avatar-btn" @click="toggleUpload" v-if="user.userState==='VALID'">更换头像</div>
        </div>
        <div v-show="showUpload" style="margin-left: 45px">
          <el-upload
            class="avatar-uploader"
            action="/upload/avatar"
            :data="uploadData"
            accept="image/*"
            :show-file-list="false"
            :on-success="uploadSuccess">
            <i class="el-icon-plus avatar-uploader-icon"></i>
            <div class="el-upload__tip" slot="tip" style="margin-left: 20px">图片大小不超过2Mb</div>
          </el-upload>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {mapMutations} from 'vuex'

    export default {
      name: "",
      data() {
        return{
          name:'',
          showUpload: false,
          showChangeAvatarBtn: false,
          user:{}
        }
      },
      computed: {
        uploadData: function () {
          return {
            id: this.user ? this.user.id : 0
          }
        },
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
        this.$ajax.get('/user/info/' + userId).then((res) => {
          let result = res.data
          if (result.code === 0) {
            this.user = result.data
            this.name = this.user.name
          } else {
            this.$message.error('查看个人信息失败！')
          }
        })
      },
      methods: {
        ...mapMutations({
          setAvatar: 'changeAvatar',
          setName: 'changeName'
        }),
        toggleChangeAvatarBtn: function () {
          this.showChangeAvatarBtn = !this.showChangeAvatarBtn
        },
        toggleUpload: function () {
          this.showUpload = !this.showUpload
        },
        uploadSuccess: function (response, file, fileList) {
          this.toggleUpload()
          this.user.avatar = file.name
          this.setAvatar(file.name)
        },
        saveName: function () {
          this.$ajax.get('/user/saveName',{
            params: {
              id : this.$store.getters.id,
              name : this.name
            }
          }).then((res) => {
            let result = res.data
            if (result.code === 0) {
              this.$message.success("保存成功")
              this.setName(this.name)
            } else {
              this.$message.error('保存失败')
            }
          })
        },
        deleteUser: function () {
          let id = this.$store.getters.id
          this.$ajax.get('/user/'+id+'/modifyState').then((res) => {
            let result = res.data
            if (result.code === 0) {
              this.$message.success("注销成功")
              this.user.userState = 'NOTVALID'
            } else {
              this.$message.error('注销失败')
            }
          })
        }
      }
    }
</script>

<style scoped>
  .change-avatar-btn {
    /*bottom: 5px;*/
    /*left: 20px;*/
    margin: 0 5px;
    padding: 5px 0;
    width: 140px;
    text-align: center;
    background-color: rgba(7, 17, 27, 0.3);
    color: #fff;
    font-size: 12px
  }
  .avatar-uploader {
    border: 1px dashed #409EFF;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    margin-right: 745px;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 150px;
    height: 150px;
    line-height: 150px;
    text-align: center;
  }
</style>
