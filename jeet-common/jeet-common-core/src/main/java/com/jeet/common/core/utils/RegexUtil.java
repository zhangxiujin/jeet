package com.jeet.common.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 * @author kernel
 */
public class RegexUtil {

	public static final String NONE_STRING = "";

	public static final String HTML_TAG_P_WHOLE_REGEX = "<p[^>]*>(?:(?!</p>)[\\s\\S])*</p>";
	public static final String HTML_TAG_P_PREFIX_REGEX = "<p[^>]*>";
	public static final String HTML_TAG_P_SUFFIX_REGEX = "</p>";
	public static final String HTML_TAG_BR1_REGEX = "<br/>";
	public static final String HTML_TAG_BR2_REGEX = "<br>";
	public static final String HTML_TAG_NBSP_REGEX = "&nbsp;";

	//数字
	public static final String REG_NUMBER = ".*\\d+.*";
	//小写字母
	public static final String REG_UPPERCASE = ".*[A-Z]+.*";
	//大写字母
	public static final String REG_LOWERCASE = ".*[a-z]+.*";
	//特殊符号(~!@#$%^&*()_+|<>,.?/:;'[]{}\)
	public static final String REG_SYMBOL = ".*[~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}\"]+.*";

	/**
	 * 过滤掉字符串中所有以https开头的url地址，并返回过滤后的新字符串
	 *
	 * @param str 需要过滤的字符串
	 * @return 返回过滤后的新字符串
	 */
	public static String filterHttpsOfString(String str) {
		Pattern pattern = Pattern.compile("https://[\\S\\.]+[:\\d]?[/\\S]+\\??[\\S=\\S&?]+[^\u4e00-\u9fa5]");
        Matcher matcher = pattern.matcher(str);
        return matcher.replaceAll(NONE_STRING);
	}

	/**
	 * 过滤掉字符串中所有以http开头的url地址，并返回过滤后的新字符串
	 *
	 * @param str 需要过滤的字符串
	 * @return 返回过滤后的新字符串
	 */
	public static String filterHttpOfString(String str) {
		Pattern pattern = Pattern.compile("http://[\\S\\.]+[:\\d]?[/\\S]+\\??[\\S=\\S&?]+[^\u4e00-\u9fa5]");
		Matcher matcher = pattern.matcher(str);
		return matcher.replaceAll(NONE_STRING);
	}

	/**
	 * 描述：是否是邮箱.
	 *
	 * @param str 指定的字符串
	 * @return 是否是邮箱:是为true，否则false
	 */
	public static boolean isEmail(String str) {
		boolean isEmail = false;
		String expr = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})$";

		if (str.matches(expr)) {
			isEmail = true;
		}
		return isEmail;
	}

	/**
	 * 判断是否是手机号
	 *
	 * @param phone 手机号
	 * @return
	 */
	public static boolean isPhone(String phone) {
		Pattern pattern = Pattern
				.compile("^(13[0-9]|15[0-9]|153|15[6-9]|180|18[23]|18[5-9])\\d{8}$");
		Matcher matcher = pattern.matcher(phone);

		if (matcher.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * 校验 字符串 至少包含 大写字母、小写字母、数字、特殊字符四项元素中的 三项
	 * @param password 密码
	 */
	public static boolean isStrongPassword(String password) {
		int i = 0;

		if (password.matches(REG_NUMBER)) i++;
		if (password.matches(REG_LOWERCASE))i++;
		if (password.matches(REG_UPPERCASE)) i++;
		if (password.matches(REG_SYMBOL)) i++;

		if (i  < 2)  return false;  //任意两种字符元素 组合
		return true;
	}


	/**
	 * 单元测试
	 * @param args
	 */
	public static void main(String[] args) {
//		String ok = filterHttpOfString("独库公路的一天 伊犁哈萨克自治州·奎屯       https://yqms3.zhxgimg.com/download/img/kernel/ok/let/aHR0cDovL3d4MS5zaW5haW1nLmNuL3RodW1ibmFpbC8wMDVRRnMzNGx5MWc1OTRqY2RsNW5qMzE0MDB1MGRsaS5qcGc= http://ok/asdfasdfasdf/kimg/123131sfafsda_saf4e3q_123dsaf!!#@@@@_!!@");
//		ok = filterHttpOfString(ok);
//		System.out.println(ok);

		System.out.println(isPhone("123@qq2.com"));
	}

}
