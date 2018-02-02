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

    def add_logs(self, **kwargs):
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
            new_log.lid = kwargs.get('lid')
            new_log.ct_time = kwargs.get('ct_time')
            new_log.ct_user = kwargs.get('ct_user')
            new_log.product_name = kwargs.get('product_name')
            new_log.class_name = kwargs.get('class_name')
            new_log.def_name = kwargs.get('def_name')
            new_log.param = kwargs.get('param')
            new_log.param_value = kwargs.get('param_value')
            new_log.level = kwargs.get('level')
            new_log.messages = kwargs.get('messages')
            new_log.ct_system = kwargs.get('ct_system')
            new_log.ct_ip = kwargs.get('ct_ip')
            new_log.package_name = kwargs.get('package_name')
            new_log.log_tag = kwargs.get('log_tag')
            new_log.log_size = kwargs.get('log_size')
            new_log.log_count = kwargs.get('log_count')

            self.session.add(new_log)
            self.session.commit()
            return True
        except Exception as e:
            print e.message
            self.session.rollback()
            return False

    def add_logs_easy(self, **kwargs):
        keys = kwargs.keys()

        log_data = {'def_name': None, 'package_name': None, 'ct_ip': None, 'level': None,
                    'class_name': None, 'messages': None, 'param': None, 'ct_system': None, 'log_tag': None,
                    'param_value': None, 'product_name': None, 'ct_user': None}

        for key in keys:
            if key in log_data:
                # if isinstance(kwargs.get(key), int):
                    log_data[key] = kwargs.get(key)

        log_data["lid"] = uuid.uuid4()
        log_data["ct_time"] = datetime.now()
        log_data["log_size"] = 0
        if log_data.get("messages") != None:
            log_data["log_size"] = len(log_data.get("messages"))
        if log_data["log_size"] < 4 * 1024 * 1024:
            log_data["log_count"] = 0
        else:
            log_data["log_count"] = 1

        try_add = self.add_logs(**log_data)
        return try_add

if __name__ == "__main__":
    slogs = SLogs()
    print slogs.add_logs_easy(ct_user="hello")

