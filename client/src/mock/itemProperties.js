/**
 * Created by Administrator on 2017/12/12 0012.
 */
const itemProperties = [{
  value: '0000',
  label: '消耗品',
  children: [{
    value: '101001',
    label: '钻石'
  }]
}, {
  value: '1001',
  label: '材料',
  children: [{
    value: '243101',
    label: '摩诃星粒'
  }]
}, {
  value: '1007',
  label: '碎片',
  children: [{
    value: '312201',
    label: '二级枪械碎片'
  }]
}, {
  value: '1004',
  label: '基因段',
  children: [{
    value: '1002001',
    label: '小喇叭'
  }]
}, {
  value: '1006',
  label: '消耗品',
  children: [{
    value: '102006',
    label: '英雄进阶礼包（人族）'
  }]
}]
const a = [
  {
    "序列": "id,int,100001,101001,101002,101003,101004,101006,101007,101008,102001,102002,102003,102004,102005,102006,102007,102008,102009,102010,102011,102012,105001,105002,105003,105004,105005,105006,106001,106002,106003,107001,111101,121101,131101,112101,112102,112103,112104,112201,112202,112203,112204,112301,112302,112303,112304,112401,112402,112403,112404,112501,112502,112503,112504,122101,122102,122103,122104,122201,122202,122203,122204,122301,122302,122303,122304,122401,122402,122403,122404,122501,122502,122503,122504,132101,132102,132103,132104,132201,132202,132203,132204,132301,132302,132303,132304,132401,132402,132403,132404,132501,132502,132503,132504,200101,200102,200103,200104,200105,200201,200202,200203,200204,200205,200206,215001,215002,215003,215004,215005,215006,215007,215101,215102,215103,215104,215105,215106,215107,215201,215202,215203,215204,215205,215206,215207,243101,243102,243103,243104,243201,243202,243203,243204,243301,243302,243303,243304,243401,243402,243403,243404,243501,243502,243503,243504,312201,312202,312203,312204,312301,312302,312303,312304,312401,312402,312403,312404,312501,312502,312503,312504,322201,322202,322203,322204,322301,322302,322303,322304,322401,322402,322403,322404,322501,322502,322503,322504,332201,332202,332203,332204,332301,332302,332303,332304,332401,332402,332403,332404,332501,332502,332503,332504,313201,313202,313203,313204,313301,313302,313303,313304,313401,313402,313403,313404,313501,313502,313503,313504,411001,411002,411003,411004,411005,411006,411007,411008,411009,411010,411011,421001,421002,421003,421004,421005,421006,421007,421008,421009,421010,431001,431002,431003,431004,431005,431006,431007,431008,431009,431010,451001,451002,451003,451004,451005,451006,451007,451008,511001,511002,511003,511004,511005,511006,511007,511008,511009,511010,511011,521001,521002,521003,521004,521005,521006,521007,521008,521009,521010,531001,531002,531003,531004,531005,531006,531007,531008,531009,531010,551001,551002,551003,551004,551005,551006,551007,551008,520001,520002,520003,520004,520005,520006,520101,520102,520103,520104,520200,520201,520202,520203,520211,520212,520213,520214,610001,610002,610003,630001,640111,640112,640113,640114,640115,640121,640122,640123,640124,640125,640131,640132,640133,640134,640135,640411,640412,640413,640421,640422,640423,640431,640432,640433,640441,660001,660002,660003,660004,660005,660006,701101,702101,703101,702102,703102,704102,703103,704103,705103,704104,705104,704105,705105,701301,701302,701303,702301,702302,702109,702309,703109,703309,704109,704309,705109,705309,840101,840102,840103,840201,840202,840203,840204,840301,840302,840303,840304,840305,840306,840401,840402,840403,840404,840405,840406,900001,900002,900003,900004,900005,900006,900007,900008,900009,900010,900011,900012,900013,900014,900015,900016,900017,900018,900019,900020,900021,900022,900023,900024,900025,900026,900027,900028,900029,900030,900031,900032,900033,900034,900035,900036,900037,"
  },
  {
    "物品名称": "name,string,时光卷轴,钻石,金币,体力,指挥官经验,补给劵,活跃度,英雄经验,时光卷轴箱,灵魂石箱,扫荡劵箱1,扫荡劵箱2,基因段箱,英雄进阶礼包（人族）,英雄进阶礼包（虫族）,英雄进阶礼包（神族）,单兵升级礼包（人族）,单兵升级礼包（虫族）,单兵升级礼包（神族）,单兵强化礼包,钻石堆,钻石袋,钻石包,钻石箱,钻石车,钻石库,人族首充包,虫族首充包,神族首充包,月卡,人工基因,生物结晶,神经元素,一级枪械,一级盔甲,一级装甲板,一级发动机,二级枪械,二级盔甲,二级装甲板,二级发动机,三级枪械,三级盔甲,三级装甲板,三级发动机,四级枪械,四级盔甲,四级装甲板,四级发动机,五级枪械,五级盔甲,五级装甲板,五级发动机,一级生物甲壳,一级复合骨爪,一级强化肌腱,一级增强毒腺,二级生物甲壳,二级复合骨爪,二级强化肌腱,二级增强毒腺,三级生物甲壳,三级复合骨爪,三级强化肌腱,三级增强毒腺,四级生物甲壳,四级复合骨爪,四级强化肌腱,四级增强毒腺,五级生物甲壳,五级复合骨爪,五级强化肌腱,五级增强毒腺,一级能量盾,一级光子刃,一级神族机甲,一级灵子炮体,二级能量盾,二级光子刃,二级神族机甲,二级灵子炮体,三级能量盾,三级光子刃,三级神族机甲,三级灵子炮体,四级能量盾,四级光子刃,四级神族机甲,四级灵子炮体,五级能量盾,五级光子刃,五级神族机甲,五级灵子炮体,征服白色品质物品,征服绿色品质物品,征服蓝色品质物品,征服紫色品质物品,征服橙色品质物品,科技1英雄基因段,科技2英雄基因段,人族科技3英雄基因段,虫族科技3英雄基因段,神族科技3英雄基因段,初级基因段箱,排行奖励1（征服单人模式小周期）,排行奖励2（征服单人模式小周期）,排行奖励3（征服单人模式小周期）,排行奖励4（征服单人模式小周期）,排行奖励5（征服单人模式小周期）,排行奖励6（征服单人模式小周期）,排行奖励7（征服单人模式小周期）,排行奖励1（PVP模式小周期）,排行奖励2（PVP模式小周期）,排行奖励3（PVP模式小周期）,排行奖励4（PVP模式小周期）,排行奖励5（PVP模式小周期）,排行奖励6（PVP模式小周期）,排行奖励7（PVP模式小周期）,排行奖励8（PVP模式小周期）,排行奖励9（PVP模式小周期）,排行奖励10（PVP模式小周期）,排行奖励11（PVP模式小周期）,排行奖励12（PVP模式小周期）,排行奖励13（PVP模式小周期）,排行奖励14（PVP模式小周期）,摩诃星粒,冰晶浮粒,反物质粒,辐射光粒,恒辐射质,星空冰石,蓝晶裂片,毒遗留体,星域晶矿,寒冰矿石,阿贝尔异矿,灵源矿石,生命雾气,寒冰雾气,聚能曜石,矿脉熔炎,牵引磁力,机控镭射磁,本源射线,暗物质源,二级枪械碎片,二级盔甲碎片,二级装甲板碎片,二级发动机碎片,三级枪械碎片,三级盔甲碎片,三级装甲板碎片,三级发动机碎片,四级枪械碎片,四级盔甲碎片,四级装甲板碎片,四级发动机碎片,五级枪械碎片,五级盔甲碎片,五级装甲板碎片,五级发动机碎片,二级生物甲壳碎片,二级复合骨爪碎片,二级强化肌腱碎片,二级增强毒腺碎片,三级生物甲壳碎片,三级复合骨爪碎片,三级强化肌腱碎片,三级增强毒腺碎片,四级生物甲壳碎片,四级复合骨爪碎片,四级强化肌腱碎片,四级增强毒腺碎片,五级生物甲壳碎片,五级复合骨爪碎片,五级强化肌腱碎片,五级增强毒腺碎片,二级能量盾碎片,二级光子刃碎片,二级神族机甲碎片,二级灵子炮体碎片,三级能量盾碎片,三级光子刃碎片,三级神族机甲碎片,三级灵子炮体碎片,四级能量盾碎片,四级光子刃碎片,四级神族机甲碎片,四级灵子炮体碎片,五级能量盾碎片,五级光子刃碎片,五级神族机甲碎片,五级灵子炮体碎片,恒辐射质碎片,星空冰石碎片,蓝晶裂片碎片,毒遗留体碎片,星域晶矿碎片,寒冰矿石碎片,阿贝尔异矿碎片,灵源矿石碎片,生命雾气碎片,寒冰雾气碎片,聚能曜石碎片,矿脉熔炎碎片,牵引磁力碎片,机控镭射磁碎片,本源射线碎片,暗物质源碎片,基因段-猎犬牛仔,基因段-钢铁玫瑰,基因段-不灭城墙,基因段-狡诈射手,基因段-医疗天使,基因段-人类大炮,基因段-战地牧师,基因段-凤凰战机,基因段-精准打击,基因段-放浪公主,基因段-信仰之力,基因段-伦恩,基因段-哈斯科,基因段-雷哲,基因段-欧兹塔,基因段-库汀,基因段-塔兹,基因段-昆西,基因段-欧温,基因段-奈哲尔,基因段-巴伯隆,基因段-姆特,基因段-赛特,基因段-提尔,基因段-阿努比斯,基因段-荷鲁斯,基因段-赫拉斯,基因段-诺伦,基因段-阿克索,基因段-克罗诺斯,基因段-安穆凯,基因段-先知之眼,基因段-暗之奈斯,基因段-光之奈斯,基因段-火异兽,基因段-电猫,基因段-奈芙蒂斯,基因段-阿顿,基因段-水异兽,猎犬牛仔,钢铁玫瑰,不灭城墙,狡诈射手,医疗天使,人类大炮,战地牧师,凤凰战机,精准打击,放浪公主,信仰之力,伦恩,哈斯科,雷哲,欧兹塔,库汀,塔兹,昆西,欧温,奈哲尔,巴伯隆,姆特,赛特,提尔,阿努比斯,荷鲁斯,赫拉斯,诺伦,阿克索,克罗诺斯,安穆凯,先知之眼,暗之奈斯,光之奈斯,火异兽,电猫,奈芙蒂斯,阿顿,水异兽,英雄进阶礼包（人族）副,英雄进阶礼包（虫族）副,英雄进阶礼包（神族）副,单兵升级礼包（人族）副,单兵升级礼包（虫族）副,单兵升级礼包（神族）副,白色单兵装备,绿色单兵装备,蓝色单兵装备,紫色单兵装备,随机基因段,随机科技1基因段,随机科技2基因段,随机科技3基因段,随机人族基因段,随机虫族基因段,随机神族基因段,随机佣兵基因段,低级经验药剂,中级经验药剂,高级经验药剂,扫荡劵,人族白色单兵装备,人族绿色单兵装备,人族蓝色单兵装备,人族紫色单兵装备,人族单兵橙装碎片,虫族白色单兵装备,虫族绿色单兵装备,虫族蓝色单兵装备,虫族紫色单兵装备,虫族单兵橙装碎片,神族白色单兵装备,神族绿色单兵装备,神族蓝色单兵装备,神族紫色单兵装备,神族单兵橙装碎片,人族科技1英雄基因段,人族科技2英雄基因段,人族科技3英雄基因段副,虫族科技1英雄基因段,虫族科技2英雄基因段,虫族科技3英雄基因段副,神族科技1英雄基因段,神族科技2英雄基因段,神族科技3英雄基因段副,佣兵科技1英雄基因段,青铜宝箱,白银宝箱,黄金宝箱,钻石宝箱,界域宝箱,星王宝箱,守卫战奖励组 1,守卫战奖励组 2,守卫战奖励组 3,守卫战奖励组 4,守卫战奖励组 5,守卫战奖励组 6,守卫战奖励组 7,守卫战奖励组 8,守卫战奖励组 9,守卫战奖励组 10,守卫战奖励组 11,守卫战奖励组 12,守卫战奖励组 13,基因段科技1（难度1、2）,基因段科技1（难度3）,基因段科技1（难度4）,基因段科技2（难度3）,基因段科技2（难度4）,难度2单兵装备,难度2基因段,难度3单兵装备,难度3基因段,难度4单兵装备,难度4基因段,难度5单兵装备,难度5基因段,药剂组1,药剂组2,药剂组3,进化材料1组,进化材料2组,进化材料3组,进化材料4组,基因段1组,基因段2组,基因段3组,基因段4组,基因段5组,基因段6组,材料宝箱1,材料宝箱2,材料宝箱3,材料宝箱4,材料宝箱5,材料宝箱6,征服白色颗粒1,征服白色颗粒2,征服白色颗粒3,征服白色颗粒4,征服白色颗粒5,征服绿色颗粒1,征服绿色颗粒2,征服绿色颗粒3,征服绿色颗粒4,征服绿色颗粒5,征服蓝色颗粒1,征服蓝色颗粒2,征服蓝色颗粒3,征服蓝色颗粒4,征服蓝色颗粒5,征服紫色颗粒1,征服紫色颗粒2,征服紫色颗粒3,征服紫色颗粒4,征服紫色颗粒5,征服橙色颗粒1,征服橙色颗粒2,征服橙色颗粒3,征服橙色颗粒4,征服橙色颗粒5,征服绿色颗粒碎片1,征服绿色颗粒碎片2,征服绿色颗粒碎片3,征服蓝色颗粒碎片1,征服蓝色颗粒碎片2,征服蓝色颗粒碎片3,征服紫色颗粒碎片1,征服紫色颗粒碎片2,征服紫色颗粒碎片3,征服橙色颗粒碎片1,征服橙色颗粒碎片2,征服橙色颗粒碎片3,"
  },
  {
    "副类型": "fatherType,int,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,6,6,6,6,6,6,6,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,6,6,6,6,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,"
  }
]
let sourceObj = {}
let optionsArr = []
for(const v of a) {
  for (const key in v){
    //sourceObj[key]=[...new Set(v[key].split(",").slice(2,-1))]
    sourceObj[key]=v[key].split(",").slice(2,-1)
  }
}
let buildOptions =() => {
  for(const k in sourceObj) {
    if (k === '副类型') {
      for (const type of [...new Set(sourceObj[k])]) {
        const optParentObj = {}
        optParentObj.value = type
        switch (type) {
          case '-1':
            optParentObj.label = '消耗品'
            break
          case '1':
            optParentObj.label = '材料'
            break
          case '4':
            optParentObj.label = '基因段'
            break
          case '6':
            optParentObj.label = '消耗品'
            break
          case '7':
            optParentObj.label = '碎片'
            break
        }
        optParentObj.children = []
        optionsArr.push(optParentObj)
        //console.log(type)
      }
    }
  }
  for (const i in  sourceObj['序列']) {
    const optChildObj = {}
    optChildObj.value =  sourceObj['序列'][i]
    optChildObj.label = sourceObj['物品名称'][i]
    for(const ov of optionsArr) {
      if(ov.value === sourceObj['副类型'][i]) {
        ov.children.push(optChildObj)
      }
    }
  }
  //console.log(sourceObj)
  console.log(JSON.stringify(optionsArr))
}
buildOptions()



