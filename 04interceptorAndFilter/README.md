# 04interceptorAndFilter

This project demonstrates three custom servlet filters and two custom Spring MVC interceptors in one request flow.

## Execution order

1. `LoggingFilter` logs the request and response.
2. `RequestIdFilter` generates a request ID.
3. `TimingFilter` measures request processing time.
4. `FirstInterceptor` runs before the controller.
5. `SecondInterceptor` runs after the first interceptor.
6. `DemoController` creates the response.

Filters run before Spring MVC selects a controller. Interceptors run inside Spring MVC, after the filters but before the controller method.

## Run

```powershell
cd .\04interceptorAndFilter\
mvn spring-boot:run
```

Open <http://localhost:8080/api/hello>. The JSON response displays the execution order, while the response headers show the values added by the filters and interceptors.

Run the tests with `mvn test`.
