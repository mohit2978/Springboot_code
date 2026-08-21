# 02interceptors

This project demonstrates a custom Spring MVC interceptor.

`CustomRequestInterceptor` runs before controller methods mapped under `/api/**`. It logs the request, adds a request attribute, and adds the `X-Custom-Interceptor` response header. Requests under `/public/**` are not intercepted.

## Run

```powershell
cd .\02interceptors\
mvn spring-boot:run
```

Try these endpoints:

- <http://localhost:8080/api/hello> — interceptor runs
- <http://localhost:8080/public/hello> — interceptor does not run

Run the tests with `mvn test`.
