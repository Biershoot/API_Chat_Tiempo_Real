# 🚀 API de Chat en Tiempo Real con Spring Boot

![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.2-green.svg)
![Java](https://img.shields.io/badge/Java-21-orange.svg)
![WebSocket](https://img.shields.io/badge/WebSocket-Enabled-blue.svg)
![Redis](https://img.shields.io/badge/Redis-Pub/Sub-red.svg)
![JWT](https://img.shields.io/badge/Security-JWT-yellow.svg)

## 📋 Descripción

Una API de chat en tiempo real altamente escalable y segura construida con Spring Boot. Diseñada para manejar comunicaciones instantáneas con soporte para múltiples instancias, autenticación segura y monitoreo avanzado.

## 🔍 Problemática que Resuelve

### Desafíos en Comunicaciones en Tiempo Real

Las aplicaciones modernas requieren comunicación instantánea entre usuarios, pero desarrollar sistemas de chat escalables presenta varios desafíos:

1. **Sincronización entre múltiples servidores** - En entornos distribuidos, los mensajes enviados a un servidor deben llegar a usuarios conectados a otros servidores.

2. **Seguridad y autenticación** - Proteger las conexiones en tiempo real y validar quién puede enviar/recibir mensajes.

3. **Persistencia y recuperación** - Mantener historial de conversaciones accesible incluso después de desconexiones.

4. **Monitoreo y optimización** - Entender patrones de uso para mejorar el rendimiento.

### Nuestra Solución

Esta API resuelve estos problemas mediante:

- **Arquitectura distribuida con Redis Pub/Sub** - Permite escalar horizontalmente manteniendo sincronización.
- **Autenticación JWT** - Tanto para API REST como WebSockets.
- **Persistencia con JPA** - Almacenamiento eficiente de mensajes con soporte para búsquedas.
- **Sistema de métricas integrado** - Monitoreo en tiempo real del rendimiento y uso.

## ⚙️ Características Principales

- ✅ **WebSockets** para comunicación bidireccional en tiempo real
- ✅ **Redis Pub/Sub** para sincronización entre múltiples instancias
- ✅ **Autenticación JWT** para API REST y WebSockets
- ✅ **Persistencia de mensajes** con JPA y MySQL/H2
- ✅ **Caché distribuida** con Redis
- ✅ **Documentación API** con OpenAPI/Swagger
- ✅ **Monitoreo y métricas** con Spring Boot Actuator y Prometheus
- ✅ **Escalabilidad horizontal** probada

## 🛠️ Tecnologías Utilizadas

- **Backend**: Spring Boot, Java 21
- **Comunicación en tiempo real**: WebSockets, STOMP
- **Distribución de mensajes**: Redis Pub/Sub
- **Persistencia**: JPA, MySQL/H2
- **Seguridad**: Spring Security, JWT
- **Documentación**: OpenAPI 3.0
- **Monitoreo**: Spring Boot Actuator, Micrometer, Prometheus
- **Herramientas**: Maven

## 📊 Arquitectura

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│   Cliente    │────▶│  API Chat   │────▶│   Cliente   │
│  WebSocket   │     │  Instancia  │     │  WebSocket  │
└─────────────┘     │      1      │     └─────────────┘
                    └──────┬──────┘
                           │
                    ┌──────▼──────┐
                    │    Redis    │
                    │   Pub/Sub   │
                    └──────┬──────┘
                           │
                    ┌──────▼──────┐
┌─────────────┐     │   API Chat  │     ┌─────────────┐
│   Cliente    │────▶│  Instancia │────▶│   Cliente   │
│  WebSocket   │     │     2      │     │  WebSocket  │
└─────────────┘     └──────┬──────┘     └─────────────┘
                           │
                    ┌──────▼──────┐
                    │   MySQL/H2  │
                    │  Persistencia│
                    └─────────────┘
```

## 🚀 Implementación

### Requisitos Previos

- JDK 21
- Maven
- Redis (local o remoto)
- MySQL (opcional, H2 para desarrollo)

### Instalación y Ejecución

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/tu-usuario/api-chat-tiempo-real.git
   cd api-chat-tiempo-real
   ```

2. **Configurar properties**
   ```
   # Editar src/main/resources/application.properties con tus credenciales DB y Redis
   ```

3. **Compilar el proyecto**
   ```bash
   mvn clean package
   ```

4. **Ejecutar la aplicación**
   ```bash
   java -jar target/realtime-chat-0.0.1-SNAPSHOT.jar
   ```

5. **Iniciar múltiples instancias** (opcional, para probar escalabilidad)
   ```bash
   java -jar target/realtime-chat-0.0.1-SNAPSHOT.jar --server.port=8082
   ```

### Despliegue en Producción

Para entornos de producción, recomendamos:

- Despliegue en Kubernetes para alta disponibilidad
- Configuración de Redis en cluster
- Configuración de bases de datos con replicación
- Implementación de balanceadores de carga

## 📈 Monitoreo y Rendimiento

La aplicación expone métricas a través de Spring Boot Actuator y Prometheus:

- Usuarios conectados en tiempo real
- Volumen de mensajes
- Latencia de procesamiento
- Uso de recursos

Accesible en:
```
http://localhost:8081/actuator/metrics
http://localhost:8081/actuator/prometheus
```

Recomendamos usar Grafana para visualizar estas métricas en paneles personalizados.

## 🧪 Pruebas

El proyecto incluye pruebas unitarias e integración:

```bash
# Ejecutar todas las pruebas
mvn test

# Ejecutar solo pruebas de integración
mvn verify
```

## 🔐 Seguridad

- Implementa autenticación y autorización con JWT
- Protección contra ataques CSRF
- Validación de entrada
- Comunicación cifrada
- Control de acceso basado en roles

## 👨‍💻 Autor

Desarrollado por Alex - [LinkedIn](https://www.linkedin.com/in/alejandroarango-dev) | [GitHub](https://github.com/tu-usuario)

---

*¿Interesado en contratar un desarrollador con experiencia en aplicaciones en tiempo real y arquitecturas escalables? ¡Contáctame para discutir cómo puedo aportar valor a tu equipo!*
