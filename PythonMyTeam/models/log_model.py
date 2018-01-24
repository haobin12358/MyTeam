# *- coding:utf8 *-
# 先放到这里好了，等有时间再分离出去
# 兼容linux系统
import sys
import os
sys.path.append(os.path.dirname(os.getcwd())) # 增加系统路径
# 引用python类
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import Column, create_engine, Integer, String, Text, DateTime
from Config import dbconfig as cfg

# 获取和mysql的连接引擎格式 "数据库://用户名:密码@ip(:端口号)/databse名(?charset=字符集)" ()里是可选内容
DB_PARAMS = "{0}://{1}:{2}@{3}/{4}?charset={5}".format(
    cfg.sqlenginename, cfg.username, cfg.password, cfg.host, cfg.log_database, cfg.charset)
mysql_engine = create_engine(DB_PARAMS, echo=True)

# 实例化基础表，这个这个基础类可以关联到数据库的具体字段
Base = declarative_base()

class Logs(Base):
    __tablename__ = 'Logs'
    lid = Column(String(64), primary_key=True)
    ct_time = Column(DateTime, nullable=False)
    ct_user = Column(String(128), nullable=False)
    product_name = Column(Integer, nullable=False)
    class_name = Column(String(64), nullable=False)
    def_name = Column(String(64), nullable=False)
    param = Column(String(64))
    param_value = Column(Text)
    level = Column(Integer)
    messages = Column(Text)
    ct_system = Column(String(32))
    ct_ip = Column(String(32))
    package_name = Column(String(64))
    log_tag = Column(String(64))
    log_size = Column(Integer)
    log_count = Column(Integer)


if __name__ == "__main__":
    '''
    运行该文件就可以在对应的数据库里生成本文件声明的所有table
    '''
    Base.metadata.create_all(mysql_engine)
