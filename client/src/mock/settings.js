export default {
  changePassword: config => {
    console.log('player changePassword' + config.body)
    return {
      status: 'success'
    }
  }
}
