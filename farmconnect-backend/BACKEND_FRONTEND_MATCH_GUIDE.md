# FarmConnect Backend - Frontend Match Guide

This backend is aligned with the frontend repo endpoints:

Frontend base URL:
VITE_API_BASE_URL=http://localhost:8080

## Auth
- POST /api/auth/register
- POST /api/auth/login
- POST /api/auth/verify-otp?email=user@gmail.com&otp=123456
- POST /api/auth/resend-otp?email=user@gmail.com

Register returns `requiresEmailVerification=true` and sends OTP.
Verify OTP enables the user.
Login returns flat fields plus JWT:

```json
{
  "id": 1,
  "fullName": "User Name",
  "name": "User Name",
  "email": "user@gmail.com",
  "role": "FARMER",
  "token": "jwt-token",
  "accessToken": "jwt-token",
  "message": "Login successful",
  "isEmailVerified": true,
  "emailVerified": true,
  "accountStatus": "ACTIVE",
  "status": "ACTIVE",
  "isAuthorized": true,
  "requiresEmailVerification": false
}
```

## Product endpoints
- GET /api/public/products
- GET /api/public/products/{id}
- POST /api/farmer/products/{farmerId}
- GET /api/farmer/products/{farmerId}
- PUT /api/farmer/product/{id}
- DELETE /api/farmer/product/{id}

## Advice endpoints
- POST /api/farmer/advice-requests/{farmerId}
- GET /api/farmer/advice-requests/{farmerId}
- GET /api/expert/advice-requests
- GET /api/expert/advice-requests/pending
- GET /api/expert/advice-requests/{id}
- PUT /api/expert/advice-requests/{id}/reply
- DELETE /api/expert/advice-requests/{id}
- GET /api/expert/advice/expert/{expertId}

## Admin endpoints
- GET /api/admin/users
- GET /api/admin/users/{id}
- PUT /api/admin/users/{id}
- DELETE /api/admin/users/{id}
- GET /api/admin/products
- DELETE /api/admin/products/{id}
- GET /api/admin/advice-requests
- DELETE /api/admin/advice-requests/{id}

## How to run
Use Maven wrapper:

```cmd
.\mvnw.cmd clean install
.\mvnw.cmd spring-boot:run
```
