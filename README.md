# 🚀 API de Chat en Tiempo Real con Spring Boot

<div align="center">

![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.2-6DB33F?style=flat-square&logo=spring-boot&logoColor=white)
![Java](https://img.shields.io/badge/Java-21-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![WebSocket](https://img.shields.io/badge/WebSockets-Real--time-4479A1?style=flat-square&logo=websocket&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-Pub/Sub-DC382D?style=flat-square&logo=redis&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-Auth-000000?style=flat-square&logo=json-web-tokens&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Ready-2496ED?style=flat-square&logo=docker&logoColor=white)
![Kubernetes](https://img.shields.io/badge/K8s-Deployable-326CE5?style=flat-square&logo=kubernetes&logoColor=white)

</div>

## 📋 Descripción

Una API de chat en tiempo real **altamente escalable y segura** construida con Spring Boot. Diseñada para manejar comunicaciones instantáneas con soporte para múltiples instancias, autenticación segura y monitoreo avanzado, implementando las mejores prácticas de desarrollo Java en 2025.

## 🔍 Problemática que Resuelve

### Desafíos en Comunicaciones en Tiempo Real

Las aplicaciones modernas requieren comunicación instantánea entre usuarios, pero desarrollar sistemas de chat escalables presenta varios desafíos:

| Desafío | Descripción |
|---------|-------------|
| **📡 Sincronización entre múltiples servidores** | En entornos distribuidos, los mensajes enviados a un servidor deben llegar a usuarios conectados a otros servidores. |
| **🔒 Seguridad y autenticación** | Proteger las conexiones en tiempo real y validar quién puede enviar/recibir mensajes. |
| **💾 Persistencia y recuperación** | Mantener historial de conversaciones accesible incluso después de desconexiones. |
| **📊 Monitoreo y optimización** | Entender patrones de uso para mejorar el rendimiento. |

### Nuestra Solución

<div align="center">

| Componente | Beneficio |
|------------|-----------|
| **🔄 Arquitectura distribuida con Redis Pub/Sub** | Permite escalar horizontalmente manteniendo sincronización en tiempo real. |
| **🔐 Autenticación JWT** | Seguridad de nivel empresarial tanto para API REST como WebSockets. |
| **📁 Persistencia con JPA** | Almacenamiento eficiente de mensajes con soporte para búsquedas optimizadas. |
| **📈 Sistema de métricas integrado** | Monitoreo en tiempo real del rendimiento y uso mediante Actuator y Prometheus. |

</div>

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

<div align="center">

| Categoría | Tecnologías |
|-----------|-------------|
| **Backend** | Spring Boot 3.3.2, Java 21, Spring WebFlux |
| **Comunicación en tiempo real** | WebSockets, STOMP, Server-Sent Events |
| **Distribución de mensajes** | Redis Pub/Sub, RabbitMQ (alternativa) |
| **Persistencia** | Spring Data JPA, MySQL/H2, Hibernate |
| **Seguridad** | Spring Security 6.2, JWT, OAuth 2.1 |
| **Documentación** | OpenAPI 3.1, Swagger UI |
| **Monitoreo** | Spring Boot Actuator, Micrometer, Prometheus, Grafana |
| **Contenedorización** | Docker, Kubernetes, Helm |
| **CI/CD** | GitHub Actions, Jenkins, Sonar |

</div>

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

- JDK 21+ (Soporte para Java Virtual Threads)
- Maven 3.9+
- Redis 7.2+ (local o remoto)
- MySQL 8.0+ (opcional, H2 para desarrollo)
- Docker (recomendado para despliegue)

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

- **Kubernetes**: Despliegue con Helm charts (incluidos)
- **Monitoreo**: Grafana dashboards preconfigurados
- **Escalado**: Configuración automática horizontal
- **Alta disponibilidad**: Redis en cluster con Sentinel
- **CI/CD**: Pipelines configuradas para GitHub Actions

## 📈 Monitoreo y Rendimiento

La aplicación expone métricas avanzadas a través de Spring Boot Actuator y Prometheus:

- **Usuarios**: Conexiones activas, historial, patrones de uso
- **Mensajes**: Volumen, latencia, tasa de entrega
- **Sistema**: CPU, memoria, conexiones, caché
- **Negocios**: KPIs configurables según necesidades

Accesible en:
```
http://localhost:8081/actuator/metrics
http://localhost:8081/actuator/prometheus
```

## 🧪 Pruebas

<div align="center">

| Tipo | Herramientas | Cobertura |
|------|--------------|-----------|
| **Unitarias** | JUnit 5, Mockito | 85%+ |
| **Integración** | Testcontainers, WireMock | 75%+ |
| **Rendimiento** | JMeter, Gatling | 1000+ usuarios concurrentes |
| **Seguridad** | OWASP ZAP | Vulnerabilidades críticas: 0 |

</div>

El proyecto incluye pruebas unitarias e integración:

```bash
# Ejecutar todas las pruebas
mvn test

# Ejecutar solo pruebas de integración
mvn verify
```

## 🔐 Seguridad

- **JWT modernizado**: Tokens con rotación y revocación
- **OAuth 2.1**: Soporte para proveedores externos
- **Protección avanzada**: XSS, CSRF, inyección
- **Auditoría**: Registro detallado de accesos
- **Cifrado**: Comunicación y datos sensibles

## 👨‍💻 Autor

<div align="center">
  <h3>Alejandro Arango</h3>
  <p>Java Developer | Spring Boot Enthusiast | Learning Cloud Architecture</p>
  
  [![LinkedIn](https://img.shields.io/badge/LinkedIn-alejandroarango--dev-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/alejandroarango-dev)
  [![GitHub](https://img.shields.io/badge/GitHub-Biershoot-181717?style=for-the-badge&logo=github&logoColor=white)](https://github.com/Biershoot)
</div>

---

<div align="center">
  <h3>🔎 ¿Buscando un desarrollador Java con potencial para tu equipo en 2025?</h3>
  <p>Con un año de experiencia práctica y gran pasión por el desarrollo de aplicaciones empresariales, sistemas distribuidos y comunicación en tiempo real.</p>
  <b>Contáctame para discutir cómo puedo aportar una perspectiva fresca y nuevas ideas a tu equipo.</b>
</div>
