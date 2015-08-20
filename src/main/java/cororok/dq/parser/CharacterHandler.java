package cororok.dq.parser;

/**
 * each handler handles its own character only.
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public interface CharacterHandler extends CommentHandler {

	public abstract void putSpace() throws ParsingException;

	public abstract void putSeparator() throws ParsingException;

	public abstract void putEnd() throws ParsingException;

	public abstract void putParameter() throws ParsingException;

	public abstract void putSubId() throws ParsingException;

	public abstract void putTab() throws ParsingException;

	public abstract void putNewLine() throws ParsingException;

	public abstract void putCharAsName(char c) throws ParsingException;

	public abstract void close() throws ParsingException;

	public abstract void exit() throws ParsingException;

}