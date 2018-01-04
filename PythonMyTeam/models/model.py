# *- coding:utf8 *-
# 引用python类
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import Column, create_engine, Integer, String, Text
import uuid
# 引用项目类
from Config import dbconfig as cfg

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


# 学生信息表
class Students(Base):
    __tablename__ = "Students"
    Sid = Column(String(64), primary_key=True)
    Uid = Column(String(64), nullable=False)
    Sname = Column(String(32), nullable=False)
    Sno = Column(String(16), nullable=False)
    Suniversity = Column(String(256), nullable=False)
    Sschool = Column(String(256), nullable=False)
    Stel = Column(String(16), nullable=False)
    Sgrade = Column(Integer)
    Ssex = Column(Integer)


# 教师信息表
class Teachers(Base):
    __tablename__ = "Teachers"
    Tid = Column(String(64), primary_key=True)
    Uid = Column(String(64), nullable=False)
    Tname = Column(String(16), nullable=False)
    Tno = Column(String(16), nullable=False)
    Ttel = Column(String(16), nullable=False)
    Tuniversity = Column(String(256), nullable=False)
    Tschool = Column(String(256), nullable=False)
    Ttime = Column(Integer)


# 竞赛信息表
class Competitions(Base):
    __tablename__ = "Competitions"
    Cid = Column(String(64), primary_key=True)
    Cname = Column(String(128), nullable=False)
    Cno = Column(Integer, nullable=False)
    Clevel = Column(Integer, nullable=False)
    Cstart = Column(String(10), nullable=False)
    Cend = Column(String(10), nullable=False)
    Cmin = Column(Integer)
    Cmax = Column(Integer)
    Cown = Column(String(64))
    Cabo = Column(Text, nullable=False)


# 团队表
class Teams(Base):
    __tablename__ = "Teams"
    TEid = Column(String(64), primary_key=True)
    TEname = Column(String(256), nullable=False)   # 团队名称
    Cid = Column(String(64), nullable=False)
    TEuse = Column(Integer, nullable=False)   # 是否可用701可用，702不可用
    TEnum = Column(Integer, nullable=False)   # 团队人数限制，增加判断竞赛人数功能，默认为竞赛人数最大值，无最大值时显示0

# 团队学生关联表
class TStudent(Base):
    __tablename__ = "TStudent"
    TSid = Column(String(64), primary_key=True)
    TEid = Column(String(64), nullable=False)
    Sid = Column(String(64), nullable=False)
    TStype = Column(Integer, nullable=False)   # 成员类型   1000创建人 1001管理员 1002其他成员
    TSsubject = Column(Integer, nullable=False)  # 审批流程  1100待审核 1101已通过 1102已拒绝 1103已退出

# 团队教师关联表
class TTeacher(Base):
    __tablename__ = "TTeacher"
    TTid = Column(String(64), primary_key=True)
    TEid = Column(String(64), nullable=False)
    Tid = Column(String(64), nullable=False)
    TTsubject = Column(Integer, nullable=False)  # 审批流程  0待审核 1已通过 2已拒绝 3已退出

# 团队任务表
class TTasks(Base):
    __tablename__ = "TTasks"
    Tkid = Column(String(64), primary_key=True)
    Tkname = Column(String(64), nullable=False) # 任务名称
    Tkabo = Column(Text) # 任务详情
    TEid = Column(String(64), nullable=False)  # 关联团队id
    Sid = Column(String(64), nullable=False)  # 处理人
    Tkstatus = Column(Integer, nullable=False)  # 任务状态 0待处理 1已处理 2被驳回 3延期中 4已结束
    Tktime = Column(String(64),nullable=False)  # 创建时间  类型需要交流

# 个人信息表
class Perinfor(Base):
    __tablename__ = "Perinfor"
    Pid = Column(String(64), primary_key=True)
    Uid = Column(String(64), nullable=False)
    Pmessage = Column(Text, nullable=False) # 消息内容
    Pstatus = Column(Integer, nullable=False) # 消息处理状态  1201已读 1200未读
    Ptype = Column(Integer) # 消息类型 901邀请 902任务 903通知 904其他
    Cid = Column(String(64))

# 学生技能表
class STechs(Base):
    __tablename__ = "STechs"
    STid = Column(String(64), primary_key=True)
    Sid = Column(String(64), nullable=False)
    STname = Column(String(32), nullable=False)
    STlevel = Column(Integer, nullable=False)


# 学生竞赛简历
class SCuse(Base):
    __tablename__ = 'SCuse'
    SCid = Column(String(64), primary_key=True)
    Sid = Column(String(64), nullable=False)
    SCname = Column(String(128), nullable=False)
    SCno = Column(String(16), nullable=False)


# 教师竞赛简历
class TCuse(Base):
    __tablename__ = 'TCuse'
    TCid = Column(String(64), primary_key=True)
    Tid = Column(String(64), nullable=False)
    TCname = Column(String(128), nullable=False)
    TCno = Column(String(16), nullable=False)
    TCnum = Column(Integer, nullable=False, default=1)


if __name__ == "__main__":
    '''
    运行该文件就可以在对应的数据库里生成本文件声明的所有table
    '''
    Base.metadata.create_all(mysql_engine)

