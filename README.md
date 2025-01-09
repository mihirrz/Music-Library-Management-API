# Music Library Management API

This Music Library Management API allows users within an organization to manage their collection of Artists, Tracks, and Albums. Each organization has a single Admin who oversees the system and its users. The API also provides functionality for users to mark their favorite Artists, Albums, and Tracks for quick access and personalization.

## Key Features:
- **One Organization, One Admin**: Each organization has a single Admin with full control over the system. The Admin manages the users and controls their access to different parts of the music library.
- **Role-Based Access Control (RBAC)**: Users have distinct roles (Admin, Editor, Viewer) with permissions tailored to their responsibilities. The Admin has full control, Editors can modify data, and Viewers can only access and view the content.
- **Entity Relationships**: 
  - **Artists**: Artists can have multiple Albums and Tracks associated with them.
  - **Albums**: Albums belong to a specific Artist and contain multiple Tracks.
  - **Tracks**: Tracks belong to both an Artist and an Album, and are used to play music.
  - **Favorites**: Users can personalize their experience by marking their favorite Artists, Albums, and Tracks for easy retrieval and quick access.
- **Entity Management**:
  - **Users:** Admins can manage users by adding, deleting, and updating their roles (except for other Admins).
  - **Artists, Albums, Tracks:** Full CRUD operations based on role permissions.
  - **Favorites:** Users can add or remove their favorite Artists, Albums, and Tracks.

## Technologies Used

- **Spring Boot**: Framework for building the backend
- **H2 Database**: In-memory database for development and testing
- **Spring Security**: For authentication and authorization
- **Maven**: For dependency management and building the project
- **JWT (JSON Web Token)**: Token-based authentication
- **Jakarta Validation API**: For custom data validation

## Prerequisites
### Ensure you have the following installed on your system:

- **Java Development Kit (JDK)**: Version 21 or higher
- **Apache Maven** : For building the project

## Installation and Setup
Follow these steps to clone and run the project:
1. Clone the Repository
```bash
  git clone https://github.com/mihirrz/music-library-management-API.git
  cd music-library-management-API
```
2. Build the Project
- Use Maven to build the project and download dependencies:
```bash
  mvn clean install
```
3. Run the Application
- Start the Spring Boot application:
```bash
  mvn spring-boot:run
```
The application will be accessible at http://localhost:8080 by default.

## Endpoints Overview

### User Creation or Signup
- To create a new user or signup, use the following request body:
- **URL**: `POST /api/v1/signup`
```json
{
  "email": "mihir@example.com",
  "role": "ROLE_ADMIN",
  "password": "admin123"
}
```
### User Login
- To create a new user or signup, use the following request body:
- **URL**: `POST /api/v1/login`
```json
{
  "email": "mihir@example.com",
  "password": "admin123"
}
```

### Fetch all Users
- To fetch all users:
- **URL**: `GET /api/v1/users`

### Artist Creation
- To create a new artist, use the following request body:
- **URL**: `POST /api/v1/artists/add-artist`
```json
{
  "name": "Alan Walker",
  "grammy": true,
  "hidden": false
}
```

### Fetch all Artists
- To fetch all artists:
- **URL**: `GET /api/v1/artists`
  
### Album Creation
- To create a new album, use the following request body:
- **URL**: `POST /api/v1/albums/add-albums`
```json
{
  "artistId": "03599a04-3663-4106-89b9-69020bca1d85",
  "name": "PUBG Theme",
  "year": 2017,
  "hidden": false
}
```
### Fetch all Albums
- To fetch all albums:
- **URL**: `GET /api/v1/albums`

### Track Creation
- To create a new track, use the following request body:
- **URL**: `POST /api/v1/tracks/add-track`
```json
{
  "artistId": "03599a04-3663-4106-89b9-69020bca1d85",
  "albumId": "68392bab-c531-45e5-a59e-e125ef5d64a0",
  "name": "On My Way - Alan Walker, Sabrina Carpenter and Farruko",
  "duration": 193,
  "hidden": false
}
```
### Fetch all Tracks
- To fetch all tracks:
- **URL**: `GET /api/v1/tracks`

## Contribution

Feel free to fork this repository and submit pull requests for any enhancements or bug fixes. For major changes, please open an issue first to discuss what you would like to change.


---

### Author

Developed by **Mihir Raj**. For any queries or support, reach out at [mihirraj52@gmail.com](mailto:mihirraj52@gmail.com).
