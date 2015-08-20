package cororok.dq.util;

import java.lang.reflect.Field;
import java.util.Comparator;

/**
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public class FiledComparator implements Comparator<Field> {

	@Override
	public int compare(Field l, Field r) {
		return l.getName().compareTo(r.getName());
	}

}
