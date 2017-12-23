# *- coding:utf8 *-
# 引用python类
from sqlalchemy.orm import sessionmaker
# 引用项目类
from models import model

# 实例化session
db_session = sessionmaker(bind=model.mysql_engine)


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
