# *- coding:utf8 *-
# 兼容linux系统
import sys
import os
sys.path.append(os.path.dirname(os.getcwd())) # 增加系统路径
# 引用python类
from flask_restful import Resource
# 引用项目类
from common.JudgeData import JudgeData
from Config.Requests import apis_wrong, system_error
from Config.Logs import PRINT_API_IS
from control.CCompetitions import CCompetitions


'''
    处理查看竞赛信息相关的接口，包含竞赛信息列表和学生信息详情
    函数名确认接口方法，参数确认接口封装内容
    根据apis的key确认具体指向
'''


class ACompetitons(Resource):
    @staticmethod
    def get(competitions):
        print(PRINT_API_IS.format(competitions))

        judgeData = JudgeData()  # 实例化
        ccompetitions = CCompetitions()  # 实例化
        apis = {
            "list": "ccompetitions.competitions_list()",  # 获取所有竞赛的简略信息
            "abo": "ccompetitions.competitions_abo()"  # 详细信息
        }

        # 判断是否存在该API
        if judgeData.inData(competitions, apis):
            return eval(apis[competitions])

        return apis_wrong
