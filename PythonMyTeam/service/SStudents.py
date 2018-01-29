# *- coding:utf8 *-
# 兼容linux系统
import sys
import os
sys.path.append(os.path.dirname(os.getcwd())) # 增加系统路径
# 引用项目类
import DBSession
from models import model
from service.SLogs import SLogs
from common.TransformToList import trans_params

# 操作students表的相关方法
class SStudents():
    def __init__(self):
        try:
            self.session = DBSession.db_session()  # 初始化
            self.status = True  # session异常的判断标记
            self.slogs = SLogs()
        except Exception as e:
            print e.message
            self.status = False
    """
    # 获取全部的学生信息列表
    def get_students_list(self):
        students_list = self.session.query(model.Students.Sid, model.Students.Sname, model.Students.Sschool,
                                           model.Students.Uid, model.Students.Sgrade).all()
        return students_list

    # 分页获取学生的信息列表
    def get_students_list_by_start_end(self, start_num, infor_num):
        students_list = self.session.query(model.Students.Sid, model.Students.Sname, model.Students.Sschool,
                                           model.Students.Uid, model.Students.Sgrade).offset(start_num - 1) \
            .limit(infor_num).all()
        return students_list
    """
    # 获取学生信息详情
    def get_student_abo_by_sid(self, sid):
        student_abo = None
        try:
            student_abo = self.session.query(model.Students.Sid, model.Students.Uid, model.Students.Sname,
                                         model.Students.Suniversity, model.Students.Sschool, model.Students.Sno,
                                         model.Students.Stel, model.Students.Ssex).filter_by(Sid=sid).all()
        except Exception as e:

            #self.slogs.add_logs_easy(product_name="7443",class_name="SStudents",def_name="get_student_abo_by_sid",
               #                      param="student_abo",param_value=str(student_abo),ct_system="linux",level=3,
               #                      messages=e.message,ct_user="haobin")
            print e.message
        finally:
            self.session.close()
        return student_abo

    # 获取学生技能
    def get_student_tech_by_sid(self, sid):
        student_tech = None
        try:
            student_tech = self.session.query(
                model.STechs.STid, model.STechs.Sid, model.STechs.STname, model.STechs.STlevel).filter_by(Sid=sid).all()
        except Exception as e:
            #self.slogs.add_logs_easy(product_name="7443", class_name="SStudents", def_name="get_student_tech_by_sid",
             #                        param="student_yech", param_value=str(student_tech), ct_system="linux", level=3,
              #                       messages=e.message,ct_user="haobin")
            print e.message
        finally:
            self.session.close()
        return student_tech

    # 获取学生竞赛简历
    def get_student_use_by_sid(self, sid):
        student_use = None
        try:
            student_use = self.session.query(model.SCuse.SCid, model.SCuse.Sid, model.SCuse.SCname, model.SCuse.SCno) \
                .filter_by(Sid=sid).all()
        except Exception as e:
            #self.slogs.add_logs_easy(product_name="7443", class_name="SStudents", def_name="get_student_use_by_sid",
             #                        param="student_use", param_value=str(student_use), ct_system="linux", level=3,
              #                       messages=e.message,ct_user="haobin")
            print e.message
        finally:
            self.session.close()
        return student_use

    # 根据学生id获取用户id
    def get_uid_by_sid(self, sid):
        uid = None
        try:
            uid = self.session.query(model.Students.Uid).filter_by(Sid = sid).scalar()
        except Exception as e:
            #self.slogs.add_logs_easy(product_name="7443", class_name="SStudents", def_name="get_uid_by_sid",
             #                        param="uid", param_value=str(uid), ct_system="linux", level=3,
              #                       messages=e.message,ct_user="haobin")
            print e.message
        finally:
            self.session.close()
        return uid

    # 根据用户id获取学生id
    def get_sid_by_uid(self, uid):
        sid = None
        try:
            sid = self.session.query(model.Students.Sid).filter_by(Uid = uid).scalar()
        except Exception as e:
            print e.message
        finally:
            self.session.close()
        return sid

    # 根据学生id获取学生姓名
    def get_sname_by_sid(self, sid):
        sname = None
        try:
            sname = self.session.query(model.Students.Sname).filter_by(Sid = sid).scalar()
        except Exception as e:
            #self.slogs.add_logs_easy(product_name="7443", class_name="SStudents", def_name="get_sname_by_sid",
             #                        param="sname", param_value=str(sname), ct_system="linux", level=3,
              #                       messages=e.message,ct_user="haobin")
            print e.message
        finally:
            self.session.close()
        return sname

    # 根据学生姓名或学院或年级查询学生
    def get_students_list(self, start_num, page_size, params):
        sql = None
        try:
            sql = self.session.query(model.Students.Sid, model.Students.Sname,
                                 model.Students.Sschool, model.Students.Sgrade).filter(*params)\
                .offset(start_num).limit(page_size).all()
        except Exception as e:
            #self.slogs.add_logs_easy(product_name="7443", class_name="SStudents", def_name="get_students_list",
             #                        param="sql", param_value=str(sql), ct_system="linux", level=3,
              #                       messages=e.message,ct_user="haobin")
            print e.message
        finally:
            self.session.close()
        return sql

    # 根据学生竞赛经历名称和学生id获取学生竞赛经历id
    def get_scid_by_scname_and_sid(self, sid, scname):
        scid = None
        try:
            scid = self.session.query(model.SCuse.SCid).filter_by(Sid = sid).filter_by(SCname = scname).scalar()
        except Exception as e:
            print e.message
        finally:
            self.session.close()
        return scid

    # 根据学生技能名称和学生id获取学生技能id
    def get_stid_by_stname_and_sid(self, sid, stname):
        stid = None
        try:
            stid = self.session.query(model.STechs.STid).filter_by(Sid = sid).filter_by(STname = stname).scalar()
        except Exception as e:
            print e.message
        finally:
            self.session.close()
        return stid

    # 根据学生id获取竞赛历史名称
    @trans_params
    def get_scname_by_sid(self, sid):
        scname = None
        try:
            scname = self.session.query(model.SCuse.SCname).filter_by(Sid = sid).all()
        except Exception as e:
            print e.message
        finally:
            self.session.close()
        return scname

    # 根据学生id获取个人技能名称
    @trans_params
    def get_stname_by_sid(self, sid):
        stname = None
        try:
            stname = self.session.query(model.STechs.STname).filter_by(Sid = sid).all()
        except Exception as e:
            print e.message
        finally:
            self.session.close()
        return stname
