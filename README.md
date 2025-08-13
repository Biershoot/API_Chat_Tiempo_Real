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

1. **Clone the repository**
   ```bash
   git clone https://github.com/Biershoot/API_Chat_Tiempo_Real.git
   cd API_Chat_Tiempo_Real
   ```

2. **Configure the database**
   - Create a MySQL database
   - Update `application.properties` with your database credentials

3. **Configure Redis**
   - Ensure Redis is running on your system
   - Update Redis configuration in `application.properties` if needed

4. **Build the project**
   ```bash
   mvn clean install
   ```

5. **Run the application**
   ```bash
   mvn spring-boot:run
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

The application provides RESTful endpoints for:
- User management
- Chat room operations
- Message handling
- WebSocket connections for real-time communication

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
