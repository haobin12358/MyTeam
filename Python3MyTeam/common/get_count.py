# *- coding:utf8 *-
# 兼容linux系统
import sys
import os
sys.path.append(os.path.dirname(os.getcwd())) # 增加系统路径
# 应用项目类
from models import model
from service.DBSession import get_session
from sqlalchemy import func

db_session, status = get_session()

model_id = {
    "Teachers": "model.Teachers.Tid",
    "Students": "model.Students.Sid",
    "Competitions": "model.Competitions.Cid"
}


# todo 获取的是全部数据，如果有筛选，无法感知
def get_count(model_name, params):
    """
    获取某个数据表中的全部数据
    :param model_name:
    :return:
    """
    if model_name not in model_id:
        return 0

    return db_session.query(func.count(eval(model_id.get(model_name)))).filter(*params).scalar()
