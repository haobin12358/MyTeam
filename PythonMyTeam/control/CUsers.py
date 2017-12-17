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

    #实现注册的数据处理
    def register(self):

        form = request.data #获取前端发送的body体
        print str(form)
        #判断body体不为空
        if str(form) == "" or str(form) == "[]":
            return param_miss
        form = json.loads(form)

        judgeData = JudgeData() #实例化
        #判断必要参数存在
        if not judgeData.inData("Uname",form) or not judgeData.inData("Upwd",form) \
            or not judgeData.inData("Utype", form):
            return param_miss
        susers = SUsers() #实例化

        list_uname = susers.get_all_user_name() #获取数据库中存在的uname
        #判断uname唯一
        if form["Uname"] in list_uname:
            return repeated_name

        is_register = susers.add_user(uuid.uuid4(),form["Uname"],form["Upwd"],form["Utype"])#写入数据库
        #判断写入数据库的响应
        if is_register == 0:
            return register_ok
        elif is_register == 1:
            return system_error
