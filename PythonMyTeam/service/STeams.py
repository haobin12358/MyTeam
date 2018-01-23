# *- coding:utf8 *-
# 兼容linux系统
import sys
import os
sys.path.append(os.path.dirname(os.getcwd())) # 增加系统路径
# 引用项目类
from models import model
import DBSession
from common.TransformToList import trans_params
from Config.SQLs import SQL_FOR_SELECT_COUNT_IN_TEAM, SQL_FOR_SELECT_TCOUNT_IN_TEAM
import sqlalchemy

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
    def get_all_teams(self, start_num, page_size):
        team_list = self.session.query(model.Teams.TEid, model.Teams.TEname,
                                       model.Teams.Cid, model.Teams.TEuse, model.Teams.TEnum)\
            .offset(start_num).limit(page_size).all()
        return team_list

    # 分页获取团队列表
    def get_team_list_by_start_end(self, start_num, infor_num):
        team_list = self.session.query(model.Teams.TEid, model.Teams.TEname,
                                       model.Teams.Cid, model.Teams.TEuse, model.Teams.TEnum).filter_by(TEuse=701)\
            .offset(start_num-1).limit(infor_num).all()
        return team_list

    # 获取团队基础信息
    def get_team_abo_by_teid(self, teid):
        team_abo = self.session.query(model.Teams.TEid, model.Teams.TEname, model.Teams.Cid,
                                      model.Teams.TEuse, model.Teams.TEnum).filter_by(TEid=teid).scalar()
        return team_abo

    # 获取团队学生信息
    def get_student_list_by_teid(self, teid):
        student_list = self.session.query(model.TStudent.Sid, model.TStudent.TStype)\
            .filter_by(TEid=teid).filter_by(TSsubject=1).all()
        return student_list

    # 获取团队教师信息
    def get_tid_by_teid(self, teid):
        teacher_list = self.session.query(model.TTeacher.Tid).filter_by(TEid=teid).filter_by(TTsubject=1).scalar()
        print ">>>>>>>>>>id:"+str(teacher_list)
        return teacher_list

    # 获取团队任务列表
    def get_task_list_by_teid(self, teid):
        task_list = self.session.query(model.TTasks.Tkname, model.TTasks.Sid, model.TTasks.Tkstatus)\
            .filter_by(TEid=teid).order_by(model.TTasks.Tktime).desc().all()
        return task_list

    # 创建团队
    def add_team(self, teid, tename, cid, teuse, tenum):
        """
        :param teid:
        :param tename:
        :param cid:
        :param teuse:
        :param tenum:
        :return: 插入数据正常返回True，数据库操作异常返回False
        """
        try:
            new_team = model.Teams()
            new_team.TEid = teid
            new_team.TEname = tename
            new_team.Cid = cid
            new_team.TEuse = teuse
            new_team.TEnum = tenum

            self.session.add(new_team)
            self.session.commit()

            return True
        except Exception as e:
            self.session.rollback()
            print e.message
            return False

    # 创建团队与学生的关联信息
    def add_student_in_team(self, tsid, teid, sid, tstype, tssubject):
        """
        :param tsid:
        :param teid:
        :param sid:
        :param tstype:
        :param tssubject:
        :return: 插入数据正常返回True，数据库操作异常返回False
        """
        try:
            new_student_in_team = model.TStudent()
            new_student_in_team.TSid = tsid
            new_student_in_team.TEid = teid
            new_student_in_team.Sid = sid
            new_student_in_team.TStype = tstype
            new_student_in_team.TSsubject = tssubject

            self.session.add(new_student_in_team)
            self.session.commit()

            return True
        except Exception as e:
            self.session.rollback()
            print e.message
            return False

    # 创建团队与教师的关联信息
    def add_teacher_in_team(self, ttid, teid, tid, ttsubject):
        """
        :param ttid:
        :param teid:
        :param tid:
        :param ttsubject:
        :return: 插入数据正常返回True，数据库操作异常返回False
        """
        try:
            new_teacher_in_team = model.TTeacher()
            new_teacher_in_team.TTid = ttid
            new_teacher_in_team.TEid = teid
            new_teacher_in_team.Tid = tid
            new_teacher_in_team.TTsubject = ttsubject

            self.session.add(new_teacher_in_team)
            self.session.commit()

            return True
        except Exception as e:
            self.session.rollback()
            print e.message
            return False

    # 删除团队，应用于系统异常情况
    def delete_team_by_teid(self, teid):
        """
        :param teid:
        :return: 删除数据正常返回True，数据库操作异常返回False
        """
        try:
            self.session.query(model.Teams).filter_by(TEid=teid).delete()
            self.session.commit()

            return True
        except Exception as e:
            self.session.rollback()
            print e.message
            return False

    # 更新团队信息表
    def update_teams_by_teid(self, teid, update_team_item):
        """
        :param teid:
        :param update_team_item:
        :return: 更新数据正常返回True，数据库操作异常返回False
        """
        try:
            self.session.query(model.Teams).filter_by(TEid=teid).update(update_team_item)
            self.session.commit()

            return True
        except Exception as e:
            self.session.rollback()
            print e.message
            return False

    # 根据团队id获取竞赛id
    def get_cid_by_teid(self, teid):
        return self.session.query(model.Teams.Cid).filter_by(TEid=teid).scalar()

    # 获取团队的创建者sid
    def get_sid_by_teid(self, teid):
        return self.session.query(model.TStudent.Sid).filter_by(TEid=teid).filter_by(TStype=1000).scalar()

    # 根据团队id获取团队当前的学生数目
    def get_count_by_teid(self, teid):
        sql = SQL_FOR_SELECT_COUNT_IN_TEAM.format(teid)
        print sql
        return self.session.execute(sql)

    # 根据团队id获取团队成员数目
    def get_tenum_by_teid(self, teid):
        return self.session.query(model.Teams.TEnum).filter_by(TEid=teid).scalar()

    # 根据团队id获取团队当前的学生数目
    def get_tcount_by_teid(self, teid):
        sql = SQL_FOR_SELECT_TCOUNT_IN_TEAM.format(teid)
        print sql
        return self.session.execute(sql)

    # 根据团队id和学生id获取tsid
    def get_tsid_by_teid_sid(self, teid, sid):
        return self.session.query(model.TStudent.TSid).filter_by(TEid=teid).filter_by(Sid=sid).scalar()

    # 根据团队id和教师id获取ttid
    def get_ttid_by_teid_tid(self, teid, tid):
        return self.session.query(model.TTeacher.TTid).filter_by(TEid=teid).filter_by(Tid=tid).scalar()

    # 根据tsid更新tstudent表
    def update_tstudent_by_tsid(self, tsid, tstudent_item):
        """
        :param tsid:
        :param tstudent_item:
        :return: 更新数据正常返回True，数据库操作异常返回False
        """
        try:
            self.session.query(model.TStudent).filter_by(TSid=tsid).update(tstudent_item)
            self.session.commit()

            return True
        except Exception as e:
            self.session.rollback()
            print e.message
            return False

    # 根据ttid更新tteacher表
    def update_tteacher_by_ttid(self, ttid, tteacher_item):
        """
        :param ttid:
        :param tteacher_item:
        :return: 更新数据正常返回True，数据库操作异常返回False
        """
        try:
            self.session.query(model.TTeacher).filter_by(TTid=ttid).update(tteacher_item)
            self.session.commit()

            return True
        except Exception as e:
            self.session.rollback()
            print e.message
            return False

    # 根据团队id获取团队名称
    def get_tename_by_teid(self, teid):
        return self.session.query(model.Teams.TEname).filter_by(TEid=teid).scalar()

    # 根据团队id和学生id获取成员类型
    def get_tstype_by_teid_sid(self, teid, sid):
        return self.session.query(model.TStudent.TStype).filter_by(TEid=teid).filter_by(Sid=sid).scalar()

    # 根据cid获取竞赛名称、竞赛届次、竞赛等级
    def get_cname_cno_clevel_by_cid(self, cid):
        return self.session.query(model.Competitions.Cname, model.Competitions.Cno, model.Competitions.Clevel)\
            .filter_by(Cid=cid).first()

    # 根据teid获取当前被待审核人数和被拒绝人数
    def get_count_wait_refuse_by_teid(self, teid):
        wait_num = self.session.query(sqlalchemy.func.count(model.TStudent.TSid))\
            .filter_by(TEid=teid).filter_by(TSsubject=1100).scalar()
        refuse_num = self.session.query(sqlalchemy.func.count(model.TStudent.TSid))\
            .filter_by(TEid=teid).filter_by(TSsubject=1102).scalar()
        return wait_num, refuse_num

if __name__ == '__main__':
    steams = STeams()
    s = steams.get_cname_cno_clevel_by_cid('a6915f77-800d-4c96-8acb-7d0c8cc81fa0')
    print s.Cname
    print type(s)