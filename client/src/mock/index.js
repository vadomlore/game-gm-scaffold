import Mock from 'mockjs'
// import loginAPI from './login'
import articleAPI from './article'
import remoteSearchAPI from './remoteSearch'
import guideAPI from './player'
import activityAPI from './activity'
// import settingsAPI from './settings'

Mock.setup({
  timeout: '350-600'
})

// 登录相关
// Mock.mock(/\/login\/login/, 'post', loginAPI.loginByUsername)
// Mock.mock(/\/login\/logout/, 'post', loginAPI.logout)
// Mock.mock(/\/user\/info\.*/, 'get', loginAPI.getUserInfo)
// Mock.mock(/\/changepassword/, 'post', settingsAPI.changePassword)

// 文章相关
Mock.mock(/\/article\/list/, 'get', articleAPI.getList)
Mock.mock(/\/article\/detail/, 'get', articleAPI.getArticle)
Mock.mock(/\/article\/pv/, 'get', articleAPI.getPv)

// 搜索相关
Mock.mock(/\/search\/user/, 'get', remoteSearchAPI.searchUser)
Mock.mock(/\/search\/player/, 'get', remoteSearchAPI.searchPlayer)

// 玩家引导相关
// Mock.mock(/\/guide\/list/, 'get', guideAPI.fetchPlayer)
Mock.mock(/\/guide\/reset/, 'post', guideAPI.resetGuide)
Mock.mock(/\/guide\/skip/, 'post', guideAPI.skipGuide)
Mock.mock(/\/player\/addExp/, 'get', guideAPI.getPlayerExp)
Mock.mock(/\/api\/v1\/getActivities\?serverKey\=.*\&functionName\=getWholeActivitiesInfo.*/, 'get', activityAPI.loadActivities)
Mock.mock(/\/api\/v1\/openActivity/, 'post', activityAPI.openActivity)
// Mock.mock(/\/api\/v1\/deleteActivity/, 'post', activityAPI.deleteActivity)
export default Mock
