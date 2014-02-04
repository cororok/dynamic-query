package cororok.dq.parser;

/**
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public class ParameterHandler extends IdHandler {

	public ParameterHandler(TokenContext context) {
		super(context);
	}

	@Override
	public void putSpace() throws ParsingException {
		if (token.isStarted() == false)
			throw new NeedValueException();

		close();
		exit();
		context.putSpace();// to next
	}

	public void close() throws ParsingException {
		if (token.length() == 0) {
			throw new NeedValueException();
		}

		token.close();
	}

	@Override
	public void putParameter() throws ParsingException {
		if (token.length() > 0)
			throw new UnsupportedCharacterException(this, Characters.PARAMETER);

		token.reset();
		TextHandler textHandler = (TextHandler) context.getCallerHandler();

		String columnNameInSQL = textHandler.getPreviousColumnName();
		String parameterName = Characters.convertColumnName(columnNameInSQL);

		token.addString(parameterName);
		close();
		exit();
	}

	@Override
	public void putSubId() throws ParsingException {
		close();
		exit();
		context.putSubId();
	}

	@Override
	public void exit() throws ParsingException {
		context.queryBuilder.addParameter(token);

		context.setHandler(context.getCallerHandler());
	}

	@Override
	public void putChar(char c) throws ParsingException {
		try {
			super.putCharAsName(c);
		} catch (UnsupportedCharacterException e) {
			close();
			exit();
			context.putChar(c);
		}
	}

	@Override
	public void putEnd() throws ParsingException {
		close();
		exit();
		context.putEnd();
	}

	@Override
	public void putNewLine() throws ParsingException {
		close();
		exit();
		context.putNewLine();
	}
}
