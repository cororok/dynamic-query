package cororok.dq.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public interface ResultSetMapper {

	public Object getObject(ResultSet rs, int columnIndex, int columnType)
			throws SQLException;

}
