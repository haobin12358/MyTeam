# *- coding:utf8 *-
#引用python类
from flask import request
import json

#引用项目类
from common.JudgeData import JudgeData
from service.STeams import STeams
from Config.Requests import system_error, param_miss

#用于处理团队相关数据
class CTeams():
    def __init__(self):
        self.steams = STeams()
        self.judgeData = JudgeData()#实例化

    # 展现团队列表，场景应用于团队信息和个人团队信息
    def teams_list(self):
        return system_error

    # 团队信息详情，根据团队唯一ID进行检索
    def team_abo(self):
        return system_error

    # 新建团队，入口在竞赛信息和团队板块的创建团队
    def new_team(self):
        return system_error

    # 更新团队信息，入口在团队信息详情，可更新数据不包含竞赛信息，如更新竞赛信息，等同新建，并关闭该团队，同步该团队的全部信息
    def update_team(self):
        return system_error

    # 邀请学生与申请加入团队，1.入口在学生信息和团队详情中2.入口在团队信息列表中
    def add_student(self):
        return system_error

    # 邀请教师，入口在教师信息和团队详情中
    def add_teacher(self):
        return system_error

    # 学生同意加入、通过学生申请，入口在我的信息中
    def sub_student(self):
        return system_error

    # 教师同意加入，入口在我的信息中
    def sub_teacher(self):
        return system_error

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
        return system_error

    # 删除团队学生，入口在团队详情中，实际修改团队学生表的属性
    def delete_student(self):
        return system_error

    # 删除团队教师，入口在团队详情中，实际修改团队教师表的属性
    def delete_teacher(self):
        return system_error

    # 删除团队任务，入口在团队详情中
    def delete_task(self):
        return system_error