# Music API

This API allows users to manage users, artists, albums, and tracks with role-based access control. Below are the endpoints for each entity and their associated access control rules.

## Endpoints Overview

### User Creation
To create a new user, use the following request body:
```json
{
  "email": "mihir@example.com",
  "role": "ROLE_ADMIN",
  "password": "admin123"
}
