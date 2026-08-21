# 03filters

This project demonstrates a custom servlet filter in Spring Boot.

`CustomRequestFilter` extends `OncePerRequestFilter`, so it runs once for every request. It creates a request ID, adds request attributes and response headers, and logs before and after the controller executes.

## Run

```powershell
cd .\03filters\
mvn spring-boot:run
```

Open <http://localhost:8080/api/hello>. The JSON response contains the request ID and filter message. The response headers also contain `X-Request-Id` and `X-Custom-Filter`.

Run the tests with `mvn test`.
