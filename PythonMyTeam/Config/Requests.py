# *- coding:utf8 *-

#放置各种通用的响应body

# api未被注册
apis_wrong = {
    "status": 405,
    "status_code": 405099,
    "messages": "The API is not registered !"
}

# 注册成功
register_ok = {
    "status":200,
    "messages":"register is ok !"
}

# 登录成功
login_ok = {
    "status":200,
    "messages":{
        "message":"login is ok !"
    }
}

# 系统异常
system_error = {
    "status":404,
    "status_code":404100,
    "messages":"System is abnormal !"
}

# 用户名重复
repeated_name = {
    "status":405,
    "status_code":405100,
    "messages":"Username is occupied !"
}

# 密码不合法
wrong_upwd = {
    "status":405,
    "status_code":405101,
    "messages":"The password is not valid !"
}

# 用户名不合法
wrong_uname = {
    "status":405,
    "status_code":405102,
    "messages":"The username is not valid !"
}

# 错误的密码
error_upwd = {
    "status":405,
    "status_code":405101,
    "messages":"The password is wrong !"
}

# 参数缺失
param_miss = {
    "status":405,
    "status_code":405201,
    "messages":"missing some parameters !"
}

# 新增个人信息成功
add_personal_success = {
    "status":200,
    "messages":"New personal information was successful !"
}

# 该用户无权限
none_permissions = {
    "status":405,
    "status_code":405202,
    "messages":"This user does not have permission !"
}

# 没有对应的身份编码
none_identity = {
    "status":405,
    "status_code":405203,
    "messages":"Can not find the corresponding identity !"
}

# 性别错误
wrong_sex = {
    "status":405,
    "status_code":405204,
    "messages":"Gender error !"
}

# 新增学生技能成功
add_student_tech_success = {
    "status":200,
    "messages":"New student skills are successful !"
}

# 请先填写个人信息
none_personal = {
    "status":405,
    "status_code":405205,
    "messages":"Please new personal informations first !"
}

# 技能名称不符合要求
wrong_stname = {
	"status":405,
	"status_code":405105,
	"messages":"Skill name does not meet the requirements !"
}

# 技能熟练度等级不在范围内
error_tech_level = {
    "status":405,
    "status_code":405206,
    "messages":"Parameter value is not in range !"
}

# 技能名称重复
repeated_stname = {
    "status":405,
    "status_code":405207,
    "messages":"STname is occupied !"
}

# 比赛经历名称重复
repeated_scname = {
    "status":405,
    "status_code":405208,
    "messages":"SCname is occupied !"
}

# 新增个人比赛经历成功
add_personal_use_success = {
    "status":200,
    "messages":"New personal competitions success !"
}

# 更新个人信息成功
update_personal_abo_success = {
    "status":200,
    "messages":"Update personal information was successful !"
}

# 更新个人技能成功
update_personal_tech_success = {
    "status":200,
    "messages":"Update student skills are successful !"
}

# 更新个人竞赛简历成功
update_personal_use_success = {
    "status":200,
    "messages":"Update personal competitions success !"
}

# 删除个人技能成功
delete_personal_tech_success = {
    "status":200,
    "messages":"Delete student skills are successful !"
}

# 删除个人竞赛简历成功
delete_personal_use_success = {
    "status":200,
    "messages":"Delete personal competitions success !"
}