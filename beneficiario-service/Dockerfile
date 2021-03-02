FROM openjdk:11
ARG JAR_FILE=target/beneficiario-service-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} beneficiario.jar
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /beneficiario.jar"]