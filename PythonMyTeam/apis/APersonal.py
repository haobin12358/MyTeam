# *- coding:utf8 *-
#引用python类
from flask_restful import Resource
#引用项目类
from common.JudgeData import JudgeData
from Config.Requests import apis_wrong
from Config.Logs import PRINT_API_IS
from control.CPersonal import CPersonal
'''
    处理个人信息相关的接口，包含增、删、改、查个人信息
    函数名确认接口方法，参数确认接口封装内容
    根据apis的key确认具体指向
'''
class APersonal(Resource):
    def get(self, personal):
        print PRINT_API_IS.format(personal)

        judgeData = JudgeData() # 实例化
        cpersonal = CPersonal() # 实例化

        apis = {
            "findall": "cpersonal.myinfor()"
        }
        # 判断是否包含该API
        if judgeData.inData(personal, apis):
            return eval(apis[personal])

        return apis_wrong
    def post(self, personal):
        print "api is " + personal + " !"

        judgeData = JudgeData()  # 实例化
        cpersonal = CPersonal()  # 实例化

        apis = {
            "new": "cpersonal.create_myinfor()",
            "stech_new": "cpersonal.create_mytech()",
            "scuse_new": "cpersonal.create_myuse()",
            "update": "cpersonal.update_myinfor()",
            "stech_update": "cpersonal.update_mytech()",
            "scuse_update": "cpersonal.update_myuse()"
        }
        # 判断是否包含该API
        if judgeData.inData(personal, apis):
            return eval(apis[personal])

        return apis_wrong
    def delete(self, personal):
        print "api is " + personal + " !"

        judgeData = JudgeData()  # 实例化
        cpersonal = CPersonal()  # 实例化

        apis = {
            "stech_delete": "cpersonal.delete_mytech()",
            "scuse_delete": "cpersonal.delete_myuse()"
        }
        # 判断是否包含该API
        if judgeData.inData(personal, apis):
            return eval(apis[personal])

        return apis_wrong