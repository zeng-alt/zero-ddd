#### 分为五种工程
1. 基于内存缓存 (caffeine)
2. 基于远程缓存 (redis)
3. 基于内存和远程缓存的二级缓存 (caffeine-redis)
    * core层
    * api层
    * 实现层（暂时为caffeine-redis实现）
    * 测试层
4. 对存储的java bean序列化(jackson实现)
5. 一些component要操作缓存的工程