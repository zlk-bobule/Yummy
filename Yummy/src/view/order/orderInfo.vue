<template xmlns:v-on="http://www.w3.org/1999/xhtml">
  <div>
    <div class="orderTop">
      <div class="orderTopInfo">
        <div class="title">订单信息</div>
      </div>
    </div>
    <div>
    <div class="orderDetail">
      <div style="height: 100%">
        <div style="display: flex;padding: 20px;height: 30px;border-bottom: 1px solid #eee;width: 100%">
          <h2 style="font-size: 16px;font-weight: 700;color: #333;">订单详情</h2>
          <a href="javascript:;" v-on:click="returnLast" style="padding-left: 140px; padding-top: 3px">&lt; 返回商家修改</a>
        </div>
        <div style="display: flex;font-size: 12px;font-weight: 700;color: #999;table-layout: fixed;height: 48px;line-height: 48px;padding: 5px 18px 0 20px;width: 100%;">
          <div class="table-top">商品</div>
          <div class="table-top">份数</div>
          <div class="table-top">小计（元）</div>
        </div>
        <div>
          <div class="cart-content" v-for="(cartPro,index) in cartPros" :key="index">
            <div class="cart-name">{{cartPro.name}}</div>
            <div class="cart-bnt-group">
              <el-input-number v-model="cartPro.number" :min="0" size="small" @change="getSum"></el-input-number>
            </div>
            <div class="cart-price">
              ￥{{cartPro.price}}
            </div>
          </div>
          <div class="cart-content" v-for="(cartPac,index) in cartPacs" :key="index">
            <div class="cart-name">{{cartPac.name}}</div>
            <div class="cart-bnt-group">
              <el-input-number v-model="cartPac.number" :min="0" size="small" @change="getSum"></el-input-number>
            </div>
            <div class="cart-price">
              ￥{{cartPac.price}}
            </div>
          </div>
        </div>
        <div style="display: flex;height: 30px;width: 100%">
          <div style="padding: 15px 0 10px 25px;font-size: 12px;color: #999">配送费</div>
          <div style="padding: 15px 0 10px 236px;color: #999">￥{{order.deliverFee}}元</div>
        </div>
        <div style="display: flex;height: 30px;width: 100%">
          <div style="padding: 25px 0 10px 25px;font-size: 12px;color: #999">满减</div>
          <div style="padding: 25px 0 10px 248px;color: #999">￥-{{reduction}}元</div>
        </div>
        <div style="display: flex;height: 30px;width: 100%" v-if="isNewUserRedShow === false">
          <div style="padding: 25px 0 10px 25px;font-size: 12px;color: #999">本店新用户立减</div>
          <div style="padding: 25px 0 10px 188px;color: #999">￥-{{newUserInResReduction}}元</div>
        </div>
        <div style="display: flex;height: 100px;width: 100px;padding-top: 20px;">
          <div style="color: red;padding-top: 50px;padding-left: 220px;font-size: 30px">￥</div>
          <div style="color: red;padding-top: 35px;font-size: 50px" v-if="isNewUserRedShow === false">{{order.sum-reduction-newUserInResReduction}}</div>
          <div style="color: red;padding-top: 35px;font-size: 50px" v-if="isNewUserRedShow === true">{{order.sum-reduction}}</div>
        </div>
      </div>
    </div>
    <div style="background-color: #ffffff;margin-left: 20px;margin-top: 20px;float:left;width: 600px">
      <div style="display: flex;padding: 20px;height: 30px;width: 100%">
        <h2 style="font-size: 16px;font-weight: 700;color: #333;">收货地址</h2>
        <a style="padding-left: 360px; padding-top: 3px">大致送达时间：{{min}}分钟</a>
      </div>
      <div style="margin-left: 30px;margin-right: 30px;height: 70px;border: 1px solid #67c23a;" v-if="userAddressList.length>0">
        <div style="width: 70px;border-right: 1px solid #67c23a;height: 70px;float: left">
          <img src="/static/image/location.png" height="40px" width="40px" style="padding-left: 20px;padding-top: 10px">
        </div>
        <div style="float: left;padding-left: 30px;color: #333333">
          <div style="float: left">
            <div style="padding-top: 15px;padding-right: 10px">
              <div style="font-weight: 700;float: left;">{{userAddress.contactName}}</div>
              <div style="font-weight: 700;float: left;margin-left: 10px" v-if="userAddress.gender==='MALE'">先生</div>
              <div style="font-weight: 700;float: left;margin-left: 10px" v-if="userAddress.gender==='FEMALE'">女士</div>
              <div style="font-weight: 700;float: left;margin-left: 20px;">{{userAddress.phone}}</div>
            </div>
            <!--<div style="padding-top: 15px;font-weight: 700">zlk 先生 18852085502</div>-->
            <div style="font-weight: 700;margin-top: 10px;float: left;width: 350px">{{userAddress.address}}</div>
          </div>
          <div style="float: left ;margin-top: 25px;padding-left: 20px">
            <a href="javascript:;" v-on:click="modifyAddress">修改</a>
          </div>
        </div>
      </div>
      <div style="margin-top: 15px;margin-left: 30px">
        <a href="javascript:;" v-on:click="chooseAddress">选择地址</a>
      </div>
      <div style="margin-top: 30px;margin-left: 20px">
        <h2 style="font-size: 16px;font-weight: 700;color: #333;">支付方式</h2>
        <div style="border: 1px solid #3a8ee6;height: 70px;margin-top: 30px;margin-left: 10px;width: 250px">
          <div style="font-weight: 500;padding-top: 12px;padding-left: 20px">在线支付</div>
          <div style="color: #888888;padding-top: 10px;padding-left: 20px">支持网银支付</div>
        </div>
      </div>
      <div style="margin-top: 30px;margin-left: 20px;">
        <h2 style="font-size: 16px;font-weight: 700;color: #333;">优惠</h2>
        <div style="margin-top: 15px;margin-left: 10px;width: 100%">
          <div style="float: left;color: #333333;font-weight: 300">您是</div>
          <div style="padding-left: 40px;color: #67c23a;font-weight: bolder;font-size: 18px">{{userLevel}}</div>
        </div>
      </div>
      <div style="color: orange;margin-top: 10px;margin-left: 30px">
        您享有{{userDiscount}}%的折扣
      </div>
      <div style="margin-top: 20px;margin-left: 20px">
        <h2 style="font-size: 16px;font-weight: 700;color: #333;">订单备注</h2>
        <div style="margin-top: 10px;margin-right: 20px">
          <el-input v-model="order.remarks" type="textarea" :autosize="{ minRows: 2}" placeholder="备注"></el-input>
        </div>
      </div>
      <div style="margin-top: 30px;margin-left: 30px;margin-bottom: 20px">
        <el-button style="background-color: #67c23a;color: white;font-weight: bolder;font-size: 20px" @click="submit">确认下单</el-button>
      </div>
    </div>
    </div>

    <el-dialog title="选择地址" :visible.sync="dialogRemarkImg1" width="700px" center :append-to-body='true'>
      <div style="width: 98%;height: 120px;box-shadow: 1px 1px 5px #888888;margin-right: 2%;" v-for="(address,index) in userAddressList" :key="index" @click="chooseOrderAddress(index)">
        <div style="padding-top: 10px;padding-left: 20px">
          <div style="float: left">{{address.contactName}}</div>
          <div style="padding-left: 40px;float: left;" v-if="userAddress.gender==='MALE'">先生</div>
          <div style="padding-left: 40px;float: left;" v-if="userAddress.gender==='FEMALE'">女士</div>
        </div>
        <div style="padding-top: 15px;padding-left: 20px">
          <div>{{address.address}}</div>
        </div>
        <div style="padding-top: 5px;padding-left: 20px">
          <div>{{address.houseNumber}}</div>
        </div>
        <div style="padding-top: 10px;padding-left: 20px">
          <div>{{address.phone}}</div>
        </div>
        <div style="padding-top: 10px;padding-left: 20px" v-if="judgeDistance(address)">
          <div style="color: red">距离过远，无法送达</div>
        </div>
      </div>
    </el-dialog>

    <el-dialog title="修改地址" :visible.sync="dialogRemarkImg2" width="700px" center :append-to-body='true'>
      <el-form v-model="userAddress" label-width="110px" label-position="left" size="medium" ref="userAddress">
        <el-form-item label="姓名" prop="contactName">
          <el-input v-model="userAddress.contactName" placeholder="请输入您的姓名" class="el-form-width"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="userAddress.gender">
            <el-option value="男" selected>男</el-option>
            <el-option value="女" >女</el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="详细地址">
          <div>
            <el-input v-model="address_detail" placeholder="请输入小区、大厦或学校" id="suggestId" name="userAddress.address" class="el-form-width"></el-input>
            <div id="allmap"></div>
          </div>
        </el-form-item>
        <el-form-item label="详细地址" prop="houseNumber">
          <el-input v-model="userAddress.houseNumber" placeholder="单元、门牌号" class="el-form-width"></el-input>
        </el-form-item>
        <el-form-item label="详细地址" prop="phone">
          <el-input v-model="userAddress.phone" placeholder="请输入您的手机号" class="el-form-width"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button style="background-color: #a4da89;color: #ffffff;width: 150px" @click="confirmModify">保存</el-button>
          <a href="javascript:;" v-on:click="cancelDialog" style="margin-left: 20px">取消</a>
        </el-form-item>
      </el-form>
    </el-dialog>

  </div>
</template>

<script>
  import Vue from 'vue'
    export default {
      name: "",
      mounted() {
        this.getOrderInfo()
        this.getAddresses()
      },
      data () {
        return{
          order: {
            userId:0,
            resId:0,
            productNames:[],
            packageNames:[],
            price:0.0,
            deliverFee:0.0,
            sum:0.0,
            remarks:'',
            userAddressId:0
          },
          cartPros:[],
          cartPacs:[],
          userAddressList:[],
          userAddress:{},
          dialogRemarkImg1:false,
          dialogRemarkImg2:false,
          address_detail: null, //详细地址
          userlocation: {lng: "", lat: ""},
          user:{},
          userLevel:'',
          userDiscount:'',
          resDiscount:{},
          reduction:0.0,
          newUserInResReduction:0.0,
          isNewUserRedShow:true,
          resInfo:{},
          min:0.0
        }
      },
      methods: {
        returnLast: function () {
          this.$router.push('/resDetail')
        },
        getOrderInfo: function () {
          let orderInfo = localStorage.getItem('orderInfo')
          let newOrderInfo
          if(orderInfo){
             newOrderInfo = JSON.parse(orderInfo)
          }
          let proNames = []
          for(let i = 0 ; i < newOrderInfo.cartProInfos.length ; i++){
            for(let j = 0 ; j < newOrderInfo.cartProInfos[i].number ; j++){
              proNames.push(newOrderInfo.cartProInfos[i].name);
            }
          }
          let pacNames = []
          for(let i = 0 ; i < newOrderInfo.cartPacInfos.length ; i++){
            for(let j = 0 ; j < newOrderInfo.cartPacInfos[i].number ; j++){
              pacNames.push(newOrderInfo.cartPacInfos[i].name);
            }
          }
          this.order.userId = newOrderInfo.userId
          this.order.resId = newOrderInfo.resId

          this.$ajax.get('/restaurant/info/'+this.order.resId).then((response) => {
              response = response.data
              if(response.code === 0){
                this.resInfo = response.data
              }else{
                this.$message.error("未能获取餐厅信息")
              }
            }
          )

          this.$ajax.get('/restaurant/getDiscount/'+this.order.resId).then((response) => {
              switch (response.data.code){
                case 0:
                  // Cookie.set('login-token', response.data.result)
                  // this.$message.success('下单成功!')
                  this.resDiscount = response.data.data
                  this.order.productNames = proNames
                  this.order.packageNames = pacNames
                  this.order.price = newOrderInfo.price
                  this.order.deliverFee = newOrderInfo.deliverFee
                  this.order.sum = newOrderInfo.sum
                  let fullReduction = this.resDiscount.fullReduction
                  console.log(fullReduction)
                  for(let i = fullReduction.length-1 ; i >= 0 ; i--){
                    if(this.order.sum >= fullReduction[i][0]){
                      this.reduction = fullReduction[i][1]
                      break;
                    }
                  }
                  this.newUserInResReduction = this.resDiscount.newUserInResReduction

                  this.$ajax.get('/order/judgePlace', {
                    params:{
                      userId:this.order.userId,
                      resId:this.order.resId
                    }
                  }).then((response) => {
                      switch (response.data.code) {
                        case 0:    //成功
                          this.isNewUserRedShow = true
                          console.log("isNewUserRedShow true")
                          break
                        case 1:    //失败
                          this.isNewUserRedShow = false
                          console.log("isNewUserRedShow false")
                          break
                      }
                    }
                  )

                  this.cartPros = newOrderInfo.cartProInfos
                  this.cartPacs = newOrderInfo.cartPacInfos
                  break
                case 4:
                  this.$message.error(response.data.message)
                  break
              }
            }
          )
          // this.order.productNames = proNames
          // this.order.packageNames = pacNames
          // this.order.price = newOrderInfo.price
          // this.order.deliverFee = newOrderInfo.deliverFee
          // this.order.sum = newOrderInfo.sum
          // let fullReduction = this.resDiscount.fullReduction
          // console.log(fullReduction)
          // for(let i = 0 ; i < fullReduction.length ; i++){
          //   if(this.order.sum >= fullReduction[i]){
          //     this.reduction = fullReduction[1]
          //     break;
          //   }
          // }
          // this.cartPros = newOrderInfo.cartProInfos
          // this.cartPacs = newOrderInfo.cartPacInfos
          this.$ajax.get('/user/info/' + newOrderInfo.userId).then((res) => {
            let result = res.data
            if (result.code === 0) {
              this.user = result.data
              let userLevel = this.user.userLevel
              switch (userLevel){
                case 'FIRSTCLASS':
                  this.userLevel = '一级会员'
                  this.userDiscount = 98
                  break
                case 'SECONDCLASS':
                  this.userLevel = '二级会员'
                  this.userDiscount = 96
                  break
                case 'THREECLASS':
                  this.userLevel = '三级会员'
                  this.userDiscount = 94
                  break
                case 'FOURCLASS':
                  this.userLevel = '四级会员'
                  this.userDiscount = 92
                  break
                case 'FIVECLASS':
                  this.userLevel = '五级会员'
                  this.userDiscount = 90
                  break
              }
            } else {
              this.$message.error('查看个人信息失败！')
            }
          })
        },
        getSum: function () {
          let sumPrice = 0
          for(let i = 0 ; i < this.cartPros.length ; i++){
            sumPrice = sumPrice + this.cartPros[i].price*this.cartPros[i].number
          }
          for(let i = 0 ; i < this.cartPacs.length ; i++){
            sumPrice = sumPrice + this.cartPacs[i].price*this.cartPacs[i].number
          }
          this.order.sum = sumPrice + this.order.deliverFee
          let fullReduction = this.resDiscount.fullReduction
          for(let i = fullReduction.length-1 ; i >= 0 ; i--){
            if(this.order.sum >= fullReduction[i][0]){
              this.reduction = fullReduction[i][1]
              break;
            }
          }

          let newCartPros = [];
          for(let i = 0 ; i < this.cartPros.length ; i++){
            if(this.cartPros[i].number > 0){
              newCartPros.push(this.cartPros[i])
            }
          }
          this.cartPros = newCartPros

          let newCartPacs = [];
          for(let i = 0 ; i < this.cartPacs.length ; i++){
            if(this.cartPacs[i].number > 0){
              newCartPacs.push(this.cartPacs[i])
            }
          }
          this.cartPacs = newCartPacs
        },
        submit: function () {
          if(!this.isNewUserRedShow){
            this.order.sum = (this.order.sum * this.userDiscount / 100-this.reduction-this.newUserInResReduction).toFixed(2)
          }else{
            this.order.sum = (this.order.sum * this.userDiscount / 100-this.reduction).toFixed(2)
          }
          console.log("here")
          console.log(this.order)
          this.$ajax.post('/order/place',this.order).then((response) => {
            switch (response.data.code){
              case 0:
                // Cookie.set('login-token', response.data.result)
                this.$message.success('下单成功!')
                if(!this.order.id){
                  Vue.set(this.order,'id',response.data.data)
                }
                localStorage.setItem('orderResult',JSON.stringify(this.order))
                localStorage.setItem('cartPros',JSON.stringify(this.cartPros))
                localStorage.setItem('cartPacs',JSON.stringify(this.cartPacs))
                localStorage.setItem('userAddress',JSON.stringify(this.userAddress))
                this.$router.push('/pay')
                break
              case 1:
                this.$message.error("订单有商品超出库存")
                break
            }
            }
          )
        },
        getAddresses: function () {
          let userId = this.$store.getters.id
          console.log("userId"+userId)
          this.$ajax.get('/user/getAddressList/' + userId).then((res) => {
            let result = res.data
            if (result.code === 0) {
              this.userAddressList = result.data
              this.getAddress()
            } else {
              this.$message.error("不能得到地址")
            }
          })
        },
        getAddress: function () {
          if(this.userAddressList.length > 0) {
            this.userAddress = this.userAddressList[0]
            this.order.userAddressId = this.userAddressList[0].id
            this.getMin(this.userAddress,this.resInfo)
          }
        },
        chooseAddress: function () {
          var self = this;
          self.dialogRemarkImg1 = true;//显示弹框
        },
        chooseOrderAddress: function (index) {
          if(!this.judgeDistance(this.userAddressList[index])) {
            this.userAddress = this.userAddressList[index]
            this.order.userAddressId = this.userAddressList[index].id
            this.getMin(this.userAddress,this.resInfo)
            this.dialogRemarkImg1 = false;
          }
        },
        modifyAddress: function () {
          var self = this;
          self.dialogRemarkImg2 = true;//显示弹框
          setTimeout(function () {//等组件生成再调用setActiveItem(index);
            self.loadAddress()
          }, 500);
        },
        confirmModify: function () {
          this.userAddress.lng = this.userlocation.lng
          this.userAddress.lat = this.userlocation.lat
          this.userAddress.address = this.address_detail
          if(this.userAddress.gender === '男'){
            this.userAddress.gender = 'MALE'
          }else{
            this.userAddress.gender = 'FEMALE'
          }
          this.$ajax.post('/user/modifyAddress', this.userAddress).then((response) => {
              switch (response.data.code) {
                case 0:
                  // Cookie.set('login-token', response.data.result)
                  this.$message.success('修改成功!')
                  this.dialogRemarkImg2 = false
                  this.getAddresses()
                  break
                case 1:
                  this.$message.error(response.message)
                  break
              }
            }
          )
        },
        cancelDialog: function () {
          this.dialogRemarkImg2 = false
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
        judgeDistance: function (address) {
          let EARTH_RADIUS = 6378137
          let radLat1 = address.lat * Math.PI / 180.0
          let radLat2 = this.resInfo.lat * Math.PI / 180.0
          let radLon1 = address.lng * Math.PI / 180.0
          let radLon2 = this.resInfo.lng * Math.PI / 180.0

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
          if(min > 70){
            return true
          } else{
            return false
          }
        },
        getMin: function (address1,address2) {
          let EARTH_RADIUS = 6378137
          let radLat1 = address1.lat * Math.PI / 180.0
          let radLat2 = address2.lat * Math.PI / 180.0
          let radLon1 = address1.lng * Math.PI / 180.0
          let radLon2 = address2.lng * Math.PI / 180.0

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
          this.min = Math.round(dist/14)
        }
      }
    }
</script>

<style scoped>
  .orderTop{
    padding: 40px 0 20px 0;
    background-color: #ffffff;
  }
  .orderTopInfo{
    position: relative;
    display: table-cell;
    vertical-align: middle;
  }
  .title{
    padding-left: 15px;
    border-left: 3px solid #409eff;
    color: #000;
    font-size: 20px;
    margin-left: 140px;
    margin-top: 20px;
  }
  .orderDetail{
    background-color: #ffffff;
    /*margin: 20px 900px 0 140px;*/
    /*height: 480px;*/
    margin-top: 20px;
    /*margin-right: 880px;*/
    margin-left: 140px;
    margin-bottom: 0;
    width: 400px;
    float:left;
  }
  .table-top{
    border: 0;
    padding: 5px 0 3px 10px;
    width: 45%;
    font-size: 12px;
    line-height: 1.3;
  }
  .cart-content{
    display: table;
    table-layout: fixed;
    background-color: #fcfaf8;
    height: 48px;
    line-height: 48px;
    /*padding: 0 18px 0 20px;*/
    width: 100%;
    font-size: 14px;
    color: #666;
  }
  .cart-name{
    padding: 5px 0 3px 10px;
    width: 45%;
    font-size: 12px;
    line-height: 1.3;
    display: table-cell;
    border-bottom: 1px solid #eee;
    vertical-align: middle;
  }
  .cart-bnt-group{
    width: 30%;
    text-align: center;
    white-space: nowrap;
    display: table-cell;
    border-bottom: 1px solid #eee;
    vertical-align: middle;
  }
  .cart-price{
    padding-right: 10px;
    text-align: right;
    width: 25%;
    display: table-cell;
    border-bottom: 1px solid #eee;
    vertical-align: middle;
  }
  .el-form-width{
    width: 300px;
  }
  #allmap{
    width: 0;
    height: 0;
    font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
    border:1px solid green;
  }
</style>
