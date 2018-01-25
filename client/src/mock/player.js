const guidePlayers = [
  {
    id: 1,
    pid: 'xxx001',
    playerAccount: 'accountId:001',
    playerName: '玩家0001',
    level: 7,
    payment: 1280,
    status: 'online'
  },
  {
    id: 2,
    pid: 'xxx002',
    playerAccount: 'accountId:002',
    playerName: '玩家0002',
    level: 7,
    payment: 1280,
    status: 'online'
  },
  {
    id: 3,
    pid: 'xxx003',
    playerAccount: 'accountId:003',
    playerName: '玩家0003',
    level: 7,
    payment: 1280,
    status: 'offline'
  },
  {
    id: 4,
    pid: 'xxx004',
    playerAccount: 'accountId:004',
    playerName: '玩家0004',
    level: 7,
    payment: 1280,
    status: 'online'
  },
  {
    id: 5,
    pid: 'xxx005',
    playerAccount: 'accountId:005',
    playerName: '玩家0005',
    level: 7,
    payment: 1280,
    status: 'online'
  }
]
export default {
  fetchPlayer: config => {
    return guidePlayers
  },
  resetGuide: config => {
    console.log(config)
    return {
      id: JSON.parse(config.body).pid,
      operation: '重置引导',
      status: 'success'
    }
  },
  skipGuide: config => {
    console.log('player id' + config.body.pid)
    return {
      id: JSON.parse(config.body).pid,
      operation: '跳过引导',
      status: 'success'
    }
  },
  addPlayerExp: config => {
    console.log('player id' + config.body.pid)
    return {
      id: JSON.parse(config.body).pid,
      operation: '跳过引导',
      status: 'success'
    }
  }
}
