<template>
  <div class="upload-info">
    <div>
      <el-upload
          class="upload-pic"
          :action="domain"
          :data="QiniuData"
          :on-remove="handleRemove"
          :on-error="uploadError"
          :on-success="uploadSuccess"
          :before-remove="beforeRemove"
          :before-upload="beforeAvatarUpload"
          :limit="3"
          multiple
          :on-exceed="handleExceed"
          :file-list="fileList"
        >
        <el-button size="small" type="primary">选择图片</el-button>
      </el-upload>
      <div>
        <img class="pic-box" :src="uploadPicUrl" v-if="uploadPicUrl">
      </div>
    </div>
    <div>
      <el-button type="primary" :loading="loading" @click="handleSubmit">提交</el-button>
      <el-button type="info" plain >取消</el-button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      loading: false,
      QiniuData: {
        key: "", //图片名字处理
        token: "" //七牛云token
      },
      domain: "https://upload-z2.qiniup.com", // 七牛云的上传地址（华南区）
      qiniuaddr: "http://xxxx.com", // 七牛云的图片外链地址
      uploadPicUrl: "", //提交到后台图片地址
      fileList: []
    };
  },
  mounted() {
    this.getQiniuToken();
  },
  methods: {
    handleRemove(file, fileList) {
      this.uploadPicUrl = "";
    },
    handleExceed(files, fileList) {
      this.$message.warning(
        `当前限制选择 3 张图片，如需更换，请删除上一张图片在重新选择！`
      );
    },
    beforeAvatarUpload(file) {
      const isPNG = file.type === "image/png";
      const isJPEG = file.type === "image/jpeg";
      const isJPG = file.type === "image/jpg";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isPNG && !isJPEG && !isJPG) {
        this.$message.error("上传头像图片只能是 jpg、png、jpeg 格式!");
        return false;
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
        return false;
      }
      this.QiniuData.key = `upload_pic_${file.name}`;
    },
    uploadSuccess(response, file, fileList) {
      console.log(fileList);
      this.uploadPicUrl = `${this.qiniuaddr}/${response.key}`;
    },
    uploadError(err, file, fileList) {
      this.$message({
        message: "上传出错，请重试！",
        type: "error",
        center: true
      });
    },
    beforeRemove(file, fileList) {
      // return this.$confirm(`确定移除 ${ file.name }？`);
    },
    //提交数据到后台
    handleSubmit() {
      let ajaxData = {
        receipt_img: this.uploadPicUrl //图片地址
      };
      this.$http.put("/xxx", ajaxData)
        .then(response => {
          let { code, data } = response.data;
          if (code == "0") {
            this.$message({
              message: "提交成功！",
              type: "success",
              center: true
            });
          }
        })
        .catch(error => {
          this.$message({
            message: error.msg,
            type: "error",
            center: true
          });
        });
    },
    //请求后台拿七牛云token
    getQiniuToken() {
      this.$http.get("/xxx")
        .then(response => {
          let { code, data } = response.data;
          if (code == "0") {
            this.QiniuData.token = data;
          }
        })
        .catch(error => {});
    }
  }
};
</script>
<style scoped>
.upload-info {
    margin-top: 60px;
}
</style>