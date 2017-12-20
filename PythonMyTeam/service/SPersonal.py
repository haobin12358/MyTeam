# *- coding:utf8 *-
#引用项目类
import DBSession
from models import model
from common.TransformToList import trans_params

#处理个人信息相关内容
class SPersonal():
    def __init__(self):
        try:
            self.session = DBSession.db_session()
            self.status = True
        except Exception as e:
            print e.message
            self.status = False

