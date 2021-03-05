package net.gf.test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import net.gf.exception.WrongRangeException;
import net.gf.prime.PrimeFinder;

/**
 * The test compares the data from external resources 
 * https://primes.utm.edu/howmany.html and https://miniwebtool.com/list-of-prime-numbers
 * @author gfeng
 *
 */
class PrimeFinderTest {

	@Test
	void testFrom1To10() {
		List<Long> expected = Arrays.stream(new long[] { 2, 3, 5, 7 }).boxed().collect(Collectors.toList());

		PrimeFinder finder = new PrimeFinder(1, 10);
		List<Long> primes = finder.find();

		assertTrue(expected.size() == primes.size() && expected.containsAll(primes) && primes.containsAll(expected),
				String.format("Actual %s is not equals expected %s", primes, expected));
	}

	@Test
	void testFrom1To1() {
		List<Long> expected = Arrays.stream(new long[] {}).boxed().collect(Collectors.toList());

		PrimeFinder finder = new PrimeFinder(1, 1);
		List<Long> primes = finder.find();

		assertTrue(expected.size() == primes.size() && expected.containsAll(primes) && primes.containsAll(expected),
				String.format("Actual %s is not equals expected %s", primes, expected));
	}

	@Test
	void testOneElement() {
		List<Long> expected = Arrays.stream(new long[] { 31 }).boxed().collect(Collectors.toList());

		PrimeFinder finder = new PrimeFinder(31, 31);
		List<Long> primes = finder.find();

		assertTrue(expected.size() == primes.size() && expected.containsAll(primes) && primes.containsAll(expected),
				String.format("Actual %s is not equals expected %s", primes, expected));
	}

	@Test
	void testNumber993169() {
		List<Long> expected = Arrays.stream(new long[] { 993169 }).boxed().collect(Collectors.toList());

		PrimeFinder finder = new PrimeFinder(993169, 993169);
		List<Long> primes = finder.find();

		assertTrue(expected.size() == primes.size() && expected.containsAll(primes) && primes.containsAll(expected),
				String.format("Actual %s is not equals expected %s", primes, expected));
	}

	// https://miniwebtool.com/list-of-prime-numbers/?to=100
	@Test
	void testFrom1To100() {
		List<Long> expected = Arrays.stream(new long[] { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59,
				61, 67, 71, 73, 79, 83, 89, 97 }).boxed().collect(Collectors.toList());

		PrimeFinder finder = new PrimeFinder(1, 100);
		List<Long> primes = finder.find();

		assertTrue(expected.size() == primes.size() && expected.containsAll(primes) && primes.containsAll(expected),
				String.format("Actual %s is not equals expected %s", primes, expected));
	}

	@Test
	void testFrom11To99() {
		List<Long> expected = Arrays.stream(
				new long[] { 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97 })
				.boxed().collect(Collectors.toList());

		PrimeFinder finder = new PrimeFinder(11, 99);
		List<Long> primes = finder.find();

		assertTrue(expected.size() == primes.size() && expected.containsAll(primes) && primes.containsAll(expected),
				String.format("Actual %s is not equals expected %s", primes, expected));
	}

	// https://miniwebtool.com/list-of-prime-numbers/?to=1000
	@Test
	void testFromTo1000() {
		List<Long> expected = Arrays
				.stream(new long[] { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79,
						83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181,
						191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283,
						293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409,
						419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523,
						541, 547, 557, 563, 569, 571, 577, 587, 593, 599, 601, 607, 613, 617, 619, 631, 641, 643, 647,
						653, 659, 661, 673, 677, 683, 691, 701, 709, 719, 727, 733, 739, 743, 751, 757, 761, 769, 773,
						787, 797, 809, 811, 821, 823, 827, 829, 839, 853, 857, 859, 863, 877, 881, 883, 887, 907, 911,
						919, 929, 937, 941, 947, 953, 967, 971, 977, 983, 991, 997 })
				.boxed().collect(Collectors.toList());

		PrimeFinder finder = new PrimeFinder(1, 1000);
		List<Long> primes = finder.find();

		assertTrue(expected.size() == primes.size() && expected.containsAll(primes) && primes.containsAll(expected),
				String.format("Actual %s is not equals expected %s", primes, expected));
	}

	// https://miniwebtool.com/list-of-prime-numbers/?to=10000
	@Test
	void testNumberOfPrimeTo10000() {
		PrimeFinder finder = new PrimeFinder(1, 10000);
		List<Long> primes = finder.find();

		assertTrue(primes.size() == 1229,
				String.format("Actual number of prime  %s is not equals expected %s", primes.size(), 1229));
	}

	// https://miniwebtool.com/list-of-prime-numbers/?to=5000
	@Test
	void testNumberOfPrimeTo5000() {
		PrimeFinder finder = new PrimeFinder(1, 5000);
		List<Long> primes = finder.find();

		assertTrue(primes.size() == 669,
				String.format("Actual number of prime  %s is not equals expected %s", primes.size(), 669));

		assertTrue(primes.contains(new Long(4943)), "4943 is should be in the prime list");
	}

	// https://primes.utm.edu/howmany.html
	@Test
	void testNumberOfPrime() {
		// key the range, value is expected prime number from
		// https://primes.utm.edu/howmany.html
		final Map<Long, Long> primeNumberMap = new HashMap<>();
		primeNumberMap.put(10l, 4l);
		primeNumberMap.put(100l, 25l);
		primeNumberMap.put(1000l, 168l);
		primeNumberMap.put(10000l, 1229l);
		primeNumberMap.put(100000l, 9592l);
		primeNumberMap.put(1000000l, 78498l);
//		primeNumberMap.put(10000000l, 664579l);
//		primeNumberMap.put(100000000l, 5761455l);

		primeNumberMap.entrySet().parallelStream().forEach(e -> {
			long range = e.getKey();
			long numberOfPrime = e.getValue();

			PrimeFinder finder = new PrimeFinder(1, range);
			List<Long> primes = finder.find();

			assertTrue(primes.size() == numberOfPrime,
					String.format("Actual number of prime  %d should be %d in range %d",
							primes.size(), numberOfPrime, range));
		});
	}
	
	@Test
	void testWrongRange () {
		Exception exception = assertThrows(WrongRangeException.class, () -> {
			new PrimeFinder(10, 1);
	    });
		
		assertTrue(exception.getMessage().contains("The range is invalid"));

		exception = assertThrows(WrongRangeException.class, () -> {
			new PrimeFinder(-10, 10);
	    });
		
		assertTrue(exception.getMessage().contains("The range is invalid"));
	}
	
	@Test
	void testCountOnly() {
		final Map<Long, Long> primeNumberMap = new HashMap<>();
		primeNumberMap.put(10l, 4l);
		primeNumberMap.put(100l, 25l);
		primeNumberMap.put(1000l, 168l);
		primeNumberMap.put(10000l, 1229l);
		primeNumberMap.put(100000l, 9592l);
		primeNumberMap.put(1000000l, 78498l);
		primeNumberMap.put(10000000l, 664579l);
		primeNumberMap.put(100000000l, 5761455l);
		primeNumberMap.put(1000000000l, 50847534l);
		primeNumberMap.put(10000000000l, 455052511l);
		primeNumberMap.put(100000000000l, 4118054813l);
		primeNumberMap.put(1000000000000l, 37607912018l);
		primeNumberMap.put(10000000000000l, 346065536839l);
		primeNumberMap.put(100000000000000l, 3204941750802l);
		primeNumberMap.put(1000000000000000l, 29844570422669l);
		primeNumberMap.put(10000000000000000l, 279238341033925l);
		primeNumberMap.put(100000000000000000l, 2623557157654233l);
		primeNumberMap.put(1000000000000000000l, 24739954287740860l);
//		primeNumberMap.put(10000000000000000000l, 234057667276344607l);
//		primeNumberMap.put(100000000000000000000l, 2220819602560918840l);
//		primeNumberMap.put(1000000000000000000000l, 21127269486018731928l);
//		primeNumberMap.put(10000000000000000000000l, 201467286689315906290l);
//		primeNumberMap.put(100000000000000000000000l, 1925320391606803968923l);
//		primeNumberMap.put(1000000000000000000000000l, 18435599767349200867866l);
//		primeNumberMap.put(10000000000000000000000000l, 176846309399143769411680l);

		primeNumberMap.entrySet().stream().forEach(e -> {
			long lower = 1;
			long upper = e.getKey();
			long numberOfPrime = e.getValue();
			
			if (upper != 10) {
				lower = (long)upper/10;
				numberOfPrime = numberOfPrime - primeNumberMap.get(lower);
			}
			
			System.out.print(upper + " ");
			long start = System.currentTimeMillis();
			
			PrimeFinder finder = new PrimeFinder(lower, upper);
			long primes = finder.count();
			System.out.print(upper + " " + numberOfPrime + " " + primes);
			System.out.println(" " + (System.currentTimeMillis() - start)/1000);
			assertTrue(primes == numberOfPrime,
					String.format("Actual number of prime  %d should be %d in range %d",
							primes, numberOfPrime, upper));
		});
	}
}
