<template>
    <div class="plan">   
        <div class="reset">
           

           <div id="myChart" :style="{width: '600px',height:'400px'}">

           </div>
           <el-button @click="resetstudy">取消当前学习计划</el-button>
  			<el-popover
                placement="top"
                width="160"
                v-model="visible">
                <p>当前计划进度会消失，确定取消吗?</p>
                <div style="text-align: right; margin: 0">
                    <el-button size="mini" type="text" @click="visible = false">取消</el-button>
                    <el-button type="primary" size="mini" @click="sure">确定</el-button>
                </div>
            </el-popover>

</div>
    </div>
</template>


<script>
import axios from 'axios'
import * as echarts from 'echarts';

export default {
  name: 'Plan',
   data() {
      return {
		sortname:[],
		sortcount:[],
		percentagearray:[],
		 visible: false,
      }
    },
    
    methods:{


        initEarch(){
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('myChart'));
 
            // 指定图表的配置项和数据


            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(
                {
				// backgroundColor:"skyblue",
				grid:{
					left:"10",
					top:"10",
					right:"0",
					bottom:"10",
					containLabel:true
				},
				xAxis: {
					type: 'value',
					splitLine:{show:false},
					axisLabel:{show:false},
					axisTick:{show:false},
					axisLine:{show:false}
				},
				yAxis:[
				{
						type: 'category',
						axisTick:{show:false},
						axisLine:{show:false},
						axisLabel:{
							color:"black",
							fontSize:14,
							textStyle: {
								color: 'black'
							}
						},
						data:this.sortname,
						max:7, // 关键：设置y刻度最大值，相当于设置总体行高
						inverse:true
					},
					{
						type: 'category',
						axisTick:{show:false},
						axisLine:{show:false},
						axisLabel:{
							show:false
						},
						data:this.sortcount,
						max:7, // 关键：设置y刻度最大值，相当于设置总体行高
						inverse:true
					}
				],
				series: [
				{
					name:"%",
					type:"bar",
					barWidth:19,
					data:this.percentagearray,//[80,50,20,60,80,40,90,30]
					barCategoryGap:20,
					label: { //图形上的文本标签
						show: true,
						position: 'right',//标签的位置
						// offset: [0,-40],  //标签文字的偏移，此处表示向上偏移40
						formatter: '{c}{a}',//标签内容格式器 {a}-系列名,{b}-数据名,{c}-数据值
						color: 'black',//标签字体颜色
						fontSize:14  //标签字号
					},
					itemStyle:{
						normal:{
							// barBorderRadius:10,
							color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [{
							offset: 0,
							color: '#22b6ed'
							}, {
							offset: 1,
							color: '#3fE279'
							}]),
						}
					},
					zlevel:1

				},{
					name:"进度条背景",
					type:"bar",
					barGap:"-100%",
					barWidth:19,
					data:[100,100,100,100,100,100,100,100,100,100],
					color:"#2e5384",
					itemStyle:{
						normal:{
							// barBorderRadius:10
						}
					}}]})},
			resetstudy(){
				
				this.visible=true
               
			},


			getData(){
				axios.get("/english/getallsortname").then((res)=>{
				
				console.log(res.data)
				//这里就写你要执行的语句即可，先让数据库的数据加载进去数组中你在从数组中取值就好了
				for(var i=0;i< res.data.length;i++){
					this.sortname.push(res.data[i].sortname)
					this.sortcount.push(res.data[i].sortcount)
				}
				axios.get("/english/getallcount",{
                    params:{
                        userid:sessionStorage.getItem('UserID')
                    }
                }).then((res)=>{
					for(var i=0;i< res.data.length;i++){
						let num = res.data[i].toFixed(2);
						console.log(num)
						this.percentagearray.push(num)
					}

					
				})
				}).catch(console.error())
					setTimeout(()=>{
						var sortname= JSON.parse(JSON.stringify(this.sortname))
						this.sortname=sortname
						
						var sortcount= JSON.parse(JSON.stringify(this.sortcount))
						this.sortcount=sortcount

						var percentagearray= JSON.parse(JSON.stringify(this.percentagearray))
						this.percentagearray=percentagearray


						this.initEarch()
				},800)
			},


			sure(){
				this.visible=false
				let UserID = sessionStorage.getItem('UserID')
				axios.get('/english/resetstudy',{
					params:{
						userid:UserID
					}
				}).then((res)=>{
						this.percentagearray=[]
						this.sortname=[]
						this.sortcount=[]
						this.getData()
						this.$message({
							message: '取消成功！',
							type: 'success',
							offset: 60
						});
					})
			}
			},
 mounted:function(){
 this.username=sessionStorage.getItem('username')
      if(sessionStorage.getItem("UserID")!=null){
            //  this.$router.push({path: '/'}).catch(err => {})
        }else{
             this.$router.push({path: '/'}).catch(err => {})
        }

			this.getData()
			
        
    }

     
}
</script>

<style  scoped>
.plan {
  width: 100%;
  height: 100%;
  background-color: rgb(250, 250, 250);
}
.reset {
    position: absolute;
  left: 50%;
  margin-left: -375px;
  margin-top: 100px;
  background-color: #fff;
  width: 750px;
  height: 520px;
  padding: 30px 30px;
}

</style>
