package com.sygamer.starreduxgm.gameaccount;
public class CodeMap {
    public static class RsCode{
        /**成功*/
        public static final int SUCCESS = 0;
        /**服务器异常*/
        public static final int ERR_CODE_SRV_ERRO = 1;
        public static final int ERRO_PARAM = 6;//参数错误

        public static final int ERRO_ACCOUNT_OP = 100;
        public static final int ERRO_ACCOUNT_OP_INVALID_REL_CODE = ERRO_ACCOUNT_OP + 1;//无效的第三方关联code
        public static final int ERRO_ACCOUNT_OP_INVALID_SRV_ID = ERRO_ACCOUNT_OP + 2;//无效的服务器id
        public static final int ERRO_ACCOUNT_OP_SRV_ID_IS_NULL = ERRO_ACCOUNT_OP + 3;//srvid为空
        public static final int ERRO_ACCOUNT_OP_PWD = ERRO_ACCOUNT_OP + 4;//密码错误
        public static final int ERRO_ACCOUNT_OP_NO_EXIST_ACCOUNT = ERRO_ACCOUNT_OP + 5;//账号不存在
        public static final int ERRO_ACCOUNT_OP_RELCODE_ALREADY_BINDED = ERRO_ACCOUNT_OP + 6;//三方账号已绑定
        public static final int ERRO_ACCOUNT_OP_THIS_UID_ALREAD_BINDED_AND_REL_CODE_NOT_REL_UID = ERRO_ACCOUNT_OP + 7;//当前账号已关联其他三方账号并且该三方账号未关联任何账号
        public static final int ERRO_ACCOUNT_OP_BIND_SUCCESS = ERRO_ACCOUNT_OP + 8;//绑定成功
        public static final int ERRO_ACCOUNT_OP_ALREADY_REGISTED = ERRO_ACCOUNT_OP + 9;//账号已注册
        public static final int ERRO_ACCOUNT_OP_NOT_IN_WHITE_LIST = ERRO_ACCOUNT_OP + 10;//账号不在白名单中
        public static final int ERRO_ACCOUNT_OP_HAS_SEND_VERIFY_CODE = ERRO_ACCOUNT_OP + 11;//已请求验证码
        public static final int ERRO_ACCOUNT_OP_RE_GET_SMS_VERIFICATION_CODE = ERRO_ACCOUNT_OP + 12;//需要重新获取验证码
        public static final int ERRO_ACCOUNT_OP_SMS_VERIFICATION_CODE_ERROR = ERRO_ACCOUNT_OP + 13;//验证码错误
        public static final int ERRO_ACCOUNT_OP_SMS_PHONE_HAS_REGISTER = ERRO_ACCOUNT_OP + 14;//手机号已注册
        public static final int ERRO_ACCOUNT_OP_NOT_SUPPORT_CHANGE_PASSWORD =  ERRO_ACCOUNT_OP + 15;//不支持修改密码
        public static final int ERRO_ACCOUNT_OP_SMS_PHONE_HAVE_NOT_REGISTER = ERRO_ACCOUNT_OP + 16;//手机号未注册
        public static final int ERRO_ACCOUNT_OP_SMS_PHONE_LIMIT = ERRO_ACCOUNT_OP + 17;//手机号请求验证码频繁
    }

    public static class Param{
        //预留公共字段
        public static final int RS_CODE = 1;//结果码

        public static final int ACCOUNT = 100;
        public static final int ACCOUNT_REL_CODE = ACCOUNT + 1;//三方code
        public static final int ACCOUNT_REL_UID = ACCOUNT + 2;//三方账号id
        public static final int ACCOUNT_UID = ACCOUNT + 3;//账号id
        public static final int ACCOUNT_PWD = ACCOUNT + 4;//三方账号id
        public static final int ACCOUNT_SDK_INFO = ACCOUNT + 5;//三方验证sessioninfo
        public static final int ACCOUNT_SRV_ID = ACCOUNT + 6;//服务器id
        public static final int ACCOUNT_SRV_IP = ACCOUNT + 7;//服务器ip
        public static final int ACCOUNT_SRV_PORT = ACCOUNT + 8;//服务器端口
        public static final int ACCOUNT_TOKEN = ACCOUNT + 9;//token
        public static final int ACCOUNT_TOKEN_TIMESTAMP = ACCOUNT + 10;//token时间戳
        public static final int ACCOUNT_SRV_HTTP_PORT = ACCOUNT + 11;//http服务器端口
        public static final int ACCOUNT_IS_BIND = ACCOUNT + 12;//账号是否被绑定
        public static final int ACCOUNT_VERIFY_CODE= ACCOUNT + 14;//验证码
        public static final int ACCOUNT_VERIFY_CODE_TYPE= ACCOUNT + 15;//验证码类型 1 注册，2 找回密码

        public static final int ACCOUNT_SDK_INFO_FIELED = 200;
        /**SDK内部uid*/
        public static final int ACCOUNT_SDK_INFO_FIELED_SDK_UID = ACCOUNT_SDK_INFO_FIELED + 1;
        /**SDK指示的渠道id*/
        public static final int ACCOUNT_SDK_INFO_FIELED_SDK_CHANNELID = ACCOUNT_SDK_INFO_FIELED + 2;
        /**渠道uid*/
        public static final int ACCOUNT_SDK_INFO_UID = ACCOUNT_SDK_INFO_FIELED + 3;
        /**渠道username*/
        public static final int ACCOUNT_SDK_INFO_USERNAME = ACCOUNT_SDK_INFO_FIELED + 4;
        /**渠道SDK登录完成后的Session ID。特别提醒：部分渠道此参数会包含特殊值如`+`，空格之类的，如直接使用URL参数传输到游戏服务器请求校验，请使用URLEncode编码。*/
        public static final int ACCOUNT_SDK_INFO_TOKEN = ACCOUNT_SDK_INFO_FIELED + 5;
        /**SDK内部定义的appid*/
        public static final int ACCOUNT_SDK_INFO_SDK_APPID = ACCOUNT_SDK_INFO_FIELED + 6;
    }

    public static class Cmd{
        public static final int CMD_ACCOUNT = 100;
        public static final int CMD_ACCOUNT_GET_SERVER_INFO = CMD_ACCOUNT + 1;//获取服务器信息
        public static final int CMD_ACCOUNT_LOGIN = CMD_ACCOUNT + 2;//登录
        public static final int CMD_ACCOUNT_RELACCOUNT_BIND = CMD_ACCOUNT + 3;//三方账号绑定
        public static final int CMD_ACCOUNT_REGIST = CMD_ACCOUNT + 4;//注册
        public static final int CMD_ACCOUNT_GET_VERIFY_CODE=CMD_ACCOUNT + 5;//获取验证码
        public static final int CMD_ACCOUNT_CHANGE_PASSWORD=CMD_ACCOUNT + 6;//修改密码
        public static final int CMD_ACCOUNT_CHECK_VERIFY_CODE=CMD_ACCOUNT + 7;//检查验证码
        public static final int CMD_ACCOUNT_INFO =CMD_ACCOUNT + 8;//获取账号信息

    }
}
