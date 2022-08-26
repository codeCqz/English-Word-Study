<template> 
    <div class="info">   
       <div class="upload">
         <el-form ref="form">
         <el-form-item label="用户名" prop="username">
           <el-input v-model="form.username" placeholder="username"></el-input>
         </el-form-item>
         <el-form-item label="密 码" prop="password">
           <el-input v-model="form.password" placeholder="password"></el-input>
         </el-form-item>
         <el-form-item label="邮 箱" prop="email">
           <el-input v-model="form.email" placeholder="email"></el-input> 
         </el-form-item>

      </el-form>

          <el-button @click="handleSubmit()">修 改</el-button>
       </div>

    </div>
</template>


<script>
import axios from 'axios'
 
export default {
  name: 'Info',
   data() {
      return {
        form:{
          userid: sessionStorage.getItem('UserID'),
          username:'',
          password:'',
          email:''
        },
         username:'',
                password:'',
                email:'',
                 options: [],
      }
  },
  methods: {
      handleSubmit(){
        axios.get('/english/updateuser',{
        params: {
          form:this.form
        }    
        }).then((res)=>{
            this.$message({
                  message: '修改成功!',
                  type: 'success',
                  offset:60,
              });
        })
    }
  },
  mounted:function(){
      axios.get('/english/getuser',{params: {
          userid:this.form.userid
        }}).then((res)=>{
           console.log(res.data)
           this.form.username = res.data.username
           this.form.password = res.data.password
           this.form.email = res.data.email
        })



  }





}
</script>

<style  scoped>
.info {
  width: 100%;
  height: 100%;
  background-color: rgb(250, 250, 250);
}
.upload {
    position: absolute;
  left: 50%;
  margin-left: -375px;
  margin-top: 100px;
  background-color: #fff;
  width: 750px;
  height: 520px;
  padding: 30px 30px;
}
.el-input{
  float: right;
    /* margin-top: 60px; */
    margin-left: 20px;
    width: 610px;
}
.el-form-item {
  margin-top: 60px;
}
button {
  margin-top: 60px;
}
</style>
