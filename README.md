## Authentication & Authorization Flow

### 1. Authentication Request Flow (Login)
* **Entry Point:** Requests to `/api/auth/**` are permitted by security configuration (`permitAll()`) and reach the `DispatcherServlet`.
* **Validation:** Inside the service layer, `AuthenticationManager` verifies user credentials.
* **Result:**
    * **If valid:** Returns an authenticated object (containing `UserDetails`).
    * **If invalid:** Throws `BadCredentialsException`. Since this occurs at the Controller/Service layer, `RestControllerAdvice` catches it and returns a consistent JSON error response to the client.

### 2. API Request Flow (via JWT)
* **Interception:** The request enters the `FilterChain`. The `JwtFilter` intercepts it to validate the JWT token.
* **Validation:**
    * **If missing/invalid:** An `AuthenticationException` is thrown. This is caught by `ExceptionTranslationFilter` and delegated to `JwtAuthenticationEntryPoint`, which returns a `401 Unauthorized` JSON response.
    * **If valid:** User information is loaded into the `SecurityContextHolder`.
* **Routing:** Once authenticated, the request proceeds through the `FilterChain` to the `DispatcherServlet`, which directs it to the appropriate Controller.