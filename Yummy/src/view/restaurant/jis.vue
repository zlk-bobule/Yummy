<template>
  <div id="all">
    <!--<el-tag>标签一</el-tag>-->
    <!--<el-tag type="success">标签二</el-tag>-->
    <!--<el-tag type="info">标签三</el-tag>-->
    <!--<el-tag type="warning">标签四</el-tag>-->
    <!--<el-tag type="danger">标签五</el-tag>-->
    <el-tag
      :key="tag"
      v-for="tag in dynamicTags"
      closable
      :disable-transitions="false"
      @close="handleClose(tag)">
      {{tag}}
    </el-tag>
  </div>
</template>
<script>
  export default {
    data(){
      return {
        address_detail: null, //详细地址
        userlocation: {lng: "", lat: ""},
        dynamicTags:["keyi","bcdhsj","cndjs","cndjs"]
      }
    },
    mounted(){
      this.$nextTick(function () {

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
          setPlace();
        });

        function setPlace() {
          map.clearOverlays();    //清除地图上所有覆盖物
          function myFun() {
            th.userlocation = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
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

      })//
    },
  }
</script>
<style scoped>
  #allmap{
    width: 400px;
    height: 400px;
    font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
    border:1px solid green;
  }
</style>
