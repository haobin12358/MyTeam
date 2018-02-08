# *- coding:utf8 *-
# 兼容linux系统
import sys
import os
sys.path.append(os.path.dirname(os.getcwd())) # 增加系统路径
from models import model


# 装饰器，用来解析数据库获取的内容，将获取到的对象转置为dict，将获取到的单个数据的tuple里的数据解析出来
def trans_params(func):
    def inner(*args, **kwargs):
        params = func(*args, **kwargs)
        result = []
        if params:
            for param in params:
                if isinstance(param, (list, tuple)):
                    data = param[0]
                    # 如果发现解析的数据是Unicode 转置为utf-8
                    result.append(data)
                elif isinstance(param, model.Base):
                    param_dict = param.__dict__
                    for param_key in param_dict:
                        # 所有的model的dict里都有这个不需要的参数，所以删除掉
                        if param_key == "_sa_instance_state":
                            continue
                        # 如果发现解析到的数据是Unicode 转置为utf-8
                        param_dict[param_key] = param_dict.get(param_key)

                    result.append(param_dict)
                else:
                    result = params
        return result

    return inner
