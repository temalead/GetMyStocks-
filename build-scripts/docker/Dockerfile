FROM openjdk:16

ARG JAR_FILE
ADD ${JAR_FILE} /opt/app.jar

ENTRYPOINT ["java", "-jar", "/opt/app.jar"]