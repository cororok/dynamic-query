package cororok.dq.util;

import java.lang.reflect.Field;

/**
 * holds all {@link java.lang.reflect.Field} of a bean and used to get/set value of the field.
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public class ReflectInfo {

	public final String className;
	private final Field[] fields;
	private final int[] types;

	/**
	 * @param className
	 * @param fields it should be a sorted array by name because binary search needs that.
	 * @param types
	 */
	public ReflectInfo(String className, Field[] fields, int[] types) {
		this.className = className;
		this.fields = fields;
		this.types = types;
	}

	public int getIndexOfField(String fieldName) { // uses binary search
		int left = 0;
		int right = fields.length - 1;
		while (left <= right) {
			int middle = left + (right - left) / 2;
			int diff = fields[middle].getName().compareTo(fieldName);
			if (diff == 0)
				return middle;
			else if (diff < 0) {
				left = middle + 1; // careful
			} else {
				right = middle - 1; // careful
			}
		}
		return -1;
	}

	public String getFieldName(int index) {
		return fields[index].getName();
	}

	public int getFieldType(int index) {
		return types[index];
	}

	/**
	 * updates value of the target field in the bean with newValue
	 * 
	 * @param index of the target field
	 * @param targetBean which has the target field
	 * @param newValue
	 * @throws Exception
	 */
	public void setFieldValue(int index, Object targetBean, Object newValue) throws Exception {
		fields[index].set(targetBean, newValue);
	}

	/**
	 * 
	 * @param index of the target field
	 * @param targetBean which has the target field
	 * @return value of the target field in the bean
	 * @throws Exception
	 */
	public Object getFieldValue(int index, Object targetBean) throws Exception {
		return fields[index].get(targetBean);
	}

	public int size() {
		return fields.length;
	}
}
