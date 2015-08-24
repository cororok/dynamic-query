package cororok.dq.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;

/**
 * factory class. makes ReflectInfo from given class.
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public class ReflectUtil {

	public static FiledComparator FIELD_COMPARATOR = new FiledComparator();

	public static ReflectInfo buildReflectInfo(String className) throws Exception {
		return buildReflectInfo(Class.forName(className));
	}

	public static ReflectInfo buildReflectInfo(Class<?> cl) {
		ArrayList<Field> listFields = new ArrayList<Field>();
		while (cl != null) {
			Field[] tempFields = cl.getDeclaredFields();
			for (Field field : tempFields) {
				if (field.isAccessible() == false)
					field.setAccessible(true);

				listFields.add(field);
			}

			cl = cl.getSuperclass();
			if (cl == null || cl.equals(Object.class))
				break;
		}

		Collections.sort(listFields, FIELD_COMPARATOR);

		Field[] fields = new Field[listFields.size()];
		int[] types = new int[listFields.size()];
		for (int i = 0; i < fields.length; i++) {
			Field field = listFields.get(i);
			fields[i] = field;
			types[i] = JavaTypes.getType(field.getType().getName());
		}

		return new ReflectInfo(cl.getName(), fields, types);
	}

}
