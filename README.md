MOTOMAMI
Descripción
MOTOMAMI es una aplicación móvil diseñada para mujeres conductoras de diversos vehículos, incluyendo motos, coches, bicicletas, patinetes eléctricos y furgonetas. La aplicación permite registrar partes de accidente y contratar seguros específicos para conductoras a partir de los 16 años.

Requisitos Previos
Java 17
Maven
Base de Datos MySQL
Clonar el Repositorio

git clone https://github.com/SikaFlex/MotoMami-TFG-hibernate.git
cd MotoMammiApplicationAGB
Configuración de la Base de Datos
Configurar hibernate.cfg.xml con los detalles de tu base de datos
Configura application.properties con los detalles de tu base de datos y rutas de los archivos. Ejemplo de configuración:
properties
Copiar código
spring.datasource.url=jdbc:mysql://localhost:3306/mm_agb
spring.datasource.username=usuario
spring.datasource.password=contraseña
spring.jpa.hibernate.ddl-auto=update


Compilación y Ejecución
bash
mvn clean install
mvn spring-boot:run
Uso

Todas las sentencia SQL para crea tu base de datos: TRABAJO FINAL SQL.sql

Registro de Partes de Accidente
Los usuarios pueden registrar partes de accidente a través de la aplicación. Además, la aplicación realiza un proceso de carga diario de un fichero de partes de accidente.

Histórico de Facturas
Los usuarios pueden ver un histórico de las facturas generadas. Cada factura incluye información detallada sobre el seguro contratado.

Arquitectura del Proyecto
Estructura del Proyecto
src/main/java: Código fuente principal
src/main/resources: Archivos de configuración y recursos
src/test/java: Pruebas unitarias
Principales Tecnologías Utilizadas
Spring Boot: Framework para la creación de aplicaciones Java
Maven: Herramienta de gestión de dependencias y construcción
Hibernate: Framework de ORM (Mapeo Objeto-Relacional)
Java 17: Versión del lenguaje de programación
Contribución
Guía para Contribuidores
Realiza un fork del repositorio.
Crea una nueva rama (git checkout -b feature/nueva-funcionalidad).
Realiza los cambios necesarios y commitea (git commit -m 'Añadir nueva funcionalidad').
Empuja los cambios a tu repositorio (git push origin feature/nueva-funcionalidad).
Crea un Pull Request en GitHub.
Pruebas
Ejecución de Pruebas
bash
Copiar código
mvn test
Despliegue
Despliegue en Producción
Configura el entorno de producción en application.properties 
Compila el proyecto con Maven.
Despliega el archivo JAR generado en el servidor de producción.
**El orden de ejecucion es el siguiente: Interfaz->Customer->Vehicles->Parts->Invoice 

Cada una dependende de la otras, siendo el orden de ejecucion el siguiente ReadFile -> ProcessFile -> GenerateInvoice


















