# *- coding:utf8 *-
#引用python类
from sqlalchemy.orm import sessionmaker
#引用项目类
from models import model

#实例化session
db_session = sessionmaker(bind=model.mysql_engine)

