# *- coding:utf8 *-
"""
1.对于各种service的启动有不同的处理，在比较低版本的linux系统中，启动一个服务，如myssql
只需要 service start mysql即可，在centOS7中，命令修改如下
/bin/systemctl start mysql
"""

"""
2.shell脚本的开机启动项，需要crond服务
服务参数命令需要先配置权限
chmod -R 777 [shell脚本]

crond同样具有对应的状态，start/stop/restart/reload/status
crond依赖包，需要yum安装vixie-cron和crontabs

crontab -e启动编译器
编译器中写入命令
0 1 * * * root [shell脚本绝对路径]
第二个数字表示每天凌晨1点执行
"""

"""
3.shell脚本的编写，通过if fi来进行文件的判断
-x判断是否有权限
-d判断路径是否存在
-f判断文件是否存在
-n判断变量是否有值
参数调用需要加$符号
date命令可以获取时间
+%Y%m%d表示年月日
+%F等同于+%Y-%m-%d
+%y为二位的年份
-d yesterday 表示昨天
-d -nday 表示n天前
"""
