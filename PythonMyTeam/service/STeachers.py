# *- coding:utf8 *-
# 引用项目类
from common.TransformToList import trans_params
from models import model
from DBSession import db_session


# 操作teachers表的相关方法
class STeachers():
    def __init__(self):
        try:
            self.session = db_session()  # 实例化
            self.status = True  # session异常的判断标记
        except Exception as e:
            print e.message
            self.status = False

    # 获取全部教师信息列表
    def get_teachers_list(self):
        teachers_list = self.session.query(model.Teachers.Tid, model.Teachers.Tname, model.Teachers.Tschool,
                                           model.Teachers.Ttime).all()
        return teachers_list

    # 分页获取教师信息列表
    def get_teachers_list_by_start_end(self, start_num, infor_num):
        teachers_list = self.session.query(model.Teachers.Tid, model.Teachers.Tname, model.Teachers.Tschool,
                                           model.Teachers.Ttime).offset(start_num).limit(infor_num).all()
        return teachers_list

    # 根据tid获取教师信息详情
    def get_teacher_abo_by_tid(self, tid):
        teacher_abo = self.session.query(model.Teachers.Tid, model.Teachers.Uid, model.Teachers.Tname,
                                         model.Teachers.Ttel, model.Teachers.Tno, model.Teachers.Tuniversity,
                                         model.Teachers.Tschool, model.Teachers.Ttime).filter_by(Tid=tid).all()
        return teacher_abo

    # 获取教师带队经历
    def get_teacher_use_by_tid(self, tid):
        teacher_use = self.session.query(model.TCuse.TCid, model.TCuse.Tid, model.TCuse.TCname, model.TCuse.TCno, model.TCuse.TCnum) \
            .filter_by(Tid=tid).all()
        return teacher_use

    # 根据教师id获取用户id
    def get_uid_by_tid(self, tid):
        return self.session.query(model.Teachers.Uid).filter_by(Tid = tid).scalar()

    # 根据教师id获取教师姓名
    def get_tname_by_tid(self, tid):
        return self.session.query(model.Teachers.Tname).filter_by(Tid = tid).scalar()