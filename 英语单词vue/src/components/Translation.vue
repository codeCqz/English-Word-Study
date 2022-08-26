<template>
    <div class="translation">   
        <div class="result">
            <div>  <el-button type="primary" @click="translate">翻译</el-button></div>
            <textarea class="text1" v-model="article" @keyup.enter="translate"></textarea>
            <textarea class="text2" v-model="result" readonly></textarea>
        </div>
    </div>
</template>


<script>
import axios from 'axios' 
export default {

  name: 'Translation',
   data() {
      return {
        article: '',
        result: '',
        code01:'',
        code02:'',
      }
    },
    methods: {
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
            console.log(this.code01+"--->"+this.code02)
            axios.get('/english/translate',{
                    params: {
                        article:this.article,
                        code1:sessionStorage.getItem('code01'),
                        code2:sessionStorage.getItem('code02')
                    }    
                }).then((res)=>{
                    console.log(res.data.trans_result[0].dst)
                    this.result = res.data.trans_result[0].dst
                })
        }
    },
    mounted:function(){
        let code1 = sessionStorage.getItem('code01');
        let code2 = sessionStorage.getItem('code02');
        let article = sessionStorage.getItem('article');
        this.article = article
       console.log(this.code01+"--->"+this.code02)
         axios.get('/english/translate',{
                params: {
                    article:this.article,
                    code1,
                    code2
                }    
            }).then((res)=>{
                console.log(res.data.trans_result[0].dst)
                this.result = res.data.trans_result[0].dst
            })
    }
}
</script>

<style  scoped>
.translation {
    width: 100%;
    height: 100%;
    background-color: rgb(250, 250, 250);
    padding-top: 200px;
}
.result {
    margin: 0 auto;
    width: 1030px;
    /* margin-top: 200px; */
}
textarea {
    font-size: 24px; line-height: 30px; height: 156px; 
    width: 500px;
    height: 300px;
    resize: none;
    padding: 15px;
}
.text2 {
    background-color: 	#F0F0F0;
    border-style:none;  
}
.text2:focus {
    outline: none;
}
.text1{
    border: 1px solid #DCDCDC;
}
.text1:focus {
    outline: none;
    border: 1px solid #4E6EF2;
}
button {
    margin-bottom: 20px;
}
</style>
