# *- coding:utf8 *-
# 兼容linux系统
import sys
import os
sys.path.append(os.path.dirname(os.getcwd())) # 增加系统路径
# 引用项目类
import DBSession
from models import model
from common.TransformToList import trans_params


# 操作competitions表的相关方法
class SCompetitions():
    def __init__(self):
        try:
            self.session = DBSession.db_session()  # 实例化
            self.status = True  # session异常的判断标记
        except Exception as e:
            print e.message
            self.status = False

    # 增加竞赛信息
    def add_competitions(self, cid, cname, cno, clevel, cstart, cend, cmin, cmax, cown, cabo):
        '''
        :param cid; 主键
        :param cname: 竞赛名称
        :param cno: 竞赛届次
        :param clevel: 竞赛等级1-9
        :param cstart: 竞赛开始时间xxxx-xx-xx
        :param cend: 竞赛结束时间xxxx-xx-xx
        :param cmin:最小参赛人数
        :param cmax:最大参赛人数
        :param cown:发起者
        :param cabo:竞赛信息详情
        :return: 插入数据正常时返回0，数据库操作异常返回1
        '''
        try:

            # 数据表赋值
            new_competitions = model.Competitions()
            new_competitions.Cid = cid
            new_competitions.Cname = cname
            new_competitions.Cno = cno
            new_competitions.Clevel = clevel
            new_competitions.Cstart = cstart
            new_competitions.Cend = cend
            new_competitions.Cmin = cmin
            new_competitions.Cmax = cmax
            new_competitions.Cown = cown
            new_competitions.Cabo = cabo
            # 执行数据库操作
            self.session.add(new_competitions)
            self.session.commit()
            return True
        except Exception as e:
            # 数据库操作异常，回退操作
            import traceback

            print traceback.format_exc()
            self.session.rollback()
            print e.message
            return False

    # 获取全部竞赛信息列表
    """
    def get_competitions_list(self):
        competitions_list = self.session.query(model.Competitions.Cid, model.Competitions.Cname,
                                               model.Competitions.Cno, model.Competitions.Clevel,
                                               model.Competitions.Cstart, model.Competitions.Cend).all()
        return competitions_list
    # 列表分页
    def get_competitions_list_by_start_end(self, start_num, infor_num):
        competitions_list = self.session.query(model.Competitions.Cid, model.Competitions.Cname,
                                               model.Competitions.Cno, model.Competitions.Clevel,
                                               model.Competitions.Cstart, model.Competitions.Cend) \
            .offset(start_num).limit(infor_num).all()
        return competitions_list
    """

    # 竞赛信息详情
    def get_competitions_abo_by_cid(self, cid):
        competition_abo = None
        try:
            competition_abo = self.session.query(model.Competitions.Cid, model.Competitions.Cname,
                                             model.Competitions.Cno, model.Competitions.Clevel,
                                             model.Competitions.Cstart, model.Competitions.Cend,
                                             model.Competitions.Cmin, model.Competitions.Cmax,
                                             model.Competitions.Cown, model.Competitions.Cabo) \
                .filter_by(Cid=cid).all()
        except Exception as e:
            print e.message
        finally:
            self.session.close()
        return competition_abo

    # 根据id获取竞赛名称和竞赛届次
    def get_competitions_name_and_no_by_cid(self, cid):
        competitions_name_and_no = None
        try:
            competitions_name_and_no = self.session.query(model.Competitions.Cname, model.Competitions.Cno)\
                .filter_by(Cid=cid).scalar()
        except Exception as e:
            print e.message
        finally:
            self.session.close()
        return competitions_name_and_no

    # 根据竞赛名称、届次、等级获取竞赛id
    def get_cid_by_cname_cno_clevel(self, cname, cno, clevel):
        cid = None
        try:
            cid = self.session.query(model.Competitions.Cid).filter_by(Cname=cname).filter_by(Cno=cno)\
                .filter_by(Clevel=clevel).scalar()
        except Exception as e:
            print e.message
        finally:
            self.session.close()
        return cid

    # 根据竞赛名称或竞赛等级或时间查询竞赛
    def get_competitions_list(self, start_num, page_size, params):
        sql = None
        try:
            sql = self.session.query(model.Competitions.Cid, model.Competitions.Cname,
                                 model.Competitions.Clevel, model.Competitions.Cstart,
                                 model.Competitions.Cend, model.Competitions.Cno).filter(*params)\
                .offset(start_num).limit(page_size).all()
        except Exception as e:
            print e.message
        finally:
            self.session.close()
        return sql

if __name__ == '__main__':
    scompetitions = SCompetitions()
    a = scompetitions.get_competitions_abo_by_cid("6e120ed9-0334-4780-ac77-68c7b2832a4e")
    print a
    print type(a)
