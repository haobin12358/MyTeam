# *- coding:utf8 *-
"""
【前注】系统默认端口号7443，网络协议http，拼装接口举例如下：
接口为/users/login，IP地址为127.0.0.1
拼装地址：http://127.0.0.1:7443/users/login
"""
# User
"""
一、用户相关
1、注册
方法post，接口/users/register
params无
body:
{
	“Uname”:”xxx”,
	“Upwd”:”xxx”,
	“Utype”:”xxx”
}
正常返回体
{
	“status”:200,
	“messages”:”register is ok !”
}
异常返回体
1）数据库异常或系统异常
{
	"status":404,
	"status_code":404100,
	"messages":"System is abnormal !"
}
2）用户名重复
{
	"status":405,
	"status_code":405100,
	"messages":"Username is occupied !"
}
3）密码不合法
{
	"status":405,
	"status_code":405101,
	"messages":"The password is not valid !"
}
4）用户名不合法
{
	"status":405,
	"status_code":405102,
	"messages":"The username is not valid !"
}
2、登录
方法post，接口/users/login
params无
body:
{
	“Uname”:”xxx”,
	“Upwd”:”xxx”
}
正常返回体
{
	“status”:200,
	“messages”:{
		“Uid”:”xxx”,
		“message”:”login is ok !”
	}
}
异常返回体
1）数据库异常或系统异常
{
	"status":404,
	"status_code":404100,
	"messages":"System is abnormal !"
}
2）用户不存在
{
	"status":405,
	"status_code":405103,
	"messages":"User does not exist, please check username or register !"
}
3）密码错误
{
	"status":405,
	"status_code":405104,
	"messages":"Wrong password or username !"
}
"""
# Personal
"""
二、个人信息
1、查看个人信息
方法get，接口/personal/findall
params:Uid
正常返回体
学生形态
{
	"status":200,
	"messages":{
		"message":"login is ok !",
		"body":{
			"Utype":100,//据此筛选
			"Sname":"xxx",
			"Sno":"xxx",
			"Suniversity":"xxx",
			"Sschool":"xxx",
			"Stel":"xxx",
			"Sgrade":2017,//可空
			"Ssex":201,//可空
			"STech":[
				{
					"STname":"xxx",
					"STlevel":1
				},
				{
					"STname":"xxx",
					"STlevel":5
				}
			],//可空（技能）
			"SCuse":[
				{
					"SCname":"xxx",
					"SCno":"xxx"
				},	
				{
					"SCname":"xxx",
					"SCno":"xxx"
				}
			]//可空（竞赛简历）
		}
	}
}
教师形态

异常返回体
1）数据库异常或系统异常
{
	"status":404,
	"status_code":404100,
	"messages":"System is abnormal !"
}
2）无对应字段
{
	"status":502,
	"messages":{
		"message":"get bad messages",
		"body":{}
	}
}
2、新增个人信息
方法post，接口/personal/new
param：Uid

body
学生形态
{
	"Sname":"张三",  //姓名
	"Sno":"123456",   //学号
	"Stel":"17700000000",   //联系方式
	"Suniversity":"杭州电子科技大学",  //学校
	"Sschool":"管理学院",  //学院
	"Sgrade":2017,  //年级，可空
	"Ssex":201  //性别，可空，201男202女
}
教师形态
{
	"Tname":"张三",
	"Tno":"123456",  //学号
	"Ttel":"17700000000",
	"Tuniversity":"杭州电子科技大学",
	"Tschool":"管理学院",
	"Ttime":1   //任教时间，可空
}
正常返回体
{
    "messages": "New personal information was successful !",
    "status": 200
}
异常返回体
数据库异常或系统异常
{
	"status":404,
	"status_code":404100,
	"messages":"System is abnormal !"
}
参数缺失
{
    "messages": "missing some parameters !",
    "status": 405,
    "status_code": 405201
}
性别异常
{
    "messages": "Gender error !",
    "status": 405,
    "status_code": 405204
}
无操作权限
{
    "messages": "This user does not have permission !",
    "status": 405,
    "status_code": 405202
}
身份检验失败
{
    "messages": "Can not find the corresponding identity !",
    "status": 405,
    "status_code": 405203
}
3、新增技能
方法post，接口/personal/stech_new
params：Uid
[
	{
		"STname":"python",//技能名称
		"STlevel":0//技能等级1入门2一般3掌握4熟练5精通
	},
	{
		"STname":"Java",
		"STlevel":0
	}
]
正常返回体
{
    "messages": "New student skills are successful !",
    "status": 200
}
数据库异常或系统异常
{
	"status":404,
	"status_code":404100,
	"messages":"System is abnormal !"
}
技能名称不符合要求
{
	"status":405,
	"status_code":405105,
	"messages":"Skill name does not meet the requirements !"
}
个人信息未填写
{
    "status":405,
    "status_code":405205,
    "messages":"Please new personal informations first !"
}
参数缺失
{
    "messages": "missing some parameters !",
    "status": 405,
    "status_code": 405201
}
无操作权限
{
    "messages": "This user does not have permission !",
    "status": 405,
    "status_code": 405202
}
身份检验失败
{
    "messages": "Can not find the corresponding identity !",
    "status": 405,
    "status_code": 405203
}
技能等级取值不在范围内
{
    "messages": "Parameter value is not in range !",
    "status": 405,
    "status_code": 405206
}
该技能已存在
{
    "messages": "STname is occupied !",
    "status": 405,
    "status_code": 405207
}
4、新增竞赛简历
方法post，接口/personal/scuse_new
param：Uid
学生形态
[
	{
		"SCname":"测试竞赛信息1",
		"SCno":"一等奖"
	},
	{
		"SCname":"测试竞赛信息2",
		"SCno":"二等奖"
	}
]
教师形态
[
	{
		"TCname":"测试竞赛信息1",
		"TCno":"一等奖",
		"TCnum":2//获奖队伍数目，可空
	},
	{
		"TCname":"测试竞赛信息2",
		"TCno":"二等奖"
	}
]
正常返回体
{
    "messages": "New personal competitions success !",
    "status": 200
}
异常情况
数据库异常或系统异常
{
	"status":404,
	"status_code":404100,
	"messages":"System is abnormal !"
}
竞赛名称不符合要求
{
	"status":405,
	"status_code":405105,
	"messages":"Competition name does not meet the requirements !"
}
个人信息未填写
{
    "status":405,
    "status_code":405205,
    "messages":"Please new personal informations first !"
}
参数缺失
{
    "messages": "missing some parameters !",
    "status": 405,
    "status_code": 405201
}
无操作权限
{
    "messages": "This user does not have permission !",
    "status": 405,
    "status_code": 405202
}
身份检验失败
{
    "messages": "Can not find the corresponding identity !",
    "status": 405,
    "status_code": 405203
}
该竞赛信息存在
{
    "status":405,
    "status_code":405208,
    "messages":"SCname is occupied !"
}
5、修改个人信息
方法post，接口/personal/update
param：Uid

body
学生形态
{
	"Sname":"张三",
	"Sno":"123456",
	"Stel":"17700000000",
	"Suniversity":"杭州电子科技大学",
	"Sschool":"会计学院",
	"Sgrade":2017,//可空
	"Ssex":201//可空，201男202女
}
教师形态
{
	"Tname":"张三",
	"Tno":"123456",
	"Ttel":"17700000000",
	"Tuniversity":"杭州电子科技大学",
	"Tschool":"会计学院",
	"Ttime":6//可空
}
正常返回体
{
    "messages": "Update personal information was successful !",
    "status": 200
}
异常返回体
数据库异常或系统异常
{
	"status":404,
	"status_code":404100,
	"messages":"System is abnormal !"
}
参数缺失
{
    "messages": "missing some parameters !",
    "status": 405,
    "status_code": 405201
}
性别异常
{
    "messages": "Gender error !",
    "status": 405,
    "status_code": 405204
}
无操作权限
{
    "messages": "This user does not have permission !",
    "status": 405,
    "status_code": 405202
}
身份检验失败
{
    "messages": "Can not find the corresponding identity !",
    "status": 405,
    "status_code": 405203
}
6、修改个人技能
方法post，接口/personal/stech_update
param：Uid
[
	{
		"STname":"python",
		"STlevel":3
	},
	{
		"STname":"Java",
		"STlevel":3
	}
]
正常返回体
{
    "messages": "Update student skills are successful !",
    "status": 200
}
数据库异常或系统异常
{
	"status":404,
	"status_code":404100,
	"messages":"System is abnormal !"
}
技能名称不符合要求
{
	"status":405,
	"status_code":405105,
	"messages":"Skill name does not meet the requirements !"
}
参数缺失
{
    "messages": "missing some parameters !",
    "status": 405,
    "status_code": 405201
}
无操作权限
{
    "messages": "This user does not have permission !",
    "status": 405,
    "status_code": 405202
}
身份检验失败
{
    "messages": "Can not find the corresponding identity !",
    "status": 405,
    "status_code": 405203
}
技能等级取值不在范围内
{
    "messages": "Parameter value is not in range !",
    "status": 405,
    "status_code": 405206
}
该技能已存在
{
    "messages": "STname is occupied !",
    "status": 405,
    "status_code": 405207
}
7、修改个人竞赛简历
方法post，接口/personal/scuse_update
param：Uid
学生形态
[
	{
		"SCname":"测试竞赛信息1",
		"SCno":"二等奖"
	},
	{
		"SCname":"测试竞赛信息2",
		"SCno":"三等奖"
	}
]
教师形态
[
	{
		"TCname":"测试竞赛信息1",
		"TCno":"二等奖",
		"TCnum":3
	},
	{
		"TCname":"测试竞赛信息2",
		"TCno":"三等奖"
	}
]
正常返回体：
{
    "messages": "Update personal competitions success !",
    "status": 200
}
异常情况
数据库异常或系统异常
{
	"status":404,
	"status_code":404100,
	"messages":"System is abnormal !"
}
竞赛名称不符合要求
{
	"status":405,
	"status_code":405105,
	"messages":"Competition name does not meet the requirements !"
}
参数缺失
{
    "messages": "missing some parameters !",
    "status": 405,
    "status_code": 405201
}
无操作权限
{
    "messages": "This user does not have permission !",
    "status": 405,
    "status_code": 405202
}
身份检验失败
{
    "messages": "Can not find the corresponding identity !",
    "status": 405,
    "status_code": 405203
}
该竞赛信息存在
{
    "status":405,
    "status_code":405208,
    "messages":"SCname is occupied !"
}
8、删除个人技能
方法delete，接口/personal/stech_delete
param：Uid
body：
[
	{
		"STname":"python"
	},
	{
		"STname":"Java"
	}
]
正常返回体
{
    "status":200,
    "messages":"Delete student skills are successful !"
}
数据库异常或系统异常
{
	"status":404,
	"status_code":404100,
	"messages":"System is abnormal !"
}
参数缺失
{
    "messages": "missing some parameters !",
    "status": 405,
    "status_code": 405201
}
无操作权限
{
    "messages": "This user does not have permission !",
    "status": 405,
    "status_code": 405202
}
9、删除个人竞赛简历
方法delete，接口/personal/scuse_delete
param：Uid
学生形态
[
	{
		"SCname":"测试竞赛信息1"
	},
	{
		"SCname":"测试竞赛信息2"
	}
]
教师形态
[
	{
		"TCname":"测试竞赛信息1"
	},
	{
		"TCname":"测试竞赛信息2"
	}
]
正常返回体
{
    "messages": "Delete personal competitions success !",
    "status": 200
}
异常返回体
数据库异常或系统异常
{
	"status":404,
	"status_code":404100,
	"messages":"System is abnormal !"
}
参数缺失
{
    "messages": "missing some parameters !",
    "status": 405,
    "status_code": 405201
}
无操作权限
{
    "messages": "This user does not have permission !",
    "status": 405,
    "status_code": 405202
}
"""
# Student
"""
三、学生信息相关
1、查看学生信息列表
方法get，接口/students/list
params:
 page_num ：分页查询的页数#必选参数 参数限制为正整数
 page_size ：分页查询的每页记录数#必选参数 参数限制为正整数
 start: 年级信息最小限制 # 可选参数 参数限制为整型
 end: 年级信息最大限制 #可选参数 参数限制为整型
 Sname: 学生姓名 #可选参数 参数限制为字符串即可
 Sschool: 学生学院信息 #可选参数 参数限制为字符串即可

正常返回体
{
    "count": 1, # 该参数是为了显示搜索到的数据总共有几条数据
    "messages": "Search student list success !", # 无用参数，显示搜索完成无异常
    "page_num": 1, # 该参数是显示目前在第几页
    "status": 200, # 状态码 
    "student_list": [  #实际数据所在
        {
            "Sgrade": 1, # 学生年级
            "Sid": "2438503e-1250-455f-8130-b2456be9c838", # 学生id 查询学生abo会用到
            "Sname": "sdfa", # 学生姓名
            "Sschool": "xmsaa" # 学生学院信息
        }
    ]
}
异常返回体
数据库异常或系统异常
{
	"status":404,
	"status_code":404101,
	"messages":"System is abnormal !"
}

异常返回体
参数校验pagenum和pagesize不通过
{
    "status": 405,
    "status_code": 405301,
    "messages": "the pagenum = {0} pagesize = {1} is not right"
}

异常返回体
参数校验pagenum或者oagesize不存在
{
    "status": 405,
    "status_code": 405201,
    "messages": "missing some parameters !"
}
2、学生详情
abo 接口还未整改完成
"""
# Teacher
"""
四、教师信息相关
1、查看教师信息列表
方法get，接口/teachers/list
param：
 page_num ：分页查询的页数#必选参数 参数限制为正整数
 page_size ：分页查询的每页记录数#必选参数 参数限制为正整数
 Ttime: 教师工作时间 # 可选参数 参数限制为整型
 Tschool: 教师学院信息 #可选参数 参数限制为字符串即可
 Tname：教师姓名 #可选参数 参数限制为字符串即可
正常返回体
{
    "count": 7, # 该参数是为了显示搜索到的数据总共有几条数据
    "messages": "Search teacher list success !", # 无用参数 查询成功提示信息
    "page_num": 1, # 该参数是显示目前在第几页
    "status": 200, # 成功查询状态码
    "teacher_list": [ 数据所在
        {
            "Tid": "2017-10-10",  # 教师id
            "Tname": "hhh", #教师姓名
            "Tschool": "sdzxcv", #教师学院
            "Ttime": 2 #教师工作时间
        },
        {
            "Tid": "47487042-5d96-47a5-8dd8-9fe83124f53d",
            "Tname": "测试3",
            "Tschool": "吃不死你",
            "Ttime": 1
        }
    ]
}

异常返回体
异常返回体
数据库异常或系统异常
{
	"status":404,
	"status_code":404101,
	"messages":"System is abnormal !"
}

异常返回体
参数校验pagenum和pagesize不通过
{
    "status": 405,
    "status_code": 405301,
    "messages": "the pagenum = {0} pagesize = {1} is not right"
}

异常返回体
参数校验pagenum或者oagesize不存在
{
    "status": 405,
    "status_code": 405201,
    "messages": "missing some parameters !"
}

2、查看教师信息详情
方法get，接口/teachers/abo
param：Tid 
正常body
尚未调测完成
"""
# Competition
"""
五、竞赛信息相关
1、查看竞赛信息列表
方法get，接口/	/list
params:
 page_num ：分页查询的页数#必选参数 参数限制为正整数
 page_size ：分页查询的每页记录数#必选参数 参数限制为正整数
 Cend: 教师工作时间 # 可选参数 参数限制为整型
 Cstart: 教师学院信息 #可选参数 参数限制为字符串即可
 Cname：教师姓名 #可选参数 参数限制为字符串即可
 Clevel: 竞赛等级 #可选参数 参数限制为整型 取1-9 后续补充1-9对应等级意义
正常返回体

{
    "competition_list": [#数据所在
        {
            "Cend": "2018-10-10", #竞赛报名结束时间
            "Cid": "78e9ba4d-bd29-4062-b4bf-384f53fb22d9", #竞赛id
            "Clevel": 1, #竞赛等级
            "Cname": "测试竞赛信息3", #竞赛名称
            "Cno": 1, # 竞赛届次
            "Cstart": "2017-10-10" # 竞赛报名开始时间
        },
        {
            "Cend": "2018-10-10",
            "Cid": "88f4ec8d-de50-4686-ae38-197e5efc3395",
            "Clevel": 3,
            "Cname": "测试竞赛信息5",
            "Cno": 1,
            "Cstart": "2017-10-10"
        }
    ],
    "count": 7,
    "messages": "Search competitions list success !",
    "page_num": 2,
    "status": 200
}

异常返回体
数据库异常或系统异常
{
	"status":404,
	"status_code":404101,
	"messages":"System is abnormal !"
}

异常返回体
参数校验pagenum和pagesize不通过
{
    "status": 405,
    "status_code": 405301,
    "messages": "the pagenum = {0} pagesize = {1} is not right"
}

异常返回体
参数校验pagenum或者oagesize不存在
{
    "status": 405,
    "status_code": 405201,
    "messages": "missing some parameters !"
}

2、查看竞赛信息详情
方法get，接口/competitions/abo
尚未完成
"""
# Team
"""
六、团队信息相关
1、我的团队
方法get，接口/team/myteam
param:User
正常情况
[
	{
		"Cname":"xxx",
		"Cno":123,
		"TEname":"xxx",
		"TEno":"xxx",
		"TEuse":0
	},
	{
		"Cname":"xxx",
		"Cno":123,
		"TEname":"xxx",
		"TEno":"xxx",
		"TEuse":0
	}	
]
异常情况
{
	"status":404,
	"status_code":404100,
	"messages":"System is abnormal !"
}
2、其他团队
方法get，接口/team/myteam
param:User
正常情况
[
	{
		"Cname":"xxx",
		"Cno":123,
		"TEname":"xxx",
		"TEno":"xxx",
		"TEuse":0
	},
	{
		"Cname":"xxx",
		"Cno":123,
		"TEname":"xxx",
		"TEno":"xxx",
		"TEuse":0
	}	
]
异常情况
{
	"status":404,
	"status_code":404100,
	"messages":"System is abnormal !"
}
"""
# organize a team
"""
六、组队相关


"""