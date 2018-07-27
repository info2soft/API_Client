# -*- coding:utf-8 -*-

import json, urllib, urllib2
import hmac, hashlib, binascii

# I2API_AK, I2API_SK,  obtain: https://127.0.0.1:55443/i2/i2/system_accesskey.php
I2API_AK = 'j6IaMfFE2KJPAH7y'  # 换成自己的
I2API_SK = 'nGzbTNgy5DW0qiPoUmD3YWzOABn3ftfz'  # 换成自己的

ip = '192.168.82.166'

class HttpClient(object):

    def __init__(self):
        self.result = {}
        self.api_root = 'http://' + ip + ':58080/i2/api2/'

    def doRequest(self, service=None, params=None, timeout=None):
        url = self.api_root + ('' if service is None else ('?service=' + service))
        print url
        if params is not None:
            assert type(params) is dict, 'params type must be dict'
            assert params, 'params must is valid values'
            params = self.buildParams(service, params)
            print params
            params = urllib.urlencode(params)
        request = urllib2.Request(url)
        try:
            response = urllib2.urlopen(request, data=params, timeout=timeout)
            self.result = {'info': response.info(), 'state': response.getcode(), 'data': json.loads(response.read())}
            return self.result
        except urllib2.URLError:
            print "there was an error with request  "

    def print_result(self):
        print 'head', self.result['info']
        print 'state', self.result['state']
        print 'result', self.result['data']
        print '-' * 20
        return 0

    def buildParams(self, service, params):
        params['service'] = service
        params['token'] = I2API_AK
        params['signature'] = self.sign(service, params)
        # print 'signature=   ',params['signature']
        return params

    def sign(self, service, params):
        sk = I2API_SK
        list = {}
        for param_key in params:
            list[param_key] = params[param_key]
        list['service'] = service
        list2 = sorted(list.keys())
        # print list2
        srcStr = ""
        for k in list2:
            # print k, list[k]
            srcStr += k + "=" + str(list[k]) + "&"
        srcStr = srcStr[:-1]
        # print srcStr, sk
        hashed = hmac.new(sk, srcStr, hashlib.sha256)
        # hashed = hmac.new(sk,srcStr, hashlib.sha1)
        sign = binascii.b2a_base64(hashed.digest())[:-1]
        # print 'sign=   ',sign
        return sign
