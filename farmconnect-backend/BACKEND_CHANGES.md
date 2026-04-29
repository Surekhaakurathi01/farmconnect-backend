# FarmConnect Backend Fixes

## What was fixed

- Made OTP endpoints compatible with the frontend:
  - `POST /api/auth/verify-otp?email=...&otp=...` returns `{ "message": "..." }`
  - `POST /api/auth/resend-otp?email=...` returns `{ "message": "..." }`
- Fixed auth response shape for the React frontend:
  - includes `id`, `fullName`, `name`, `email`, `role`, `token`, `accessToken`, `isEmailVerified`, `emailVerified`, `accountStatus`, `status`, and `isAuthorized`.
- Added `PUBLIC` role because the frontend registration/login UI includes it.
- Allowed frontend-compatible request fields:
  - Products: `cropName`, `pricePerUnit`, `notes`, `location`, `harvestDate`, etc.
  - Advice requests: `image`, `category`, `priority`, `fieldLocation`, etc.
  - Expert replies: `answer`, `reply`, `expertReply`, or `response`.
- Replaced hard-coded mail/database secrets in `application.properties` with environment variables.
- Email sending no longer breaks registration during local development if SMTP is not configured; OTP text is logged in the backend console.
- Cleaned broken admin profile placeholder methods.

## Required frontend setting

Create `.env.local` in the frontend project:

```properties
VITE_API_BASE_URL=http://localhost:8080
```

## SMTP setup for real OTP emails

Set these environment variables before starting the backend:

```bash
export FARMCONNECT_MAIL_USERNAME="your-gmail@gmail.com"
export FARMCONNECT_MAIL_PASSWORD="your-gmail-app-password"
```

For Gmail, use an App Password, not your normal Gmail password.

## Run backend

```bash
./mvnw spring-boot:run
```

If Maven wrapper is not executable:

```bash
chmod +x mvnw
./mvnw spring-boot:run
```
