package cororok.dq.parser;

/**
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public class NeedValueException extends ParsingException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NeedValueException() {
		super("It needs a value");
	}

	public NeedValueException(String msg) {
		super("It needs a value :" + msg);
	}

}
