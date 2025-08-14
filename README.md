# 🚀 API de Chat en Tiempo Real con Spring Boot

<div align="center">

![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.2-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![WebSocket](https://img.shields.io/badge/WebSocket-Enabled-4479A1?style=for-the-badge&logo=socket.io&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-Pub/Sub-DC382D?style=for-the-badge&logo=redis&logoColor=white)
![JWT](https://img.shields.io/badge/Security-JWT-000000?style=for-the-badge&logo=json-web-tokens&logoColor=white)

<img src="https://i.imgur.com/6dBUz5C.png" alt="Chat API Logo" width="300"/>

</div>

<hr/>

## 📋 Descripción

> **Una API de chat en tiempo real altamente escalable y segura construida con Spring Boot.**  
> Diseñada para manejar comunicaciones instantáneas con soporte para múltiples instancias, autenticación segura y monitoreo avanzado.

<hr/>

## 🔍 Problemática que Resuelve

### Desafíos en Comunicaciones en Tiempo Real

Las aplicaciones modernas requieren comunicación instantánea entre usuarios, pero desarrollar sistemas de chat escalables presenta varios desafíos:

| ⚠️ Desafío | 💡 Descripción |
|------------|---------------|
| **1. Sincronización entre múltiples servidores** | En entornos distribuidos, los mensajes enviados a un servidor deben llegar a usuarios conectados a otros servidores. |
| **2. Seguridad y autenticación** | Proteger las conexiones en tiempo real y validar quién puede enviar/recibir mensajes. |
| **3. Persistencia y recuperación** | Mantener historial de conversaciones accesible incluso después de desconexiones. |
| **4. Monitoreo y optimización** | Entender patrones de uso para mejorar el rendimiento. |

### Nuestra Solución

<div align="center">

| 🧩 Componente | 📝 Descripción |
|--------------|----------------|
| **Arquitectura distribuida con Redis Pub/Sub** | Permite escalar horizontalmente manteniendo sincronización. |
| **Autenticación JWT** | Tanto para API REST como WebSockets. |
| **Persistencia con JPA** | Almacenamiento eficiente de mensajes con soporte para búsquedas. |
| **Sistema de métricas integrado** | Monitoreo en tiempo real del rendimiento y uso. |

</div>

<hr/>

## ⚙️ Características Principales

<div align="center">

| ✅ WebSockets | ✅ Redis Pub/Sub | ✅ Autenticación JWT | ✅ Persistencia JPA |
|:-------------:|:----------------:|:--------------------:|:-------------------:|
| Comunicación bidireccional en tiempo real | Sincronización entre múltiples instancias | Seguridad para API REST y WebSockets | Almacenamiento con MySQL/H2 |

| ✅ Caché Distribuida | ✅ Documentación API | ✅ Monitoreo | ✅ Escalabilidad |
|:--------------------:|:-------------------:|:------------:|:----------------:|
| Optimización con Redis | OpenAPI/Swagger | Actuator y Prometheus | Probada horizontalmente |

</div>

<hr/>

## 🛠️ Tecnologías Utilizadas

<div align="center">

![Backend](https://img.shields.io/badge/Backend-Spring_Boot_&_Java_21-6DB33F?style=flat-square&logo=spring&logoColor=white)
![Real-time](https://img.shields.io/badge/Real--time-WebSockets_&_STOMP-4479A1?style=flat-square&logo=websocket&logoColor=white)
![Mensajes](https://img.shields.io/badge/Mensajes-Redis_Pub/Sub-DC382D?style=flat-square&logo=redis&logoColor=white)
![Persistencia](https://img.shields.io/badge/Persistencia-JPA_&_MySQL/H2-4479A1?style=flat-square&logo=mysql&logoColor=white)
![Seguridad](https://img.shields.io/badge/Seguridad-Spring_Security_&_JWT-6DB33F?style=flat-square&logo=spring-security&logoColor=white)
![Documentación](https://img.shields.io/badge/Documentación-OpenAPI_3.0-85EA2D?style=flat-square&logo=swagger&logoColor=black)
![Monitoreo](https://img.shields.io/badge/Monitoreo-Actuator_&_Prometheus-E6522C?style=flat-square&logo=prometheus&logoColor=white)
![Herramientas](https://img.shields.io/badge/Herramientas-Maven-C71A36?style=flat-square&logo=apache-maven&logoColor=white)

</div>

<hr/>

## 📊 Arquitectura

<div align="center">
<pre>
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
</pre>
</div>

<hr/>

## 🚀 Implementación

### Requisitos Previos

<div align="center">

![JDK](https://img.shields.io/badge/JDK-21-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Latest-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-Local_o_Remoto-DC382D?style=for-the-badge&logo=redis&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-Opcional_(H2_para_dev)-4479A1?style=for-the-badge&logo=mysql&logoColor=white)

</div>

### Instalación y Ejecución

<details>
<summary><b>📋 Ver instrucciones detalladas</b></summary>

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/tu-usuario/api-chat-tiempo-real.git
   cd api-chat-tiempo-real
   ```

2. **Configurar properties**
   ```properties
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
</details>

### Despliegue en Producción

<div align="center">

| 🌟 Recomendación | 📝 Descripción |
|-----------------|----------------|
| ![Kubernetes](https://img.shields.io/badge/Kubernetes-Alta_Disponibilidad-326CE5?style=flat-square&logo=kubernetes&logoColor=white) | Despliegue en Kubernetes para alta disponibilidad |
| ![Redis Cluster](https://img.shields.io/badge/Redis-Cluster-DC382D?style=flat-square&logo=redis&logoColor=white) | Configuración de Redis en cluster |
| ![DB Replication](https://img.shields.io/badge/MySQL-Replicación-4479A1?style=flat-square&logo=mysql&logoColor=white) | Configuración de bases de datos con replicación |
| ![Load Balancer](https://img.shields.io/badge/Balanceador-Carga-009639?style=flat-square&logo=nginx&logoColor=white) | Implementación de balanceadores de carga |

</div>

<hr/>

## 📈 Monitoreo y Rendimiento

<div align="center">

![Métricas](https://img.shields.io/badge/Métricas-Spring_Boot_Actuator_+_Prometheus-E6522C?style=for-the-badge&logo=prometheus&logoColor=white)

</div>

La aplicación expone métricas completas a través de Spring Boot Actuator y Prometheus:

<div align="center">

| 📊 Métrica | 📝 Descripción |
|------------|----------------|
| 👥 **Usuarios conectados** | Monitoreo en tiempo real de usuarios activos |
| 📨 **Volumen de mensajes** | Cantidad de mensajes enviados por intervalo |
| ⏱️ **Latencia de procesamiento** | Tiempo que toma procesar cada mensaje |
| 🔄 **Uso de recursos** | CPU, memoria y conexiones activas |

</div>

Accesible en:
```
http://localhost:8081/actuator/metrics
http://localhost:8081/actuator/prometheus
```

> 💡 Recomendamos usar Grafana para visualizar estas métricas en paneles personalizados.

<hr/>

## 🧪 Pruebas

<div align="center">

![Unit Tests](https://img.shields.io/badge/Unit_Tests-JUnit_5-25A162?style=flat-square&logo=junit5&logoColor=white)
![Integration Tests](https://img.shields.io/badge/Integration_Tests-Spring_Test-6DB33F?style=flat-square&logo=spring&logoColor=white)

</div>

El proyecto incluye pruebas unitarias e integración:

```bash
# Ejecutar todas las pruebas
mvn test

# Ejecutar solo pruebas de integración
mvn verify
```

<hr/>

## 🔐 Seguridad

<div align="center">

| 🛡️ Característica | 📝 Detalle |
|------------------|------------|
| ![JWT](https://img.shields.io/badge/JWT-Autenticación-000000?style=flat-square&logo=json-web-tokens&logoColor=white) | Implementa autenticación y autorización con JWT |
| ![CSRF](https://img.shields.io/badge/CSRF-Protección-6DB33F?style=flat-square&logo=spring-security&logoColor=white) | Protección contra ataques CSRF |
| ![Validation](https://img.shields.io/badge/Validación-Entrada-6DB33F?style=flat-square&logo=spring-security&logoColor=white) | Validación de entrada |
| ![TLS](https://img.shields.io/badge/TLS-Comunicación_Cifrada-006600?style=flat-square&logo=let's-encrypt&logoColor=white) | Comunicación cifrada |
| ![RBAC](https://img.shields.io/badge/RBAC-Control_de_Acceso-6DB33F?style=flat-square&logo=spring-security&logoColor=white) | Control de acceso basado en roles |

</div>

<hr/>

## 👨‍💻 Autor

<div align="center">
  <img src="https://avatars.githubusercontent.com/u/1234567?v=4" width="100" style="border-radius:50%"/>
  <h3>Alex</h3>
  
  [![LinkedIn](https://img.shields.io/badge/LinkedIn-alejandroarango--dev-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/alejandroarango-dev)
  [![GitHub](https://img.shields.io/badge/GitHub-tu--usuario-181717?style=for-the-badge&logo=github&logoColor=white)](https://github.com/tu-usuario)
</div>

---

<div align="center">
  <i>¿Interesado en contratar un desarrollador con experiencia en aplicaciones en tiempo real y arquitecturas escalables?</i>
  <br/>
  <b>¡Contáctame para discutir cómo puedo aportar valor a tu equipo!</b>
</div>
