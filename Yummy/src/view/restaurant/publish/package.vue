<template>
  <div class="package">
    <el-form v-model="package" label-width="110px" label-position="left" size="medium" ref="packageForm">
      <el-form-item label="套餐名" prop="name">
        <el-input v-model="package.name" placeholder="请输入套餐名"></el-input>
      </el-form-item>
      <el-form-item label="发布数量" prop="number">
        <el-input-number v-model="package.number"
                         :min="1"></el-input-number>
        <span>最少发布一份</span>
      </el-form-item>
      <el-form-item label="套餐描述">
        <el-input
          type="textarea"
          :autosize="{ minRows: 2}"
          placeholder="套餐描述"
          v-model="package.description">
        </el-input>
      </el-form-item>
      <el-form-item label="套餐图片">
        <div>
          <el-upload
            class="pic-uploader"
            ref="uploadFile"
            action="/upload/packageImage"
            :data="uploadData"
            :auto-upload="false"
            accept="image/*">
            <i class="el-icon-plus pic-uploader-icon"></i>
            <div class="el-upload__tip" slot="tip" style="margin-left: 25px">图片大小不超过2Mb</div>
          </el-upload>
        </div>
      </el-form-item>
      <el-form-item label="发布日期">
        <el-date-picker
          v-model="package.date"
          type="time"
          format="yyyy-MM-dd"
          value-format="yyyy-MM-dd"
          placeholder="选择发布日期"
          :picker-options="endDateOpt">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="时间段">
        <el-time-picker
          v-model="package.startTime"
          type="datetime"
          format="HH:mm"
          value-format="HH:mm"
          placeholder="开始时间"
          :picker-options="endDateOpt">
        </el-time-picker>
        <el-time-picker
          v-model="package.endTime"
          type="datetime"
          format="HH:mm"
          value-format="HH:mm"
          placeholder="结束时间"
          :picker-options="endDateOpt">
        </el-time-picker>
      </el-form-item>
      <el-form-item label="选择单品">
        <el-table
          :data="products"
          style="width: 100%"
          height="200"
        >
          <el-table-column fixed width="120" label="单品名" prop="name"></el-table-column>
          <el-table-column width="120" label="价格" prop="price"></el-table-column>
          <el-table-column width="360" label="库存数量" prop="number"></el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button
                size="mini"
                @click="handleEdit(scope.$index, scope.row)">选择</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-form-item>
      <el-form-item prop="productNames">
        <el-tag
          :key="tag"
          v-for="tag in package.productNames"
          closable
          :disable-transitions="false"
          @close="handleClose(tag)">
          {{tag}}
        </el-tag>
      </el-form-item>
      <el-form-item label="套餐原价格">
        <a style="font-size: 25px">{{sumPrice}}</a>
      </el-form-item>
      <el-form-item label="套餐定价" prop="price">
        <el-input v-model="package.price" type="text" onkeyup="this.value=(this.value.match(/^\d+\.?\d*/)||[''])[0]"
                  placeholder="请给套餐定价（优惠自定)"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="publish" type="primary" round>发布套餐</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
    export default {
      name: "",
      data () {
        return {
          package: {
            resId: this.$store.getters.id,
            name:'',
            productNames:[],
            price:0.0,
            number:'',
            description:'',
            image:'',
            date:'',
            startTime:'',
            endTime:''
          },
          products:[],
          sumPrice:0,
          uploadData: {
            id: 0
          }
        }
      },
      watch: {
        'package.endTime': function () {
          this.products = []
          this.$nextTick(() => {
            this.getDivision()
          })
        }
      },
      methods:{
        getDivision : function(){
          let resId = this.$store.getters.id
          console.log(resId)
          this.$ajax.get('/publish/' + resId + '/getProducts',{
            params: {
              date:this.package.date,
              startTime: this.package.startTime,
              endTime: this.package.endTime
            }
          }).then((res) => {
            let result = res.data
            if (result.code === 0) {
              //将数据映射到products里面a
              this.products = result.data
            } else {
              this.$message.error(result.message)
            }
          })
        },
        handleEdit:function (index,row) {
          let num = prompt("请输入添加的单品数量","输入数量");
          let trueNum = 0
          if(num*this.package.number > row.number){
            // num = row.number
            console.log("here1")
            console.log(this.package.number)
            while(trueNum*this.package.number <= row.number){
              trueNum = trueNum + 1
            }
            trueNum = trueNum - 1
            console.log(trueNum)
            console.log("trueNum")
          }else{
            console.log("here2")
            trueNum = num
          }
          row.number = row.number - trueNum*this.package.number
          let sumPrice = this.sumPrice;
          sumPrice = sumPrice + trueNum*row.price;
          this.sumPrice = sumPrice
          for(let i = 0;i<trueNum;i++){
            this.package.productNames.push(row.name)
          }
        },
        handleClose:function (tag) {
          let newArray = [];
          let array = this.package.productNames;
          let count = 0;
          for(let i = 0;i<array.length;i++){
            if(!(array[i] === tag)){
              newArray.push(array[i]);
              count++
            }
          }
          for(let j = 0 ; j<(array.length-count-1);j++){
            newArray.push(tag)
          }
          this.package.productNames = newArray;

          //套餐总价减去单品价格
          let proArray = this.products;
          let proPrice = 0;
          for(let i = 0;i<proArray.length;i++){
            if(proArray[i].name === tag){
              proPrice = proArray[i].price;
              this.products[i].number = this.products[i].number + this.package.number
              break
            }
          }
          this.sumPrice = this.sumPrice - proPrice;
        },
        publish:function(){
          this.$ajax.post('/publish/publishPackage',this.package).then((response) => {
              switch (response.data.code){
                case 0:
                  // Cookie.set('login-token', response.data.result)
                  this.$message.success('发布成功!')
                  let id = response.data.data
                  this.uploadData.id = id
                  this.$refs.uploadFile.submit()
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
  .package{
    padding: 40px 0 100px 0
  }
  .el-input{
    width: 300px
  }
  .pic-uploader{
    border: 1px dashed #409EFF;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    margin-right: 515px;
  }
  .pic-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 150px;
    height: 150px;
    line-height: 150px;
    text-align: center;
    margin-left: 10px;
  }
</style>
