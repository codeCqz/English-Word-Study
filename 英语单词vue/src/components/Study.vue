<template>
    <div class="study">   
       <div class="one">
          <h1>{{this.levelname}}</h1>
       </div>
       <div id="tr">
          <h1>{{this.word}}</h1>
          <p>英[{{this.ph_en}}]</p>
          <i class="play el-icon-microphone" @click="play"></i>
          <div v-show="show">
               <div class="t" >
                <h3>释义</h3>
                <ul v-for="t in translation" :key="t.id">
                   <li>
                      <i>{{t.part}}</i><span>{{t.translation}}</span>
                   </li>
                </ul>
              </div>
              <el-button type="success" @click="know">认识</el-button>
              <el-button type="info" @click="dim">模糊</el-button>
              <el-button type="warning" @click="incognizance">不认识</el-button>
              <el-button type="primary" @click="addnewwordlist">加入生词</el-button>
            </div>
          <div id="showAnswer" v-show="showAn">
            <div>
              请把英文发音和中文解释说出口
            </div>
            <button @click="showAnswer">
              点击显示答案
            </button>
          </div>


            
       </div>
    </div>
</template>


<script>
import axios from 'axios' 
export default {
  name: 'Study',
   data() {
      return {
        i:0,
        length:'',
        show: false,
        showAn:true,
        translation:'',
        level: '',
        levelname:'',
        studyform:'',
        word:'',
        ph_en:'',
        worddata:[]
      };
    },
    methods:{
        addnewwordlist(){
             axios.get('/english/addnewwordlist',{
                    params: {
                        userid:sessionStorage.getItem('UserID'),
                        word:this.word,
                        level:this.level
                    }    
                }).then((res)=>{
                    this.$message({
                      message: '收藏成功！',
                      type: 'success',
                      offset: 60
                    });
                })



        },


        play(){
          var url = "http://dict.youdao.com/dictvoice?type=0&audio="+this.word
          this.audio = new Audio();
          this.audio.src =url;
          this.audio.play();
        },
        showAnswer(){
          this.play();
          this.showAn=!this.showAn
          this.show=!this.show
           axios.get('/english/findword',{
                params: {
                    word:this.word
                }    
            }).then((res)=>{
                
                    this.translation=res.data
                    console.log(res.data)
      
            }),error=>{
                this.$message({
                  message: '抱歉，没有找到您要查找的单词!',
                  type: 'warning',
                  offset: 60
                });
            }
        },
        know(){
               this.showAn=!this.showAn
              this.show=!this.show
    
            let UserID = sessionStorage.getItem('UserID');
            axios.get('/english/recitewords',{
              params: {
                  word:this.word,
                  id: sessionStorage.getItem('UserID')
              }    
            }).then((res)=>{
                if(this.i+1>=this.length){
                this.$message({
                        message: '恭喜您完成今日计划！',
                        type: 'success',
                        offset: 60
                    });
          


         




      
                axios.get('/english/setplan',{
                    params: {
                        id:UserID
                    }    
                }).then((res)=>{
                    
                })

            this.$router.push({path: '/'}).catch(err => {})
   
            axios.get(
              '/english/getreview',{
                    params: {
                        id:UserID
                    }    
                }
                ).then((res)=>{
                  console.log(res.data)
                if(res.data=='0'){
                axios.get('/english/checkcomplete',{
                    params: {
                        userid:sessionStorage.getItem('UserID'),
                        level:this.level
                    }    
                }).then((res)=>{
                    if(res.data==1){
                       this.$message({
                          message: '恭喜您已经完成此计划!',
                          type: 'success',
                          offset: 60
                        });
                       this.$router.push({path: '/'}).catch(err => {})
                    }else{
                        this.$router.push({path: '/'}).catch(err => {})
                    }
                })
             
                }
            })
      }else{
      this.i+=1
      this.word = this.worddata[this.i].word
          let soundmarkUrl = '/soundmark/' + "api/dictionary.php?w="+this.word+"&type=json&key=54A9DE969E911BC5294B70DA8ED5C9C4"
          axios.post(soundmarkUrl).then((res)=>{
            console.log(res.data)
            this.ph_en=res.data.symbols[0].ph_en
          })
       
           axios.get('/english/findword',{
                params: {
                    word:this.word
                }    
            }).then((res)=>{
                    this.translation=res.data
                    console.log(res.data)
            }),error=>{
                this.$message({
                  message: '抱歉，没有找到您要查找的单词!',
                  type: 'warning',
                  offset: 60
                });
            }
      
      // })
      }




            })
            
      
      

        },
        dim(){
            this.showAn=!this.showAn
            this.show=!this.show
           axios.get('/english/setdimword',{
              params: {
                  word:this.word,
                  id: sessionStorage.getItem('UserID')
              }    
            }).then((res)=>{


            })



          if(this.i+6<=this.length){
            this.worddata.splice(this.i+3,0,this.worddata[this.i])
            this.worddata.splice(this.i+6,0,this.worddata[this.i])
            this.length+=2
          }else if(this.i+4<=this.length){
            this.worddata.splice(this.i+2,0,this.worddata[this.i])
            this.worddata.splice(this.i+4,0,this.worddata[this.i])
            this.length+=2
          }else{
            this.worddata.splice(this.i+1,0,this.worddata[this.i])
            this.worddata.splice(this.i+2,0,this.worddata[this.i])
            this.length+=2
          }






          // console.log(this.worddata)
            this.i+=1
            this.word = this.worddata[this.i].word
             let soundmarkUrl = '/soundmark/' + "api/dictionary.php?w="+this.word+"&type=json&key=54A9DE969E911BC5294B70DA8ED5C9C4"
            axios.post(soundmarkUrl).then((res)=>{
            this.ph_en=res.data.symbols[0].ph_en
          })
       



          //  axios.get('/english/findword',{
          //       params: {
          //           word:this.word
          //       }    
          //   }).then((res)=>{
          //           this.translation=res.data
          //           console.log(res.data)
      
          //   }),error=>{
          //       this.$message({
          //         message: '抱歉，没有找到您要查找的单词!',
          //         type: 'warning',
          //         offset: 60
          //       });
          //   }
        },
        incognizance(){
          this.showAn=!this.showAn
          this.show=!this.show

        axios.get('/english/setincognizancword',{
              params: {
                  word:this.word,
                  id: sessionStorage.getItem('UserID')
              }    
            }).then((res)=>{


            })



          console.log(this.i)
          if(this.i+9<=this.length){
            this.worddata.splice(this.i+3,0,this.worddata[this.i])
            this.worddata.splice(this.i+6,0,this.worddata[this.i])
            this.worddata.splice(this.i+9,0,this.worddata[this.i])
            this.length+=3
          }else if(this.i+6<=this.length){
            this.worddata.splice(this.i+2,0,this.worddata[this.i])
            this.worddata.splice(this.i+4,0,this.worddata[this.i])
            this.worddata.splice(this.i+6,0,this.worddata[this.i])
            this.length+=3
          }else{
            this.worddata.splice(this.i+1,0,this.worddata[this.i])
            this.worddata.splice(this.i+2,0,this.worddata[this.i])
            this.worddata.splice(this.i+3,0,this.worddata[this.i])
            this.length+=3
          }











          this.i+=1
          
          this.word = this.worddata[this.i].word
           let soundmarkUrl = '/soundmark/' + "api/dictionary.php?w="+this.word+"&type=json&key=54A9DE969E911BC5294B70DA8ED5C9C4"
           axios.post(soundmarkUrl).then((res)=>{
            // console.log(res.data)
            this.ph_en=res.data.symbols[0].ph_en
          })
       
           axios.get('/english/findword',{
                params: {
                    word:this.word
                }    
            }).then((res)=>{
                this.translation=res.data
                // console.log(res.data)
      
            }),error=>{
                this.$message({
                  message: '抱歉，没有找到您要查找的单词!',
                  type: 'warning',
                  offset: 60
                });
            }
          
        }
    }, 
    mounted:function(){
       this.username=sessionStorage.getItem('username')
        if(sessionStorage.getItem("UserID")!=null){
            //  this.$router.push({path: '/'}).catch(err => {})
        }else{
             this.$router.push({path: '/'}).catch(err => {})
        }

      let UserID = sessionStorage.getItem('UserID');
      axios.get('/english/getinfo',{params: {
              id:UserID,
          }  }).then((res)=>{
      
            this.level=res.data.level
      
             axios.get('/english/getsortname',{
                  params: {
                      level:this.level,
                  }    
              }).then((res)=>{
                  this.levelname=res.data
           
              })
          })

         
             
     axios.get('/english/getdata',{
          params: {
              userid: sessionStorage.getItem('UserID')
          }    
      }).then((res)=>{
          
          this.worddata=res.data
          this.word = this.worddata[this.i].word
          this.length = this.worddata.length
          console.log(this.worddata)


          let soundmarkUrl = '/soundmark/' + "api/dictionary.php?w="+this.word+"&type=json&key=54A9DE969E911BC5294B70DA8ED5C9C4"
          axios.post(soundmarkUrl).then((res)=>{
            this.ph_en=res.data.symbols[0].ph_en
          })
      })
      
      
      
   
    }
}
</script>

<style  scoped>
.study {
  width: 100%;
  height: calc(100vw - 550px);
  background-color: rgb(250, 250, 250);
}


#tr {
  position: absolute;
  left: 50%;
  margin-left: -375px;
  margin-top: 160px;
  background-color: #fff;
  width: 750px;
  min-height: 500px;
  padding: 30px 30px;
  text-align: center;
  line-height: 100px;
  /* font-size: 50px; */
}
#tr>p{
  font-size: 16px; 
  display: inline-block;
  margin-left: 20px;
}
#tr>h1{ 
  display: inline-block;
  font-size: 50px;
  margin-top: 60px;
}

#showAnswer>div {
  font-size: 25px; 
  margin-top: 30px;
}
#showAnswer>button {
  font-size: 25px; 
  margin-top: -100px;
  width: 160px;
  background-color: Transparent; 
  border-style :none;
  color: #7FFF00;
}



.one {
  position: absolute;
  margin-top: 90px;
  width: 760;
  left: 50%;
  margin-left: -375px;
}
.play {

  color: rgba(0,0,0,.45);
  display: inline-block;
  margin-left: 20px;

}
.play:active {
  color:skyblue
}
button {
  margin-top: 30px;
  margin-right: 50px;
  width: 100px;
  height: 50px;
}
p {
  display: inline-block;
}
.play {
  font-weight: 700;
  font-size: 20px;
  color: rgba(0,0,0,.45);
  display: inline-block;
  margin-left: 10px;

}
.play:active {
  color:skyblue
}


.t {
    text-align: left;
    border-top: 1px solid #f0f0f0;
    margin-top: 26px;
    padding-top: 20px;
    padding-left: 50px;
    padding-right: 50px;
}
ul>li {
  display: flex;
  list-style-type:none;
  font-size: 25px;
  margin-top: 20px;
}
ul>li>span {
  display: inline-block;
  margin-left: 40px;
  word-wrap:break-word;
  line-height: 25px;
  width:580px;
  height: 56px;
}
ul>li>i {
    font-weight: 700;
    font-style: italic;
    font-size: 30px;
    color: rgba(0,0,0,.45);
    display: inline-block;
    min-width: 60px;
    flex-grow: 0;
    flex-shrink: 0;
    height: 56px;
    line-height: 25px;
}
</style>
