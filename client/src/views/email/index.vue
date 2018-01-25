<template>
  <el-container>
    <el-main>
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="发件人" prop="addresser">
          <el-input v-model="ruleForm.addresser"></el-input>
        </el-form-item>
        <el-form-item label="收件人" prop="receiver">
          <div class="emailContainer" @click="getFocus">
            <el-tag size="mini"
                    v-for="player in compReceiver"
                    closable
                    :key="player"
                    @close="removeReceiver(player)"><span>{{player}}</span></el-tag>
            <div class="editableAddr-box">
              <input type="text" class="nui-editableAddr-ipt" v-model="editableAddrIpt"  @blur="buildRece" v-focus="focusStatus"  />
              <span class="nui-editableAddr-txt">{{editableAddrIpt}}</span>
            </div>
          </div>
          <div class="el-form-item__error" v-if="isWrite">请填写收件人</div>
        </el-form-item>
        <el-form-item label="主题" prop="subject">
          <el-input v-model="ruleForm.subject"></el-input>
        </el-form-item>
        <el-form-item label="" prop="email_cont">
          <el-input type="textarea" :autosize="{ minRows: 10}" v-model="ruleForm.email_cont"></el-input>
        </el-form-item>
        <el-row>
          <el-col :span="6">
            <el-form-item label="定时发送" prop="delivery">
              <el-switch v-model="ruleForm.delivery"></el-switch>
            </el-form-item>
          </el-col>
          <el-col :span="18" v-show="ruleForm.delivery">
            <el-form-item label="发送时间" prop="delivery">
              <el-date-picker v-model="ruleForm.display_time" type="datetime" format="yyyy-MM-dd HH:mm:ss" placeholder="选择发送日期时间"></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <accessory @sendResource="getResource"></accessory>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">发送</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-main>
    <el-aside width="24%">
      <el-container>
        <el-header>玩家列表</el-header>
        <el-main>
          <el-input placeholder="请输入内容" @keyup.enter.native="handleFilter"  v-model="listQuery.inputs" :disabled="isInput" class="input-with-select search_group">
            <el-select v-model="listQuery.condition" filterable slot="prepend" @change="handleChange" placeholder="选择条件" class="select_input">
              <el-option-group
                v-for="group in conditionOptions"
                :key="group.label"
                :label="group.label">
                <el-option
                  v-for="item in group.options"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-option-group>
            </el-select>
            <el-button slot="append" type="primary"  icon="search" @click="handleFilter">搜索</el-button>
          </el-input>
          <div class="userList">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>Game Player</span>
                <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>
              </div>
              <div class="text item" v-loading="listLoading" element-loading-text="给我一点时间" v-for="i in playerList">
                <el-tag size="mini"><span @click="addToReceiver(i)">{{i.playerName+'&lt;'+i.pid+'&gt;'}}</span></el-tag>
              </div>
            </el-card>
          </div>
        </el-main>
      </el-container>
    </el-aside>
  </el-container>
</template>

<script>
  import accessory from './accessory.vue'
  import { fetchPlayerList, recordOperationLog, sendPlayerMail } from '@/api/email'
  import { getServerName, getUserName } from '@/utils/auth' // 验权
  export default {
    name: 'email',
    components: {
      accessory
    },
    data() {
      return {
        editableAddrIpt: '',
        isInput: false,
        playerList: null,
        total: null,
        listLoading: true,
        focusStatus: false,
        isWrite: false,
        listQuery: {
          page: 1,
          limit: 20,
          condition: undefined, // 选择的条件搜索
          inputs: undefined // 输入的条件
        },
        player: {},
        uArr: [], // 玩家id
        pArr: [], // 玩家
        conditionOptions: [{
          label: '选择条件',
          group: '001',
          options: [{
            label: '玩家ID',
            value: 'pid'
          }, {
            label: '玩家昵称',
            value: 'nickName'
          }, {
            label: '注册时间',
            value: 'rigist'
          }]
        }, {
          label: '已预设条件',
          group: '002',
          options: [{
            label: '注册大于2年',
            value: 'lg2year'
          }, {
            label: '在线时长5小时',
            value: 'line5h'
          }]
        }],
        value8: '',
        ruleForm: {
          addresser: '盛焱游戏团队',
          receiver: null,
          subject: '',
          email_cont: '',
          delivery: false,
          type: 1,
          resource: null,
          display_time: undefined, // 前台展示时间
          desc: ''
        },
        mails: {
          source: '', // String 发件人id
          sourceName: '', // 发件人名称
          dest: [], // 收件人id
          title: '', // 邮件标题
          countent: '', // 邮件内容
          createAt: '', // 发件人名称
          type: '', // TYPE_P2P 玩家-玩家, TYPE_SYS2ALL 系统-全体玩家, TYPE_SYS2P 系统-单玩家
          attachments: [[1001, 2, 1001], [1002, 200, 1002]] // 附件
        },
        rules: {
          addresser: [
            { required: true, message: '请输入发件人', trigger: 'blur' }
          ],
          receiver: [
            { required: true, message: '请填写收件人', trigger: 'blur' }
          ],
          subject: [
            { required: true, message: '请填写主题', trigger: 'blur' }
          ]
        }
      }
    },
    computed: {
      compReceiver: function() {
        const viewData = this.pArr
        if (viewData.length > 0 && typeof viewData[viewData.length - 1] === 'object') {
          viewData[viewData.length - 1] = viewData[viewData.length - 1].nick + '<' + viewData[viewData.length - 1].pId + '>'
        }
        return viewData
      },
      compResource: function() {
        const sourceArr = []
        const arr = []
        const source = this.ruleForm.resource
        for (const i in source) {
          arr[0] = parseInt(source[i].rId)
          arr[1] = parseInt(source[i].nums)
          arr[2] = parseInt(source[i].rId)
          sourceArr.push(arr)
        }
        return sourceArr
      }
    },
    directives: {
      focus: {
        update: function(el, { value }) {
          if (value) {
            el.focus()
          }
        }
      }
    },
    methods: {
      getResource(resource) {
        console.log(resource)
        this.ruleForm.resource = resource
      },
      addToReceiver(dest) {
        this.player.nick = dest.playerName
        this.player.pId = dest.pid
        this.pArr.push(this.player)
        this.uArr.push(dest.pid)
        this.ruleForm.receiver = this.uArr
        console.log(this.ruleForm.receiver)
      },
      recordLog() {
        var buildObj = {}
        buildObj.operation = 'emailLog'
        buildObj.content = JSON.stringify(this.ruleForm)
        buildObj.createAt = new Date().getTime()
        buildObj.user = getUserName()
        buildObj.serverName = getServerName()
        buildObj.extension = ''
        recordOperationLog(buildObj).then((response) => {
        })
      },
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.recordLog()
            const buildEmails = {}
            buildEmails.source = '0'
            buildEmails.sourceName = this.ruleForm.addresser
            buildEmails.dests = this.ruleForm.receiver
            buildEmails.title = this.ruleForm.subject
            buildEmails.content = this.ruleForm.email_cont
            buildEmails.time = new Date().getTime()
            buildEmails.type = this.ruleForm.type
            buildEmails.attachements = this.compResource
            if (this.ruleForm.delivery) {
              buildEmails.sendTime = (this.ruleForm.delivery) && (buildEmails.sendTime = Date.parse(this.ruleForm.display_time))
            }
            sendPlayerMail(buildEmails).then((response) => {
              if (response.data.status.toLowerCase() === 'ok') {
                this.$message({
                  message: '邮件发送成功',
                  type: 'success',
                  center: true,
                  customClass: 'tips'
                })
              }
            })
          } else {
            console.log('error submit!!')
            return false
          }
        })
      },
      getUserList() {
        this.listLoading = true
        const ckey = this.listQuery.condition
        this.listQuery[ckey] = this.listQuery.inputs
        fetchPlayerList(this.listQuery).then(response => {
          console.log(response.data)
          this.playerList = response.data.elements
          this.total = response.data.total
          this.listLoading = false
        })
      },
      handleFilter() {
        this.getUserList()
      },
      handleChange(value) {
        this.conditionOptions.forEach((v) => {
          if (v.options.some(x => x.value === value)) {
            (v.group === '001') ? (this.isInput = false) : (this.isInput = true)
          }
        })
      },
      getFocus() {
        this.focusStatus = true
      },
      checkReceiver() {
        return this.uArr.length <= 0
      },
      buildRece() {
        this.focusStatus = false
        if (this.editableAddrIpt.trim().length <= 0) {
          if (this.checkReceiver()) {
            this.isWrite = true
          }
          return
        }
        this.addToReceiver({ playerName: '', pid: this.editableAddrIpt })
        this.editableAddrIpt = ''
        this.isWrite = false
      },
      removeReceiver(tag) {
        const start = this.compReceiver.indexOf(tag)
        this.compReceiver.splice(start, 1)
        this.uArr.splice(start, 1)
      },
      resetForm(formName) {
        this.$refs[formName].resetFields()
      }
    }
  }
</script>

<style>
  .el-aside {
    background-color: #D3DCE6;
    color: #333;
    max-width: 24%;
    border-left: 1px solid #ddd;
  }
  .el-header{
    line-height: 60px;
    text-align: center;
  }

  .el-main {
    background-color: #E9EEF3;
    color: #333;
  }
  .el-select .el-input {
    width: 130px;
  }
  .input-with-select .el-input-group__prepend {
    background-color: #fff;
  }
  .userList{
    margin-top: 20px;
  }
  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }
  .el-input-group__append{
    background-color: #409EFF;
    color: #fff;
  }
  .search_group .el-input__icon,
  .search_group .select_input .el-input__inner{
    cursor: pointer !important;
  }
  .tips{
    min-width: 70px;
  }
  .el-tag--mini {
    cursor: pointer;
  }
  .emailContainer {
    background: #fff;
    border: 1px solid #ddd;
    border-radius: 4px;
    padding: 0 15px;
    cursor: text;
    min-height: 40.5px;
  }
  .emailContainer .el-tag--mini {
    margin-right: 5px;
  }
  .emailContainer .editableAddr-box{
    position: relative;
    max-width: 465px;
    height: 12px;
    line-height: 12px;
    font-size: 12px;
    display: inline-block;
    top: 0px;
    padding: 2px 7px;
  }
  .nui-editableAddr-ipt {
    position: absolute;
    left: 9px;
    top: 2px;
    width: 100%;
    margin: 0;
    padding: 0;
    display: inline;
    border: 0;
    outline: 0;
  }
  .emailContainer .nui-editableAddr-ipt {
    top: 2px;
    left: 2px;
  }
  .emailContainer .nui-editableAddr-txt {
    visibility: hidden;
    white-space: nowrap;
  }
</style>
