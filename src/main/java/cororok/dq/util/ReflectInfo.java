package cororok.dq.util;

import java.lang.reflect.Field;

/**
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public class ReflectInfo {

	public final String className;
	private final Field[] fields;
	private final int[] types;

	public ReflectInfo(String className, Field[] fields, int[] types) {
		this.className = className;
		this.fields = fields;
		this.types = types;
	}

	public int getIndexOfField(String fieldName) {
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

	public void setFieldValue(int index, Object bean, Object newValue) throws Exception {
		fields[index].set(bean, newValue);
	}

	public Object getFieldValue(int index, Object bean) throws Exception {
		return fields[index].get(bean);
	}

	public int size() {
		return fields.length;
	}
}
