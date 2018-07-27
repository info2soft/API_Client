<?php
/**
 * Created by PhpStorm.
 * User: ganl
 * Date: 16-5-23
 * Time: 上午10:38
 */

/*ini_set('display_errors','On');
error_reporting(E_ALL);*/

//I2API_AK, I2API_SK,  obtain: https://127.0.0.1:55443/i2/i2/system_accesskey.php
define('I2API_AK', 'j6IaMfFE2KJPAH7y');
define('I2API_SK', 'nGzbTNgy5DW0qiPoUmD3YWzOABn3ftfz');

require_once dirname(__FILE__) . '/client/PhalApiClient.php';

class MyFilter implements PhalApiClientFilter
{

    public function filter($service, array &$params)
    {
        $arr = $params;
        $arr['service'] = $service;
        unset($arr['signature']);
        ksort($arr);

        $retStr = "";
        foreach ($arr as $key => $param) {
            $retStr .= $key . '=' . $param . "&";
        }
        $retStr = substr($retStr, 0, strlen($retStr) - 1);

        $utf8Str = mb_convert_encoding($retStr, "UTF-8");

        $vKeys = base64_encode(hash_hmac('sha256', $utf8Str, I2API_SK, true));
        $params['signature'] = $vKeys;
    }
}

if (I2API_AK == '' || I2API_SK == '') {
    exit('invalid api ak or sk');
}

$client = PhalApiClient::create()
    ->withHost('http://127.0.0.1:58080/i2/api2/')
    ->withFilter(new MyFilter());

//////-------------- USER

/*$rs = $client->reset()
    ->withService('Auth.Token')
    ->withParams('username', 'admin')
    ->withParams('pwd', hash('sha256', '123456'))
    ->withParams('pwd', '123456')
    ->withTimeout(3000)
    ->request();*/

/*$rs = $client->reset()
    ->withService('Auth.Token')
    ->withParams('username', 'test1')
    ->withParams('pwd', md5('123456'))
    ->withTimeout(3000)
    ->request();*/

/*$rs = $client->reset()
    ->withService('User.CreateAccount')
    ->withParams('token', I2API_AK)
    ->withParams('username', '6666')
    ->withParams('pwd', hash('sha256', ('123456')))
    ->withParams('role', 0)
    ->withParams('mobile', '18501767968')
    ->withParams('email', 'ganl@info2soft.com')
    ->withParams('comment', 'just comment')
    ->request();*/

/*--------------
{
    ret: 200,
    data: {
        returnCode: 0,
        uuid: "A56D6D32-FA95-CC4A-8D91-73BD888BEA17",
        returnMsg: ""
    },
    msg: ""
}
*/

//print_r($rs->getRet());
//echo "\n";
//print_r($rs->getData());
//echo "\n";
//var_dump($rs->getMsg());

/*
/**
int(200)

array(4) {
["title"]=>
string(12) "Hello World!"
["content"]=>
string(36) "dogstar您好，欢迎使用PhalApi！"
["version"]=>
string(5) "1.2.1"
["time"]=>
int(1444925238)
}

string(0) ""

 */

//echo "\n--------------------\n";
$rs = $client->reset()
    ->withService('User.Baseinfo')
    ->withParams('token', I2API_AK)
    ->withParams('username', 'admin')
//    ->withParams('pwd', hash('sha256', ('123456')))
    ->request();


var_dump($rs->getRet());
echo "\n";
var_dump($rs->getData());
echo "\n";
var_dump($rs->getMsg());
