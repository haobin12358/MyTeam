# *- coding:utf8 *-
#引用python类
from flask import request
import json

#引用项目类
from common.JudgeData import JudgeData
from service.STeams import STeams
from Config.Requests import system_error, param_miss

#用于处理团队相关数据
class CTeams():
    def __init__(self):
        self.steams = STeams()
        self.judgeData = JudgeData()#实例化

    def teams_list(self):
        pass

    def myteam_list(self):
        pass

    def new_myteam(self):
        pass