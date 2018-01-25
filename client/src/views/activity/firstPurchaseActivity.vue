<template>
  <div class="app-container calendar-list-container">  
    <el-form label-width="100px" :model="ruleForm" :rules="rules" ref="ruleForm">
        <el-form-item label="活动名称" prop="activityType">
            <el-input v-model="ruleForm.activityType"></el-input>
        </el-form-item>
        <el-form-item label="活动开始时间" prop="activityOpenTime">
           <div class="block">
                <el-date-picker
                v-model="ruleForm.activityOpenTime"
                type="datetime"
                placeholder="选择日期时间">
                </el-date-picker>
            </div>
        </el-form-item>
        <el-form-item label="活动结束时间" prop="activityEndTime">
           <div class="block">
                <el-date-picker
                v-model="ruleForm.activityEndTime"
                type="datetime"
                placeholder="选择日期时间">
                </el-date-picker>
            </div>
        </el-form-item>
        <el-form-item label="活动描述" prop="description">
            <el-input type="textarea" v-model="ruleForm.description"></el-input>
        </el-form-item>
        <firstPurchaseActivityViewer @sendSevenDayParams="sendFirstPurchaseParams"> </firstPurchaseActivityViewer>
         <el-form-item>
            <el-button v-loading="loading" type="primary" @click="onSubmit('ruleForm')">立即创建</el-button>
            <el-button>取消</el-button>
        </el-form-item>
    </el-form>
  </div>
</template>
<script>
import firstPurchaseActivityViewer from '@/views/activity/firstPurchaseActivityViewer.vue'

import { recordOperationLog } from '@/api/email'
import { openActivity } from '@/api/activity'
import { getServerName, getUserName } from '@/utils/auth' // 验权
import waves from '@/directive/waves/index.js' // 水波纹指令
import { parseTime } from '@/utils'
export default {
  name: 'firstPurchaseActivity',
  components: {
    firstPurchaseActivityViewer
  },
  directives: {
    waves
  },
  data() {
    return {
      loading: false,
      ruleForm: {
        activityType: '10006',
        activityOpenTime: '',
        activityEndTime: '',
        description: '',
        openParams: JSON.stringify({
          baseNumber: 0,
          awards: {}
        })
      },
      rules: {
        activityType: [
            { required: true, message: '请输入活动名称', trigger: 'blur' }
        ],
        activityOpenTime: [
            { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
        ],
        activityEndTime: [
            { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
        ],
        description: [
            { required: true, message: '请填写活动描述', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => {
        if (j === 'timestamp') {
          return parseTime(v[j])
        } else {
          return v[j]
        }
      }))
    },
    sevenParams(data) {
      console.log('emit data:' + data)
      this.ruleForm.openParams = data
    },
    getOpenParams(data) {
      this.ruleForm.openParams = data
    },
    getResponseErrMsg(response) {
      if (response.data.err !== undefined) {
        return response.data.err
      }
      if (response.data.members !== undefined && response.data.members.err !== undefined) {
        return response.data.members.err
      }
      return ''
    },
    noticeGMError(response) {
      console.log(response.data.hasOwnProperty('err'))
      if (response.data.err !== undefined || (response.data.members !== undefined && response.data.members.err !== undefined)) {
        this.listLoading = false
        this.$notify({
          title: '失败',
          message: this.getResponseErrMsg(response),
          type: 'warning',
          duration: 2000
        })
        return true
      }
      return false
    },
    onSubmit(formName) {
      console.log(JSON.stringify(this.ruleForm))
      this.$refs[formName].validate((valid) => {
        console.log('valid ? %s', valid)
        if (valid) {
          this.loading = true
          const condition = {
            activityType: this.ruleForm.activityType,
            activityOpenTime: new Date(this.ruleForm.activityOpenTime).getTime(),
            activityEndTime: new Date(this.ruleForm.activityEndTime).getTime(),
            description: this.ruleForm.description,
            openParams: this.ruleForm.openParams
          }
          openActivity(condition).then(response => {
            if (!this.noticeGMError(response)) {
              this.$notify({
                title: '成功',
                message: '增加新活动成功',
                type: 'success',
                duration: 2000
              })
              this.loading = false
              recordOperationLog({
                operation: 'activityLog',
                content: JSON.stringify({
                  activityId: response.data.activityId,
                  operate: 'add',
                  activityType: '10006',
                  activityOpenTime: this.ruleForm.activityOpenTime,
                  activityEndTime: this.ruleForm.activityEndTime,
                  description: this.ruleForm.description,
                  openParams: this.ruleForm.openParams
                }),
                createAt: new Date().getTime(),
                user: getUserName(),
                serverName: getServerName()
              })
            }
          }).catch(err => {
            console.log(err)
            this.listLoading = false
            this.loading = false
          })
        }
      })
    }
  }
}
</script>