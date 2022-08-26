<template>
  <div id="studyenglish">
      <div class="search">
          <input  type="text" placeholder="请输入您要查询的单词" v-model="word"/>
           <el-button type="primary" id="sh" @click="search">查词</el-button>


          <el-button class="function1"  @click="exitSearch()">取消搜索</el-button>
      </div>
       
       
              
              
   


        <div id="tr">
          <div id="collect">
                <el-table :data="collecttableData">
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
                        @click.native.prevent="deleteRow(scope.$index, collecttableData)"
                        type="text"
                        size="small">
                        移除
                      </el-button>
                    </template>
                  </el-table-column>
                </el-table>
                </div>


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
  
  name: 'Collect',
  data(){
    return {
        pagesize:9,
        word:"",
        delword:'',
        dialogTableVisible: false,
        // form:{
        //   word:'',
        //   studyform:'',
        //   userid: sessionStorage.getItem('UserID')
        // },
        currentPage:1,
        total: null,
        tableData: [],
        collecttableData:[],
        multipleSelection: []
    }
  },
  methods:{
    deleteRow(index, rows) {
        console.log(rows[index].word)
        this.delword=rows[index].word
        axios.get('/english/deletecollectword',{ 
          params: {
                  userid:sessionStorage.getItem('UserID'),
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
      toggleSelection() {
          this.$refs.multipleTable.clearSelection();
      },
  
     
      exitSearch(){
          axios.get('/english/findsortcount',{
                  params: {id:sessionStorage.getItem('UserID'),}  
                }).then((res)=>{
                    this.total=res.data
                })
          axios.get('/english/findsortword',{
                params: {
                  id: sessionStorage.getItem('UserID'),
                  currentPage:this.currentPage
                }    
                }).then((res)=>{
                  console.log(res.data)
                  this.tableData=res.data
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
                  id: sessionStorage.getItem('UserID'),
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
     
  },
   mounted:function(){
      

 axios.get('/english/getcollectcount',{
                params: {
                  userid: sessionStorage.getItem('UserID'),
                }    
                }).then((res)=>{
                  this.total=Number(res.data);
                console.log(res.data)
                })

      
      
       axios.get('/english/findcollectword',{
                params: {
                  userid: sessionStorage.getItem('UserID'),
                  currentPage:this.currentPage
                }    
                }).then((res)=>{
                  console.log(res.data)
                  this.collecttableData=res.data
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
    margin-top: 70px;
}
input {
    outline: none;
    height: 66px;
    width: 240px;
    border: none;
    font-size: 20px;
    border-radius:0px;
    border-radius: 0px;
    padding-left: 15px;
    vertical-align: middle;
}
#sh {
    height: 66px;
    width: 100px;
    border: none;
    font-size: 20px;
    border-radius:0px;
    border-radius: 0px;
    vertical-align:middle;
    margin-right: 20px;
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
#collect {
  overflow: auto;
  height: 500px;
}
</style>