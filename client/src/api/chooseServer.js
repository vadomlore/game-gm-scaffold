import { default as fetch } from '@/utils/fetch'

export function getServerInfo() {
  return fetch({
    url: '/api/gameserver/all',
    method: 'get',
    headers: {
      'X-Requested-With': 'XMLHttpRequest'
    }
  })
}
export function addServer(serverInfo) {
  const data = serverInfo
  return fetch({
    url: '/api/gameserver/create',
    method: 'post',
    data: data,
    headers: {
      'X-Requested-With': 'XMLHttpRequest'
    }
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

