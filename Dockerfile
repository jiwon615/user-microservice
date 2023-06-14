FROM openjdk:17
VOLUME /tmp
COPY build/libs/user-service-1.0.jar JimartUserService.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "JimartUserService.jar"]