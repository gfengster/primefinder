package net.gf.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.gf.controller.PrimeController;
import net.gf.model.PrimeData;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class RestfulPrimeApplicationTests {
	// key the range, value is expected prime number from
	// https://primes.utm.edu/howmany.html
	private static final Map<Long, Long> PRIME_TEST_DATA = new HashMap<>();
	static {
		PRIME_TEST_DATA.put(10l, 4l);
		PRIME_TEST_DATA.put(100l, 25l);
		PRIME_TEST_DATA.put(1000l, 168l);
		PRIME_TEST_DATA.put(10000l, 1229l);
		PRIME_TEST_DATA.put(100000l, 9592l);
		PRIME_TEST_DATA.put(1000000l, 78498l);
	//	PRIME_TEST_DATA.put(10000000l, 664579l);
	//	PRIME_TEST_DATA.put(100000000l, 5761455l);
	//	PRIME_TEST_DATA.put(1000000000l, 50847534l);
	//	PRIME_TEST_DATA.put(10000000000l, 455052511l);
	}
	
	
	private static final String ENDPOINT_WITH_RANGE = "http://localhost:%s/prime/%d/%d";
	
	private static final String ENDPOINT_WITH_UPPER = "http://localhost:%s/prime/%d";
		
	private static final String ERROR404 = "http://localhost:%s/trigger404error";
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private PrimeController primeController;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	void contextLoads() {
		assertThat(primeController).isNotNull();
		assertNotNull(primeController.usage());
	}

	@Test
	void testEndpointWithRange() {
		PRIME_TEST_DATA.entrySet().parallelStream().forEach(e -> {
			long lower = 1;
			long upper = e.getKey();
			long numberOfPrime = e.getValue();
			
			try {
				if (upper != 10) {
					lower = (long)upper/10;
					numberOfPrime = numberOfPrime - PRIME_TEST_DATA.get(lower);
				}
				
				PrimeData data = getResult(lower, upper);
				assertEquals(lower, data.getLower());
				assertEquals(upper, data.getUpper());
							
				assertTrue(data.getPrimes().size() == numberOfPrime,
						String.format("Actual number of prime  %d should be %d in range %d",
								data.getPrimes().size(), numberOfPrime, upper));

			} catch (JsonProcessingException e1) {
				e1.printStackTrace();
			}
		});
	}
	
	@Test
	void testEndpointWithHigh() {
		PRIME_TEST_DATA.entrySet().parallelStream().forEach(e -> {
			long upper = e.getKey();
			long numberOfPrime = e.getValue();
			
			try {
				PrimeData data = getResult(upper);
				assertEquals(1, data.getLower());
				assertEquals(upper, data.getUpper());
							
				assertTrue(data.getPrimes().size() == numberOfPrime,
						String.format("Actual number of prime  %d should be %d in range %d",
								data.getPrimes().size(), numberOfPrime, upper));

			} catch (JsonProcessingException e1) {
				e1.printStackTrace();
			}
		});
	}
	
	@Test
	void testPostEndPoint() throws URISyntaxException, JsonMappingException, JsonProcessingException {
		List<Long> expected = Arrays.stream(
				new long[] {97,101,103,107,109,113,127,131,137,139,149}).boxed().collect(Collectors.toList());

		String url = "http://localhost:" + port + "/prime";
		
		PrimeData data = new PrimeData(95, 150);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		HttpEntity<PrimeData> request = new HttpEntity<>(data, headers);
		
		ResponseEntity<String> response = restTemplate.postForEntity(url, request,  String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);

		ObjectMapper objectMapper = new ObjectMapper();
		data = objectMapper.readValue(response.getBody(), PrimeData.class);
		List<Long> primes = data.getPrimes();
		
		assertTrue(expected.size() == primes.size() && expected.containsAll(primes) && primes.containsAll(expected), 
				String.format("Actual %s is not equals expected %s", primes, expected));
		
	}
	
	@Test
	void test404ErrorPage() {
		String url = String.format(ERROR404, port);
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

		assertTrue(response.getBody().contains("404 NOT_FOUND"));
	}
	
	@Test
	void testNagitiveRange() {
		String url = String.format(ENDPOINT_WITH_RANGE, port, -3, 21);
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
		
		assertTrue(response.getBody().contains("The range is invalid"));
	}
	
	@Test
	void testInvalidRange() {
		String url = String.format(ENDPOINT_WITH_RANGE, port, 38, 22);
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
		
		assertTrue(response.getBody().contains("The range is invalid"));
	}
	
	@Test
	void testOutOfLongRange() {
		String url = "http://localhost:" + port 
				+ "/prime/10000000000000000000000";

		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}

	private PrimeData getResult(long lower, long upper) throws JsonMappingException, JsonProcessingException {
		String url = String.format(ENDPOINT_WITH_RANGE, port, lower, upper);
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(response.getBody(), PrimeData.class);
	}
	
	private PrimeData getResult(long upper) throws JsonMappingException, JsonProcessingException {
		String url = String.format(ENDPOINT_WITH_UPPER, port, upper);
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(response.getBody(), PrimeData.class);
	}
}
