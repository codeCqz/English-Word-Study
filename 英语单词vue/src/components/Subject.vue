<template>
    <div id="view" :style ="{'backgroundImage': 'url(' + url + ')','backgroundRepeat': 'no-repeat','background-size':'100% 100%'}"> 
        <div class="search">   
            <ul>
                <li class="l clearfix" :style="style1" @click="toggle(1)">查词</li>
                <li class="l clearfix" :style="style2" @click="toggle(2)">翻译</li>
            </ul>
           <div id="seinput">
                <div v-show="isSearch">
                    <input  type="text" placeholder="请输入您要查询的单词" v-model="word" @keyup.enter="search" />
                    <button class="el-icon-search"   @click="search"></button>
                </div>
                <div v-show="!isSearch">
                    <input  type="text" placeholder="请输入您要查询的短语或句子" v-model="article" @keyup.enter="translate"/>
                    <button class="el-icon-search"    @click="translate"></button>
                </div>
           </div>
        </div>
      <keep-alive>
        <router-view></router-view>
      </keep-alive>
    </div>
</template>


<script>
import axios from 'axios' 

export default {
  name: 'Subject',
  data() {
      return {
        v:1,
        word:'',
        article:'',
        code01:'',
        code02:'',
        url:'https://browser9.qhimg.com/bdr/__85/t01560a2e72f51deec4.jpg',
        translation:{},
        isRouterAlive: true,
        isSearch: true,
        style1:"background-color: #fff;",
        style2:"",
        id: '',
        sort:[],
        strings:[],
        currentDate: new Date(),
        isShow: true
      };
  },
  methods:{
//    back() {
//       this.$store.state.tagsView.visitedViews.splice(this.$store.state.tagsView.visitedViews.findIndex(item => item.path === this.$route.path), 1)
//       this.$router.push(this.$store.state.tagsView.visitedViews[this.$store.state.tagsView.visitedViews.length - 1].path)
//     },
      search(){

        if(this.word!=''){
            axios.get('/english/findword',{
                params: {
                    word:this.word
                }    
            }).then((res)=>{
                console.log(res.data)
                
                if(res.data===""){
                    
                    this.$message({
                        message: '抱歉，没有找到您要查找的单词!',
                        type: 'warning',
                        offset:60
                    });
                }else{
                    this.translation=res.data
                    this.$store.state.word=this.word
                    this.$store.state.translation=this.translation
                    this.isShow=false
                    localStorage.setItem("messageStore",JSON.stringify(this.$store.state))
                    this.$router.push({path: '/main/result'}).catch(err => {})
      
                }
            }),error=>{
                this.$message({
                    message: '抱歉，没有找到您要查找的单词!',
                    type: 'warning'
                });
            }
        }
      },
      toggle(val){
          if(this.v==val){

          }else{
            
            this.isSearch=!this.isSearch
            if(this.v==2){
                this.style1="background-color: #fff;"
                this.style2=""
                this.v=1
            }else{
                this.style1=""
                this.style2="background-color: #fff;"
                this.v=2
            }
          }
      },
      translate(){
            var reg = new RegExp("[\\u4E00-\\u9FFF]");
			if(reg.test(this.article)){
				this.code01="zh"
                this.code02="en"     
			}else{
				this.code01="en"
                this.code02="zh"
			}
            sessionStorage.setItem('code01',this.code01);
            sessionStorage.setItem('code02',this.code02);
            sessionStorage.setItem('article',this.article);
            this.$router.push({path: '/main/translation'}).catch(err => {})
        
      }
  },
  mounted:function(){
    //   let format = 'js'
    //   let idx = '0'
    //   let n = '1'
    //   let mkt = 'zh-CN'
    //   let imgUrl = '/wallpaper/' + 'HPImageArchive.aspx?' + `format=${format}&idx=${idx}&n=${n}&mkt=${mkt}`
    //   axios.get(imgUrl).then((res)=>{
        
    //     if(res.data.images[0].url!=null){
    //         this.url = "https://cn.bing.com"+res.data.images[0].url
            
    //     }else{
            
    //     }
    //   })

      
   },
}
</script>

<style  scoped>

.search {
    position: absolute;
    left: 50%;
    margin-left: -25%;
    margin-top: 20%;
    width: 100%;
    height: 58%;
}
#seinput {
    width: 100%;
    height: 100%;
    float: left;
}
#seinput>div {
    width: 100%;
    height: 100%;
}
input {
    outline: none;
    height: 14%;
    width:40%;
    border: none;
    font-size: 20px;
    border-radius:0px;
    border-radius: 0px;
    padding-left: 15px;
    /* box-shadow: -9px 1px 12px 0 rgba(0, 0, 0, 0.06); */
    vertical-align: middle;
}
button {
    height:14%;
    width: 5%;
    border: none;
    font-size: 20px;
    border-radius:0px;
    border-radius: 0px;
    /* box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1); */
    background-color: lightgray;
    vertical-align:middle;
}
/* input:focus{
    background-color:#F5F5F5;
} */
.l {
    list-style-type:none;
    float: left;
    width: 60px;
    height: 40px;
    text-align: center;
    line-height: 40px;
    border-top-left-radius: 4px;
    border-top-right-radius: 4px;
    background-color: #ffffff66;
}
.l:nth-last-child(1)
{
    margin-left: 10px;
}

.clearfix:after{
    content: "020"; 
    display: block; 
    height: 0; 
    clear: both; 
    visibility: hidden;  
}

.clearfix {
  /* 触发 hasLayout */ 
  zoom: 1; 
}
#view {

  height: 100%;
  width: 100%;
}


#dd:not(:first-child) {
   margin-left: 26px;
}
#dd {
    font-family: ys;
    list-style-type:none;
    float: left;
    width: 66px;
    height: 66px;
    box-shadow: 0 0 6px #fff;
    border-radius: 4px;
    line-height: 21px;
    font-size: 21px;
    text-align: center;
    background-color: #fff;
    padding: 12px;
}
#dd:hover{
    transform: scale(1.2,1.2);
    box-shadow: 0 0 6px #fff;
    cursor: pointer;
}
.today {
    position: absolute;
    margin-top: 400px;
}

</style>
