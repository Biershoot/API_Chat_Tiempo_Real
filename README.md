# API Chat Tiempo Real

A real-time chat application built with Spring Boot, featuring WebSocket communication, security, and Redis integration.

## 🚀 Features

- **Real-time Chat**: WebSocket-based messaging for instant communication
- **Security**: Spring Security integration for user authentication and authorization
- **Database**: MySQL database with JPA for data persistence
- **Caching**: Redis integration for session management and caching
- **RESTful API**: Clean REST endpoints for chat operations
- **Validation**: Input validation using Spring Validation

## 🛠️ Technology Stack

- **Backend**: Spring Boot 3.3.2
- **Java**: JDK 21
- **Database**: MySQL
- **Cache**: Redis
- **Build Tool**: Maven
- **Security**: Spring Security
- **WebSocket**: Spring WebSocket
- **ORM**: Spring Data JPA

## 📋 Prerequisites

- Java 21 or higher
- Maven 3.6+
- MySQL 8.0+
- Redis 6.0+

## 🔧 Installation

### Option 1: Development Mode (Recommended for testing)

1. **Clone the repository**
   ```bash
   git clone https://github.com/Biershoot/API_Chat_Tiempo_Real.git
   cd API_Chat_Tiempo_Real
   ```

2. **Run with H2 in-memory database (no setup required)**
   ```bash
   # Using Maven wrapper (recommended)
   ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
   
   # Or using Maven if installed
   mvn spring-boot:run -Dspring-boot.run.profiles=dev
   ```

3. **Access the application**
   - API Health Check: http://localhost:8081/api/test/health
   - H2 Database Console: http://localhost:8081/h2-console
   - JDBC URL: `jdbc:h2:mem:testdb`
   - Username: `root`
   - Password: `root`

### Option 2: Production Mode (MySQL + Redis)

1. **Prerequisites**
   - MySQL 8.0+ running on localhost:3306
   - Redis 6.0+ running on localhost:6379

2. **Configure database**
   - Create a MySQL database named `api_chat`
   - Update `application.properties` with your database credentials

3. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

## 📁 Project Structure

```
src/main/java/com/alex/chat/
├── RealtimeChatApplication.java     # Main application class
├── config/                          # Configuration classes
├── security/                        # Security configuration
├── websocket/                       # WebSocket configuration
├── user/
│   ├── entity/                      # User entity
│   └── repo/                        # User repository
├── chat/
│   ├── entity/                      # Chat entity
│   └── repo/                        # Chat repository
└── message/
    ├── entity/                      # Message entity
    └── repo/                        # Message repository
```

## 🔌 API Endpoints

### Test Endpoints
- `GET /api/test/health` - Health check endpoint
- `GET /api/test/info` - Application information

### WebSocket Endpoints
- `WS /ws` - WebSocket connection endpoint
- `STOMP /app` - Application destination prefix
- `STOMP /topic` - Topic-based messaging
- `STOMP /queue` - Queue-based messaging
- `STOMP /user` - User-specific messaging

### Database Console (Development)
- `GET /h2-console` - H2 Database Console (dev profile only)

### Access URLs
- **Health Check:** http://localhost:8081/api/test/health
- **Info:** http://localhost:8081/api/test/info
- **H2 Console:** http://localhost:8081/h2-console

### Planned Endpoints
- User management (CRUD operations)
- Chat room operations
- Message handling
- Authentication and authorization

## 🔐 Security

The application includes Spring Security for:
- User authentication
- Role-based access control
- Session management with Redis

## 🌐 WebSocket

Real-time communication is handled through WebSocket connections, allowing for:
- Instant message delivery
- Live chat rooms
- User presence indicators

## 📝 Configuration

Key configuration files:
- `application.properties`: Database, Redis, and application settings
- `pom.xml`: Maven dependencies and build configuration

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request


## 👨‍💻 Author

**Alejandro Arango Calderon** - [Biershoot](https://github.com/Biershoot)

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- The open-source community for various libraries and tools
