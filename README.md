# SpringBoot Demo
## Application为启动类

访问地址是：http://localhost:8088/psi/index.html

src目录介绍：

annotation 自定义注解

common 公共部分，一些工具类、token工具、ueditor工具包

config 系统的配置，包括reids、apache MQ配置

dao 数据库操作层

filter 拦截器，日志记录、校验权限等

services rest服务定义

task 定时任务

vo 数据库实体类

resources目录介绍：

config 配置文件，根据环境不同分为3套，分别是dev、test、prod

mapper mybatis mapper文件

public html文件，没啥用

templates 模板文件，一般导出Excel用到，目前都是没用的文件

application.yml springboot配置文件，一般是直接修改config的文件，不需要改动这个文件

banner.txt 没啥用

config.json ueditor 配置文件

log4j2.yml log配置文件

log4j2-orig.yml 原来log配置文件

