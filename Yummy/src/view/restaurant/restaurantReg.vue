<template>
  <div class="res-back">
    <div class="article">
      <div class="title">注册餐厅</div>
      <el-steps :space="200" :active="step" finish-status="success" :align-center="true" :simple="true" style="margin-bottom: 30px">
        <el-step title="步骤 1"></el-step>
        <el-step title="步骤 2"></el-step>
      </el-steps>
      <el-form v-model="resSignUpForm" label-width="110px" label-position="left" size="medium" ref="resSignUpForm" v-show="step === 1">
        <el-form-item label="餐厅名称" prop="resName">
          <el-input v-model="resSignUpForm.resName" placeholder="输入餐厅名称"></el-input>
        </el-form-item>
        <el-form-item label="外卖电话" prop="takeOutPhone">
          <el-input v-model="resSignUpForm.takeOutPhone" placeholder="输入外卖电话"></el-input>
        </el-form-item>
        <el-form-item label="联系人姓名" prop="contactName">
          <el-input v-model="resSignUpForm.contactName" placeholder="输入联系人姓名"></el-input>
        </el-form-item>
        <el-form-item label="联系人电话" prop="contactPhone">
          <el-input v-model="resSignUpForm.contactPhone" placeholder="输入联系人电话"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="resSignUpForm.email" placeholder="输入餐厅邮箱"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="resSignUpForm.password" placeholder="输入密码" type="password"></el-input>
        </el-form-item>
        <el-form-item label="门店分类" prop="types">
          <el-select v-model="resSignUpForm.types" multiple>
            <el-option
              v-for="type in types"
              :key="type.value"
              :label="type.value"
              :value="type.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="详细地址">
          <div id="all">
            <el-input v-model="address_detail" placeholder="请与执照地址一致" id="suggestId" name="address_detail" class="input_style"></el-input>
            <div id="allmap"></div>
            <!--<p>{{userlocation.lng}}</p>-->
          </div>
        </el-form-item>
        <el-form-item label="配送费" prop="deliverFee">
          <el-input v-model="resSignUpForm.deliverFee" placeholder="输入配送费" onkeyup="this.value=(this.value.match(/^\d+\.?\d*/)||[''])[0]"></el-input>
        </el-form-item>
        <el-form-item label="起送价" prop="initialDeliverPrice">
          <el-input v-model="resSignUpForm.initialDeliverPrice" placeholder="输入起送价" onkeyup="this.value=(this.value.match(/^\d+\.?\d*/)||[''])[0]"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="submit" type="primary" round>提交</el-button>
        </el-form-item>
      </el-form>
      <el-form label-width="110px" label-position="left" size="medium" v-show="step === 2">
        <el-form-item label="门脸照">
          <div v-show="showUpload1">
            <el-upload
              class="pic-uploader"
              action="/upload/frontPic"
              :data="uploadData"
              accept="image/*"
              :show-file-list="false"
              :on-success="uploadSuccess1">
              <i class="el-icon-plus pic-uploader-icon"></i>
              <div class="el-upload__tip" slot="tip" style="margin-left: 25px">图片大小不超过2Mb</div>
            </el-upload>
          </div>
          <div v-show="!showUpload1" @mouseover="toggleChangeAvatarBtn1" @mouseout="toggleChangeAvatarBtn1" style="
          height:150px; width:150px;float: left;margin-left: 45px">
            <img :src="getFrontPic" width="150px" height="150px">
            <div v-show="showChangeAvatarBtn1" class="change-btn" @click="toggleUpload1">更换头像</div>
          </div>
        </el-form-item>

        <el-form-item label="店内照">
          <div v-show="showUpload2">
            <el-upload
              class="pic-uploader"
              action="/upload/shopPic"
              :data="uploadData"
              accept="image/*"
              :show-file-list="false"
              :on-success="uploadSuccess2">
              <i class="el-icon-plus pic-uploader-icon"></i>
              <div class="el-upload__tip" slot="tip" style="margin-left: 25px">图片大小不超过2Mb</div>
            </el-upload>
          </div>
          <div v-show="!showUpload2" @mouseover="toggleChangeAvatarBtn2" @mouseout="toggleChangeAvatarBtn2" style="
          height:150px; width:150px;float: left;margin-left: 45px">
            <img :src="getShopPic" width="150px" height="150px">
            <div v-show="showChangeAvatarBtn2" class="change-btn" @click="toggleUpload2">更换头像</div>
          </div>
        </el-form-item>

        <el-form-item label="logo">
          <div v-show="showUpload3">
            <el-upload
              class="pic-uploader"
              action="/upload/logo"
              :data="uploadData"
              accept="image/*"
              :show-file-list="false"
              :on-success="uploadSuccess3">
              <i class="el-icon-plus pic-uploader-icon"></i>
              <div class="el-upload__tip" slot="tip" style="margin-left: 25px">图片大小不超过2Mb</div>
            </el-upload>
          </div>
          <div v-show="!showUpload3" @mouseover="toggleChangeAvatarBtn3" @mouseout="toggleChangeAvatarBtn3" style="
          height:150px; width:150px;float: left;margin-left: 45px">
            <img :src="getLogo" width="150px" height="150px">
            <div v-show="showChangeAvatarBtn3" class="change-btn" @click="toggleUpload3">更换头像</div>
          </div>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" round @click="gotoResHome">进入餐厅主页</el-button>
        </el-form-item>

      </el-form>
    </div>
  </div>
</template>

<script>
  import Vue from 'vue';
  // import { pca, pcaa } from 'area-data'; // v5 or higher
  import 'vue-area-linkage/dist/index.css'; // v2 or higher
  import VueAreaLinkage from 'vue-area-linkage';
  import Cookie from 'js-cookie'
  import {mapMutations} from 'vuex'

  Vue.use(VueAreaLinkage)

    export default {
      name: "restaurantSign",
      data () {
          return{
            step:1,
            // pca:pca,
            // pcaa:pcaa,
            address_detail: null, //详细地址
            userlocation: {lng: '', lat: ""},
            resSignUpForm: {
              resName:'',
              takeOutPhone:'',
              contactName:'',
              contact_phone:'',
              types: [],
              address: '',
              lng:0.00000000000,
              lat:0.00000000000,
              password:'',
              deliverFee:0.0,
              initialDeliverPrice:0.0,
              email:''
            },
            frontPic:'',
            shopPic:'',
            logo:'',
            showUpload1:true,
            showChangeAvatarBtn1: false,
            showUpload2:true,
            showChangeAvatarBtn2: false,
            showUpload3:true,
            showChangeAvatarBtn3: false,
            restaurant:{},
            types: [{value: '烧烤'},{value: '炸鸡炸串'},{value: '小龙虾'},{value: '地方小吃'},{value: '零食'},{value: '奶茶果汁'},{value: '甜品'},{value: '咖啡'}],
            rules: {
              resName: [
                {
                  required: true,
                  message: '请输入门店名称',
                  trigger: 'blur'
                }
              ],
              takeOutPhone: [
                {
                  required: true,
                  message: '请输入手机号码',
                  trigger: 'blur'
                },
                {
                  validator: function (rule, value, callback) {
                    if (/^1[34578]\d{9}$/.test(value) === false) {
                      callback(new Error('请输入正确的手机号'))
                    } else {
                      callback()
                    }
                  },
                  trigger: 'blur'
                }
              ],
              contactName: [
                {
                  required: true,
                  message: '请输入门店名称',
                  trigger: 'blur'
                }
              ],
              contactPhone: [
                {
                  required: true,
                  message: '请输入手机号码',
                  trigger: 'blur'
                },
                {
                  validator: function (rule, value, callback) {
                    if (/^1[34578]\d{9}$/.test(value) === false) {
                      callback(new Error('请输入正确的手机号'))
                    } else {
                      callback()
                    }
                  },
                  trigger: 'blur'
                }
              ],
              // city:[
              //   {
              //     required: true
              //   }
              // ]
            }
          }
      },
      computed: {
        uploadData: function () {
          return {
            id: this.restaurant ? this.restaurant.id : 0
          }
        },
        getFrontPic: function () {
          console.log("this.restaurant.frontPic")
          console.log(this.restaurant.frontPic)
          if(this.restaurant.frontPic!=null) {
            console.log("here")
            if (this.restaurant.frontPic === '') {
              return '/static/image/default_avatar.png'
            } else {
              return '/show/frontPic/' + this.$store.getters.id + '/' + this.restaurant.frontPic
              // return '/static/image/default_avatar.png'
            }
          }
        },
        getShopPic: function () {
          if(this.restaurant.shopPic!=null){
            if(this.restaurant.shopPic === ''){
              return '/static/image/default_avatar.png'
            }else {
              return '/show/shopPic/'+this.$store.getters.id + '/' + this.restaurant.shopPic
            }
          }
        },
        getLogo: function () {
          if(this.restaurant.logo!=null){
            if(this.restaurant.logo === ''){
              return '/static/image/default_avatar.png'
            }else{
              return '/show/logo/'+this.$store.getters.id + '/' + this.restaurant.logo
            }
          }
        }
      },
      mounted () {
        this.$nextTick(function () {

          var th = this
          // 创建Map实例
          var map = new BMap.Map("allmap");
          // 初始化地图,设置中心点坐标，
          var point = new BMap.Point(121.160724,31.173277); // 创建点坐标，汉得公司的经纬度坐标
          map.centerAndZoom(point, 15);
          map.enableScrollWheelZoom();
          var ac = new BMap.Autocomplete(    //建立一个自动完成的对象
            {
              "input": "suggestId",
              "location": map
            })
          var myValue
          ac.addEventListener("onconfirm", function (e) {    //鼠标点击下拉列表后的事件
            var _value = e.item.value;
            myValue = _value.province + _value.city + _value.district + _value.street + _value.business;
            this.address_detail = myValue
            console.log(this.address_detail)
            setPlace();
          });

          function setPlace() {
            map.clearOverlays();    //清除地图上所有覆盖物
            function myFun() {
              th.userlocation = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
              th.address_detail = myValue
              console.log("myvalue   "+th.address_detail)
              map.centerAndZoom(th.userlocation, 18);
              map.addOverlay(new BMap.Marker(th.userlocation));    //添加标注
            }

            var local = new BMap.LocalSearch(map, { //智能搜索
              onSearchComplete: myFun
            });
            local.search(myValue);

            //测试输出坐标（指的是输入框最后确定地点的经纬度）
            map.addEventListener("click",function(e){
              //经度
              console.log(th.userlocation.lng);
              //维度
              console.log(th.userlocation.lat);

            })
          }

        })
      },
      methods:{
        ...mapMutations({
          userLogin: 'login',
          setAvatar: 'changeAvatar'
        }),
        submit:function(){
          this.resSignUpForm.address = this.address_detail
          this.resSignUpForm.lng = this.userlocation.lng
          this.resSignUpForm.lat = this.userlocation.lat
          console.log("this.resSignUpForm")
          console.log(this.resSignUpForm)
          this.$ajax.post('/restaurant/sign',this.resSignUpForm).then((response) => {
            switch (response.data.code){
              case 0:
                // Cookie.set('login-token', response.data.result)
                // this.$message.success('注册成功!')
                let id = response.data.data
                let payload = {
                  userId: id,
                  username: this.resSignUpForm.resName,
                  userType: 2,
                  avatar: 'default_avatar.png'
                }
                this.userLogin(payload)
                this.step = 2
                this.$ajax.get('/restaurant/info/'+id).then((res) => {
                  let result = res.data
                  if (result.code === 0) {
                    this.restaurant = result.data
                  } else {
                    this.$message.error('注销失败')
                  }
                })
                break
              case 1:
                this.$message.error(response.message)
                break
            }
            }
          )
        },
        uploadSuccess1: function (response, file, fileList) {
          this.toggleUpload1()
          this.restaurant.frontPic = file.name
          console.log(this.restaurant.frontPic)
        },
        toggleUpload1: function () {
          this.showUpload1 = !this.showUpload1
        },
        toggleChangeAvatarBtn1: function () {
          this.showChangeAvatarBtn1 = !this.showChangeAvatarBtn1
        },
        uploadSuccess2: function (response, file, fileList) {
          this.toggleUpload2()
          this.restaurant.shopPic = file.name
        },
        toggleUpload2: function () {
          this.showUpload2 = !this.showUpload2
        },
        toggleChangeAvatarBtn2: function () {
          this.showChangeAvatarBtn2 = !this.showChangeAvatarBtn2
        },
        uploadSuccess3: function (response, file, fileList) {
          this.toggleUpload3()
          this.restaurant.logo = file.name
        },
        toggleUpload3: function () {
          this.showUpload3 = !this.showUpload3
        },
        toggleChangeAvatarBtn3: function () {
          this.showChangeAvatarBtn3 = !this.showChangeAvatarBtn3
        },
        gotoResHome: function () {
          this.setAvatar(this.restaurant.logo)
          this.$message.success('等待管理员审核')
          this.$router.push("/startPage")
        }
      }
    }
</script>

<style scoped>
  .res-back{
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
  #allmap{
    width: 400px;
    height: 400px;
    font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
    border:1px solid green;
    margin-top: 10px;
  }
  .pic-uploader{
    border: 1px dashed #409EFF;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    margin-right: 515px;
  }
  .pic-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 150px;
    height: 150px;
    line-height: 150px;
    text-align: center;
    margin-left: 10px;
  }
  .change-btn {
    /*bottom: 5px;*/
    /*left: 20px;*/
    margin: 0 5px;
    padding: 0 0;
    width: 140px;
    text-align: center;
    background-color: rgba(7, 17, 27, 0.3);
    color: #fff;
    font-size: 12px
  }
</style>
