import Vue from 'vue'
import Router from 'vue-router'
const _import = require('./_import_' + process.env.NODE_ENV)
// in development env not use Lazy Loading,because Lazy Loading too many pages will cause webpack hot update too slow.so only in production use Lazy Loading

Vue.use(Router)

/* layout */
import Layout from '../views/layout/Layout'

/**
* icon : the icon show in the sidebar
* hidden : if `hidden:true` will not show in the sidebar
* redirect : if `redirect:noredirect` will no redirct in the levelbar
* noDropdown : if `noDropdown:true` will has no submenu
* meta : { role: ['admin'] }  will control the page role
**/
export const constantRouterMap = [
  { path: '/login', component: _import('login/index'), hidden: true },
  { path: '/chooseServer', component: _import('chooseServer/index'), hidden: true },
  { path: '/authredirect', component: _import('login/authredirect'), hidden: true },
  { path: '/404', component: _import('errorPage/404'), hidden: true },
  { path: '/401', component: _import('errorPage/401'), hidden: true },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: '首页',
    hidden: true,
    children: [{ path: 'dashboard', component: _import('dashboard/index') }]
  },
  {
    path: '/introduction',
    component: Layout,
    redirect: '/introduction/index',
    icon: 'people',
    noDropdown: true,
    children: [{ path: 'index', component: _import('introduction/index'), name: '简述' }]
  }
]

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})

export const asyncRouterMap = [
  {
    path: '/recharge',
    component: Layout,
    redirect: '/recharge/index',
    name: '用户充值',
    icon: 'email',
    children: [
      { path: 'index', component: _import('recharge/index'), name: '充值记录' }
    ]
  },
  {
    path: '/email',
    component: Layout,
    redirect: '/email/index',
    name: '邮件',
    icon: 'email',
    children: [
      { path: 'index', component: _import('email/index'), name: '发送邮件' },
      { path: 'emaillist', component: _import('email/emaillist'), name: '所有邮件' },
      { path: 'timerSendEmail', component: _import('email/timerSendEmail'), name: '邮件定时发送' }
    ]
  },
  {
    path: '/permission',
    component: Layout,
    redirect: '/permission/index',
    name: '权限管理',
    icon: 'lock',
    meta: { role: ['admin'] },
    children: [
      { path: 'permissionView', component: _import('permission/permissionView'), name: '权限管理', meta: { role: ['admin'] }}
    ]
  },
  {
    path: '/settings',
    component: Layout,
    redirect: '/settings/changepassword',
    name: '设置',
    icon: 'lock',
    meta: { role: ['admin'] },
    noDropdown: true,
    children: [{ path: 'changepassword', component: _import('settings/changepassword'), name: '修改密码', meta: { role: ['admin'] }}]
  },
  {
    path: '/theme',
    component: Layout,
    redirect: 'noredirect',
    name: 'theme',
    icon: 'theme',
    noDropdown: true,
    children: [{ path: 'index', component: _import('theme/index'), name: '换肤' }]
  },
  {
    path: '/game',
    component: Layout,
    redirect: '/game/index',
    name: 'Game',
    icon: 'password',
    children: [
      { path: 'guide', component: _import('game/guide'), name: '玩家引导' },
      { path: 'playerInfo', component: _import('game/playerInfo'), name: '玩家信息' },
      { path: 'playerCurrencyInfo', component: _import('game/playerCurrencyInfo'), name: '玩家货币信息' },
      { path: 'playerRaceInfo', component: _import('game/playerRaceInfo'), name: '玩家英雄信息' },
      { path: 'giveplayeritem', component: _import('game/giveplayeritem'), name: '赠送玩家道具' },
      { path: 'playerItemsInfo', component: _import('game/playerItemsInfo'), name: '玩家道具信息' },
      { path: 'guardBattleTimes', component: _import('game/guardBattleTimes'), name: '守卫战次数设置' },
      { path: 'playerLevelInfo', component: _import('game/playerLevelInfo'), name: '玩家关卡信息' }
    ]
  },
  {
    path: '/activity',
    component: Layout,
    redirect: '/game/index',
    name: '活动',
    icon: 'password',
    children: [
      { path: 'index', component: _import('activity/index'), name: '游戏活动' },
      { path: 'sevenDayActivity', component: _import('activity/sevenDayActivity'), name: '增加七日活动' },
      { path: 'firstPurchaseActivity', component: _import('activity/firstPurchaseActivity'), name: '增加首充活动' },
      { path: 'activityList', component: _import('activity/activityList'), name: '活动操作记录' }
    ]
  }
]
