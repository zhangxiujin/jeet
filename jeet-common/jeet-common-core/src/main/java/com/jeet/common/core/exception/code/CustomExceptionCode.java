package com.jeet.common.core.exception.code;

/**
 * @author zhangxiujin
 * @date 2021/6/25 14:33
 * @description 统一业务异常错误码
 */
public enum CustomExceptionCode {

    SMS_SEND_FAIL(1001, "短信发送失败"),
    SMS_BUSINESS_TYPE_ERROR(1002, "短信业务类型错误"),
    SMS_CODE_EXPIRED(1003, "短信验证码已过期, 请重新发送短信验证码"),
    SMS_CODE_ERROR(1004, "短信验证码错误"),
    FRONT_USER_EXISTED(1005, "前端用户已注册"),
    PASSWORD_STRENGTH_VERIFY_FAILED(1006, "密码强度校验失败"),
    PHONE_FORMAT_ERROR(1007, "手机号格式错误"),
    LOGIN_TYPE_ERROR(1008, "登录类型错误"),
    USER_STATUS_ERROR(1009, "用户已被列入黑名单"),
    //参数错误
    PARAM_ERROR(1010, "%s"),
    DOMAIN_REL_DEL_ERROR(1011, "领域被引用无法删除"),
    USER_NOT_REGISTRY_ERROR(1012, "用户还没有注册"),
    PASSWORD_NOT_SAME_ERROR(1013, "两次密码不一致"),
    USER_HAVE_REGISTRY_ERROR(1014, "用户已被注册"),
    PASSWORD_NOT_CORRECT(1015, "账号密码不正确"),
    DOMAIN_HAVE_EXISTED(1016, "领域已经存在"),
    REWARD_PARAM_CONFIG_ISNULL(1017, "悬赏参数没有进行配置"),
    WATCH_AMOUNT_OVER_LIMIT(1018, "围观费用超出设置上限"),
    CONTENT_SENSITIVE(1019, "内容有敏感信息"),
    NO_LOGIN(1020, "请登录"),
    CANNOT_USER_INFO(1021, "找不到用户信息"),
    INVALID_ID_CARD(1022, "无效的身份证"),
    INVALID_MOBILE(1023, "无效的手机号"),
    USER_NICKNAME_SENSITIVE(1024, "用户昵称有敏感信息"),
    INVALID_EMAIL(1025, "无效的邮箱"),
    BIRTHDAY_CAN_NOT_EXCEED_CURRENT_TIME(1026, "生日不能超出当前时间"),
    PAY_PASSWORD_NOT_EXIST(1027, "未设置支付密码"),
    PAY_PASSWORD_EXIST(1028, "已设置支付密码"),
    PAY_PASSWORD_ERROR(1029, "支付密码错误"),

    ADD_JOB_EXCEPTION(1050, "新增任务异常"),
    DELETE_JOB_EXCEPTION(1051, "删除任务异常"),
    WECHAT_LOGIN_FAIL(1061, "微信登录失败"),
    ACCOUNT_NOT_EXISTED(1062, "账号不存在"),
    ACCOUNT_NOT_BIND_WX(1063, "账号未绑定微信"),
    TOKEN_EXPIRED(1064, "登录状态已过期"),
    LOGIN_FAIL(1065, "登录失败"),
    LOGIN_REQUEST_FAIL(1066, "登录请求不成功，请联系系统管理商。"),

    /**
     * 网络异常
     */
    CONNECT_TIME_OUT(2001, "连接超时"),
    REMOTE_SERVICE_EXCEPTION(2002, "服务异常");

    private final Integer code;
    private final String info;

    CustomExceptionCode(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
