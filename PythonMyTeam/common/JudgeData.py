# *- coding:utf8 *-
# 兼容linux系统
import sys
import os
sys.path.append(os.path.dirname(os.getcwd())) # 增加系统路径
# 用于判断数组中是否含有某个参数
from MyException import ParamsNotExitError
from .get_count import get_count

class JudgeData():

    def inData(self, param, jsonitem):
        return bool(param in jsonitem.keys())

    def check_gotta_params(self, jsonitem, gotta_params):
        result_prams = filter(lambda x: x not in jsonitem, gotta_params)
        if result_prams:
            raise ParamsNotExitError(result_prams)

    def check_page_params(self, jsonitem):
        # 判断是否缺失 page_num page_size 必须存在
        # 判断page_num page_size 是否是正整数
        if "page_num" not in jsonitem or "page_size" not in jsonitem:
            raise ParamsNotExitError()
        try:
            page_num = int(jsonitem.get("page_num"))
            page_size = int(jsonitem.get("page_size"))
            page_num = page_num if page_num >= 1 else 1
            page_size = page_size if page_size >= 1 else 10
            return page_num, page_size
        except ValueError as e:
            e.message = "the pagenum = {0} pagesize = {1} is not right"
            raise ValueError(e)

    @staticmethod
    def check_page_value(page_num, page_size, model_name, params):
        count = get_count(model_name, params)
        if page_size * page_num > count:
            page_num = count / page_size
        page_num = page_num if page_num > 0 else 1
        return page_num, count
