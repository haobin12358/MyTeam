# *- coding:utf8 *-
# 兼容linux系统
import sys
import os
sys.path.append(os.path.dirname(os.getcwd())) # 增加系统路径
#引用项目类
import DBSession
from models import model
from common.TransformToList import trans_params


# 操作user表的相关方法
class SUsers():
    def __init__(self):
        try:
            self.session = DBSession.db_session() #实例化
            self.status = True #session异常的判断标记
        except Exception as e:
            print e.message
            self.status = False

    # 插入单个user
    def add_user(self, uid, uname, upwd, utype):
        """
        :param uid: 主键
        :param uname: 用户名
        :param upwd: 密码
        :param utype: 用户类型100-102
        :return: 插入数据正常返回 True,数据库操作异常返回 False
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
            self.session.close()
            return True
        except Exception as e:
            # 数据库操作异常的话执行rollback 来回退操作
            self.session.rollback()
            self.session.close()
            print e.message
            return False

    # 获取所有的username
    @trans_params
    def get_all_user_name(self):
        user_name = None
        try:
            user_name = self.session.query(model.Uers.Uname).all()
        except Exception as e:
            print e.message
        finally:
            self.session.close()
        return user_name

    # 根据用户名获取对应密码
    def get_upwd_by_uname(self, uname):
        upwd = None
        try:
            upwd = self.session.query(model.Uers.Upwd).filter_by(Uname = uname).scalar()
        except Exception as e:
            print e.message
        finally:
            self.session.close()
        return upwd

    # 根据用户名获取对应id
    def get_uid_by_uname(self, uname):
        uid = None
        try:
            uid = self.session.query(model.Uers.Uid).filter_by(Uname = uname).scalar()
        except Exception as e:
            print e.message
        finally:
            self.session.close()
        return uid

    # 根据用户id获取用户类型
    def get_utype_by_uid(self, uid):
        utype = 0
        try:
            utype = self.session.query(model.Uers.Utype).filter_by(Uid=uid).scalar()
        except Exception as e:
            print e.message
        finally:
            self.session.close()
        return utype
