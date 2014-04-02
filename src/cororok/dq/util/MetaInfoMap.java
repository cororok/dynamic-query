package cororok.dq.util;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author songduk.park cororok@gmail.com
 *
 */
public class MetaInfoMap {

	static Map<String, ReflectInfo> REFLECT_INFO_MAP = new HashMap<String, ReflectInfo>();

	/**
	 * It uses TreeMap because TreeMap uses comparison to identify a key.
	 */
	static Map<ReadOnlyArray<String>, ReadOnlyArray<String>> COLUMN_MAP = new TreeMap<ReadOnlyArray<String>, ReadOnlyArray<String>>();
	static Map<ReadOnlyInts, ReadOnlyInts> TYPE_MAP = new TreeMap<ReadOnlyInts, ReadOnlyInts>();

	static SafeReadWrite<Class<?>, ReflectInfo> rwReflectionInfo;
	static SafeReadWrite<ReadOnlyArray<String>, ReadOnlyArray<String>> rwColumnInfo;
	static SafeReadWrite<ReadOnlyInts, ReadOnlyInts> rwTypeInfo;

	static {
		rwReflectionInfo = new SafeReadWrite<Class<?>, ReflectInfo>() {
			@Override
			ReflectInfo writeLogic(Class<?> cl) {
				// double check
				ReflectInfo oldOne = readLogic(cl);
				if (oldOne != null)
					return oldOne;

				ReflectInfo newOne = ReflectUtil.buildReflectInfo(cl);
				REFLECT_INFO_MAP.put(cl.getName(), newOne);
				return newOne;
			}

			@Override
			ReflectInfo readLogic(Class<?> cl) {
				return REFLECT_INFO_MAP.get(cl.getName());
			}
		};

		rwColumnInfo = new SafeReadWrite<ReadOnlyArray<String>, ReadOnlyArray<String>>() {
			@Override
			ReadOnlyArray<String> writeLogic(ReadOnlyArray<String> newColumn) {
				// double check
				ReadOnlyArray<String> oldOne = readLogic(newColumn);
				if (oldOne != null)
					return oldOne;

				COLUMN_MAP.put(newColumn, newColumn);
				return newColumn;
			}

			@Override
			ReadOnlyArray<String> readLogic(ReadOnlyArray<String> newColumn) {
				return COLUMN_MAP.get(newColumn);
			}
		};

		rwTypeInfo = new SafeReadWrite<ReadOnlyInts, ReadOnlyInts>() {
			@Override
			ReadOnlyInts writeLogic(ReadOnlyInts newTypes) {
				// double check
				ReadOnlyInts oldOne = readLogic(newTypes);
				if (oldOne != null)
					return oldOne;

				TYPE_MAP.put(newTypes, newTypes);
				return newTypes;
			}

			@Override
			ReadOnlyInts readLogic(ReadOnlyInts newTypes) {
				return TYPE_MAP.get(newTypes);
			}
		};

	}

	/**
	 * if old one exists return old one or create and put new one and return new
	 * one
	 * 
	 * @param cl
	 * @return
	 */
	public static ReflectInfo getReflectlInfo(Class<?> cl) {
		ReflectInfo oldOne = rwReflectionInfo.read(cl);
		if (oldOne != null)
			return oldOne;

		return rwReflectionInfo.write(cl);
	}

	/**
	 * if old one exists return old one or put new one and return new one
	 * 
	 * @param newColumns
	 * @return
	 */
	public static ReadOnlyArray<String> getOrPutColumns(
			ReadOnlyArray<String> newColumns) {
		ReadOnlyArray<String> oldOne = rwColumnInfo.read(newColumns);
		if (oldOne != null)
			return oldOne;

		return rwColumnInfo.write(newColumns);
	}

	/**
	 * if old one exists return old one or put new one and return new one
	 * 
	 * @param newTypes
	 * @return
	 */
	public static ReadOnlyInts getOrPutTypes(ReadOnlyInts newTypes) {
		ReadOnlyInts oldOne = rwTypeInfo.read(newTypes);
		if (oldOne != null)
			return oldOne;

		return rwTypeInfo.write(newTypes);
	}

}
