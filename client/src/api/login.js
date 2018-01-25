import { default as fetch } from '@/utils/fetch'

export function loginByUsername(username, password) {
  const data = {
    username,
    password
  }
  return fetch({
    method: 'post',
    url: '/api/auth/login',
    data: data,
    headers: {
      'X-Requested-With': 'XMLHttpRequest'
    }
  })
}

export function logout() {
  return fetch({
    url: '/api/logout',
    method: 'post'
  })
}

export function getUserInfo(token) {
  return fetch({
    url: '/api/me',
    method: 'get',
    headers: {
      'X-Requested-With': 'XMLHttpRequest'
    }
  })
}

