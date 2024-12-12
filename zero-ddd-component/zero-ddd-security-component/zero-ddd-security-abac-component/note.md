1. 基于角色的 ABAC（RBAC）：将 ABAC 与 RBAC 结合，实现基于角色的访问控制。
2. 基于规则的 ABAC：使用规则语言定义策略，提高策略的表达能力。
3. 基于模型的 ABAC：使用模型来描述策略，实现更高级别的抽象和自动化。

pre步骤
1. 通过@AbacPreAuthorize注解，在方法上添加权限验证, 找到AuthorizationManagerBeforeMethodInterceptor
2. 在AuthorizationManagerBeforeMethodInterceptor的PreAuthorizeExpressionAttributeRegistry属性拿到对应的上下文和表达式
    * 通过AbacPreAuthorize注解#resourceType和#value 拿到对应的资源表达式管理器（List<ExpressionAttribute>），可以获得表达式
    * 通过AbacPreAuthorize注解#value 拿到对应的上下文管理器(可能有多个Map<k, List<K>)，可以获得对应的资源上下文
    * 通过PreAuthorizeExpressionAttributeRegistry#MethodSecurityExpressionHandler可以拿到方法参数和用户信息并加入上下文中
    * 通过AuthorizationManagerBeforeMethodInterceptor拿到我们的环境上下文