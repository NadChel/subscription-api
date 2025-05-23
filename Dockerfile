FROM amazoncorretto:17-alpine-jdk AS builder

WORKDIR /app

COPY . .

RUN apk add --no-cache maven && \
    mvn package -Dmaven.test.skip=true

FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

COPY --from=builder /app/target/subscription-api-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

CMD ["java", "-jar", "subscription-api-0.0.1-SNAPSHOT.jar"]