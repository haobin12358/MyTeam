# *- coding:utf8 *-
# 引用python类
from flask import Flask
import flask_restful

# 引用项目类
from apis.AUsers import AUsers
from apis.APersonal import APersonal
from apis.AStudents import AStudents
from apis.ATeachers import ATeachers
from apis.ACompetitions import ACompetitons
from apis.ATeams import ATeams
from apis.AInfo import AInfo

# 实例化flask启动器
app = Flask(__name__)
# 创建继承flask_restful的api接口
api = flask_restful.Api(app)

# 定义实际接口
api.add_resource(AUsers, "/users/<string:users>")
api.add_resource(APersonal, "/personal/<string:personal>")
api.add_resource(AStudents, "/students/<string:students>")
api.add_resource(ATeachers, "/teachers/<string:teachers>")
api.add_resource(ACompetitons, "/competitions/<string:competitions>")
api.add_resource(ATeams, "/team/<string:team>")
api.add_resource(AInfo, "/info/<string:info>")

# 启动方法
if __name__ == '__main__':
    app.run('0.0.0.0', 7443, debug=True)
