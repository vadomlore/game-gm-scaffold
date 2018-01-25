import fetch from '@/utils/fetch'

export function changePassword(oldPassword, newPassword) {
  const data = {
    oldPassword,
    newPassword
  }
  return fetch({
    method: 'post',
    url: '/api/changepassword',
    headers: {
      'X-Requested-With': 'XMLHttpRequest'
    },
    data: data
  })
}

export function isUserNameExists(username) {
  const data = {
    username
  }
  return fetch({
    method: 'get',
    url: '/api/user/exists',
    params: data,
    headers: {
      'X-Requested-With': 'XMLHttpRequest'
    }
  })
}

/**
 *
 * @param {json} userData
 */
export function addNewUser(userData) {
  console.log('posted data：+' + JSON.stringify(userData))
  return fetch({
    method: 'post',
    url: '/api/user/add',
    data: userData,
    headers: {
      'X-Requested-With': 'XMLHttpRequest'
    }
  })
}

export function listAllGrantedUsers(query) {
  return fetch({
    method: 'get',
    url: '/api/user/list',
    params: query,
    headers: {
      'X-Requested-With': 'XMLHttpRequest'
    }
  })
}

export function deleteUser(id) {
  return fetch({
    method: 'get',
    url: '/api/user/delete/' + id,
    headers: {
      'X-Requested-With': 'XMLHttpRequest'
    }
  })
}

