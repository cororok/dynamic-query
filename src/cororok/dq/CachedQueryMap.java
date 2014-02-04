package cororok.dq;

import java.util.HashMap;
import java.util.Map;

import cororok.dq.parser.ParsingException;
import cororok.dq.util.CachedMap;

/**
 * It limits the maximum size of subQuery. if it reaches the maximum(cacheSize)
 * it removes old one.
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public class CachedQueryMap extends QueryMap {

	public CachedQueryMap(Map<String, MainQuery> queries, int cacheSize)
			throws ParsingException {
		super(queries, new CachedMap<String, LinkedQuery>(
				new HashMap<String, LinkedQuery>(), cacheSize));
	}

	@Override
	synchronized Query getOrCreateSubQuery(MainQuery mainQuery, String newId,
			String... subIds) {
		Query oldSubQuery = null;
		oldSubQuery = subQueries.get(newId);
		if (oldSubQuery != null)
			return oldSubQuery;

		Query newSubQuery = new LinkedQuery(newId);
		mainQuery.populateSubQuery(newSubQuery, subIds);

		subQueries.put(newId, newSubQuery);
		return newSubQuery;
	}

}
