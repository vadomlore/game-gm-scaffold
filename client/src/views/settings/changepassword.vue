<template>
  <div class="app-container">
    <el-form ref="changePasswordForm" :model="changePasswordForm" :rules="changePasswordRules" label-width="80px">
        <el-form-item label="原密码" prop="oldPassword">
            <el-input v-model="changePasswordForm.oldPassword" type='password'></el-input>        
        </el-form-item>
        <el-form-item label="新密码"  prop="newPassword">
            <el-input v-model="changePasswordForm.newPassword"  type='password'></el-input>
        </el-form-item>
        <el-form-item label="新密码确认" prop="checkNewPassword">
            <el-input v-model="changePasswordForm.checkNewPassword"  type='password'></el-input>        
        </el-form-item>
        <el-form-item>
            <el-button type="primary" :loading="loading" @click.native.prevent="onChangePassword('changePasswordForm')">确认</el-button>
        </el-form-item>
    </el-form>   
    <h2 v-if="showNotice" class="success">
    <span>修改密码成功</span></h2>
  </div>
</template>
<script>
export default{
  name: 'changePassword',
  data() {
    const confirmPassword = (rule, value, callback) => {
      if (value.length < 6) {
        callback(new Error('密码不能小于6位'))
      }
      if (value !== this.changePasswordForm.newPassword) {
        callback(new Error('两次输入密码不一致'))
      } else {
        callback()
      }
    }
    const validatePassword = (rule, value, callback) => {
      if (value.length < 6) {
        callback(new Error('密码不能小于6位'))
      } else {
        callback()
      }
    }
    return {
      changePasswordForm: {
        oldPassword: '',
        newPassword: '',
        checkNewPassword: ''
      },
      changePasswordRules: {
        oldPassword: [
            { required: true, trigger: 'blur', message: '请输入密码' },
            { trigger: 'blur', validator: validatePassword },
            { trigger: 'change', validator: validatePassword }
        ],
        newPassword: [
            { required: true, trigger: 'blur', message: '请输入密码' },
            { trigger: 'blur', validator: validatePassword },
            { trigger: 'change', validator: validatePassword }
        ],
        checkNewPassword: [
            { required: true, trigger: 'blur', message: '请再次输入密码' },
            { trigger: 'blur', validator: confirmPassword }
        ]
      },
      loading: false,
      showNotice: false
    }
  },
  methods: {
    onChangePassword(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.loading = true
          this.$store.dispatch('ChangePassword', this.changePasswordForm).then(() => {
            this.loading = false
            this.showNotice = true
          }).catch(() => {
            this.loading = false
            this.showNotice = false
          })
          console.log('change password submit success.')
        } else {
          console.log('change password error submit!!')
          return false
        }
      })
    }
  }
}
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
.success {
  margin: 40px 20px 0;
  span {
    display: block;
    font-size: 40px;
    margin-top: 10px;
    color:#67C23A;
    font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;
  }
}
</style>
