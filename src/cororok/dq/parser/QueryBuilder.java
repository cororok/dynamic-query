package cororok.dq.parser;

import java.util.Map;

import cororok.dq.MainQuery;

/**
 * assembles tokens and make queries.
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public interface QueryBuilder {

	abstract void addId(Token token) throws ParsingException;

	abstract void addParameter(Token token) throws ParsingException;

	abstract void addExtParameter(Token token) throws ParsingException;

	abstract void addExtText(Token token) throws ParsingException;

	abstract void addSharedId(Token token, int startingPosition)
			throws ParsingException;

	abstract void addSubId(Token token, int startingPosition)
			throws ParsingException;

	abstract void addText(Token token) throws ParsingException;

	abstract Map<String, MainQuery> getQueries() throws ParsingException;

	abstract void close() throws ParsingException;

	int sizeOfMainQuery();

	int sizeOfTotalExtQueries();

}
