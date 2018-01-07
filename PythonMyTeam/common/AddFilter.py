# *- coding:utf8 *-


def add_filter(params, sql, model_name, opera_type_dict):
    """
    返回组合查询的sql语句
    :param params: 包含需要筛选的条件key
    :param sql: 需要查询的原sql语句
    :param model_name: 需要查询的数据库表名
    :return: 添加筛选条件的sql语句(sqlalchemy对象)
    """
    for param_key in params:
        value = params.get(param_key)
        # 这里filter没有数据类型校验 可能会出现 sql 注入
        opera_type = opera_type_dict.get(param_key)
        if opera_type == "like ":
            value = "%{0}%".format(value)
            filter_key = eval("model.{2}.{0} like \"{1}\"".format(
                param_key, value, model_name))
            sql = sql.filter(filter_key)
        elif opera_type == "==":
            value = "%{0}%".format(value)
            filter_key = eval("model.{2}.{0} like \"{1}\"".format(
                param_key, value, model_name))
            sql = sql.filter(filter_key)

    return sql
