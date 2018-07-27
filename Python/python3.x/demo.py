#-*- coding:utf-8 -*-

from HttpClient import HttpClient

ip = '192.168.82.166'

client = HttpClient(ip)
result = client.doRequest('User.Baseinfo', {"username": "admin"})
print(result)
client.print_result()
