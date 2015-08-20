package cororok.dq;

import java.io.Reader;
import java.util.Map;

import cororok.dq.parser.DefaultQueryBuilder;
import cororok.dq.parser.ParsingException;
import cororok.dq.parser.QueryBuilder;
import cororok.dq.parser.QueryParser;

/**
 * static methods to create a QueryMap
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public class QueryFactory {

	private QueryFactory() {
	}

	private static QueryBuilder createQueryBuilder() {
		return new DefaultQueryBuilder();
	}

	private static QueryParser createQueryParser(QueryBuilder builder) {
		return new QueryParser(builder);
	}

	/**
	 * create a QueryMap with file
	 * 
	 * @param file
	 * @return
	 * @throws ParsingException
	 */
	public static QueryMap createQueryMap(String file) throws ParsingException {
		return createQueryMap(file, 0);
	}

	/**
	 * create a CachedQueryMap with file
	 * 
	 * @param file
	 * @param cacheSize
	 * @return
	 * @throws ParsingException
	 */
	public static QueryMap createQueryMap(String file, int cacheSize) throws ParsingException {
		QueryBuilder builder = createQueryBuilder();
		QueryParser parser = createQueryParser(builder);

		parser.parse(file);
		return createQueryMap(builder.getQueries(), cacheSize);
	}

	/**
	 * create a QueryMap with reader
	 * 
	 * @param reader contains sqls
	 * 
	 * @return
	 * @throws ParsingException
	 */
	public static QueryMap createQueryMap(Reader reader) throws ParsingException {
		return createQueryMap(reader, 0);
	}

	/**
	 * create a CachedQueryMap with reader
	 * 
	 * @param reader
	 * @param cacheSize
	 * @return
	 * @throws ParsingException
	 */
	public static QueryMap createQueryMap(Reader reader, int cacheSize) throws ParsingException {
		QueryBuilder builder = createQueryBuilder();
		QueryParser parser = createQueryParser(builder);

		parser.parse(reader);
		return createQueryMap(builder.getQueries(), cacheSize);
	}

	private static QueryMap createQueryMap(Map<String, MainQuery> map, int cacheSize) throws ParsingException {
		if (cacheSize > 0)
			return new CachedQueryMap(map, cacheSize);
		else
			return new QueryMap(map);
	}

}
