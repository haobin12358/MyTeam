# *- coding:utf8 *-

#放置各种通用的响应body

apis_wrong = {
    "status": 405,
    "status_code": 405099,
    "messages": "The API is not registered !"
}

register_ok = {
    "status":200,
    "messages":"register is ok !"
}

login_ok = {
    "status":200,
    "messages":{
        "message":"login is ok !"
    }
}

system_error = {
    "status":404,
    "status_code":404100,
    "messages":"System is abnormal !"
}

repeated_name = {
    "status":405,
    "status_code":405100,
    "messages":"Username is occupied !"
}

wrong_upwd = {
    "status":405,
    "status_code":405101,
    "messages":"The password is not valid !"
}

wrong_uname = {
    "status":405,
    "status_code":405102,
    "messages":"The username is not valid !"
}

error_upwd = {
    "status":405,
    "status_code":405101,
    "messages":"The password is wrong !"
}

param_miss = {
    "status":405,
    "status_code":405201,
    "messages":"missing some parameters !"
}

