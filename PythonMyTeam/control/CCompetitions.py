# *- coding:utf8 *-
# 引用python类
from flask import request
import json
# 引用项目类
from common.JudgeData import JudgeData
from common.get_model_return_list import get_model_return_list
from service.SCompetitions import SCompetitions
from Config.Requests import param_miss, system_error, search_competitions_list_success, search_competition_abo_success


# 处理竞赛信息相关数据
class CCompetitions():
    def __init__(self):
        self.judgeData = JudgeData()  # 全局实例化参数校验对象
        self.scompetitions = SCompetitions()

    # 实现数据列表展示的数据处理
    def competitions_list(self):
        if not self.scompetitions.status:
            return system_error
        args = request.args.to_dict()
        # 判断是否含有参数
        if not args:
            search_competitions_list_success["competition_list"] = self.get_competition_list()
            return search_competitions_list_success
        try:
            # 参数成对存在，判断是否缺失,并判断具体内容是否合法，非法或为空均返回-1
            page_num, page_size = self.judgeData.check_page_params(args)

            start_num = (page_num - 1) * page_size
            search_competitions_list_success["competition_list"] = self.get_competition_list(start_num, page_size)
            return search_competitions_list_success
        except Exception as e:
            print e.message
            return system_error

    # 实现竞赛详情页的数据处理
    def competitions_abo(self):
        if not self.scompetitions.status:
            return system_error
        args = request.args.to_dict()
        # 判断是否含有参数并判断是否含有Cid参数
        if not args or not self.judgeData.inData("Cid", args):
            return param_miss

        cid = args["Cid"]
        try:
            competition_abo = self.scompetitions.get_competitions_abo_by_cid(cid)
            search_competition_abo_success["competition_abo"] = get_model_return_list(competition_abo)
            return search_competition_abo_success
        except Exception as e:
            print e.message
            return system_error

    def get_competition_list(self, start_num=-1, page_size=-1):
        # 判断是否是分页查询
        if start_num >= 0 and page_size > 0:
            competitions_list = self.scompetitions.get_competitions_list_by_start_end(start_num, page_size)
        else:
            competitions_list = self.scompetitions.get_competitions_list()
        # [competitions1, 2, 3...]
        if isinstance(competitions_list, list) and competitions_list:
            return get_model_return_list(competitions_list)
        else:
            return []
