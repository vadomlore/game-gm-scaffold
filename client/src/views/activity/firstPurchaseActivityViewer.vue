<template>
  <el-form-item label="参数" prop="">
     <el-input v-model="purchaseBaseNumber" placeholder="基数">
     </el-input>
    <el-cascader
      placeholder="试试搜索：物品"
      :options="options"
      v-model="selectedOptions"
      :show-all-levels="false"
      @change="handleChange"
      filterable
    ></el-cascader>
    <el-input-number v-model="num1" @change="handleChange" :min="1" :max="100" label="描述文字"></el-input-number>
    <el-button type="primary" @click="addAttrs">添加</el-button>
    <el-button type="primary" @click="cleanGameData">清除</el-button>
    <div class="block">
      <div class="accessoryList">
        <el-input
          v-model="paramDataShower">
        </el-input>
      </div>
    </div>
  </el-form-item>
</template>
<script>
  import { getProperty } from '@/api/email'
  export default {
    data() {
      return {
        selectedOptions: [],
        num1: 1,
        gamepokeys: {
          baseNumber: 0,
          awards: {}
        },
        paramData: JSON.stringify({
          baseNumber: 0,
          awards: {}
        }),
        options: [],
        purchaseBaseNumber: 0
      }
    },
    created() {
      this.getPropertyOptions()
    },
    computed: {
      paramDataShower: function() {
        return this.paramData
      }
    },
    methods: {
      getPropertyOptions() {
        getProperty('').then(response => {
          const arr = response.data.data
          const optionsArr = []
          const sourceObj = {}
          for (const v of arr) {
            for (const key in v) {
              // sourceObj[key]=[...new Set(v[key].split(",").slice(2,-1))]
              sourceObj[key] = v[key].split(',').slice(2, -1)
            }
          }
          for (const k in sourceObj) {
            if (k === '副类型') {
              for (const type of [...new Set(sourceObj[k])]) {
                const optParentObj = {}
                optParentObj.value = type
                switch (type) {
                  case '-1':
                    optParentObj.label = '奖励'
                    break
                  case '1':
                    optParentObj.label = '材料'
                    break
                  case '4':
                    optParentObj.label = '基因段'
                    break
                  case '6':
                    optParentObj.label = '消耗品'
                    break
                  case '7':
                    optParentObj.label = '碎片'
                    break
                }
                optParentObj.children = []
                optionsArr.push(optParentObj)
              }
            }
          }
          for (const i in sourceObj['序列']) {
            const optChildObj = {}
            optChildObj.value = sourceObj['序列'][i]
            optChildObj.label = sourceObj['物品名称'][i]
            for (const ov of optionsArr) {
              if (ov.value === sourceObj['副类型'][i]) {
                ov.children.push(optChildObj)
              }
            }
          }
          this.options = optionsArr
          console.log(JSON.stringify(optionsArr))
        })
      },
      addAttrs() {
        const goods = this.selectedOptions[1]
        if (!goods) {
          this.$message({
            message: '请选择物品',
            type: 'warning'
          })
          return
        }
        this.gamepokeys.baseNumber = this.purchaseBaseNumber
        const tagObj = {}
        tagObj[goods] = this.num1
        Object.assign(this.gamepokeys.awards, tagObj)
        console.log('gamepokeys:' + JSON.stringify(this.gamepokeys))
        this.paramData = JSON.stringify(this.gamepokeys)
        this.$emit('sendFirstPurchaseParams', this.paramData)
      },
      cleanGameData() {
        this.gamepokeys = {
          baseNumber: 0,
          awards: {}
        }
        this.paramData = JSON.stringify(this.gamepokeys)
      },
      handleChange(value) {
        console.log(value)
      }
    }
  }
</script>
<style>
  .accessoryList{
    margin-top: 20px;
    background: #fff;
    padding: 20px 10px;
    border: 1px solid #ddd;
    border-radius: 4px;
    min-height: 120px;
  }
  .accessoryList .el-tag{
    margin-right: 20px;
  }
</style>
