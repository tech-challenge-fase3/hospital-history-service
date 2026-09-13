FROM eclipse-temurin:25-jdk AS build
WORKDIR /workspace
COPY hospital-parent/pom.xml hospital-parent/pom.xml
COPY hospital-history-service/pom.xml hospital-history-service/pom.xml
COPY hospital-history-service/.mvn hospital-history-service/.mvn
COPY hospital-history-service/mvnw hospital-history-service/mvnw
COPY hospital-history-service/src hospital-history-service/src
RUN chmod +x hospital-history-service/mvnw \
    && ./hospital-history-service/mvnw -f hospital-history-service/pom.xml clean package -DskipTests \
    && cd hospital-history-service/target \
    && jar xf app.jar META-INF/MANIFEST.MF \
    && grep -q 'Main-Class: org.springframework.boot.loader.launch.JarLauncher' META-INF/MANIFEST.MF

FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=build /workspace/hospital-history-service/target/app.jar /app/app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
