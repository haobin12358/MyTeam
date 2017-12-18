# *- coding:utf8 *-
from models import model
from sqlalchemy.orm import sessionmaker

db_session = sessionmaker(bind=model.mysql_engine)

