<template>
    <div class="product">
      <el-form v-model="product" label-width="110px" label-position="left" size="medium" ref="productForm">
        <el-form-item label="单品名" prop="name">
          <el-input v-model="product.name" placeholder="请输入单品名"></el-input>
        </el-form-item>
        <el-form-item label="单品价格" prop="price">
          <el-input v-model="product.price" type="text" onkeyup="this.value=(this.value.match(/^\d+\.?\d*/)||[''])[0]"
                    placeholder="请输入单品价格"></el-input>
        </el-form-item>
        <el-form-item label="发布数量" prop="number">
          <el-input-number v-model="product.number"
                           :min="1"></el-input-number>
          <span>最少发布一份</span>
        </el-form-item>
        <el-form-item label="单品描述">
          <el-input
            type="textarea"
            :autosize="{ minRows: 2}"
            placeholder="单品描述"
            v-model="product.description">
          </el-input>
        </el-form-item>
        <el-form-item label="发布日期">
          <el-date-picker
            v-model="product.date"
            type="time"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd"
            placeholder="选择发布日期"
            :picker-options="endDateOpt">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="时间段">
          <el-time-picker
            v-model="product.startTime"
            type="datetime"
            format="HH:mm"
            value-format="HH:mm"
            placeholder="开始时间"
            :picker-options="endDateOpt">
          </el-time-picker>
          <el-time-picker
            v-model="product.endTime"
            type="datetime"
            format="HH:mm"
            value-format="HH:mm"
            placeholder="结束时间"
            :picker-options="endDateOpt">
          </el-time-picker>
        </el-form-item>
        <el-form-item label="单品图片">
          <div>
            <el-upload
              class="pic-uploader"
              ref="uploadFile"
              action="/upload/productImage"
              :data="uploadData"
              :auto-upload="false"
              accept="image/*">
              <i class="el-icon-plus pic-uploader-icon"></i>
              <div class="el-upload__tip" slot="tip" style="margin-left: 25px">图片大小不超过2Mb</div>
            </el-upload>
          </div>
        </el-form-item>
        <el-form-item>
          <el-button @click="publish" type="primary" round>发布单品</el-button>
        </el-form-item>
      </el-form>
    </div>
</template>

<script>
    export default {
      name: "",
      data () {
        return {
          product: {
            resId: this.$store.getters.id,
            name: '',
            price: 0.0,
            number:'',
            description:'',
            date:'',
            startTime:'',
            endTime:'',
            image:''
          },
          uploadData: {
            id: 0
          }
        }
      },
      mounted() {
        console.log(this.product.resId)
      },
      methods: {
        publish:function(){
          console.log(this.product)
          this.$ajax.post('/publish/publishProduct',this.product).then((response) => {
              switch (response.data.code){
                case 0:
                  // Cookie.set('login-token', response.data.result)
                  this.$message.success('发布成功!')
                  let id = response.data.data
                  this.uploadData.id = id
                  console.log(id)
                  console.log(this.$refs.uploadFile)
                  this.$refs.uploadFile.submit()
                  console.log("here")
                  this.product.name = ''
                  this.product.price = 0.0
                  this.product.number = ''
                  this.product.description = ''
                  this.product.date = ''
                  this.product.startTime = ''
                  this.product.endTime = ''
                  this.$refs.uploadFile.clearFiles()
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
  .product{
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
  .change-btn {
    /*bottom: 5px;*/
    /*left: 20px;*/
    margin: 0 5px;
    padding: 0 0;
    width: 140px;
    text-align: center;
    background-color: rgba(7, 17, 27, 0.3);
    color: #fff;
    font-size: 12px
  }
</style>
