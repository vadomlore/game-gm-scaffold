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
      <el-table-column align="center" label="序号" width="65">
        <template scope="scope">
          <span>{{scope.row.id}}</span>
        </template>
      </el-table-column>

      <el-table-column width="180px" align="center" label="用户Id">
        <template scope="scope">
          <span>{{scope.row.pid}}</span>
        </template>
      </el-table-column>

      <el-table-column min-width="180px" label="用户账户">
        <template scope="scope">
        <span>{{scope.row.playerAccount}}</span>
        </template>
      </el-table-column>

      <el-table-column width="110px" align="center" label="用户名称">
        <template scope="scope">
          <span>{{scope.row.playerName}}</span>
        </template>
      </el-table-column>

      <el-table-column min-width="80px" label="等级">
        <template scope="scope">
        <span>{{scope.row.level}}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="80px" label="经验">
        <template scope="scope">
        <span>{{scope.row.exp}}</span>
        </template>
      </el-table-column>

     <el-table-column class-name="status-col" label="状态" width="90px">
        <template scope="scope">
          <el-tag :type="scope.row.isOnline | statusFilter">{{scope.row.isOnline ? "online":"offline"}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" label="编辑" width="300px">
        <template scope="scope">
          <el-button size="small" type="success" @click="handleAddExp(scope.$index, scope.row)">修改玩家经验
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
          <span class="demonstration">{{temp.id}}</span>
        </el-form-item>
        <el-form-item label="玩家账户信息">
          <span class="demonstration">{{temp.playerAccount}}</span>
        </el-form-item>
        <el-form-item label="增加经验">
            <el-input v-model="temp.exp"> </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addExp" type="primary">确定修改</el-button>
        <el-button @click="dialogFormVisible = false">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { addPlayerExp, fetchPlayer, getPlayerPidByUid } from '@/api/player'
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
      listQuery: {
        page: 1,
        limit: 20,
        pid: undefined,
        uid: undefined,
        channelId: undefined,
        playerAccount: undefined,
        playerName: undefined,
        type: undefined,
        diamond: undefined,
        gold: undefined,
        strength: undefined
      },
      temp: {
        id: undefined,
        pid: undefined,
        diamond: undefined,
        gold: undefined,
        strength: undefined
      },
      resp: {
        id: undefined,
        operation: '',
        status: 'success'
      },
      statusOptions: ['published', 'draft', 'deleted'],
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        addExp: '增加经验'
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
      fetchPlayer(this.listQuery).then(response => {
        this.listLoading = false
        if (!this.noticeGMError(response)) {
          console.log('lower:' + this.listQuery.limit)
          console.log('upper:' + this.listQuery.limit * (this.listQuery.page - 1))
          this.list = response.data.elements.filter((item, index) => index < this.listQuery.limit * this.listQuery.page && index >= this.listQuery.limit * (this.listQuery.page - 1))
          this.total = response.data.elements.length
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
    handleAddExp(index, row) {
      this.temp = Object.assign({}, row)
      this.dialogStatus = 'addCurrency'
      this.dialogFormVisible = true
      console.log('index:%s, pid:%s, temp %s', index, this.temp.pid, this.temp, 'modify player currency.')
    },
    addExp() {
      this.listLoading = true
      this.dialogFormVisible = false
      addPlayerExp(this.temp.pid, this.temp.exp).then(response => {
        this.listLoading = false
        if (!this.noticeGMError(response)) {
          this.temp = Object.assign(this.temp, response.data.members)
          for (const v of this.list) {
            if (v.id === this.temp.id) {
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
        id: undefined,
        pid: undefined,
        playerAccount: undefined,
        playerName: undefined,
        gold: undefined,
        diamond: undefined,
        strength: undefined
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
    }
  }
}
</script>
