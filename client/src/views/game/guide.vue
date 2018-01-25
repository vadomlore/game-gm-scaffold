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
      <el-select @change='handleFilter' style="width: 120px" class="filter-item" v-model="listQuery.sort" placeholder="排序">
        <el-option v-for="item in sortOptions" :key="item.key" :label="item.label" :value="item.key">
        </el-option>
      </el-select>
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

      <el-table-column min-width="300px" label="用户账户">
        <template scope="scope">
        <span>{{scope.row.playerAccount}}</span>
        </template>
      </el-table-column>

      <el-table-column width="110px" align="center" label="用户名称">
        <template scope="scope">
          <span>{{scope.row.playerName}}</span>
        </template>
      </el-table-column>

      <el-table-column min-width="300px" label="等级">
        <template scope="scope">
        <span>{{scope.row.level}}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="300px" label="充值数">
        <template scope="scope">
        <span>{{scope.row.payment}}</span>
        </template>
      </el-table-column>
     <el-table-column class-name="status-col" label="状态" width="90">
        <template scope="scope">
          <el-tag :type="scope.row.isOnline | statusFilter">{{scope.row.isOnline?"online":"offline"}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作" width="300px">
        <template scope="scope">
          <el-button size="small" type="success" @click="handleGuideOperation(scope.$index, scope.row, 'skip')">跳过引导
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
      <el-form class="small-space" :model="resp" label-position="left" label-width="70px" style='width: 400px; margin-left:50px;'>
        <el-form-item label="玩家">
          <span class="demonstration">{{resp.id}}</span>
        </el-form-item>
        <el-form-item label="操作">
          <span class="demonstration">{{resp.operation}}</span>
        </el-form-item>
        <el-form-item label="状态">
          <span class="demonstration">{{resp.status}}</span>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { fetchPlayer, skipGuide, getPlayerPidByUid } from '@/api/player'
import waves from '@/directive/waves/index.js' // 水波纹指令
import { parseTime } from '@/utils'

const serverOptions = [
      { key: '192.168.0.151:8002', display_name: 'zyl的游戏服务' },
      { key: '116.62.133.46:5031', display_name: '测试服' }
]

// arr to obj
const serverKeyValue = serverOptions.reduce((acc, cur) => {
  acc[cur.key] = cur.display_name
  return acc
}, {})

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
        channelId: undefined,
        pid: undefined,
        uid: undefined,
        playerAccount: undefined,
        playerName: undefined,
        type: undefined,
        sort: '+id'
      },
      resp: {
        id: undefined,
        operation: '',
        status: 'success'
      },
      serverOptions,
      sortOptions: [{ label: '按ID升序列', key: '+id' }, { label: '按ID降序', key: '-id' }],
      statusOptions: ['published', 'draft', 'deleted'],
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        skip: '跳过引导'
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
    },
    serverFilter(type) {
      return serverKeyValue[type]
    }
  },
  methods: {
    getList() {
      if (this.listQuery === undefined) return
      this.listLoading = true
      console.log(this.listQuery)
      if (this.listQuery.uid !== undefined) {
        getPlayerPidByUid({ uid: this.listQuery.uid, channelId: this.listQuery.channelId }).then(response => {
          this.listQuery.pid = response.data.pid
          this.fetchPlayerData()
        }).catch(x => {
          this.listLoading = false
        })
      } else {
        this.fetchPlayerData()
      }
    },
    fetchPlayerData() {
      fetchPlayer(this.listQuery).then(response => {
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
        if (!hasError) {
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
    handleGuideOperation(index, row, operation) {
      console.log('index:%s, row:%s operation:%s', index, row.pid, operation)
      this.listLoading = true
      switch (operation) {
        case 'skip':
          skipGuide(row.pid).then(response => {
            this.dialogStatus = 'skip'
            console.log('skipGuideResp:' + JSON.stringify(response.data))
            this.resp = response.data.members
            this.listLoading = false
            // this.dialogFormVisible = true
            this.$notify({
              title: '成功',
              message: '玩家' + this.resp.id + '跳过引导成功',
              type: 'success',
              duration: 2000
            })
          })
          break
        default:
          this.listLoading = false
          break
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
    }
  }
}
</script>
