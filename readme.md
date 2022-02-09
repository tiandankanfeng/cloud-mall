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

后期将启用 ZGC.

分布式任务调度器选型：xxx-job, url:`http://sky.liangye-xo.xyz:8050/xxl-job-admin/`

如何做到用户肖像的构建? 肖像, 并非就是一幅画, 其实就是对用户进行标识, 通过标识我们能够知道此用户感兴趣的商品是什么?
因此关键在于肖像的构建, 本文使用标签的形式完成用户肖像的构建
为用户添加标签时机：
a. 识别用户购物车中商品标签
b. 统计用户页面上商品点击次数
c. 定时任务实现上述两者

标签应用时机：
a. 首页展示用户商品信息
b. 用户进行搜索时对搜索到的商品进行优先级排序.

规范下返回：ResultDto