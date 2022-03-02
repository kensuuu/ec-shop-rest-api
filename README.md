# ec-shop-rest-api
## Introduction
| Method | Authorization | Path             | Description                             |
|--------|-----------|------------------|-----------------------------------------|
| POST   | -         | /v1/auth/signup  | Create a new user                       |
| POST   | -         | /v1/auth/login   | Login a user                            |
| POST   | -         | /v1/auth/refresh | Create a new jwt token by refresh token |
| GET    | ✓         | /v1/users        | Get user info                           |

## Usage
### Signup
| row      | required | length |
|----------|----------|--------|
| username | ✓        | 6~20   |
| password | ✓        | 8~40   |
| email    | ✓        | ~50    |
| role     | -        | -      |
```
curl -X POST http://localhost:8080/v1/auth/signup -H 'Content-Type: application/json' -d '{
  "username": "test01", "password": "password", "email": "test01@gmail.com", "role": "user"}'
```