# *- coding:utf8 *-
import DBSession
from models import model
from common.TransformToList import trans_params


# 操作user表的相关方法
class SUsers():
    def __init__(self):
        try:
            self.session = DBSession.db_session()
            self.status = True
        except Exception, e:
            print e.message
            self.status = False

    # 插入单个user
    def add_user(self, uid, uname, upwd, utype):
        """
        :param uid:
        :param uname:
        :param upwd:
        :param utype:
        :return: 插入数据正常返回 0,数据库操作异常返回 1
        """
        try:
            # 实例化一个User对象 并赋值
            new_user = model.Uers()
            new_user.Uid = uid
            new_user.Uname = uname
            new_user.Upwd = upwd
            new_user.Utype = utype
            # 数据库操作添加一个User数据
            self.session.add(new_user)
            self.session.commit()

            return 0
        except Exception as e:
            # 数据库操作异常的话执行rollback 来回退操作
            self.session.rollback()
            print e.message
            return 1

    @trans_params
    # 获取所有的username
    def get_all_user_name(self):
        return self.session.query(model.Uers.Uname).all()
