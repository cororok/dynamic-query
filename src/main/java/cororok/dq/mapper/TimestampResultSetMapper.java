package cororok.dq.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * convert TIME, TIMESTAMP and DATE to java.sql.Timestamp
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public class TimestampResultSetMapper implements ResultSetMapper {

	private static final TimestampResultSetMapper instance = new TimestampResultSetMapper();

	private TimestampResultSetMapper() {
	}

	public static TimestampResultSetMapper getInstance() {
		return instance;
	}

	@Override
	public Object getObject(ResultSet rs, int columnIndex, int columnType) throws SQLException {

		switch (columnType) {
		case Types.TIME:
			return rs.getTimestamp(columnIndex);
		case Types.TIMESTAMP:
			return rs.getTimestamp(columnIndex);
		case Types.DATE:
			return rs.getTimestamp(columnIndex);
		default:
			return rs.getObject(columnIndex);
		}
	}
}
