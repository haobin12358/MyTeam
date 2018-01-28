# *- coding:utf8 *-
# 兼容linux系统
import sys
import os
sys.path.append(os.path.dirname(os.getcwd())) # 增加系统路径
import json
import uuid
from datetime import datetime
# 引用项目类
import DBSession
from models import log_model

class SLogs():
    def __init__(self):
        try:
            self.session = DBSession.db_log_session()  # 实例化
            self.status = True  # session异常的判断标记
        except Exception as e:
            print e.message
            self.status = False

    def add_logs(self, lid, ct_time, ct_user, product_name, class_name, def_name, param, param_value, level, messages,
                 ct_system, ct_ip, package_name, log_tag, log_size, log_count):
        """
        :param lid: 列表id
        :param ct_time: 创建时间
        :param ct_user: 创建人
        :param product_name: 产品名称（端口号）
        :param class_name: 类名
        :param def_name: 函数名
        :param param: 参数名
        :param param_value: 参数值
        :param level: 日志等级
        :param messages: 日志内容
        :param ct_system: 操作系统
        :param ct_ip: 系统ip
        :param package_name: 包名
        :param log_tag: 日志标记
        :param log_size: 日志大小
        :param log_count: 日志分条数目，目前为0
        :return:
        """
        try:
            new_log = log_model.Logs()
            new_log.lid = lid
            new_log.ct_time = ct_time
            new_log.ct_user = ct_user
            new_log.product_name = product_name
            new_log.class_name = class_name
            new_log.def_name = def_name
            new_log.param = param
            new_log.param_value = param_value
            new_log.level = level
            new_log.messages = messages
            new_log.ct_system = ct_system
            new_log.ct_ip = ct_ip
            new_log.package_name = package_name
            new_log.log_tag = log_tag
            new_log.log_size = log_size
            new_log.log_count = log_count
            self.session.add(new_log)
            self.session.commit()
            return True
        except Exception as e:
            print e.message
            self.session.rollback()
            return False

    def add_logs_easy(self, **kwargs):
        keys = kwargs.keys()
        list_param = ["lid", "ct_time", "ct_user", "product_name", "class_name", "def_name", "param",
                      "param_value", "level", "messages", "ct_system", "ct_ip", "package_name", "log_tag",
                      "log_size", "log_count"]
        list_values = [None, None, "public_user", 7443, "unknown class", "unknown def", None,
                       None, None, None, None, None, None, None, None, None]
        for row in keys:
            if row in list_param:
                list_values[list_param.index(row)] = kwargs[row]
        list_values[0] = uuid.uuid4()
        list_values[1] = datetime.now()
        if list_values[9] != None:
            list_values[14] = len(list_values[9])
        else:
            list_values[14] = 0
        if list_values[14] < 4 * 1024 *1024:
            list_values[15] = 0
        else:
            list_values[15] = 1

        try_add = self.add_logs(list_values[0], list_values[1],list_values[2], list_values[3], list_values[4],
                                list_values[5], list_values[6], list_values[7], list_values[8], list_values[9],
                                list_values[10], list_values[11], list_values[12], list_values[13], list_values[14],
                                list_values[15])
        return try_add

if __name__ == "__main__":
    slogs = SLogs()
    print slogs.add_logs_easy(ct_user="hello")

