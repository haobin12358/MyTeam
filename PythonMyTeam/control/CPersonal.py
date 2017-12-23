# *- coding:utf8 *-
#引用python类
from flask import request
#引用项目类
from common.JudgeData import JudgeData
from service.SPersonal import SPersonal
from Config.Requests import param_miss,system_error

#用于处理个人信息相关数据
class CPersonal():
    def __init__(self):
        self.judgeData = JudgeData()
        self.spersonal = SPersonal()

    def myinfor(self):
        pass

    def create_myinfor(self):

        pass

    def create_mytech(self):
        pass

    def create_myuse(self):
        pass

    def update_myinfor(self):
        pass

    def update_mytech(self):
        pass

    def update_myuse(self):
        pass

    def delete_mytech(self):
        pass

    def delete_myuse(self):
        pass