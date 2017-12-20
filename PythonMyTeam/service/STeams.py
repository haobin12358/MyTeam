# *- coding:utf8 *-
#引用项目类
from models import model
import DBSession
from common.TransformToList import trans_params

#操作teams表的相关方法
class STeams():
    def __init__(self):
        try:
            self.session = DBSession.db_session()
            self.status = True
        except Exception as e:
            print e.message
            self.status = False

