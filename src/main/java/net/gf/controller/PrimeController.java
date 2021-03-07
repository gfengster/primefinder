package net.gf.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.gf.exception.WrongRangeException;
import net.gf.model.PrimeData;
import net.gf.prime.PrimeFinder;

/**
 * Endpoint services collection  
 * @author gfeng
 *
 */
@RestController
@RequestMapping("/prime")
public class PrimeController {
	private static final Logger logger = LoggerFactory.getLogger(PrimeController.class);
	
	private static final int NUMBER_CPU = Runtime.getRuntime().availableProcessors();
	private static final Executor EXECUTOR = Executors.newFixedThreadPool(NUMBER_CPU);
	
	@Value("${prime.process.count}")
	private int processCount;
	
	@GetMapping("")
	public String usage(){
		return "Usage: /prime/{lower}/{upper} or /prime/{upper}";
	}
	
	@GetMapping("/error")
	public String error(){
		return "Usage: /prime/{lower}/{upper} or /prime/{upper}";
	}
	
	@GetMapping("/{lower}/{upper}")
	public PrimeData find(@PathVariable("lower") final long lower, 
			@PathVariable(name="upper") final long upper) throws WrongRangeException {
		return getPrimeData(lower, upper);
	}
	
	@GetMapping("/{upper}")
	public PrimeData findToUpper(@PathVariable(name="upper") final long upper) throws WrongRangeException {
		return getPrimeData(1, upper);
	}
	
	@PostMapping("")
	public PrimeData find(@RequestBody PrimeData data) throws WrongRangeException {
		if (data.getLower() == 0)
			return getPrimeData(1, data.getUpper());
		else
			return getPrimeData(data.getLower(), data.getUpper());
	}
	
	private PrimeData getPrimeData(final long lower, final long upper) {
		logger.info(String.format("Find prime from %d to %d", lower, upper));
				
		final long startProcess = System.currentTimeMillis();
		
		final PrimeData data = new PrimeData(lower, upper);
		
		// Find prime in one thread or in multithreads
		if((upper - lower) < processCount) {
			data.setPrimes(getPrimeListSingleThread(lower, upper));
		} else {
			data.setPrimes(getPrimeDataMultiThreads(lower, upper));
		}
		
		final long endProcess = System.currentTimeMillis();
		
		logger.debug(String.format("Process period %d milliseconds.", (endProcess - startProcess)));
		
		return data;
	}
	
	private List<Long> getPrimeListSingleThread(final long lower, final long upper) {
		logger.debug(String.format("Single Thread find prime from %d to %d", lower, upper));
		
		final PrimeFinder finder = new PrimeFinder(lower, upper);
		final List<Long> primes = finder.find();
	
		return primes;
	}
	
	private List<Long>  getPrimeDataMultiThreads(final long lower, final long upper) {
		final List<Long> allPrimes = new ArrayList<>();
		
		// Try to make same range in each thread. 
		final int numThreads = (int)(upper -lower)/processCount + 1;
		final int actualCount = (int)(upper -lower)/numThreads;
		
		final Collection<CompletableFuture<List<Long>>> futures = new ArrayList<>(numThreads);
		
		long start = lower;
		while (start <= upper) {
			long rangeUpper = start + actualCount;
			
			rangeUpper = rangeUpper < upper ? rangeUpper : upper;
			
			final PrimeSupplier supplier = new PrimeSupplier(start, rangeUpper);
			
			final CompletableFuture<List<Long>> future
				= CompletableFuture.supplyAsync(supplier, EXECUTOR);
			
			futures.add(future);
			
			start = rangeUpper + 1;
		}
		
		futures.stream().forEach(t -> {
			try {
				allPrimes.addAll(t.get());
			} catch (InterruptedException | ExecutionException e) {
				logger.error("Fail in prime number finder", e);
			}
		});
		
		Collections.sort(allPrimes);
		
		return allPrimes;
	}
	
	/**
	 * An inner class for {@link CompletableFuture}
	 */
	private class PrimeSupplier implements Supplier<List<Long>> {
		private final long lower;
		private final long upper;
		
		private PrimeSupplier(final long lower, final long upper) {
			this.lower = lower;
			this.upper = upper;
		}
		
		@Override
		public List<Long> get() {
			return getPrimeListSingleThread(this.lower, this.upper);
		}
	}
}
