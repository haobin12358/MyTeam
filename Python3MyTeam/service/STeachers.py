# *- coding:utf8 *-
# 兼容linux系统
import sys
import os
sys.path.append(os.path.dirname(os.getcwd())) # 增加系统路径
# 引用项目类
from common.TransformToList import trans_params
from models import model
from service.DBSession import db_session
from sqlalchemy import func

# 操作teachers表的相关方法
class STeachers():
    def __init__(self):
        try:
            self.session = db_session()  # 实例化
            self.status = True  # session异常的判断标记
        except Exception as e:
            print(e)
            self.status = False

    # # 获取全部教师信息列表
    # def get_teachers_list(self):
    #     teachers_list = self.session.query(model.Teachers.Tid, model.Teachers.Tname, model.Teachers.Tschool,
    #                                        model.Teachers.Ttime).all()
    #     return teachers_list

    # # 分页获取教师信息列表
    # def get_teachers_list_by_start_end(self, start_num, infor_num):
    #     teachers_list = self.session.query(model.Teachers.Tid, model.Teachers.Tname, model.Teachers.Tschool,
    #                                        model.Teachers.Ttime).offset(start_num).limit(infor_num).all()
    #     return teachers_list

    # 根据tid获取教师信息详情
    def get_teacher_abo_by_tid(self, tid):
        teacher_abo = None
        try:
            teacher_abo = self.session.query(model.Teachers.Tid, model.Teachers.Uid, model.Teachers.Tname,
                                         model.Teachers.Ttel, model.Teachers.Tno, model.Teachers.Tuniversity,
                                         model.Teachers.Tschool, model.Teachers.Ttime).filter_by(Tid=tid).all()
        except Exception as e:
            print(e)
        finally:
            self.session.close()
        return teacher_abo

    # 获取教师带队经历
    def get_teacher_use_by_tid(self, tid):
        teacher_use = None
        try:
            teacher_use = self.session.query(model.TCuse.TCid, model.TCuse.Tid, model.TCuse.TCname, model.TCuse.TCno, model.TCuse.TCnum) \
                .filter_by(Tid=tid).all()
        except Exception as e:
            print(e)
        finally:
            self.session.close()
        return teacher_use

    # 根据教师id获取用户id
    def get_uid_by_tid(self, tid):
        uid = None
        try:
            uid = self.session.query(model.Teachers.Uid).filter_by(Tid = tid).scalar()
        except Exception as e:
            print(e)
        finally:
            self.session.close()
        return uid

    # 根据教师id获取教师姓名
    def get_tname_by_tid(self, tid):
        tname = None
        try:
            tname = self.session.query(model.Teachers.Tname).filter_by(Tid = tid).scalar()
        except Exception as e:
            print(e)
        finally:
            self.session.close()
        return tname

    # 根据教师姓名或学院或任教时间查询教师
    def get_teachers_list(self, start_num, page_size, params):
        sql = None
        try:
            sql = self.session.query(model.Teachers.Tid, model.Teachers.Tname,
                                 model.Teachers.Tschool, model.Teachers.Ttime).filter(*params)\
                .offset(start_num).limit(page_size).all()
        except Exception as e:
            print(e)
        finally:
            self.session.close()
        return sql

    # 获取教师总数
    def get_all_count(self):
        s = 0
        try:
            s = self.session.query(func.count(eval("model.Teachers.Tid"))).scalar()
        except Exception as e:
            print(e)
        finally:
            self.session.close()
        return s

    # 根据教师竞赛经历名称和教师id获取教师竞赛经历id
    def get_tcid_by_tcname_and_tid(self, tid, tcname):
        tcid = None
        try:
            tcid = self.session.query(model.TCuse.TCid).filter_by(Tid = tid).filter_by(TCname = tcname).scalar()
        except Exception as e:
            print(e)
        finally:
            self.session.close()
        return tcid

    # 根据教工号、学校获取教师id
    def get_tid_by_tno_tuniversity(self, tno, tuniversity):
        tid = None
        try:
            tid = self.session.query(model.Teachers.Tid).filter_by(Tno=tno).filter_by(Tuniversity=tuniversity).scalar()
        except Exception as e:
            print(e)
        finally:
            self.session.close()
        return tid
