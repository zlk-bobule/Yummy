<template xmlns:v-on="http://www.w3.org/1999/xhtml">
  <div class="choose">
    <i style="color: #888888">当前位置：</i>
    <a style="color: #000000">{{address_detail}}</a>
    <a href="javascript:;" v-on:click="showRemarkPic" style="margin-left: 10px">[切换位置]</a><br>
    <div class="resType">
      <label style="color: #888888">商家分类:</label>
      <div class="types">
        <el-radio-group v-model="chooseType" size="medium">
          <el-radio-button :label="type" v-for="type in this.types" :key="type"></el-radio-button>
        </el-radio-group>
      </div>
    </div>
    <div class="res">
      <div class="resCard" v-for="(resInfo,index) in resInfos" :key="index" @click="enterRes(index)" v-if="resInfo.deliverMin<=50">
        <el-card :body-style="{ padding: '0px' }">
          <div class="rstblock-logo">
            <img :src="'/show/logo/' + resInfo.id + '/' + resInfo.logo" width="70" height="70" class="rstblock-logo-icon">
            <span>{{resInfo.deliverMin}} 分钟</span>
          </div>
          <div class="rstblock-content">
            <div class="rstblock-title">{{resInfo.resName}}</div>
            <!--<div class="starrating icon-star">-->
              <!--<span class="icon-star" style="width:90%;"></span>-->
            <!--</div>-->
            <div class="rstblock-cost">配送费¥{{resInfo.deliverFee}}</div>
            <div class="rstblock-schedule">可预订，起送价￥{{resInfo.initialDeliverPrice}}</div>
          </div>
        </el-card>
      </div>
    </div>

    <el-dialog title="切换地址" :visible.sync="dialogRemarkImg" width="700px" center :append-to-body='true'>
      <el-form label-width="110px" label-position="left" size="medium">
        <el-form-item label="详细地址">
          <div>
            <el-input v-model="address_detail" placeholder="请输入小区、大厦或学校" id="suggestId" class="el-form-width"></el-input>
            <div id="allmap"></div>
          </div>
        </el-form-item>
        <el-form-item>
          <el-button style="background-color: #5daf34;color: #ffffff;width: 150px" @click="saveAddress">保存</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

  </div>
</template>

<script>
  import Vue from 'vue';
  import 'vue-area-linkage/dist/index.css'; // v2 or higher
  import VueAreaLinkage from 'vue-area-linkage';
  Vue.use(VueAreaLinkage)

    export default {
        watch: {
          'chooseType' : function () {
            this.getResInfos()
          }
        },
        data() {
            return {
              chooseType:'全部',
              types: ['全部','烧烤', '炸鸡炸串','小龙虾' , '地方小吃', '零食', '奶茶果汁','甜品','咖啡'],
              resInfos:[],
              dialogRemarkImg:false,
              address_detail:'南京市鼓楼区南京大学(鼓楼校区)',
              userlocation: {lng: 118.786007, lat: 32.06143},
            }
        },
        computed: {
          getResAvatar: function (resInfo) {
            if (resInfo.logo === '') {
              return '/static/image/res_default.png'
            } else {
              return '/show/logo/' + resInfo.id + '/' + resInfo.logo
            }
          }
        },
        mounted () {
          this.getResInfos()
        },
        methods: {
          getResInfos: function () {
            let type = this.chooseType
            this.$ajax.get('/restaurant/getInfos/'+type).then((res) =>{
              let result = res.data;
              if(result.code === 0){
                this.resInfos = result.data
                for(let i = 0 ; i < this.resInfos.length ; i++){
                  if(!this.resInfos[i].deliverMin){
                    let EARTH_RADIUS = 6378137
                    let radLat1 = this.userlocation.lat * Math.PI / 180.0
                    let radLat2 = this.resInfos[i].lat * Math.PI / 180.0
                    let radLon1 = this.userlocation.lng * Math.PI / 180.0
                    let radLon2 = this.resInfos[i].lng * Math.PI / 180.0

                    if (radLat1 < 0)
                      radLat1 = Math.PI / 2 + Math.abs(radLat1);// south
                    if (radLat1 > 0)
                      radLat1 = Math.PI / 2 - Math.abs(radLat1);// north
                    if (radLon1 < 0)
                      radLon1 = Math.PI * 2 - Math.abs(radLon1);// west
                    if (radLat2 < 0)
                      radLat2 = Math.PI / 2 + Math.abs(radLat2);// south
                    if (radLat2 > 0)
                      radLat2 = Math.PI / 2 - Math.abs(radLat2);// north
                    if (radLon2 < 0)
                      radLon2 = Math.PI * 2 - Math.abs(radLon2);// west
                    let x1 = EARTH_RADIUS * Math.cos(radLon1) * Math.sin(radLat1);
                    let y1 = EARTH_RADIUS * Math.sin(radLon1) * Math.sin(radLat1);
                    let z1 = EARTH_RADIUS * Math.cos(radLat1);

                    let x2 = EARTH_RADIUS * Math.cos(radLon2) * Math.sin(radLat2);
                    let y2 = EARTH_RADIUS * Math.sin(radLon2) * Math.sin(radLat2);
                    let z2 = EARTH_RADIUS * Math.cos(radLat2);

                    let d = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)+ (z1 - z2) * (z1 - z2));
                    //余弦定理求夹角
                    let theta = Math.acos((EARTH_RADIUS * EARTH_RADIUS + EARTH_RADIUS * EARTH_RADIUS - d * d) / (2 * EARTH_RADIUS * EARTH_RADIUS));
                    let dist = theta * EARTH_RADIUS;
                    let min = Math.round(dist/14)

                    Vue.set(this.resInfos[i],'deliverMin',min)
                  }
                }
              } else {
                this.$message.error('获取信息失败')
              }
            })
          },
          enterRes: function(index) {
            let resInfo = this.resInfos[index]
            localStorage.setItem('resInfo',JSON.stringify(resInfo))
            // localStorage.setItem('resState', 'new')
            this.$router.push('/resDetail')
          },
          showRemarkPic: function () {
            var self = this;
            self.dialogRemarkImg = true
            setTimeout(function () {//等组件生成再调用setActiveItem(index);
              self.loadAddress()
            }, 500);
          },
          loadAddress: function () {
            var th = this
            // 创建Map实例
            var map = new BMap.Map("allmap");
            // 初始化地图,设置中心点坐标，
            var point = new BMap.Point(118.786007,32.06143); // 创建点坐标，汉得公司的经纬度坐标
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
          },
          saveAddress: function () {
            this.dialogRemarkImg = false;
            this.getResInfos()
          },
          getResAvatar: function (resInfo) {
            if (resInfo.logo === '') {
              return '/static/image/res_default.png'
            } else {
              return '/show/logo/' + resInfo.id + '/' + resInfo.logo
            }
          }
        }
    }

</script>

<style>
  .choose{
    padding: 70px 0 100px 30px
  }
  .resType{
    width: 1300px;
    margin-bottom: 15px;
    padding: 10px 20px 10px 20px;
    background-color: #fff;
    margin-top: 20px;
    margin-left: 10px;
  }
  .types{
    position: relative;
    margin: 0 auto;
    margin-top: 20px;
  }
  .res{
    width: 1300px;
    background-color: #fff;
    margin-bottom: 15px;
    margin-left: 10px;
    padding: 10px 20px 10px 20px;

    display: flex;
    flex-wrap: wrap
  }
  .resCard{
    background-color: #fff;
    border-bottom: 1px #f5f5f5 solid;
    width: 25%;
    height: 140px;
    font-size: 12px;
    position: relative;
  }
  .rstblock-logo {
    padding: 20px;
    color: #999;
    text-align: center;
    float: left;
    font-size: 12px;
    position: relative;
  }
  .rstblock-logo-icon {
    display: block;
    width: 70px;
    height: 70px;
    margin-bottom: 10px;
  }
  .image-wrapper {
    width: 100%;
    height: 150px;
    overflow: hidden;

  }
  .image{
    width: 100%;
  }
  .rstblock-content {
    padding: 20px;
  }
  .rstblock-title {
    font-size: 16px;
    margin-bottom: 6px;
    font-weight: 600;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    color: #333;
  }
  .rstblock-cost {
    color: #999;
    margin-top: 15px;
    font-size: 12px;
  }
  .rstblock-schedule {
    background-color: #fff;
    color: #14ae18;
    border: 1px solid #14ae18;
    line-height: 18px;
    margin-top: 15px;
    margin-left: 80px;
  }
  #allmap{
    width: 400px;
    height: 400px;
    font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
    border:1px solid green;
    margin-top: 20px;
  }
  .el-form-width{
    width: 300px;
  }
</style>
