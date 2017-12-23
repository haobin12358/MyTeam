# *- coding:utf8 *-


def get_model_return_list(model_list, model_name):
    """
    从数据库中获取到的list(列表)中每一个是一个数据库查询结果对象，
    在这里将每一个结果对象转置为dict(字典)
    :param model_list: 从数据库中直接获取到的list
    :param model_name: model名
    :return: 转置后的list
    """
    model_return_list = []
    for item in model_list:
        item_dict = item.__dict__
        print item_dict
        model_dict = model_name.__dict__
        model_item = {}
        for key in model_dict:
            if key in item_dict.keys():
                model_item[key] = item_dict.get(key)
        model_return_list.append(model_item)
    return model_return_list
