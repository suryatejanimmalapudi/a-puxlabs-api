FROM eclipse-temurin:25-jdk AS builder

WORKDIR /app

# Copy the Gradle wrapper first so dependency resolution can be cached.
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

RUN chmod +x gradlew

COPY src src

RUN ./gradlew bootJar --no-daemon \
    && find build/libs -maxdepth 1 -type f -name '*.jar' ! -name '*-plain.jar' -exec cp {} app.jar \;


FROM eclipse-temurin:25-jre

WORKDIR /app

COPY --from=builder /app/app.jar app.jar

# Cloud Run sends SIGTERM to the container during shutdown. Spring Boot handles
# graceful shutdown, while this keeps the runtime image non-root.
RUN groupadd --system app && useradd --system --gid app app \
    && chown app:app /app/app.jar
USER app

EXPOSE 8080

ENTRYPOINT ["java", "-Duser.timezone=Asia/Kolkata", "-jar", "/app/app.jar"]
