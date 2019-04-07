<template xmlns:v-on="http://www.w3.org/1999/xhtml">
  <div style="background-color: #fafafa;width: 900px">
    <div style="font-size: 20px;font-weight: bolder;margin-left: 20px;padding-top: 20px">我的订单</div>
    <div style="width: 800px;height: 1px;background-color: #8cc5ff;margin-left: 30px;margin-top: 18px"></div>
    <div style="margin-left: 30px" v-loading.fullscreen.lock="fullscreenLoading">
      <el-tabs v-model="state">
        <el-tab-pane name="ALL">
          <span slot="label"><i class="el-icon-document"></i> 所有订单<span v-show="state === 'ALL'"></span></span>
        </el-tab-pane>
        <el-tab-pane name="PLACE">
          <span slot="label"><i class="el-icon-question"></i> 下单未付款<span v-show="state === 'PLACE'"></span></span>
        </el-tab-pane>
        <el-tab-pane name="PAID">
          <span slot="label"><i class="el-icon-circle-check"></i> 已付款订单<span v-show="state === 'PAID'"></span></span>
        </el-tab-pane>
        <el-tab-pane name="DELIVERED">
          <span slot="label"><i class="el-icon-circle-check"></i> 已送达订单<span v-show="state === 'DELIVERED'"></span></span>
        </el-tab-pane>
        <el-tab-pane name="CANCEL">
          <span slot="label"><i class="el-icon-circle-close"></i> 已取消订单<span v-show="state === 'CANCEL'"></span></span>
        </el-tab-pane>
      </el-tabs>
    </div>
    <div style="margin-left: 30px">
      <el-radio-group v-model="sortBy" size="medium">
        <el-radio-button label="时间">时间</el-radio-button>
        <el-radio-button label="金额" @click.native="changeSec">金额 <i
          :class="{'el-icon-caret-bottom': isSec,'el-icon-caret-top': !isSec}" v-if="sortBy === '金额'"></i>
        </el-radio-button>
        <el-radio-button label="餐厅">餐厅</el-radio-button>
      </el-radio-group>
    </div>
    <div>
      <div style="margin-left: 30px;margin-right: 70px;background-color: #e1f3d8;height: 30px;margin-top: 20px">
        <div style="padding-left: 20px;font-size: 13px;padding-top: 10px">
          <div style="float: left">下单时间</div>
          <div style="float: left;padding-left: 70px">订单内容</div>
          <div style="float: left;padding-left: 230px">支付金额（元）</div>
          <div style="float: left;padding-left: 80px">状态</div>
          <div style="padding-left: 700px">操作</div>
        </div>
      </div>
    </div>
    <div style="margin-top: 30px;margin-left: 30px;margin-right: 70px">
      <div v-for="(order,index) in orderList" :key="index">
        <div style="background-color: #ffffff;height: 100px">
          <div style="float: left;width: 140px;height: 100px">
            <div style="overflow: hidden;padding-top: 40px;padding-left: 10px">{{order.placeTime}}</div>
          </div>
          <div style="float: left;width: 285px;height: 100px">
            <div>
              <img :src="'/show/productImage/' + order.proId + '/' + order.image" height="70px" width="70px" style="border-radius: 50%;margin-top: 15px;margin-left: 10px;float: left" v-if="!order.pacId">
              <img :src="'/show/packageImage/' + order.pacId + '/' + order.image" height="70px" width="70px" style="border-radius: 50%;margin-top: 15px;margin-left: 10px;float: left" v-if="!order.proId">
              <div style="color: #6f7180;overflow: hidden;padding-top: 40px;padding-left: 10px" v-if="order.productNames.length > 0">{{order.productNames[0]}}1份 1 个菜品</div>
              <div style="color: #6f7180;overflow: hidden;padding-top: 40px;padding-left: 10px" v-if="order.packageNames.length > 0 && order.productNames.length === 0">{{order.packageNames[0]}}1份 1 个菜品</div>
            </div>
          </div>
          <div style="float: left;width: 140px;height: 100px">
            <div style="font-size: 20px;font-weight: bolder;margin-top: 35px;margin-left: 20px">{{order.sum}}</div>
          </div>
          <div style="float: left;width: 115px;height: 100px">
            <div style="color: #565656;margin-left: 20px;margin-top: 40px" v-if="order.orderState === 'DELIVERED'">订单已完成</div>
            <div style="color: #565656;margin-left: 20px;margin-top: 40px" v-if="order.orderState === 'PLACE'">下单未付款</div>
            <div style="color: #565656;margin-left: 20px;margin-top: 40px" v-if="order.orderState === 'PAID'">付款配送中</div>
            <div style="color: #565656;margin-left: 20px;margin-top: 40px" v-if="order.orderState === 'CANCEL'">订单已取消</div>
          </div>
          <div style="float: left;width: 120px;height: 100px">
            <button style="width: 80px;height: 30px;background: #d9ecff;margin-left: 20px;margin-top: 20px" v-on:click="gotoOrderConcrete(order)">订单详情</button>
            <!--<div style="margin-top: 20px;color: #565656;margin-left: 35px;font-size: 12px">再来一单</div>-->
          </div>
        </div>
        <div style="height: 30px;background: linear-gradient(to bottom, #fde2e2, white);margin-left: 30px;margin-right: 30px"></div>
      </div>
    </div>
  </div>

</template>

<script>
  import Vue from 'vue'
    export default {
      name: "",
      data() {
        return{
          fullscreenLoading: false,
          state:'ALL',
          sortBy: '时间',
          isSec: true,
          secCount: 0,
          orderList:[]
        }
      },
      watch:{
        'state': function () {
          this.getOrderList()
          this.isSec = true
        },
        'sortBy': function () {
          this.getOrderList()
          this.isSec = true
        },
        'isSec': function () {
          this.orderList = this.orderList.reverse()
        }
      },
      mounted () {
        this.getOrderList()
      },
      computed:{
      },
      methods:{
        changeSec: function () {
          if (this.secCount % 2 === 0) {
            this.isSec = !this.isSec
          }
          this.secCount++
        },
        gotoOrderConcrete: function (order) {
          localStorage.setItem('orderConcrete',JSON.stringify(order))
          this.$router.push('/concreteOrder')
        },
        getOrderList: function () {
          this.$ajax.get('/order/getOrderList',{
            params: {
              state:this.state,
              sortBy:this.sortBy,
              userId:this.$store.getters.id
            }
          }).then((res) => {
            let result = res.data
            if (result.code === 0) {
              this.orderList = result.data
              this.getImage()
            } else {
              this.$message.error("获取订单失败")
            }
          })
        },
        getImage: function () {
          for (let i = 0; i < this.orderList.length; i++) {
            let order = this.orderList[i]
            console.log("order")
            console.log(order)
            let passName
            let judgeWhere
            if (order.productNames.length > 0) {
              passName = order.productNames[0]
              judgeWhere = 0
            } else {
              passName = order.packageNames[0]
              judgeWhere = 1
            }
            this.$ajax.get('/order/getProOrPacImage', {
              params: {
                name: passName,
                resId: order.resId,
                placeTime: order.placeTime
              }
            }).then((res) => {
              let result = res.data
              if (result.code === 0) {
                if (judgeWhere === 0) {
                  let proPublish = result.data
                  if((!this.orderList[i].proId)&&(!this.orderList[i].image)){
                    Vue.set(this.orderList[i],'proId',proPublish.id)
                    Vue.set(this.orderList[i],'image',proPublish.image)
                  }
                } else {
                  let pacPublish = result.data
                  if((!this.orderList[i].pacId)&&(!this.orderList[i].image)){
                    Vue.set(this.orderList[i],'pacId',pacPublish.id)
                    Vue.set(this.orderList[i],'image',pacPublish.image)
                  }
                  // return '/show/packageImage/' + pacPublish.id + '/' + pacPublish.image
                }
              } else {
                this.$message.error("获取订单失败")
              }
            })
          }
        }
      }
    }
</script>

<style scoped>

</style>
