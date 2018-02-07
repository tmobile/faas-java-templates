FROM openjdk:8 AS builder
ENV APP_HOME=/root/dev/vertx/

RUN mkdir -p $APP_HOME/src/main/java/function

WORKDIR $APP_HOME
COPY . .

RUN ./gradlew clean fatJar

FROM openjdk:alpine

RUN apk --no-cache add curl \
    && echo "Pulling watchdog binary from Github." \
    && curl -sSLf https://github.com/openfaas-incubator/of-watchdog/releases/download/0.2.1/of-watchdog > /usr/bin/fwatchdog \
    && chmod +x /usr/bin/fwatchdog \
    && apk del curl --no-cache

ENV VERTICLE_FILE java-vertx-1.0-SNAPSHOT.jar
ENV VERTICLE_HOME /usr/verticles

COPY --from=builder /root/dev/vertx/build/libs/$VERTICLE_FILE $VERTICLE_HOME/

WORKDIR $VERTICLE_HOME

ENV fprocess="java -jar $VERTICLE_FILE"

CMD ["fwatchdog"]