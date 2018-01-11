# *- coding:utf8 *-
# 引用python类
from flask import request
# 引用项目类
from common.JudgeData import JudgeData
from service.STeachers import STeachers
from Config.Requests import param_miss, system_error, search_teachers_list_success, search_teachers_abo_success
from common.get_model_return_list import get_model_return_list
from common.MyException import ParamsNotExitError
from common.get_str import get_str


# 用于处理教师信息相关数据
class CTeachers():
    def __init__(self):
        self.judgeData = JudgeData()
        self.steachers = STeachers()  # 实例化

    def teachers_list(self):
        """
        获取所有教师的简略信息
        :return: 所有教师简略信息的list
        """
        if not self.steachers.status:
            return system_error
        args = request.args.to_dict()
        from models.model import Teachers
        # 判断是否含有参数
        try:
            # 参数成对存在，判断是否缺失,并判断具体内容是否合法，非法或为空均返回具体错误
            page_num, page_size = self.judgeData.check_page_params(args)
            params = []
            if "Ttime" in args:
                params.append(Teachers.Ttime == args.get("Ttime"))
            if "Tname" in args:
                name = get_str(args, "Tname")
                params.append(Teachers.Tname.like("%{0}%".format(name)))
            if "Tschool" in args:
                school = get_str(args, "Tschool")
                params.append(Teachers.Tschool.like("%{0}%".format(school)))

            page_num, count = JudgeData.check_page_value(page_num, page_size, "Teachers", params)
            start_num = (page_num - 1) * page_size
            search_teachers_list_success["teacher_list"] = self.get_teachers_list(start_num, page_size, params)
            search_teachers_list_success["count"] = count
            search_teachers_list_success["page_num"] = page_num
            return search_teachers_list_success
        except (ParamsNotExitError, ValueError) as e:
            print e.message
            return param_miss
        except Exception as e:
            print e.message
            return system_error

    def teachers_abo(self):
        """
        通过Tid来获取教师的详细信息，包括带队经历
        必须有参数Tid
        :return:
        """
        if not self.steachers.status:
            return system_error
        args = request.args.to_dict()
        # 判断是否含有参数并 校验参数中是否存在Tid，如果没有，抛出异常
        if not args or not self.judgeData.inData("Tid", args):
            return param_miss

        try:
            tid = args["Tid"]  # 获取Tid
            # 获取教师的所有基础信息
            teacher_abo = get_model_return_list(self.steachers.get_teacher_abo_by_tid(tid))
            # 获取教师的所有竞赛信息
            teacher_use = get_model_return_list(self.steachers.get_teacher_use_by_tid(tid))
            # 将竞赛信息拼装进教师基础信息中
            teacher_abo[0]["TCuse"] = teacher_use
            search_teachers_abo_success["teacher_abo"] = teacher_abo
            return search_teachers_abo_success
        except Exception as e:
            # 防止系统的莫名错误
            print e.message
            return system_error

    def get_teachers_list(self, start_num, page_size, params):
        """
        获取数据中所有教师内容
        :param start_num: 分页查询的页数
        :param page_size: 分页查询的每页记录数
        :param params 筛选条件 后端根据请求下发参数自动生成
        :return:teachers_list type is list default []
        """

        teachers_list = self.steachers.get_teachers_list(start_num, page_size, params)
        # [teachers_list1, 2, 3...]
        if isinstance(teachers_list, list) and teachers_list:
            return get_model_return_list(teachers_list)
        else:
            return []
