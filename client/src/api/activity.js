import fetch from '@/utils/fetch'

import { getServerName } from '@/utils/auth' // 验权

// 获取活动信息
export function loadActivities() {
  const params = { }
  Object.assign(params,
    {
      'serverKey': getServerName(),
      'functionName': 'getWholeActivitiesInfo',
      'relCode': 0
    })
  return fetch({
    url: '/api/v1/getActivities',
    method: 'get',
    params: params
  })
}

// 开启活动
export function openActivity(condition) {
  const data = condition
  const param = {}
  Object.assign(param,
    {
      'serverKey': getServerName(),
      'functionName': 'openActivity'
    })
  return fetch({
    url: '/api/v1/openActivity',
    method: 'post',
    params: param,
    data: data
  })
}

// 删除活动
export function deleteActivity(condition) {
  const params = {}
  Object.assign(params,
    {
      'serverKey': getServerName(),
      'functionName': 'deleteActivity'
    })
  return fetch({
    url: '/api/v1/deleteActivity',
    method: 'post',
    params: params,
    data: condition
  })
}

export function fetchActivityRecords(query) {
  return fetch({
    url: 'api/operationLog/activityRecords',
    method: 'get',
    params: query
  })
}
