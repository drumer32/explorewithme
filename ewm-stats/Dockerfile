FROM amazoncorretto:16
COPY target/*.jar ewm-stats.jar
EXPOSE 9090
ENTRYPOINT ["java","-jar","/ewm-stats.jar"]