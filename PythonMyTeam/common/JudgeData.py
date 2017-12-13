# *- coding:utf8 *-
#用于判断数组中是否含有某个参数
class JudgeData():

    def inData(self, param, jsonitem):
        for row in jsonitem.keys():
            if row == param:
                return True
        return False