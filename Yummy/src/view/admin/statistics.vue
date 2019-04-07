<template>
  <div style="margin-left: 240px;margin-right: 240px;;margin-top: 80px;background-color: #ffffff">
    <div style="font-size: 20px;font-weight: bolder;margin-left: 20px;padding-top: 20px">查看统计信息</div>
    <div style="width: 800px;height: 1px;background-color: #8cc5ff;margin-left: 70px;margin-top: 18px"></div>
    <div style="font-size: 16px;font-weight: bolder;margin-left: 80px;margin-top: 20px">餐厅统计</div>
    <div>
      <div style="width: 40%;height: 450px;margin-left: 300px" id="echartss"></div>
    </div>
    <div style="font-size: 16px;font-weight: bolder;margin-left: 80px;margin-top: 20px;margin-bottom: 10px">会员统计</div>
    <div class='user-growth-chart'>
      <el-card style="margin-left: 30px;margin-right: 30px">
        <div ref="userGrowthChart" style="width: 100%; height: 500px;"></div>
      </el-card>
    </div>
  </div>
</template>

<script>
  import echarts from 'echarts'
  import Vue from 'vue'
  // let echarts = require('echarts/lib/echarts')
  echarts.dataTool = require("echarts/extension/dataTool")
  // 引入柱状图组件
  require('echarts/lib/chart/pie')
  require('echarts/lib/chart/bar')
  require('echarts/lib/chart/line')
  require('echarts/lib/chart/boxplot')
  // 引入提示框和title组件
  require('echarts/lib/component/tooltip')
  require('echarts/lib/component/legend')
  require('echarts/lib/component/title')
  export default {
    watch:{
      echarts1_option:{
        handler(newVal, oldVal) {
          if (this.echarts1_option) {
            let myChart = echarts.init(document.getElementById('echartss'))
            if (newVal) {
              myChart.setOption(newVal);
            } else {
              myChart.setOption(oldVal);
            }
          } else {
            let myChart = echarts.init(document.getElementById('echartss'))
            myChart.setOption(this.echarts1_option)
          }
        },
        deep: true //对象内部属性的监听，关键。
      }
    },
   data () {
     return {
       echarts1_option:{
//需要的话下面内容copy到主体代码块即可
//          backgroundColor: '#45515a',
         backgroundColor:'#ffffff',
         //标题
         title: {
           text: '餐厅类型统计',
           subtext:'饼图示例',
           left: 'center',
           top: 20,
           textStyle: {
             color: '#565656',
             fontStyle:'italic'//标题字体
           }
         },
         //弹窗，响应鼠标指向，显示具体细节
         tooltip : {
           trigger: 'item',//以具体项目触发弹窗
           /*
           内容格式器，一共有abcd四个代号，例如在饼图中，{a}指系列，即流量来源，{b}指数据项目，如搜索引擎，{c}指数值，如
           value，{d}百分比。{x}本身代表了相应字符，可以和其他字符拼凑，在弹窗中显示
           */
           formatter: "{a} <br/>{b} : {c} ({d}%)"
         },
         //图例，选择要显示的项目
         legend:{
           orient:'vertical',
           left:'left',
           textStyle:{
             color:'#565656'
           },
           data:['烧烤', '炸鸡炸串','小龙虾' , '地方小吃', '零食', '奶茶果汁','甜品','咖啡']  //注意要和数据的name相对应
         },
         //工具箱
         toolbox:{
           show:true,//显示工具箱
           feature:{
             dataView:{show:true}, //以文字形式显示数据
             restore:{show:true},   //还原
             //dataZoom:{show:true}, //区域缩放
             saveAsImage:{show:true},  //保存图片
             //magicType:{type:['line','bar']}//动态数据切换，数据显示可以在该规定内容中切换显示方式，
           }
         },
         //视觉映射组件，将数据映射到视觉元素上
         // visualMap: {
         //   show: false,
         //   min: 10,
         //   max: 650,
         //   dimension: 0, //选取数据的维度，如人数据：[身高，体重]，则1代表将体重进行映射，默认值为数组的最后一位
         //   // seriesIndex: 4, //选取数据集合中的哪个数组，如{一班}，{二班}，默认选取data中的所有数据集
         //   // inRange: {
         //   //   //选定了要映射的对象，用inRange详细写要渲染的具体细节，[x，y]中x指最小值对应的量（亮度，饱和度等），y指最大值对应的量，其余的按各自value线性渲染
         //   //   color:['red'],
         //   //   colorLightness: [0,1],
         //   //   colorSaturation:[0,1]
         //   // }
         // },
//数据
         series : [
           {
             name:'访问来源',
             type:'pie',
             radius : '55%',
             center: ['50%', '50%'],
             data:[
               // {value:335, name:'烧烤'},
               // {value:310, name:'炸鸡炸串'},
               // {value:274, name:'小龙虾'},
               // {value:1, name:'地方小吃'},
               // {value:1, name:'零食'},
               // {value:2, name:'奶茶果汁'},
               // {value:335, name:'甜品'},
               // {value:35, name:'咖啡'},
             ].sort(function (a, b) { return a.value - b.value; }),
             roseType: 'radius',//角度和半径展现百分比，'area'只用半径展现
             label: { //饼图图形的文本标签
               normal: {  //下同，normal指在普通情况下样式，而非高亮时样式
                 textStyle: {
                   color: 'rgba(255, 255, 255, 0.3)'
                 }
               }
             },
             labelLine: {  //引导线样式
               normal: {
                 lineStyle: {
                   color: 'rgba(255, 255, 255, 0.3)'
                 },
                 smooth: 0.5, //0-1，越大越平滑弯曲
                 length: 10,  //从块到文字的第一段长
                 length2: 20  //拐弯到文字的段长
               }
             },
             // itemStyle: { //图例样式
             //   normal: {
             //     color: '#ffffef',
             //     shadowBlur: 50,//阴影模糊程度
             //     shadowColor: 'rgba(0, 0, 0, 0.5)'//阴影颜色，一般黑
             //   }
             // },

             animationType: 'scale', //初始动画效果，scale是缩放，expansion是展开
             animationEasing: 'elasticOut', //初始动画缓动效果
             animationDelay: function (idx) {  //数据更新动画时长，idx限定了每个数据块从无到有的速度
               return Math.random() * 200;
             }
           }
         ]
       }
     }
   },
   mounted(){
     let myChart = echarts.init(document.getElementById('echartss'))
     // 绘制图表，this.echarts1_option是数据
     this.getResTypeData()
     myChart.setOption(this.echarts1_option)
     this.drawUserGrowthChart()
   },
    methods:{
      getResTypeData: function () {
        this.$ajax.get('/admin/getResTypeList').then((res) => {
          let result = res.data
          if (result.code === 0) {
            let map = result.data
            let keys = Object.keys(map)
            keys.forEach((key) => {
              // data.push(map[key])
              let item = {}
              if((!item.value)&&(!item.name)){
                Vue.set(item,'value',map[key])
                Vue.set(item,'name',key)
              }
              console.log("item")
              console.log(item)
              this.echarts1_option.series[0].data.push(item)
              console.log("this.echarts1_option.series[0].data")
              console.log(this.echarts1_option.series[0].data)
            })
          } else {
            this.$message.error("获取餐厅类型统计信息失败")
          }
        })
      },
      drawUserGrowthChart: function () {
        let myChart = this.$echarts.init(this.$refs.userGrowthChart)
        let defaultData = [['2000-06-05', 116], ['2000-06-06', 129], ['2000-06-07', 135], ['2000-06-08', 86], ['2000-06-09', 73], ['2000-06-10', 85], ['2000-06-11', 73], ['2000-06-12', 68], ['2000-06-13', 92], ['2000-06-14', 130], ['2000-06-15', 245], ['2000-06-16', 139], ['2000-06-17', 115], ['2000-06-18', 111], ['2000-06-19', 309], ['2000-06-20', 206], ['2000-06-21', 137], ['2000-06-22', 128], ['2000-06-23', 85], ['2000-06-24', 94], ['2000-06-25', 71], ['2000-06-26', 106], ['2000-06-27', 84], ['2000-06-28', 93], ['2000-06-29', 85], ['2000-06-30', 73], ['2000-07-01', 83], ['2000-07-02', 125], ['2000-07-03', 107], ['2000-07-04', 82], ['2000-07-05', 44], ['2000-07-06', 72], ['2000-07-07', 106], ['2000-07-08', 107], ['2000-07-09', 66], ['2000-07-10', 91], ['2000-07-11', 92], ['2000-07-12', 113], ['2000-07-13', 107], ['2000-07-14', 131], ['2000-07-15', 111], ['2000-07-16', 64], ['2000-07-17', 69], ['2000-07-18', 88], ['2000-07-19', 77], ['2000-07-20', 83], ['2000-07-21', 111], ['2000-07-22', 57], ['2000-07-23', 55], ['2000-07-24', 60]]
        let defaultDateList = defaultData.map(function (item) {
          return item[0]
        })
        let defaultValueList = defaultData.map(function (item) {
          return item[1]
        })
        myChart.setOption({
          visualMap: [{
            show: false,
            type: 'continuous',
            seriesIndex: 0,
            min: 0,
            max: 400
          }],
          title: [{
            left: 'center',
            text: '用户增长图'
          }],
          grid: {
            left: '3%',
            right: '4%',
            bottom: '5%',
            containLabel: true
          },
          tooltip: {
            trigger: 'axis'
          },
          xAxis: [{
            data: defaultDateList
          }],
          yAxis: [{
            name: '人',
            splitLine: {show: false}
          }],
          series: [{
            type: 'line',
            showSymbol: false,
            data: defaultValueList
          }]
        })

        window.addEventListener('resize', function () {
          myChart.resize()
        })
        this.$ajax.get('/admin/user/tendency', {
          params: {
            startDate: '2019-03-14',
            interval: 1
          }
        }).then((res) => {
          let result = res.data
          if (result.code === 0) {
            let data = result.data
            let myData = []
            let dates = Object.keys(data)
            let count = 0
            dates.forEach((date) => {
              count += data[date]
              myData.push([date, count])
            })
            var dateList = myData.map(function (item) {
              return item[0]
            })
            var valueList = myData.map(function (item) {
              return item[1]
            })
            console.log("valuelist")
            console.log(valueList)
            myChart.setOption({
              xAxis: [{
                data: dateList
              }],
              series: [
                {
                  data: valueList
                }
              ]
            })
          }
        })
      }
    }
  }
</script>

<style>
  /*.ksmc{*/
    /*background-color: ;*/
  /*}*/
</style>
