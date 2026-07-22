## Authentication & Authorization Flow

### 1. Registration Flow (`/api/auth/register`)
* **Access:** `/api/auth/**` is configured with `.permitAll()` in Spring Security.
* **Process:**
  1. Check if the `username` already exists. If yes, throw `AuthException` (handled globally by `@ControllerAdvice` to return a clean JSON response).
  2. If valid, call `createUser()` to map `RegisterDTO` to the `User` entity and save it to the DB.
  3. Call `createMember()` to save profile data into the `Member` table, establishing a 1-1 relationship with the newly created `User`.
  4. Call `assignDefaultRole()` to assign the default `ROLE_MEMBER` (Admin can upgrade roles later).
  5. Return `UserResponse` wrapped in `ResponseEntity<ApiResponse<UserResponse>>`.

### 2. Login Flow (`/api/auth/login`)
* **Process:**
  1. Pass credentials (`LoginRequest`) to `AuthenticationManager`. If invalid, it throws `BadCredentialsException`.
  2. Upon successful authentication, generate an **Access Token** and a **Refresh Token**.
  3. Store the new `RefreshToken` entity in the database.
  4. Return `LoginResponse` (containing tokens) to Next.js, which securely stores them in **HttpOnly Cookies** (or directly set via Spring Boot ResponseCookie if using a SPA client).

### 3. Logout Flow (`/api/auth/logout`)
* **Process:**
  1. Client sends a logout request with the `RefreshToken`.
  2. Backend invalidates/deletes the `RefreshToken` from the database and clears the client cookie.

### 4. Refresh Token Flow (`/api/auth/refresh`)
* **Process:**
  1. Validate the incoming `RefreshToken` (check expiration, signature, and presence in DB). If invalid, throw `AuthException`.
  2. If valid, execute **Refresh Token Rotation**: generate a new Access Token & Refresh Token, delete the old Refresh Token from DB, and insert the new one.
  3. Return the new tokens to the client.

### 5. API Request Flow (via JWT)
* **Interception:** The request enters the `FilterChain`. The `JwtFilter` intercepts it to validate the JWT token.
* **Validation:**
    * **If missing/invalid:** An `AuthenticationException` is thrown. This is caught by `ExceptionTranslationFilter` and delegated to `JwtAuthenticationEntryPoint`, which returns a `401 Unauthorized` JSON response.
    * **If valid:** User information is loaded into the `SecurityContextHolder`.
* **Routing:** Once authenticated, the request proceeds through the `FilterChain` to the `DispatcherServlet`, which directs it to the appropriate Controller.