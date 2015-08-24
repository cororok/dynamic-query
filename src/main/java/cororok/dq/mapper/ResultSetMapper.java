package cororok.dq.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mapper which calls ResultSet to get value of the column
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public interface ResultSetMapper {

	/**
	 * @param rs target ResultSet which will return value.
	 * @param columnIndex index of the target column in the ResultSet
	 * @param columnType it may be used to find a proper get method in ResultSet
	 * @return value of the column
	 * @throws SQLException
	 */
	public Object getObject(ResultSet rs, int columnIndex, int columnType) throws SQLException;

}
