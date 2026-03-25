### 🔷 High-Level Flow

1. Client requests authorization from the user
2. User authenticates and grants/denies access
3. Authorization Server returns an **authorization code**
4. Client exchanges code for **access token**
5. Client uses access token to call Resource Server
6. Resource Server validates token and returns data

### 🔷 Endpoints in This Demo
Java                  | Endpoint  |                     Role                      |
----------------------|-----------|-----------------------------------------------|
|LoginServlet.java     |/login    | Starts OAuth flow (Client)                    |
|AuthorizeServlet.java |/authorize| User login + consent (Authorization Server)   |
|CallbackServlet.java  |/callback | Receives authorization code (Client)          |
|GetTokenServlet.java  |/getToken | Helper to call token endpoint                 |
|TokenServlet.java     |/token    | Issues access token                           |
