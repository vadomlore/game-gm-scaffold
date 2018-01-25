<template>
  <el-container>
    <el-header>Header</el-header>
    <el-main>
      <el-table :key='tableKey' :data="rechargeList" v-loading="listLoading" element-loading-text="给我一点时间" border fit
                highlight-current-row style="width: 100%">
        <el-table-column align="center" label="序号" width="65" type="index"></el-table-column>

        <el-table-column width="180px" align="center" label="充值时间">
          <template scope="scope">
            <span>{{scope.row.crttime | parseTime('{y}-{m}-{d} {h}:{i}')}}</span>
          </template>
        </el-table-column>

        <el-table-column min-width="300px" label="详情">
          <template scope="scope">
            <span class="link-type" @click="getEmailInfo(scope.row)">{{scope.row.productid}}</span>
          </template>
        </el-table-column>

        <el-table-column width="110px" align="center" label="数量">
          <template scope="scope">
            <span>{{scope.row.productnum}}</span>
          </template>
        </el-table-column>

        <el-table-column width="280px" label="充值ID">
          <template scope="scope">
            <span>{{scope.row.pid}}</span>
          </template>
        </el-table-column>

        <el-table-column width="110px" align="center" label="充值金额">
          <template scope="scope">
            <span>{{scope.row.price}}</span>
          </template>
        </el-table-column>

        <el-table-column width="110px" align="center" label="状态">
          <template scope="scope">
            <span>{{scope.row.state}}</span>
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

    </el-main>
    <el-footer>Footer</el-footer>
  </el-container>
</template>

<script>
  import { fetchRechargeList } from '@/api/recharge'
  export default {
    data() {
      return {
        total: null,
        listLoading: false,
        tableKey: 0,
        listQuery: {
          page: 1,
          limit: 20
        },
        rechargeList: []
      }
    },
    created() {
      this.getRechargeList()
    },
    methods: {
      getRechargeList() {
        this.listLoading = true
        fetchRechargeList(this.listQuery).then(response => {
          this.rechargeList = response.data.data
          this.total = response.data.status
          this.listLoading = false
          console.log(response.data)
        })
      },
      handleSizeChange(val) {
        this.listQuery.limit = val
        this.getRechargeList()
      },
      handleCurrentChange(val) {
        this.listQuery.page = val
        this.getRechargeList()
      }
    }
  }
</script>

<style>
  .el-header, .el-footer {
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
  }

  .el-main {
    background-color: #E9EEF3;
    color: #333;
  }
</style>
