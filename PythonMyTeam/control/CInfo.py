# *- coding:utf8 *-
import sys
import os
sys.path.append(os.path.dirname(os.getcwd())) # 增加系统路径
# 引用python类
from flask import request
import json
import uuid
from service.SStudents import SStudents
from service.SCompetitions import SCompetitions
from Config.Requests import param_miss, system_error
from service.SInfor import SInfor
from service.STeams import STeams

# 引用项目类
from common.JudgeData import JudgeData

class CInfo():
    def __init__(self):
        self.judgeData = JudgeData()
        self.student = SStudents()
        self.competition = SCompetitions()
        self.info = SInfor()
        self.team = STeams()

    def get_info(self):
        info_list = []

        args = request.args.to_dict()
        if not self.judgeData.inData("Uid", args):
            return param_miss

        uid = args["Uid"]
        model_info_list = self.info.get_info_by_uid2(uid)
        print str(model_info_list)
        for row in model_info_list:
            info_list_item = {}
            print str(row)
            info_list_item["Pid"] = row.Pid
            if row.TEid != None:
                info_list_item["TEname"] = self.team.get_tename_by_teid(row.TEid)
            else:
                info_list_item["TEname"] = None
            if row.Cid != None:
                cname_cno = self.competition.get_competitions_name_level_no_by_cid(row.Cid)
                print cname_cno
                info_list_item["Cname"] = cname_cno.Cname
                info_list_item["Cno"] = cname_cno.Cno
                info_list_item["Clevel"] = cname_cno.Clevel
            else:
                info_list_item["Cname"] = None
                info_list_item["Cno"] = None
                info_list_item["Clevel"] = None
            info_list_item["Sname"] = self.student.get_sname_by_sid(self.student.get_sid_by_uid(row.Uid))
            info_list_item["Pmessage"] = row.Pmessage
            info_list_item["Ptype"] = row.Ptype
            info_list.append(info_list_item)
        return info_list
