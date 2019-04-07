<template xmlns:v-on="http://www.w3.org/1999/xhtml">
  <div style="height: 700px;background-color: #ffffff">
    <div style="padding-top: 20px;padding-left: 20px">
      <div style="font-size: 20px;border-left: 3px solid #409eff;padding-left: 10px;font-weight: normal">地址管理</div>
    </div>
    <div style="float:left;width: 850px;height: 1px; background: #eee;margin-top: 50px;margin-left: 70px"></div>
    <div style="margin-left: 70px;margin-right: 70px;height: 300px;margin-top: 70px;display: flex;flex-wrap: wrap">
      <div style="width: 31.3%;height: 120px;box-shadow: 1px 1px 5px #888888;margin-right: 2%;" v-for="(userAddress,index) in userAddressList" :key="index">
        <div style="padding-top: 10px;padding-left: 20px">
          <div style="float: left">{{userAddress.contactName}}</div>
          <div style="padding-left: 40px;float: left;" v-if="userAddress.gender==='MALE'">先生</div>
          <div style="padding-left: 40px;float: left;" v-if="userAddress.gender==='FEMALE'">女士</div>
          <a href="javascript:;" v-on:click="modifyAddress(index)" style="float: left;font-size: 10px;margin-left: 60px;margin-top: 2px" v-if="userState==='VALID'">修改</a>
          <a href="javascript:;" v-on:click="deleteAddress(index)" style="font-size: 10px;margin-left: 20px" v-if="userState==='VALID'">删除</a>
        </div>
        <div style="padding-top: 15px;padding-left: 20px">
          <div>{{userAddress.address}}</div>
        </div>
        <div style="padding-top: 5px;padding-left: 20px">
          <div>{{userAddress.houseNumber}}</div>
        </div>
        <div style="padding-top: 10px;padding-left: 20px">
          <div>{{userAddress.phone}}</div>
        </div>
      </div>

      <a style="width: 31.3%;height: 120px;box-shadow: 1px 1px 5px #888888;margin-right: 2%;background-color: #f0f7ff" href="javascript:;" v-on:click="showRemarkPic" v-if="userState==='VALID'">
        <div style="font-size: 25px;font-weight: bold;padding-left: 80px;padding-top: 40px;float: left">+</div>
        <div style="padding-top: 48px;margin-left: 10px">添加新地址</div>
      </a>

    </div>
    <!--<el-button type="primary" @click="showRemarkPic" >改变索引</el-button>-->
    <el-dialog title="添加地址" :visible.sync="dialogRemarkImg" width="700px" center :append-to-body='true'>
      <el-form v-model="newAddress" label-width="110px" label-position="left" size="medium" ref="newAddress">
        <el-form-item label="姓名" prop="contactName">
          <el-input v-model="newAddress.contactName" placeholder="请输入您的姓名" class="el-form-width"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="newAddress.gender">
            <el-option value="男" selected>男</el-option>
            <el-option value="女" >女</el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="详细地址">
          <div>
            <el-input v-model="address_detail" placeholder="请输入小区、大厦或学校" id="suggestId" name="newAddress.address" class="el-form-width"></el-input>
            <div id="allmap"></div>
          </div>
        </el-form-item>
        <el-form-item label="详细地址" prop="houseNumber">
          <el-input v-model="newAddress.houseNumber" placeholder="单元、门牌号" class="el-form-width"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="newAddress.phone" placeholder="请输入您的手机号" class="el-form-width"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button style="background-color: #a4da89;color: #ffffff;width: 150px" @click="addAddress">保存</el-button>
          <a href="javascript:;" v-on:click="cancelDialog" style="margin-left: 20px">取消</a>
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
      name: "",
      data(){
        return{
          userAddressList:[],
          dialogRemarkImg:false,
          newAddress:{
            userId: 0,
            contactName:'',
            gender:'',
            address:'',
            lng:0.0000000000,
            lat:0.0000000000,
            houseNumber:'',
            phone:'',
            id:0
          },
          address_detail: null, //详细地址
          userlocation: {lng: "", lat: ""},
          addOrModify:0, //0表示添加，1表示修改
          addressId:0,
          userState:''
          // resSignUpForm:{
          //   address: '',
          //   lng:'',
          //   lat:'',
          //   userlocation: {lng: "", lat: ""}
          // }
        }
      },
      mounted(){
        this.newAddress.userId = this.$store.getters.id
        this.$nextTick(
          // this.loadAddress()
        ),
          this.getAddressList()
        this.getUserState()
      },
      methods:{
        modifyAddress:function (index) {
          let address = this.userAddressList[index]
          this.address_detail = address.address
          this.newAddress.lng = address.lng
          this.newAddress.lat = address.lat
          this.showRemarkPic()
          console.log(address)
          this.newAddress.id = address.id
          this.newAddress.contactName = address.contactName
          if(address.gender === 'MALE'){
            this.newAddress.gender = '男'
          }else{
            this.newAddress.gender = '女'
          }
          this.newAddress.lng = address.lng
          this.newAddress.lat = address.lat
          this.newAddress.houseNumber = address.houseNumber
          this.newAddress.phone = address.phone
          this.addOrModify = 1
        },
        deleteAddress: function (index) {
          let address = this.userAddressList[index]
          let addressId = address.id
          this.$ajax.get('/user/deleteAddress/'+addressId).then((response) => {
            let result = response.data
            if (result.code === 0) {
              this.$message.success("删除成功！")
              this.getAddressList()
            } else {
              this.$message.error(result.message)
            }
            }
          )
        },
        addAddress: function () {
          // console.log(this.newAddress)

          this.newAddress.lng = this.userlocation.lng
          this.newAddress.lat = this.userlocation.lat
          this.newAddress.address = this.address_detail
          if(this.newAddress.gender === '男'){
            this.newAddress.gender = 'MALE'
          }else{
            this.newAddress.gender = 'FEMALE'
          }
          console.log(this.newAddress)
          if (this.addOrModify === 0) {
            this.$ajax.post('/user/addUserAddress', this.newAddress).then((response) => {
                switch (response.data.code) {
                  case 0:
                    // Cookie.set('login-token', response.data.result)
                    this.$message.success('添加成功!')
                    this.getAddressList()
                    this.dialogRemarkImg = false
                    break
                  case 1:
                    this.$message.error(response.message)
                    break
                }
              }
            )
          }else{
            this.$ajax.post('/user/modifyAddress', this.newAddress).then((response) => {
                switch (response.data.code) {
                  case 0:
                    // Cookie.set('login-token', response.data.result)
                    this.$message.success('修改成功!')
                    this.getAddressList()
                    this.dialogRemarkImg = false
                    this.newAddress.userId = 0
                    this.newAddress.contactName = ''
                    this.newAddress.gender = ''
                    this.newAddress.address = ''
                    this.newAddress.lng = 0.0000000000
                    this.newAddress.lat = 0.0000000000
                    this.newAddress.houseNumber = ''
                    this.newAddress.phone = ''
                    this.addOrModify = 0
                    this.getAddressList()
                    this.dialogRemarkImg = false
                    break
                  case 1:
                    this.$message.error(response.message)
                    break
                }
              }
            )
          }
        },
        showRemarkPic: function () {
          var self = this;
          self.dialogRemarkImg = true;//显示弹框
          setTimeout(function () {//等组件生成再调用setActiveItem(index);
            self.loadAddress()
          }, 500);
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
        cancelDialog: function () {
          this.dialogRemarkImg = false;
        },
        getAddressList: function () {
          let userId = this.$store.getters.id
          console.log("userId"+userId)
          this.$ajax.get('/user/getAddressList/' + userId).then((res) => {
            let result = res.data
            if (result.code === 0) {
              this.userAddressList = result.data
            } else {
              this.$message.error(result.message)
            }
          })
        },
        getUserState: function () {
          let userId = this.$store.getters.id
          this.$ajax.get('/user/info/' + userId).then((res) => {
            let result = res.data
            if (result.code === 0) {
              this.userState = result.data.userState
            } else {
              this.$message.error(result.message)
            }
          })
        }
      }
    }
</script>

<style scoped>
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
