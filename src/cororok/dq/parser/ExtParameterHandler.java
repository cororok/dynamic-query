package cororok.dq.parser;

/**
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public class ExtParameterHandler extends ParameterHandler {

	public ExtParameterHandler(TokenContext context) {
		super(context);
	}

	@Override
	public void exit() throws ParsingException {
		context.queryBuilder.addExtParameter(token);

		context.setHandler(context.getCallerHandler());
	}

	@Override
	public void putChar(char c) throws ParsingException {
		if (c == Characters.SUB_END) {
			close();
			exit();
			context.putChar(Characters.SUB_END);
		} else
			super.putChar(c);
	}

}
