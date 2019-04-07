<template xmlns:v-on="http://www.w3.org/1999/xhtml">
  <div style="margin-left: 240px;margin-right: 240px;height: 800px;margin-top: 80px;background-color: #ffffff">
    <div style="font-size: 20px;font-weight: bolder;margin-left: 20px;padding-top: 20px">统计信息</div>
    <div style="width: 800px;height: 1px;background-color: #8cc5ff;margin-left: 70px;margin-top: 18px"></div>
    <div style="margin-left: 30px">
      <el-tabs v-model="state">
        <el-tab-pane name="ORDER">
          <span slot="label"><i class="el-icon-circle-check"></i> 点餐<span v-show="state === 'ORDER'"></span></span>
        </el-tab-pane>
        <el-tab-pane name="CANCEL">
          <span slot="label"><i class="el-icon-circle-close"></i> 退订<span v-show="state === 'CANCEL'"></span></span>
        </el-tab-pane>
        <el-tab-pane name="FINANCE">
          <span slot="label"><i class="el-icon-document"></i> 财务<span v-show="state === 'FINANCE'"></span></span>
        </el-tab-pane>
      </el-tabs>
    </div>
    <div v-if="(state === 'ORDER')||(state === 'CANCEL')">
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
    <div style="margin-top: 30px;margin-left: 30px;margin-right: 70px" v-if="(state === 'ORDER')||(state === 'CANCEL')">
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
            <div style="color: #565656;margin-left: 20px;margin-top: 40px" v-if="order.orderState === 'DELIVERED'">订单已送达</div>
            <div style="color: #565656;margin-left: 20px;margin-top: 40px" v-if="order.orderState === 'PAID'">订单未送达</div>
            <div style="color: #565656;margin-left: 20px;margin-top: 40px" v-if="order.orderState === 'CANCEL'">订单已取消</div>
          </div>
          <div style="float: left;width: 120px;height: 100px">
            <button style="width: 80px;height: 30px;background: #d9ecff;margin-left: 20px;margin-top: 20px" v-on:click="gotoOrderConcrete(order)">订单详情</button>
            <div style="margin-top: 20px;color: #565656;margin-left: 35px;font-size: 12px">再来一单</div>
          </div>
        </div>
        <div style="height: 30px;background: linear-gradient(to bottom, #fde2e2, white);margin-left: 30px;margin-right: 30px"></div>
      </div>
    </div>
    <div v-if="state === 'FINANCE'">
      <div style="margin-left: 30px;margin-right: 70px;background-color: #e1f3d8;height: 30px;margin-top: 20px">
        <div style="padding-left: 20px;font-size: 13px;padding-top: 10px">
          <div style="float: left">日期</div>
          <div style="float: left;padding-left: 470px">餐厅收入（元）</div>
        </div>
      </div>
      <div v-for="(fi,index) in financeList" :key="index">
        <div style="height: 70px">
          <div style="float: left;width: 300px;height: 70px">
            <div style="overflow: hidden;padding-top: 40px;padding-left: 40px">{{fi[0]}}</div>
          </div>
          <div style="float: left;width: 400px;height: 70px">
            <div style="overflow: hidden;padding-top: 40px;padding-left: 260px;font-size: 18px;font-weight: bolder">{{fi[1]}}</div>
          </div>
        </div>
        <div style="width: 800px;height: 1px;background-color: #8cc5ff;margin-left: 30px;margin-top: 18px"></div>
      </div>
    </div>
  </div>
</template>

<script>
  import Vue from 'vue'
    export default {
      name: "",
      data () {
        return {
          state:'ORDER',
          orderList:[],
          financeList:[]
        }
      },
      mounted(){
        this.getOrderList()
      },
      watch:{
        'state':function () {
          if((this.state === 'ORDER')||(this.state === 'CANCEL')){
            this.getOrderList()
          } else {
            this.getFinance()
          }
        }
      },
      methods:{
        getOrderList: function () {
          if(this.state === 'ORDER'){
            this.$ajax.get('/order/getDeliverList/'+this.$store.getters.id).then((res) => {
              let result = res.data
              if (result.code === 0) {
                this.orderList = result.data
                this.getImage()
              } else {
                this.$message.error("获取订单失败")
              }
            })
          }
          if(this.state === 'CANCEL'){
            this.$ajax.get('/order/getCancelList/'+this.$store.getters.id).then((res) => {
              let result = res.data
              if (result.code === 0) {
                this.orderList = result.data
                this.getImage()
              } else {
                this.$message.error("获取订单失败")
              }
            })
          }
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
        },
        gotoOrderConcrete: function (order) {
          localStorage.setItem('orderConcrete',JSON.stringify(order))
          this.$router.push('/concreteOrder')
        },
        getFinance: function () {
          this.$ajax.get('/order/getFinance/'+this.$store.getters.id).then((res) => {
            let result = res.data
            if (result.code === 0) {
              this.financeList = result.data
            } else {
              this.$message.error("获取订单失败")
            }
          })
        }
      }
    }
</script>

<style scoped>

</style>
