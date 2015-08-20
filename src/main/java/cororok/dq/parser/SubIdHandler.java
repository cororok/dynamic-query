package cororok.dq.parser;

/**
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public class SubIdHandler extends IdHandler {

	int startingPosition;

	public SubIdHandler(TokenContext context) {
		super(context);
	}

	@Override
	public void putSubId() throws ParsingException {
		if (token.isStarted())
			super.putSubId();
		else
			context.setHandler(context.sharedSubIdHandler);
	}

	@Override
	public void putSpace() throws ParsingException {
		if (token.isClosed())
			return;

		if (token.isStarted() == false)
			throw new NeedValueException();

		close();
	}

	@Override
	public void exit() throws ParsingException {
		context.queryBuilder.addSubId(token, startingPosition);

		context.setHandler(context.extTextHandler);
	}

	@Override
	public void putSeparator() throws ParsingException {
		throw new UnsupportedCharacterException(Characters.SEPARATOR);
	}

	@Override
	public void putEnd() throws ParsingException {
		throw new ParsingException("need closing");
	}

	@Override
	public void putChar(char c) throws ParsingException {
		if (c == Characters.SUB_START) {
			putSubStart();
			return;
		} else if (c == Characters.SUB_END) {
			throw new UnsupportedCharacterException(c);
		}

		if (token.isClosed()) {
			throw new NeedValueException();
		}
		Characters.checkValidName(c);
		token.addChar(c);
	}

	void putSubStart() throws ParsingException {
		close();
		exit();
	}

	void setWhereItStart(int startingPosition) {
		this.startingPosition = startingPosition;
	}

}
