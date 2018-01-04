# *- coding:utf8 *-
# 引用项目类
import DBSession
from models import model
from common.TransformToList import trans_params

# 处理各类个人信息，后期可能会扩展
class SInfor():
    def __init__(self):
        try:
            self.session = DBSession.db_session()
            self.status = True
        except Exception as e:
            print e.message
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
            print e.message
            return False