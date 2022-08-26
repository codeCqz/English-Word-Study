<template>
    <header>
        <div class="today">
            <span>每日一句:</span><p v-text="strings"></p><i class="play el-icon-microphone" @click="play"></i>
        </div>



        <div class="user" v-show="isLogin">
            <el-dropdown trigger="click" @command="handleCommand">
                <span class="el-dropdown-link ">
                    欢迎登录,<i>{{this.username}}</i>
                </span>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item icon="el-icon-s-home" command="a">主页</el-dropdown-item>
                   
                     <el-dropdown-item icon="el-icon-s-custom" command="b">信息</el-dropdown-item> 
                    <el-dropdown-item icon="el-icon-s-order" command="d">计划</el-dropdown-item>
                    <el-dropdown-item icon=" el-icon-folder-opened" command="e">生词表</el-dropdown-item> 
                    <el-dropdown-item icon="el-icon-switch-button" command="c">退出</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>



            <el-button type="success" @click="study">学习单词</el-button>

            <el-popover
                placement="top"
                width="160"
                v-model="visible">
                <p>今日计划已经完成确认继续学习吗?</p>
                <div style="text-align: right; margin: 0">
                    <el-button size="mini" type="text" @click="visible = false">取消</el-button>
                    <el-button type="primary" size="mini" @click="sure">确定</el-button>
                </div>
            </el-popover>


        </div>




        <div class="login" v-show="!isLogin">
            <el-button type="primary" @click="dialogFormVisible = true">登录</el-button>
            <!-- Form -->

            <el-dialog width="450px" title="登录" :visible.sync="dialogFormVisible" center>
                <el-form ref="form" :model="form" :rules="rules"  label-width="60px">
                    <el-form-item
                            prop="email"
                            label="邮箱"
                            :rules="[
                                { required: true, message: '请输入邮箱地址', trigger: 'blur' },
                                { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
                            ]">
                        <el-input v-model="form.email"  placeholder="邮箱" clearable></el-input>
                    </el-form-item>

                    <el-form-item label="密码" prop="password">
                        <el-input v-model="form.password" placeholder="密码" clearable show-password></el-input>
                    </el-form-item>

                    <!-- <el-checkbox v-model="checked">记住密码</el-checkbox> -->
                </el-form>
                    <div slot="footer" class="dialog-footer">
                        <el-button @click="dialogFormVisible = false">取 消</el-button>
                        <el-button type="primary" @click="login">登 录</el-button>
                    </div>
                </el-dialog>
            <el-button type="warning" @click="dialogFormVisible2= true">注册</el-button>
            <el-dialog width="450px" title="注册" :visible.sync="dialogFormVisible2" center>
                <el-form  :model="form2" :rules="rules2"  label-width="80px"  ref="form2" >
                    <el-form-item label="用户名" prop="username">
                        <el-input v-model="form2.username" placeholder="用户名" clearable></el-input>
                    </el-form-item>

                    <el-form-item label="密码" prop="password">
                        <el-input v-model="form2.password" placeholder="密码" clearable show-password autocomplete="off"></el-input>
                    </el-form-item>

                    <el-form-item label="确认密码" prop="password2">
                        <el-input v-model="form2.password2" placeholder="确认密码" clearable show-password autocomplete="off"></el-input>
                    </el-form-item>

                    <el-form-item
                        prop="email"
                        label="邮箱"
                        :rules2="[
                            { required: true, message: '请输入邮箱地址', trigger: 'blur' },
                            { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
                        ]">
                        <el-input v-model="form2.email"  placeholder="邮箱" clearable></el-input>
                    </el-form-item>

                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible2 = false">取 消</el-button>
                    <el-button type="primary" @click="register">确 定</el-button>
                </div>
            </el-dialog>
            <el-button type="success" @click="check">学习单词</el-button>
        </div>
        <el-dialog  width="450px" title="学习方式" :visible.sync="dialogFormVisible3" center="">
            <el-form :model="studyform">

                <el-form-item label="词汇分类" :label-width="formLabelWidth">
                    <el-select v-model="studyform.level" placeholder="请选择词汇分类"
                            :popper-append-to-body="false" 
                            popper-class="select-popper">
                        <el-option
                            v-for="item in options"
                            :key="item.id"
                            :label=item.sortname+item.sortcount
                            :value="item.level">
                        </el-option>
                    </el-select>
                </el-form-item>



                <el-form-item label="学习方式" :label-width="formLabelWidth">
                    <el-select v-model="studyform.way" placeholder="请选择学习方式"
                            :popper-append-to-body="false" 
                            popper-class="select-popper"
                            @change="change"
                    >     
                        <el-option label="系统选词学习" value="system"></el-option>
                        <el-option label="自己选词学习" value="self"></el-option>
                    </el-select>
                </el-form-item>

        <el-form-item label="单词数量" :label-width="formLabelWidth" v-show="isChange">   
                    <el-select v-model="studyform.count" placeholder="请选择单词数量"
                    :popper-append-to-body="false" 
                            popper-class="select-popper"
                            allow-create
                            filterable
                    >
                        <el-option label="20" value="20"></el-option>
                        <el-option label="40" value="40"></el-option>
                        <el-option label="60" value="60"></el-option>
                        <el-option label="80" value="80"></el-option>
                    </el-select>

        </el-form-item>


            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible3 = false">取 消</el-button>
                <el-button type="primary" @click="submit">确 定</el-button>
            </div>
        </el-dialog>
    </header>
</template>


<script>
import axios from 'axios' 
export default {
  name: 'Header',
  data(){
       var validatePass = (rule2, value, callback) => {
        if (value === '') {
            callback(new Error('请输入密码'));
        } else {
            if (this.form2.password2 !== '') {
                this.$refs.form2.validateField('password2');
            }
            callback();
        }
    };
    var validatePass2 = (rule2, value, callback) => {
        if (value === '') {
            callback(new Error('请再次输入密码'));
        } else if (value !== this.form2.password) {
            callback(new Error('两次输入密码不一致!'));
        } else {
            callback();
        }
    };
      return {
          val:'',
          strings:'',
          src:'',
          isLogin: false,
          isChange: false,
          visible: false,
          dialogFormVisible: false,
          dialogFormVisible2: false,
          dialogFormVisible3: false,
          allcount:'',
           form: {
                email:'',
                password:'',
                
            },
            // autologin: ture,
            rules: {
                password: [
                    { required: true, message: '请输入密码' ,trigger: 'blur'}
                ],
            },

            form2: {
                username:'',
                password:'',
                password2:'',
                email:''
            },
      
      rules2: {
        username: [
            { required: true, message: '请输入用户名' ,trigger: 'blur'}
        ],
        password:[
            { validator: validatePass, trigger: 'blur' }
        ],
        password2: [
            { validator: validatePass2, trigger: 'blur' }
        ]
      },
      formLabelWidth: '120px',
      studyform:{
        way:'',
        count:'',
        level:''
      },
      options: [],
      username:'',
      UserID:''
      }
  },
  methods:{
      submit(){
          if((this.studyform.level==""&& this.studyform.way=="")||(this.studyform.level=="") || (this.studyform.way=="")||(this.studyform.way=="system"&&this.studyform.count=="")){
            this.$message({
                message: '请选择后进行学习哦!',
                type: 'warning',
                offset:60,
                customClass:'zZindex'
            });
          }else{ 
                // console.log(this.studyform)
                let UserID = sessionStorage.getItem('UserID');
                axios.get('/english/study',{
                    params: {
                        form:this.studyform,
                        id: UserID
                    }    
                }).then((res)=>{
                    console.log(res.data)
                    if(res.data=='1'){
                         this.$message({
                            message: '请选择继续学习',
                            type: 'warning',
                            offset:70
                        });
                    }else{
                        this.$store.state.sortcount=res.data
                        this.$store.state.way = this.studyform.way
                        this.dialogFormVisible3=false
                        this.$store.state.studyform=this.studyform
                        sessionStorage.setItem('level',JSON.stringify(this.$store.state.studyform.level));
                        sessionStorage.setItem("sortcount",this.$store.state.sortcount)
                        if(this.studyform.way=="self"){
                            this.$router.push({path: '/main/choose'}).catch(err => {})
                        }else{
                            this.$router.push({path: '/main/study'}).catch(err => {})
                        }
                    }
                })
                 
          }
          sessionStorage.setItem("studyform", this.studyform)
      },
      handleCommand(command) {
        if(command=="c"){
            console.log("退出");
            this.isLogin=false
            sessionStorage.clear(); 
             this.$router.push({path: '/'}).catch(err => {})
             this.$notify({
                title: '提示',
                message: '退出成功！',
                type: 'success',
                offset: 50
            }); 
        }else if(command=="b"){
            this.$router.push({path: '/main/info'}).catch(err => {})
        }
        else if(command=="a"){
            this.$router.push({path: '/'}).catch(err => {})
        }
        else if(command=="d"){
            this.$router.push({path: '/main/plan'}).catch(err => {})
        }
        else if(command=="e"){
this.$router.push({path: '/main/collect'}).catch(err => {})
        }
      },
      check(){
        if(sessionStorage.getItem("UserID")!=null){
            this.$router.push({path: '/main/study'}).catch(err => {})
        }else{
            this.$message({
            message: '请登录后进行学习哦!',
            type: 'warning',
            offset:70
            });
        }
      },
      study(){
                let UserID = sessionStorage.getItem('UserID');
                
                console.log(UserID)
                if(UserID!=''){
                    if(UserID!=null){
                        axios.get('/english/isStudy',{params: {id: sessionStorage.getItem('UserID')}    
                    }).then((res)=>{
                        if(res.data==0){
                            this.dialogFormVisible3= true
                        }else{
                            axios.get('/english/lastTime',{params: {id: UserID}}).then((res)=>{
                               if(res.data!=0){
                                      this.$router.push({path: '/main/study'}).catch(err => {})
                                }else{
                                    let userid =   sessionStorage.getItem("UserID")
                                    axios.get('/english/getplan',{ params: { userid:userid}}).then((res)=>{
                                        if(res.data=='1'){
                                            this.visible=true
                                        }
                                    })
                                }
                            })
                        }
                    })
                        }else{
                            this.$router.push({path: '/main/study'}).catch(err => {})                
                        }
                }

      },
      sure(){
        this.visible = false
        let UserID = sessionStorage.getItem('UserID')
                axios.get('/english/getInfo',{
                    params:{
                        userid:UserID
                    }
                }).then((res)=>{
                if(res.data.way=='self'){
                      let UserID = sessionStorage.getItem('UserID');
                
                console.log(UserID)
                if(UserID!=''){
                    if(UserID!=null){
                        axios.get('/english/isStudy',{params: {id: sessionStorage.getItem('UserID')}    
                    }).then((res)=>{
                        if(res.data==0){
                            this.dialogFormVisible3= true
                        }else{
                            axios.get('/english/lastTime',{params: {id: UserID}}).then((res)=>{
                               if(res.data!=0){
                                      this.$router.push({path: '/main/study'}).catch(err => {})
                                }else{
                                    let userid =   sessionStorage.getItem("UserID")
                                    axios.get('/english/getplan',{ params: { userid:userid}}).then((res)=>{
                                    if(res.data=='1'){
                                       
                                        let UserID = sessionStorage.getItem('UserID');
                                            if(UserID!=''){    
                                                let UserID = sessionStorage.getItem('UserID');
                                                axios.get('/english/getinfo',{params: {id:UserID,}  }).then((res)=>{
                                                console.log(res.data.way+"way")
                                                    if(res.data.way==='self'){
                                                        axios.get('/english/getplan',{params: {userid:UserID,}}).then((res)=>{
                                                            if(res.data===1){
                                                                axios.get("/english/getisreview",{params: {userid:UserID} }).then((res)=>{
                                                                console.log("review"+res.data)
                                                                if(res.data===0){
                                                                    axios.get("/english/getisstudy",{params: {userid:UserID,} }).then((res)=>{
                                                                        if(res.data===0){
                                                                            this.$router.replace({path: '/main/choose'})
                                                                        }
                                                                    })
                                                                }else{
                                                                     this.$router.replace({path: '/main/study'})
                                                                }
                                                                })
                                                            }
                                                        })
                                                    }
                                                })
                                            }
                                    }
                                    })

                            
                    
                                }
                            })
                            }
                            })
                        }else{
                            this.$router.push({path: '/main/study'}).catch(err => {})                
                        }
                }
                }else{
                    this.$router.push({path: '/main/study'}).catch(err => {})
                }
                })
       
      },
      change(){
          console.log(this.studyform.way)
          if(this.studyform.way=="system"){
            this.isChange=true;
          }else{
            this.isChange=false;
          }
      },
      login(){ 
           this.$refs['form'].validate((valid) => {
            if (valid) {
                this.dialogFormVisible = false 
              this.$notify({
                title: '提示',
                dangerouslyUseHTMLString: true,
                message: '登录中  <i class="el-icon-loading"></i>',
                offset: 50
              });
            axios.get('/english/login',{
                params: {
                  form:this.form
                }    
                }).then((res)=>{
                      if(res.data!==404){
                          
                          this.$notify({
                              title: '提示',
                              message: '登录成功！',
                              type: 'success',
                              offset: 50
                            });
                          sessionStorage.setItem('UserID',res.data);
                           axios.get('/english/getuser',{
                                params: {
                                    userid:sessionStorage.getItem('UserID')
                                }    
                            }).then((res)=>{
                                sessionStorage.setItem('username',res.data.username);
                                this.username=sessionStorage.getItem('username')
                            })
                          console.log(res.data)
                          this.UserID = sessionStorage.getItem('UserID')
                          this.isLogin=true
                            if(this.UserID!=null){
                                axios.get('/english/isStudy',{
                                params: {
                                    id:res.data
                                }    
                            }).then((res)=>{
                                if(res.data==0){
                                    this.dialogFormVisible3= false
    
                                }else{
                                   
                                }
                            })
                            }
                         
                  }else if(res.data===404){
                    
                    this.$notify.error({
                      title: '提示',
                      message: '登录失败！',
                      offset: 50
                    });
                  }     
            },error=>{
              this.$notify.error({
                    title: '提示',
                    message: '登录失败！',
                    offset: 50
                  });
            })
					} else {          
						return false;
					}
      })
      },
        play(){
            
                this.audio = new Audio();
                this.audio.src =this.src;
                this.audio.play();
            
        },

        
      register(){
        
        this.$refs['form2'].validate((valid) => {

        
        if (valid) {
            this.dialogFormVisible = false 
        this.$notify({
                title: '提示',
                dangerouslyUseHTMLString: true,
                message: '注册中  <i class="el-icon-loading"></i>',
                offset: 50
              });
            axios.get('/english/register',{
                params: {
                  form:this.form2
                }    
            }).then((res)=>{
                  if(res.data!==404){
                        this.$notify({
                        title: '提示',
                        message: '注册成功！请登录',
                        type: 'success',
                        offset: 50
                      });
                      this.dialogFormVisible2=false
                      this.dialogFormVisible = true
                  }else if(res.data===404){
                    this.$notify.error({
                      title: '提示',
                      message: '注册失败！',
                      offset: 50
                    });
                    this.dialogFormVisible2=false
                    this.form2=[]
                  }     
            },error=>{
                this.$notify.error({
                      title: '提示',
                      message: '注册失败！',
                      offset: 50
                    });
                    this.dialogFormVisible2=false
                    this.form2=[]
            })
            } else {
                return false;
            }
      })
    }
  },
 mounted:function(){
                
        this.username=sessionStorage.getItem('username')
        if(sessionStorage.getItem("UserID")!=null){
            this.isLogin=true
        }else{
            this.isLogin=false
        }


      Date.prototype.Format = function (fmt) {
            var o = {
                "M+": this.getMonth() + 1, //月份 
                "d+": this.getDate(), //日 
                "H+": this.getHours(), //小时 
                "m+": this.getMinutes(), //分 
                "s+": this.getSeconds(), //秒 
                "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
                "S": this.getMilliseconds() //毫秒 
            };
            if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
            for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            return fmt;
        }
       var date1 = new Date().Format("yyyy-MM-dd");
       var url = "/day/"+"index.php?c=dailysentence&m=getdetail&title="+date1
        axios.get(url).then((res)=>{
            this.src = res.data.tts
            this.strings+=res.data.content
            this.strings+=res.data.note
        })
            

        axios.get('/english/sort').then((res)=>{
          this.options = res.data
        })

    

 }
}
</script>

<style scoped>

header {  
    height: 7%;
    width: 100%;
    position: fixed;
    font-size: 1.125em;
    background-color: rgba(255,255,255,0.9);
    top: 0;
    /* padding-right: 2%; */
    line-height: 320%;
    z-index:9999
}
.today {
    float: left;
    width: 80%;
}
p {
    display: inline-block;
    margin-left: 0.5%;
    
}
.user {
   
    float: right;
    height: 100%;
    width: 15%

}
.user>i {
    
    font-weight:bold !important;
    color:skyblue;
}
.login {
    float: right;
    width: 20%
}
.avatar{
    height: 1%;
    width: 1%;
    border-radius: 5%;
    vertical-align:middle;
}
.el-dropdown-menu {
    
    opacity: 0.8;
}
.play:active {
    color:skyblue
}
button {
    margin-left: 5%;
}
@font-face {
  font-family: "ys";
  src: url('../assets/font/YS.TTF') format('truetype');
  font-weight: normal;
  font-style: normal;
 }
 .today {
     display: inline-block;
     font-family: ys;
 }
 .el-select /deep/ .select-popper {
      z-index: 8888 !important;
      top: auto !important;
      left: auto !important
    }
.zZindex {
z-index:3000 !important;
}
.el-checkbox {
    margin-left: 2%;
}
</style>