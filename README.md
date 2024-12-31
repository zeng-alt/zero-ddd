## 旨在打造一款支持CQRS的功能全备的重量级架构


1. 实现@SwithTenant切换租户,使用可插拔式AOP
2. 变更Auth服务，使其登录时不请求main服务
3. 使用redis实现ABAC数据中台
4. 添加验证码过滤器使用web