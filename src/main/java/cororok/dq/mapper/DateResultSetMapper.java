package cororok.dq.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;

/**
 * convert TIME, TIMESTAMP and DATE to java.util.Date
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public class DateResultSetMapper implements ResultSetMapper {

	private static final DateResultSetMapper instance = new DateResultSetMapper();

	private DateResultSetMapper() {
	}

	public static DateResultSetMapper getInstance() {
		return instance;
	}

	@Override
	public Object getObject(ResultSet rs, int columnIndex, int columnType) throws SQLException {

		switch (columnType) {
		case Types.TIME:
			return getJavaDate(rs.getTimestamp(columnIndex));
		case Types.TIMESTAMP:
			return getJavaDate(rs.getTimestamp(columnIndex));
		case Types.DATE:
			return getJavaDate(rs.getTimestamp(columnIndex));
		default:
			return rs.getObject(columnIndex);
		}
	}

	private Date getJavaDate(Timestamp timestamp) {
		if (timestamp == null)
			return null;
		return new Date(timestamp.getTime());
	}

}
