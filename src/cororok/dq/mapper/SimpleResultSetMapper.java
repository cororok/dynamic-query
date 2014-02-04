package cororok.dq.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;

/**
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public class SimpleResultSetMapper implements ResultSetMapper {

	private static final SimpleResultSetMapper instance = new SimpleResultSetMapper();

	private SimpleResultSetMapper() {
	}

	public static SimpleResultSetMapper getInstance() {
		return instance;
	}

	@Override
	public Object getObject(ResultSet rs, int columnIndex, int columnType)
			throws SQLException {

		switch (columnType) {
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
