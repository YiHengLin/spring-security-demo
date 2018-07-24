# spring-security-demo
Demo project for Spring Security using JWT

1. insert test data in data.sql, where h2 console
```console
http://localhost:8080/h2-console/
```
2. Send POST requst to authentication endpoint, /auth, you should recieve a JWT token.
```console
curl -X POST http://localhost:8080/auth -H "Content-Type:application/json" -d "{\"username\":\"user\",\"password\":\"password\"}"
```
3. Send a GET request to /user, with Authorization header, noted that _there's a space berween Bearer and JWT token_.
```console
curl -X GET http://localhost:8080/user -H "Authorization:Bearer ${JWT token}
```
4. You will get a user detail (the identity you pass authentication with), which means you pass authentication of spring security :) 


## Test user/password and authority
* admin/admin, ADMIN, enabled
* user/password, USER, enabled
* disabled/password, USER, disabled
