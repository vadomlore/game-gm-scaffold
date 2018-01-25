<template>
  <div class="emailInfo">
    <el-form ref="form" :model="emailInfo" label-width="80px">
      <el-form-item label="发件人">
        <span>{{emailInfo.content.addresser}}</span>
      </el-form-item>
      <el-form-item label="发送时间">
        <span>{{emailInfo.createAt | parseTime('{y}-{m}-{d} {h}:{i}')}}</span>
      </el-form-item>
      <el-form-item label="收件人">
        <div class="receiverList">
          <el-tag size="mini" v-for="receivers in emailInfo.content.receiver">{{receivers}}</el-tag>
        </div>
      </el-form-item>
      <el-form-item label="">
        <el-input type="textarea" :autosize="{ minRows: 5}" :disabled="true" v-model="emailInfo.content.email_cont"></el-input>
      </el-form-item>
      <md-input icon="search" name="title" placeholder="输入标题" :disabled="true">附件
        <el-badge :value="emailInfo.content.resource.length" class="item"></el-badge>
      </md-input>
      <el-form-item>
        <div class="resourceList">
          <el-tag
            v-for="item in  emailInfo.content.resource"
            size="medium"
            :key="item.name"
            :type="item.type">
            {{item.name}} x{{item.nums}}
          </el-tag>
        </div>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
  import MdInput from '@/components/MDinput'
  export default {
    props: ['emailInfo'],
    components: {
      MdInput
    },
    data() {
      return {
        form: {
          name: '',
          region: '',
          date1: '',
          date2: '',
          delivery: false,
          type: [],
          resource: '',
          desc: ''
        }
      }
    },
    methods: {
      onSubmit() {
        console.log('submit!')
      }
    }
  }
</script>
<style>
  .el-badge__content {
    background-color: #7cb7f3 !important;
    border-radius: 10px;
    color: #fff;
    padding: 0 8px !important;
  }
  .el-textarea.is-disabled .el-textarea__inner {
    background-color: #f5f7fa;
    border-color: #dfe4ed;
    color: #409eff;
    cursor: not-allowed;
  }
  .resourceList .el-tag--medium {
    margin-right: 20px;
  }
</style>
