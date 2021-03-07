package net.gf.model;

import java.util.List;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * A class holds found prime numbers between lower and upper. 
 * lower and upper number are included.
 * The service returns this object to clients if serving successfully.
 * 
 * @author gfeng
 *
 */
@ToString
public class PrimeData {
	@Getter
	@Setter
	private long lower;

	@Getter
	@Setter
	private long upper;
	
	@Getter
	@Setter
	private List<Long> primes;
	
	public PrimeData() {
		
	}
	
	public PrimeData(long lower, long upper) {
		this.lower = lower;
		this.upper = upper;
	}
}
