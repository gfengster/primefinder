package net.gf.prime;

import java.util.ArrayList;
import java.util.List;

import net.gf.exception.WrongRangeException;
import net.gf.model.PrimeError;

/**
 * This class is the algorithm of computing prime number.
 * The algorithm is testing whether the number is a multiple of any 
 * integer between 2 and number's square root.
 * @author gfeng
 *
 */
public final class PrimeFinder {
	private static boolean isPrime(long guess) {
		if (guess <= 1 || guess == 4 || guess%2 == 0) 
            return false; 
        
		if (guess <= 3) 
            return true; 
		
		boolean isPrime = true;

		long guessRange = (long)Math.sqrt(guess);
		for (long i = 3; i <= guessRange; i+=2) {
			// condition for nonprime number
			if (guess%i == 0) {
				isPrime = false;
				break;
			}
		}

		return isPrime;
	}
	
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

		while (guess <= upper) {
			if (isPrime(guess))
				primes.add(guess);

			guess += 2;
		}

		return primes;
	}
}