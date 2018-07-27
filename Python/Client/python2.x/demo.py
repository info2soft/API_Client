#-*- coding:utf-8 -*-

from HttpClient import HttpClient

client = HttpClient()
result = client.doRequest('User.Baseinfo', {"username": "admin"})
print result
client.print_result()
