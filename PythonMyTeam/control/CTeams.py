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
from service.SUsers import SUsers
from Config.Requests import system_error, param_miss, new_team_success, none_permissions, update_team_success, \
    invent_success, team_is_full, join_team_success, delete_team_success, delete_student_from_team_success, \
    refuse_join_team_success, have_invate_this_users
from Config.Logs import WRITE_STUDENT_TEAM_ERROR, NEW_INVITATION, NEW_REQUEST, JOIN_TEAM_SUCCESS,\
    TEACHER_JOIN_TEAM_SUCCESS, REFUSE_JOIN_TEAM, TEACHER_REFUSE_JOIN_TEAM

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
        self.suser = SUsers()
        if self.sinfor.status or self.sstudent.status or self.steacher.status or \
                self.scompetitions.status or self.suser.status:
            self.status = True
        else:
            self.status = False

    # 展现团队列表，场景应用于团队信息和个人团队信息，没有校检
    def teams_list(self):
        if not self.steams.status:  # 校验数据库是否连接异常
            return system_error
        args = request.args.to_dict()

        # 判断是否含有参数
        try:
            # 参数成对存在，判断是否缺失,并判断具体内容是否合法，非法或为空均报错
            page_num, page_size = self.judgeData.check_page_params(args)
            if "Tname" in args:
                tename = get_str(args, "TEname")
                # params.append(Students.Sname.like("%{0}%".format(name)))
            if "Cname" in args:
                cname = get_str(args, "Cname")
                # params.append(Students.Sschool.like("%{0}%".format(school)))
            if "Cno" in args:
                cno = get_str(args, "Cno")
            if "Clevel" in args:
                clevel = get_str(args, "Clevel")
            if "Sname" in args:
                sname = get_str(args, "Sname")
            if "isFull" in args:
                isFull = get_str(args, "Sname")

            team_list_data = self.steams.get_all_teams(page_size*(page_num-1)+1,page_size)
            result_of_team_list = []
            for row in team_list_data:
                result_of_team_item = {}
                result_of_team_item["TEid"] = row.TEid
                result_of_team_item["TEname"] = row.TEname
                tenum = row.TEnum
                tenum_now = self.steams.get_count_by_teid(row.TEid)
                if tenum != 0 and tenum > tenum_now:
                    result_of_team_item["isFull"] = 0
                else:
                    result_of_team_item["isFull"] = 1
                competitions_item = self.steams.get_cname_cno_clevel_by_cid(row.Cid)
                result_of_team_item["Cname"] = competitions_item.Cname
                result_of_team_item["Cno"] = competitions_item.Cno
                result_of_team_item["Clevel"] = competitions_item.Clevel
                leader_id = self.steams.get_sid_by_teid(row.TEid)
                result_of_team_item["TEleader"] = self.sstudent.get_sname_by_sid(leader_id)
                teacher_id = self.steams.get_tid_by_teid(row.TEid)
                result_of_team_item["TEteachername"] = self.steacher.get_tname_by_tid(teacher_id)

                result_of_team_list.append(result_of_team_item)
            result_response = {}
            result_response["status"] = 200
            result_response["messages"] = ""
            result_response["team_list"] = result_of_team_list
            return result_response

        except(ParamsNotExitError)as e:
            print e.message
            return param_miss
        except ValueError as e:
            print e.message
            # return param_notright.fromkeys(e.message)
        except Exception as e:
            print e.message
            return system_error

    # 团队信息详情，根据团队唯一ID进行检索，目前写的非常粗糙，没有进行权限的校检，也没有进行数据的校检，单纯算是完成了正向逻辑
    def team_abo(self):
        if not self.steams.status:  # 校验数据库是否连接异常
            return system_error
        args = request.args.to_dict()

        if "TEid" not in args or "Uid" not in args:
            return param_miss
        teid = get_str(args, "TEid")
        uid = get_str(args, "Uid")
        utype = self.suser.get_utype_by_uid(uid)
        in_team = True

        result_of_team_abo = {}
        result_of_team_student_list = []


        team_abo = self.steams.get_team_abo_by_teid(teid)
        result_of_team_abo["TEid"] = team_abo.TEid
        result_of_team_abo["TEname"] = team_abo.TEname
        result_of_team_abo["TEnum"] = team_abo.TEnum
        cid = team_abo.Cid
        competition_abo = self.scompetitions.get_competitions_abo_by_cid(cid)
        result_of_team_abo["Cname"] = competition_abo[0].Cname
        result_of_team_abo["Cno"] = competition_abo[0].Cno
        result_of_team_abo["Clevel"] = competition_abo[0].Clevel
        result_of_team_abo["Cabo"] = competition_abo[0].Cabo
        leader_id = self.steams.get_sid_by_teid(teid)
        result_of_team_abo["TEleader"] = self.sstudent.get_sname_by_sid(leader_id)
        teacher_id = self.steams.get_tid_by_teid(teid)
        result_of_team_abo["Teachername"] = self.steacher.get_tname_by_tid(teacher_id)
        wait_num, refuse_num = self.steams.get_count_wait_refuse_by_teid(teid)
        result_of_team_abo["Wait"] = wait_num
        result_of_team_abo["Refuse"] = refuse_num
        team_student = self.steams.get_student_list_by_teid(teid)
        for row in team_student:
            result_of_team_student_item = {}
            result_of_team_student_item["Sid"] = row.Sid
            result_of_team_student_item["TStype"] = row.TStype
            result_of_team_student_item["Sname"] = self.sstudent.get_sname_by_sid(row.Sid)
            result_of_team_student_list.append(result_of_team_student_item)
        result_of_team_abo["student_list"] = result_of_team_student_list
        result_response = {}
        result_response["status"] = 200
        result_response["messages"] = ""
        result_response["team_abo"] = result_of_team_abo

        return result_response

    # 新建团队（创建团队信息表和团队学生表的主要人员），入口在竞赛信息和团队板块的创建团队
    def new_team(self):
        add_status = True
        if not self.status:
            return system_error
        args = request.args.to_dict() # 获取参数
        print args

        # 判断参数中含有Uid
        if not self.judgeData.inData("Uid", args):
            return param_miss

        uid = args["Uid"]

        data = request.data # 获取body体
        data = json.loads(data)
        # 判断body体中含有必要参数
        if not self.judgeData.inData("TEname", data) or not self.judgeData.inData("Cname", data) \
            or not self.judgeData.inData("TEnum", data) or not self.judgeData.inData("Cno", data) \
                or not self.judgeData.inData("Clevel", data):
            return param_miss

        cname = data["Cname"]
        cno = data["Cno"]
        clevel = data["Clevel"]

        cid = self.scompetitions.get_cid_by_cname_cno_clevel(cname,cno,clevel)
        tename = data["TEname"]
        tenum = data["TEnum"]
        teid = uuid.uuid4()

        # 创建团队信息
        add_team = self.steams.add_team(teid, tename, cid, 701, tenum)
        if not add_team and add_status:
            add_status = False

        sid = self.sstudent.get_sid_by_uid(uid)
        # 写入团队创始人信息
        add_team_student = self.steams.add_student_in_team(uuid.uuid4(), teid, sid, 1000, 1101)
        if not add_team_student and add_status:
            add_status = False

        # 如果data中含有students的信息，那么写入团队成员信息，这个函数重构
        if self.judgeData.inData("Students", data):
            for row in data["Students"]:
                if not self.judgeData.inData("Sno", row) or not self.judgeData.inData("Suniversity", row):
                    return param_miss
                else:
                    sid = self.sstudent.get_sid_by_sno_suniversity(row["Sno"], row["Suniversity"])
                    add_team_student_list = self.steams.add_student_in_team(uuid.uuid4(), teid, sid, 1002, 1100)
                    if not add_team_student_list and add_status:
                        add_status = False
                    uid2 = self.sstudent.get_uid_by_sid(sid)  # 根据students中所有的sid来获取
                    add_infor = self.sinfor.add_infor(uuid.uuid4(), uid, NEW_INVITATION, 1200, 901, cid, teid, uid2) # 这里需要判断一下分步异常的问题
                    if not add_infor and add_status:
                        add_status = False

        if self.judgeData.inData("Teachers", data):
            for row in data["Teachers"]:
                if not self.judgeData.inData("Tno", row) or not self.judgeData.inData("Tuniversity", row):
                    return param_miss
                else:
                    tid = self.steacher.get_tid_by_tno_tuniversity(row["Tno"], row["Tuniversity"])
                    add_team_teacher_list = self.steams.add_teacher_in_team(uuid.uuid4(), teid, tid, 1100)
                    if not add_team_teacher_list and add_status:
                        add_status = False
                    uid2 = self.steacher.get_uid_by_tid(tid)
                    add_infor = self.sinfor.add_infor(uuid.uuid4(), uid, NEW_INVITATION, 1200, 901, cid, teid, uid2) # 这里需要判断一下分步异常的问题
                    if not add_infor and add_status:
                        add_status = False
        if not add_status:
            return system_error # 应返回写入数据库异常
        return new_team_success

    # 更新团队信息，入口在团队信息详情，可更新数据不包含竞赛信息的大部分内容，如更新竞赛信息，只能更新竞赛的等级
    def update_team(self):
        if not self.status:
            return system_error
        update_team_item = {}  # 新建空的json变量

        args = request.args.to_dict() # 获取参数
        print args
        # 判断参数中含有Uid
        if not self.judgeData.inData("Uid", args):
            return param_miss

        uid = args["Uid"]
        sid = self.sstudent.get_sid_by_uid(uid) # 获取sid

        data = request.data # 获取body体
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
        if tstype != 1000 and tstype != 1001:
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

    # 邀请学生加入团队，入口在学生信息和团队详情中
    def add_student(self):
        add_status = True
        if not self.status:
            return system_error
        args = request.args.to_dict() # 获取参数
        print args

        # 判断参数中含有Uid和TEid
        if not self.judgeData.inData("TEid", args) or not self.judgeData.inData("Uid", args):
            return param_miss

        teid = args["TEid"]
        uid = args["Uid"]

        data = request.data # 获取body体
        data = json.loads(data)

        # 判断body体中含有必要参数
        if not self.judgeData.inData("Sno", data) or not self.judgeData.inData("Suniversity", data):
            return param_miss

        sno = data["Sno"]
        suniversity = data["Suniversity"]
        sid = self.sstudent.get_sid_by_sno_suniversity(sno, suniversity)
        sid_list = self.steams.get_sid_list_by_teid(teid)
        if sid in sid_list:
            return have_invate_this_users
        cid = self.steams.get_cid_by_teid(teid)
        uid2 = self.sstudent.get_uid_by_sid(sid)

        # 创建学生团队关联，创建通知信息
        response_of_student_and_team = self.steams.add_student_in_team(uuid.uuid4(), teid, sid, 1002, 1100)
        if not response_of_student_and_team and add_status:
            add_status = False

        response_of_infor = self.sinfor.add_infor(uuid.uuid4(), uid, NEW_INVITATION, 1200, 901, cid, teid, uid2)
        if not response_of_infor and add_status:
            add_status = False

        if not add_status:
            return system_error
        return invent_success

    # 申请加入团队
    def invate_add_team(self):
        add_status = True
        if not self.status:
            return system_error
        args = request.args.to_dict() # 获取参数
        print args

        # 判断参数中含有Uid和TEid
        if not self.judgeData.inData("Uid", args):
            return param_miss
        uid = args["Uid"]

        data = request.data # 获取body体
        data = json.loads(data)
        if not self.judgeData.inData("TEid", data):
            return param_miss
        teid = data["TEid"]

        sid = self.sstudent.get_sid_by_uid(uid)
        sid_list = self.steams.get_sid_list_by_teid(teid)
        if sid in sid_list:
            return have_invate_this_users
        cid = self.steams.get_cid_by_teid(teid)

        leader_sid = self.steams.get_sid_by_teid(teid)
        uid2 = self.sstudent.get_uid_by_sid(leader_sid)

        response_of_student_and_team = self.steams.add_student_in_team(uuid.uuid4(), teid, sid, 1002, 1100)
        if not response_of_student_and_team and add_status:
            add_status = False

        response_of_infor = self.sinfor.add_infor(uuid.uuid4(), uid, NEW_REQUEST, 1200, 905, cid, teid, uid2)
        if not response_of_infor and add_status:
            add_status = False

        if not add_status:
            return system_error
        return invent_success


    # 邀请教师，入口在教师信息和团队详情中
    def add_teacher(self):
        add_status = True
        args = request.args.to_dict() # 获取参数
        print args
        # 判断参数中含有Uid
        if not self.judgeData.inData("TEid", args) or not self.judgeData.inData("Uid", args):
            return param_miss

        teid = args["TEid"]
        uid = args["Uid"]

        data = request.data # 获取body体
        data = json.loads(data)
        # 判断body体中含有必要参数
        if not self.judgeData.inData("Tno", data) or not self.judgeData.inData("Tuniversity", data):
            return param_miss

        tno = data["Tno"]
        tuniversity = data["Tuniversity"]
        tid = self.steacher.get_tid_by_tno_tuniversity(tno, tuniversity)
        tid_list = self.steams.get_tid_list_by_teid(teid)
        if tid in tid_list:
            return have_invate_this_users
        uid2 = self.steacher.get_uid_by_tid(tid)
        cid = self.steams.get_cid_by_teid(teid)

        # 创建教师团队关联，创建通知信息
        response_of_student_and_team = self.steams.add_teacher_in_team(uuid.uuid4(), teid, tid, 1100)
        if not response_of_student_and_team and add_status:
            add_status = False
        response_of_infor = self.sinfor.add_infor(uuid.uuid4(), uid, NEW_INVITATION, 1200, 901, cid, teid, uid2)
        if not response_of_infor and add_status:
            add_status = False

        if not add_status:
            return system_error
        return invent_success

    # 学生同意加入、通过学生申请，入口在我的信息中, 目前只完成了学生同意加入部分
    def sub_student(self):
        update_tstudent_item = {} # 新建空的json变量，放置TSid和TSsubject
        add_status = True
        args = request.args.to_dict() # 获取参数
        print args
        # 判断参数中含有Uid
        if not self.judgeData.inData("Uid", args) or not self.judgeData.inData("sub_index", args):
            return param_miss

        uid = args["Uid"] # 这里的Uid一定是接收者
        sub_index = int(args["sub_index"]) # 这里标记的是同意&拒绝，1101通过，1102拒绝

        data = request.data # 获取body体
        data = json.loads(data)

        # 判断body体中含有必要参数
        if not self.judgeData.inData("Pid", data):
            return param_miss

        pid = data["Pid"] # 这里获取到消息的id，消息id中根据ptype901邀请905申请来判断，无论申请还是邀请，修改的都是当前Uid在Team中的关联关系
        teid = self.sinfor.get_teid_by_pid(pid)
        sid = self.sstudent.get_sid_by_uid(uid)
        tsid = self.steams.get_tsid_by_teid_sid(teid, sid)
        if sub_index == 1102:
            update_tstudent_item["TSid"] = tsid
            update_tstudent_item["Sid"] = sid
            update_tstudent_item["TEid"] = teid
            update_tstudent_item["TStype"] = 1002
            update_tstudent_item["TSsubject"] = 1102

            response_of_update_tstudent = self.steams.update_tstudent_by_tsid(tsid, update_tstudent_item)
            if not response_of_update_tstudent and add_status:
                add_status = False

            sname = self.sstudent.get_sname_by_sid(sid)
            tename = self.steams.get_tename_by_teid(teid)
            message = REFUSE_JOIN_TEAM.format(sname, tename)  # 信息报文
            uid2 = self.sinfor.get_uid2_by_pid(pid)
            response_of_infor = self.sinfor.add_infor(uuid.uuid4(), uid, message, 1200, 903, None, teid, uid2)
            if not response_of_update_tstudent and add_status:
                add_status = False

            if not add_status:
                return system_error
            return refuse_join_team_success
        elif sub_index == 1101:
            # 根据团队id获取当前团队成员的数量，与团队最大数量进行对比，超过则显示满员
            # 这里需要对teid进行防sql注入处理
            students_num = self.steams.get_count_by_teid(teid)
            tenum = self.steams.get_tenum_by_teid(teid)

            if students_num >= tenum:
                return team_is_full

            # 判断该学生是否加入该竞赛的其他团队，待补充
            # 目前允许一个学生加入一个竞赛的多个团队
            update_tstudent_item["TSid"] = tsid
            update_tstudent_item["Sid"] = sid
            update_tstudent_item["TEid"] = teid
            update_tstudent_item["TStype"] = 1002
            update_tstudent_item["TSsubject"] = 1101

            response_of_update_tstudent = self.steams.update_tstudent_by_tsid(tsid, update_tstudent_item)
            if not response_of_update_tstudent and add_status:
                add_status = False
            sname = self.sstudent.get_sname_by_sid(sid)
            tename = self.steams.get_tename_by_teid(teid)
            message = JOIN_TEAM_SUCCESS.format(sname, tename)  # 信息报文
            uid2 = self.sinfor.get_uid2_by_pid(pid)
            response_of_infor = self.sinfor.add_infor(uuid.uuid4(), uid, message, 1200, 903, None, teid, uid2)
            if not response_of_update_tstudent and add_status:
                add_status = False

            if not add_status:
                return system_error
            return join_team_success
        else:
            return param_miss

    # 教师同意加入，入口在我的信息中
    def sub_teacher(self):
        add_status = True
        update_tteacher_item = {} # 新建空的json变量
        args = request.args.to_dict() # 获取参数
        print args
        if not self.judgeData.inData("Uid", args) or not self.judgeData.inData("sub_index", args):
            return param_miss

        uid = args["Uid"]
        sub_index = args["sub_index"]

        data = request.data # 获取body体
        data = json.loads(data)

        # 判断body体中含有必要参数
        if not self.judgeData.inData("Pid", data):
            return param_miss
        pid = data["Pid"]
        teid = self.sinfor.get_teid_by_pid(pid)
        tid = self.spersonal.get_tid_by_uid(uid)
        ttid = self.steams.get_ttid_by_teid_tid(teid, tid)

        if sub_index == 1102:
            update_tteacher_item["TTid"] = ttid
            update_tteacher_item["Tid"] = tid
            update_tteacher_item["TEid"] = teid
            update_tteacher_item["TTsubject"] = 1102

            response_of_update_tteacher = self.steams.update_tteacher_by_ttid(ttid, update_tteacher_item)
            if not response_of_update_tteacher and add_status:
                add_status = False

            tname = self.steacher.get_tname_by_tid(tid)
            tename = self.steams.get_tename_by_teid(teid)
            message = TEACHER_REFUSE_JOIN_TEAM.format(tname, tename)  # 信息报文
            uid2 = self.sinfor.get_uid2_by_pid(pid)
            response_of_infor = self.sinfor.add_infor(uuid.uuid4(), uid, message, 1200, 903, None, teid, uid2)
            if not response_of_infor and add_status:
                add_status = False

            if not add_status:
                return system_error
            return refuse_join_team_success
        elif sub_index == 1101:
            # 根据团队id获取当前团队教师的数量，默认最大为1，超过则加入失败
            # 这里需要对teid进行防sql注入处理
            teacher_num = self.steams.get_tcount_by_teid(teid)
            if teacher_num >= 1:
                return team_is_full

            update_tteacher_item["TTid"] = ttid
            update_tteacher_item["Tid"] = tid
            update_tteacher_item["TEid"] = teid
            update_tteacher_item["TTsubject"] = 1101

            response_of_update_tteacher = self.steams.update_tteacher_by_ttid(ttid, update_tteacher_item)
            if not response_of_update_tteacher and add_status:
                add_status = False
            tname = self.steacher.get_tname_by_tid(tid)
            tename = self.steams.get_tename_by_teid(teid)
            message = TEACHER_JOIN_TEAM_SUCCESS.format(tname, tename)  # 信息报文
            uid2 = self.sinfor.get_uid2_by_pid(pid)
            response_of_infor = self.sinfor.add_infor(uuid.uuid4(), uid, message, 1200, 903, None, teid, uid2)
            if not response_of_infor and add_status:
                add_status = False

            if not add_status:
                return system_error
            return join_team_success
        else:
            return param_miss

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
        sid = self.sstudent.get_sid_by_uid(uid)
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
        sid = self.sstudent.get_sid_by_uid(uid)
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