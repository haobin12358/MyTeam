# *- coding:utf8 *-
# 兼容linux系统
import sys
import os
sys.path.append(os.path.dirname(os.getcwd())) # 增加系统路径
# 引用python类
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import Column, create_engine, Integer, String, Text, DateTime
import uuid
# 引用项目类
from config import dbconfig as cfg

# 获取和mysql的连接引擎格式 "数据库://用户名:密码@ip(:端口号)/databse名(?charset=字符集)" ()里是可选内容
DB_PARAMS = "{0}://{1}:{2}@{3}/{4}?charset={5}".format(
    cfg.sqlenginename, cfg.username, cfg.password, cfg.host, cfg.database, cfg.charset)
mysql_engine = create_engine(DB_PARAMS, echo=True)

# 实例化基础表，这个这个基础类可以关联到数据库的具体字段
Base = declarative_base()

# 用户表
class Uers(Base):
    __tablename__ = "Users"
    Uid = Column(String(64), primary_key=True)
    Uname = Column(String(32), nullable=False)
    Upwd = Column(String(32), nullable=False)
    Utype = Column(Integer, nullable=False)


# 用户信息表
class UMessages(Base):
    __tablename__ = "UMessages"
    Mid = Column(String(64), primary_key=True)
    Uid = Column(String(64), nullable=False)
    Uname = Column(String(64), nullable=False)

# 团队表
class Teams(Base):
    __tablename__ = "Teams"
    TEid = Column(String(64), primary_key=True)
    TEname = Column(String(256), nullable=False)   # 团队名称
    TEuse = Column(Integer, nullable=False)   # 是否可用701可用，702不可用

# 团队用户关联表
class TUser(Base):
    __tablename__ = "TUser"
    TUid = Column(String(64), primary_key=True)
    TEid = Column(String(64), nullable=False)
    Uid = Column(String(64), nullable=False)
    TUtype = Column(Integer, nullable=False)   # 成员类型   1000创建人 1001管理员 1002其他成员
    TUsubject = Column(Integer, nullable=False)  # 审批流程  1100待审核 1101已通过 1102已拒绝 1103已退出

# 团队任务表
class TTasks(Base):
    __tablename__ = "TTasks"
    Tkid = Column(String(64), primary_key=True)
    Tkname = Column(String(64), nullable=False) # 任务名称
    Tkabo = Column(Text) # 任务详情
    TEid = Column(String(64), nullable=False)  # 关联团队id
    Uid = Column(String(64), nullable=False)  # 处理人
    Tkstatus = Column(Integer, nullable=False)  # 任务状态 0待处理 1已处理 2被驳回 3延期中 4已结束
    Tktime = Column(DateTime)  # 创建时间

# 个人信息表
class Perinfor(Base):
    __tablename__ = "Perinfor"
    Pid = Column(String(64), primary_key=True)
    Uid = Column(String(64), nullable=False)
    Pmessage = Column(Text, nullable=False) # 消息内容
    Pstatus = Column(Integer, nullable=False) # 消息处理状态  1201已读 1200未读
    Ptype = Column(Integer) # 消息类型 901邀请 902任务 903通知 904其他 905申请
    TEid = Column(String(64))


if __name__ == "__main__":
    '''
    运行该文件就可以在对应的数据库里生成本文件声明的所有table
    '''
    Base.metadata.create_all(mysql_engine)

