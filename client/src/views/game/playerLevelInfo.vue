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
      <el-select v-model="listQuery.selectedRaceType" placeholder="请选择" style="width: 200px;" class="filter-item">
        <el-option
          v-for="item in raceTypeOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
      <el-button class="filter-item" type="primary" v-waves icon="search" @click="handleFilter">搜索</el-button>
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="通关到" v-model="listQuery.unlockIdTo" />
      <el-button class="filter-item" type="primary" v-waves icon="search" @click="handleClarkToLevel">确定</el-button>
    </div>

    <el-table :key='tableKey' :data="sortedList" v-loading="listLoading" element-loading-text="给我一点时间" border fit highlight-current-row style="width: 100%">
      <el-table-column min-width="110px" label="index">
        <template scope="scope">
        <span>{{scope.$index}}</span>
        </template>
      </el-table-column>
      <el-table-column width="180px" align="center" label="checkpointId">
        <template scope="scope">
          <span>{{scope.row.checkpointId}}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="110px" label="种族">
        <template scope="scope">
          <span class="demonstration">{{showRace(responseData.showRaceType)}}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="110px" label="checkpointType">
        <template scope="scope">
        <span>{{scope.row.checkpointType}}</span>
        </template>
      </el-table-column>
      <el-table-column width="110px" align="center" label="chapter">
        <template scope="scope">
           <span>{{scope.row.chapter}}</span>
        </template>
      </el-table-column>
      <el-table-column width="110px" align="center" label="section">
        <template scope="scope">
           <span>{{scope.row.section}}</span>
        </template>
      </el-table-column>
      <el-table-column width="110px" align="center" label="preNormalId">
        <template scope="scope">
           <span>{{scope.row.preNormalId}}</span>
        </template>
      </el-table-column>
      <el-table-column width="110px" align="center" label="preEliteId">
        <template scope="scope">
           <span>{{scope.row.preEliteId}}</span>
        </template>
      </el-table-column>
      <el-table-column width="110px" align="center" label="nextNormalId">
        <template scope="scope">
           <span>{{scope.row.nextNormalId}}</span>
        </template>
      </el-table-column>
      <el-table-column width="110px" align="center" label="nextEliteId">
        <template scope="scope">
           <span>{{scope.row.nextEliteId}}</span>
        </template>
      </el-table-column>
      <el-table-column width="110px" align="center" label="通关状态">
        <template scope="scope">
           <el-tag :type="findPassLevel(scope.row, responseData.PT, responseData.JY)===undefined?'locked':'clark' | statusFilter">{{ findPassLevel(scope.row, responseData.PT, responseData.JY) === undefined ? "locked" :"clark"}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column width="110px" align="center" label="通关星">
        <template scope="scope">
           <span>{{ findPassLevel(scope.row, responseData.PT, responseData.JY) === undefined ? undefined : findPassLevel(scope.row, responseData.PT, responseData.JY).stars}}</span>
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
          <span class="demonstration">{{this.responseData.pid}}</span>
        </el-form-item>
        <el-form-item label="关卡Id">
            <span class="demonstration">{{temp.checkpointId}}</span>
        </el-form-item>
        <el-form-item label="种族">
          <span class="demonstration">{{showRace(this.temp.showRaceType)}}</span>
        </el-form-item>
      </el-form>
       <div slot="footer" class="dialog-footer">
         <el-button @click="handleClarkLevel" type="primary">确定通关</el-button>
         <el-button @click="dialogFormVisible = false">取消</el-button>
       </div>
    </el-dialog>
  </div>
</template>

<script>
import { showChapterMissionInfo, clarkLevel, clarkLevelTo, getPlayerPidByUid } from '@/api/player'
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
      sortedList: null,
      total: null,
      listLoading: false,
      selectedPid: undefined,
      responseData: {
        showRaceType: undefined,
        PT: [],
        JY: [],
        pid: undefined
      },
      listQuery: {
        page: 1,
        limit: 20,
        channelId: undefined,
        pid: undefined,
        uid: undefined,
        selectedRaceType: undefined,
        unlockIdTo: undefined
      },
      temp: {
        pid: undefined,
        checkpointId: undefined,
        showRaceType: undefined
      },
      statusOptions: ['clark', 'locked'],
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
      tableKey: 0,
      raceTypeOptions: [{
        value: '3',
        label: '虫族'
      },
      {
        value: '4',
        label: '神族'
      },
      {
        value: '2',
        label: '人族'
      }]
    }
  },
  filters: {
    statusFilter(status) {
      const statusMap = {
        clark: 'success',
        locked: 'gray'
      }
      return statusMap[status]
    }
  },
  methods: {
    getList() {
      if (this.listQuery === undefined || this.listQuery.pid === undefined || this.listQuery.selectedRaceType === undefined) return
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
      showChapterMissionInfo(this.listQuery).then(response => {
        console.log(JSON.stringify(this.listQuery))
        this.listLoading = false
        if (!this.noticeGMError(response)) {
          this.selectedPid = response.data.pid
          this.list = Object.values(response.data.levelCfg)
          this.responseData.showRaceType = response.data.race
          this.responseData.pid = response.data.pid
          this.responseData.PT = response.data.PT
          this.responseData.JY = response.data.JY
          const sortedMissions = this.filterWithSortedLevel(this.list, 'PT').concat(this.filterWithSortedLevel(this.list, 'JY'))
          this.total = sortedMissions.length
          this.sortedList = sortedMissions.filter((item, index) => index < this.listQuery.limit * this.listQuery.page && index >= this.listQuery.limit * (this.listQuery.page - 1))
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
    showEditHandleClark(index, row, pid) {
      this.dialogFormVisible = true
      this.temp.checkpointId = row.checkpointId
      this.temp.pid = pid
      this.temp.showRaceType = this.responseData.showRaceType
    },
    handleClarkLevel() {
      this.listLoading = true
      this.dialogFormVisible = false
      clarkLevel(this.temp).then(response => {
        this.listLoading = false
        if (!this.noticeGMError(response)) {
          this.temp = Object.assign(this.temp, response.data)
          this.responseData.PT = response.data.PT
          this.responseData.JY = response.data.JY
          // for (const v of this.sortedList) {
          //   if (v.checkpointId === this.temp.checkpointId) {
          //     const index = this.sortedList.indexOf(v)
          //     this.responseData.PT = response.data.PT
          //     this.responseData.JY = response.data.JY
          //     this.list.splice(index, 1, this.temp)
          //     break
          //   }
          // }
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
    getLevelInfoBy(index, type) {
      if (type === 'JY') {
        return 'JY' + (parseInt(index / 10) + 1) + (Array(4).join('0') + (index % 10 + 1)).slice(-4)
      } else if (type === 'PT') {
        return 'PT' + (parseInt(index / 10) + 1) + (Array(4).join('0') + (index % 10 + 1)).slice(-4)
      }
    },
    filterWithSortedLevel(levels, key) {
      return Object.values(levels).filter(x => x.checkpointId.startsWith(key)).sort((x, y) => { return parseInt(x.checkpointId.substr(2, 5)) - parseInt(y.checkpointId.substr(2, 5)) })
    },
    findPassLevel(level, ptRecords, jyRecords) {
      console.log('ptRecords' + JSON.stringify(jyRecords))
      for (const record in ptRecords) {
        console.log('level.checkPointId:%s  pt.checkPointId:%s', level.checkpointId, this.getLevelInfoBy(record.stage_id, 'PT'))
      }

      if (level.checkpointId.startsWith('PT')) {
        const record = ptRecords.find(x => level.checkpointId === this.getLevelInfoBy(x.stage_id, 'PT'))
        return record
      }
      if (level.checkpointId.startsWith('JY')) {
        const record = jyRecords.find(x => level.checkpointId === this.getLevelInfoBy(x.stage_id, 'JY'))
        return record
      }
      return undefined
    },
    showRace(value) {
      console.log('showRace:' + value)
      if (this.raceTypeOptions.find(x => x.value === value)) {
        return this.raceTypeOptions.find(x => x.value === value).label
      }
      return ''
    },
    handleClarkToLevel() {
      console.log('handleClarkToLevel')
      console.log(JSON.stringify(this.listQuery))
      if (this.listQuery.pid === undefined || this.listQuery.selectedRaceType === undefined || this.listQuery.unlockIdTo === undefined) return
      clarkLevelTo(this.listQuery.pid, this.listQuery.selectedRaceType, this.listQuery.unlockIdTo).then(response => {
        if (!this.noticeGMError(response)) {
          this.listQuery.page = 1
          this.getList()
        }
      })
    }
  }
}
</script>
