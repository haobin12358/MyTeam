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

            return True
        except Exception as e:
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
        :return:
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
            print e.message
            return False

    def add_student_tech_by_uid(self, stid, sid, stname, stlevel):
        """
        :param stid:
        :param sid:
        :param stname:
        :param stlevel:
        :return:
        """
        try:
            new_tech = model.STechs()
            new_tech.STid = stid
            new_tech.Sid = sid
            new_tech.STname = stname
            new_tech.STlevel = stlevel

            self.session.add(new_tech)
            self.session.commit()

            return True
        except Exception as e:
            print e.message
            return False

    def get_tid_by_uid(self, uid):
        return self.session.query(model.Teachers.Tid).filter_by(Uid = uid).scalar()

    def get_sid_by_uid(self, uid):
        return self.session.query(model.Students.Sid).filter_by(Uid = uid).scalar()


