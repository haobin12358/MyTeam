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
    Sid = Column(String(64), nullable=False)
    TStyle = Column(Integer, nullable=False)


# 团队教师关联表
class TTeacher(Base):
    __tablename__ = "TTeacher"
    TTid = Column(String(64), primary_key=True)
    TEid = Column(String(64), nullable=False)
    Tid = Column(String(64), nullable=False)


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
    TCon = Column(String(16), nullable=False)
    TCnum = Column(Integer, nullable=False, default=1)


if __name__ == "__main__":
    '''
    运行该文件就可以在对应的数据库里生成本文件声明的所有table
    '''
    Base.metadata.create_all(mysql_engine)

