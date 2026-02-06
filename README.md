# 🎵 MusicStreaming – Microservices Project

Proyecto backend basado en arquitectura de microservicios desarrollado con Spring Boot,
que modela un sistema sencillo de music streaming con gestión de usuarios y artistas.

El objetivo del proyecto es aplicar buenas prácticas de:
- Microservicios
- Spring Boot + JPA
- Comunicación entre servicios
- Persistencia desacoplada
- Uso de Git y GitHub en un entorno real

## 🧱 Arquitectura general

El proyecto está organizado como un monorepositorio con dos microservicios independientes:

musicstreaming-microservices/
 ├─ user/       → Microservicio de usuarios
 └─ artista/    → Microservicio de artistas

Cada microservicio:
- Es una aplicación Spring Boot independiente
- Tiene su propia base de datos
- Se ejecuta en un puerto distinto
- Puede evolucionar y desplegarse de forma separada

---

## 🔹 Microservicio user

Responsable de la gestión de usuarios y su relación con artistas.

Funcionalidades:
- Crear usuarios
- Consultar usuarios
- Actualizar y borrar usuarios
- Asociar usuarios con artistas

Modelo principal:
- User
- UserArtist (entidad de relación)

La relación con artistas se gestiona mediante IDs, ya que los artistas pertenecen a otro microservicio.

Tecnologías:
- Spring Boot
- Spring Data JPA
- H2
- WebClient
- Lombok



## 🔹 Microservicio artista

Responsable de la gestión de artistas.

Funcionalidades:
- Crear artistas
- Consultar artistas
- Listar artistas

Este microservicio no conoce a los usuarios, manteniendo el desacoplamiento.

Tecnologías:
- Spring Boot
- Spring Data JPA
- H2
- Lombok



## 🔗 Comunicación entre microservicios

- Comunicación HTTP REST
- WebClient para llamadas entre servicios
- No se comparten entidades entre microservicios
- La información se obtiene dinámicamente por ID


