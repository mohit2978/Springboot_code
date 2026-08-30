package com.example.exceptionhandling;

import com.example.exceptionhandling.dto.CreateUserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllUsers_shouldReturnOkAndUserList() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Alice Smith")));
    }

    @Test
    void getUserById_whenFound_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Alice Smith")));
    }

    @Test
    void getUserById_whenNotFound_shouldReturn404AndApiErrorResponse() throws Exception {
        mockMvc.perform(get("/api/users/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.error", is("Not Found")))
                .andExpect(jsonPath("$.message", containsString("User with ID 999 does not exist")))
                .andExpect(jsonPath("$.path", is("/api/users/999")))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }

    @Test
    void createUser_whenValid_shouldReturnCreated() throws Exception {
        CreateUserRequest request = new CreateUserRequest("Charlie Brown", "charlie@example.com", 25);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Charlie Brown")))
                .andExpect(jsonPath("$.email", is("charlie@example.com")))
                .andExpect(jsonPath("$.age", is(25)));
    }

    @Test
    void createUser_whenValidationFails_shouldReturn400WithFieldErrors() throws Exception {
        CreateUserRequest invalidRequest = new CreateUserRequest("", "not-an-email", 12);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.error", is("Bad Request")))
                .andExpect(jsonPath("$.message", is("Validation failed for one or more fields")))
                .andExpect(jsonPath("$.path", is("/api/users")))
                .andExpect(jsonPath("$.validationErrors", notNullValue()))
                .andExpect(jsonPath("$.validationErrors.length()", greaterThanOrEqualTo(3)));
    }

    @Test
    void createUser_whenDuplicateEmail_shouldReturn409Conflict() throws Exception {
        CreateUserRequest duplicateRequest = new CreateUserRequest("Alice Duplicate", "alice@example.com", 30);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(duplicateRequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status", is(409)))
                .andExpect(jsonPath("$.error", is("Conflict")))
                .andExpect(jsonPath("$.message", containsString("already exists")));
    }

    @Test
    void testTypeMismatch_whenInvalidQueryParam_shouldReturn400() throws Exception {
        mockMvc.perform(get("/api/users/test/type-mismatch").param("id", "invalid-number"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.error", is("Bad Request")))
                .andExpect(jsonPath("$.message", containsString("Parameter 'id' should be of type 'Long'")));
    }

    @Test
    void testInvalidOperation_shouldReturn400() throws Exception {
        mockMvc.perform(get("/api/users/test/invalid-operation"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.message", containsString("This operation is not permitted")));
    }

    @Test
    void testServerError_shouldReturn500() throws Exception {
        mockMvc.perform(get("/api/users/test/server-error"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status", is(500)))
                .andExpect(jsonPath("$.error", is("Internal Server Error")))
                .andExpect(jsonPath("$.message", containsString("unexpected internal error occurred")));
    }

    @Test
    void testMalformedJson_shouldReturn400() throws Exception {
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{invalid-json-body}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.message", is("Malformed JSON request body")));
    }

    @Test
    void testMethodNotSupported_shouldReturn405() throws Exception {
        mockMvc.perform(patch("/api/users/test/server-error"))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("$.status", is(405)))
                .andExpect(jsonPath("$.error", is("Method Not Allowed")));
    }

    @Test
    void testControllerLevelExceptionHandler_shouldTakePrecedence() throws Exception {
        mockMvc.perform(get("/api/users/test/controller-handled"))
                .andExpect(status().isIAmATeapot())
                .andExpect(jsonPath("$.status", is(418)))
                .andExpect(jsonPath("$.error", is("Handled By Controller ExceptionHandler")))
                .andExpect(jsonPath("$.message", containsString("This exception is handled locally inside UserController")));
    }

    @Test
    void testResponseStatusAnnotation_shouldReturn402() throws Exception {
        mockMvc.perform(get("/api/users/test/response-status"))
                .andExpect(status().isPaymentRequired())
                .andExpect(jsonPath("$.status", is(402)))
                .andExpect(jsonPath("$.message", containsString("Payment is required to access this resource")));
    }
}
