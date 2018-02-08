# *- coding:utf8 *-
# 兼容linux系统
import sys
import os
sys.path.append(os.path.dirname(os.getcwd())) # 增加系统路径
#引用python类
from flask_restful import Resource
#引用项目类
from common.JudgeData import JudgeData
from Config.Requests import apis_wrong
from Config.Logs import PRINT_API_IS
from control.CTeams import CTeams
'''
    处理团队信息相关的接口，包含查看团队，创建团队，申请加入团队等功能
    函数名确认接口方法，参数确认接口封装内容
    根据apis的key确认具体指向
'''

class ATeams(Resource):

    def get(self, team):
        print(PRINT_API_IS.format(team))

        judgeData = JudgeData() #实例化
        cteams = CTeams() #实例化

        apis = {
            "allteam": "cteams.teams_list()",
            "teamabo": "cteams.team_abo()",
            "myteam": "cteams.myteams()"
        }
        #判断是否存在该API
        if judgeData.inData(team, apis):
            return eval(apis[team])

        return apis_wrong

    def post(self, team):
        print(PRINT_API_IS.format(team))

        judgeData = JudgeData()  # 实例化
        cteams = CTeams()

        apis = {
            "newteam": "cteams.new_team()",
            "updateteam": "cteams.update_team()",
            "addstudent": "cteams.add_student()",
            "addteacher": "cteams.add_teacher()",
            "invatestudent": "cteams.invate_add_team()",
            "substudent": "cteams.sub_student()",
            "subteacher": "cteams.sub_teacher()",
            "addtask": "cteams.add_task()",
            "subtask": "cteams.sub_task()",
            "updatetask": "cteams.update_task()"
        }
        # 判断是否存在该API
        if judgeData.inData(team, apis):
            return eval(apis[team])

        return apis_wrong

    def delete(self, team):
        print(PRINT_API_IS.format(team))

        judgeData = JudgeData()  # 实例化
        cteams = CTeams()  # 实例化

        apis = {
            "deleteteam": "cteams.delete_team()",
            "deletestudent": "cteams.delete_student()",
            "deletetask": "cteams.delete_task()",
            "deleteteacher": "cteams.delete_teacher()"
        }
        # 判断是否存在该API
        if judgeData.inData(team, apis):
            return eval(apis[team])

        return apis_wrong