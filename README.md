# spring-security-demo
Demo project for Spring Security using JWT

1. insert test data in data.sql, where h2 console: http://localhost:8080/h2-console/
2. Send POST requst to authentication endpoint, /auth,
	curl -X POST http://localhost:8080/auth -H "Content-Type:application/json" -d "{\"username\":\"user\",\"password\":\"password\"}"
	, you should recieve a JWT token.
3. Send a GET request to /user, with Authorization header, noted that there's a space berween Bearer and JWT token.
	curl -X GET http://localhost:8080/user -H "Authorization:Bearer "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiZXhwIjoxNTMyNDQyOTAyLCJpYXQiOjE1MzI0MzkzMDJ9.w-HMygpC_jCPCbv2iQoVC0VnFgphXxJSoR8-AhlTC8zmYdaxfI45DlwchJ96yet-LO1XFblf0EO90Z1PaP2aTQ"
4. You will get a user detail (the identity you pass authentication with), which means you pass authentication of spring security :) 
