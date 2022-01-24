远程仓库已整合完毕, 个人信息凭证存放到本地 setting.xml, deploy时将通过 SNAPSHOT以及 RELEASE
区分上传至那个仓库.
基于后期需要和前端伙伴但又懒写 api文档, 因此在项目中集成了 swagger, 这里顺便记录下使用 3.0.0版本下
页面打不开可能存在兼容问题(使用稳定一版 2.9.2) swagger似乎也有适配 starter一版了, 但写法和拆分单独引进的不太一样, 
后期尝试整合 starter.

struct:
原本:
app -> implementation -> infrastructure
implementation -> domain
优化成:
app -> implementation -> infrastructure -> domain

优化后的好处: 可以对 domain层进行细致抽象, domain层解耦的同时也不脱离于其它层存在