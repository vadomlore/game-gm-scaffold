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
    <el-table :key='tableKey' :data="list" v-loading="listLoading" element-loading-text="给我一点时间" border fit highlight-current-row style="width: 100% "
    @selection-change="handleSelectionChange"
    ref="multipleTable"
    >
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
        <el-table-column min-width="80px" label="经验">
            <template scope="scope">
            <span>{{scope.row.exp}}</span>
            </template>
        </el-table-column>
         <el-table-column label="选择"
            type="selection"
            width="55">
        </el-table-column>
    </el-table>
    <div v-show="!listLoading" class="pagination-container">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="listQuery.page"
        :page-sizes="[10,20,30, 50]" :page-size="listQuery.limit" layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>
    <div class="app-container" >
      <el-form :model="inputItem" label-width="80px"  size="mini">
          <el-form-item label="添加道具">
          </el-form-item>
          <el-form-item label="物品Id">
              <el-input v-model="inputItem.id" style="width: 130px;"></el-input>
          </el-form-item>
          <el-form-item label="物品数量">
              <el-input-number v-model="inputItem.count" :min="1" :max="100"></el-input-number>
          </el-form-item>
          <el-form-item>
              <el-button type="primary" @click="onAddItem">立即创建</el-button>
              <el-button>取消</el-button>
          </el-form-item>
      </el-form>
    </div>
    <div class="app-container">
        <span>已添加的物品</span>
        <br />
        <el-tag
            :key="tag.index"
            v-for="tag in dynamicItemTags"
            closable
            :disable-transitions="false"
            @close="handleClose(tag)">
            id:{{tag.id}}-count:{{tag.count}}
        </el-tag>
    </div>
     <div class="pagination-container">
        <el-button @click="handleAddResource()">确定赠送</el-button>
    </div>
  </div>
</template>
<script>
import { fetchPlayer, addPlayerResource, getPlayerPidByUid } from '@/api/player'
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
        channelId: undefined,
        pid: undefined,
        uid: undefined,
        playerAccount: undefined,
        playerName: undefined,
        type: undefined,
        diamond: undefined,
        gold: undefined,
        strength: undefined
      },
      resp: {
        id: undefined,
        operation: '',
        status: 'success'
      },
      dialogPvVisible: false,
      pvData: [],
      showAuditor: false,
      tableKey: 0,
      inputItem: {
        id: undefined,
        count: undefined
      },
      inputItems: [],
      dynamicItemTags: [],
      multipleSelectedRows: []
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
    onAddItem() {
      this.inputItems.push({
        'id': this.inputItem.id,
        'count': this.inputItem.count
      })
      const index = this.dynamicItemTags.length
      this.dynamicItemTags.push({
        'index': index,
        'id': this.inputItem.id,
        'count': this.inputItem.count
      })
    },
    handleClose(tag) {
      this.dynamicItemTags.splice(tag.index, 1)
      this.inputItems.splice(tag.index, 1)
      console.log(JSON.stringify(this.inputItems))
    },
    toggleSelection() {
      this.$refs.multipleTable.clearSelection()
    },
    handleSelectionChange(val) {
      this.multipleSelectedRows = val
    },
    handleAddResource() {
      if (this.multipleSelectedRows.length === 0) {
        return
      }
      console.log()
      addPlayerResource(this.multipleSelectedRows.map(x => x.pid), this.inputItems).then(response => {
        if (!this.noticeGMError(response)) {
          this.$notify({
            title: 'success',
            message: '成功添加物品',
            type: 'success',
            duration: 2000
          })
        }
      })
    }
  }
}
</script>
<style>
  .el-tag + .el-tag {
    margin-left: 10px;
  }
  .button-new-tag {
    margin-left: 10px;
    height: 32px;
    line-height: 30px;
    padding-top: 0;
    padding-bottom: 0;
  }
  .input-new-tag {
    width: 90px;
    margin-left: 10px;
    vertical-align: bottom;
  }
</style>
