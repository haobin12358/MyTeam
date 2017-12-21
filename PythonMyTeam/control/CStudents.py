# *- coding:utf8 *-
#引用Python类
from flask import request
import json
#引用项目类
from common.JudgeData import JudgeData
from service.SStudents import SStudents
from Config.Requests import param_miss, system_error
#用于处理学生信息相关的数据
class CStudents():
    def __init__(self):
        self.judgeData = JudgeData()
        self.sstudents = SStudents() #实例化

    def students_list(self):
        args = request.args
        print str(args)
        #判断是否含有参数
        if str(args) == "" or str(args) == {}:
            students_list = self.sstudents.get_students_list()
            if students_list is not None:
                return students_list
            else:
                return []
        args = json.loads(args)

        #参数成对存在，判断是否缺失
        if not self.judgeData.inData("page_no", args) or not self.judgeData.inData("infor_num", args):
            return param_miss

        page_no = args["page_no"]
        infor_num = args["infor_num"]

        start_num = (page_no - 1) * infor_num

        students_list = self.sstudents.get_students_list_by_start_end(start_num, infor_num)

        if students_list is not None:
            return students_list#返回体现在很有问题
        else:
            return system_error

    def students_abo(self):
        args = request.args
        print str(args)
        #判断是否有参数
        if str(args) == "" or str(args) == {}:
            return param_miss

        args = json.loads(args)
        #判断参数是否缺失
        if not self.judgeData.inData("Sid", args):
            return param_miss

        sid = args["Sid"]

        student_abo = self.sstudents.get_student_abo_by_sid(sid)
        student_tech = self.sstudents.get_student_tech_by_sid(sid)
        student_use = self.sstudents.get_student_use_by_sid(sid)

        if student_abo is not None:
            return student_abo, student_tech, student_use #返回体很有问题
        else:
            return system_error

