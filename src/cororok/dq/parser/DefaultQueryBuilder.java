package cororok.dq.parser;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cororok.dq.MainQuery;
import cororok.dq.TempQuery;

/**
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public class DefaultQueryBuilder implements QueryBuilder {

	Logger log = LoggerFactory.getLogger(this.getClass());
	Map<String, MainQuery> mainQueries = new HashMap<String, MainQuery>();

	TempQuery tempMainQuery;
	TempQuery tempExtQuery;
	int totalExtQueries = 0;

	@Override
	public void addId(Token token) throws ParsingException {
		String value = token.getStringAndReset();
		log.debug(value);

		saveQuery();
		this.tempMainQuery = new TempQuery(value);
	}

	private void saveQuery() {
		if (tempMainQuery != null) {
			if (mainQueries.containsKey(tempMainQuery.id))
				throw new RuntimeException(tempMainQuery.id + " alreay exist");

			totalExtQueries += tempMainQuery.tempExtQueries.size();
			mainQueries.put(tempMainQuery.id, tempMainQuery.createQuery());

			this.tempMainQuery = null; // to correct correct # of queries
			this.tempExtQuery = null;
		}
	}

	@Override
	public void addText(Token token) throws ParsingException {
		String value = token.getStringAndReset();
		log.debug(value);
		this.tempMainQuery.text = value;
	}

	@Override
	public void addParameter(Token token) throws ParsingException {
		String value = token.getStringAndReset();
		value = Characters.convertColumnName(value);
		log.debug(value);
		this.tempMainQuery.parameters.add(value);
	}

	@Override
	public void addSubId(Token token, int startPosition)
			throws ParsingException {
		String value = token.getStringAndReset();
		log.debug(value);
		this.tempExtQuery = new TempQuery(value);
		this.tempExtQuery.startPosition = startPosition;
		this.tempExtQuery.startParameter = this.tempMainQuery.parameters.size();

		this.tempMainQuery.tempExtQueries.add(this.tempExtQuery);
	}

	@Override
	public void addSharedId(Token token, int startingPosition)
			throws ParsingException {
		addSubId(token, startingPosition);
	}

	@Override
	public void addExtText(Token token) throws ParsingException {
		String value = token.getStringAndReset();
		log.debug(value);
		this.tempExtQuery.text = value;
	}

	@Override
	public void addExtParameter(Token token) throws ParsingException {
		String value = token.getStringAndReset();
		value = Characters.convertColumnName(value);
		log.debug(value);
		this.tempExtQuery.parameters.add(value);
	}

	public Map<String, MainQuery> getQueries() throws ParsingException {
		return mainQueries;
	}

	@Override
	public void close() throws ParsingException {
		log.debug("close");
		if (this.tempMainQuery == null)
			return;

		if (this.tempMainQuery.text == null)
			throw new NeedValueException(";");
		else {
			saveQuery();
			this.tempMainQuery = null;
			this.tempExtQuery = null;
		}
	}

	@Override
	public int sizeOfMainQuery() {
		// +1 if it didn't add tempMain yet
		return mainQueries.size() + (tempMainQuery == null ? 0 : 1);
	}

	@Override
	public int sizeOfTotalExtQueries() {
		return totalExtQueries
				+ (tempMainQuery == null ? 0 : tempMainQuery.tempExtQueries
						.size());
	}

}
