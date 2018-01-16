# *- coding:utf8 *-
# 兼容linux系统
import sys
import os
sys.path.append(os.path.dirname(os.getcwd())) # 增加系统路径
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

    def get_utype_by_uid(self, uid):
        return self.session.query(model.Uers.Utype).filter_by(Uid = uid).scalar()

    def add_student_abo_by_uid(self, sid, uid, sname, sno, suniversity, sschool, stel, sgrade, ssex):
        """
        :param sid:
        :param uid:
        :param sname:
        :param sno:
        :param suniversity:
        :param sschool:
        :param stel:
        :param sgrade:
        :param ssex:
        :return: 插入数据正常返回True，数据库操作异常返回False
        """
        try:
            new_student = model.Students()
            new_student.Sid = sid
            new_student.Uid = uid
            new_student.Sname = sname
            new_student.Sno = sno
            new_student.Suniversity = suniversity
            new_student.Sschool = sschool
            new_student.Stel = stel
            new_student.Sgrade = sgrade
            new_student.Ssex = ssex

            self.session.add(new_student)
            self.session.commit()
            self.session.close()

            return True
        except Exception as e:
            self.session.rollback()
            print e.message
            return False

    def add_teacher_abo_by_uid(self, tid, uid, tname, tno, ttel, tuniversity, tschool, ttime):
        """
        :param tid:
        :param uid:
        :param tname:
        :param tno:
        :param ttel:
        :param tuniversity:
        :param tschool:
        :param ttime:
        :return: 插入数据正常返回True，数据库操作异常返回False
        """
        try:
            new_teacher = model.Teachers()
            new_teacher.Tid = tid
            new_teacher.Tname = tname
            new_teacher.Uid = uid
            new_teacher.Tno = tno
            new_teacher.Ttel = ttel
            new_teacher.Tuniversity = tuniversity
            new_teacher.Tschool = tschool
            new_teacher.Ttime = ttime

            self.session.add(new_teacher)
            self.session.commit()

            return True
        except Exception as e:
            self.session.rollback()
            print e.message
            return False

    def add_student_tech_by_sid(self, stid, sid, stname, stlevel):
        """
        :param stid:
        :param sid:
        :param stname:
        :param stlevel:
        :return: 插入数据正常返回True，数据库操作异常返回False
        """
        try:
            new_tech = model.STechs()
            new_tech.STid = stid
            new_tech.Sid = sid
            new_tech.STname = stname
            new_tech.STlevel = stlevel

            self.session.add(new_tech)
            self.session.commit()
            self.session.close()

            return True
        except Exception as e:
            self.session.rollback()
            print e.message
            return False

    def get_tid_by_uid(self, uid):
        return self.session.query(model.Teachers.Tid).filter_by(Uid = uid).scalar()

    def get_sid_by_uid(self, uid):
        return self.session.query(model.Students.Sid).filter_by(Uid = uid).scalar()

    def add_student_use_by_sid(self, scid, sid, scname, scno):
        """
        :param scid:
        :param sid:
        :param scname:
        :param scno:
        :return: 插入数据正常返回True，数据库操作异常返回False
        """
        try:
            new_student_use = model.SCuse()
            new_student_use.SCid = scid
            new_student_use.Sid = sid
            new_student_use.SCname = scname
            new_student_use.SCno = scno

            self.session.add(new_student_use)
            self.session.commit()

            return True
        except Exception as e:
            self.session.rollback()
            print e.message
            return False

    def add_teacher_use_by_tid(self, tcid, tid, tcname, tcno, tcnum):
        """
        :param tcid:
        :param tid:
        :param tcname:
        :param tcno:
        :param tcnum:
        :return: 插入数据正常返回True，数据库操作异常返回False
        """
        try:
            new_teacher_use = model.TCuse()
            new_teacher_use.TCid = tcid
            new_teacher_use.Tid = tid
            new_teacher_use.TCname = tcname
            new_teacher_use.TCno = tcno
            new_teacher_use.TCnum = tcnum

            self.session.add(new_teacher_use)
            self.session.commit()

            return True
        except Exception as e:
            self.session.rollback()
            print e.message
            return False

    def delete_student_tech_by_stid(self, stid):
        """
        :param stid:
        :return:
        """
        try:
            self.session.query(model.STechs).filter_by(STid = stid).delete()
            self.session.commit()

            return True
        except Exception as e:
            self.session.rollback()
            print e.message
            return False

    def delete_student_use_by_scid(self, scid):
        """
        :param scid:
        :return:
        """
        try:
            self.session.query(model.SCuse).filter_by(SCid = scid).delete()
            self.session.commit()

            return True
        except Exception as e:
            print e.message
            return False

    def delete_teacher_use_by_tcid(self, tcid):
        """
        :param tcid:
        :return:
        """
        try:
            self.session.query(model.TCuse).filter_by(TCid = tcid).delete()
            self.session.commit()

            return True
        except Exception as e:
            print e.message
            return False

    def update_student_abo_by_uid(self, uid, student_abo):
        """
        :param uid:
        :param student_abo:
        :return:
        """
        try:
            self.session.query(model.Students).filter_by(Uid = uid).update(student_abo)
            self.session.commit()

            return True
        except Exception as e:
            self.session.rollback()
            print e.message
            return False

    def update_teacher_abo_by_uid(self, uid, teacher_abo):
        """
        :param uid:
        :param teacher_abo:
        :return:
        """
        try:
            self.session.query(model.Teachers).filter_by(Uid = uid).update(teacher_abo)
            self.session.commit()

            return True
        except Exception as e:
            self.session.rollback()
            print e.message
            return False

    def update_student_tech_by_stid(self, stid, student_tech):
        """
        :param stid:
        :param student_tech:
        :return:
        """
        try:
            self.session.query(model.STechs).filter_by(STid = stid).update(student_tech)
            self.session.commit()

            return True
        except Exception as e:
            self.session.rollback()
            print e.message
            return False

    def update_student_use_by_scid(self, scid, student_use):
        """
        :param scid:
        :param student_use:
        :return:
        """
        try:
            self.session.query(model.SCuse).filter_by(SCid = scid).update(student_use)
            self.session.commit()

            return True
        except Exception as e:
            self.session.rollback()
            print e.message
            return False

    def update_teacher_use_by_tcid(self, tcid, teacher_use):
        """
        :param tcid:
        :param teacher_use:
        :return:
        """
        try:
            self.session.query(model.TCuse).filter_by(TCid = tcid).update(teacher_use)
            self.session.commit()

            return True
        except Exception as e:
            self.session.rollback()
            print e.message
            return False

    @trans_params
    def get_stname_by_sid(self, sid):
        return self.session.query(model.STechs.STname).filter_by(Sid = sid).all()

    @trans_params
    def get_scname_by_sid(self, sid):
        return self.session.query(model.SCuse.SCname).filter_by(Sid = sid).all()

    def get_stid_by_stname_and_sid(self, sid, stname):
        return self.session.query(model.STechs.STid).filter_by(Sid = sid).filter_by(STname = stname).scalar()

    def get_scid_by_scname_and_sid(self, sid, scname):
        return self.session.query(model.SCuse.SCid).filter_by(Sid = sid).filter_by(SCname = scname).scalar()

    def get_tcid_by_tcname_and_tid(self, tid, tcname):
        return self.session.query(model.TCuse.TCid).filter_by(Tid = tid).filter_by(TCname = tcname).scalar()