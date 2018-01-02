# *- coding:utf8 *-
# 引用项目类
from models import model
import DBSession
from common.TransformToList import trans_params

# 操作teams表的相关方法
class STeams():
    def __init__(self):
        try:
            self.session = DBSession.db_session()
            self.status = True
        except Exception as e:
            print e.message
            self.status = False

    # 获取团队列表
    def get_all_teams(self):
        team_list = self.session.query(model.Teams.TEid, model.Teams.TEname,
                                       model.Teams.Cid, model.Teams.TEuse, model.Teams.TEnum).all()
        return team_list

    # 分页获取团队列表
    def get_team_list_by_start_end(self, start_num, infor_num):
        team_list = self.session.query(model.Teams.TEid, model.Teams.TEname,
                                       model.Teams.Cid, model.Teams.TEuse, model.Teams.TEnum).offset(start_num-1)\
            .limit(infor_num).all()
        return team_list

    # 获取团队基础信息
    def get_team_abo_by_teid(self, teid):
        team_abo = self.session.query(model.Teams.TEid, model.Teams.TEname, model.Teams.Cid,
                                      model.Teams.TEuse, model.Teams.TEnum).filter_by(TEid=teid).all()
        return team_abo

    # 获取团队学生信息
    def get_student_list_by_teid(self, teid):
        student_list = self.session.query(model.TStudent.Sid, model.TStudent.TStype)\
            .filter_by(TEid=teid).filter_by(TSsubject=1).all()
        return student_list

    # 获取团队教师信息
    def get_teacher_list_by_teid(self, teid):
        teacher_list = self.session.query(model.TTeacher.Tid).filter_by(TEid=teid).filter_by(TTsubject=1).all()
        return teacher_list

    # 获取团队任务列表
    def get_task_list_by_teid(self, teid):
        task_list = self.session.query(model.TTasks.Tkname, model.TTasks.Sid, model.TTasks.Tkstatus)\
            .filter_by(TEid=teid).order_by(model.TTasks.Tktime).desc().all()
        return task_list
