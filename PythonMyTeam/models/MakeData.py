# *- coding:utf8 *-
# 兼容linux系统
import sys
import os
sys.path.append(os.path.dirname(os.getcwd())) # 增加系统路径
import uuid
from service.SStudents import SStudents
from service.STeachers import STeachers
from service.SPersonal import SPersonal
from service.SCompetitions import SCompetitions
from service.SUsers import SUsers
from service.STeams import STeams
from service.SInfor import SInfor

class MakeData():
    def __init__(self):
        self.student = SStudents()
        self.teacher = STeachers()
        self.personal = SPersonal()
        self.competition = SCompetitions()
        self.user = SUsers()
        self.team = STeams()
        self.infor = SInfor()

    def make_id(self):
        user_ids = []
        student_ids = []
        teacher_ids = []
        competition_ids = []
        team_ids = []
        i = 0
        while i < 22:
            user_ids.append(uuid.uuid4())
            student_ids.append(uuid.uuid4())
            teacher_ids.append(uuid.uuid4())
            competition_ids.append(uuid.uuid4())
            team_ids.append(uuid.uuid4())
            i = i + 1
        return user_ids, student_ids, teacher_ids, competition_ids, team_ids

    def add_users(self, user_ids):
        i = 0
        name = "test"
        pwd = "123"
        utype = 100
        while i < 22:
            self.user.add_user(user_ids[i], name + str(i), pwd, utype)
            i = i + 1
            if i > 10:
                utype = 101

    def add_students(self, user_ids, student_ids):
        i = 0
        name = "student"
        no = "130323"
        university = "杭州电子科技大学"
        school = "管理学院"
        tel = "135880460"
        grade = 2018
        sex = 201
        while i < 11:
            self.personal.add_student_abo_by_uid(student_ids[i], user_ids[i],
                                                 name + str(i), no + str(i), university,
                                                 school, tel + str(i), grade, sex)
            i = i + 1
            if i > 5:
                sex = 202

    def add_teachers(self, user_ids, teacher_ids):
        i = 0
        name = "teacher"
        no = "7403"
        university = "杭州电子科技大学"
        school = "管理学院"
        tel = "177064411"
        while i < 11:
            self.personal.add_teacher_abo_by_uid(user_ids[i+11], teacher_ids[i],
                                                 name + str(i), no, tel + str(i) + str(i), university, school, i + 1)
            i = i + 1

    def add_competitions(self, competition_ids):
        i = 0
        name = "test_competition"
        level = 1
        start = "2018-01-22"
        end = "2019-01-22"
        min = 3
        max = 5
        own = "浙江团省委"
        abo = "测试数据"
        while i < 22:
            self.competition.add_competitions(competition_ids[i], name + str(i), i, level, start, end, min, max, own, abo)
            i = i + 1

    def add_team(self, team_ids, competition_ids):
        i = 0
        name = "test_team"
        while i < 22:
            self.team.add_team(team_ids[i], name + str(i), competition_ids[i], 701, 5)
            i = i + 1

    def add_tsstudent(self, team_ids, student_ids):
        i = 0
        while i < 6:
            self.team.add_student_in_team(uuid.uuid4(), team_ids[i], student_ids[i], 1000, 1101)
            self.team.add_student_in_team(uuid.uuid4(), team_ids[i], student_ids[i + 1], 1001, 1101)
            self.team.add_student_in_team(uuid.uuid4(), team_ids[i], student_ids[i + 2], 1001, 1101)
            self.team.add_student_in_team(uuid.uuid4(), team_ids[i], student_ids[i + 3], 1002, 1101)
            self.team.add_student_in_team(uuid.uuid4(), team_ids[i], student_ids[i + 4], 1002, 1101)
            self.team.add_student_in_team(uuid.uuid4(), team_ids[i], student_ids[i + 5], 1002, 1102)
            i = i + 1

    def add_tsteacher(self, team_ids, teacher_ids):
        i = 0
        while i < 6:
            self.team.add_teacher_in_team(uuid.uuid4(), team_ids[i], teacher_ids[i], 1)
            i = i + 1

    def add_stech(self, student_ids):
        i = 0
        name = "test_tech"
        while i < 11:
            self.personal.add_student_tech_by_sid(uuid.uuid4(), student_ids[i], name + str(i), i%5 + 1)
            self.personal.add_student_tech_by_sid(uuid.uuid4(), student_ids[i], name + str(i + 1), i % 5 + 1)
            i = i + 1

    def add_scuse(self, student_ids):
        i = 0
        name = "测试竞赛简历"
        no = "一等奖"
        while i < 11:
            self.personal.add_student_use_by_sid(uuid.uuid4(), student_ids[i], name + "1", no)
            self.personal.add_student_use_by_sid(uuid.uuid4(), student_ids[i], name + "2", no)
            i = i + 1

    def add_tcuse(self, teacher_ids):
        i = 0
        name = "测试竞赛简历"
        no = "一等奖"
        while i < 11:
            self.personal.add_teacher_use_by_tid(uuid.uuid4(), teacher_ids[i], name + "1", no, i)
            i = i + 1

if __name__ == "__main__":
    data = MakeData()
    tuser_ids, tstudent_ids, tteacher_ids, tcompetition_ids, tteam_ids = data.make_id()
    data.add_users(tuser_ids)
    data.add_students(tuser_ids, tstudent_ids)
    data.add_teachers(tuser_ids, tteacher_ids)
    data.add_competitions(tcompetition_ids)
    data.add_team(tteam_ids, tcompetition_ids)
    data.add_tsstudent(tteam_ids, tstudent_ids)
    data.add_tsteacher(tteam_ids, tteacher_ids)
    data.add_stech(tstudent_ids)
    data.add_scuse(tstudent_ids)
    data.add_tcuse(tteacher_ids)