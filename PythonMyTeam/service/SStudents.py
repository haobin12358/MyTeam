# *- coding:utf8 *-
#引用项目类
import DBSession
#操作students表的相关方法
class SStudents():
    def __init__(self):
        try:
            self.session = DBSession.db_session() #初始化
            self.status = True #session异常的判断标记
        except Exception as e:
            print e.message
            self.status = False