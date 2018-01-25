<template>
  <div class="app-container calendar-list-container">
    <div class="filter-container">
      <el-button class="filter-item" type="primary" v-waves icon="search" @click="handleFilter">刷新活动</el-button>
    </div>

    <el-table :key='tableKey' :data="list" v-loading="listLoading" element-loading-text="给我一点时间" border fit highlight-current-row style="width: 100%">
      <el-table-column align="center" label="序号" width="65">
        <template scope="scope">
          <span>{{scope.$index}}</span>
        </template>
      </el-table-column>

      <el-table-column width="180px" align="center" label="活动Id">
        <template scope="scope">
          <span>{{scope.row.activityId}}</span>
        </template>
      </el-table-column>

      <el-table-column min-width="100px" label="活动类型">
        <template scope="scope">
        <span>{{scope.row.activityType}}</span>
        </template>
      </el-table-column>

      <el-table-column width="110px" align="center" label="活动开启时间">
        <template scope="scope">
          <span>{{new Date(scope.row.activityOpenTime)}}</span>
        </template>
      </el-table-column>
      <el-table-column width="110px" align="center" label="活动结束时间">
        <template scope="scope">
          <span>{{new Date(scope.row.activityEndTime)}}</span>
        </template>
      </el-table-column>
       <el-table-column width="300px" align="center" label="活动描述">
        <template scope="scope">
          <span>{{scope.row.description}}</span>
        </template>
      </el-table-column>
      <el-table-column width="110px" align="center" label="status">
        <template scope="scope">
          <span>{{scope.row.status}}</span>
        </template>
      </el-table-column>      
      <el-table-column align="center" label="操作" width="300px">
        <template scope="scope">
          <el-button size="small" type="success" @click="showDeleteActivityDialog(scope.$index, scope.row, 'delete')">删除活动
          </el-button>
        </template>
      </el-table-column>    
    </el-table>
    <div v-show="!listLoading" class="pagination-container">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="listQuery.page"
        :page-sizes="[10,20,30, 50]" :page-size="listQuery.limit" layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form class="small-space" :model="temp" label-position="left" label-width="70px" style='width: 400px; margin-left:50px;'>
        <el-form-item label="活动">
          <span class="demonstration">{{temp.activityId}}</span>
        </el-form-item>
        <el-form-item label="操作">
          <span class="demonstration">"删除"</span>
        </el-form-item>
        <el-form-item label="状态">
          <span class="demonstration">{{temp.status}}</span>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="doDeleteActivity">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { recordOperationLog } from '@/api/email'
import { loadActivities, deleteActivity } from '@/api/activity'
import { getServerName, getUserName } from '@/utils/auth' // 验权
import waves from '@/directive/waves/index.js' // 水波纹指令
import { parseTime } from '@/utils'

export default {
  name: 'table_demo',
  directives: {
    waves
  },
  // 活动activity数据格式
  // {
    //   "activityId": xxx,
    //   "description":desc,
    //   "type": type,
    //   "startTime": xxx,
    //   "endTime":xxx,
    //   "isEnabled": xxx,
    //   }
  data() {
    return {
      list: null,
      total: null,
      listLoading: false,
      listQuery: {
        page: 1,
        limit: 20,
        sort: '+id'

      },
      temp: {
      },
      sortOptions: [{ label: '按ID升序列', key: '+id' }, { label: '按ID降序', key: '-id' }],
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        delete: '删除活动'
      },
      dialogPvVisible: false,
      pvData: [],
      showAuditor: false,
      tableKey: 0
    }
  },
  created: function() {
    this.getList()
  },
  filters: {
    statusFilter(status) {
      const statusMap = {
        online: 'success',
        offline: 'gray'
      }
      return statusMap[status]
    }
  },
  methods: {
    getList() {
      this.listLoading = true
      console.log(this.listQuery)
      loadActivities(this.listQuery).then(response => {
        let hasError = false
        this.listLoading = false
        if (response.data.members !== undefined && response.data.members.err !== undefined) {
          this.$notify({
            title: '失败',
            message: response.data.members.err,
            type: 'warning',
            duration: 2000
          })
          hasError = true
          return
        }
        console.log(response.data)
        if (!hasError) {
          this.list = response.data.filter((item, index) => index < this.listQuery.limit * this.listQuery.page && index >= this.listQuery.limit * (this.listQuery.page - 1))
          this.total = response.data.length
        } else {
          this.list = []
        }
      })
    },
    handleFilter() {
      console.log('handleFilter')
      this.listQuery.page = 1
      this.getList()
    },
    handleSizeChange(val) {
      this.listQuery.limit = val
      this.getList()
    },
    handleCurrentChange(val) {
      this.listQuery.page = val
      this.getList()
    },
    showDeleteActivityDialog(index, row, operation) {
      console.log('index:%s, row:%s operation:%s', index, row.activityId, operation)
      this.dialogStatus = 'delete'
      this.temp = {}
      Object.assign(this.temp, row)
      this.dialogFormVisible = true
    },
    doDeleteActivity() {
      deleteActivity(this.temp).then(response => {
        console.log('deleteActivity response data:' + JSON.stringify(response.data))
        this.listLoading = false
        if (!this.noticeGMError(response)) {
          this.recordLog({
            activityId: this.temp.activityId,
            operate: 'delete',
            activityType: this.temp.activityType,
            activityOpenTime: new Date(this.temp.activityOpenTime),
            activityEndTime: new Date(this.temp.activityEndTime),
            description: this.temp.description
          })
        }
        this.getList()
        this.listLoading = false
        this.dialogFormVisible = false
      })
    },
    recordLog(activityObj) {
      var buildObj = {}
      buildObj.operation = 'activityLog'
      buildObj.content = JSON.stringify(activityObj)
      buildObj.createAt = new Date().getTime()
      buildObj.user = getUserName()
      buildObj.serverName = getServerName()
      recordOperationLog(buildObj)
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => {
        if (j === 'timestamp') {
          return parseTime(v[j])
        } else {
          return v[j]
        }
      }))
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
    }
  }
}
</script>
