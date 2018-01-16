# *- coding:utf8 *-
# 1.安装mysql5.6
# 1）安装libaio
yum search libaio # 检索libaio组件存在
# centOS系统默认包含libaio组件，如缺失
yum install libaio # 安装libaio组件
# 2）检查mysql是否存在
yum list install | grep mysql # 检索mysql是否存在
yum -y remove mysql-*** # 清理查询出来的内容
# 3）下载mysql
wget http://dev.mysql.com/get/mysql-community-release-el7-5.noarch.rpm
# 4）添加MySQL Yum Repository
yum localinstall mysql-community-release-el7-5.noarch.rpm # 安装Repository
yum repolist enabled | grep "mysql.*-community.*" # 验证是否安装成功
# 5） 选择要启动的mysql版本
yum repolist all | grep mysql # 查看版本，默认5.6启动，5.5和5.7禁用
yum-config-manager --disable mysql56-community # 禁用5.6版本
yum-config-manager --enable mysql57-community-dmr # 启动5.7版本
yum repolist enabled | grep mysql # 查看当前启动的mysql版本
# 6） 安装mysql服务
yum install mysql-community-server # 安装mysql
rpm -qi mysql-community-server.x86_64 0:5.6.24-3.el7 # 安装对应服务
whereis mysql # 查看mysql目录
# 7）启动和关闭mysql
systemctl start mysqld # 启动
systemctl stop mysqld # 关闭
systemctl status mysqld # 查看状态
# 8）设置mysql的root密码
mysql # 进入mysql
use mysql; # 使用mysql表
update user set password=password("root") where user='root'; # 更新密码
flush privileges; # 刷新mysql状态
exit; # 退出

# linux安装python组件
# 通过pip安装即可

# linux安装
