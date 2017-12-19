# *- coding:utf8 *-
#引用python类
from flask import request
import json
#引用项目类
from common.JudgeData import JudgeData
from service.SCompetitions import SCompetitions
from Config.Requests import param_miss, system_error

#处理竞赛信息相关数据
class CCompetitions():
    def __init__(self):
        self.judgeData = JudgeData() #全局实例化
        self.scompetitions = SCompetitions()

    #实现数据列表展示的数据处理
    def competitions_list(self):
        args = request.args
        print str(args)
        #判断是否含有参数
        if str(args) == "" or str(args) == {}:
            competitions_list = self.scompetitions.get_competitions_list()
            if competitions_list is not None:
                return competitions_list
            else:
                return []
        args = json.loads(args)

        #参数成对存在，判断是否缺失
        if not self.judgeData.inData("page_no", args) or not self.judgeData.inData("infor_num", args):
            return param_miss

        page_no = args["page_no"]
        infor_num = args["infor_num"]

        start_num = (page_no - 1) * infor_num

        competitions_list = self.scompetitions.get_competitions_list_by_start_end(start_num, infor_num)

        if competitions_list is not None:
            return competitions_list#返回体现在很有问题
        else:
            return system_error

    #实现竞赛详情页的数据处理
    def competitions_abo(self):
        args = request.args
        print str(args)
        #判断是否含有参数
        if str(args) == "" or str(args) == {}:
            return param_miss

        args = json.loads(args)
        cid = args["Cid"]

        competition_abo = self.scompetitions.get_competitions_abo_by_cid(cid)

        if competition_abo is not None:
            return competition_abo #返回体现在很有问题
        else:
            return system_error