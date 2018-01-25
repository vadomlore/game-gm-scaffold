<template>
  <el-container>
    <el-main>
      <el-form :inline="true" :model="guardBattleTimes" class="demo-form-inline">
        <el-form-item>
          <el-select v-model="guardBattleTimes.channelId" placeholder="机型" class="filter-item">
            <el-option label="邮箱" value="4"></el-option>
            <el-option label="小米二" value="1002"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-input v-model="guardBattleTimes.pid" placeholder="pid"></el-input>
        </el-form-item>
        <el-form-item>
          <el-input v-model="guardBattleTimes.uid" placeholder="uid"></el-input>
        </el-form-item>
        <el-form-item>
          <el-input v-model="guardBattleTimes.times" placeholder="挑战次数"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit('guardBattleTimes')">确定</el-button>
        </el-form-item>
      </el-form>
    </el-main>
  </el-container>
</template>
<script>
  import { setGuardBattleTimes, getPlayerPidByUid } from '@/api/player'
  export default {
    data() {
      return {
        guardBattleTimes: {
          channelId: '',
          pid: '',
          uid: '',
          times: ''
        }
      }
    },
    methods: {
      onSubmit() {
        if (this.guardBattleTimes.uid !== undefined) {
          getPlayerPidByUid({ uid: this.guardBattleTimes.uid, channelId: this.guardBattleTimes.channelId }).then(response => {
            this.listQuery.pid = response.data.pid
            this.fetchPlayerData()
          })
        } else {
          this.fetchPlayerData()
        }
      },
      fetchPlayerData() {
        setGuardBattleTimes(this.guardBattleTimes).then(response => {
          if (!this.noticeGMError(response)) {
            this.$notify({
              title: '成功',
              message: response.data,
              type: 'success',
              duration: 2000
            })
          }
          // alert(JSON.stringify(response))
          // console.log(this.guardBattleTimes)
        })
      },
      noticeGMError(response) {
        if (response.data.err !== undefined || (response.data.members !== undefined && response.data.members.err !== undefined)) {
          this.listLoading = false
          this.$notify({
            title: '失败',
            message: response.data.members.err,
            type: 'warning',
            duration: 2000
          })
          return true
        }
        return false
      }
    }
  }
</script>
