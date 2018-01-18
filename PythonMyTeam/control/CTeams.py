# *- coding:utf8 *-
# 兼容linux系统
import sys
import os
sys.path.append(os.path.dirname(os.getcwd())) # 增加系统路径
# 引用python类
from flask import request
import json
import uuid

# 引用项目类
from common.JudgeData import JudgeData
from common.get_model_return_list import get_model_return_list
from common.get_str import get_str
from common.MyException import ParamsNotExitError
from service.STeams import STeams
from service.SPersonal import SPersonal
from service.SInfor import SInfor
from service.SStudents import SStudents
from service.STeachers import STeachers
from service.SCompetitions import SCompetitions
from Config.Requests import system_error, param_miss, new_team_success, none_permissions, update_team_success, \
    invent_success, team_is_full, join_team_success, delete_team_success, delete_student_from_team_success
from Config.Logs import WRITE_STUDENT_TEAM_ERROR, NEW_INVITATION, NEW_REQUEST, JOIN_TEAM_SUCCESS,\
    TEACHER_JOIN_TEAM_SUCCESS

# 用于处理团队相关数据
class CTeams():
    def __init__(self):
        self.steams = STeams()
        self.judgeData = JudgeData()#实例化
        self.spersonal = SPersonal()
        self.sinfor = SInfor()
        self.sstudent = SStudents()
        self.steacher = STeachers()
        self.scompetitions = SCompetitions()

    # 展现团队列表，场景应用于团队信息和个人团队信息
    def teams_list(self):

        if not self.steams.status:  # 校验数据库是否连接异常
            return system_error
        args = request.args.to_dict()
        # 判断是否含有参数
        params = []
        try:
            # 参数成对存在，判断是否缺失,并判断具体内容是否合法，非法或为空均报错
            page_num, page_size = self.judgeData.check_page_params(args)
            from models.model import Students
            if "start" in args:
                params.append(Students.Sgrade >= args.get("start"))
            if "end" in args:
                params.append(Students.Sgrade <= args.get("end"))
            if "TEname" in args:
                tename = get_str(args, "TEname")
                # params.append(Students.Sname.like("%{0}%".format(name)))
            if "Cname" in args:
                cname = get_str(args, "Cname")
                # params.append(Students.Sschool.like("%{0}%".format(school)))

            page_num, count = JudgeData.check_page_value(page_num, page_size, "Students", params)
            start_num = (page_num - 1) * page_size
            # search_student_list_success["student_list"] = self.get_students_list(start_num, page_size, params)
            # search_student_list_success["count"] = count
            # search_student_list_success["page_num"] = page_num
            # return search_student_list_success
        except(ParamsNotExitError)as e:
            print e.message
            return param_miss
        except ValueError as e:
            print e.message
            # return param_notright.fromkeys(e.message)
        except Exception as e:
            print e.message
            return system_error

    # 团队信息详情，根据团队唯一ID进行检索
    def team_abo(self):
        return system_error

    # 新建团队（创建团队信息表和团队学生表的主要人员），入口在竞赛信息和团队板块的创建团队
    def new_team(self):
        args = request.args.to_dict() # 获取参数
        print args
        # 判断参数非空
        if not args:
            return param_miss
        # 判断参数中含有Uid
        if not self.judgeData.inData("Uid", args):
            return param_miss

        uid = args["Uid"]

        data = request.data # 获取body体
        # 判断body体非空
        if data == {} or str(data) == "":
            return param_miss
        data = json.loads(data)

        # 判断body体中含有必要参数
        if not self.judgeData.inData("TEname", data) or not self.judgeData.inData("Cid", data) \
            or not self.judgeData.inData("TEnum", data):
            return param_miss

        tename = data["TEname"]
        cid = data["Cid"]
        tenum = data["TEnum"]
        teid = uuid.uuid4()

        sid = self.spersonal.get_sid_by_uid(uid)

        # 创建团队信息
        add_team = self.steams.add_team(teid, tename, cid, 701, tenum)

        if not add_team:
            return system_error

        # 写入团队创始人信息
        add_team_student = self.steams.add_student_in_team(uuid.uuid4(), teid, sid, 1000, 1101)

        if not add_team_student:
            delete_team = self.steams.delete_team_by_teid(teid)
            if delete_team:
                print WRITE_STUDENT_TEAM_ERROR
            return system_error

        # 如果data中含有students的信息，那么写入团队成员信息，这个函数重构
        if self.judgeData.inData("Students", data):
            for row in data["Students"]:
                if self.judgeData.inData("Sid", row):
                    add_team_student_list = self.steams.add_student_in_team(uuid.uuid4(), teid, row["Sid"], 1002, 1100)
                    uid = self.sstudent.get_uid_by_sid(row["Sid"])
                    add_infor = self.sinfor.add_infor(uuid.uuid4(), uid, NEW_INVITATION, 1200, 901, cid)  # 这里需要判断一下分步异常的问题

        if self.judgeData.inData("Teachers", data):
            for row in data["Teachers"]:
                if self.judgeData.inData("Tid", row):
                    add_team_teacher_list = self.steams.add_teacher_in_team(uuid.uuid4(), teid, row["Tid"], 1100)
                    uid = self.steacher.get_uid_by_tid(row["Tid"])
                    add_infor = self.sinfor.add_infor(uuid.uuid4(), uid, NEW_INVITATION, 1200, 901, cid) # 这里需要判断一下分步异常的问题

        return new_team_success

    # 更新团队信息，入口在团队信息详情，可更新数据不包含竞赛信息的大部分内容，如更新竞赛信息，只能更新竞赛的等级
    def update_team(self):
        update_team_item = {}  # 新建空的json变量

        args = request.args.to_dict() # 获取参数
        print args
        # 判断参数非空
        if not args:
            return param_miss
        # 判断参数中含有Uid
        if not self.judgeData.inData("Uid", args):
            return param_miss

        uid = args["Uid"]
        sid = self.spersonal.get_sid_by_uid(uid) # 获取sid

        data = request.data # 获取body体
        # 判断body体非空
        if data == {} or str(data) == "":
            return param_miss
        data = json.loads(data)

        # 判断body体中含有必要参数
        if not self.judgeData.inData("TEname", data) or not self.judgeData.inData("Cname", data) \
            or not self.judgeData.inData("TEnum", data) or not self.judgeData.inData("Cno", data) \
                or not self.judgeData.inData("Clevel", data) or not self.judgeData.inData("TEid", data):
            return param_miss

        cid = self.scompetitions.get_cid_by_cname_cno_clevel(data["Cname"], data["Cno"], data["Clevel"])
        teid = data["TEid"]
        tstype = self.steams.get_tstype_by_teid_sid(teid, sid)  # 获取成员身份判断权限

        # 不是团队创建者或管理员，提醒没有权限
        if tstype != 1000 or tstype != 1001:
            return none_permissions

        # 如果具有权限，继续执行
        update_team_item["TEid"] = data["TEid"]
        update_team_item["TEname"] = data["TEname"]
        update_team_item["Cid"] = cid
        update_team_item["TEuse"] = 701 # 被废弃的团队是不可修改的，所以可修改的状态默认为701
        update_team_item["TEnum"] = data["TEnum"]
        # 更新团队表
        response_of_update = self.steams.update_teams_by_teid(teid, update_team_item)

        if not response_of_update:
            return system_error
        return update_team_success

    # 邀请学生与申请加入团队，1.入口在学生信息和团队详情中2.入口在团队信息列表中(邀请为0，申请为1，用instatus标记)
    def add_student(self):
        args = request.args.to_dict() # 获取参数
        print args
        # 判断参数非空
        if not args:
            return param_miss
        # 判断参数中含有Uid
        if not self.judgeData.inData("TEid", args) or not self.judgeData.inData("Instatus", args):
            return param_miss

        teid = args["TEid"]
        instatus = args["Instatus"]

        data = request.data # 获取body体
        # 判断body体非空
        if data == {} or str(data) == "":
            return param_miss
        data = json.loads(data)

        # 判断body体中含有必要参数
        if not self.judgeData.inData("Sid", data):
            return param_miss

        sid = data["Sid"]
        cid = self.steams.get_cid_by_teid(teid)

        # 创建学生团队关联，创建通知信息
        response_of_student_and_team = self.steams.add_student_in_team(uuid.uuid4(), teid, sid, 1002, 1100)
        if instatus == 0:
            uid = self.sstudent.get_uid_by_sid(sid)
            response_of_infor = self.sinfor.add_infor(uuid.uuid4(), uid, NEW_INVITATION, 1200, 901, cid, teid, None)
        else:
            creator_sid = self.steams.get_sid_by_teid(teid)
            uid = self.sstudent.get_student_use_by_sid(creator_sid)
            response_of_infor = self.sinfor.add_infor(uuid.uuid4(), uid, NEW_REQUEST, 1200, 905, None, None, sid)  # 逻辑有点乱

        if not response_of_student_and_team or not response_of_infor:
            return system_error

        return invent_success

    # 邀请教师，入口在教师信息和团队详情中
    def add_teacher(self):
        args = request.args.to_dict() # 获取参数
        print args
        # 判断参数非空
        if not args:
            return param_miss
        # 判断参数中含有Uid
        if not self.judgeData.inData("TEid", args):
            return param_miss

        teid = args["TEid"]

        data = request.data # 获取body体
        # 判断body体非空
        if data == {} or str(data) == "":
            return param_miss
        data = json.loads(data)

        # 判断body体中含有必要参数
        if not self.judgeData.inData("Tid", data):
            return param_miss

        tid = data["Tid"]
        uid = self.steacher.get_uid_by_tid(tid)
        cid = self.steams.get_cid_by_teid(teid)

        # 创建学生团队关联，创建通知信息
        response_of_student_and_team = self.steams.add_teacher_in_team(uuid.uuid4(), teid, tid, 1100)
        response_of_infor = self.sinfor.add_infor(uuid.uuid4(), uid, NEW_INVITATION, 1200, 901, cid, teid, None)

        if not response_of_student_and_team or not response_of_infor:
            return system_error
        return invent_success

    # 学生同意加入、通过学生申请，入口在我的信息中, 目前只完成了学生同意加入部分
    def sub_student(self):
        update_tstudent_item = {} # 新建空的json变量
        args = request.args.to_dict() # 获取参数
        print args
        # 判断参数非空
        if not args:
            return param_miss
        # 判断参数中含有Uid
        if not self.judgeData.inData("Uid", args):
            return param_miss

        uid = args["Uid"]

        data = request.data # 获取body体
        # 判断body体非空
        if data == {} or str(data) == "":
            return param_miss
        data = json.loads(data)

        # 判断body体中含有必要参数
        if not self.judgeData.inData("TEid", data):
            return param_miss

        teid = data["TEid"]

        # 根据团队id获取当前团队成员的数量，与团队最大数量进行对比，超过则加入失败
        # 这里需要对teid进行防sql注入处理
        students_num = self.steams.get_count_by_teid(teid)
        tenum = self.steams.get_tenum_by_teid(teid)

        if students_num >= tenum:
            return team_is_full

        # 判断该学生是否加入该竞赛的其他团队，待补充

        sid = self.spersonal.get_sid_by_uid(uid)
        sname = self.sstudent.get_sname_by_sid(sid)
        tename = self.steams.get_tename_by_teid(teid)
        tsid = self.steams.get_tsid_by_teid_sid(teid, sid)
        team_leader_sid = self.steams.get_sid_by_teid(teid)
        team_leader_uid = self.sstudent.get_uid_by_sid(team_leader_sid)
        update_tstudent_item["TSid"] = tsid
        update_tstudent_item["Sid"] = sid
        update_tstudent_item["TEid"] = teid
        update_tstudent_item["TStype"] = 1002
        update_tstudent_item["TSsubject"] = 1101

        message = JOIN_TEAM_SUCCESS.format(sname, tename)  # 信息报文
        response_of_update_tstudent = self.steams.update_tstudent_by_tsid(tsid, update_tstudent_item)
        response_of_infor = self.sinfor.add_infor(uuid.uuid4(), team_leader_uid, message, 1200, 903, None, None, None)

        if not response_of_infor or not response_of_update_tstudent:
            return system_error

        return join_team_success

    # 教师同意加入，入口在我的信息中
    def sub_teacher(self):
        update_tteacher_item = {} # 新建空的json变量
        args = request.args.to_dict() # 获取参数
        print args
        # 判断参数非空
        if not args:
            return param_miss
        # 判断参数中含有Uid
        if not self.judgeData.inData("Uid", args):
            return param_miss

        uid = args["Uid"]

        data = request.data # 获取body体
        # 判断body体非空
        if data == {} or str(data) == "":
            return param_miss
        data = json.loads(data)

        # 判断body体中含有必要参数
        if not self.judgeData.inData("TEid", data):
            return param_miss

        teid = data["TEid"]

        # 根据团队id获取当前团队成员的数量，与团队最大数量进行对比，超过则加入失败
        # 这里需要对teid进行防sql注入处理
        teacher_num = self.steams.get_tcount_by_teid(teid)

        if teacher_num >= 1:
            return team_is_full

        # 判断该学生是否加入该竞赛的其他团队，待补充

        tid = self.spersonal.get_tid_by_uid(uid)
        tname = self.steacher.get_tname_by_tid(tid)
        tename = self.steams.get_tename_by_teid(teid)
        ttid = self.steams.get_ttid_by_teid_tid(teid, tid)
        team_leader_sid = self.steams.get_sid_by_teid(teid)
        team_leader_uid = self.sstudent.get_uid_by_sid(team_leader_sid)
        update_tteacher_item["TTid"] = ttid
        update_tteacher_item["Tid"] = tid
        update_tteacher_item["TEid"] = teid
        update_tteacher_item["TTsubject"] = 1101

        message = TEACHER_JOIN_TEAM_SUCCESS.format(tname, tename)  # 信息报文
        response_of_update_tteacher = self.steams.update_tteacher_by_ttid(ttid, update_tteacher_item)
        response_of_infor = self.sinfor.add_infor(uuid.uuid4(), team_leader_uid, message, 1200, 903, None, None, None)

        if not response_of_infor or not response_of_update_tteacher:
            return system_error

        return join_team_success

    # 新增团队任务，入口在团队详情中
    def add_task(self):
        return system_error

    # 修改团队任务完成情况，入口在团队详情中
    def sub_task(self):
        return system_error

    # 修改团队任务内容，入口在团队详情中
    def update_task(self):
        return system_error

    # 删除团队，入口在我的团队中，实际修改团队的可用属性
    def delete_team(self):
        args = request.args.to_dict()  # 获取参数
        print args
        # 判断参数非空
        if not args:
            return param_miss
        # 判断参数中含有Uid
        if not self.judgeData.inData("Uid", args):
            return param_miss

        uid = args["Uid"]

        data = request.data  # 获取body体
        # 判断body体非空
        if data == {} or str(data) == "":
            return param_miss
        data = json.loads(data)

        # 判断body体中含有必要参数
        if not self.judgeData.inData("TEid", data):
            return param_miss

        teid = data["TEid"]
        sid = self.spersonal.get_sid_by_uid(uid)
        tstype = self.steams.get_tstype_by_teid_sid(teid, sid)

        if tstype != 1000:
            return none_permissions

        teuse = 702

        update_team_item = {}
        update_team_item["TEid"] = teid
        update_team_item["TEuse"] = teuse

        response_of_update_team = self.steams.update_teams_by_teid(teid, update_team_item)

        if not response_of_update_team:
            return system_error
        return delete_team_success

    # 删除团队学生，入口在团队详情中，实际修改团队学生表的属性
    def delete_student(self):
        args = request.args.to_dict()  # 获取参数
        print args
        # 判断参数非空
        if not args:
            return param_miss
        # 判断参数中含有Uid
        if not self.judgeData.inData("Uid", args):
            return param_miss

        uid = args["Uid"]

        data = request.data  # 获取body体
        # 判断body体非空
        if data == {} or str(data) == "":
            return param_miss
        data = json.loads(data)

        # 判断body体中含有必要参数
        if not self.judgeData.inData("TEid", data):
            return param_miss

        teid = data["TEid"]
        sid = self.spersonal.get_sid_by_uid(uid)
        tstype = self.steams.get_tstype_by_teid_sid(teid, sid)

        if tstype != 1000 :
            return none_permissions

        tsid = self.steams.get_tsid_by_teid_sid(teid, sid)

        delete_student = {}
        delete_student["TSsubject"] = 1103

        response_of_delete_student = self.steams.update_tstudent_by_tsid(tsid, delete_student)

        if not response_of_delete_student:
            return system_error

        return delete_student_from_team_success

    # 删除团队教师，入口在团队详情中，实际修改团队教师表的属性
    def delete_teacher(self):
        args = request.args.to_dict()  # 获取参数
        print args
        # 判断参数非空
        if not args:
            return param_miss
        # 判断参数中含有Uid
        if not self.judgeData.inData("Uid", args):
            return param_miss

        uid = args["Uid"]

        data = request.data  # 获取body体
        # 判断body体非空
        if data == {} or str(data) == "":
            return param_miss
        data = json.loads(data)

        # 判断body体中含有必要参数
        if not self.judgeData.inData("TEid", data):
            return param_miss

        teid = data["TEid"]
        tid = self.spersonal.get_tid_by_uid(uid)
        tstype = self.steams.get_tstype_by_teid_sid(teid, tid)

        if tstype != 1000 :
            return none_permissions

        tsid = self.steams.get_tsid_by_teid_sid(teid, tid)

        delete_student = {}
        delete_student["TSsubject"] = 1103

        response_of_delete_student = self.steams.update_tstudent_by_tsid(tsid, delete_student)

        if not response_of_delete_student:
            return system_error

        return delete_student_from_team_success

    # 删除团队任务，入口在团队详情中
    def delete_task(self):
        return system_error