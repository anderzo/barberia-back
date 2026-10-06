# services: 
además de validar, tiene la lógica de negocio y coordina varios repositorios. Ejemplo: 
al crear una cita, comprueba que el barbero ofrezca el servicio, que esté en su horario y calcula la hora de fin.

## vpara JWT

Para qué: crear y leer tokens firmados, agregamos esto para su funcionamiento:

``` 
Pom.xml

<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.12.6</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.12.6</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.12.6</version>
    <scope>runtime</scope>
</dependency>

 ``` 