package net.gf.exception;

import net.gf.model.PrimeError;

public class WrongRangeException extends RuntimeException {

	private static final long serialVersionUID = -5160154600286047031L;

	private final PrimeError error;
	public WrongRangeException(PrimeError error) {
		super(error.getError());
		
		this.error = error;
	}
	
	public PrimeError getError() {
		return this.error;
	}
}
