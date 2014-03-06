package de.florianbrucker.chainboard;

public class IllegalTransitionException extends RuntimeException {

	private static final long serialVersionUID = -481684410229646516L;

	public IllegalTransitionException() {
	}

	public IllegalTransitionException(String detailMessage) {
		super(detailMessage);
	}

	public IllegalTransitionException(Throwable throwable) {
		super(throwable);
	}

	public IllegalTransitionException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}
	
	public IllegalTransitionException(State oldState, State newState) {
		super("Cannot transition from state " + oldState + " into state " + newState);
	}

}
