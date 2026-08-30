package com.example.exceptionhandling.controller;

import com.example.exceptionhandling.dto.ApiErrorResponse;
import com.example.exceptionhandling.dto.CreateUserRequest;
import com.example.exceptionhandling.dto.UserResponse;
import com.example.exceptionhandling.exception.AnnotatedPaymentException;
import com.example.exceptionhandling.exception.ControllerSpecificException;
import com.example.exceptionhandling.exception.InvalidOperationException;
import com.example.exceptionhandling.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserResponse created = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/test/invalid-operation")
    public ResponseEntity<Void> triggerInvalidOperation() {
        throw new InvalidOperationException("This operation is not permitted for the current account state");
    }

    @GetMapping("/test/type-mismatch")
    public ResponseEntity<String> triggerTypeMismatch(@RequestParam("id") Long id) {
        return ResponseEntity.ok("Received valid numeric ID: " + id);
    }

    @GetMapping("/test/server-error")
    public ResponseEntity<Void> triggerServerError() {
        throw new RuntimeException("Unexpected internal calculation failure (simulated database crash)");
    }

    @GetMapping("/test/response-status")
    public ResponseEntity<Void> triggerResponseStatusException() {
        throw new AnnotatedPaymentException("Premium membership required for this resource");
    }

    @GetMapping("/test/controller-handled")
    public ResponseEntity<Void> triggerControllerHandledException() {
        throw new ControllerSpecificException("This exception is handled locally inside UserController");
    }

    /**
     * Controller-level Exception Handler.
     * Takes precedence over @RestControllerAdvice for this specific controller.
     */
    @ExceptionHandler(ControllerSpecificException.class)
    public ResponseEntity<ApiErrorResponse> handleControllerSpecificException(
            ControllerSpecificException ex, HttpServletRequest request) {
        ApiErrorResponse response = new ApiErrorResponse(
                HttpStatus.I_AM_A_TEAPOT.value(),
                "Handled By Controller ExceptionHandler",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(response);
    }
}
