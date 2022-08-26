<template>
  <div id="studyenglish">
      <div class="search">
          <input  type="text" placeholder="请输入您要查询的单词" v-model="word"/>
           <el-button type="primary" id="sh" @click="search">查词</el-button>


          <el-button class="function1"  @click="exitSearch()">取消搜索</el-button>
          <el-button  class="function1"  @click="toggleSelection()">取消选择</el-button>
          <el-button  class="function"   @click="join()">加入今日计划</el-button>
		      <el-button  class="function"   @click="todayplan()">查看今日计划</el-button>
          <el-button  class="function1"  @click="finish()">选择完毕</el-button> 
        </div>
       
            <el-dialog title="今日计划" :visible.sync="dialogTableVisible">
               <div id="plantoday">
                <el-table :data="todaytableData">
                  tooltip-effect="dark"
                  style="width: 100%">
                  <el-table-column
                    label="单词"
                    width="180">
                    <template slot-scope="scope">{{ scope.row.word }}</template>
                  </el-table-column>
                  <el-table-column
                    prop="translation"
                    label="翻译"
                    show-overflow-tooltip>
                  </el-table-column>
                  <el-table-column
                    fixed="right"
                    label="操作"
                    width="120">
                    <template slot-scope="scope">
                      <el-button
                        @click.native.prevent="deleteRow(scope.$index, todaytableData)"
                        type="text"
                        size="small">
                        移除
                      </el-button>
                    </template>
                  </el-table-column>
                </el-table>
                </div>
              </el-dialog>
   


        <div id="tr">



















              <el-table
                    
                      ref="multipleTable"
                      :data="tableData"
                      tooltip-effect="dark"
                      style="width: 100%"
                      @selection-change="handleSelectionChange">
                      <el-table-column
                        type="selection"
                        width="55">
                      </el-table-column>
                      <el-table-column
                        label="单词"
                        width="180">
                        <template slot-scope="scope">{{ scope.row.word }}</template>
                      </el-table-column>
                      <el-table-column
                        prop="translation"
                        label="翻译"
                        show-overflow-tooltip>
                      </el-table-column>
                    </el-table>

 <el-pagination
  layout="prev, pager, next"
  :total="total"
  :page-size="pagesize" 
  :current-page="currentPage"
  @current-change="currentchange"
   :hide-on-single-page="true"
   v-if="total>1"
  >
  </el-pagination>

        </div>
  </div>
</template>

<script>
import axios from 'axios' 
export default {
  
  name: 'Choose',
  data(){
    return {
        pagesize:9,
        word:"",
        delword:'',
        dialogTableVisible: false,
        level:'',
        form:{
          word:'',
          // way:this.$store.state.way,
          // studyform:'',
          userid: sessionStorage.getItem('UserID')
        },
        currentPage:1,
        total: null,
        tableData: [],
        todaytableData:[],
        multipleSelection: []
    }
  },
  methods:{
      toggleSelection() {
          this.$refs.multipleTable.clearSelection();
      },
    deleteRow(index, rows) {
        console.log(rows[index].word)
        this.delword=rows[index].word
        axios.get('/english/deleteword',{ 
          params: {
                  userid:this.form.userid,
                  word:this.delword
                }    
                }).then((res)=>{
                  if(res.data=="200"){
                        this.$message({
                        message: '移除成功',
                        type: 'success',
                        offset: 60
                    });
                  }else{
                        this.$message({
                        message: '移除失败',
                        type: 'error',
                        offset: 60
                    });
                  }
                })
        rows.splice(index, 1);
        
      },
      todayplan(){
        this.dialogTableVisible = true

        axios.get('/english/gettodayplan',{ 
          params: {
                  id:sessionStorage.getItem('UserID'),
                }    
                }).then((res)=>{
                  // console.log(res.data)
                  this.todaytableData=res.data
                })

      },
      exitSearch(){
          let count = sessionStorage.getItem("sortcount")
          var countvalue=Number(count);
        
          this.total=countvalue
         

         
          axios.get('/english/findsortword',{
                params: {
                  userid:sessionStorage.getItem('UserID'),
                  currentPage:this.currentPage
                }    
                }).then((res)=>{
                  console.log(res.data)
                  this.tableData=res.data
                })
       

      },

      join(){
        for(var i=0;i<9;i++){
            if(typeof this.$refs.multipleTable.selection[i] != 'undefined'){
                console.log(this.$refs.multipleTable.selection[i].word)
                this.form.word+=this.$refs.multipleTable.selection[i].word+","
            }else{
              console.log("null")
            }  
            }
            axios.get('/english/saveword',{
            params: {
              form:this.form
            }    
            }).then((res)=>{
            this.form.word=''
            console.log(res.data)
            if(res.data=="200"){
               this.$message({
                  message: '加入成功！！！',
                  type: 'success',
                  offset:60
                });
            }else if(res.data=="500"){
               this.$message({
                  message: '请勿重复添加',
                  type: 'warning',
                  offset: 60
                });
            }else{
               this.$message({
                  message: '添加错误',
                  type: 'error',
                  offset: 60
                });
            }
                
                  })
            },
      search(){
        if(this.word!=""){
        axios.get('/english/findmyword',{
                        params: {
                          word:this.word
                        }    
                        }).then((res)=>{
                          console.log(res.data)
                          this.tableData=res.data
                          this.total = 1
                        })
        }else{
           this.$message({
          message: '请输入单词后再搜索',
          type: 'warning',
          offset: 60
        });
        }
        
      },

      currentchange(val){

        console.log(val)
        this.currentPage=val
         axios.get('/english/findsortword',{
                params: {
                 userid: sessionStorage.getItem('UserID'),
                  currentPage:this.currentPage
                }    
                }).then((res)=>{
                  console.log(res.data)
                  this.tableData=res.data
                })
        },
      toggleSelection() {
          this.$refs.multipleTable.clearSelection();
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      finish(){
         axios.get('/english/finish',{
                  params: {id:this.form.userid}  
                }).then((res)=>{
                    this.$router.push({path: '/main/study'}).catch(err => {})
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


      let count = sessionStorage.getItem("sortcount")
      var countvalue=Number(count);
      this.level=sessionStorage.getItem('level');
      this.total=countvalue
       axios.get('/english/findsortword',{
                params: {
                  userid: sessionStorage.getItem('UserID'),
                  currentPage:this.currentPage
                }    
                }).then((res)=>{
                  console.log(res.data)
                  this.tableData=res.data
                })
                      
                 
    }
}
</script>
<style scoped>
#studyenglish {
  width: 100%;
  height: 100%;
  background-color: rgb(250, 250, 250);
}
.search {
    position: absolute;
    left: 50%;
    margin-left: -40%;
    margin-top: 6%;
}
input {
    outline: none;
    height: 66px;
    width: 20%;
    border: none;
    font-size: 1.25em;
    border-radius:0px;
    border-radius: 0px;
    padding-left: 1%;
    vertical-align: middle;
}
#sh {
    height: 66px;
    width: 8%;
    border: none;
    font-size: 1.25em;
    border-radius:0px;
    border-radius: 0px;
    vertical-align:middle;
    margin-right: 1%;
}
.function {
  height: 66px;
  width: 160px;
  border: none;
  font-size: 20px;
  text-align: center;
  border-radius:0px;
  border-radius: 0px;
  vertical-align:middle;
  margin-left: 22px;
}

.function1 {
  height: 66px;
    width: 120px;
    border: none;
    font-size: 20px;
    text-align: center;
    border-radius:0px;
    border-radius: 0px;
    vertical-align:middle;
    margin-left: 22px;
}
#tr {
  position: absolute;
  left: 50%;
  margin-left: -40%;
  margin-top: 180px;
  background-color: #fff;
  width: 80%;
  height: 560px;
  padding: 30px 30px;
}
#plantoday {
  overflow: auto;
  height: 500px;
}
</style>