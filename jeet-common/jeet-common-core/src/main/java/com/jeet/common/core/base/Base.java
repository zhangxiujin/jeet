package com.jeet.common.core.base;


/**
 *	@author zhangxiujin
 */
public class Base {
	
	protected static final String UNDER_LINE = "_";
	
	protected static final String STR_NULL = null;
	
	protected static final Exception EXCEPTION_NULL = null;
	
	protected static final String NULLSTR = "";
	
	protected static final String SPACE_STR = " ";
	
	protected static final String SEPARATOR = "/";
	

	protected static final boolean isEmpty(Object obj) {
		if(obj == null) {
			return true;
		}
		return false;
	} 
	
	protected static final boolean isEmpty(String str) {
		if(str == null || str.equals("")) {
			return true;
		}
		return false;
	}
	
	protected static final boolean isEmpty(Integer integer) {
		if(integer == null) {
			return true;
		}
		return false;
	}
	
	protected static final boolean isNull(String str) {
		if(str == null) {
			return true;
		} 
		return false;
	}
	
	/**
	 *	处理异常 
	 */
	protected static final RuntimeException handleEx(Throwable e) {
		throw new RuntimeException(e);
	}
}
