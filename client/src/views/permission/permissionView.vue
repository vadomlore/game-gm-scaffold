<template>
  <div class="app-container calendar-list-container">
    <div class="filter-container">
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="输入用户名" v-model="listQuery.name">
      </el-input>
      <el-button class="filter-item" type="primary" v-waves icon="el-icon-search" @click="handleFilter">搜索</el-button>
      <!-- Form -->
        <el-button type="text" @click="dialogFormVisible = true">创建角色</el-button>
        <el-dialog title="创建角色" :visible.sync="dialogFormVisible">
            <el-form ref="addUserRuleForm" :model="addUserRuleForm" :rules="addNewUserRules" label-width="80px">
                <el-form-item label="用户名" prop="name">
                    <el-input v-model="addUserRuleForm.name"></el-input>
                </el-form-item>
                <el-form-item label="密码"  prop="password">
                    <el-input v-model="addUserRuleForm.password"  type='password'></el-input>
                </el-form-item>
                <el-form-item label="密码确认" prop="checkPassword">
                    <el-input v-model="addUserRuleForm.checkPassword"  type='password'></el-input>        
                </el-form-item>
                <el-form-item label="选择角色" prop="role">
                    <el-radio-group v-model="addUserRuleForm.role">
                        <el-radio :label="1">admin</el-radio>
                        <el-radio :label="2">developer</el-radio>
                        <el-radio :label="3">vip</el-radio>
                        <el-radio :label="4">visitor</el-radio>
                    </el-radio-group>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click.native.prevent="handleCreateNewUser">确认</el-button>
            </div>
        </el-dialog>
    </div>

    <el-table :key='tableKey' :data="list" v-loading="listLoading" element-loading-text="给我一点时间" border fit highlight-current-row style="width: 100%">
      <el-table-column min-width="110px" label="index" type="index">
      </el-table-column>
      <el-table-column width="180px" align="center" label="Id" prop="id">
      </el-table-column>
      <el-table-column width="180px" align="center" label="名称" prop="name">
      </el-table-column>
      <el-table-column min-width="110px" label="权限">
        <template slot-scope="scope">
            <el-tag close-transition>{{scope.row.namedRoles}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column min-width="110px" label="操作">
         <template slot-scope="scope">
            <el-button
                size="mini"
                type="danger"
                @click="handleDelete(scope.$index, scope.row)">删除</el-button>
      </template>
      </el-table-column>

    </el-table>
    <div v-show="!listLoading" class="pagination-container">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="listQuery.page"
        :page-sizes="[10,20,30, 50]" :page-size="listQuery.limit" layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>

  </div>
</template>

<script>
import waves from '@/directive/waves/index.js' // 水波纹指令
import { isUserNameExists, addNewUser, listAllGrantedUsers, deleteUser } from '@/api/settings'
export default {
  name: 'table_demo',
  directives: {
    waves
  },
  data() {
    var validatePassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      } else {
        if (this.addUserRuleForm.password !== '') {
          this.$refs.addUserRuleForm.validateField('checkPassword')
        }
        callback()
      }
    }
    var validateRole = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('Role不能为空'))
      } else {
        callback()
      }
    }
    var validatecheckPassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else {
        if (value !== this.addUserRuleForm.password) {
          callback(new Error('两次输入密码不一致!'))
        }
        callback()
      }
    }
    var validateName = (rule, value, callback) => {
      if (value === null && value.trim() === '') {
        return callback(new Error('用户名不能为空'))
      }
      setTimeout(() => {
        isUserNameExists(value).then(response => {
          if (response.data.status === 'exists') {
            callback(new Error('名字已经存在'))
          } else {
            callback()
          }
        })
      }, 1000)
    }
    return {
      list: null,
      total: null,
      listLoading: false,
      listQuery: {
        page: 1,
        limit: 20,
        name: undefined
      },
      temp: {
      },
      dialogFormVisible: false,
      dialogStatus: '',
      dialogPvVisible: false,
      pvData: [],
      showAuditor: false,
      tableKey: 0,
      addUserRuleForm: {
        name: '',
        password: '',
        checkPassword: '',
        role: 'insightsUser'
      },
      addNewUserRules: {
        name: [{ validator: validateName, trigger: 'blur' }],
        password: [{ validator: validatePassword, trigger: 'blur' }],
        checkPassword: [{ validator: validatecheckPassword, trigger: 'blur' }],
        role: [{ validator: validateRole, trigger: 'blur' },
            { trigger: 'change', validator: validateRole }
        ]
      },
      filters: {
        statusFilter(status) {
          const statusMap = {
            clark: 'success',
            locked: 'gray'
          }
          return statusMap[status]
        }
      }
    }
  },
  methods: {
    getList() {
      console.log('getList')
      console.log(JSON.stringify(this.listQuery))
      this.listLoading = true
      listAllGrantedUsers(this.listQuery).then(response => {
        this.listLoading = false
        if (!this.noticeGMError(response)) {
          console.log('resp:' + JSON.stringify(response.data))
          this.list = response.data.filter((item, index) => index < this.listQuery.limit * this.listQuery.page && index >= this.listQuery.limit * (this.listQuery.page - 1))
          this.total = response.data.length
        }
      })
    },
    handleCreateNewUser() {
      this.$refs.addUserRuleForm.validate((valid) => {
        if (valid) {
          addNewUser({
            name: this.addUserRuleForm.name,
            password: this.addUserRuleForm.password,
            roles: [this.addUserRuleForm.role]
          }).then(response => {
            this.dialogFormVisible = false
            console.log('onCreateUserResponse: ' + JSON.stringify(response.data))
            this.getList()
            this.$notify({
              title: '成功',
              message: '添加账号成功',
              type: 'success',
              duration: 2000
            })
          }).catch(e => {
            this.dialogFormVisible = false
            this.$notify({
              title: '失败',
              message: '添加账号失败',
              type: 'warning',
              duration: 2000
            })
          })
        } else {
          return false
        }
      })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
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
    handleDelete(index, row) {
      deleteUser(row.id).then(response => {
        for (const v of this.list) {
          if (v.id === row.id) {
            const index = this.list.indexOf(v)
            this.list.splice(index, 1)
            break
          }
        }
      })
    },
    resetTemp() {
      this.temp = {}
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