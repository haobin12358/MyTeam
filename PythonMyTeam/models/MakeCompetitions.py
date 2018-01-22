# *- coding:utf8 *-
# 兼容linux系统
import sys
import os
sys.path.append(os.path.dirname(os.getcwd())) # 增加系统路径
# 引用python类
import uuid
# 引用项目类
from Config.CompetitionsList import Competitions_list, Teachers_list
from service.SCompetitions import SCompetitions
from service.SPersonal import SPersonal

# 实例化
scompetitions = SCompetitions()
spersonal = SPersonal()

if __name__ == "__main__":
    '''
        运行该文件就可以创建竞赛信息的基础测试信息
    '''
    for row in Competitions_list:
        scompetitions.add_competitions(uuid.uuid4(), row["Cname"], row["Cno"],
                                    row["Clevel"], row["Cstart"], row["Cend"],
                                    row["Cmin"], row["Cmax"], row["Cown"], row["Cabo"])
    """
    创建教师脏数据    
    """
    for row in Teachers_list:
        spersonal.add_teacher_abo_by_uid(
            uuid.uuid4(), uuid.uuid4(), row.get("Tname"),
            row.get("Tno"), row.get("Ttel"), row.get("Tuniversity"),
            row.get("Tschool"), row.get("Ttime")
        )
