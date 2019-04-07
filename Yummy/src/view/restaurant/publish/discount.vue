<template>
  <div style="padding: 40px 0 100px 0">
    <el-form v-model="discount" label-width="110px" label-position="left" size="medium">
      <el-form-item label="发布日期">
        <el-date-picker
          v-model="discount.date"
          type="time"
          format="yyyy-MM-dd"
          value-format="yyyy-MM-dd"
          placeholder="选择发布日期"
          :picker-options="endDateOpt">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="满减">
        <el-input v-model="full" style="width:100px" placeholder="满" onkeyup="this.value=(this.value.match(/^\d+\.?\d*/)||[''])[0]"></el-input>
        <el-input v-model="reduction" style="width:100px" placeholder="减" onkeyup="this.value=(this.value.match(/^\d+\.?\d*/)||[''])[0]"></el-input>
        <el-button style="margin-left: 30px;background-color: #66b1ff;color: #ffffff;" @click="addFullReduction">添加</el-button>
      </el-form-item>
      <el-form-item>
        <el-tag v-for="row in kvArray" :key="row" closable @close="handleClose(row)" style="margin-left: 10px">
          满{{row[0]}}减{{row[1]}}
        </el-tag>
      </el-form-item>
      <el-form-item label="本店新用户立减">
        <el-input v-model="discount.newUserInResReduction" style="width:250px" onkeyup="this.value=(this.value.match(/^\d+\.?\d*/)||[''])[0]"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" round @click="publish">发布套餐</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>

  const map = new Map();

    export default {
      name: "",
      data () {
        return {
          discount:{
            resId: this.$store.getters.id,
            date:'',
            fullReduction:[],
            newUserInResReduction:0.0
          },
          full:0.0,
          reduction:0.0,
          kvArray:[]
        }
      },
      methods:{
        addFullReduction: function () {
          let array = []
          array.push(this.full)
          array.push(this.reduction)
          this.kvArray.push(array)
          this.full = ''
          this.reduction = ''
        },
        handleClose: function (row) {
          let newArray = []
          for(let i = 0 ; i < this.kvArray.length ; i++){
            if(this.kvArray[i][0]!=row[0]){
              newArray.push(this.kvArray[i])
            }
          }
          this.kvArray = newArray
        },
        publish: function () {
          this.discount.fullReduction = this.kvArray
          console.log(this.discount.fullReduction)
          this.$ajax.post('/publish/publishDiscount',this.discount).then((response) => {
              switch (response.data.code){
                case 0:
                  // Cookie.set('login-token', response.data.result)
                  this.$message.success('发布成功!')
                  this.$router.push('/resHome')
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

<style scoped>

</style>
