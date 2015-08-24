package cororok.dq;

import java.sql.SQLException;

/**
 * error when ParameterMapper throws an exception.
 * 
 * @author songduk.park cororok@gmail.com
 *
 */
public class ParamerSettingException extends SQLException {

	private static final long serialVersionUID = 1L;

	public ParamerSettingException(int i, String param, Object value, int fieldType, Exception e) {
		super("errors at index:" + i + ", value:" + value + ", for " + param + "(type=" + fieldType + ")", e);
	}

	public ParamerSettingException(int i, String param, Object value, Exception e) {
		super("errors at index:" + i + ", value:" + value + ", for " + param, e);
	}
}
