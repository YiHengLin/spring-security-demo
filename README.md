# spring-security-demo
Demo project for Spring Security using JWT

1. insert test data in data.sql, where h2 console: 
	http://localhost:8080/h2-console/

2. Send POST requst to authentication endpoint, /auth, you should recieve a JWT token.
	curl -X POST http://localhost:8080/auth -H "Content-Type:application/json" -d "{\"username\":\"user\",\"password\":\"password\"}"
	
3. Send a GET request to /user, with Authorization header, noted that there's a space berween Bearer and JWT token.
	curl -X GET http://localhost:8080/user -H "Authorization:Bearer ${JWT token}
	
4. You will get a user detail (the identity you pass authentication with), which means you pass authentication of spring security :) 
