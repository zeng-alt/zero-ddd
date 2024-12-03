### 不要在同一个事务中切换多个租户，只能同一个事务中使用同一个租户，如果有需求请使用分布式事务

1. tenant-management是管理租户的模块[database, schema]
2. database-tenant 是独立数据库，要引入tenant-management
3. schema-tenant 是同一个数据库不同schema, 要引入tenant-management
4. sharded-tenant 同一数据库同一schema, 通过表字段进行区别租户

这三个模块是互斥，不能同时引入并开启