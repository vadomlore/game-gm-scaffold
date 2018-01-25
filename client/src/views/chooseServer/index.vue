<template>
    <el-row>
        <el-col :span="24">
            <header class="header">
                <h1><a href="/en-us" title="PlayFab">PlayFab</a></h1>
                <el-dropdown size="mini" @command="handleCommand"  trigger="click" class="profile">
                    <span class="el-dropdown-link">admin<i class="el-icon-arrow-down el-icon--right"></i></span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item command="a">服务器</el-dropdown-item>
                        <el-dropdown-item command="logout" divided>退出登录</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </header>
        </el-col>
        <el-col :span="24">
            <section class="studio-index">
                <header>
                    <h3>My Studios and Titles</h3>
                    <div class="actions">
                        <el-button type="primary"  @click="dialogFormVisible = true">新增服务器</el-button>
                        <el-dialog title="新增服务器" :visible.sync="dialogFormVisible" :show-close="false">
                            <el-form :model="addServerForm" :rules="rules" ref="addServerForm">
                                <el-form-item label="服务器名称" :label-width="formLabelWidth" prop="serverKey">
                                    <el-input v-model="addServerForm.serverKey" auto-complete="off"></el-input>
                                </el-form-item>
                                <el-form-item label="账号服Id" :label-width="formLabelWidth" prop="serverId">
                                    <el-input v-model="addServerForm.serverId" auto-complete="off"></el-input>
                                </el-form-item>
                                <el-form-item label="账号服Url" :label-width="formLabelWidth" prop="accountServerUrl">
                                    <el-input v-model="addServerForm.accountServerUrl" auto-complete="off"></el-input>
                                </el-form-item>
                                <el-form-item label="游戏服务器IP" :label-width="formLabelWidth" prop="serverIp">
                                    <el-input v-model="addServerForm.serverIp" auto-complete="off"></el-input>
                                </el-form-item>
                                <el-form-item label="服务器描述" :label-width="formLabelWidth" prop="description">
                                    <el-input v-model="addServerForm.description" auto-complete="off"></el-input>
                                </el-form-item>
                            </el-form>
                            <div slot="footer" class="dialog-footer">
                                <el-button @click="dialogFormVisible = false">取 消</el-button>
                                <el-button type="primary" @click="submitForm('addServerForm')">确 定</el-button>
                            </div>
                        </el-dialog>
                    </div>
                </header>
                <article>
                    <dl class="studios">
                        <dt>
                           <header>{{addServerForm.game}}</header>
                        </dt>
                        <dd>
                            <el-row >
                                <el-col :span="4" v-for="(item, index) in list" :key="index" :class="index < 4 ? 'mt0' : 'mt20'" :offset="index%4 > 0 ? 2 : 0">
                                    <el-card :body-style="{ padding: '0px',background:'#f4f4f4' }">
                                        <el-tooltip placement="right" effect="light">
                                            <div slot="content">{{item.description}}</div>
                                            <img src="../../assets/img/server.png" class="image" @click="navigateToDashboard(item.serverKey)">
                                        </el-tooltip>
                                        <div style="padding: 14px;background: #fff;">
                                            <span>{{item.serverKey}}</span>
                                            <div class="bottom clearfix">
                                                <time class="time">{{item.serverIp}}</time>
                                                <el-button type="text" class="button">操作按钮</el-button>
                                            </div>
                                        </div>
                                    </el-card>
                                </el-col>
                            </el-row>
                        </dd>
                    </dl>
                </article>
            </section>
        </el-col>
    </el-row>
</template>

<script>
    import { getServerInfo, addServer } from '@/api/chooseServer'
    export default {
      name: 'chooseServer',
      data() {
        return {
          dialogFormVisible: false,
          addServerForm: {
            serverKey: '',
            serverIp: '',
            serverId: '',
            accountServerUrl: '',
            description: '',
            game: '星际归来2'
          },
          rules: {
            serverKey: [
                { required: true, message: '请输入服务器名称', trigger: 'blur' }
            ],
            serverIp: [
                { required: true, message: '请输入服务器IP', trigger: 'blur' }
            ],
            serverId: [
                { required: true, message: '请输入账号服务器Id', trigger: 'blur' }
            ],
            accountServerUrl: [
                { required: true, message: '请输入账号服务器url', trigger: 'blur' }
            ],
            description: [
                { required: true, message: '请填写服务器的描述', trigger: 'blur' }
            ]
          },
          formLabelWidth: '120px',
          list: null
        }
      },
      created() {
        this.getServerList()
      },
      methods: {
        handleCommand(command) {
          if (command === 'logout') {
            this.$store.dispatch('LogOut').then(() => {
              location.reload()// 为了重新实例化vue-router对象 避免bug
            })
          }
        },
        navigateToDashboard(serverName) {
          this.$store.dispatch('setServerName', serverName)
          this.$router.push({ path: '/' })
          // this.showDialog = true
        },
        getServerList() {
          getServerInfo().then((response) => {
            this.list = response.data
          })
        },
        submitForm(formName) {
          this.$refs[formName].validate((valid) => {
            if (valid) {
              addServer(this.addServerForm).then((response) => {
                if (response.data) {
                  var buildObj = { }
                  buildObj.serverKey = this.addServerForm.serverKey
                  buildObj.serverIp = this.addServerForm.serverIp
                  buildObj.description = this.addServerForm.description
                  buildObj.serverId = this.addServerForm.serverId
                  buildObj.accountServerUrl = this.addServerForm.accountServerUrl
                  this.list.push(buildObj)
                  this.dialogFormVisible = false
                }
              }).catch(() => {
                alert('服务器添加失败')
              })
            } else {
              console.log('error submit!!')
              return false
            }
          })
        }
      }
    }
</script>

<style>
    .header {
        background: #323a44 no-repeat top left;
        height: 60px;
        overflow: hidden;
    }

    .header h1 {
        margin: 0;
        font: 2.8em/1.3 "Open Sans Light", Helvetica, Arial, sans-serif;
        padding-left: 0.33em;
    }

    .header h1 a {
        color: #fff;
    }

    .el-dropdown-link {
        cursor: pointer;
        color: #409EFF;
    }

    .el-icon-arrow-down {
        font-size: 12px;
    }

    .el-dropdown-menu__item {
        text-align: center;
    }

    header div.profile {
        position: absolute;
        top: 20px;
        right: 12px;
        text-align: right;
        color: #f5f5f5;
    }

    section.studio-index > header {
        min-height: 24px;
    }

    section > header {
        position: relative;
        padding: 24px;
        border-bottom: 1px solid #d9d9d9;
    }

    section > header h3 {
        margin: 0;
    }

    section > header div.actions {
        margin: 0;
        position: absolute;
        right: 24px;
        top: 16px;
    }
    .time {
        font-size: 13px;
        color: #999;
    }

    .bottom {
        margin-top: 13px;
        line-height: 12px;
    }

    .button {
        padding: 0;
        float: right;
    }

    .image {
        width: 100%;
        display: block;
        cursor:pointer;
    }

    .clearfix:before,
    .clearfix:after {
        display: table;
        content: "";
    }

    .clearfix:after {
        clear: both
    }
   .studios dt{
        width: 240px;
        float: left;
        border: 1px solid #ccc;
        padding: 9px;
        text-align: center;
        color: #f56a00;
        font-size: 1em;
        border-radius: 3px;
        margin-left: 10px;
    }
    .studios dd{
        padding-left: 240px;
    }
  .mt20{
    margin-top: 20px;
  }
</style>

