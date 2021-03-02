FROM openjdk:11
ARG JAR_FILE=target/caixa-service-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} caixa.jar
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /caixa.jar"]