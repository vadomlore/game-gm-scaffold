import fetch from '@/utils/fetch'

import { getServerName } from '@/utils/auth' // 验权

// 获取Guide相关信息
// export function fetchPlayer(query) {
//   console.log(JSON.stringify(query))
//   return fetch({
//     url: '/guide/list',
//     method: 'get',
//     params: query
//   })
// }

// 修改玩家货币
export function setGuardBattleTimes(data) {
  const params = {}
  Object.assign(params, { 'serverKey': getServerName(), 'functionName': 'setGuardBattleTimes' })
  return fetch({
    url: '/api/v1/setGuardBattleTimes',
    method: 'post',
    params: params,
    data: data
  })
}

// 获取玩家相关信息
export function getPlayerPidByUid(query) {
  Object.assign(query, { 'serverKey': getServerName() })
  console.log('queryInfo:' + JSON.stringify(query))
  return fetch({
    url: '/api/player/getPidByUid',
    method: 'get',
    params: query
  })
}
// 获取玩家相关信息
export function fetchPlayer(query) {
  Object.assign(query, { 'serverKey': getServerName() })
  console.log('queryInfo:' + JSON.stringify(query))
  return fetch({
    url: '/api/player/list',
    method: 'get',
    params: query
  })
}

// 获取玩家相关信息
export function fetchSimplePlayer(pid) {
  const query = {
    'pid': pid
  }
  Object.assign(query, { 'serverKey': getServerName() })
  console.log('queryInfo:' + JSON.stringify(query))
  return fetch({
    url: '/api/player/querySimplePlayer',
    method: 'get',
    params: query
  })
}

// 重置引导
export function resetGuide(pid) {
  const data = {
    pid
  }
  console.log(data)
  return fetch({
    url: '/guide/reset',
    method: 'post',
    data: data
  })
}

// 跳过引导
export function skipGuide(pid) {
  const data = {
    'pid': pid,
    'serverKey': getServerName()
  }
  return fetch({
    url: '/api/guide/skip',
    method: 'get',
    params: data
  })
}

// 增加玩家经验
export function addPlayerExp(pid, exp) {
  const params = { 'pid': pid, exp }
  Object.assign(params, { 'serverKey': getServerName() })
  console.log('addPlayeExp %s %s %s', getServerName(), pid, exp)
  return fetch({
    url: '/api/player/addExp',
    method: 'get',
    params: params
  })
}

// 修改玩家货币
export function modifyPlayerCurrency(pid, diamond, gold, strength) {
  const params = { 'pid': pid, 'diamond': diamond, 'gold': gold, 'strength': strength }
  Object.assign(params, { 'serverKey': getServerName() })
  console.log('modifyPlayerCurrency %s %s', getServerName(), params)
  return fetch({
    url: '/api/player/modifyCurrency',
    method: 'get',
    params: params
  })
}

// 获取玩家种族单兵/英雄信息
export function fetchArmy(listQuery) {
  const params = { 'pid': listQuery.pid, 'type': listQuery.selectHeroTypeValue }
  Object.assign(params, { 'serverKey': getServerName() })
  console.log('fetchArmy %s %s', getServerName(), params)
  return fetch({
    url: '/api/player/fetchArmy',
    method: 'get',
    params: params
  })
}

// 删除玩家种族单兵/英雄信息
export function deleteArmy(pid, cid, race) {
  const data = { 'pid': pid, 'type': race, 'cid': cid }
  console.log('deleteArmy %s %s', getServerName(), JSON.stringify(data))
  return fetch({
    url: '/api/player/deleteArmy',
    method: 'post',
    params: { 'serverKey': getServerName() },
    data: data
  })
}

// 修改玩家种族单兵/英雄信息
export function modifyArmy(pid, type, param) {
  const data = { 'pid': pid, 'type': type,
    'data': param
  }
  Object.assign(data, param)
  console.log('modifyArmy %s %s', getServerName(), JSON.stringify(data))
  return fetch({
    url: '/api/player/modifyArmy',
    method: 'post',
    params: { 'serverKey': getServerName() },
    data: data
  })
}

// 增加种族经验
export function addRaceLevel(pid, raceName, raceLevel) {
  let race = 0
  if (raceName.indexOf('Zerg') > 0) {
    race = 3
  }
  if (raceName.indexOf('Protoss') > 0) {
    race = 4
  }
  if (raceName.indexOf('Terran') > 0) {
    race = 2
  }
  const data = { 'pid': pid, 'type': race, 'level': raceLevel }
  console.log('addRaceLevel %s %s %s', getServerName(), JSON.stringify(data))
  return fetch({
    url: '/api/player/addRaceLevel',
    method: 'post',
    params: { 'serverKey': getServerName() },
    data: data
  })
}

// 增加道具
export function addPlayerResource(pids, items) {
  const data = { 'pids': pids, 'items': items }
  console.log('addResource %s %s %s', getServerName(), JSON.stringify(data))
  return fetch({
    url: '/api/player/addResource',
    method: 'post',
    params: { 'serverKey': getServerName() },
    data: data
  })
}

// 显示玩家道具,返回值{resource:[], item:[]}
export function listPlayerResource(queryCondition) {
  const params = queryCondition
  Object.assign(params, { 'serverKey': getServerName() })
  console.log('listPlayerResource %s %s %s', queryCondition.pid, getServerName(), JSON.stringify(params))
  return fetch({
    url: '/api/player/listPlayerResource',
    method: 'get',
    params: params
  })
}
export function modifyPlayerItem(pid, itemId, type, count) {
  const data = {
    'pid': pid,
    'itemId': itemId,
    'count': count,
    'type': type
  }
  const params = {}
  Object.assign(params, { 'serverKey': getServerName() })
  return fetch({
    url: '/api/player/modifyPlayerItem',
    method: 'post',
    data: data,
    params: params
  })
}

// 关卡信息
export function showChapterMissionInfo(query) {
  const params = { 'pid': query.pid, 'race': query.selectedRaceType }

  Object.assign(params,
    {
      'serverKey': getServerName(),
      'functionName': 'showChapterMissionInfo'
    })
  return fetch({
    url: '/api/v1',
    method: 'get',
    params: params
  })
}

export function clarkLevel(param) {
  const data = {
    'pid': param.pid,
    'type': param.type,
    'checkpointId': param.checkpointId
  }
  Object.assign(param, {
    'serverKey': getServerName(),
    'functionName': 'clarkLevel'
  })
  return fetch({
    url: '/api/v1',
    method: 'post',
    params: param,
    data: data,
    headers: {
      'X-Requested-With': 'XMLHttpRequest'
    }
  })
}
export function clarkLevelTo(pid, selectedRaceType, unlockIdTo) {
  const data = {
    'pid': pid,
    'type': selectedRaceType,
    'checkpointId': unlockIdTo
  }
  const param = {}
  Object.assign(param, {
    'serverKey': getServerName(),
    'functionName': 'clarkLevelTo'
  })
  // alert('Clark to :' + JSON.stringify(data))
  return fetch({
    url: '/api/v1',
    method: 'post',
    params: param,
    data: data,
    headers: {
      'X-Requested-With': 'XMLHttpRequest'
    }
  })
}
