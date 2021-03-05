package net.gf.model;

import java.util.List;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

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
	@NonNull
	private List<Long> primes;
	
	public PrimeData() {
		
	}
	
	public PrimeData(long low, long high) {
		this.lower = low;
		this.upper = high;
	}
}
