# -*- coding:utf-8-*-
import DBSession
from models import model
session = DBSession.db_session()
def add_user(uid,uname,upwd,utype):
    new_user = model.Uers()
    new_user.Uid = uid
    new_user.Uname = uname
    new_user.Upwd = upwd
    new_user.Utype = utype
    session.add(new_user)
    session.commit()

if __name__ == "__main__":
    import uuid
    id = uuid.uuid4()
    print id
    add_user(id ,"haobin","haobin",102)