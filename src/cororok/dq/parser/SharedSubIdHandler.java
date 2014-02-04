package cororok.dq.parser;

/**
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public class SharedSubIdHandler extends IdHandler {

	public SharedSubIdHandler(TokenContext context) {
		super(context);
	}

	@Override
	public void putSubId() throws ParsingException {
		throw new UnsupportedCharacterException(this, Characters.SUB_ID);
	}

	@Override
	public void exit() throws ParsingException {
		context.queryBuilder.addSharedId(token,
				context.subIdHandler.startingPosition);

		context.setHandler(context.textHandler);
	}

	@Override
	public void putChar(char c) throws ParsingException {
		super.putCharAsName(c);
	}

	@Override
	public void putSeparator() throws ParsingException {
		close();
		exit();
	}

	@Override
	public void putSpace() throws ParsingException {
		super.putSpace();
		exit();
	}
}
