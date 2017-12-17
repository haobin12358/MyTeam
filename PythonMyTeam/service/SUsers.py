# -*- coding:utf-8-*-
import DBSession
from models import model

class SUsers():
    def __init__(self):
        self.session = DBSession.db_session()
    def add_user(self,uid,uname,upwd,utype):
        try:
            new_user = model.Uers()
            new_user.Uid = uid
            new_user.Uname = uname
            new_user.Upwd = upwd
            new_user.Utype = utype
            self.session.add(new_user)
            self.session.commit()

            return 0
        except Exception as e:
            print e.message
            return 1

