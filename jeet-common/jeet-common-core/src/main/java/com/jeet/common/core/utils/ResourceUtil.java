package com.jeet.common.core.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 *	@author kernel 
 */
public class ResourceUtil {

	/**
	 *	通过相对路径获取资源文件input流并返回
	 *
	 * 	@param	clazz	类对象
	 * 	@param	resourcePathRelativeClass	资源相对于clazz类的路径
	 */
	public static InputStream findResourceInPath(Class<?> clazz, String resourcePathRelativeClass) {
		return clazz.getResourceAsStream(resourcePathRelativeClass);
	}
	
	/**
	 *	通过相对路径获取资源的classpath字符串 
	 */
	public static String getResourceClasspath(Class<?> clazz, String resourcePathRelativeClass) {
		try {
			return new File(clazz.
					getResource(resourcePathRelativeClass).getFile()).getCanonicalPath();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 *	通过资源文件名获取在classpath根路径下的资源文件input流并返回 
	 */
	public static InputStream findResourceAtRoot(String resourceName) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName);
	}
	
	/**
	 *	获取带有file://前缀的classpath
	 */
	public static String getWholeClassPath(Class<?> clazz) {
		return clazz.getResource("/").toString();
	}
	
	/**
	 *	获取无file://前缀的classpath 
	 */
	public static String getClassPath(Class<?> clazz) {
		String wholeClassPath = getWholeClassPath(clazz);
		return wholeClassPath.substring(6, wholeClassPath.length());
	}
}
