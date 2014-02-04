package cororok.dq.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public class TokenContext implements CharacterHandler {
	private CharacterHandler currentHandler;
	private CharacterHandler callerHandler;

	IdHandler idHandler = new IdHandler(this);
	TextHandler textHandler = new TextHandler(this);
	ParameterHandler parameterHandler = new ParameterHandler(this);
	ExtParameterHandler extParameterHandler = new ExtParameterHandler(this);
	SubIdHandler subIdHandler = new SubIdHandler(this);
	ExtTextHandler extTextHandler = new ExtTextHandler(this);
	SharedSubIdHandler sharedSubIdHandler = new SharedSubIdHandler(this);
	ExternalFileHandler externalFileHandler = new ExternalFileHandler(this);

	Logger log = LoggerFactory.getLogger(this.getClass());

	{
		// starts from idHandler
		this.currentHandler = idHandler;
	}

	String subFile;

	/**
	 * It is like the unix pipe, this sends output to builder as builder's
	 * input.
	 */
	QueryBuilder queryBuilder;
	FileInfo fileInfo;
	int countWarn;

	public TokenContext(QueryBuilder queryBuilder, FileInfo fileInfo) {
		this.queryBuilder = queryBuilder;
		this.fileInfo = fileInfo;
	}

	public void setFileInfo(FileInfo fileInfo) {
		this.fileInfo = fileInfo;
	}

	@Override
	public void close() throws ParsingException {
		this.queryBuilder.close();

		if (countWarn > 0)
			log.warn("It warned " + countWarn
					+ " times. Please check warnnings. Thank you");
	}

	@Override
	public void exit() throws ParsingException {
	}

	public CharacterHandler getCallerHandler() {
		return callerHandler;
	}

	CharacterHandler getCurrentHandler() {
		return this.currentHandler;
	}

	public String getSubFile() {
		return subFile;
	}

	@Override
	public void putChar(char c) throws ParsingException {
		currentHandler.putChar(c);
	}

	@Override
	public void putCharAsName(char c) throws ParsingException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void putCharWhileBlockComment(char c) throws ParsingException {
		currentHandler.putCharWhileBlockComment(c);
	}

	@Override
	public void putCharWhileLineComment(char c) throws ParsingException {
		currentHandler.putCharWhileLineComment(c);
	}

	@Override
	public void putCharWhileString(char c) throws ParsingException {
		currentHandler.putCharWhileString(c);
	}

	@Override
	public void putEnd() throws ParsingException {
		currentHandler.putEnd();
	}

	@Override
	public void putNewLine() throws ParsingException {
		currentHandler.putNewLine();
	}

	@Override
	public void putParameter() throws ParsingException {
		currentHandler.putParameter();
	}

	@Override
	public void putSeparator() throws ParsingException {
		currentHandler.putSeparator();
	}

	@Override
	public void putSpace() throws ParsingException {
		currentHandler.putSpace();
	}

	@Override
	public void putSubId() throws ParsingException {
		currentHandler.putSubId();
	}

	@Override
	public void putTab() throws ParsingException {
		currentHandler.putTab();
	}

	public void setCallerHandler(CharacterHandler handler) {
		this.callerHandler = handler;
	}

	/**
	 * replace the current handler with new one
	 * 
	 * @param handler
	 *            new handler
	 */
	void setHandler(CharacterHandler handler) {
		this.currentHandler = handler;
	}

	public String setSubFile(String subFile) {
		return this.subFile = subFile;
	}

	public void warn(String msg) {
		++countWarn;
		this.fileInfo.warn(msg);
	}

	public int sizeOfMainQuery() {
		return this.queryBuilder.sizeOfMainQuery();
	}

	public int sizeOfTotalExtQueries() {
		return this.queryBuilder.sizeOfTotalExtQueries();
	}

}
