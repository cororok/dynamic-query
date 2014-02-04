package cororok.dq.parser;

/**
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public class TextHandler extends AbstractCharacterHandler {

	public TextHandler(TokenContext context) {
		super(context);
		this.token = new Token();
	}

	@Override
	public void close() throws ParsingException {
		token.close();
		exit();
	}

	@Override
	public void exit() throws ParsingException {
		context.queryBuilder.addText(token);
		context.setHandler(context.idHandler);
	}

	String getPreviousColumnName() {
		return token.getPreviousColumnName();
	}

	@Override
	public void putChar(char c) throws ParsingException {
		token.addChar(c);
	}

	@Override
	public void putCharWhileBlockComment(char c) throws ParsingException {
		token.addChar(c);
	}

	@Override
	public void putCharWhileLineComment(char c) throws ParsingException {
		// ignore
	}

	@Override
	public void putCharWhileString(char c) throws ParsingException {
		token.addChar(c);
	}

	@Override
	public void putEnd() throws ParsingException {
		close();
	}

	@Override
	public void putNewLine() throws ParsingException {
		if (token.isStarted()) // ignore first one
			putChar(Characters.NEWLINE);
		else
			token.start();
	}

	@Override
	public void putParameter() throws ParsingException {
		putChar(Characters.QUESTION);

		context.setCallerHandler(this);
		context.setHandler(context.parameterHandler);
	}

	@Override
	public void putSeparator() throws ParsingException {
		context.warn("detected '" + Characters.SEPARATOR
				+ "'  Maybe you missed ';' at the end of the previous query.");
		putChar(Characters.SEPARATOR);
	}

	@Override
	public void putSpace() throws ParsingException {
		if (token.isStarted())
			putChar(Characters.SPACE);
	}

	@Override
	public void putSubId() throws ParsingException {
		context.subIdHandler.setWhereItStart(token.length());
		context.setHandler(context.subIdHandler);
	}

	@Override
	public void putTab() throws ParsingException {
		if (token.isStarted())
			putChar(Characters.TAB);
	}
}
