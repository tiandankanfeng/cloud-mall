 Cloud-Mall:基于用户肖像的电商系统
prd:https://www.processon.com/view/link/61fe5f9e5653bb06de15ad19

远程仓库已整合完毕, 个人信息凭证存放到本地 setting.xml, deploy时将通过 SNAPSHOT以及 RELEASE
区分上传至那个仓库.
基于后期需要和前端伙伴但又懒写 api文档, 因此在项目中集成了 swagger, springfox官方已经支持 starter方式引入了, 并不存在兼容问题仅仅是
swagger-ui的页面链接发生了更变而已, 官方：https://springfox.github.io/springfox/docs/snapshot/#changes-in-swagger-ui
knife是对 swagger-ui的再优化版本, 确实对 ui功能做了很大增强，官方: https://doc.xiaominfo.com/knife4j/documentation/,
从此 api文档不再愁: http://localhost:8000/doc.html

struct:
原本:
app -> implementation -> infrastructure
implementation -> domain
优化成:
app -> implementation -> infrastructure -> domain
最后更改：
app -> implementation -> domain -> infrastructure

把 domain层进行抽取, 但是这么做存在一些纠结点：domain层离不开表实体, 但实体是存在于基础设施层,
实体层存在的地方便是 mybatis-plus归属, 此时只有两种方案：将实体以及 plus依赖存储到领域层, 但领域层
应该是脱离于基础设施专注于对象模型而存在, 因此我不会这么去做,
所以更改为了最后一种：更变依赖设施架构层次


涉及到明文存储：关系型数据库使用 druid，其它敏感字符使用 `jasypt ENC`, 后期将公钥从代码中移除，规避风险
数据库：docker安装，一分钟即 ok.

Session说明：用户注册, 登陆成功后, 前端会将用户信息相关存储在 cookie，服务端根据自身敏感业务需要来去获取用户相关信息
切面中会去校验用户信息, 以及对返回值的统一封装.

使用 `spring-cloud-function`函数式编写工具方法, 抽取重复功能代码以及优化程序性能

已启用 ZGC：
`nohup java -jar -XX:StartFlightRecording -XX:+UseZGC -Xmx2g  cloud-mall-app-1.0.0-SNAPSHOT.jar &`
```shell
[root@VM-12-8-centos workplace]# jinfo -flags 535139
VM Flags:
-XX:CICompilerCount=2 -XX:+FlightRecorder -XX:InitialHeapSize=31457280 
-XX:MaxHeapSize=10737418240 -XX:MinHeapDeltaBytes=2097152 -XX:MinHeapSize=8388608 
-XX:NonNMethodCodeHeapSize=5826188 -XX:NonProfiledCodeHeapSize=122916026 -XX:ProfiledCodeHeapSize=122916026 
-XX:ReservedCodeCacheSize=251658240 -XX:+SegmentedCodeCache -XX:SoftMaxHeapSize=10737418240 
-XX:StartFlightRecording=dumponexit=false -XX:+UnlockExperimentalVMOptions -XX:+UseCompressedClassPointers 
-XX:-UseCompressedOops -XX:+UseZGC
```

分布式任务调度器选型：xxx-job, url:`http://sky.liangye-xo.xyz:8050/xxl-job-admin/`
也有基于 redis实现了下单机下的调度, 若区使用需保证幂等消费

mysql:8.0
权限审批流：Yearning
link: http://sky.liangye-xo.xyz:10010/#/login
用户名: yuandao
密码:Wu913428!

./Yearning run --push "sky.liangye-xo.xyz" -port "8888"
./Yearning -s -b "sky.liangye-xo.xyz" -p "8888"

如何做到用户肖像的构建? 肖像, 并非就是一幅画, 其实就是对用户进行标识, 通过标识我们能够知道此用户感兴趣的商品是什么?
因此关键在于肖像的构建, 本文使用标签的形式完成用户肖像的构建
为用户添加标签时机：
a. 识别用户购物车中商品标签
b. 统计用户页面上商品点击次数
c. 定时任务实现上述两者

标签应用时机：
a. 首页展示用户商品信息
用户肖像标签优先级排序
优先级高的 tag对应商品有限展示, 但考虑到如果项目真正搞起来后商品信息过多的情况, 不可能在首页只去展示一个
tag对应的所有商品, 应加以控制， 指定一个数目下进行权重比显示相较而言才是比较靠谱的一种做法.
b. 用户进行搜索时对搜索到的商品进行优先级排序.

规范下返回：ResultDto

监控方面：
1.引入了 `druid`对数据源进行管理并且实时监控慢 sql
2.引入了 `arthas`对 Java程序进行监控
3.todo - 基于 RateLimiter实现网关层面的监控.

```shell
[INFO] arthas-boot version: 3.5.5
[INFO] Process 3014072 already using port 3658
[INFO] Process 3014072 already using port 8563
[INFO] Found existing java process, please choose one and input the serial number of the process, eg : 1. Then hit ENTER.
* [1]: 3014072 cloud-mall-app-1.0.0-SNAPSHOT.jar
  [2]: 4113391 arthas-boot.jar
  [3]: 164410 xxl-job-admin-2.3.1-SNAPSHOT.jar
  1
  [INFO] arthas home: /root/.arthas/lib/3.5.6/arthas
  [INFO] The target process already listen port 3658, skip attach.
  [INFO] arthas-client connect 127.0.0.1 3658
  ,---.  ,------. ,--------.,--.  ,--.  ,---.   ,---.
  /  O  \ |  .--. ''--.  .--'|  '--'  | /  O  \ '   .-'
  |  .-.  ||  '--'.'   |  |   |  .--.  ||  .-.  |`.  `-.
  |  | |  ||  |\  \    |  |   |  |  |  ||  | |  |.-'    |
  `--' `--'`--' '--'   `--'   `--'  `--'`--' `--'`-----'

wiki       https://arthas.aliyun.com/doc
tutorials  https://arthas.aliyun.com/doc/arthas-tutorials.html
version    3.5.6
main_class
pid        3014072
time       2022-03-11 11:29:35
```

基于 boot引入 Nacos: http://sky.liangye-xo.xyz:8848/nacos/

前端仓库：`https://gitee.com/jay0722/fgt-system/tree/master/vue-element-admin-master/src/views`













































