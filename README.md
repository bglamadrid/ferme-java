# FERME Backend Webservice

## Requisitos

* JDK 7
* Maven
* Una base de datos Oracle y los [Drivers JDBC](https://blogs.oracle.com/dev2dev/get-oracle-jdbc-drivers-and-ucp-from-oracle-maven-repository-without-ides). El proyecto se ejecutó pensando en la versión 11g.
* Si se opta por otra base de datos, se debe reconfigurar el dialecto de Hibernate en el archivo `src/main/java/resources/application.properties`. Se recomienda Revisar también cualquier cambio que sea necesario hacer en las clases del package **cl.duoc.alumnos.ferme.domain.entities** 

## Compilar

1. Posicionarse en directorio raíz de la aplicación, donde reside el archivo *pom.xml*
2. Ejecutar `mvn clean install`

## Desplegar

* `mvn spring-boot:run` (en directorio `/ferme-rest-api/`) - Levanta un Tomcat embebido. Éste se puede configurar en el archivo *src/main/java/resources/application.properties*
* `mvn package` - Genera un EAR en la carpeta ``ferme-ear/target/``, que puede ser instalado en un servidor de aplicaciones a elección.
