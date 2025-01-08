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
```
### Artist Creation
To create a new artist, use the following request body:
```json
{
  "name": "Alan Walker",
  "grammy": true,
  "hidden": false
}
```
### Album Creation
To create a new album, use the following request body:
```json
{
  "artistId": "03599a04-3663-4106-89b9-69020bca1d85",
  "name": "PUBG Theme",
  "year": 2017,
  "hidden": false
}
```
### Track Creation
To create a new track, use the following request body:
```json
{
  "artistId": "03599a04-3663-4106-89b9-69020bca1d85",
  "albumId": "68392bab-c531-45e5-a59e-e125ef5d64a0",
  "name": "On My Way - Alan Walker, Sabrina Carpenter and Farruko",
  "duration": 193,
  "hidden": false
}
```
