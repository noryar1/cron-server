# cron-server
通用的定时任务服务，基于Quartz实现的分布式定时任务触发服务，任务触发与业务逻辑解耦，也就是注册的定时任务是实际处理业务逻辑的Http URL+Request Type+Params三元素组成。

# 整体架构
![architecture](https://raw.githubusercontent.com/noryar1/cron-server/main/public/images/architecture.png)

