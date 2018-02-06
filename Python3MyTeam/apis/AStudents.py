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
from control.CStudents import CStudents

'''
    处理查看学生信息相关的接口，包含学生信息列表和学生信息详情
    函数名确认接口方法，参数确认接口封装内容
    根据apis的key确认具体指向
'''


class AStudents(Resource):
    def get(self, students):
        print(PRINT_API_IS.format(students))

        judgeData = JudgeData()  # 实例化
        cstudents = CStudents()  # 实例化

        apis = {
            "list": "cstudents.students_list()",
            "abo": "cstudents.students_abo()"
        }

        # 判断是否包含该API
        if judgeData.inData(students, apis):
            return eval(apis[students])

        return apis_wrong
