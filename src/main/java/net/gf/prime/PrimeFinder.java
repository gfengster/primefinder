package net.gf.prime;

import java.util.ArrayList;
import java.util.List;

import net.gf.exception.WrongRangeException;
import net.gf.model.PrimeError;

public final class PrimeFinder {
	private final long lower;
	private final long upper;
	
	public PrimeFinder(long lower, long upper) throws WrongRangeException {
		if (lower < 1 || upper < 1 || lower > upper) {
			final PrimeError error = new PrimeError(lower, upper, "The range is invalid");
			throw new WrongRangeException(error);
		}
		
		this.lower = lower;
		this.upper = upper;
	}

	public List<Long> find(){
		final List<Long> primes = new ArrayList<>();
	
		if (upper < 2)
			return primes;
		
		long guess = lower;
		
		if (lower <= 2 && upper >= 2) {
			primes.add((long)2);
			guess = 3;
		} 
		
		if (guess % 2 == 0) {
			guess++;
		}
		
		boolean isPrime;
		long guessRange;
		
		while (guess <= upper) {
			isPrime = true;

			guessRange = (long)Math.sqrt(guess);
			for (int i = 2; i <= guessRange; i++) {
				// condition for nonprime number
				if (guess%i == 0) {
					isPrime = false;
					break;
				}
			}

			if (isPrime)
				primes.add(guess);

			guess += 2;
		}

		return primes;
	}
}
