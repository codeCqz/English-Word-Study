<template>
    <div class="result">   
        <div class="search">
          <input  type="text" placeholder="请输入您要查询的单词" v-model="tl"  @keyup.enter="tr" />
           <el-button type="primary" @click="tr">翻译</el-button>
        </div>

        <div id="tr">
            <h1>{{this.word}}</h1>
            
            <span class="test">
              <p>英[{{this.ph_en}}]</p>
              <i class="play el-icon-microphone" @click="play"></i>
            </span>


            <div class="t" >
                <h3>释义</h3>
                <ul v-for="t in translation" :key="t.id">
                   <li>
                      <i>{{t.part}}</i><span>{{t.translation}}</span>
                   </li>
                </ul>
            </div>
        </div>
    </div>
</template>


<script>
import axios from 'axios' 
export default {
  name: 'Result',
//   back(){
//     this.$router.back()
// },
   data() {
      return {
        tl:this.$store.state.word,
        word: "",
        ph_en:'',
        translation: ""
      };
    },
    methods:{
        tr(){
          
          if(this.word!=''){
            var word =this.tl
            axios.get('/english/findword',{
                params: {
                    word:word
                }    
            }).then((res)=>{
                console.log(res.data)
                
                if(res.data===""){
                    
                    this.$message({
                        message: '抱歉，没有找到您要查找的单词!',
                        type: 'warning',
                        offset: 60
                    });
                }else{
                    this.translation=res.data
                    console.log(res.data)
                    
                    this.word =this.tl
                    this.$store.state.word = this.word
                    this.$store.state.translation = this.translation
                    let soundmarkUrl = '/soundmark/' + "api/dictionary.php?w="+this.tl+"&type=json&key=54A9DE969E911BC5294B70DA8ED5C9C4"
                    axios.post(soundmarkUrl).then((res)=>{
                      this.ph_en=res.data.symbols[0].ph_en
                    })
                    localStorage.setItem("messageStore",JSON.stringify(this.$store.state))
                }
            }),error=>{
                this.$message({
                  message: '抱歉，没有找到您要查找的单词!',
                  type: 'warning',
                  offset: 60
                });
            }
        }
        },
        play(){
          var url = "http://dict.youdao.com/dictvoice?type=0&audio="+this.word
          this.audio = new Audio();
          this.audio.src =url;
          this.audio.play();
        }  
      },
      mounted:function(){
          localStorage.getItem("messageStore") && this.$store.replaceState(Object.assign(this.$store.state,JSON.parse(localStorage.getItem("messageStore"))));
          if(this.$store.state.word!=null && this.$store.state.translation!=null){
            this.word=this.$store.state.word
            this.translation=this.$store.state.translation
            this.tl = this.word
          }

          let soundmarkUrl = '/soundmark/' + "api/dictionary.php?w="+this.tl+"&type=json&key=54A9DE969E911BC5294B70DA8ED5C9C4"
          axios.post(soundmarkUrl).then((res)=>{
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
      },
     
}
</script>

<style  scoped>
.result {
  width: 100%;
  height: 100%;
  background-color: rgb(250, 250, 250);
}
.search {
    position: absolute;
    left: 50%;
    margin-left: -375px;
    margin-top: 80px;
}
input {
    outline: none;
    height: 66px;
    width: 650px;
    border: none;
    font-size: 20px;
    border-radius:0px;
    border-radius: 0px;
    padding-left: 15px;
    /* box-shadow: -9px 1px 12px 0 rgba(0, 0, 0, 0.06); */
    vertical-align: middle;
}
button {
    height: 66px;
    width: 100px;
    border: none;
    font-size: 20px;
    border-radius:0px;
    border-radius: 0px;
    /* box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1); */
    vertical-align:middle;
}
#tr {
  position: absolute;
  left: 50%;
  margin-left: -375px;
  margin-top: 180px;
  background-color: #fff;
  width: 750px;
  height: 520px;
  padding: 30px 30px;
}
.t {
    border-top: 1px solid #f0f0f0;
    margin-top: 26px;
    padding-top: 20px;
}
ul>li {
   list-style-type:none;
   margin-top: 20px;
}
ul>li>span {
  display: inline-block;
  float: right;
  margin-left: 20px;
  word-wrap:break-word;
  width:580px;
  height: 56px;
}
ul>li>i {
    font-weight: 700;
    font-style: italic;
    font-size: 20px;
    color: rgba(0,0,0,.45);
    display: inline-block;
    min-width: 73px;
    flex-grow: 0;
    flex-shrink: 0;
    min-height: 60px;
}
h1 {
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

.test {
  margin-left: 30px;
      font-size: 16px;
    color: rgba(0,0,0,.65);
    line-height: 22px;
    /* display: inline-block; */
    margin-right: 33px;
}
.test>p {
  display: inline-block;
}
</style>
