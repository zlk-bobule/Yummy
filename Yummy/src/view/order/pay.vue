<template>
    <div>
      <div style="margin-top: 70px;margin-left: 100px;margin-right:110px;background-color: #ffffff">
        <div style="margin-left: 40px;padding-top: 20px;font-size: 24px">订单详情</div>
        <div style="width: 1100px;height: 1px;background-color: #000;margin-left: 30px;margin-top: 18px"></div>
        <div style="margin-left: 40px;font-size: 15px;margin-top: 20px">{{resInfo.resName}}</div>
        <div>
          <div style="margin-left: 900px;float: left">支付金额</div>
          <div style="font-size: 20px;color: red">￥{{order.sum}}</div>
        </div>
        <div style="margin-left: 40px;float: left">{{userAddress.contactName}}</div>
        <div style="margin-left: 120px">{{userAddress.phone}}</div>
        <div style="margin-left: 40px;margin-top: 10px">{{userAddress.address}}</div>
        <div v-for="(proName,index) in order.productNames" :key="index">
          <div style="margin-left: 40px;float: left">{{proName}}</div>
          <div style="margin-left: 260px;margin-top: 20px">✖️1</div>
        </div>
        <div v-for="(pacName,index) in order.packageNames" :key="index">
          <div style="margin-left: 40px;float: left">{{pacName}}</div>
          <div style="margin-left: 260px;margin-top: 20px">✖️1</div>
        </div>
        <div style="height: 30px"></div>
      </div>
      <div style="margin-top: 70px;margin-left: 100px;margin-right:110px;background-color: #ffffff;height: 340px">
        <div>
          <div style="margin-left: 40px;padding-top: 20px;font-size: 24px;float: left">请选择支付方式</div>
          <div>
            <div style="margin-left: 30px;padding-top: 25px;float: left">剩余支付时间</div>
            <div style="padding-top: 25px;color: red;float: left">{{min}}分{{sec}}秒</div>
            <div style="padding-top: 25px">，逾期订单将自动取消</div>
          </div>
        </div>
        <div style="width: 1100px;height: 1px;background-color: #000;margin-left: 30px;margin-top: 18px"></div>
        <div>
          <div style="margin-left: 40px;padding-top: 30px;float: left">第三方支付</div>
          <div style="color: red;padding-top: 25px;font-size: 20px">￥{{order.sum}}</div>
        </div>
        <img src="/static/image/网银.png" height="100px" width="150px" style="border: #67c23a;margin-left: 40px;margin-top: 15px">
        <div style="width: 1100px;height: 1px;background-color: #000;margin-left: 30px;margin-top: 18px"></div>
        <el-button style="margin-top: 15px;margin-left: 40px;background-color: #67c23a;color: #ffffff;font-weight: bolder;width: 100px" @click="pay">确认支付</el-button>
      </div>

      <el-dialog title="订单过期" :visible.sync="dialogRemarkImg" width="700px" center :append-to-body='true'>
        <div style="font-weight: 700;font-size: 20px">
          您的订单已失效
        </div>
        <el-button style="background-color: #67c23a;color: white;font-weight: bolder;font-size: 20px;margin-top: 20px" @click="returnHome">返回主页</el-button>
      </el-dialog>

    </div>
</template>

<style></style>

<script>
    export default {
      data() {
        return {
          order:{},
          cartPros:[],
          cartPacs:[],
          resInfo:{},
          userAddress:{},
          min:0,
          sec:0,
          end:0,
          msec:0,
          dialogRemarkImg:false
        }
      },
      watch:{
        'msec': function () {
          if(this.msec === 0){
            console.log("暂停")
            var self = this;
            self.dialogRemarkImg = true;//显示弹框
          }
        }
      },
      mounted(){
        this.end = Date.parse(new Date()) + 2 * 60 * 1000
        let orderResult = localStorage.getItem('orderResult')
        if(orderResult){
          this.order = JSON.parse(orderResult)
          this.$ajax.get('/restaurant/info/'+this.order.resId).then((res) =>{
            let result = res.data;
            if(result.code === 0){
              this.resInfo = result.data
            } else {
              this.$message.error('获取信息失败')
            }
          })
        }
        let cartPros = localStorage.getItem('cartPros')
        if(cartPros){
          this.cartPros = JSON.parse(cartPros)
        }
        let cartPacs = localStorage.getItem('cartPacs')
        if(cartPacs){
          this.cartPacs = JSON.parse(cartPacs)
        }
        let userAddress = localStorage.getItem('userAddress')
        if(userAddress){
          this.userAddress = JSON.parse(userAddress)
        }
        this.countdown()
      },
      methods:{
        pay: function () {
          this.$ajax.get('/order/pay',{
            params: {
              userId:this.order.userId,
              sum:this.order.sum,
              id:this.order.id
            }
          }).then((response) => {
              response = response.data
              if(response.code == 0){
                this.$message.success("支付成功")
                this.$router.push('/home')
              }else{
                this.$message.error("支付失败")
              }
            }
          )
        },
        countdown: function () {
          const now = Date.parse(new Date())
          const msec = this.end - now
          this.msec = msec
          let min = parseInt(msec / 1000 / 60 % 60)
          let sec = parseInt(msec / 1000 % 60)
          this.min = min > 9 ? min : '0' + min
          this.sec = sec > 9 ? sec : '0' + sec
          const that = this
          setTimeout(function () {
            that.countdown()
          }, 1000)
        },
        returnHome: function () {
          this.$ajax.get('/order/cancelOrder/'+this.order.id).then((response) => {
              switch (response.data.code){
                case 0:
                  // Cookie.set('login-token', response.data.result)
                  this.$router.push('/home')
                  break
                case 1:
                  this.$message.error(response.message)
                  break
              }
            }
          )
        }
      }
    }

</script>
