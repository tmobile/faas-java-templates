FROM openjdk:8-alpine AS builder

ENV APP_HOME=/root/vertx/

RUN mkdir -p $APP_HOME/function

WORKDIR $APP_HOME

COPY . .

RUN ./gradlew clean fatJar

RUN apk --no-cache add curl \
    && echo "Pulling watchdog binary from Github." \
    && curl -sSLf https://github.com/openfaas-incubator/of-watchdog/releases/download/0.2.1/of-watchdog > /usr/bin/fwatchdog \
    && chmod +x /usr/bin/fwatchdog \
    && apk del curl --no-cache

FROM openjdk:8-alpine

RUN apk --no-cache add ca-certificates

RUN mkdir -p /usr/verticles

WORKDIR /usr/verticles

COPY --from=builder /root/vertx/build/libs/java-vertx-1.0-SNAPSHOT.jar .
COPY --from=builder /usr/bin/fwatchdog .

ENV fprocess="java -jar /usr/verticles/java-vertx-1.0-SNAPSHOT.jar"
ENV mode="http"
ENV upstream_url="http://127.0.0.1:8081"

CMD ["./fwatchdog"]