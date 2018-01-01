# *- coding:utf8 *-
# 引用python类
from flask import request
import json
import uuid
# 引用项目类
from common.JudgeData import JudgeData
from service.SPersonal import SPersonal
from service.SStudents import SStudents
from service.STeachers import STeachers
from common.get_model_return_list import get_model_return_list
from Config.Requests import param_miss, system_error, add_personal_success,\
    none_permissions, none_identity, wrong_sex, add_student_tech_success, \
    none_personal, error_tech_level, repeated_stname, repeated_scname, \
    add_personal_use_success, update_personal_abo_success, \
    update_personal_tech_success, update_personal_use_success, \
    delete_personal_tech_success, delete_personal_use_success, \
    search_teachers_abo_success, search_student_abo_success


# 用于处理个人信息相关数据
class CPersonal():
    def __init__(self):
        self.judgeData = JudgeData()  # 实例化
        self.spersonal = SPersonal()
        self.sstudent = SStudents()
        self.steacher = STeachers()

    # 获取个人信息
    def myinfor(self):
        args = request.args.to_dict()  # 获取到的参数
        print args
        # 判断参数非空
        if not args:
            return param_miss
        # 判断参数中含有Uid
        if not self.judgeData.inData("Uid", args):
            return param_miss

        uid = args["Uid"]
        # 获取用户身份
        utype = self.spersonal.get_utype_by_uid(uid)
        print utype
        # 判断系统是否异常
        if not self.spersonal.status:
            return system_error

        # 判断用户身份，其中100为学生，101为教师，102为管理员
        if utype == 100:
            sid = self.spersonal.get_sid_by_uid(uid)

            # 获取数据库中数据
            # 获取学生的基础信息
            student_abo = get_model_return_list(self.sstudent.get_student_abo_by_sid(sid))
            # 获取学生的技能信息
            student_tech = get_model_return_list(self.sstudent.get_student_tech_by_sid(sid))
            # 获取学生的竞赛信息
            student_use = get_model_return_list(self.sstudent.get_student_use_by_sid(sid))
            # 拼装返回结构体
            student_abo[0]["STech"] = student_tech
            student_abo[0]["SCUse"] = student_use
            search_student_abo_success["student_abo"] = student_abo
            return search_student_abo_success  # 已修改
        elif utype == 101:
            tid = self.spersonal.get_tid_by_uid(uid)

            # 获取教师的所有基础信息
            teacher_abo = get_model_return_list(self.steacher.get_teacher_abo_by_tid(tid))
            # 获取教师的所有竞赛信息
            teacher_use = get_model_return_list(self.steacher.get_teacher_use_by_tid(tid))
            # 将竞赛信息拼装进教师基础信息中
            teacher_abo[0]["TCuse"] = teacher_use
            search_teachers_abo_success["teacher_abo"] = teacher_abo
            return search_teachers_abo_success
        elif utype == 102:
            return none_permissions
        else:
            return none_identity

    # 新建个人信息
    def create_myinfor(self):
        args = request.args.to_dict()  # 获取到的参数
        print args
        form = request.data  # 获取到的body体
        print form
        # 判断参数非空
        if not args:
            return param_miss
        # 判断body体非空
        if form == {} or str(form) == "":
            return param_miss
        # 转化为json
        form = json.loads(form)
        # 判断参数中含有Uid
        if not self.judgeData.inData("Uid", args):
            return param_miss

        uid = args["Uid"]
        # 获取用户身份
        utype = self.spersonal.get_utype_by_uid(uid)
        print utype
        # 判断系统正常
        if not self.spersonal.status:
            return system_error
        # 判断用户身份，其中100为学生，101为教师，102为管理员
        if utype == 100:
            # 判断body中的必要参数是否缺失
            if not self.judgeData.inData("Sname", form) or not self.judgeData.inData("Sno", form) \
                    or not self.judgeData.inData("Suniversity", form) or not self.judgeData.inData("Sschool", form) \
                    or not self.judgeData.inData("Stel", form):
                return param_miss
            # 判断body中的非必要参数是否存在
            if not self.judgeData.inData("Sgrade", form):
                form["Sgrade"] = None
            if not self.judgeData.inData("Ssex", form):
                form["Ssex"] = None
            elif form["Ssex"] != 201 and form["Ssex"] != 202:
                return wrong_sex

            # 填写个人信息到数据库
            add_personal_abo = self.spersonal.add_student_abo_by_uid(uuid.uuid4(), args["Uid"], form["Sname"],
                                                                     form["Sno"],
                                                                     form["Suniversity"], form["Sschool"], form["Stel"],
                                                                     form["Sgrade"], form["Ssex"])
            # 判断插入是否成功
            if add_personal_abo:
                return add_personal_success
            else:
                return system_error
        elif utype == 101:
            # 判断body中的必要参数是否缺失
            if not self.judgeData.inData("Tname", form) or not self.judgeData.inData("Tno", form) \
                    or not self.judgeData.inData("Ttel", form) or not self.judgeData.inData("Tuniversity", form) \
                    or not self.judgeData.inData("Tschool", form):
                return param_miss
            # 判断body中的非必要参数是否存在
            if not self.judgeData.inData("Ttime", form):
                form["Ttime"] = None

            # 填写个人信息到数据库
            add_personal_abo = self.spersonal.add_teacher_abo_by_uid(uuid.uuid4(), args["Uid"], form["Tname"],
                                                                     form["Tno"], form["Ttel"], form["Tuniversity"],
                                                                     form["Tschool"], form["Ttime"])
            # 判断插入是否成功
            if add_personal_abo:
                return add_personal_success
            else:
                return system_error
        elif utype == 102:
            return none_permissions
        else:
            return none_identity

    # 新建个人技能
    def create_mytech(self):
        args = request.args.to_dict()  # 获取参数
        print args
        form = request.data  # 获取body
        print form

        # 判断参数非空
        if not args:
            print 0
            return param_miss
        # 判断body体非空
        if form == {} or str(form) == "":
            return param_miss
        # 转化为json
        form = json.loads(form)
        # 判断参数中含有Uid
        if not self.judgeData.inData("Uid", args):
            return param_miss

        uid = args["Uid"]
        # 获取用户身份
        utype = self.spersonal.get_utype_by_uid(uid)
        print utype
        # 判断系统正常
        if not self.spersonal.status:
            return system_error
        # 判断用户身份，其中100为学生，101为教师，102为管理员
        if utype == 100:
            sid = self.spersonal.get_sid_by_uid(uid)
            if sid == "":
                return none_personal
            # 缺失校检技能名称的过程
            for row in form:
                # 判断必填参数是否存在
                if not self.judgeData.inData("STname", row) or not self.judgeData.inData("STlevel", row):
                    return param_miss
                # 校检等级合法，1入门2一般3掌握4熟练5精通
                if row["STlevel"] > 5 or row["STlevel"] < 1:
                    return error_tech_level
                # 判断技能名称是否重复
                stname_list = self.spersonal.get_stname_by_sid(sid)
                if row["STname"] in stname_list:
                    return repeated_stname

                # 写入技能
                add_personal_tech = self.spersonal.add_student_tech_by_sid(uuid.uuid4(), sid, row["STname"],
                                                                           row["STlevel"])

                # 判断系统异常
                if not add_personal_tech:
                    return system_error
            return add_student_tech_success
        elif utype == 101 or utype == 102:
            return none_permissions
        else:
            return none_identity

    # 新建个人比赛经历
    def create_myuse(self):
        args = request.args.to_dict()  # 获取参数
        print args
        form = request.data  # 获取body
        print form

        # 判断参数非空
        if not args:
            print 0
            return param_miss
        # 判断body体非空
        if form == {} or str(form) == "":
            return param_miss
        # 转化为json
        form = json.loads(form)
        # 判断参数中含有Uid
        if not self.judgeData.inData("Uid", args):
            return param_miss

        uid = args["Uid"]
        # 获取用户身份
        utype = self.spersonal.get_utype_by_uid(uid)
        print utype
        # 判断系统正常
        if not self.spersonal.status:
            return system_error
        # 判断用户身份，其中100为学生，101为教师，102为管理员
        if utype == 100:
            sid = self.spersonal.get_sid_by_uid(uid)
            if sid == "":
                return none_personal
            for row in form:
                # 判断必填参数缺失
                if not self.judgeData.inData("SCname", row) or not self.judgeData.inData("SCno", row):
                    return param_miss

                # 似乎应该校检一下两个参数，但是不知道应该怎么校检
                # 校检个人比赛经历中的竞赛名称重复
                scname_list = self.spersonal.get_scname_by_sid(sid)
                if row["SCname"] in scname_list:
                    return repeated_scname

                # 写入数据库
                add_personal_use = self.spersonal.add_student_use_by_sid(uuid.uuid4(), sid, row["SCname"],
                                                                         row["SCno"])
                if not add_personal_use:
                    return system_error

            return add_personal_use_success
        elif utype == 101:
            tid = self.spersonal.get_tid_by_uid(uid)
            if tid == "":
                return none_personal
            for row in form:
                # 判断必填参数缺失
                if not self.judgeData.inData("TCname", row) or not self.judgeData.inData("TCno", row):
                    return param_miss
                # 判断非必填参数是否存在
                if not self.judgeData.inData("TCnum", row):
                    row["TCnum"] = 1

                # 似乎应该进行一些校检，但是不知道该怎么校检
                # 写入数据库
                add_personal_use = self.spersonal.add_teacher_use_by_tid(uuid.uuid4(), tid, row["TCname"],
                                                                         row["TCno"], row["TCnum"])
                if not add_personal_use:
                    return system_error
            return add_personal_use_success
        elif utype == 102:
            return none_permissions
        else:
            return none_identity

    # 更新个人信息
    def update_myinfor(self):
        args = request.args.to_dict()  # 获取参数
        print args
        # 判断参数非空
        if not args:
            return param_miss
        # 判断参数中包含Uid
        if not self.judgeData.inData("Uid", args):
            return param_miss
        form = request.data  # 获取body体
        print form
        form = json.loads(form)
        # 判断body非空
        if form == {} or str(form) == "":
            return param_miss
        uid = args["Uid"]
        # 获取用户身份
        utype = self.spersonal.get_utype_by_uid(uid)
        print utype
        # 判断系统正常
        if not self.spersonal.status:
            return system_error

        # 判断用户身份，其中100为学生，101为教师，102为管理员
        if utype == 100:
            # 判断body中的必要参数是否缺失
            if not self.judgeData.inData("Sname", form) or not self.judgeData.inData("Sno", form) \
                    or not self.judgeData.inData("Suniversity", form) or not self.judgeData.inData("Sschool", form) \
                    or not self.judgeData.inData("Stel", form):
                return param_miss
            if self.judgeData.inData("Ssex", form) and form["Ssex"] != 201 and form["Ssex"] != 202:
                return wrong_sex
            # 修改数据库信息
            update_personal_abo = self.spersonal.update_student_abo_by_uid(uid, form)

            if not update_personal_abo:
                return system_error
            return update_personal_abo_success
        elif utype == 101:
            # 判断body中的必要参数是否缺失
            if not self.judgeData.inData("Tname", form) or not self.judgeData.inData("Tno", form) \
                    or not self.judgeData.inData("Ttel", form) or not self.judgeData.inData("Tuniversity", form) \
                    or not self.judgeData.inData("Tschool", form):
                return param_miss

            # 修改数据库信息
            update_personal_abo = self.spersonal.update_teacher_abo_by_uid(uid, form)

            if not update_personal_abo:
                return system_error
            return update_personal_abo_success
        elif utype == 102:
            return none_permissions
        else:
            return none_identity

    # 更新个人技能
    def update_mytech(self):
        args = request.args.to_dict()  # 获取参数
        print args
        form = request.data  # 获取body体
        print form
        # 判断参数非空
        if not args:
            return param_miss
        # 判断参数中含有Uid
        if not self.judgeData.inData("Uid", args):
            return param_miss
        form = json.loads(form)
        # 判断body非空
        if form == {} or str(form) == "":
            return param_miss

        uid = args["Uid"]
        # 获取用户身份
        utype = self.spersonal.get_utype_by_uid(uid)
        print utype

        # 判断用户身份，其中100为学生，101为教师，102为管理员
        if utype == 100:
            sid = self.spersonal.get_sid_by_uid(uid)
            if sid == "":
                return none_personal
            # 缺失校检技能名称的过程
            for row in form:
                # 判断必填参数是否存在
                if not self.judgeData.inData("STname", row) or not self.judgeData.inData("STlevel", row):
                    return param_miss
                # 校检等级合法，1入门2一般3掌握4熟练5精通
                if row["STlevel"] > 5 or row["STlevel"] < 1:
                    return error_tech_level
                # 判断技能名称是否重复，这里判断有问题
                '''
                stname_list = self.spersonal.get_stname_by_sid(sid)
                if row["STname"] in stname_list:
                    return repeated_stname
                '''
                stid = self.spersonal.get_stid_by_stname_and_sid(sid, row["STname"])
                # 更新数据库
                update_personal_tech = self.spersonal.update_student_tech_by_stid(stid, row)

                if not update_personal_tech:
                    return system_error
            return update_personal_tech_success
        elif utype == 101 or utype == 102:
            return none_permissions
        else:
            return none_identity

    # 更新个人竞赛简历
    def update_myuse(self):
        args = request.args.to_dict()  # 获取参数
        print args
        form = request.data  # 获取body体
        print form
        # 判断参数非空
        if not args:
            return param_miss
        # 判断参数中含有Uid
        if not self.judgeData.inData("Uid", args):
            return param_miss
        form = json.loads(form)
        # 判断body非空
        if form == {} or str(form) == "":
            return param_miss

        uid = args["Uid"]
        # 获取用户身份
        utype = self.spersonal.get_utype_by_uid(uid)

        # 判断用户身份，其中100为学生，101为教师，102为管理员
        if utype == 100:
            sid = self.spersonal.get_sid_by_uid(uid)
            if sid == "":
                return none_personal
            for row in form:
                # 判断必填参数缺失
                if not self.judgeData.inData("SCname", row) or not self.judgeData.inData("SCno", row):
                    return param_miss

                # 似乎应该校检一下两个参数，但是不知道应该怎么校检
                scid = self.spersonal.get_scid_by_scname_and_sid(sid, row["SCname"])

                # 更新数据库
                update_personal_use = self.spersonal.update_student_use_by_scid(scid, row)

                if not update_personal_use:
                    return system_error

            return update_personal_use_success
        elif utype == 101:
            tid = self.spersonal.get_tid_by_uid(uid)
            if tid == "":
                return none_personal
            for row in form:
                # 判断必填参数缺失
                if not self.judgeData.inData("TCname", row) or not self.judgeData.inData("TCno", row):
                    return param_miss
                # 判断非必填参数是否存在
                if not self.judgeData.inData("TCnum", row):
                    row["TCnum"] = 1

                # 似乎应该进行一些校检，但是不知道该怎么校检
                # 获取tcid
                tcid = self.spersonal.get_tcid_by_tcname_and_tid(tid, row["TCname"])
                # 更新数据库
                update_personal_use = self.spersonal.update_teacher_use_by_tcid(tcid, row)
                if not update_personal_use:
                    return system_error
            return update_personal_use_success
        elif utype == 102:
            return none_permissions
        else:
            return none_identity

    # 删除个人技能
    def delete_mytech(self):
        args = request.args.to_dict()  # 获取参数
        print args
        form = request.data  # 获取body体
        print form
        # 判断参数非空
        if not args:
            return param_miss
        # 判断参数中含有Uid
        if not self.judgeData.inData("Uid", args):
            return param_miss
        form = json.loads(form)
        # 判断body非空
        if form == {} or str(form) == "":
            return param_miss

        uid = args["Uid"]
        # 获取用户身份
        utype = self.spersonal.get_utype_by_uid(uid)
        print utype

        # 判断用户身份，其中100为学生，101为教师，102为管理员
        if utype == 100:
            sid = self.spersonal.get_sid_by_uid(uid)
            # 缺失校检技能名称的过程
            for row in form:
                # 判断必填参数是否存在
                if not self.judgeData.inData("STname", row):
                    return param_miss

                stid = self.spersonal.get_stid_by_stname_and_sid(sid, row["STname"])
                # 更新数据库
                delete_personal_tech = self.spersonal.delete_student_tech_by_stid(stid)

                if not delete_personal_tech:
                    return system_error
            return delete_personal_tech_success
        elif utype == 101 or utype == 102:
            return none_permissions
        else:
            return none_identity

    def delete_myuse(self):
        args = request.args.to_dict()  # 获取参数
        print args
        form = request.data  # 获取body体
        print form
        # 判断参数非空
        if not args:
            return param_miss
        # 判断参数中含有Uid
        if not self.judgeData.inData("Uid", args):
            return param_miss
        form = json.loads(form)
        # 判断body非空
        if form == {} or str(form) == "":
            return param_miss

        uid = args["Uid"]
        # 获取用户身份
        utype = self.spersonal.get_utype_by_uid(uid)

        # 判断用户身份，其中100为学生，101为教师，102为管理员
        if utype == 100:
            sid = self.spersonal.get_sid_by_uid(uid)
            for row in form:
                # 判断必填参数缺失
                if not self.judgeData.inData("SCname", row):
                    return param_miss

                scid = self.spersonal.get_scid_by_scname_and_sid(sid, row["SCname"])

                # 更新数据库
                delete_personal_use = self.spersonal.delete_student_use_by_scid(scid)

                if not delete_personal_use:
                    return system_error

            return delete_personal_use_success
        elif utype == 101:
            tid = self.spersonal.get_tid_by_uid(uid)
            for row in form:
                # 判断必填参数缺失
                if not self.judgeData.inData("TCname", row):
                    return param_miss

                # 获取tcid
                tcid = self.spersonal.get_tcid_by_tcname_and_tid(tid, row["TCname"])
                # 更新数据库
                delete_personal_use = self.spersonal.delete_teacher_use_by_tcid(tcid)
                if not delete_personal_use:
                    return system_error
            return delete_personal_use_success
        elif utype == 102:
            return none_permissions
        else:
            return none_identity
