<template>
  <div class="app-container calendar-list-container">
    <div class="filter-container">
      <el-select v-model="listQuery.channelId" placeholder="机型" class="filter-item">
        <el-option label="邮箱" value="4"></el-option>
        <el-option label="小米二" value="1002"></el-option>
      </el-select>
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="uid" v-model="listQuery.uid">
      </el-input>
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="pid" v-model="listQuery.pid">
      </el-input>
      <el-button class="filter-item" type="primary" v-waves icon="search" @click="handleFilter">搜索</el-button>
    </div>

    <el-table :key='tableKey' :data="list" v-loading="listLoading" element-loading-text="给我一点时间" border fit highlight-current-row style="width: 100%">
      <el-table-column width="180px" align="center" label="物品Id">
        <template scope="scope">
          <span>{{scope.row.itemId}}</span>
        </template>
      </el-table-column>

      <el-table-column min-width="300px" label="物品数量">
        <template scope="scope">
        <span>{{scope.row.count}}</span>
        </template>
      </el-table-column>

      <el-table-column width="110px" align="center" label="类型">
        <template scope="scope">
          <span>{{scope.row.type==1?"资源":"物品"}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="编辑" width="300px">
        <template scope="scope">
          <el-button size="small" type="success" @click="handleModifyPlayerItem(scope.$index, scope.row, selectedPid)">修改玩家道具数量
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
      <el-form class="small-space" :model="temp" label-position="left" label-width="120px" style='width: 400px; margin-left:50px;'>
        <el-form-item label="玩家">
          <span class="demonstration">{{this.selectedPid}}</span>
        </el-form-item>
        <el-form-item label="物品Id">
            <span class="demonstration">{{temp.itemId}}</span>
        </el-form-item>
          <el-form-item label="物品类型">
            <span class="demonstration">{{this.itemTypeName(temp.type)}}</span>
        </el-form-item>
        <el-form-item label="物品数量">
            <el-input v-model="temp.count"> </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="modifyItem" type="primary">确定修改</el-button>
        <el-button @click="dialogFormVisible = false">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPlayerResource, modifyPlayerItem, getPlayerPidByUid } from '@/api/player'
import waves from '@/directive/waves/index.js' // 水波纹指令
import { parseTime } from '@/utils'

export default {
  name: 'table_demo',
  directives: {
    waves
  },
  data() {
    return {
      list: null,
      total: null,
      listLoading: false,
      selectedPid: undefined,
      listQuery: {
        page: 1,
        limit: 20,
        channelId: undefined,
        pid: undefined,
        uid: undefined
      },
      temp: {
        pid: undefined,
        itemId: undefined,
        type: undefined,
        count: undefined
      },
      resp: {
        id: undefined,
        operation: '',
        status: 'success'
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        modifyItem: '增加玩家道具数量'
      },
      dialogPvVisible: false,
      pvData: [],
      showAuditor: false,
      tableKey: 0
    }
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
      if (this.listQuery === undefined || this.listQuery.pid == null) return
      this.listLoading = true
      console.log(this.listQuery)
      if (this.listQuery.uid !== undefined) {
        getPlayerPidByUid({ uid: this.listQuery.uid, channelId: this.listQuery.channelId }).then(response => {
          this.listQuery.pid = response.data.pid
          this.fetchPlayerData()
        })
      } else {
        this.fetchPlayerData()
      }
    },
    fetchPlayerData() {
      listPlayerResource(this.listQuery).then(response => {
        console.log(JSON.stringify(this.listQuery))
        this.listLoading = false
        if (!this.noticeGMError(response)) {
          this.selectedPid = response.data.pid
          this.list = response.data.items.filter((item, index) => index < this.listQuery.limit * this.listQuery.page && index >= this.listQuery.limit * (this.listQuery.page - 1))
          this.total = this.list.length
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
    handleModifyPlayerItem(index, row, pid) {
      this.dialogFormVisible = true
      this.temp.itemId = row.itemId
      this.temp.type = row.type
      this.temp.count = 0
    },
    modifyItem() {
      this.listLoading = true
      this.dialogFormVisible = false

      modifyPlayerItem(this.selectedPid, this.temp.itemId, this.temp.type, this.temp.count).then(response => {
        this.listLoading = false
        if (!this.noticeGMError(response)) {
          this.temp = Object.assign(this.temp, response.data)
          for (const v of this.list) {
            if (v.itemId === this.temp.itemId) {
              const index = this.list.indexOf(v)
              this.list.splice(index, 1, this.temp)
              break
            }
          }
          this.$notify({
            title: '成功',
            message: '更新成功',
            type: 'success',
            duration: 2000
          })
        }
      })
    },
    resetTemp() {
      this.temp = {
      }
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
    },
    itemTypeName(type) {
      if (type === 1) {
        return '资源'
      }
      if (type === 2) {
        return '物品'
      }
      return ''
    }
  }
}
</script>
