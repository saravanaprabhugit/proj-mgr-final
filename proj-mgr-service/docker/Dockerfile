FROM openjdk:8
ADD /proj-mgr-service-0.0.1-SNAPSHOT.jar proj-mgr-service-0.0.1-SNAPSHOT.jar
EXPOSE 9090
RUN bash -c 'touch /proj-mgr-service-0.0.1-SNAPSHOT.jar'
ENTRYPOINT ["java","-jar","proj-mgr-service-0.0.1-SNAPSHOT.jar"]