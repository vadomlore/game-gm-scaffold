<template>
  <el-form-item label="选择物品" prop="">
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
    <div class="block">
      <div class="accessoryList">
        <el-tag
          v-for="tag in gamepokeys"
          :key="tag.name"
          :closable="true"
          @close="handleClose(tag)"
          :type="tag.type">
          {{tag.name}} x{{tag.nums}}
        </el-tag>
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
        gamepokeys: [],
        options: []
      }
    },
    created() {
      this.getPropertyOptions()
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
        const goods = this.selectedOptions[1]
        console.log(this.gamepokeys)
        if (!goods) {
          this.$message({
            message: '请选择物品',
            type: 'warning'
          })
          return
        }
        const tagObj = {}
        tagObj.name = goods
        tagObj.rId = goods
        tagObj.type = 'success'
        tagObj.nums = this.num1
        let flag = false
        for (var i = 0; i < this.gamepokeys.length; i++) {
          if (goods === this.gamepokeys[i].name) {
            flag = true
            this.gamepokeys[i].nums += this.num1
          }
        }
        if (!flag) {
          this.gamepokeys.push(tagObj)
        }
        this.$emit('sendResource', this.gamepokeys)
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
