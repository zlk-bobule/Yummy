<template>
  <div class="resInfo">
    <div class="article">
      <div class="title">餐厅信息</div>
      <el-form v-model="restaurant" label-width="110px" label-position="left" size="medium" ref="resForm">
        <el-form-item label="识别码" prop="identifyCode">
          <span style="color: #6f7180">{{ restaurant.identifyCode }}</span>
        </el-form-item>
        <el-form-item label="餐厅名称" prop="resName">
          <el-input v-model="restaurant.resName" placeholder="输入餐厅名称"></el-input>
        </el-form-item>
        <el-form-item label="外卖电话" prop="takeOutPhone">
          <el-input v-model="restaurant.takeOutPhone" placeholder="输入外卖电话"></el-input>
        </el-form-item>
        <el-form-item label="联系人姓名" prop="contactName">
          <el-input v-model="restaurant.contactName" placeholder="输入联系人姓名"></el-input>
        </el-form-item>
        <el-form-item label="联系人电话" prop="contactPhone">
          <el-input v-model="restaurant.contactPhone" placeholder="输入联系人电话"></el-input>
        </el-form-item>
        <el-form-item label="门店分类" prop="types">
          <el-select v-model="restaurant.types" multiple>
            <el-option
              v-for="type in types"
              :key="type.value"
              :label="type.value"
              :value="type.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="详细地址" prop="address">
          <div id="all">
          <el-input v-model="restaurant.address" placeholder="请与执照地址一致" id="suggestId" name="restaurant.address" class="input_style"></el-input>
            <div id="allmap"></div>
          </div>
        </el-form-item>
        <el-form-item label="门脸照" style="margin-bottom: 60px">
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

        <el-form-item label="店内照" style="margin-bottom: 60px">
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

        <el-form-item label="logo" style="margin-bottom: 60px">
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
          <el-button @click="modify" type="primary" round>保存</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
  import {mapMutations, mapGetters} from 'vuex'

    export default {
        data() {
            return {
              restaurant: {
                identifyCode:'',
                resName:'',
                takeOutPhone:'',
                contactName:'',
                contactPhone:'',
                types: [],
                address: '',
                lng:'',
                lat:'',
                frontPic:'',
                shopPic:'',
                logo:'',
                userlocation: {lng: "", lat: ""}
              },
              showUpload1:false,
              showChangeAvatarBtn1: false,
              showUpload2:false,
              showChangeAvatarBtn2: false,
              showUpload3:false,
              showChangeAvatarBtn3: false,
              types: [{value: '烧烤'},{value: '炸鸡炸串'},{value: '小龙虾'},{value: '地方小吃'},{value: '零食'},{value: '川湘菜'},{value: '粤菜'},{value: '东北菜'},{value: '云南菜'},{value: '江浙菜'},{value: '西北菜'},{value: '奶茶果汁'},{value: '甜品'},{value: '咖啡'},{value: '盖浇饭'},{value: '汉堡'},{value: '米粉面馆'},{value: '麻辣烫'},{value: '包子粥店'},{value: '生煎锅贴'},{value: '饺子混沌'},{value: '香辣砂锅'},{value: '简餐'},{value: '盖浇饭'}]
            }
        },
      computed: {
      ...mapGetters([
          'id'
        ]),
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
        let resId = this.$store.getters.id
        this.$ajax.get('/restaurant/info/' + resId).then((res) => {
          let result = res.data
          if (result.code === 0) {
            this.restaurant = result.data
          } else {
            this.$message.error('查看个人信息失败！')
          }
        })

        this.$nextTick(function () {

          var th = this
          // 创建Map实例
          var map = new BMap.Map("allmap");
          // 初始化地图,设置中心点坐标，
          var point = new BMap.Point(121.477358,31.238494); // 创建点坐标
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
            setPlace();
          });

          function setPlace() {
            map.clearOverlays();    //清除地图上所有覆盖物
            function myFun() {
              th.restaurant.userlocation = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
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
              console.log(th.restaurant.userlocation.lng);
              //维度
              console.log(th.restaurant.userlocation.lat);

            })
          }

        })
      },
      methods: {
        ...mapMutations({
          resModify: 'login'
        }),
        modify:function(){
          let resId = this.$store.getters.id
          this.$ajax.post('/restaurant/modify/'+resId,this.restaurant).then((response) => {
              switch (response.data.code){
                case 0:
                  // Cookie.set('login-token', response.data.result)
                  this.$message.success('修改成功，请等经理审核!')
                  let payload = {
                    userId: resId,
                    username: this.restaurant.resName,
                    userType: 2,
                    avatar: this.restaurant.logo
                  }
                  this.resModify(payload)
                  this.$router.push('/login')
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
          this.$router.push("/resHome")
        }
      }
    }

</script>

<style>
  .resInfo{
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
    margin-top: 0;
    margin-left: 5px;
    margin-right: 5px;
    margin-bottom: 10px;
    padding: 0 0;
    width: 140px;
    text-align: center;
    background-color: rgba(7, 17, 27, 0.3);
    color: #fff;
    font-size: 12px
  }
</style>
