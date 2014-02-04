package cororok.dq.parser;

/**
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public interface CommentHandler {

	void putCharWhileString(char c) throws ParsingException;;

	void putChar(char c) throws ParsingException;;

	void putCharWhileLineComment(char c) throws ParsingException;;

	void putCharWhileBlockComment(char c) throws ParsingException;;

}
