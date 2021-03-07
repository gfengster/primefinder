**A Prime number find Service**

The service is a Restful API and coded in Java with SpringBoot. Its build tool is Maven.

The project includes algorithm, integration tests. The result of the service compares to external resources https://primes.utm.edu/howmany.html and https://miniwebtool.com/list-of-prime-numbers. The CI/CD pipeline is built in GitHub. The project was developed, tested on Ubuntu 20.04.2 LTS.

The service can take the maximum upper number is long. 

1. Checkout the code

   `git clone https://github.com/gfengster/primefinder.git` 

2. Build the project

   `cd primefinder/`

   `mvn clean package`

3. Run the application

   Start up the application by running one of the following command in terminal

   3.1 `java -jar ./target/primefinder-0.0.1-SNAPSHOT.jar`

   3.2 `mvn spring-boot:run`

4. API endpoints

   4.1 Endpoint "/prime/{upper}", method is GET

   This endpoint will return a json object with prime number in range of 1 to upper number.<br>

   For example:<br>

   `http://localhost:8080/prime/10`  will return<br>

   `{"lower":1,"upper":10,"primes":[2,3,5,7]}`

   4.2 Endpoint "/prime/{lower}/{upper}", method is GET

   This endpoint will return a json object with prime number in range between lower and upper number.<br>

   For example:

   `http://localhost:8080/prime/50/97` will return<br>

   `{"lower":50,"upper":97,"primes":[53,59,61,67,71,73,79,83,89,97]}`

   4.3 Endpoint "/prime", method is POST

   The request must have a request body. The body defines lower and upper number.<br>

   For example:

   `curl -X POST -H "Content-Type: application/json" -d '{"lower":95,"upper":150}' http://localhost:8080/prime` will return<br>

   `{"lower":95,"upper":150,"primes":[97,101,103,107,109,113,127,131,137,139,149]}`

5. Exception handling

   5.1 If upper number is less than lower, an error object will be returned.
   
   `http://localhost:8080/prime/50/23` will return<br>
   `{"lower":50,"upper":23,"error":"The range is invalid"}`

   5.2 If one of number is less than 0, an error object will be returned.
   
   `http://localhost:8080/prime/-5/10` will return<br>
   `{"lower":-5,"upper":10,"error":"The range is invalid"}`

   5.3 If endpoint is invalid, an error page will be returned.
   
   `http://localhost:8080/primes/5/10` will return 404 NOT_FOUND.

