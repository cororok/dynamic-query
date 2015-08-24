package cororok.dq.mapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Mapper which finds and calls a proper set method in PreparedStatement to set given parameterValue.
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public interface ParameterMapper {

	void setParameterValue(PreparedStatement ps, int parameterIndex, Object parameterValue) throws SQLException;

	/**
	 * set value parameterValue to PreparedStatement
	 * 
	 * @param ps
	 * @param parameterIndex starts from 1 not 0 as PreparedStatement des
	 * @param parameterValue will be passed to ps to be saved in ps.
	 * @param fieldType java type of parameterValue. see {@link cororok.dq.util.JavaTypes}
	 * @throws SQLException
	 */
	void setParameterValue(PreparedStatement ps, int parameterIndex, Object parameterValue, int fieldType)
			throws SQLException;
}
