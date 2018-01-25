<template>
  <el-container>
    <el-header>
    </el-header>
    <el-main>
      <el-table :key='tableKey' :data="activityRecord" v-loading="listLoading" element-loading-text="给我一点时间" border fit highlight-current-row style="width: 100%">
        <el-table-column align="center" label="序号" width="65">
          <template scope="scope">
            <span>{{scope.$index}}</span>
          </template>
        </el-table-column>

        <el-table-column width="180px" align="center" label="操作时间">
          <template scope="scope">
            <span>{{scope.row.createAt | parseTime('{y}-{m}-{d} {h}:{i}')}}</span>
          </template>
        </el-table-column>

        <el-table-column min-width="80px" label="操作记录">
          <template scope="scope">
            <span>{{scope.row.content.operate}}</span>
          </template>
        </el-table-column>

        <el-table-column min-width="300px" label="操作内容详细">
          <template scope="scope">
            <span class="link-type" @click="getActivityInfo(scope.row)">{{scope.row.content}}</span>
          </template>
        </el-table-column>

        <el-table-column width="110px" align="center" label="操作用户">
          <template scope="scope">
            <span>{{scope.row.user}}</span>
          </template>
        </el-table-column>

        <el-table-column width="110px" align="center" label="所在服务器">
          <template scope="scope">
            <span>{{scope.row.serverName}}</span>
          </template>
        </el-table-column>
      </el-table>

      <div v-show="!listLoading" class="pagination-container">
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="listQuery.page"
                       :page-sizes="[10,20,30, 50]" :page-size="listQuery.limit" layout="total, sizes, prev, pager, next, jumper" :total="total">
        </el-pagination>
      </div>
      <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible">
        <activity-info :activityInfo="aInfo"></activity-info>
      </el-dialog>
    </el-main>
    <el-footer>Footer</el-footer>
  </el-container>
</template>
<script>
  import activityInfo from './activityInfo.vue'
  import { fetchActivityRecords } from '@/api/activity'
  export default {
    components: {
      activityInfo
    },
    data() {
      return {
        activityRecord: null,
        total: null,
        listLoading: false,
        dialogFormVisible: false,
        dialogTitle: '',
        listQuery: {
          page: 1,
          limit: 10,
          optype: 'activityLog'
        },
        aInfo: {},
        multipleSelection: [],
        tableKey: 0
      }
    },
    created() {
      this.getActivityList()
    },
    methods: {
      getActivityList() {
        this.listLoading = true
        fetchActivityRecords(this.listQuery).then(response => {
          const source = response.data.data
          for (const i in source) {
            source[i].content = JSON.parse(source[i].content)
          }
          this.activityRecord = source
          this.total = response.data.status
          this.listLoading = false
          console.log(source)
        })
      },
      getActivityInfo(row) {
        this.aInfo = Object.assign({}, row)
        console.log(this.aInfo)
        this.dialogTitle = this.aInfo.content.activityType
        this.dialogFormVisible = true
      },
      handleSizeChange(val) {
        this.listQuery.limit = val
        this.getActivityList()
      },
      handleCurrentChange(val) {
        this.listQuery.page = val
        this.getActivityList()
      },
      tableRowClassName({ row, rowIndex }) {
        if (rowIndex === 1) {
          return 'warning-row'
        } else if (rowIndex === 3) {
          return 'success-row'
        }
        return ''
      }
    }
  }
</script>

<style>
  .el-main {
    background-color: #E9EEF3;
    color: #333;
  }
  .el-header, .el-footer {
    background-color: #B3C0D1;
    color: #333;
    text-align: left !important;
    line-height: 60px;
  }
  .el-radio-button:first-child .el-radio-button__inner {
    border-left: 0;
  }
  .el-table .warning-row {
    background: oldlace;
  }

  .el-table .success-row {
    background: #f0f9eb;
  }
  .el-badge__content.is-fixed {
    position: absolute;
    top: 8px;
    right: 10px;
  }

</style>
