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
from control.CUsers import CUsers
from Config.Logs import PRINT_API_IS

'''
    处理用户相关的接口，包含注册与登录
    函数名确认接口的方法，参数确认接口的封装内容
    根据apis中的key来确认具体指向
'''
class AUsers(Resource):
    def post(self, users):
        print(PRINT_API_IS.format(users))

        judgeData = JudgeData()#实例化
        control_user = CUsers()#实例化

        apis = {
            "register":"control_user.register()",
            "login":"control_user.login()"
        }

        #判断是否包含该API
        if judgeData.inData(users, apis):
            return eval(apis[users])

        return apis_wrong