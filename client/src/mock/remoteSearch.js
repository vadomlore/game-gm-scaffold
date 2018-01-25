import Mock from 'mockjs'
import { param2Obj } from '@/utils'

const NameList = []
const count = 100
const player = {
  'elements': [
    {
      'itemId': 1,
      'pid': '7186e437aa9b482fa636f1e7febb6b90',
      'playerAccount': '41D0A9976C904E13A9EC7676660930EE',
      'playerName': '高老师',
      'isOnline': true,
      'level': 2,
      'exp': 0,
      'diamond': 1000,
      'gold': 150000,
      'energy': 120
    }
  ]
}
for (let i = 0; i < count; i++) {
  NameList.push(Mock.mock({
    name: '@first'
  }))
}
NameList.push({ name: 'mockPan' })

export default {
  searchUser: config => {
    const { name } = param2Obj(config.url)
    const mockNameList = NameList.filter(item => {
      const lowerCaseName = item.name.toLowerCase()
      if (name && lowerCaseName.indexOf(name.toLowerCase()) < 0) return false
      return true
    })
    return { items: mockNameList }
  },
  searchPlayer: config => {
    return { items: player }
  }
}
