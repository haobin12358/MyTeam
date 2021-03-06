# *- coding:utf8 *-
# 引用python类
from flask import Flask
import flask_restful

# 引用项目类
from apis.AUsers import AUsers


# 实例化flask启动器
myteam = Flask(__name__)
# 创建继承flask_restful的api接口
api = flask_restful.Api(myteam)

# 定义实际接口
api.add_resource(AUsers, "/users/<string:users>")


# 启动方法
if __name__ == '__main__':
    myteam.run('0.0.0.0', 1901, debug=True)