# *- coding:utf8 *-
# 用于判断数组中是否含有某个参数


class JudgeData():

    def inData(self, param, jsonitem):
        return bool(param in jsonitem.keys())

    def check_page_params(self, jsonitem):
        # 参数成对存在，判断是否缺失
        if not self.inData("page_num", jsonitem) or not self.inData("page_size", jsonitem):
            return -1, -1
        try:
            page_num = int(jsonitem.get("page_num"))
            page_size = int(jsonitem.get("page_size"))
            return page_num if page_num >= 1 else 1, page_size if page_size >= 1 else 1
        except Exception as e:
            print e.message
            return -1, -1
