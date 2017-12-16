# *- coding:utf8 *-
#引用python类
from flask_restful import Resource
#引用项目类
from common.JudgeData import JudgeData
from Config.Requests import apis_wrong
from Config.Logs import PRINT_API_IS
'''
    处理团队信息相关的接口，包含查看团队，创建团队，申请加入团队等功能
    函数名确认接口方法，参数确认接口封装内容
    根据apis的key确认具体指向
'''

class ATeams(Resource):

    def get(self, team):
        print PRINT_API_IS.format(team)

        judgeData = JudgeData() #实例化

        apis = {
            "myteam": "",
            "teams": ""
        }
        #判断是否存在该API
        if judgeData.inData(team, apis):
            return eval(apis[team])

        return apis_wrong

    def post(self, team):
        print PRINT_API_IS.format(team)

        judgeData = JudgeData()  # 实例化

        apis = {

        }
        # 判断是否存在该API
        if judgeData.inData(team, apis):
            return eval(apis[team])

        return apis_wrong

    def delete(self, team):
        print PRINT_API_IS.format(team)

        judgeData = JudgeData()  # 实例化

        apis = {
            "myteam": ""
        }
        # 判断是否存在该API
        if judgeData.inData(team, apis):
            return eval(apis[team])

        return apis_wrong