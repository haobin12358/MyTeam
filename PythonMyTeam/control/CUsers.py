# *- coding:utf8 *-
#引用python类
from flask import request
import json
import uuid
#引用项目类
from common.JudgeData import JudgeData
from service.SUsers import SUsers
from Config.Requests import register_ok,system_error,repeated_name,wrong_uname,wrong_upwd,param_miss,error_upwd,login_ok
#用于处理用户相关的数据
class CUsers():
    def __init__(self):
        self.judgeData = JudgeData() #全局实例化
        self.susers = SUsers()

    #实现登录的数据处理
    def login(self):
        form = request.data #获取前端发送的body体
        print str(form)
        #判断body体不为空
        if str(form) == "" or str(form) == "[]":
            return param_miss
        form = json.loads(form)
        #判断必要存在的参数
        if not self.judgeData.inData("Uname",form) or not self.judgeData.inData("Upwd",form):
            return param_miss

        list_uname = self.susers.get_all_user_name() #获取数据库中存在的uname
        #判断uname存在
        if form["Uname"] not in list_uname:
            return
        Upwd = self.susers.get_upwd_by_uname(form["Uname"]) #根据用户名获取数据库的密码
        #判断session是否异常
        if Upwd == False:
            return system_error
        #判断用户名与密码匹配
        if Upwd != form["Upwd"]:
            return error_upwd

        Uid = self.susers.get_uid_by_uname(form["Uname"]) #根据用户名获取数据库的id
        Utype = self.susers.get_utype_by_uid(Uid)

        login_ok["messages"]["Uid"] = Uid #将获取到的内容放置到body中
        login_ok["messages"]["Utype"] = Utype

        return login_ok

    #实现注册的数据处理
    def register(self):

        form = request.data #获取前端发送的body体
        print request.values
        #print str(form)
        #判断body体不为空
        if str(form) == "" or str(form) == "[]":
            return param_miss
        print form
        form = json.loads(form)

        #判断必要参数存在
        if not self.judgeData.inData("Uname",form) or not self.judgeData.inData("Upwd",form) \
            or not self.judgeData.inData("Utype", form):
            return param_miss

        list_uname = self.susers.get_all_user_name() #获取数据库中存在的uname
        #判断session是否异常
        if list_uname == False:
            return system_error
        #判断uname唯一
        if form["Uname"] in list_uname:
            return repeated_name

        is_register = self.susers.add_user(uuid.uuid4(),form["Uname"],form["Upwd"],form["Utype"])#写入数据库
        #判断写入数据库的响应
        if is_register:
            return register_ok
        else:
            return system_error
