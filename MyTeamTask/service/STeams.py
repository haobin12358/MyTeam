# *- coding:utf8 *-
import sys
import os
sys.path.append(os.path.dirname(os.getcwd()))
import DBSession
from models import model
from common.TransformToList import trans_params

class STeams():
    def __init__(self):
        try:
            self.session = DBSession.db_session() #实例化
            self.status = True #session异常的判断标记
        except Exception as e:
            print e.message
            self.status = False

    def add_task(self):
        """
        :return:
        """
        return
