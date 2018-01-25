<template>
  <el-container>
    <el-header>
      <div class="btns">
        <el-button size="mini">删除</el-button>
        <el-radio-group v-model="listQuery.labelIf" size="mini" @change="getEmailList">
          <el-radio-button label="all">所有邮件</el-radio-button>
          <el-radio-button label="">已删除</el-radio-button>
          <el-radio-button label="">未删除</el-radio-button>
        </el-radio-group>
        <el-button size="mini">刷新</el-button>
      </div>
    </el-header>
    <el-main>
      <el-table :key='tableKey' :data="emailList" v-loading="listLoading" element-loading-text="给我一点时间" border fit highlight-current-row style="width: 100%">
        <el-table-column align="center" label="序号" width="65">
          <template scope="scope">
            <span>{{scope.row.id}}</span>
          </template>
        </el-table-column>

        <el-table-column width="180px" align="center" label="时间">
          <template scope="scope">
            <span>{{scope.row.createAt | parseTime('{y}-{m}-{d} {h}:{i}')}}</span>
          </template>
        </el-table-column>

        <el-table-column min-width="300px" label="标题">
          <template scope="scope">
            <span class="link-type" @click="getEmailInfo(scope.row)">{{scope.row.content.subject}}</span>
          </template>
        </el-table-column>

        <el-table-column width="110px" align="center" label="姓名">
          <template scope="scope">
            <span>{{scope.row.user}}</span>
          </template>
        </el-table-column>

        <el-table-column width="150px" align="center" label="附件">
          <template scope="scope">
            <el-badge :value="scope.row.content.resource.length" :max="99" class="item">
              <el-button size="mini">附件</el-button>
            </el-badge>
          </template>
        </el-table-column>

        <el-table-column width="110px" align="center" label="所在服务器">
          <template scope="scope">
            <span>{{scope.row.serverName}}</span>
          </template>
        </el-table-column>

        <el-table-column align="center" label="操作" width="150">
          <template scope="scope">
            <el-button v-if="scope.row.status!='published'" size="small" type="success">预览</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div v-show="!listLoading" class="pagination-container">
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="listQuery.page"
                       :page-sizes="[10,20,30, 50]" :page-size="listQuery.limit" layout="total, sizes, prev, pager, next, jumper" :total="total">
        </el-pagination>
      </div>

      <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible">
        <email-info :emailInfo="eInfo"></email-info>
      </el-dialog>
    </el-main>
    <el-footer>Footer</el-footer>
  </el-container>
</template>
<script>
  import emailInfo from './emailInfo.vue'
  import { fetchEmailList } from '@/api/email'
  export default {
    components: {
      emailInfo
    },
    data() {
      return {
        emailList: null,
        total: null,
        listLoading: false,
        dialogFormVisible: false,
        dialogTitle: '',
        listQuery: {
          page: 1,
          limit: 10,
          optype: 'emailLog'
        },
        eInfo: {},
        multipleSelection: [],
        tableKey: 0
      }
    },
    created() {
      this.getEmailList()
    },
    methods: {
      getEmailList() {
        this.listLoading = true
        fetchEmailList(this.listQuery).then(response => {
          const source = response.data.data
          for (const i in source) {
            source[i].content = JSON.parse(source[i].content)
          }
          this.emailList = source
          this.total = response.data.status
          this.listLoading = false
          console.log(source)
        })
      },
      getEmailInfo(row) {
        this.eInfo = Object.assign({}, row)
        console.log(this.eInfo)
        this.dialogTitle = this.eInfo.content.subject
        this.dialogStatus = 'update'
        this.dialogFormVisible = true
      },
      handleSizeChange(val) {
        this.listQuery.limit = val
        this.getEmailList()
      },
      handleCurrentChange(val) {
        this.listQuery.page = val
        this.getEmailList()
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
