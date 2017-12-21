# *- coding:utf8 *-
#引用python类
from flask import request
import json
#引用项目类
from common.JudgeData import JudgeData
from service.STeachers import STeachers
from Config.Requests import param_miss,system_error

#用于处理教师信息相关数据
class CTeachers():
    def __init__(self):
        self.judgeData = JudgeData()
        self.steachers = STeachers()#实例化

    def teachers_list(self):
        args = request.args
        print str(args)
        #判断是否含有参数
        if str(args) == "" or str(args) == {}:
            teachers_list = self.steachers.get_teachers_list()
            if teachers_list is not None:
                return teachers_list
            else:
                return []
        args = json.loads(args)

        #参数成对存在，判断是否缺失
        if not self.judgeData.inData("page_no", args) or not self.judgeData.inData("infor_num", args):
            return param_miss

        page_no = args["page_no"]
        infor_num = args["infor_num"]

        start_num = (page_no - 1) * infor_num

        teachers_list = self.steachers.get_teachers_list_by_start_end(start_num, infor_num)

        if teachers_list is not None:
            return teachers_list#返回体现在很有问题
        else:
            return system_error

    def teachers_abo(self):
        args = request.args
        print str(args)
        #判断是否含有参数
        if str(args) == "" or str(args) == {}:
            return param_miss

        args = json.loads(args)

        if not self.judgeData.inData("Tid", args):
            return param_miss

        tid = args["Tid"]

        teacher_abo = self.steachers.get_teacher_abo_by_tid(tid)
        teacher_use = self.steachers.get_teacher_use_by_tid(tid)

        if teacher_abo is not None:
            return teacher_abo, teacher_abo#返回体存在问题
        else:
            return system_error