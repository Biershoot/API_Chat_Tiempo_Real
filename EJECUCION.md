# 🚀 Instrucciones de Ejecución - API Chat Tiempo Real

## ✅ Estado del Proyecto

El proyecto **API Chat Tiempo Real** ha sido completamente configurado y está listo para ejecutarse. Se ha reorganizado la estructura de paquetes y se han agregado todas las configuraciones necesarias.

## 📁 Estructura Final del Proyecto

```
com.alex.chat/
├─ RealtimeChatApplication.java     # Clase principal
├─ config/                          # Configuraciones generales
├─ security/
│  └─ SecurityConfig.java          # Configuración de seguridad
├─ websocket/
│  └─ WebSocketConfig.java         # Configuración WebSocket
├─ controller/
│  └─ TestController.java          # Controlador de pruebas
├─ user/
│  ├─ entity/User.java
│  └─ repo/UserRepository.java
├─ chat/
│  ├─ entity/Chat.java
│  └─ repo/ChatRepository.java
└─ message/
    ├─ entity/Message.java
    └─ repo/MessageRepository.java
```

## 🔧 Opciones de Ejecución

### Opción 1: Script Automático (Recomendado)
```bash
# En Windows
.\start-dev.bat

# En Linux/Mac
./start-dev.sh
```

### Opción 2: Comando Manual
```bash
# Usando Maven Wrapper
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

# Usando Maven (si está instalado)
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Opción 3: IDE
1. Abrir el proyecto en IntelliJ IDEA o Eclipse
2. Ejecutar `RealtimeChatApplication.java`
3. Agregar VM Options: `-Dspring.profiles.active=dev`

## 🌐 Endpoints Disponibles

Una vez que la aplicación esté ejecutándose:

- **Health Check:** http://localhost:8080/api/test/health
- **Info:** http://localhost:8080/api/test/info
- **H2 Console:** http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: `password`

## 🔍 Solución de Problemas

### Error: "Access denied for user 'root'@'localhost'"
**Solución:** Asegúrate de usar el perfil de desarrollo:
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

### Error: "No es posible conectar con el servidor remoto"
**Solución:** 
1. Verifica que no haya otros procesos en el puerto 8080
2. Espera 15-20 segundos para que la aplicación inicie completamente
3. Usa el script `start-dev.bat` para una ejecución más estable

### Error: "Maven no se reconoce"
**Solución:** Usa el Maven Wrapper incluido:
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

## 📊 Configuraciones Implementadas

### ✅ Base de Datos
- **Desarrollo:** H2 en memoria (sin configuración adicional)
- **Producción:** MySQL con configuración completa

### ✅ Seguridad
- Spring Security configurado
- Endpoints de prueba públicos
- Soporte para H2 Console

### ✅ WebSocket
- Configuración STOMP completa
- Endpoints para mensajería en tiempo real

### ✅ Entidades JPA
- User, Chat, Message con relaciones
- Repositorios con consultas personalizadas

## 🎯 Próximos Pasos

1. **Ejecutar la aplicación** usando cualquiera de las opciones anteriores
2. **Verificar endpoints** accediendo a http://localhost:8080/api/test/health
3. **Explorar H2 Console** para ver las tablas creadas
4. **Implementar funcionalidades adicionales** según necesidades

## 📞 Soporte

Si encuentras problemas:
1. Verifica que Java 21 esté instalado
2. Usa el perfil de desarrollo (`-Dspring-boot.run.profiles=dev`)
3. Revisa los logs de la aplicación
4. Consulta el README.md principal

---

**¡El proyecto está listo para ejecutarse! 🎉**
