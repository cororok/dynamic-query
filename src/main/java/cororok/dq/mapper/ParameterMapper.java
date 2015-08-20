package cororok.dq.mapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public interface ParameterMapper {

	void setParameterValue(PreparedStatement ps, int parameterIndex, Object parameter) throws SQLException;

	void setParameterValue(PreparedStatement ps, int parameterIndex, Object parameterValue, int fieldType)
			throws SQLException;
}
