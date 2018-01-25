import { default as fetch } from '@/utils/fetch'
import { getServerName } from '@/utils/auth' // 验权

export function getServerInfo() {
  return fetch({
    url: '/api/gameserver/all',
    method: 'get',
    headers: {
      'X-Requested-With': 'XMLHttpRequest'
    }
  })
}
export function fetchPlayerList(query) {
  Object.assign(query, { 'serverKey': getServerName() })
  return fetch({
    url: '/api/player/querySimplePlayer',
    method: 'get',
    params: query
  })
}
export function fetchEmailList(query) {
  return fetch({
    url: 'api/operationLog/emailList',
    method: 'get',
    params: query
  })
}
export function getProperty(query) {
  return fetch({
    url: 'api/email/getProperty',
    method: 'get',
    params: query
  })
}

export function recordOperationLog(obj) {
  const data = obj
  return fetch({
    url: '/api/operationLog/record',
    method: 'post',
    data: data,
    headers: {
      'X-Requested-With': 'XMLHttpRequest'
    }
  })
}

/**
 * params数据结构
 * {
 *    data:{
 *        "source":"pid1xxxxx",
 *        "sourceName":"abc",
 *        "dests":["pid10001", "pid10002"],
 *        "title":"titlename",
 *        "content":"content",
 *        "time":10000123,(timestamp)类型数据
 *        "type":mailType, ///TYPE_P2P = 1, TYPE_SYS2ALL = 2, TYPE_SYS2P = 3;
 *        "attachements":[[1,1,1],[2,2,2],[3,3,3]]] //list<int[]]>
 *        "sendTime":1231123 //long (optional) 如果有该字段，表示是定时发送
 *    }
 * }
 * @param {*发送玩家邮件} params
 */
export function sendPlayerMail(params) {
  const query = {
  }
  Object.assign(query, { 'serverKey': getServerName() })
  const data = { data: params }
  return fetch({
    url: '/api/mail/sendPlayerMail',
    method: 'post',
    params: query,
    data: data,
    headers: {
      'X-Requested-With': 'XMLHttpRequest'
    }
  })
}

