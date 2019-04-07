<template xmlns:v-on="http://www.w3.org/1999/xhtml">
  <div>
    <div class="detail">
      <div class="shopguide-info">
        <img :src="'/show/logo/' + resInfo.id + '/' + resInfo.logo" width="70" height="70" class="img">
        <div class="shopguide-info-wrapper">
          <div>
            <h1 class="ng-binding">{{resInfo.resName}}</h1>
          </div>
          <p class="shopguide-info-rate">商家地址：</p>
          <p class="shopguide-info-addr">{{resInfo.address}}</p>
        </div>
      </div>
      <div class="shopguide-server">
          <span>
            <em style="color: black">起送价</em>
            <em class="shopguide-server-value">{{resInfo.initialDeliverPrice}}</em>
          </span>
          <span>
            <em style="color: black">配送费</em>
            <em class="shopguide-server-value">{{resInfo.deliverFee}}</em>
          </span>
      </div>
    </div>
    <div class="search">
      <div class="allCom">
        <h1>所有商品</h1>
      </div>
      <div class="s-input">
          <el-input v-model="searchContent" placeholder="搜索美食"></el-input>
      </div>
    </div>
    <div class="proAndPac">
      <div>
        <a style="font-size: 30px">单品</a>
      </div>
      <div class="pro">
        <div class="proCard" v-for="(proInfo,index) in proInfos" :key="index">
            <div>
              <img :src="'/show/productImage/' + proInfo.id + '/' + proInfo.image" width="100" height="100" class="problock-logo-icon">
            </div>
            <div class="food-main">
              <h3 class="shopmenu-food-name">{{proInfo.name}}</h3>
            </div>
            <span class="price">
              {{proInfo.price}}
              <small>起</small>
            </span>
            <div>
              <el-button class="get-button" type="primary" @click="proAddToCart(index)">加入购物车</el-button>
              <!--<el-input-number class="getNum" :min="1" v-if="this.num===1" size="small"></el-input-number>-->
            </div>
        </div>
      </div>
      <div>
        <a style="font-size: 30px">套餐</a>
      </div>
      <div class="pac">
        <div class="proCard" v-for="(pacInfo,index) in pacInfos" :key="index">
          <div>
            <img :src="'/show/packageImage/' + pacInfo.id + '/' + pacInfo.image" width="100" height="100" class="problock-logo-icon">
          </div>
          <div class="food-main">
            <h3 class="shopmenu-food-name">{{pacInfo.name}}</h3>
          </div>
          <span class="price">
              {{pacInfo.price}}
              <small>起</small>
            </span>
          <div>
            <el-button class="get-button" type="primary" @click="pacAddToCart(index)">加入购物车</el-button>
          </div>
        </div>
      </div>
      <div class="cart">
        <div class="cart-top">
          <div class="cart-font">
            <h1 style="font-size: 25px;padding-top: 6px;">购物车</h1>
            <el-button class="cart-clear-btn" @click="clear">清空</el-button>
          </div>
        </div>
        <div class="propac">
          <div class="cartCard" v-for="(cartProInfo,index) in cartProInfos" :key="index">
            <div>
              <h3 class="shopmenu-food-name cartName">{{cartProInfo.name}}</h3>
            </div>
            <div>
              <el-input-number v-model="cartProInfo.number" class="cart-input-number" :min="0" size="small" @change="getSum"></el-input-number>
            </div>
            <div>
              <span class="price cart-price">
              {{cartProInfo.price}}
            </span>
            </div>
          </div>
          <div class="cartCard" v-for="(cartPacInfo,index) in cartPacInfos" :key="index">
            <div>
              <h3 class="shopmenu-food-name cartName">{{cartPacInfo.name}}</h3>
            </div>
            <div>
              <el-input-number v-model="cartPacInfo.number" class="cart-input-number" :min="0" size="small" @change="getSum"></el-input-number>
            </div>
            <div>
              <span class="price cart-price">
              {{cartPacInfo.price}}
            </span>
            </div>
          </div>
        </div>
        <div class="cart-bot">
          <div class="sum">
            <span class="cart-bot-price">
              {{this.sum}}
            </span>
            <span class="deliver-fee">配送费￥{{this.deliverFee}} 元</span>
          </div>
          <div class="clarify">
            <span class="clarify-font" v-if="this.isDeliver===false">还差 {{this.difference}} 元起送</span>
            <button class="clarify-btn" v-if="this.isDeliver===true" v-on:click="placeOrder">去结算></button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import Vue from 'vue'
  import mapGetters from 'vuex'

    export default {
        watch:{
          'searchContent' : function () {
            this.getProInfos(this.searchContent)
            this.getPacInfos(this.searchContent)
          },
          'sum' : function () {
            console.log(this.resInfo.initialDeliverPrice)
            if(this.sum >= this.resInfo.initialDeliverPrice){
              this.isDeliver = true
            }else{
              this.difference = (this.resInfo.initialDeliverPrice - this.sum).toFixed(2)
              this.isDeliver = false
            }
          }
        },
        data() {
            return {
              resInfo:{},
              searchContent:'',
              proInfos:[],
              pacInfos:[],
              isDeliver:false,
              cartProInfos:[],
              cartPacInfos:[],
              sum:0,
              deliverFee:0,
              difference:0, //差额
              yumOrder:{
                userId:0,
                resId:0,
                cartProInfos:[],
                cartPacInfos:[],
                price:0.0,
                deliverFee:0.0,
                sum:0.0
              }
            }
        },
        mounted () {
          this.getResInfo()
          this.getProInfos(this.searchContent)
          this.getPacInfos(this.searchContent)
        },
        methods: {
          getResInfo: function () {
            let resInfo = localStorage.getItem('resInfo')
            if(resInfo){
              this.resInfo = JSON.parse(resInfo)
            }
            this.deliverFee = this.resInfo.deliverFee
            console.log(resInfo)
          },
          getProInfos: function (search) {
            console.log('getProInfos')
            let resId = this.resInfo.id;
            this.$ajax.get('/publish/getProInfos/'+resId,{
            params: {
              search:search
            }
            }).then((res) =>{
              let result = res.data;
              if(result.code === 0){
                this.proInfos = result.data
              } else {
                this.$message.error('获取信息失败')
              }
            })
          },
          getPacInfos: function (search) {
            console.log('getPacInfos')
            let resId = this.resInfo.id
            this.$ajax.get('/publish/getPacInfos/'+resId,{
              params: {
                search:search
              }
            }).then((res) =>{
              let result = res.data;
              if(result.code === 0){
                this.pacInfos = result.data
              } else {
                this.$message.error('获取信息失败')
              }
            })
          },
          proAddToCart: function (index) {
            let proInfo = this.proInfos[index];
            let ischosen = false
            let chooseIndex;
            for(let i = 0 ; i<this.cartProInfos.length ; i++){
              if(proInfo.name === this.cartProInfos[i].name){
                ischosen = true;
                chooseIndex = i;
              }
            }
            let cartNum = 1;
            if(ischosen){
              cartNum = this.cartProInfos[chooseIndex].number
            }
            if(!ischosen){
              let pro = {};
              if((!pro.name)&&(!pro.number)&&(!pro.price)){
                Vue.set(pro,'name',proInfo.name)
                Vue.set(pro,'number',1)
                Vue.set(pro,'price',proInfo.price)
              }
              this.cartProInfos.push(pro)
            }else{
              this.cartProInfos[chooseIndex].number = cartNum+1
            }
            this.getSum()
          },
          pacAddToCart: function(index) {
            let pacInfo = this.pacInfos[index];
            let ischosen = false
            let chooseIndex;
            for(let i = 0 ; i<this.cartPacInfos.length ; i++){
              if(pacInfo.name === this.cartPacInfos[i].name){
                ischosen = true;
                chooseIndex = i;
              }
            }
            let cartNum = 1;
            if(ischosen){
              cartNum = this.cartPacInfos[chooseIndex].number
            }
            if(!ischosen){
              let pro = {};
              if((!pro.name)&&(!pro.number)&&(!pro.price)){
                Vue.set(pro,'name',pacInfo.name)
                Vue.set(pro,'number',1)
                Vue.set(pro,'price',pacInfo.price)
              }
              this.cartPacInfos.push(pro)
            }else{
              this.cartPacInfos[chooseIndex].number = cartNum+1
            }
            this.getSum()
          },
          getSum: function () {
            let sumPrice = 0
            for(let i = 0 ; i < this.cartProInfos.length ; i++){
              sumPrice = sumPrice + this.cartProInfos[i].price*this.cartProInfos[i].number
            }
            for(let i = 0 ; i < this.cartPacInfos.length ; i++){
              sumPrice = sumPrice + this.cartPacInfos[i].price*this.cartPacInfos[i].number
            }
            this.sum = sumPrice

            let newCartPros = [];
            for(let i = 0 ; i < this.cartProInfos.length ; i++){
              if(this.cartProInfos[i].number > 0){
                newCartPros.push(this.cartProInfos[i])
              }
            }
            this.cartProInfos = newCartPros

            let newCartPacs = [];
            for(let i = 0 ; i < this.cartPacInfos.length ; i++){
              if(this.cartPacInfos[i].number > 0){
                newCartPacs.push(this.cartPacInfos[i])
              }
            }
            this.cartPacInfos = newCartPacs
          },
          clear: function () {
            this.cartProInfos = []
            this.cartPacInfos = []
            this.sum = 0
          },
          placeOrder: function () {
            this.yumOrder.userId = this.$store.getters.id
            this.yumOrder.resId = this.resInfo.id
            // let proNames = []
            // for(let i = 0 ; i < this.cartProInfos.length ; i++){
            //   for(let j = 0 ; j < this.cartProInfos[i].number ; j++){
            //     proNames.push(this.cartProInfos[i].name);
            //   }
            // }
            // let pacNames = []
            // for(let i = 0 ; i < this.cartPacInfos.length ; i++){
            //   for(let j = 0 ; j < this.cartPacInfos[i].number ; j++){
            //     pacNames.push(this.cartPacInfos[i].name);
            //   }
            // }
            // this.yumOrder.productNames = proNames
            // this.yumOrder.packageNames = pacNames
            this.yumOrder.cartProInfos = this.cartProInfos
            this.yumOrder.cartPacInfos = this.cartPacInfos
            this.yumOrder.price = this.sum
            this.yumOrder.deliverFee = this.deliverFee
            this.yumOrder.sum = this.sum + this.deliverFee

            // this.$ajax.post('/order/place',this.yumOrder).then((response) => {
            //   switch (response.data.code){
            //     case 0:
            //       // Cookie.set('login-token', response.data.result)
            //       this.$message.success('下单成功!')
            //
            //       this.$router.push('/home')
            //       break
            //     case 1:
            //       this.$message.error(response.message)
            //       break
            //   }
            //   }
            // )
            localStorage.setItem('orderInfo',JSON.stringify(this.yumOrder))
            this.$router.push('/orderInfo')
          }
        }
    }

</script>

<style>
  .detail{
    padding: 40px 0 100px 0;
    background-image: url("/static/image/detail_back.png");
    background-repeat:no-repeat;
    background-size:100% 100%;
    -moz-background-size:100% 100%;
    height: 50px;
  }
  .shopguide-info {
    position: relative;
    display: table-cell;
    vertical-align: middle;
    color: #fff;
    z-index: 1;
  }
  .img{
    margin-right: 15px;
    width: 95px;
    height: 95px;
    border-radius: 50%;
    border: 4px solid #FFF;
    border: 4px solid rgba(255,255,255,.3);
    vertical-align: middle;
  }
  .shopguide-info-wrapper{
    display: inline-block;
    vertical-align: middle;
  }
  .ng-binding{
    font-size: 20px;
    max-width: 270px;
    display: inline-block;
    vertical-align: middle;
    white-space: nowrap;
    text-overflow: ellipsis;
    overflow: hidden;
    font-weight: 400;
  }
  .shopguide-info-rate {
    margin: 10px 0 10px 0;
  }
  .shopguide-info-addr{
    padding-left: 20px;
    color: black;
  }
  .shopguide-server {
    display: table-cell;
    padding-right: 100px;
    vertical-align: middle;
    color: #fff;
    text-align: right;
    width: 490px;
    padding-left: 320px;
  }
  .shopguide-server>span {
    display: inline-block;
    margin-left: 80px;
    vertical-align: top;
    text-align: center;
  }
  em{
    font-style: normal;
    font-weight: 400;
  }
  .shopguide-server-value {
    display: block;
    margin-top: 20px;
    margin-bottom: 3px;
    padding-top: 5px;
    font-size: 18px;
  }
  .search{
    padding: 10px 0 50px 0;
    height: 10px;
    background-color: #ffffff;
    display: flex;
  }
  .allCom{
    margin-left: 250px;
    margin-top: 12px;
    font-size: 25px;
  }
  .s-input{
    margin-left: 600px;
    margin-top: 10px;
  }
  .proAndPac{
    padding-top: 20px;
    padding-left: 120px;
    padding-right: 500px;
  }
  .pro,.pac{
    padding-top: 20px;
    display: flex;
    flex-wrap: wrap
  }
  .proCard{
    background-color: #fff;
    border-bottom: 1px #f5f5f5 solid;
    width: 48%;
    height: 102px;
    font-size: 12px;
    position: relative;
    margin-right: 2%;
    margin-bottom: 12px;
    box-shadow: 1px 1px 5px #888888;
  }
  .problock-logo-icon{
    float: left;
    margin-right: 14px;
    width: 100px;
    height: 100px;
  }
  .food-main{
    font-size: 12px
  }
  .shopmenu-food-name{
    margin-top: 10px;
    font-size: 14px;
    font-weight: 700;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    width: 100px;
  }
  .price{
    position: absolute;
    bottom: 10px;
    font-size: 14px;
    font-weight: 700;
    color: #f74342;
    content: "\00a5";
  }
  small{
    margin-left: 3px;
    font-size: 12px;
    color: #888;
  }
  .price:before {
    content: "\00a5";
    margin-right: 3px;
    font-size: 12px;
  }
  .get-button{
    display: inline-block;
    background-color: #0089dc;
    color: #fff;
    position: absolute;
    right: 10px;
    bottom: 10px;
    border: 0;
    cursor: pointer;
    width: 90px;
    height: 26px;
    line-height: 26px;
    border-radius: 20px;
    text-align: center;
    outline: 0;
    margin: 0;
    padding: 0;
  }
  .getNum{
    display: inline-block;
    right: 10px;
    bottom: 10px;
    top: 40px;
    left: 140px;
  }
  .cart{
    position: fixed;
    right: 0;
    top: 0;
    bottom: 0;
    height: 166px;
    margin: auto;
    z-index: 10;
  }
  .cart-top{
    background-color: #2c3e50;
    height: 40px;
    color: white;
    width: 300px;
  }
  .cart-font{
    width: 200px;
    display: flex;
  }
  .cart-clear-btn{
    background-color: #2c3e50;
    color: white;
    margin-left: 10px;
  }
  .propac{
    display: flex;
    flex-wrap: wrap;
    width: 300px;
    background-color: #ffffff;
  }
  .cartCard{
    box-shadow: 1px 1px 5px #888888;
    position: relative;
    width: 300px;
    height: 35px;
    display: flex;
  }
  .cartName{
    padding-left: 10px;
  }
  .cart-input-number{
    margin-left: 30px;
  }
  .cart-price{
    margin-left: 30px;
  }
  .cart-bot{
    height: 40px;
    width: 300px;
    display: flex;
  }
  .sum{
    width: 170px;
    background-color: #2c3e50;
  }
  .clarify{
    width: 130px;
    background-color: #b4bccc;
  }
  .cart-bot-price{
    font-size: 22px;
    font-weight: 700;
    color: #f74342;
    content: "\00a5";
    margin-left: 20px;
    position: absolute;
    margin-top: 12px;
  }
  .cart-bot-price:before{
    content: "\00a5";
    margin-right: 3px;
    font-size: 12px;
  }
  .deliver-fee{
    position: absolute;
    color: #b4bccc;
    margin-left: 80px;
    margin-top: 18px;
    font-size: 10px;
  }
  .clarify-font{
    position: absolute;
    margin-top: 12px;
    margin-left: 10px;
    font-weight: bold;
  }
  .clarify-btn{
    width: 130px;
    height: 40px;
    background-color: #67c23a;
    color: #ffffff;
    font-size: 15px;
    font-weight: bolder;
  }
</style>
