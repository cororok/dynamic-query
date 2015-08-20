package cororok.dq;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import cororok.dq.mapper.ParameterMapper;
import cororok.dq.mapper.ResultSetMapper;
import cororok.dq.mapper.SimpleParameterMapper;
import cororok.dq.mapper.TimestampResultSetMapper;
import cororok.dq.parser.ParsingException;

/**
 * It contains final (fixed size, read only ) mainQueries and dynamic subQueries which grows. It creates and returns
 * QueryUtil for Query/SubQuery. A query is shared by threads and queryUtils. But each different queryUtil should be
 * used by each thread.
 * 
 * You would use IOC, singleton class or static class to maintain this instance during the application runs.
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public class QueryMap {

	private final Map<String, MainQuery> mainQueries;
	Map<String, Query> subQueries;

	private AtomicInteger countSub = new AtomicInteger();

	/**
	 * is shared by QueryUtils created by this instance.
	 */
	ParameterMapper parameterMapper = SimpleParameterMapper.getInstance();
	/**
	 * is shared by QueryUtils created by this instance.
	 */
	ResultSetMapper resultSetMapper = TimestampResultSetMapper.getInstance();

	public QueryMap(Map<String, MainQuery> mainQueries) throws ParsingException {
		this.mainQueries = mainQueries;
		subQueries = new HashMap<String, Query>();
		updateSharedQuery();
	}

	private void updateSharedQuery() throws ParsingException {
		for (MainQuery mainQuery : mainQueries.values()) {
			mainQuery.updateSharedQuery(mainQueries);
		}
	}

	public QueryMap(Map<String, MainQuery> mainQueries, Map subQueries) throws ParsingException {
		this.mainQueries = mainQueries;
		this.subQueries = subQueries;
		updateSharedQuery();
	}

	QueryUtil createQueryUtil(Query query) {
		return new QueryUtil(query, parameterMapper, resultSetMapper);
	}

	/**
	 * create a QueryUtil for subQuery of mainQuery
	 * 
	 * @param mainQuery main Query
	 * @param subIds sub ids of main Query
	 * @return
	 */
	public QueryUtil createQueryUtil(Query mainQuery, String... subIds) {
		return createQueryUtil(getQuery(mainQuery, subIds));
	}

	/**
	 * create a QueryUtil for Query of mainId
	 * 
	 * @param mainId id of main Query
	 * @return
	 */
	public QueryUtil createQueryUtil(String mainId) {
		return createQueryUtil(getQuery(mainId));
	}

	/**
	 * create a QueryUtil for subQuery of mainQuery
	 * 
	 * @param mainId id of main Query
	 * @param subIds sub ids of main Query
	 * @return
	 */
	public QueryUtil createQueryUtil(String mainId, String... subIds) {
		return createQueryUtil(getQuery(mainId, subIds));
	}

	Query getOrCreateSubQuery(MainQuery mainQuery, String newId, String... subIds) {
		int count = countSub.get();
		Query oldOne = null;
		oldOne = subQueries.get(newId);
		if (count == countSub.get() && oldOne != null)
			return oldOne;

		// create a new query
		Query newSubQuery = new Query(newId);
		mainQuery.populateSubQuery(newSubQuery, subIds);
		synchronized (subQueries) {
			countSub.incrementAndGet();
			oldOne = subQueries.get(newId); // double check
			if (oldOne != null)
				return oldOne;

			subQueries.put(newId, newSubQuery);
			return newSubQuery;
		}

	}

	public ParameterMapper getParameterMapper() {
		return this.parameterMapper;
	}

	public ResultSetMapper getResultSetMapper() {
		return this.resultSetMapper;
	}

	/**
	 * return a subQuery of main Query.
	 * 
	 * @param mainQuery main Query
	 * @param subIds sub ids of main Query
	 * @return
	 */
	public Query getQuery(Query mainQuery, String... subIds) {
		return getQuery(mainQuery.getId(), subIds);
	}

	/**
	 * return a main Query whose id is mainId.
	 * 
	 * @param mainId id of main Query
	 * @return
	 */
	public Query getQuery(String mainId) {
		return mainQueries.get(mainId);
	}

	/**
	 * return a sub Query of main Query.
	 * 
	 * @param mainId id of main Query
	 * @param subIds sub ids of main Query
	 * @return
	 */
	public Query getQuery(String mainId, String... subIds) {
		MainQuery mainQuery = mainQueries.get(mainId);
		if (mainQuery == null)
			return null;

		Arrays.sort(subIds);
		StringBuilder sb = new StringBuilder(mainId);
		for (String subId : subIds) {
			sb.append('.');
			sb.append(subId);
		}

		return getOrCreateSubQuery(mainQuery, sb.toString(), subIds);
	}

	public Set<String> keySetOfMainQueries() {
		return mainQueries.keySet();
	}

	public Set<String> keySetOfSubQueries() {
		return subQueries.keySet();
	}

	/**
	 * replaces current parameterMapper with new one. But it does not replace old one that belongs to already
	 * instantiated QueryUtils. Probably it will be called at the first time to use customized ParameterMapper not
	 * default ParameterMapper.
	 * 
	 * 
	 * @param parameterMapper
	 */
	public void setParameterMapper(ParameterMapper parameterMapper) {
		if (parameterMapper == null)
			throw new NullPointerException();

		this.parameterMapper = parameterMapper;
	}

	/**
	 * replaces current ResultSetMapper with new one. see setParameterMapper(..)
	 * 
	 * @param resultSetMapper
	 */
	public void setResultMapper(ResultSetMapper resultSetMapper) {
		if (resultSetMapper == null)
			throw new NullPointerException();

		this.resultSetMapper = resultSetMapper;
	}

	public int sizeOfMainQueries() {
		return mainQueries.size();
	}

	public int sizeOfSubQueries() {
		return subQueries.size();
	}

}
