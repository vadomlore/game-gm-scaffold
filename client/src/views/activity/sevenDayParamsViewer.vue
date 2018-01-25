<template>
  <el-form-item label="参数" prop="">
     <el-select v-model="selectedDay" placeholder="请选择时间">
        <el-option
        v-for="item in daySelectedOptions"
        :key="item.value"
        :label="item.label"
        :value="item.value">
        </el-option>
     </el-select>

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
        gamepokeys: {},
        paramData: JSON.stringify({}),
        options: [],
        daySelectedOptions: [{
          value: '1',
          label: '1'
        }, {
          value: '2',
          label: '2'
        }, {
          value: '3',
          label: '3'
        }, {
          value: '4',
          label: '4'
        }, {
          value: '5',
          label: '5'
        }, {
          value: '6',
          label: '6'
        }, {
          value: '7',
          label: '7'
        }],
        selectedDay: ''
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
        // console.log(this.selectedOptions)
        // let type = this.selectedOptions[0]
        if (this.selectedDay === undefined || this.selectedDay === '') return

        const goods = this.selectedOptions[1]
        if (!goods) {
          this.$message({
            message: '请选择物品',
            type: 'warning'
          })
          return
        }
        if (!this.gamepokeys.hasOwnProperty(this.selectedDay)) {
          this.gamepokeys[this.selectedDay] = {}
        }
        const tagObj = {}
        tagObj[goods] = this.num1
        Object.assign(this.gamepokeys[this.selectedDay], tagObj)
        console.log('gamepokeys:' + JSON.stringify(this.gamepokeys))
        this.paramData = JSON.stringify(this.gamepokeys)
        this.$emit('sendSevenDayParams', this.paramData)
      },
      cleanGameData() {
        this.gamepokeys = {}
        this.paramData = JSON.stringify(this.gamepokeys)
      },
      handleChange(value) {
        console.log(value)
      },
      handleClose(tag) {
        this.gamepokeys.splice(this.gamepokeys.indexOf(tag), 1)
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
