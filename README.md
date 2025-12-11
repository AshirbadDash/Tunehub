# Project Tunehub

Tunehub is a full-stack music streaming web application developed using Java, Spring Boot, Thymeleaf, and MySQL. It provides a secure platform for users to create, update, and manage playlists of songs with role-based access control.

## Features

### User Management
- **Secure User Registration** with password confirmation
- **Authentication & Authorization** with role-based access (Admin, Customer, Artist, Moderator)
- **Session Management** with secure cookie-only tracking
- **User Profile Management** with phone number and email verification support
- **Account Types**: Basic, Premium, Family, and Student plans

### Security Features
- Session fixation protection
- HttpOnly and SameSite cookie configuration
- Password encryption with BCrypt
- Generic error messages to prevent user enumeration
- Global exception handling

### Music & Playlist Management
- Create, Update, and Delete Playlists
- Browse and Display Songs
- Role-based content management

### Administration
- Admin dashboard for user management
- Music content management
- System monitoring via Spring Actuator

## Technologies Used

### Backend
- Java 21
- Spring Boot 3.5.8
- Spring Data JPA
- Spring Boot Validation
- Maven
- MySQL Database

### Frontend
- Thymeleaf Template Engine
- Tailwind CSS 4.1.17
- DaisyUI 5.5.5
- GSAP Animation Library

### Security & Infrastructure
- BCrypt Password Hashing
- Session-based Authentication
- Logback Logging Framework
- Razorpay Payment Integration

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- **Java JDK 21** or higher
- **Maven 3.6+**
- **MySQL 8.0+**
- **Node.js 24.11.0** (for frontend build)
- **npm 11.6.1+**
- IntelliJ IDEA or any preferred IDE

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/AshirbadDash/Tunehub.git
   cd Tunehub
   ```

2. **Configure Database**
   - Create a MySQL database:
     ```sql
     CREATE DATABASE tunehub_app;
     ```
   - Update database credentials in `src/main/resources/application-dev.properties`:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/tunehub_app?createDatabaseIfNotExist=true
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     ```

3. **Configure Environment Variables** (Optional)
   ```bash
   export RAZORPAY_KEY=your_razorpay_key
   export RAZORPAY_SECRET=your_razorpay_secret
   ```

4. **Build and Run**
   ```bash
   # Clean and install dependencies
   mvn clean install
   
   # Run the application
   mvn spring-boot:run
   ```

5. **Access the Application**
   - Open your browser and navigate to: `http://localhost:8080`
   - Login page: `http://localhost:8080/login`
   - Register page: `http://localhost:8080/register`

### Configuration

The application uses profile-based configuration:
- **Dev Profile**: `application-dev.properties` (default)
- **Prod Profile**: `application-prod.properties`

#### Key Configuration Properties

```properties
# Session Security
server.servlet.session.tracking-modes=cookie
server.servlet.session.cookie.http-only=true
server.servlet.session.timeout=30m

# Security Settings
app.security.bcrypt-strength=12
app.security.max-login-attempts=5

# File Upload
spring.servlet.multipart.max-file-size=15MB
app.upload.avatar-path=uploads/avatars/
app.upload.music-path=uploads/music/
```

## Usage

### For Users
1. **Registration**: Navigate to `/register` to create a new account
   - Provide username, email, password (with confirmation), and optional phone number
   - Password must contain uppercase, lowercase, digit, and special character

2. **Login**: Access `/login` with your credentials
   - Sessions are secure with HttpOnly cookies
   - No JSESSIONID exposed in URLs

3. **Dashboard**: After login, access your personalized dashboard
   - View and manage playlists
   - Browse available songs
   - Update profile information

### For Administrators
1. **Admin Dashboard**: Access `/admin/dashboard` (requires ADMIN role)
2. **User Management**: Manage users at `/admin/users`
3. **Music Management**: Manage content at `/admin/music`

### User Roles
- **CUSTOMER**: Standard user with basic access
- **ADMIN**: Full system access with administrative privileges
- **ARTIST**: Music creator with content management access
- **MODERATOR**: Content moderation access

### Account Types
- **Basic**: Free account with limited features
- **Premium**: $9.99/month - Full features
- **Family**: $14.99/month - Up to 6 members
- **Student**: $4.99/month - Discounted plan

## Project Structure

```
Tunehub/
├── src/
│   ├── main/
│   │   ├── java/com/tunehub/
│   │   │   ├── common/          # Utility classes (SessionHelper, DateTime utils)
│   │   │   ├── config/          # Configuration classes
│   │   │   ├── controller/      # MVC Controllers
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   ├── exception/       # Custom exceptions & global handler
│   │   │   ├── interceptor/     # Authentication interceptor
│   │   │   ├── model/           # Entity classes & Enums
│   │   │   ├── repository/      # JPA Repositories
│   │   │   ├── service/         # Service interfaces
│   │   │   └── serviceimpl/     # Service implementations
│   │   ├── resources/
│   │   │   ├── application.properties
│   │   │   ├── application-dev.properties
│   │   │   ├── application-prod.properties
│   │   │   ├── logback-spring.xml
│   │   │   ├── static/          # CSS, JS, Images
│   │   │   └── templates/       # Thymeleaf templates
│   │   └── frontend/            # Frontend build files
│   └── test/                    # Test classes
├── logs/                        # Application logs
├── pom.xml                      # Maven configuration
└── README.md
```

## Contributing

Please read [CONTRIBUTING.md](https://github.com/AshirbadDash/Tunehub/CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

### Development Guidelines
- Follow Java coding conventions
- Write unit tests for new features
- Update documentation for API changes
- Use meaningful commit messages
- Create feature branches for new work

## Authors

- **Ashirbad Dash** - *Initial work* - [AshirbadDash](https://github.com/AshirbadDash)

## License

This project is licensed under the MIT License - see the [LICENSE.md](https://github.com/AshirbadDash/Tunehub/LICENSE.md) file for details

## Acknowledgments

- Spring Boot Team for the excellent framework
- Tailwind CSS and DaisyUI for beautiful UI components
- MySQL for reliable database management
- All contributors who help improve this project

## Support

For issues, questions, or contributions:
- **Issues**: [GitHub Issues](https://github.com/AshirbadDash/Tunehub/issues)
- **Discussions**: [GitHub Discussions](https://github.com/AshirbadDash/Tunehub/discussions)



