# *- coding:utf8 *-
class ParamsNotExitError(Exception):
    '''
    该exception是为了捕获请求体里没有必填参数，默认是pagenum或者pagesize
    '''
    def __init__(self, params="pagesize or pagenum"):
        message = "{0} is not exit".format(params)
        Exception.__init__(self, message)
