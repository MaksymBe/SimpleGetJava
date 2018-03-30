FROM java:8
WORKDIR /
ADD target/EasyGet-1.0-SNAPSHOT.jar app.jar
CMD java -jar app.jar