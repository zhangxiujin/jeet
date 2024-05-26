package com.jeet.common.core.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 *	@author kernel
 */
public class DataConverter {

	/**
	 *	@param o 对象（对象的父类是Object）
	 *
	 * 	@return
	 * 		返回对象所有属性值集合
	 */
	public static List<Object> convertList(Object o) throws Exception {
		Field[] declaredFields = o.getClass().getDeclaredFields();
		List<Object> values = new ArrayList<Object>();
		for(Field f : declaredFields) {
			f.setAccessible(true);
			Object value = f.get(o);
			values.add(value);
		}
		return values;
	}

	/**
	 * 	@author kernel
	 *
	 *	针对两个pojo对象中同类型和名称相同的属性之间的值拷贝
	 *
	 * 	@param	source	源对象
	 * 	@param	dest	目标对象
	 *
	 * 	@return	目标对象
	 *
	 */
	public static Object convert(Object source, Object dest) {
		Field[] sourceFields = source.getClass().getDeclaredFields();
		Field[] destFields = dest.getClass().getDeclaredFields();

		Field[] sources = getAllField(source.getClass(), sourceFields);
		Field[] dests = getAllField(dest.getClass(), destFields);
		boolean[] sets = new boolean[dests.length];
		try {
			for(Field s : sources) {
				for(int i = 0; i < dests.length; i ++) {
					Field d = dests[i];
					d.setAccessible(true);
					s.setAccessible(true);
					if(d.getName().equals(s.getName())
							&& d.getType().getName().equals(s.getType().getName()) && sets[i] == false) {
						d.set(dest, s.get(source));
						sets[i] = true;
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return dest;
	}

	private static Field[] getAllField(Class<?> source, Field[] fields) {
		Class<?> superclass = source.getSuperclass();
		if(superclass.getSimpleName().equals("Object")) {
			return fields;
		} else {
			Field[] superFields = superclass.getDeclaredFields();
			Field[] sourceFields = new Field[fields.length + superFields.length];
			System.arraycopy(fields, 0, sourceFields, 0, fields.length);
			System.arraycopy(superFields, 0, sourceFields, fields.length, superFields.length);
			fields = sourceFields;
			return getAllField(superclass, fields);
		}
	}


}
