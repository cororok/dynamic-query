package cororok.dq.mapper;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

import cororok.dq.util.JavaTypes;

/**
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public class SimpleParameterMapper implements ParameterMapper {

	private static final SimpleParameterMapper instance = new SimpleParameterMapper();

	private SimpleParameterMapper() {
	}

	public static SimpleParameterMapper getInstance() {
		return instance;
	}

	@Override
	public void setParameterValue(PreparedStatement ps, int parameterIndex, Object parameterValue) throws SQLException {

		if (parameterValue instanceof String)
			ps.setString(parameterIndex, (String) parameterValue);
		else if (parameterValue instanceof Integer)
			ps.setInt(parameterIndex, ((Integer) parameterValue).intValue());
		else if (parameterValue instanceof BigDecimal)
			ps.setBigDecimal(parameterIndex, (BigDecimal) parameterValue);
		else if (parameterValue instanceof Double)
			ps.setDouble(parameterIndex, (Double) parameterValue);
		else if (parameterValue instanceof java.sql.Timestamp)
			ps.setTimestamp(parameterIndex, (java.sql.Timestamp) parameterValue);
		else if (parameterValue instanceof java.sql.Time)
			ps.setTime(parameterIndex, (java.sql.Time) parameterValue);
		else if (parameterValue instanceof java.sql.Date)
			ps.setDate(parameterIndex, (java.sql.Date) parameterValue);
		else if (parameterValue instanceof java.util.Date) {
			long time = ((java.util.Date) parameterValue).getTime();
			ps.setTimestamp(parameterIndex, new java.sql.Timestamp(time));
		} else
			ps.setObject(parameterIndex, parameterValue);
	}

	@Override
	public void setParameterValue(PreparedStatement ps, int parameterIndex, Object parameterValue, int fieldType)
			throws SQLException {

		switch (fieldType) {
		case JavaTypes.STRING:
			ps.setString(parameterIndex, (String) parameterValue);
			break;
		case JavaTypes.BIGDECIMAL:
			ps.setBigDecimal(parameterIndex, (BigDecimal) parameterValue);
			break;
		case JavaTypes.INTEGER:
			ps.setInt(parameterIndex, (Integer) parameterValue);
			break;
		case JavaTypes.DOUBLE:
			ps.setDouble(parameterIndex, (Double) parameterValue);
			break;

		case JavaTypes.LONG:
			ps.setLong(parameterIndex, (Long) parameterValue);
			break;

		case JavaTypes.FLOAT:
			ps.setFloat(parameterIndex, (Float) parameterValue);
			break;

		case JavaTypes.DATE:
			if (parameterValue == null) {
				ps.setTimestamp(parameterIndex, null);
				break;
			}

			long time = ((java.util.Date) parameterValue).getTime();
			ps.setTimestamp(parameterIndex, new Timestamp(time));
			break;
		case JavaTypes.TIMESTAMP_SQL:
			ps.setTimestamp(parameterIndex, (Timestamp) parameterValue);
			break;

		case JavaTypes.DATE_SQL:
			ps.setDate(parameterIndex, (java.sql.Date) parameterValue);
			break;
		case JavaTypes.TIME_SQL:
			ps.setTime(parameterIndex, (Time) parameterValue);
			break;
		case JavaTypes.BOOLEAN:
			ps.setBoolean(parameterIndex, (Boolean) parameterValue);
			break;

		default:
			throw new SQLException("unknown type for fieldType " + fieldType);
		}

	}
}
