# *- coding:utf8 *-
# 兼容linux系统
import sys
import os
sys.path.append(os.path.dirname(os.getcwd())) # 增加系统路径
# 引用Python类
from flask import request
# 引用项目类
from common.JudgeData import JudgeData
from service.SStudents import SStudents
from Config.Requests import param_miss, system_error, search_student_list_success, search_student_abo_success, param_notright
from common.get_model_return_list import get_model_return_list
from common.get_str import get_str
from common.MyException import ParamsNotExitError


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
        params = []
        try:
            # 参数成对存在，判断是否缺失,并判断具体内容是否合法，非法或为空均报错
            page_num, page_size = self.judgeData.check_page_params(args)
            from models.model import Students
            if "start" in args:
                params.append(Students.Sgrade >= args.get("start"))
            if "end" in args:
                params.append(Students.Sgrade <= args.get("end"))
            if "Sname" in args:
                name = get_str(args, "Sname")
                params.append(Students.Sname.like("%{0}%".format(name)))
            if "Sschool" in args:
                school = get_str(args, "Sschool")
                params.append(Students.Sschool.like("%{0}%".format(school)))

            page_num, count = JudgeData.check_page_value(page_num, page_size, "Students", params)
            start_num = (page_num - 1) * page_size
            search_student_list_success["student_list"] = self.get_students_list(start_num, page_size, params)
            search_student_list_success["count"] = count
            search_student_list_success["page_num"] = page_num
            return search_student_list_success
        except(ParamsNotExitError)as e:
            print(e)
            return param_miss
        except ValueError as e:
            print(e)
            return param_notright.fromkeys(e)
        except Exception as e:
            print(e)
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
        # 判断是否含有参数并校验参数中是否存在Sid，如果没有，抛出异常
        if not args or not self.judgeData.inData("Sid", args):
            return param_miss

        try:
            sid = args["Sid"]
            # 获取数据库中数据
            # 获取学生的基础信息
            student_abo = get_model_return_list(self.sstudents.get_student_abo_by_sid(sid))
            # 获取学生的技能信息
            student_tech = get_model_return_list(self.sstudents.get_student_tech_by_sid(sid))
            # 获取学生的竞赛信息
            student_use = get_model_return_list(self.sstudents.get_student_use_by_sid(sid))
            # 拼装返回结构体
            student_abo[0]["STech"] = student_tech
            student_abo[0]["SCUse"] = student_use
            search_student_abo_success["student_abo"] = student_abo
            return search_student_abo_success
        except Exception as e:
            # 防止系统的莫名错误
            print(e)
            return system_error

    def get_students_list(self, start_num, page_size, params):
        """
        获取所有的学生数据内容
        :param start_num: 分页查询的页数
        :param page_size: 分页查询的每页记录数
        :param params: 筛选条件，由后端生成
        :return:
        """
        students_list = self.sstudents.get_students_list(start_num, page_size, params)
        # [students_list1, 2, 3...]
        if isinstance(students_list, list) and students_list:
            return get_model_return_list(students_list)
        else:
            return []
