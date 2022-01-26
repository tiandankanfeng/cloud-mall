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

优化后的好处: 可以对 domain层进行细致抽象, domain层解耦的同时也不脱离于其它层存在

涉及到明文存储：初步想法，将公钥存储到数据库中去，秘钥即为数据库名称自身.
数据库：docker安装，一分钟即 ok.
