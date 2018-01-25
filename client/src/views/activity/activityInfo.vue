<template>
  <div class="activityInfo">
    <el-form ref="form" :model="activityInfo" label-width="80px">
      <el-form-item label="活动Id">
        <span>{{activityInfo.content.activityId}}</span>
      </el-form-item>
      <el-form-item label="活动类型">
        <span>{{activityInfo.content.activityType}}</span>
      </el-form-item>
      <el-form-item label="活动开始时间">
        <span>{{new Date(activityInfo.content.activityOpenTime)}}</span>
      </el-form-item>
      <el-form-item label="活动结束时间">
        <span>{{new Date(activityInfo.content.activityOpenTime)}}</span>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
  import MdInput from '@/components/MDinput'
  export default {
    props: ['activityInfo'],
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
