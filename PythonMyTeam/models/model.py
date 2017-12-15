# -*- coding:utf-8-*-
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import Column, Index, Integer, String, UniqueConstraint,Text

Base = declarative_base()


class Uers(Base):
    __tablename__ = "Users"
    Uid = Column(String(64), primary_key=True)
    Uname = Column(String(32),nullable=False)
    Upwd = Column(String(32),nullable=False)
    Utype = Column(Integer,nullable=False)

class Students(Base):
    __tablename__ = "Students"
    Sid = Column(String(64),primary_key=True)
    Uid = Column(String(64),nullable=False)
    Sname = Column(String(32),nullable=False)
    Sno = Column(String(16),nullable=False)
    Suniversity = Column(String(256),nullable=False)
    Sschool = Column(String(32),nullable=False)
    Stel = Column(String(16),nullable=False)
    Sgrade = Column(Integer)
    Ssex = Column(Integer)

class Teachers(Base):
    __tablename__ = "Teachers"
    Tid = Column(String(64),primary_key=True)
    Uid = Column(String(64),nullable=False)
    Tname = Column(String(16),nullable=False)
    Tno = Column(String(16),nullable=False)
    Ttel = Column(String(16),nullable=False)
    Tuniversity = Column(String(256),nullable=False)
    Tschool = Column(String(32),nullable=False)
    Ttime = Column(Integer)

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

class Teams(Base):
    __tablename__ = "Teams"
    TEid = Column(String(64),primary_key=True)
    Sid = Column(String(64),nullable=False)
    TStyle = Column(Integer,nullable=False)

class TTeacher(Base):
    __tablename__ = "TTeacher"
    TEid = Column(String(64),nullable=False)
    Tid = Column(String(64),nullable=False)

class STechs(Base):
    STid = Column(String(64),primary_key=True)
    Sid = Column(String(64),nullable=False)
    STname = Column(String(32),nullable=False)
    STlevel = Column(Integer,nullable=False)

class SCuse(Base):
    SCid = Column(String(64),primary_key=True)
    Sid = Column(String(64),nullable=False)
    SCname = Column(String(128),nullable=False)
    SCno = Column(String(16),nullable=False)

class Tcuse(Base):
    TCid = Column(String(64),primary_key=True)
    Tid = Column(String(64),nullable=False)
    TCname = Column(String(128),nullable=False)
    TCon = Column(String(16),nullable=False)
    TCnum = Column(Integer,nullable=False,default=1)

