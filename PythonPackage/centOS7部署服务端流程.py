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
# 2.安装uwsgi
yum install -y pcre pcre-devel pcre-static # 预安装基础组件
pip install uwsgi # 安装

# 2-1.项目根目录下配置config.ini
[uwsgi]
socket = 127.0.0.1:8001     #启动程序时所使用的地址和端口，通常在本地运行flask项目，
                            #地址和端口是127.0.0.1:5000,
                            #不过在服务器上是通过uwsgi设置端口，通过uwsgi来启动项目，
                            #也就是说启动了uwsgi，也就启动了项目。
chdir = /home/www/     #项目目录
wsgi-file = manage.py      #flask程序的启动文件，通常在本地是通过运行  
                           #python manage.py runserver 来启动项目的
callable = app      #程序内启用的application变量名
processes = 4     #处理器个数
threads = 2     #线程个数
stats = 127.0.0.1:9191      #获取uwsgi统计信息的服务地址

# 2-2.启动uwsgi
uswgi config.ini

# 3.安装nginx
rpm -ivh http://nginx.org/packages/centos/6/noarch/RPMS/nginx-release-centos-6-0.el6.ngx.noarch.rpm
yum install nginx

# 3-1 nginx的一些命令
nginx # 启动
nginx -s stop # 停止
pkill -9 nginx # 强行停止
nginx -t # 查看是否应用配置文件
nginx -v # 查看nginx版本

# 3-2 nginx配置文件，在/etc/nginx目录下，名字为nginx.conf

events {
    worker_connections  1024;
}
http {
    include       mime.types;    
    default_type  application/octet-stream;    
    sendfile        on;    
    keepalive_timeout  65;

    server {
        listen       80;# 默认的web访问端口
        server_name  xxx.xxx.xxx.xxx;# 你的公网ip
        #charset koi8-r;
        access_log  /home/www/WebBlogold/logs/access.log;# 服务器接收的请求日志，
                                                         # 需要在项目文件夹下创建
                                                         # logs文件夹，下同。
        error_log  /home/www/WebBlogold/logs/error.log;  # 错误日志
        location / {
            include        uwsgi_params;# 这里是导入的uwsgi配置

            uwsgi_pass     127.0.0.1:8001;# 需要和uwsgi的配置文件里socket项的地址
                                          # 相同,否则无法让uwsgi接收到请求。
            uwsgi_param UWSGI_PYHOME /home/www/WebBlogold/venv;# python的位置(虚拟环境下)
            uwsgi_param UWSGI_CHDIR  /home/www/WebBlogold;# 项目根目录
            uwsgi_param UWSGI_SCRIPT manage:app;# 启动项目的主程序(在本地上运行
                                                # 这个主程序可以在flask内置的
                                                # 服务器上访问你的项目)

        }
    }
}

# 3-3 查看端口占用情况
netstat -ntlp


# 3-4 后台启动
uwsgi /opt/PythonMyTeam/uwsgiconfig.ini -d /opt/logs/error/error.log -p 8 --threads 10
