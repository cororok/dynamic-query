package cororok.dq.util;

/**
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public class JavaTypes {

	public static final int INTEGER = 1;
	public static final int LONG = 2;
	public static final int DOUBLE = 3;
	public static final int FLOAT = 4;
	public static final int BOOLEAN = 5;
	public static final int STRING = 6;
	public static final int DATE_SQL = 7;
	public static final int TIME_SQL = 8;
	public static final int TIMESTAMP_SQL = 9;
	public static final int DATE = 10;
	public static final int BIGDECIMAL = 11;

	public static final int UNKNOWN = -1;

	public static int getType(String name) {
		if (name.equals("java.lang.String"))
			return STRING;

		if (name.equals("java.math.BigDecimal"))
			return BIGDECIMAL;

		if (name.equals("int") || name.equals("java.lang.Integer"))
			return INTEGER;

		if (name.equals("double") || name.equals("java.lang.Double"))
			return DOUBLE;

		if (name.equals("long") || name.equals("java.lang.Long"))
			return LONG;

		if (name.equals("float") || name.equals("java.lang.Float"))
			return FLOAT;

		if (name.equals("java.util.Date"))
			return DATE;

		if (name.equals("java.sql.Timestamp"))
			return TIMESTAMP_SQL;

		if (name.equals("java.sql.Date"))
			return DATE_SQL;
		if (name.equals("java.sql.Time"))
			return TIME_SQL;

		if (name.equals("boolean") || name.equals("java.lang.Boolean"))
			return BOOLEAN;

		return -1;
	}
}
