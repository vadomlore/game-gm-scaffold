# GM文件结构

GM工具由3部分组成,包括gm vue客户端, gm账号 服务器， 游戏服http 端口((此处没有包含游戏服务器),游戏服务器及其与gm vue客户端的数据由用户自己定义。

# 运行程序

## 客户端
npm install

npm run dev

如果要使用Mockup数据查看页面效果，需要打开
game-gm-scaffold\client\src\main.js中的

```

    // import './mock'  // 该项目所有请求使用mockjs模拟

```

以及完善mockup中的请求, mockup中的请求位于

game-gm-scaffold\client\src\mock


# 服务器

./mvnw spring-boot:run

或者./mvnw clean package 

然后运行

java -jar target/{application-name}.jar

服务器使用的是OAuth的认证系统认证


该项目来修改自 <http://panjiachen.github.io/vue-element-admin>
