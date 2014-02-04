package cororok.dq.util;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MetaInfoMap {

	static Map<String, ReflectInfo> REFLECT_INFO_MAP = new HashMap<String, ReflectInfo>();

	static Map<ReadOnlyArray<String>, ReadOnlyArray<String>> COLUMN_MAP = new TreeMap<ReadOnlyArray<String>, ReadOnlyArray<String>>();
	static Map<ReadOnlyInts, ReadOnlyInts> TYPE_MAP = new TreeMap<ReadOnlyInts, ReadOnlyInts>();

	private static AtomicInteger COUNT_REFLECT = new AtomicInteger();
	private static AtomicInteger COUNT_COUNT = new AtomicInteger();
	private static AtomicInteger COUNT_TYPE = new AtomicInteger();

	public static ReflectInfo getReflectlInfo(Class<?> cl) {
		String className = cl.getName();
		int count = COUNT_REFLECT.get();

		ReflectInfo oldOne = null;
		oldOne = REFLECT_INFO_MAP.get(className);
		if (count == COUNT_REFLECT.get() && oldOne != null)
			return oldOne;

		ReflectInfo newOne = ReflectUtil.buildReflectInfo(cl);
		synchronized (REFLECT_INFO_MAP) {
			COUNT_REFLECT.incrementAndGet();
			oldOne = REFLECT_INFO_MAP.get(className); // double check
			if (oldOne != null)
				return oldOne;

			REFLECT_INFO_MAP.put(className, newOne);
			COUNT_REFLECT.incrementAndGet();
			return newOne;
		}
	}

	public static ReadOnlyArray<String> getOrPutColumns(
			ReadOnlyArray<String> columns) {
		int count = COUNT_COUNT.get();

		ReadOnlyArray<String> oldOne = null;
		oldOne = COLUMN_MAP.get(columns);
		if (count == COUNT_COUNT.get() && oldOne != null)
			return oldOne;

		synchronized (COLUMN_MAP) {
			COUNT_COUNT.incrementAndGet();
			oldOne = COLUMN_MAP.get(columns);
			; // double check
			if (oldOne != null)
				return oldOne;

			COLUMN_MAP.put(columns, columns);
			COUNT_COUNT.incrementAndGet();
			return columns;
		}
	}

	public static ReadOnlyInts getOrPutTypes(ReadOnlyInts types) {
		int count = COUNT_TYPE.get();

		ReadOnlyInts oldOne = null;
		oldOne = TYPE_MAP.get(types);
		if (count == COUNT_TYPE.get() && oldOne != null)
			return oldOne;

		synchronized (TYPE_MAP) {
			COUNT_TYPE.incrementAndGet();
			oldOne = TYPE_MAP.get(types);
			; // double check
			if (oldOne != null)
				return oldOne;

			TYPE_MAP.put(types, types);
			COUNT_TYPE.incrementAndGet();
			return types;
		}
	}

}
