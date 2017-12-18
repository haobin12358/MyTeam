# *- coding:utf8 *-
#引用python类
import uuid
#引用项目类
from Config.CompetitionsList import Competitions_list
from service.SCompetitions import SCompetitions

#实例化
scompetitions = SCompetitions()

if __name__ == "__main__":
    '''
        运行该文件就可以创建竞赛信息的基础测试信息
    '''
    for row in Competitions_list:
        scompetitions.add_competitions(uuid.uuid4(), row["Cname"], row["Cno"],
                                       row["Clevel"], row["Cstart"], row["Cend"],
                                       row["Cmin"], row["Cmax"], row["Cown"], row["Cabo"])