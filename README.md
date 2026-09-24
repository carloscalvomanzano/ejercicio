Markdown
# 📘 Ejercicio – API de Precios (Arquitectura Hexagonal)
 
Este proyecto implementa una API REST en Spring Boot 4.1.x para calcular el precio aplicable a un producto en una fecha concreta.
 
La solución está diseñada siguiendo arquitectura hexagonal, separando claramente dominio, aplicación e infraestructura.
 
---
 
# 🧩 Arquitectura Hexagonal
 
La arquitectura hexagonal organiza el sistema en tres capas principales:
 
- Dominio
- Aplicación
- Infraestructura
 
Conectadas mediante puertos y adaptadores.
 
A continuación se muestra el diagrama técnico generado, con las clases reales del proyecto y sus dependencias.
 
## Dependencias entre capas
 
```text
┌─────────────────────────────────────────────┐
│                  application                │
│                                             │
│               ┌───────────────┐             │
│               │ PricingService│             │
│               └───────┬───────┘             │
└───────────────────────│─────────────────────┘
                        │ usa
                        ▼
┌─────────────────────────────────────────────┐
│                   domain.port               │
│                                             │
│           ┌───────────────────────┐         │
│           │ PriceRepositoryPort   │         │
│           └───────────────────────┘         │
└─────────────────────▲───────────────────────┘
                      │ implementa
                      │
┌─────────────────────┼───────────────────────┐
│       infrastructure.persistence            │
│                                             │
│   ┌──────────────────────────────────────┐  │
│   │ PriceRepositoryAdapter               │  │
│   └───────────────┬──────────────────────┘  │
│                   │ usa                     │
│                   ▼                         │
│   ┌──────────────────────────────────────┐  │
│   │ SpringDataPriceRepository            │  │
│   └───────────────┬──────────────────────┘  │
│                   │ gestiona                │
│                   ▼                         │
│   ┌──────────────────────────────────────┐  │
│   │ PriceEntity                          │  │
│   └──────────────────────────────────────┘  │
└─────────────────────────────────────────────┘
 
 
┌─────────────────────────────────────────────┐
│               infrastructure.api            │
│                                             │
│       ┌──────────────────────────────┐      │
│       │ PricingController            │      │
│       └──────────────┬───────────────┘      │
│                      │ llama a              │
│                      ▼                      │
│               PricingService                │
│                                             │
│       ┌──────────────────────────────┐      │
│       │ PriceResponse                │      │
│       └──────────────────────────────┘      │
└─────────────────────────────────────────────┘
 
 
┌─────────────────────────────────────────────┐
│               domain.model                  │
│                                             │
│               ┌───────────┐                 │
│               │ Price     │                 │
│               └───────────┘                 │
└─────────────────────────────────────────────┘
```

---
 
# 📂 Estructura completa del proyecto
 
```text
src
├── main
│ ├── java
│ │ └── com
│ │     └── example
│ │         └── pricing
│ │             ├── application
│ │             │ └── PricingService.java
│ │             ├── domain
│ │             │ ├── model
│ │             │ │ └── Price.java
│ │             │ └── port
│ │             │   └── PriceRepositoryPort.java
│ │             ├── infrastructure
│ │             │    ├── api
│ │             │    │ ├── PricingController.java
│ │             │    │ └── PriceResponse.java
│ │             │    └── persistence
│ │             │        ├── PriceEntity.java
│ │             │        ├── SpringDataPriceRepository.java
│ │             │        └── PriceRepositoryAdapter.java
│ │             └──PricingApplication.java
│ └── resources
│   ├── application.properties
│   └── data.sql
│
└── test
    └── java
        └── com
            └── example
                └── pricing
                    └── api
                        └── PricingApplicationTests.java
```
 
---
 
# Descripción de cada capa
 
## Dominio (`domain`)
 
Contiene el modelo de negocio y los puertos que definen las dependencias externas.
 
### Clases
 
- `Price` → Entidad de dominio.
- `PriceRepositoryPort` → Puerto de salida para obtener precios.
 
El dominio no depende de ninguna tecnología.
 
---
 
## Aplicación (`application`)
 
Implementa los casos de uso del sistema.
 
### Clases
 
- `PricingService` → Orquesta la lógica de aplicación y utiliza el puerto del dominio.
 
La aplicación depende únicamente del dominio.
 
---
 
## Infraestructura (`infrastructure`)
 
Implementa los adaptadores que conectan el dominio con tecnologías externas.
 
### Entrada (API REST)
 
#### Clases
 
- `PricingController`
- `PriceResponse`
 
### Persistencia (JPA)
 
#### Clases
 
- `PriceEntity`
- `SpringDataPriceRepository`
- `PriceRepositoryAdapter` → Implementación de `PriceRepositoryPort`
 
La infraestructura depende de aplicación y dominio, nunca al revés.
 
---
 
# Endpoint principal
 
## GET `/api/prices`
 
### Parámetros
 
| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| applicationDate | LocalDateTime | Fecha en la que se quiere obtener el precio |
| productId | Long | Identificador del producto |
| brandId | Long | Identificador de la marca |
 
### Ejemplo
 
```http
GET /api/prices?applicationDate=2020-06-14T10:00:00&productId=35455&brandId=1
```
 
---
 
# Tests de integración
 
Los tests validan cinco escenarios especialmente relevantes en motores de pricing reales.
 
## ✔ 1. Solapamiento de tarifas
 
Varias tarifas son válidas en la misma fecha.
 
Se comprueba la selección correcta según prioridad.
 
## ✔ 2. Rangos de fechas
 
Cada tarifa tiene un intervalo de validez.
 
Se verifica que el motor elige la tarifa activa.
 
## ✔ 3. Cambios horarios
 
Algunas tarifas cambian dentro del mismo día.
 
Se valida la precisión temporal de las consultas.
 
## ✔ 4. Prioridad entre tarifas superpuestas
 
Cuando varias tarifas cubren el mismo rango temporal, prevalece la de mayor prioridad.
 
## ✔ 5. Consistencia del modelo
 
Se comprueba que el JSON devuelto contiene todos los campos esperados.
 
### Ejecución
 
Los tests se ejecutan mediante:
 
- `RestTestClient`
- Servidor embebido con `WebEnvironment.RANDOM_PORT`
 
---
 
# Cómo ejecutar el proyecto
 
## Ejecutar la API
 
```bash
mvn spring-boot:run
```
 
## Ejecutar los tests
 
```bash
mvn test
```
 
---
 
# Requisitos
 
- JDK 21
- Maven 3.9+
- Git
 
---
 
# Autor
 
**Carlos Calvo Manzano**
 
Repositorio:
 
https://github.com/carloscalvomanzano/ejercicio
``