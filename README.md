# Sistema de Reservas de Canchas de Fútbol

API REST para gestionar campos de fútbol, usuarios y reservas de horarios, con asignación de balones y camisetas. Incluye autenticación JWT, autorización por roles, validaciones y consultas filtradas.

## Tecnologías

| Componente | Tecnología |
| --- | --- |
| Lenguaje | Java 25 |
| Framework | Spring Boot 4.0.3 |
| Persistencia | Spring Data JPA y MySQL |
| Seguridad | Spring Security, BCrypt y JWT HS256 |
| JWT | JJWT 0.12.6 |
| Validación | Jakarta Validation y validadores personalizados |
| Construcción | Maven Wrapper y Lombok |
| Cobertura | JaCoCo 0.8.14 |

H2 también está declarado como dependencia y existen archivos en `data/`, pero la conexión activa utiliza MySQL.

## Funcionalidades

- Registro de usuarios, login y consulta del usuario autenticado.
- Consulta de canchas por identificador, tamaño, superficie y estado.
- Catálogos de balones y camisetas.
- Creación y consulta de reservas.
- Búsqueda por campo, día, estado y filtros combinados.
- Asignación y retiro de balones y camisetas de una reserva.
- Comprobaciones de cruces de horarios.
- Cancelación mediante cambio de estado.
- Roles `ADMIN`, `USUARIO` y `RECEPCION`, con permisos definidos en el modelo.

## Requisitos

- JDK 25 y `JAVA_HOME` configurado.
- MySQL accesible.
- Internet para descargar Maven y dependencias en la primera ejecución.

Usa `mvnw.cmd` en Windows o `mvnw` en Linux/macOS; no necesitas instalar Maven por separado.

## Instalación local

Ejecuta los comandos desde la raíz del proyecto.

### 1. Crear la base de datos

Desde un cliente MySQL:

```sql
CREATE DATABASE proyectoreservacanchas CHARACTER SET utf8mb4;
```

Concede al usuario de la aplicación los permisos necesarios sobre esa base. Hibernate usa `ddl-auto=update`, por lo que puede crear o modificar tablas durante el arranque.

### 2. Configurar conexión y JWT

La configuración se encuentra en [application.properties](src/main/resources/application.properties). Actualmente contiene valores fijos de conexión y de clave JWT.

Puedes sobrescribirlos con variables de entorno de Spring Boot. Ejemplo en PowerShell:

```powershell
$env:SPRING_DATASOURCE_URL = 'jdbc:mysql://localhost:3306/proyectoreservacanchas'
$env:SPRING_DATASOURCE_USERNAME = 'tu_usuario'
$env:SPRING_DATASOURCE_PASSWORD = 'tu_contrasena'
$env:APP_JWT_EXPIRATIONMS = '28800000'

# Genera una clave propia de 32 bytes y la codifica en Base64.
$claveJwt = New-Object byte[] 32
$generadorJwt = [System.Security.Cryptography.RandomNumberGenerator]::Create()
$generadorJwt.GetBytes($claveJwt)
$generadorJwt.Dispose()
$env:APP_JWT_SECRET = [Convert]::ToBase64String($claveJwt)
```

La clave debe ser Base64 válido que represente al menos 32 bytes: el servicio JWT la decodifica y firma con HS256. Conserva la misma clave entre reinicios si quieres mantener válidos los tokens emitidos. La expiración configurada, `28800000` milisegundos, equivale a 8 horas.

Estas variables afectan a los procesos iniciados desde esa terminal. El proyecto no configura una importación automática de `.env`; crear ese archivo por sí solo no cambia la configuración.

Antes de publicar el proyecto, externaliza y sustituye las credenciales y la clave JWT que están incluidas en el archivo de propiedades.

### 3. Arrancar

En la misma terminal de Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

En Linux/macOS, configura las variables equivalentes en tu shell y ejecuta:

```bash
./mvnw spring-boot:run
```

La API utiliza por defecto `http://localhost:8080`. Puedes sobrescribir el puerto con `SERVER_PORT`. No incluye una interfaz web de usuario; prueba las rutas mediante un cliente HTTP.

## Autenticación

| Método | Ruta | Acceso |
| --- | --- | --- |
| POST | `/auth/register` | Público |
| POST | `/auth/login` | Público |
| GET | `/auth/me` | JWT válido |

Ejemplo de cuerpo para registrar una cuenta:

```json
{
  "dni": "12345678",
  "nombre": "Ana",
  "username": "ana.demo",
  "apellido": "Perez",
  "numero": "900000000",
  "email": "ana@example.com",
  "password": "ReemplazarPorUnaClavePropia",
  "reservas": []
}
```

El registro devuelve los datos del usuario. Después inicia sesión para obtener el token:

```json
{
  "username": "ana.demo",
  "password": "ReemplazarPorUnaClavePropia"
}
```

Envía este segundo cuerpo a `POST /auth/login` con `Content-Type: application/json`. Usa el campo `token` de la respuesta en las peticiones privadas:

```http
Authorization: Bearer <token>
```

La API utiliza sesiones stateless y contraseñas BCrypt.

### Primera cuenta administrativa

El bloque de inicialización de usuarios en [Application.java](src/main/java/com/Reservas/SistemaReservas/Application.java) está comentado. Por tanto, no se crean automáticamente cuentas administrativas al iniciar una base nueva.

El registro público crea una cuenta de usuario común. `GET /user/conv/{id}` permite asignar administración, pero exige un administrador existente: no sirve para crear el primer administrador desde una instalación vacía. Debes preparar esa primera cuenta y sus relaciones de roles/permisos mediante un proceso de inicialización controlado.

## Principales endpoints

Las rutas distinguen mayúsculas y minúsculas; por ejemplo, el prefijo es `/campoFutbol`.

| Método | Ruta | Función |
| --- | --- | --- |
| GET | `/campoFutbol/findAll` | Listar campos |
| GET | `/campoFutbol/findById/{id}` | Consultar un campo |
| GET | `/campoFutbol/findByTamano/{tamanoCampo}` | Filtrar por tamaño |
| GET | `/campoFutbol/findBySuperficie/{superficie}` | Filtrar por superficie |
| GET | `/campoFutbol/findByEstado/{estado}` | Filtrar por estado del campo |
| POST | `/campoFutbol/save` | Crear campo; requiere ADMIN |
| GET | `/balon/listar` | Listar balones |
| GET | `/balon/buscarPorId/{id}` | Consultar balón |
| POST | `/balon/guardar` | Crear balón; requiere ADMIN |
| GET | `/camiseta/listAll` | Listar camisetas |
| POST | `/camiseta/save` | Crear camiseta; requiere ADMIN |
| POST | `/reserva/save` | Crear reserva |
| GET | `/reserva/findAll` | Listar reservas |
| GET | `/reserva/findById/{id}` | Consultar reserva |
| GET | `/reserva/findByCampo/{id}` | Consultar reservas del campo |
| GET | `/reserva/findByDia/{dia}` | Buscar por día |
| GET | `/reserva/findByEstadoReserva/{estado}` | Buscar por estado de reserva |
| GET | `/reserva/filtrar` | Consulta con filtros |
| POST | `/reserva/cambiarEstado/{id}` | Cancelar cambiando el estado a DISPONIBLE |
| POST | `/reserva/reservarBalon/{idReserva}/{idBalon}` | Asociar balón |
| DELETE | `/reserva/borrarReservaBalon/{idReserva}` | Retirar balón |
| POST | `/reserva/reservarCamiseta/{idReserva}/{idCamiseta}` | Asociar camiseta |
| DELETE | `/reserva/borrarReservaCamiseta/{idReserva}` | Retirar camiseta |
| GET | `/user/findAll` | Consultar usuarios autenticándose |

La lectura GET de campos y balones y la ruta `/camiseta/listAll` son públicas. El resto exige autenticación, además de las restricciones específicas por método. Los estados de reserva son `RESERVADA`, `PENDIENTE` y `DISPONIBLE`.

Consulta [Controller](src/main/java/com/Reservas/SistemaReservas/Controller/) y [dto/request](src/main/java/com/Reservas/SistemaReservas/dto/request/) para los contratos completos. Las reservas referencian usuarios y campos existentes.

## Integración con un frontend

CORS permite actualmente estos orígenes:

- `http://localhost:3000`
- `http://127.0.0.1:3000`

Los métodos admitidos son GET, POST, PUT, PATCH, DELETE y OPTIONS. Si tu cliente utiliza otro puerto o dominio, ajusta [SecurityConfig.java](src/main/java/com/Reservas/SistemaReservas/config/SecurityConfig.java).

## Pruebas, cobertura y compilación

Con la configuración de entorno disponible:

```powershell
# Ejecutar pruebas
.\mvnw.cmd test

# Compilar, ejecutar pruebas y generar JAR y reporte de cobertura
.\mvnw.cmd clean verify

# Ejecutar el JAR
java -jar target/SistemaReservas-0.0.1-SNAPSHOT.jar
```

En Linux/macOS sustituye `.\mvnw.cmd` por `./mvnw`.

Actualmente hay una prueba de carga de contexto en `ApplicationTests`. Usa una base de datos de pruebas accesible: el contexto carga JPA y puede actualizar su esquema.

JaCoCo está configurado para generar el reporte durante `prepare-package`. Tras una ejecución satisfactoria con datos de cobertura, el HTML estará en `target/site/jacoco/index.html`.

El repositorio no incluye actualmente Dockerfile ni configuración Compose. La forma de ejecución documentada es Maven o el JAR.

## Estructura

```text
src/main/java/com/Reservas/SistemaReservas/
├── Controller/          # Endpoints REST
├── Entity/              # Entidades, enumeraciones y seguridad
├── Repository/          # Repositorios y especificaciones
├── Services/
│   ├── impl/            # Implementaciones
│   └── reglas/          # Contratos de servicios
├── config/              # Spring Security y CORS
├── dto/                 # Requests, responses y mappers
├── excepcion/           # Excepciones y manejo de errores
├── security/            # Filtro y servicio JWT
├── validations/         # Validadores personalizados
└── Application.java     # Punto de entrada
```

La configuración está en `src/main/resources/application.properties`; los mensajes de validación, en `ValidationMessages.properties`.
