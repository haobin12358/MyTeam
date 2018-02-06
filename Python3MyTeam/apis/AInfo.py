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
from control.CInfo import CInfo

"""
    处理个人信息，接收到的各种邀请、系统通知等等
    包含get、post、delete方法
"""
class AInfo(Resource):
    def __init__(self):
        self.judgeData = JudgeData() # 实例化
        self.cinfo = CInfo()

    def get(self, info):
        print(PRINT_API_IS.format(info))

        apis = {
            "allinfo":"self.cinfo.get_info()"
        }

        if self.judgeData.inData(info, apis):
            return eval(apis[info])

        return apis_wrong

    def post(self, info):

        return apis_wrong

    def delete(self, info):

        return apis_wrong
