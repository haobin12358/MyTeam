# *- coding:utf8 *-
# 兼容linux系统
import sys
import os
sys.path.append(os.path.dirname(os.getcwd())) # 增加系统路径
# 引用python类
from flask_restful import Resource
# 引用项目类
from common.JudgeData import JudgeData
from Config.Requests import apis_wrong
from Config.Logs import PRINT_API_IS
from control.CTeachers import CTeachers
'''
    处理查看教师信息相关的接口，包含教师信息列表和学生信息详情
    函数名确认接口方法，参数确认接口封装内容
    根据apis的key确认具体指向
'''

class ATeachers(Resource):

    def get(self, teachers):
        print(PRINT_API_IS.format(teachers))

        judgeData = JudgeData()  # 实例化
        cteachers = CTeachers()  # 实例化

        apis = {
            "list": "cteachers.teachers_list()",
            "abo": "cteachers.teachers_abo()"
        }
        # 判断是否包含该API
        if judgeData.inData(teachers, apis):
            return eval(apis[teachers])

        return apis_wrong
