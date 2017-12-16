# *- coding:utf8 *-
#引用python类
from flask_restful import Resource
#引用项目类
from common.JudgeData import JudgeData
from Config.Requests import apis_wrong
from Config.Logs import PRINT_API_IS
'''
    处理个人信息相关的接口，包含增、删、改、查个人信息
    函数名确认接口方法，参数确认接口封装内容
    根据apis的key确认具体指向
'''
class APersonal(Resource):
    def get(self, personal):
        print PRINT_API_IS.format(personal)

        judgeData = JudgeData()  # 实例化

        apis = {
            "findall": ""
        }
        # 判断是否包含该API
        if judgeData.inData(personal, apis):
            return apis[personal]

        return apis_wrong
    def post(self, personal):
        print "api is " + personal + " !"

        judgeData = JudgeData()  # 实例化

        apis = {
            "new": "",
            "stech_new": "",
            "scuse_new": "",
            "update": "",
            "stech_update": "",
            "scuse_update": ""
        }
        # 判断是否包含该API
        if judgeData.inData(personal, apis):
            return apis[personal]

        return apis_wrong
    def delete(self, personal):
        print "api is " + personal + " !"

        judgeData = JudgeData()  # 实例化

        apis = {
            "stech_delete": "",
            "scuse_delete": ""
        }
        # 判断是否包含该API
        if judgeData.inData(personal, apis):
            return apis[personal]

        return apis_wrong