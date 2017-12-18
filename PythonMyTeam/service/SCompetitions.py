# *- coding:utf8 *-
#引用项目类
import DBSession
from models.model import Competitions

#操作competitions相关数据
class SCompetitions():
    def __init__(self):
        try:
            self.session = DBSession.db_session() #实例化
            self.status = True #session异常的判断标记
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
            #实例化Competitions表。并赋值
            new_competition = Competitions()
            new_competition.Cid = cid
            new_competition.Cname = cname
            new_competition.Cno = cno
            new_competition.Clevel = clevel
            new_competition.Cstart = cstart
            new_competition.Cend = cend
            new_competition.Cmin = cmin
            new_competition.Cmax = cmax
            new_competition.Cown = cown
            new_competition.Cabo = cabo
            #执行数据库操作
            self.session.add(new_competition)
            self.session.commit()

            return True
        except Exception as e:
            #数据库操作异常，回退操作
            self.session.rollback()
            print e.message
            return False
