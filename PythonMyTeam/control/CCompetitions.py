# *- coding:utf8 *-
# 引用python类
from flask import request
import json
# 引用项目类
from common.JudgeData import JudgeData
from common.MyException import ParamsNotExitError
from common.get_model_return_list import get_model_return_list
from common.get_str import get_str
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
        params = []
        try:
            from models.model import Competitions
            # 参数成对存在，判断是否缺失,并判断具体内容是否合法，非法或为空均返回-1
            page_num, page_size = self.judgeData.check_page_params(args, "Competitions")
            start_num = (page_num - 1) * page_size
            if "Cend" in args:
                params.append(Competitions.Cstart >= args.get("Cstart"))
            if "Cstart" in args:
                params.append(Competitions.Tname <= args.get("Cend"))
            if "Cname" in args:
                name = get_str(args, "Cname")
                params.append(Competitions.Cname.like("%{0}%".format(name)))
            if "Clevel" in args:
                params.append(Competitions.Clevel == args.get("Clevel"))
            search_competitions_list_success["competition_list"] = self.get_competition_list(start_num, page_size, params)
            return search_competitions_list_success
        except (ParamsNotExitError, ValueError) as e:
            print e.message
            return param_miss
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

    def get_competition_list(self, start_num, page_size, params):
        # 根据筛选条件查询全部竞赛信息
        competitions_list = self.scompetitions.get_competitions_list(start_num, page_size, params)
        # [competitions1, 2, 3...]
        if isinstance(competitions_list, list) and competitions_list:
            return get_model_return_list(competitions_list)
        else:
            return []
