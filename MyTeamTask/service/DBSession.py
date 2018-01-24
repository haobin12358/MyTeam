# *- coding:utf8 *-
# 兼容linux系统
import sys
import os
sys.path.append(os.path.dirname(os.getcwd())) # 增加系统路径
# 引用python类
from sqlalchemy.orm import sessionmaker
# 引用项目类
from models import model
from models import log_model

# 实例化session
db_session = sessionmaker(bind=model.mysql_engine)
db_log_session = sessionmaker(bind=log_model.mysql_engine)


# 获取数据库连接session
def get_session():
    try:
        session = db_session()
        status = True
    except Exception as e:
        print e.message
        session = None
        status = False
    finally:
        return session, status
