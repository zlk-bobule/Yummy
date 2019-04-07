<template xmlns:v-on="http://www.w3.org/1999/xhtml">
  <div style="margin-left: 240px;margin-right: 240px;height: 800px;margin-top: 80px;background-color: #ffffff">
    <div style="font-size: 20px;font-weight: bolder;margin-left: 20px;padding-top: 20px">审批餐厅信息</div>
    <div style="width: 800px;height: 1px;background-color: #8cc5ff;margin-left: 70px;margin-top: 18px"></div>
    <div style="margin-left: 70px">
      <el-tabs v-model="state">
        <el-tab-pane name="ExamineSign">
          <span slot="label"><i class="el-icon-circle-check"></i> 审批餐厅注册<span v-show="state === 'ORDER'"></span></span>
        </el-tab-pane>
        <el-tab-pane name="ExamineModify">
          <span slot="label"><i class="el-icon-circle-check"></i> 审批餐厅修改<span v-show="state === 'CANCEL'"></span></span>
        </el-tab-pane>
      </el-tabs>
    </div>
    <div>
      <div style="margin-left: 30px;margin-right: 70px;background-color: #e1f3d8;height: 30px;margin-top: 20px">
        <div style="padding-left: 20px;font-size: 13px;padding-top: 10px">
          <div style="float: left">识别码</div>
          <div style="float: left;padding-left: 80px;padding-right: 50px">门店名</div>
          <div style="float: left;padding-left: 130px">logo</div>
          <div style="float: left;padding-left: 140px">分类</div>
          <div style="padding-left: 660px">操作</div>
        </div>
      </div>
    </div>
    <div>
      <div style="margin-top: 30px;margin-left: 30px;margin-right: 70px">
        <div v-for="(resInfo,index) in resInfos" :key="index">
          <div style="background-color: #ffffff;height: 100px">
            <div style="float: left;width: 100px;height: 100px">
              <div style="overflow: hidden;padding-top: 40px;padding-left: 10px">{{resInfo.identifyCode}}</div>
            </div>
            <div style="float: left;width: 240px;height: 100px">
              <div style="overflow: hidden;padding-top: 40px;padding-left: 30px">{{resInfo.resName}}</div>
            </div>
            <div style="float: left;width: 160px;height: 100px">
              <div>
                <img :src="'/show/logo/' + resInfo.id + '/' + resInfo.logo" height="70px" width="70px" style="border-radius: 50%;margin-top: 15px;float: left">
              </div>
            </div>
            <div style="float: left;width: 140px;height: 100px">
              <div style="margin-top: 35px;margin-left: 20px">{{resInfo.types[0]}}</div>
            </div>
            <div style="float: left;width: 200px;height: 100px">
              <div>
                <button v-on:click="pass(resInfo)" style="width: 80px; height: 30px; background: rgb(217, 236, 255); margin-left: 10px; margin-top: 20px; float: left;">审核通过</button>
                <button v-on:click="noPass(resInfo)" style="width: 80px; height: 30px; background: rgb(217, 236, 255); margin-left: 10px; margin-top: 20px;">审核不通过</button>
              </div>
              <div style="margin-top: 16px">
                <a style="color: #565656;margin-left: 75px;font-size: 12px" href="javascript:;" v-on:click="showRemarkPic(resInfo)">餐厅信息</a>
              </div>
            </div>
          </div>
          <div style="height: 30px;background: linear-gradient(to bottom, #c6e2ff, white);margin-left: 30px;margin-right: 30px"></div>
        </div>
      </div>
    </div>

    <el-dialog title="餐厅信息" :visible.sync="dialogRemarkImg" width="700px" center :append-to-body='true'>
      <el-form v-model="resConcrete" label-width="110px" label-position="left" size="medium" ref="resConcrete">
        <el-form-item label="餐厅名">
          <div class="itemFont">{{resConcrete.resName}}</div>
        </el-form-item>
        <el-form-item label="识别码">
          <div class="itemFont">{{resConcrete.identifyCode}}</div>
        </el-form-item>
        <el-form-item label="外卖电话">
          <div class="itemFont">{{resConcrete.takeOutPhone}}</div>
        </el-form-item>
        <el-form-item label="联系人姓名">
          <div class="itemFont">{{resConcrete.contactName}}</div>
        </el-form-item>
        <el-form-item label="联系人电话">
          <div class="itemFont">{{resConcrete.contactPhone}}</div>
        </el-form-item>
        <el-form-item label="门店分类">
          <div class="itemFont">{{resConcrete.types}}</div>
        </el-form-item>
        <el-form-item label="地址">
          <div class="itemFont">{{resConcrete.address}}</div>
        </el-form-item>
        <el-form-item label="起送价">
          <div class="itemFont">{{resConcrete.initialDeliverPrice}}</div>
        </el-form-item>
        <el-form-item label="配送费">
          <div class="itemFont">{{resConcrete.deliverFee}}</div>
        </el-form-item>
      </el-form>

    </el-dialog>

  </div>
</template>

<script>
    export default {
      name: "",
      data () {
        return {
          state:'ExamineSign',
          resInfos:[],
          dialogRemarkImg:false,
          resConcrete:{}
        }
      },
      watch:{
        'state':function () {
          this.getResInfos()
        }
      },
      mounted(){
        this.getResInfos()
      },
      methods:{
        getResInfos: function () {
          if(this.state === 'ExamineSign'){
            this.$ajax.get('/restaurant/getExaminedSignRes').then((res) => {
              let result = res.data
              if (result.code === 0) {
                this.resInfos = result.data
              } else {
                this.$message.error("获取餐厅信息失败")
              }
            })
          }
          if(this.state === 'ExamineModify'){
            this.$ajax.get('/restaurant/getExaminedModifyRes').then((res) => {
              let result = res.data
              if (result.code === 0) {
                this.resInfos = result.data
              } else {
                this.$message.error("获取餐厅信息失败")
              }
            })
          }
        },
        pass: function (resInfo) {
          this.$ajax.get('/restaurant/pass/'+resInfo.id).then((res) => {
            let result = res.data
            if (result.code === 0) {
              this.$message.success("审核通过")
              this.getResInfos()

              this.$ajax.get('/restaurant/sendPassMail',{
                params:{
                  mail:resInfo.email,
                  identifyCode:resInfo.identifyCode
                }
              }).then((res) => {
                let result = res.data
                if (result.code === 0) {
                  this.$message.success("发送邮件成功")
                } else {
                  this.$message.error("发送邮件失败")
                }
              })

            } else {
              this.$message.error("获取餐厅信息失败")
            }
          })
        },
        noPass: function (resInfo) {
          this.$ajax.get('/restaurant/noPass/'+resInfo.id).then((res) => {
            let result = res.data
            if (result.code === 0) {
              this.$message.success("审核不通过")
              this.getResInfos()

              this.$ajax.get('/restaurant/sendNoPassMail',{
                params:{
                  mail:resInfo.email
                }
              }).then((res) => {
                let result = res.data
                if (result.code === 0) {
                  this.$message.success("发送邮件成功")
                } else {
                  this.$message.error("发送邮件失败")
                }
              })

            } else {
              this.$message.error("获取餐厅信息失败")
            }
          })
        },
        showRemarkPic: function (resInfo) {
          var self = this;
          self.dialogRemarkImg = true;//显示弹框
          setTimeout(function () {//等组件生成再调用setActiveItem(index);
            self.resConcrete = resInfo
          }, 500);
        }
      }
    }
</script>

<style scoped>
  .itemFont{
    font-size: 18px;
    font-weight: bolder;
  }
</style>
