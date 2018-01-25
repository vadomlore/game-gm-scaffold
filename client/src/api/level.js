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
