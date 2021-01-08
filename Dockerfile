FROM openjdk:8-alpine

COPY target/uberjar/fcs.jar /fcs/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/fcs/app.jar"]
