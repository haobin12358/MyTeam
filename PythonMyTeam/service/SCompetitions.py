# *- coding:utf8 *-
#引用项目类
import DBSession
from models.model import Competitions
from common.TransformToList import trans_params

#操作competitions表的相关方法
class SCompetitions():
    def __init__(self):
        try:
            self.session = DBSession.db_session() #实例化
            self.status = True #session异常的判断标记
            self.new_competition = Competitions() #实例化Competitions表
        except Exception as e:
            print e.message
            self.status = False

    #增加竞赛信息
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
            #数据表赋值
            self.new_competition.Cid = cid
            self.new_competition.Cname = cname
            self.new_competition.Cno = cno
            self.new_competition.Clevel = clevel
            self.new_competition.Cstart = cstart
            self.new_competition.Cend = cend
            self.new_competition.Cmin = cmin
            self.new_competition.Cmax = cmax
            self.new_competition.Cown = cown
            self.new_competition.Cabo = cabo
            #执行数据库操作
            self.session.add(self.new_competition)
            self.session.commit()

            return True
        except Exception as e:
            #数据库操作异常，回退操作
            self.session.rollback()
            print e.message
            return False

    #获取全部竞赛信息列表
    @trans_params
    def get_competitions_list(self):
        competitions_list = self.session.query(self.new_competition.Cid,
                                               self.new_competition.Cname, self.new_competition.Cno,
                                               self.new_competition.Clevel, self.new_competition.Cstart,
                                               self.new_competition.Cend).all()
        return competitions_list

    #列表分页，目前未完成
    @trans_params
    def get_competitions_list(self, page_no, infor_num):
        competitions_list = self.session.query(self.new_competition.Cid,
                                               self.new_competition.Cname, self.new_competition.Cno,
                                               self.new_competition.Clevel, self.new_competition.Cstart,
                                               self.new_competition.Cend).all()
        return competitions_list

    #竞赛信息详情
    @trans_params
    def get_competitions_abo_by_cid(self, cid):
        competition_abo = self.session.query(self.new_competition).scalar()
        return competition_abo


