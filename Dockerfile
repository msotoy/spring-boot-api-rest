FROM adoptopenjdk/openjdk11:ubi
RUN mkdir /opt/app
COPY target/reactive-0.0.1-SNAPSHOT.jar /opt/app
CMD ["java", "-jar", "/opt/app/reactive-0.0.1-SNAPSHOT.jar"]