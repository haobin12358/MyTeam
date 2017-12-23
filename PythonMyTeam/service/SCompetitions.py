# *- coding:utf8 *-
#引用项目类
import DBSession
from models import model
from common.TransformToList import trans_params

#操作competitions表的相关方法
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
            #数据表赋值
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
            #执行数据库操作
            self.session.add(new_competitions)
            self.session.commit()
            return True
        except Exception as e:
            #数据库操作异常，回退操作
            self.session.rollback()
            print e.message
            return False

    #获取全部竞赛信息列表
    def get_competitions_list(self):
        competitions_list = self.session.query(model.Competitions.Cid, model.Competitions.Cname,
                                               model.Competitions.Cno, model.Competitions.Clevel,
                                               model.Competitions.Cstart, model.Competitions.Cend).all()
        return competitions_list

    #列表分页
    def get_competitions_list_by_start_end(self, start_num, infor_num):
        competitions_list = self.session.query(model.Competitions.Cid, model.Competitions.Cname,
                                               model.Competitions.Cno, model.Competitions.Clevel,
                                               model.Competitions.Cstart, model.Competitions.Cend)\
            .offset(start_num - 1).limit(infor_num).all()
        return competitions_list

    #竞赛信息详情
    def get_competitions_abo_by_cid(self, cid):
        competition_abo = self.session.query(model.Competitions.Cid, model.Competitions.Cname,
                                               model.Competitions.Cno, model.Competitions.Clevel,
                                               model.Competitions.Cstart, model.Competitions.Cend,
                                               model.Competitions.Cmin, model.Competitions.Cmax,
                                               model.Competitions.Cown, model.Competitions.Cabo)\
            .filter_by(Cid = cid).all()
        return competition_abo

if __name__ == '__main__':
    scompetitions = SCompetitions()
    print ">>>" + str(scompetitions.get_competitions_abo_by_cid("d3500890-e217-4e93-8f65-e433b4f85213"))
