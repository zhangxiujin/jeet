package com.jeet.common.core.utils;


import com.jeet.common.core.base.Base;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 *	@author kernel
 */
public class ListUtil extends Base {

	public static final int SORT_ASC = 1;
	public static final int SORT_DESC = -1;

	/**
	 * 判断List是否为空
	 * @param list 集合
	 * @param <T> true 或 false
	 * @return
	 */
	public static <T> boolean checkNull(List<T> list) {
		return null == list || list.size() < 1;
	}

	/**
	 *	将保存有字符串的集合转换为字符串数组
	 *
	 *	@param	list	字符串集合
	 *	@return	String[]	字符串数组
	 */
	public static final String[] listToStringArray(List<String> list) {
		return list.toArray(new String[list.size()]);
	}

	/**
	 *	将保存有长整型的集合转换为长整型数组
	 *
	 *	@param	list	长整型集合
	 *	@return	Long[]	长整型数组
	 */
	public static final Long[] listToLongArray(List<Long> list) {
		return list.toArray(new Long[list.size()]);
	}

	/**
	 *	将字符串集合转换为一个字符串，元素与元素之间以逗号间隔(例如:"1,2,3,4,5")
	 *
	 *	@param	list	字符串集合
	 *	@return	字符串
	 */
	public static final String strListToStrings(List<String> list) {
		if(!isEmpty(list)) {
			String str = NULLSTR;
			for(int i = 0; i < list.size(); i ++) {
				str += list.get(i) + ",";
			}
			return str.substring(0, str.length() - 1);
		} else {
			return NULLSTR;
		}
	}

	/**
	 *	将字符串集合转换为一个字符串，元素与元素之间以delimiter间隔
	 *
	 *	@param	list	字符串集合
	 *	@return	字符串
	 */
	public static final String listToString(String delimiter, List<String> list) {
		return String.join(delimiter, list);
	}

	/**
	 *	将Long集合转换为一个字符串，元素与元素之间以delimiter间隔
	 *
	 *	@param	list	字符串集合
	 *	@return	字符串
	 */
	public static final String listToString(List<Long> list, String delimiter) {
		return StringUtils.join(list, ",");
	}

	/**
	 *	将int型集合转换为一个字符串， 元素与元素之间以逗号间隔(例如:"1,2,3,4,5")
	 *
	 *	@param	list	int型集合
	 *	@return	字符串
	 */
	public static final String intListToStrings(List<Integer> list) {
		if(list.size() > 0) {
			String str = NULLSTR;
			for(int i = 0; i < list.size(); i ++) {
				str += list.get(i) + ",";
			}
			return str.substring(0, str.length() - 1);
		} else {
			return NULLSTR;
		}
	}

	/**
	 *	字符串型list转int型list
	 *
	 *	@param	list	字符串型list
	 *	@return	返回int型list
	 */
	public static final List<Integer> strListToIntList(List<String> list) {
		if(!isEmpty(list)) {
			List<Integer> newList = new ArrayList<Integer>();
			for(String s : list) {
				newList.add(Integer.parseInt(s));
			}
			return newList;
		} else {
			return new ArrayList<Integer>();
		}
	}

	/**
	 *	将Integer型集合进行数据进行顺序排序
	 *
	 *	@param	intList	整型数集合
	 *	@param	sortModel	排序方式
	 *	@return	返回排序后的Integer型集合
	 */
	public static final List<Integer> sortIntList(List<Integer> intList, int sortModel) {
		if (SORT_ASC == sortModel) {
			Collections.sort(intList);
		} else if (SORT_DESC == sortModel) {
			Collections.sort(intList, (a, b) -> b.compareTo(a));
		}
		return intList;
	}

	/**
	 * Collection集合转 List集合
	 * @param collection
	 * @return 返回List集合
	 */
	public static final <T> List<T> collectionToList(Collection<T> collection) {
		return new ArrayList<T>(collection);
	}

	/**
	 * 深度复制list对象,先序列化对象，再反序列化对象（造成不同的类加载器对同一个类进行加载）
	 *
	 * @param src 需要复制的对象列表
	 * @return 返回新的对象列表
	 * @throws IOException 读取Object流信息失败
	 * @throws ClassNotFoundException 泛型类不存在
	 */
	public static <T> List<T> deepCopy(List<T> src)
			throws IOException, ClassNotFoundException
	{
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(byteOut);
		out.writeObject(src);
		ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
		ObjectInputStream in = new ObjectInputStream(byteIn);
		return (List<T>)in.readObject();
	}

	/**
	 * 基于迭代的集合List深拷贝
	 * @param src 源集合
	 * @param <T> POJO对象
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> deepCopyOfForeach(List<T> src) throws Exception {
		List<T> dest = new ArrayList<T>();
		for (T t : src) {
			Object o = t.getClass().newInstance();
			T d = (T) DataConverter.convert(t, o);
			dest.add(d);
		}
		return dest;
	}


	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>();
		list.add(100);
		list.add(33);
		list.add(3);
		list.add(1);
		list.add(30);
		list.add(2);
//		sortIntList(list, SORT_DESC);
		List<Integer> result = list.stream().filter(it -> {return it < 10;}).collect(Collectors.toList());
		System.out.println(result.toString());
	}
}
