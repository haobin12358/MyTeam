# *- coding:utf8 *-
# 兼容linux系统
import sys
import os
sys.path.append(os.path.dirname(os.getcwd())) # 增加系统路径
# 引用项目类
from service import DBSession
from models import model
from common.TransformToList import trans_params

# 处理各类个人信息，后期可能会扩展
class SInfor():
    def __init__(self):
        try:
            self.session = DBSession.db_session()
            self.status = True
        except Exception as e:
            print(e)
            self.status = False

    # 创建信息
    def add_infor(self, pid, uid, pmessage, pstatus, ptype, cid, teid, sid):
        """
        :param pid:
        :param uid:
        :param pmessage:
        :param pstatus:
        :param ptype:
        :param cid:
        :param teid:
        :return: 插入数据正常返回True，数据库操作异常返回False
        """
        try:
            new_infor = model.Perinfor()
            new_infor.Pid = pid
            new_infor.Uid = uid
            new_infor.Pmessage = pmessage
            new_infor.Pstatus = pstatus
            new_infor.Ptype = ptype
            new_infor.Cid = cid
            new_infor.TEid = teid
            new_infor.Sid = sid

            self.session.add(new_infor)
            self.session.commit()

            return True
        except Exception as e:
            self.session.rollback()
            print(e)
            return False

    # 根据pid获取teid
    def get_teid_by_pid(self, pid):
        teid = None
        try:
            teid = self.session.query(model.Perinfor.TEid).filter_by(Pid=pid).scalar()
        except Exception as e:
            print(e)
        finally:
            self.session.close()
        return teid

    # 根据pid获取发送人uid
    def get_uid2_by_pid(self, pid):
        uid = None
        try:
            uid = self.session.query(model.Perinfor.Uid).filter_by(Pid=pid).scalar()
        except Exception as e:
            print(e)
        finally:
            self.session.close()
        return uid

    # 获取个人信息list
    def get_info_by_uid2(self, uid):
        info_list = None
        try:
            info_list = self.session.query(model.Perinfor.Pid, model.Perinfor.TEid, model.Perinfor.Cid,
                                           model.Perinfor.Uid, model.Perinfor.Pmessage,
                                           model.Perinfor.Ptype).filter_by(Pstatus=1200).filter_by(Sid=uid).all()
        except Exception as e:
            print(e)
        finally:
            self.session.close()
        return info_list