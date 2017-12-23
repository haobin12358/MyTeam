# *- coding:utf8 *-
# 引用Python类
from flask import request
import json
# 引用项目类
from common.JudgeData import JudgeData
from service.SStudents import SStudents
from Config.Requests import param_miss, system_error
from common.get_model_return_list import get_model_return_list


# 用于处理学生信息相关的数据
class CStudents():
    def __init__(self):
        self.judgeData = JudgeData()
        self.sstudents = SStudents()  # 实例化

    def students_list(self):
        """
        获取所有学生的简略信息
        :return:
        """
        if not self.sstudents.status:  # 校验数据库是否连接异常
            return system_error
        args = request.args.to_dict()
        # 判断是否含有参数
        if not args:
            return self.get_students_list()
        try:
            # 参数成对存在，判断是否缺失,并判断具体内容是否合法，非法或为空均返回-1
            page_num, page_size = self.judgeData.check_page_params(args)
            start_num = (page_num - 1) * page_size
            return self.get_students_list(start_num, page_size)
        except Exception as e:
            print e.message
            return system_error

    def students_abo(self):
        """
        获取学生的的详细信息，包括竞赛信息，技能信息
        必须有参数Sid
        :return:
        """
        if not self.sstudents.status:
            return system_error
        args = request.args.to_dict()
        # 判断是否含有参数
        if not args:
            return param_miss

        if not self.judgeData.inData("Sid", args):  # 校验参数中是否存在Sid，如果没有，抛出异常
            return param_miss

        try:
            sid = args["Sid"]
            from models.model import Students
            from models.model import STechs
            from models.model import SCuse
            # 获取数据库中数据
            # 获取学生的基础信息
            student_abo = get_model_return_list(self.sstudents.get_student_abo_by_sid(sid), Students)
            # 获取学生的技能信息
            student_tech = get_model_return_list(self.sstudents.get_student_tech_by_sid(sid), STechs)
            # 获取学生的竞赛信息
            student_use = get_model_return_list(self.sstudents.get_student_use_by_sid(sid), SCuse)
            # 拼装返回结构体
            student_abo[0]["STech"] = student_tech
            student_abo[0]["SCUse"] = student_use
            return student_abo
        except Exception as e:
            # 防止系统的莫名错误
            print e.message
            return system_error

    def get_students_list(self, start_num=-1, page_size=-1):
        """
        判断是否是分页查询，以此来执行不同的数据库操作动作。获取不同的数据内容
        :param start_num: 分页查询的页数 如果不是分页查询，该值为2
        :param page_size: 分页查询的每页记录数，如果不是分页查询，该值为-1
        :return:
        """
        if start_num >= 0 and page_size > 0:
            students_list = self.sstudents.get_students_list_by_start_end(start_num, page_size)
        else:
            students_list = self.sstudents.get_students_list()
        # [students_list1, 2, 3...]
        if isinstance(students_list, list) and students_list:
            from models.model import Students
            return get_model_return_list(students_list, Students)
        else:
            return []
