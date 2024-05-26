package com.jeet.common.core.utils;


import com.jeet.common.core.base.Base;

/**
 * 密码工具
 * @author zhangxiujin
 * @date 2020/12/8
 */
public class PasswordUtil extends Base {

    /**
     * 需要排除的简单密码
     */
    private static final String[] excludePasswords = new String[] {
            "admin", "administrator", "guest", "test", "oracle", "huawei",
            "cisco", "root", "111111", "123456", "888888", "qwerasdf", "chinatelecom"};

    /**
     * 校验密码 是否 通过
     * @param accounts  一到多个账号(包括普通账号、手机号、邮箱)
     * @param password  密码
     * @param isAdmin 是否为超级管理员（最高权限）- true为是，false为否
     * @return 返回密码是否通过，true通过，false不通过
     */
    public static boolean verifyPasswordThrough(String password, boolean isAdmin, String... accounts) {
        //校验密码 是否为null或空字符串，如果是，则校验不通过
        if (passwordIsEmpty(password)) {
            return false;
        }

        //是否包含 规则中规定的禁用密码，如果包含，校验不通过
        if (containExcludePassword(password)) {
            return false;
        }

        //判断是否包含账号字符串（包含大小写混和），如果包含，校验不通过
        if (containAccount(password, accounts)) {
            return false;
        }

        //校验密码是否为 复杂密码
        if (!verifyPassComplexity(password, isAdmin)) {
            return false;
        }

        return true;
    }

    /**
     * 校验密码 是否 通过
     * @param password  密码
     * @return 返回密码是否通过，true通过，false不通过
     */
    public static boolean verifyPasswordThrough(String password) {
        //校验密码 是否为null或空字符串，如果是，则校验不通过
        if (passwordIsEmpty(password)) {
            return false;
        }

        if (passwordContainsSpace(password)) {
            return false;
        }

        if (passwordMaxLengthOver(password, 20)) {
            return false;
        }

        //是否包含 规则中规定的禁用密码，如果包含，校验不通过
//        if (containExcludePassword(password)) {
//            return false;
//        }

        //校验密码是否为 复杂密码
        if (!verifyPassComplexity(password, false)) {
            return false;
        }

        return true;
    }

    /**
     * 是否超出密码最长限制
     * @param password
     * @param length
     * @return
     */
    private static boolean passwordMaxLengthOver(String password, int length) {
        return password.length() > length;
    }

    /**
     * 密码是否包含空格
     * @param password
     * @return
     */
    private static boolean passwordContainsSpace(String password) {
        return password.contains(" ");
    }

    /**
     * 校验密码是否为空
     * @param password 密码
     * @return true为空，false不为空
     */
    private static boolean passwordIsEmpty(String password) {
        return isEmpty(password);
    }

    /**
     * 校验 密码 是否为复杂密码
     * 校验标准为：
     * 1.系统最高权限密码口令长度至少12位复杂密码
     * 2.重要系统增加采用数字证书、短信、白名单、双因子认证等手段
     * 3.普通用户，密码要求至少 8位 以上的复杂密码 （至少包含 大写字母、小写字母、数字、特殊字符四项元素中的 三项）
     * @param password 密码
     * @param isAdmin 是否为管理员 - true为是，false为不是
     * @return true 通过 - 复杂密码 , false 不通过 - 简单密码
     */
    private static boolean verifyPassComplexity(String password, boolean isAdmin) {
        //校验密码是否为复杂密码
        if (isAdmin) {
            // 管理员
            // 校验密码长度是否为12位以上
            if (password.length() < 12) {
                return false;
            }
            if (!RegexUtil.isStrongPassword(password)) {
                return false;
            }
        } else {
            // 普通账号
            // 校验密码长度是否为6位以上
            if (password.length() < 6) {
                return false;
            }
            if (!RegexUtil.isStrongPassword(password)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 校验是否在 规则中排除的密码列表 之中
     * @param password 待校验的 密码
     * @return true包含规则中排除的简单密码，false不包含规则中排除的简单密码
     */
    private static boolean containExcludePassword(String password) {
        for (String excludePassword : excludePasswords) {
            if (password.toLowerCase().contains(excludePassword.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检验 登录密码 是否 包含 账号字符串（包含大小写混合的账号、以及账号字符串的倒序字符串）
     * 比如： 账号为 zhangsan ，密码为 Passzhangsan123，则密码包含账号字符串
     * @param accounts 一到多个账号(包括普通账号、手机号、邮箱)
     * @param password 密码
     * @return
     */
    private static boolean containAccount(String password, String... accounts) {
        for (String account : accounts) {
            if (account != null && !account.equals("")) {
                // 判断密码 是否包含账号字符串（包含大小写混合的账号字符串）
                if (password.toLowerCase().contains(account.toLowerCase())) {
                    return true;
                }
                // 判断密码是否包含账号字符串的倒序字符串
                if (password.toLowerCase().contains(
                        new StringBuilder(account.toLowerCase()).reverse().toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(verifyPasswordThrough("Huige666.", false, new String[]{"abs001","133"}));
    }

}
