package com.jeet.common.core.utils;

import java.util.UUID;

/**
 * 自定义主键生成策略
 *
 */
public class PrimaryKeyUtil {

	public static String create() {
		return UUID.randomUUID().toString().replaceAll("-", "");
//		return MD5Encrypt.encrypt(UUID.randomUUID().toString());  
	}
	
}
