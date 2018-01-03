# *- coding:utf8 *-
# 引用项目类
import DBSession
from models import model


# 操作students表的相关方法
class SStudents():
    def __init__(self):
        try:
            self.session = DBSession.db_session()  # 初始化
            self.status = True  # session异常的判断标记
        except Exception as e:
            print e.message
            self.status = False

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

    # 获取学生信息详情
    def get_student_abo_by_sid(self, sid):
        student_abo = self.session.query(model.Students.Sid, model.Students.Uid, model.Students.Sname,
                                         model.Students.Suniversity, model.Students.Sschool, model.Students.Sno,
                                         model.Students.Stel, model.Students.Ssex).filter_by(Sid=sid).all()
        return student_abo

    # 获取学生技能
    def get_student_tech_by_sid(self, sid):
        student_tech = self.session.query(
            model.STechs.STid, model.STechs.Sid, model.STechs.STname, model.STechs.STlevel).filter_by(Sid=sid).all()

        return student_tech

    # 获取学生竞赛简历
    def get_student_use_by_sid(self, sid):
        student_use = self.session.query(model.SCuse.SCid, model.SCuse.Sid, model.SCuse.SCname, model.SCuse.SCno) \
            .filter_by(Sid=sid).all()
        return student_use

    # 根据学生id获取用户id
    def get_uid_by_sid(self, sid):
        return self.session.query(model.Students.Uid).filter_by(Sid = sid).scalar()