<template xmlns:v-on="http://www.w3.org/1999/xhtml">
  <div style="width: 1000px;background-color: #ffffff;padding-bottom: 30px;margin-top: 60px;margin-left: 140px">
    <div>
      <div style="width: 520px;height: 200px;background-color: #ffffff;float: left">
        <div style="margin-left: 30px;padding-top: 20px;font-size: 20px;font-weight: bolder">订单详情</div>
        <div style="margin-left: 20px;height: 1px;width: 500px;background-color: #8cc5ff;margin-top: 20px"></div>
        <div style="margin-top: 10px;margin-left: 30px;width: 450px;background-color: #f6f6f7;height: 125px">
          <div style="margin-left: 20px">
            <div style="float: left">
              <img :src="'/show/logo/' + resInfo.id + '/' + resInfo.logo" height="100px" width="100px" style="margin-top: 13px">
            </div>
            <div>
              <div style="padding-top: 30px;font-size: 15px;font-weight: bold;margin-left: 120px">{{resInfo.resName}}</div>
              <div style="padding-top: 18px;margin-left: 120px;color: #6f7180">商家电话：{{resInfo.takeOutPhone}}</div>
            </div>
          </div>
        </div>
      </div>
      <div style="height: 180px;padding-top: 20px;padding-left: 520px">
        <div style="height: 180px;background-color: #fefdee;margin-right: 20px;border-radius:30px; border:2px solid #f8e5bb">
          <div v-if="order.orderState==='CANCEL'">
            <div style="padding-top: 50px;padding-left: 80px;font-size: 20px;font-feature-settings: revert">订单已取消</div>
            <div style="padding-top: 30px;padding-left: 80px;color: #6f7180">系统于{{order.endTime}}取消订单</div>
          </div>
          <div v-if="order.orderState==='PLACE'">
            <div style="padding-top: 40px;padding-left: 80px;font-size: 20px;font-feature-settings: revert">等待支付</div>
            <div style="padding-top: 20px;padding-left: 80px;color: #6f7180">逾期未支付订单将自动取消</div>
            <div>
              <el-button style="margin-top: 30px;margin-left: 80px;float: left" @click="cancelOrder">取消订单</el-button>
              <el-button style="margin-top: 30px;margin-left: 30px;color: white;background-color: red;font-size: 15px;font-weight: bolder" @click="pay">去支付></el-button>
            </div>
          </div>
          <div v-if="order.orderState==='PAID'">
            <div style="padding-top: 50px;padding-left: 80px;font-size: 20px;font-feature-settings: revert">订单正在配送中</div>
            <div>
              <el-button style="margin-top: 30px;margin-left: 80px;float: left" @click="cancelOrder">取消订单</el-button>
              <el-button style="margin-top: 30px;margin-left: 40px;color: white;background-color: red;font-size: 15px" @click="confirmArrive">确认到达</el-button>
            </div>
          </div>
          <div v-if="order.orderState==='DELIVERED'">
            <div style="padding-top: 50px;padding-left: 80px;font-size: 20px;font-feature-settings: revert">订单已送达</div>
            <!--<div style="padding-top: 30px;padding-left: 80px;color: #6f7180">17:53确认送达</div>-->
          </div>
        </div>
      </div>
    </div>
    <div style="height: 380px;margin-top: 30px;margin-left: 30px;margin-right: 30px;background-color: #f6f6f7;border:2px solid #f0f2f5" v-if="order.orderState==='PAID'">
      <div style="margin-left: 60px;margin-top: 20px;font-size: 18px">配送跟踪</div>
      <el-input placeholder="请输入小区、大厦或学校" id="suggestId" v-if="false"></el-input>
      <div id="allmap"></div>
    </div>
    <div style="background-color: #ffffff;margin-left: 30px;margin-top: 15px;margin-right: 30px; overflow:auto;">
      <div style="width: 500px;float: left;background-color: #e1f3d8;border:1px solid #c0ccda;">
        <div style="padding-top: 15px;padding-left: 20px;padding-bottom: 15px">
          <div style="float: left;font-size: 15px;font-weight: bolder">菜品</div>
          <div style="float: left;font-size: 15px;font-weight: bolder;padding-left: 220px">数量</div>
          <div style="font-size: 15px;font-weight: bolder;padding-left: 350px">小计（元）</div>
        </div>
        <div>
          <div v-for="(proInfo,index) in proInfos" :key="index">
            <div style="width: 500px;height: 1px;background-color: #c0ccda"></div>
            <div style="padding-left: 20px;padding-top: 15px;padding-bottom: 15px">
              <div style="float: left;font-size: 13px;color: #6f7180;">{{proInfo.name}}</div>
              <div style="float: left;font-size: 13px;padding-left: 80px;color: #6f7180">1</div>
              <div style="font-size: 13px;padding-left: 350px;color: #6f7180">{{proInfo.price}}</div>
            </div>
          </div>
          <div v-for="(pacInfo,index) in pacInfos" :key="index">
            <div style="width: 500px;height: 1px;background-color: #c0ccda"></div>
            <div style="padding-left: 20px;padding-top: 15px;padding-bottom: 15px">
              <div style="float: left;font-size: 13px;color: #6f7180;">{{pacInfo.name}}</div>
              <div style="float: left;font-size: 13px;padding-left: 80px;color: #6f7180">1</div>
              <div style="font-size: 13px;padding-left: 350px;color: #6f7180">{{pacInfo.price}}</div>
            </div>
          </div>
        </div>
        <div style="width: 500px;height: 1px;background-color: #c0ccda"></div>
        <div style="padding-left: 20px;padding-top: 15px">
          <div style="padding-bottom: 15px">
            <div style="float: left;color: #6f7180">优惠</div>
            <div style="color: red;padding-left: 350px">-{{discount}}</div>
          </div>
          <div style="padding-bottom: 15px;color: #6f7180">
            <div style="float: left;">配送费</div>
            <div style="color: #6f7180;padding-left: 350px">{{order.deliverFee}}</div>
          </div>
        </div>
        <div style="width: 500px;height: 1px;background-color: #c0ccda"></div>
        <div style="padding-left: 300px;padding-bottom: 15px">
          <div style="color: #565656;font-size: 16px;float: left;padding-top: 15px">实际支付:</div>
          <div style="color: red;font-size: 24px;padding-top: 12px;padding-left: 80px">{{order.sum}}</div>
        </div>
      </div>
      <div style="margin-left: 520px;background-color: #f6f6f7;padding-top: 15px;padding-left: 20px;padding-bottom: 15px">
        <div style="font-weight: bolder">配送信息</div>
        <div style="padding-top: 15px">配送方式：	{{resInfo.resName}}提供配送服务</div>
        <div style="width: 400px;height: 1px;background-color: #c0ccda;margin-top: 15px;margin-bottom: 15px"></div>
        <div>联 系 人：	{{address.contactName}}(先生)</div>
        <div style="padding-top: 15px">联系电话：	{{address.phone}}</div>
        <div style="padding-top: 15px">收货地址：	{{address.address}}</div>
        <div style="width: 400px;height: 1px;background-color: #c0ccda;margin-top: 15px;margin-bottom: 15px"></div>
        <div>备 注：	{{order.remarks}}</div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "",
    data(){
      return{
        order:{},
        resInfo:{},
        proInfos:[],
        pacInfos:[],
        address:{},
        discount:0.0
      }
    },
    mounted(){
      this.getOrder()
      this.$nextTick(function () {
        this.loadAddress()
      })
    },
    methods:{
      cancelOrder: function () {
        this.$ajax.get('/order/cancelOrder/'+this.order.id).then((res) =>{
          let result = res.data;
          if(result.code === 0){
            let refund = result.data
            if(refund === 0.0) {
              this.$message.success("退订成功")
            }else{
              this.$message.success("退订成功，返还金额为"+refund+"元")
            }
            this.$router.push('/home')
          } else {
            this.$message.error('取消订单失败')
          }
        })
      },
      confirmArrive: function () {
        this.$ajax.get('/order/confirmArrive/'+this.order.id).then((res) =>{
          let result = res.data;
          if(result.code === 0){
            this.$message.success("确认到达")
            this.$router.push('/home')
          } else {
            this.$message.error('确认到达失败')
          }
        })
      },
      pay: function () {
        this.$ajax.get('/order/getOrderInfo/'+this.order.id).then((res) =>{
          let result = res.data;
          if(result.code === 0){
            localStorage.setItem('orderResult',JSON.stringify(result.data))

            this.$ajax.get('/user/getAddress/'+this.order.userAddressId).then((response) =>{
              response = response.data;
              if(response.code === 0){
                localStorage.setItem('userAddress',JSON.stringify(response.data))
              } else {
                this.$message.error("获取地址失败")
              }
            })

            this.$router.push('/pay')
          } else {
            this.$message.error("获取订单失败")
          }
        })
      },
      loadAddress: function () {
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
          console.log("ncsncjdsthis")
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
      getOrder: function () {
        let order = localStorage.getItem('orderConcrete')
        if(order){
          this.order = JSON.parse(order)
          this.$ajax.get('/restaurant/info/'+this.order.resId).then((res) =>{
            let result = res.data;
            if(result.code === 0){
              this.resInfo = result.data
            } else {
              this.$message.error('获取信息失败')
            }
          })
          for(let i = 0 ; i < this.order.productNames.length ; i++){
            this.$ajax.get('/order/getProOrPacImage',{
              params: {
                name:this.order.productNames[i],
                resId:this.order.resId,
                placeTime:this.order.placeTime
              }
            }).then((res) =>{
              let result = res.data;
              if(result.code === 0){
                this.proInfos.push(result.data)
              } else {
                this.$message.error('获取信息失败')
              }
            })
          }
          for(let i = 0 ; i < this.order.packageNames.length ; i++){
            this.$ajax.get('/order/getProOrPacImage',{
              params: {
                name:this.order.packageNames[i],
                resId:this.order.resId,
                placeTime:this.order.placeTime
              }
            }).then((res) =>{
              let result = res.data;
              if(result.code === 0){
                this.pacInfos.push(result.data)
              } else {
                this.$message.error('获取信息失败')
              }
            })
          }
          this.$ajax.get('/user/getAddress/'+this.order.userAddressId).then((res) =>{
            let result = res.data;
            if(result.code === 0){
              this.address = result.data
            } else {
              this.$message.error('获取信息失败')
            }
          })
          this.discount = (this.order.price+this.order.deliverFee-this.order.sum).toFixed(2)
        }
      }
    }
  }
</script>

<style scoped>
  #allmap{
    width: 300px;
    height: 300px;
    margin-left: 300px;
    margin-top: 30px;
    font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
    border:1px solid green;
  }
</style>
