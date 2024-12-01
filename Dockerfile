FROM openjdk:21
ADD target/TopCV-*.jar /TopCV.jar
ENTRYPOINT ["java", "-jar", "/TopCV.jar"]