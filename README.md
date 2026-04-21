# Product Hexagonal Architecture

Aplicación de gestión de productos con Arquitectura Hexagonal, Spring Boot 4.0.5, Java 21 y H2 Database.

## Contenidos

- [Descripción](#descripción-del-proyecto)
- [Arquitectura](#arquitectura)
- [Buenas Prácticas](#buenas-prácticas-implementadas)
- [Estructura](#estructura-del-proyecto)
- [Requisitos](#requisitos-previos)
- [Instalación](#instalación)
- [Ejecución](#ejecución)
- [API](#api-endpoints)
- [Testing](#testing)



## Descripción del Proyecto

Sistema de gestión de productos implementando arquitectura hexagonal con separación clara entre:
- Dominio: Lógica pura sin dependencias externas
- Aplicación: Orquestación de casos de uso
- Infraestructura: Detalles técnicos y adaptadores



## Arquitectura Hexagonal

La arquitectura hexagonal organiza el código en tres capas independientes:

INFRASTRUCTURE (Controllers, Adapters, Config)
APPLICATION (Use Cases, Services)
DOMAIN (Models, Business Rules)

### Capas

**Domain Layer (domain/)**
- model/: Entidades de dominio
- ports/: Interfaces que definen contratos
- exception/: Excepciones específicas del negocio

**Application Layer (application/)**
- usecase/: Interfaces que definen operaciones
- service/: Implementación de casos de uso

**Infrastructure Layer (infrastructure/)**
- input/rest/: Controllers, DTOs, Mappers
- output/adapter/: Adaptadores de persistencia
- config/: Configuración de inyección de dependencias



## Buenas Prácticas Implementadas

### 1. Separación de Responsabilidades (SRP)
Cada clase tiene una única razón para cambiar. Domain layer es independiente de frameworks.

### 2. Inyección de Dependencias
Las dependencias se inyectan por constructor, facilitando testing y reduciendo acoplamiento.

### 3. Puertos y Adaptadores
El dominio define interfaces (puertos) que los adaptadores implementan. Permite cambiar de BD sin afectar lógica.

### 4. DTOs (Data Transfer Objects)
Separación entre modelos: ProductRequest (entrada), ProductResponse (salida), ErrorResponse (errores).

### 5. Mappers
Traducción explícita entre capas (DTO <-> Dominio <-> Entidades JPA).

### 6. Manejo Centralizado de Excepciones
Un único punto para manejar excepciones garantiza respuestas consistentes.

### 7. Validación en Dominio
Las reglas de negocio se validan en el dominio asegurando estados válidos siempre.

### 8. Configuración Centralizada
Inyección de dependencias centralizada (DomainConfig) facilita cambios.

### 9. Optional para Valores Nulos
Evita NullPointerException con API explícita para valores que pueden no existir.

### 10. Naming Conventions Claros
UseCase, Service, Adapter, Mapper permiten entender responsabilidad de cada clase.

### 11. Estructura de Paquetes por Capas
Organización clara: domain/, application/, infrastructure/.

### 12. Configuración Externizada
Propiedades en application.properties permiten cambios sin recompilación.

## Estructura del Proyecto

```plain
product-hexagonal/
├── README.md
├── pom.xml
├── mvnw / mvnw.cmd
├── db/
│   └── product_db.mv.db
└── src/
    ├── main/
    │   ├── java/com/devsenior/cdiaz/product/
    │   │   ├── ProductHexagonalApplication.java
    │   │   ├── domain/
    │   │   │   ├── model/Product.java
    │   │   │   ├── exception/ProductNotFoundException.java
    │   │   │   └── ports/
    │   │   ├── application/
    │   │   │   ├── usecase/
    │   │   │   │   ├── CreateProductUseCase.java
    │   │   │   │   └── GetProductUseCase.java
    │   │   │   └── service/ProductService.java
    │   │   └── infrastructure/
    │   │       ├── config/DomainConfig.java
    │   │       ├── input/rest/
    │   │       │   ├── ProductController.java
    │   │       │   ├── advice/GlobalExceptionHandler.java
    │   │       │   ├── dto/
    │   │       │   │   ├── ProductRequest.java
    │   │       │   │   ├── ProductResponse.java
    │   │       │   │   └── ErrorResponse.java
    │   │       │   └── mapper/ProductRestMapper.java
    │   │       └── output/
    │   │           ├── adapter/ProductPersistenceAdapter.java
    │   │           └── jpa/
    │   │               ├── ProductEntity.java
    │   │               └── SpringDataProductRepository.java
    │   └── resources/application.properties
    └── test/ProductHexagonalApplicationTests.java
```

## Requisitos Previos

- Java 21 LTS (Adoptium)
- Maven 3.8.0 o superior

Verificar:

```bash
java -version
mvn -version
```

## Instalación

```bash
cd c:\Users\Docente\Desktop\Java\ImpulsaJava\Modulo12\product-hexagonal
mvn clean install
```

## Ejecución

### Con Maven

```bash
mvn spring-boot:run
```

### Con IDE (VS Code)
Clic derecho en ProductHexagonalApplication.java → Run Java

### Acceso
- Aplicación: http://localhost:8080
- H2 Console: http://localhost:8080/h2-console
  Usuario: sa
  Contraseña: (vacío)
  JDBC URL: jdbc:h2:file:./db/product_db

## API Endpoints

### 1. Crear Producto

#### POST /api/products

```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{"name":"Laptop","price":999.99}'
```

**Response (201 Created)**:

```json
{
  "id": 1,
  "name": "Laptop",
  "price": 999.99
}
```

**Errores posibles**:
- 400 Bad Request - Precio inválido (menor o igual a 0)
- 400 Bad Request - Campo requerido faltante

### 2. Obtener Producto por ID

#### GET /api/products/{id}

```bash
curl http://localhost:8080/api/products/1
```

**Response (200 OK)**:

```json
{
  "id": 1,
  "name": "Laptop",
  "price": 999.99
}
```

**Errores posibles**:
- 404 Not Found - Producto no existe

### 3. Ejemplos Adicionales de Peticiones

**Crear varios productos**:

```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{"name":"Mouse","price":25.50}'

curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{"name":"Keyboard","price":79.99}'
```

**Obtener productos específicos**:

```bash
curl http://localhost:8080/api/products/2
curl http://localhost:8080/api/products/3
```

**Crear producto con precio inválido (retorna 400)**:

```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{"name":"Invalid","price":0}'
```

## Testing

**Ejecutar tests**:

```bash
mvn test
mvn test -Dtest=ProductServiceTest
mvn test jacoco:report
``` 

**Estructura**:

```plain
src/test/java/com/devsenior/cdiaz/product/
├── ProductHexagonalApplicationTests.java
├── domain/model/ProductTest.java
├── application/service/ProductServiceTest.java
├── infrastructure/input/rest/ProductControllerTest.java
└── infrastructure/output/ProductPersistenceAdapterTest.java
```

## Referencias

**Arquitectura**:
- Hexagonal Architecture
- Clean Architecture
- SOLID Principles

**Stack**:
- Spring Boot 4.0.5
- Spring Data JPA
- Maven

**Libros**:
- Clean Code - Robert C. Martin
- Clean Architecture - Robert C. Martin
- Domain-Driven Design - Eric Evans

**Última actualización**: Abril 2026  
**Versión**: 0.0.1-SNAPSHOT  
**Java**: 21 LTS  
**Spring Boot**: 4.0.5  
**Autor**: DevSenior - CDIAZ  
