<template>
<div id="app-container">
  <div>
    <el-row :gutter="20" v-for="(g, index0) in list">
      <el-col :span="6" v-for="(item, index1) in g.group">
        <div class="grid-content bg-purple">
            <el-button class="item" :plain="true" @click="navigateToDashboard(item.name)">{{item.name}}</el-button>
            <el-button  class="item" :plain="true">{{item.url}}</el-button>
            <el-button   class="item" :plain="true">{{item.description}}</el-button>      
        </div>
      </el-col>
    </el-row>
  </div>  
</div>
</template>
<script>
export default {
  data() {
    return {
      list: [
        {
          group: [
            {
              name: 'AFSD',
              url: '192.168.5.123',
              description: 'desc'
            },
            {
              name: 'AFSD',
              url: '192.168.5.123',
              description: 'desc'
            },
            {
              name: 'AFSD',
              url: '192.168.5.123',
              description: 'desc'
            },
            {
              name: 'AFSD',
              url: '192.168.5.123',
              description: 'desc'
            },
            {
              name: 'AFSD',
              url: '192.168.5.123',
              description: 'desc'
            }
          ]
        }, {
          group: [{
            name: 'AFSD',
            url: '192.168.5.123',
            description: 'desc'
          }
          ]
        }
      ]
    }
  },
  methods: {
    showPwd() {
      if (this.pwdType === 'password') {
        this.pwdType = ''
      } else {
        this.pwdType = 'password'
      }
    },
    navigateToDashboard(serverName) {
      this.$store.dispatch('setServerName', serverName)
      this.$router.push({ path: '/' })
                // this.showDialog = true
    }
  }
}
</script>
<style scoped>
  .box-card{
    width: 400px;
    margin: 20px auto;
  }
  .block{
    padding: 30px 24px;
  }
  .alert-item{
    margin-bottom: 10px;
  }
  .tag-item{
    margin-right: 15px;
  }
  .link-title{
    margin-left:35px;
  }
    .el-row {
    margin-bottom: 20px;
    &:last-child {
      margin-bottom: 0;
    }
  }
  .el-col {
    border-radius: 4px;
  }
  .bg-purple-dark {
    background: #99a9bf;
  }
  .bg-purple {
    background: #d3dce6;
  }
  .bg-purple-light {
    background: #e5e9f2;
  }
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }
  .item {
    margin: 4px;
  }
</style>
