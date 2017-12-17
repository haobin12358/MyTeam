# *- coding:utf8 *-
#引用python类
from flask import request
import json
import uuid
#引用项目类
from common.JudgeData import JudgeData
from service.SUsers import SUsers
from Config.Requests import register_ok,system_error,repeated_name,wrong_uname,wrong_upwd,param_miss
#用于处理用户相关的数据
class CUsers():
    def login(self):
        return

    def register(self):

        form = request.data
        print str(form)
        if str(form) == "" or str(form) == "[]":
            return param_miss
        form = json.loads(form)

        judgeData = JudgeData()
        if not judgeData.inData("Uname",form) or not judgeData.inData("Upwd",form) \
            or not judgeData.inData("Utype", form):
            return param_miss
        susers = SUsers()

        is_register = susers.register(uuid.uuid4(),form["Uname"],form["Upwd"],form["Utype"])

        if is_register == 0:
            return register_ok
        elif is_register == 1:
            return system_error
        elif is_register == 2:
            return repeated_name