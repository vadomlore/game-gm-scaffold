import { default as fetch } from '@/utils/fetch'

export function fetchRechargeList(query) {
  return fetch({
    url: '/api/rechargeLog/rechargeList',
    method: 'get',
    params: query,
    headers: {
      'X-Requested-With': 'XMLHttpRequest'
    }
  })
}
