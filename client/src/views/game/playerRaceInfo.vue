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
      <el-select v-model="listQuery.selectHeroTypeValue" placeholder="请选择" style="width: 200px;" class="filter-item">
            <el-option
            v-for="item in heroTypeOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
            </el-option>
        </el-select>
       <el-button class="filter-item" type="primary" v-waves icon="search" @click="handleFilter">搜索</el-button>
    </div>
    <el-table :key='tableKey' :data="raceInformation" v-loading="listLoading" element-loading-text="给我一点时间" border fit highlight-current-row style="width: 100%">
      <el-table-column align="center" label="序号" width="65px" type="index">
      </el-table-column>

       <el-table-column min-width="100px" label="pid" property="selectedPid">
      </el-table-column>

      <el-table-column width="80px" align="center" label="种族" property="selectedType">
      </el-table-column>

      <el-table-column width="80px" align="center" label="种族等级"  property="raceLevel">
      </el-table-column>
      <el-table-column align="center" label="增加种族等级" width="300px">
        <template slot-scope="scope">
          <el-button size="small" type="success" @click="handleAddRaceExp(scope.$index, scope.row)">修改种族等级等级
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <br />
    <br />
   <div class="label">
      {{queryResultTypeName}}
    </div>
    <br />
    <el-table :key='tableKey' :data="list" v-loading="listLoading" element-loading-text="给我一点时间" border fit highlight-current-row style="width: 100%">
      <el-table-column align="center" label="序号" width="65px"  type="index">
      </el-table-column>

       <el-table-column min-width="100px" label="cid">
        <template scope="scope">
        <span>{{scope.row.cid}}</span>
        </template>
      </el-table-column>

      <el-table-column width="80px" align="center" label="等级" property="level">
      </el-table-column>

      <el-table-column width="80px" align="center" label="经验" property="exp">
      </el-table-column>

      <el-table-column min-width="80px" label="强化等级" property="strengthLv">
      </el-table-column>
      <el-table-column min-width="80px" label="进化等级" property="evolutionGrade">
      </el-table-column>
      <el-table-column min-width="200px" label="进化槽位" property="evolutionPosition">
      </el-table-column>
      <el-table-column min-width="80px" label="军阶等级" property="militaryRank">
      </el-table-column>
      <el-table-column min-width="100px" label="技能等级" property="skillLevel">
      </el-table-column>
      <el-table-column align="center" label="编辑" width="300px">
        <template slot-scope="scope">
          <el-button size="small" type="success" @click="handleEditArmy(scope.$index, scope.row)">修改信息
          </el-button>
        </template>
      </el-table-column>
      <el-table-column align="center" label="删除" width="300px">
        <template scope="scope">
          <el-button size="small" type="success" @click="handleRemoveArmy(scope.$index, scope.row)">删除信息
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
        <el-form-item label="id">
          <span class="demonstration">{{temp.id}}</span>
        </el-form-item>
        <el-form-item label="cid">
          <span class="demonstration">{{temp.cid}}</span>
        </el-form-item>
        <el-form-item label="等级">
           <el-input v-model="temp.level"> </el-input>
        </el-form-item>
        <el-form-item label="经验" v-if="temp.exp">
            <el-input v-model="temp.exp"> </el-input>
        </el-form-item>
        <el-form-item label="强化等级" v-if="temp.strengthLv !== undefined">
            <el-input v-model="temp.strengthLv"> </el-input>
        </el-form-item>
        <el-form-item label="技能等级">
            <el-input v-model="temp.skillLevel"> </el-input>
        </el-form-item>
        <el-form-item label="进化等级" v-if="temp.evolutionGrade !== undefined">
            <el-input v-model="temp.evolutionGrade"> </el-input>
        </el-form-item>
        <el-form-item label="进化槽位" v-if="temp.evolutionPosition !== undefined">
            <el-input v-model="temp.evolutionPosition"> </el-input>
        </el-form-item>
        <el-form-item label="军阶等级" v-if="temp.militaryRank !== undefined">
            <el-input v-model="temp.militaryRank"> </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="modifyHero" type="primary">确定修改</el-button>
        <el-button @click="dialogFormVisible = false">取消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="删除操作" :visible.sync="deleteConfirmVisible">
      <el-form class="small-space" :model="deleteTemp" label-position="left" label-width="120px" style='width: 400px; margin-left:50px;'>
        <el-form-item label="cid">
          <span class="demonstration">{{deleteTemp.cid}}</span>
        </el-form-item>
        <el-form-item label="race">
          <span class="demonstration">{{deleteTemp.raceInfo}}</span>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="deleteHero" type="primary">确定删除</el-button>
        <el-button @click="deleteConfirmVisible = false">取消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="修改种族等级经验操作" :visible.sync="addExpConfirmVisible">
      <el-form class="small-space" :model="raceInfomationTemp" label-position="left" label-width="120px" style='width: 400px; margin-left:50px;'>
        <el-form-item label="pid">
          <span class="demonstration">{{raceInfomationTemp.selectedPid}}</span>
        </el-form-item>
        <el-form-item label="race">
          <span class="demonstration">{{raceInfomationTemp.selectedType}}</span>
        </el-form-item>
        <el-form-item label="level">
            <el-input v-model="raceInfomationTemp.raceLevel"> </el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addRaceExp" type="primary">确定</el-button>
        <el-button @click="addExpConfirmVisible = false">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { modifyArmy, deleteArmy, fetchArmy, addRaceLevel, getPlayerPidByUid } from '@/api/player'
import waves from '@/directive/waves/index.js' // 水波纹指令
import { parseTime } from '@/utils'

export default {
  name: 'table_demo',
  directives: {
    waves
  },
  data() {
    return {
      list: undefined,
      total: undefined,
      listLoading: false,
      selectedType: undefined,
      raceLevel: undefined,
      selectedPid: undefined,
      listQuery: {
        page: 1,
        limit: 20,
        channelId: undefined,
        pid: undefined,
        uid: undefined,
        selectHeroTypeValue: undefined
      },
      heroTypeOptions: [{
        value: 'HeroZerg',
        label: '虫族英雄'
      }, {
        value: 'HeroProtoss',
        label: '神族英雄'
      }, {
        value: 'HeroTerran',
        label: '人族英雄'
      }, {
        value: 'SpawnZerg',
        label: '虫族单兵'
      }, {
        value: 'SpawnProtoss',
        label: '神族单兵'
      }, {
        value: 'SpwanTerran',
        label: '人族单兵'
      }],
      selectedTargetRace: null,
      temp: {
        id: undefined,
        cid: undefined,
        level: undefined,
        exp: undefined,
        strengthLv: undefined,
        skillLevel: undefined,
        evolutionPosition: undefined,
        militaryRank: undefined,
        evolutionGrade: undefined
      },
      deleteTemp: {
        cid: undefined,
        raceInfo: undefined
      },
      raceInfomationTemp: {
        selectedType: undefined,
        raceLevel: undefined,
        selectedPid: undefined
      },
      resp: {
        id: undefined,
        operation: '',
        status: 'success'
      },
      statusOptions: ['published', 'draft', 'deleted'],
      dialogFormVisible: false,
      deleteConfirmVisible: false,
      addExpConfirmVisible: false,
      dialogStatus: '',
      textMap: {
        modifyCurrency: '修改玩家货币数量'
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
  computed: {
    queryResultTypeName: function() {
      if (this.heroTypeOptions.find((item) => item.value === this.selectedType) === undefined) {
        return ''
      }
      return this.heroTypeOptions.find((item) => item.value === this.selectedType).label
    },
    queryResultType: function() {
      if (this.heroTypeOptions.find((item) => item.value === this.selectedType) === undefined) {
        return ''
      }
      return this.heroTypeOptions.find((item) => item.value === this.selectedType).value
    },
    raceInformation: function() {
      if (this.raceLevel === undefined) {
        return []
      }
      return [{
        selectedType: this.selectedType,
        raceLevel: this.raceLevel,
        selectedPid: this.selectedPid
      }]
    }
  },
  methods: {
    getList() {
      if (this.listQuery === undefined || this.listQuery.pid === undefined || this.listQuery.selectHeroTypeValue === undefined) return
      this.listLoading = true
      console.log('queryList:' + JSON.stringify(this.listQuery))
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
      fetchArmy(this.listQuery).then(response => {
        this.listLoading = false
        if (!this.noticeGMError(response)) {
          console.log('fetchArmy response list:' + JSON.stringify(response.data.members.details))

          this.list = response.data.members.details.filter((item, index) => index < this.listQuery.limit * this.listQuery.page && index >= this.listQuery.limit * (this.listQuery.page - 1))
          console.log('fetchArmy response list:' + JSON.stringify(this.list))
          this.total = this.list.length
          this.selectedType = response.data.members.type
          this.selectedPid = response.data.members.pid
          this.raceLevel = response.data.members.race_level
        } else {
          this.list = []
          this.reaceLevel = undefined
        }
      }).catch(err => {
        this.listLoading = false
        console.log(err)
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
    handleEditArmy(index, item) {
      this.resetTemp()
      Object.assign(this.temp, item)
      console.log('temp=>' + JSON.stringify(this.temp))
      this.dialogFormVisible = true
    },
    handleRemoveArmy(index, item) {
      this.deleteTemp.cid = item.cid
      this.deleteTemp.raceInfo = this.queryResultType
      this.deleteConfirmVisible = true
    },
    handleAddRaceExp(index, item) {
      this.raceInfomationTemp.selectedType = item.selectedType
      this.raceInfomationTemp.raceLevel = item.raceLevel
      this.raceInfomationTemp.selectedPid = item.selectedPid
      this.addExpConfirmVisible = true
    },
    addRaceExp() {
      this.listLoading = true
      addRaceLevel(this.raceInfomationTemp.selectedPid, this.raceInfomationTemp.selectedType,
      this.raceInfomationTemp.raceLevel,
      ).then(response => {
        this.deleteConfirmVisible = false
        this.deleteConfirmVisible = false
        this.listLoading = false
        console.log('addRaceLevelInfo response:' + JSON.stringify(response.data))
        if (!this.noticeGMError(response)) {
          this.raceLevel = response.data.race_level
          this.$notify({
            title: '成功',
            message: '修改等级成功',
            type: 'success',
            duration: 2000
          })
        }
      }).catch(err => {
        this.addExpConfirmVisible = false
        this.listLoading = false
        console.log('addRaceExp err' + err)
      })
    },
    deleteHero() {
      this.listLoading = true
      deleteArmy(this.selectedPid, this.deleteTemp.cid, this.deleteTemp.raceInfo).then(response => {
        this.deleteConfirmVisible = false
        this.listLoading = false
        console.log('deleteArmy response:' + JSON.stringify(response.data))
        if (!this.noticeGMError(response)) {
          for (const v of this.list) {
            if (v.cid === response.data.cid) {
              const index = this.list.indexOf(v)
              this.list.splice(index, 1)
              break
            }
          }
          this.$notify({
            title: '成功',
            message: '删除成功',
            type: 'success',
            duration: 2000
          })
        }
      }).catch(err => {
        this.deleteConfirmVisible = false
        this.listLoading = false
        console.log('deleteArmy err' + err)
      })
    },
    modifyHero() {
      this.dialogFormVisible = false
      modifyArmy(this.selectedPid, this.queryResultType, this.temp).then(response => {
        this.resetTemp()
        if (!this.noticeGMError(response)) {
          const responseData = response.data
          console.log(JSON.stringify(responseData))
          for (const v of this.list) {
            if (v.cid === responseData.cid) {
              const index = this.list.indexOf(v)
              Object.assign(v, responseData)
              this.list.splice(index, 1, v)
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
      }).catch(err => {
        this.resetTemp()
        console.log(err)
      })
    },
    resetTemp() {
      this.temp = {
        id: undefined,
        cid: undefined,
        level: undefined,
        exp: undefined,
        strengthLv: undefined,
        skillLevel: undefined,
        evolutionPosition: undefined,
        militaryRank: undefined,
        evolutionGrade: undefined
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
      if (response.data.err !== undefined || response.data.err !== undefined || (response.data.members !== undefined && response.data.members.err !== undefined)) {
        this.listLoading = false
        this.$notify({
          title: '失败',
          message: response.data.err !== undefined ? response.data.err : response.data.members.err !== undefined ? response.data.members.err : 'Unknown Error',
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
