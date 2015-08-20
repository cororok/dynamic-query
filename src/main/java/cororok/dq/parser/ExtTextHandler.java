package cororok.dq.parser;

/**
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public class ExtTextHandler extends TextHandler {

	public ExtTextHandler(TokenContext context) {
		super(context);
	}

	@Override
	public void putChar(char c) throws ParsingException {
		if (c == Characters.SUB_END) {
			close();
			return;
		}
		token.addChar(c);
	}

	@Override
	public void putParameter() throws ParsingException {
		putChar(Characters.QUESTION);

		context.setCallerHandler(this);
		context.setHandler(context.extParameterHandler);
	}

	@Override
	public void putEnd() throws ParsingException {
		throw new RuntimeException("Need charachter " + Characters.SUB_END);
	}

	@Override
	public void exit() throws ParsingException {
		context.queryBuilder.addExtText(token);
		context.setHandler(context.textHandler);
	}

}
