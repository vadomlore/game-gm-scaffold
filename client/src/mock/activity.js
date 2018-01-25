import Mock from 'mockjs'
const activities = [
  {
    activityId: 1001,
    activityType: 'sevenDay',
    activityOpenTime: 151387200000,
    activityEndTime: 151387200000,
    description: 'aaa',
    params: '',
    status: 'OK'
  },
  {
    activityId: 10005,
    activityType: 'sevenDay',
    activityOpenTime: 151387200000,
    activityEndTime: 151387200000,
    description: 'aaa',
    params: '',
    status: 'OK'
  },
  {
    activityId: 100030,
    activityType: 'sevenDay',
    activityOpenTime: 151387200000,
    activityEndTime: 151387200000,
    description: 'aaa',
    params: '',
    status: 'OK'
  }
]
export default {
  loadActivities: config => {
    return activities
  },
  openActivity: config => {
    console.log('mockUpConfig:' + JSON.stringify(config))
    const newActivity = {
      activityId: Mock.Random.natural(),
      activityType: JSON.parse(config.body).activityType,
      activityOpenTime: Date(JSON.parse(config.body).activityOpenTime),
      activityEndTime: Date(JSON.parse(config.body).activityEndTime),
      description: JSON.parse(config.body).description,
      status: 'OK'
    }
    activities.elements.push(newActivity)
    return newActivity
  },
  deleteActivity: config => {
    console.log('mock delete activity id %s', JSON.parse(config.body).activityId)
    const activityId = JSON.parse(config.body).activityId
    for (const v of activities.elements) {
      if (v.activityId === activityId) {
        const index = activities.elements.indexOf(v)
        activities.elements.splice(index, 1)
        return 'ok'
      }
    }
    return { err: 'invalid activityId' }
  }
}
