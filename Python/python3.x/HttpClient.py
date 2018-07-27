# -*- coding:utf-8 -*-
# ganl

import json
from urllib import request, parse
import hashlib, hmac, base64

# I2API_AK, I2API_SK,  obtain: https://127.0.0.1:55443/i2/i2/system_accesskey.php
I2API_AK = 'j6IaMfFE2KJPAH7y'  # 换成自己的
I2API_SK = 'nGzbTNgy5DW0qiPoUmD3YWzOABn3ftfz'  # 换成自己的


# ip = '192.168.82.166'

class HttpClient(object):
    def __init__(self, ip):
        self.result = {}
        self.api_root = 'http://' + ip + ':58080/i2/api2/'

    def doRequest(self, service=None, params=None, timeout=None):
        url = self.api_root + ('' if service is None else ('?service=' + service))
        data = None
        if params is not None:
            assert type(params) is dict, 'params type must be dict'
            assert params, 'params must is valid values'
            params = self.buildParams(service, params)
            # params = parse.urlencode(params)
            # print(params)
            data = parse.urlencode(params).encode(encoding='UTF8')
            print(data)
        _request = request.Request(url)
        response = request.urlopen(_request, data=data, timeout=timeout)
        self.result = {'info': response.info(), 'state': response.getcode(), 'data': json.loads(response.read().decode())}
        return self.result

    def print_result(self):
        print('head ', self.result['info'])
        print('state ', self.result['state'])
        print('result ', self.result['data'])
        print('-' * 20)
        return 0

    def buildParams(self, service, params):
        params['service'] = service
        params['token'] = I2API_AK
        params['signature'] = self.sign(service, params)
        return params

    def sign(self, service, params):
        sk = I2API_SK
        list = {}
        for param_key in params:
            list[param_key] = params[param_key]
        list['service'] = service
        list2 = sorted(list.keys())
        srcStr = ""
        for k in list2:
            srcStr += k + "=" + str(list[k]) + "&"
        srcStr = srcStr[:-1]
        hashed = hmac.new(sk.encode(), srcStr.encode(), digestmod=hashlib.sha256)
        sign = base64.b64encode(hashed.digest())
        # print(hashed.digest().encode('base64').rstrip())
        print(sign)
        return sign
