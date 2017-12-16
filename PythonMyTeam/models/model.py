# -*- coding:utf-8-*-
#引用python类
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import Column, Index, Integer, String, UniqueConstraint,Text

Base = declarative_base() #实例化

#用户表
class Uers(Base):
    __tablename__ = "Users"
    Uid = Column(String(64), primary_key=True)
    Uname = Column(String(32),nullable=False)
    Upwd = Column(String(32),nullable=False)
    Utype = Column(Integer,nullable=False)

#学生信息表
class Students(Base):
    __tablename__ = "Students"
    Sid = Column(String(64),primary_key=True)
    Uid = Column(String(64),nullable=False)
    Sname = Column(String(32),nullable=False)
    Sno = Column(String(16),nullable=False)
    Suniversity = Column(String(256),nullable=False)
    Sschool = Column(String(256),nullable=False)
    Stel = Column(String(16),nullable=False)
    Sgrade = Column(Integer)
    Ssex = Column(Integer)

#教师信息表
class Teachers(Base):
    __tablename__ = "Teachers"
    Tid = Column(String(64),primary_key=True)
    Uid = Column(String(64),nullable=False)
    Tname = Column(String(16),nullable=False)
    Tno = Column(String(16),nullable=False)
    Ttel = Column(String(16),nullable=False)
    Tuniversity = Column(String(256),nullable=False)
    Tschool = Column(String(256),nullable=False)
    Ttime = Column(Integer)

#竞赛信息表
class Competitions(Base):
    __tablename__ = "Competitions"
    Cid = Column(String(64),primary_key=True)
    Cname = Column(String(128),nullable=False)
    Cno = Column(Integer,nullable=False)
    Clevel = Column(Integer,nullable=False)
    Cstart = Column(String(10),nullable=False)
    Cend = Column(String(10),nullable=False)
    Cmin = Column(Integer)
    Cmax = Column(Integer)
    Cown = Column(String(64))
    Cabo = Column(Text,nullable=False)

#团队表
class Teams(Base):
    __tablename__ = "Teams"
    TEid = Column(String(64),primary_key=True)
    Sid = Column(String(64),nullable=False)
    TStyle = Column(Integer,nullable=False)

#团队教师关联表
class TTeacher(Base):
    __tablename__ = "TTeacher"
    TEid = Column(String(64),nullable=False)
    Tid = Column(String(64),nullable=False)

#学生技能表
class STechs(Base):
    STid = Column(String(64),primary_key=True)
    Sid = Column(String(64),nullable=False)
    STname = Column(String(32),nullable=False)
    STlevel = Column(Integer,nullable=False)

#学生竞赛简历
class SCuse(Base):
    SCid = Column(String(64),primary_key=True)
    Sid = Column(String(64),nullable=False)
    SCname = Column(String(128),nullable=False)
    SCno = Column(String(16),nullable=False)

#教师竞赛简历
class Tcuse(Base):
    TCid = Column(String(64),primary_key=True)
    Tid = Column(String(64),nullable=False)
    TCname = Column(String(128),nullable=False)
    TCon = Column(String(16),nullable=False)
    TCnum = Column(Integer,nullable=False,default=1)

