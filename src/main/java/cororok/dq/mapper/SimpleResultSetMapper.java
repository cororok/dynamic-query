package cororok.dq.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * simply return value using rs.getObject(..) method.
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
	public Object getObject(ResultSet rs, int columnIndex, int columnType) throws SQLException {
		return rs.getObject(columnIndex);
	}

}