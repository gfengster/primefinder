package net.gf.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * A class holds error message 
 * The service returns this object to clients if serving fail.
 * 
 * @author gfeng
 *
 */
@ToString
public class PrimeError {
	@Getter
	@Setter
	private long lower;

	@Getter
	@Setter
	private long upper;
	
	@Getter
	@Setter
	@NonNull
	private String error;
	
	public PrimeError(long lower, long upper, String error) {
		this.lower = lower;
		this.upper = upper;
		this.error = error;
	}
}
