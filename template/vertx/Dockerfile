FROM openjdk:8-alpine AS builder

ENV APP_HOME=/root/vertx/

RUN mkdir -p $APP_HOME/function

COPY function/ $APP_HOME/function/

WORKDIR $APP_HOME/function

RUN ./gradlew clean fatJar

RUN apk --no-cache add curl \
    && echo "Pulling watchdog binary from Github." \
    && curl -sSLf https://github.com/openfaas-incubator/of-watchdog/releases/download/0.2.1/of-watchdog > /usr/bin/fwatchdog \
    && chmod +x /usr/bin/fwatchdog \
    && apk del curl --no-cache

FROM openjdk:8-alpine

RUN apk --no-cache add ca-certificates

ENV VERTICLE_FILE java-vertx-1.0-SNAPSHOT.jar
ENV VERTICLE_HOME /usr/verticles

WORKDIR $VERTICLE_HOME

COPY --from=builder /root/vertx/function/build/libs/$VERTICLE_FILE .
COPY --from=builder /usr/bin/fwatchdog .

ENV fprocess="java -jar $VERTICLE_FILE"
ENV mode="http"
ENV upstream_url="http://127.0.0.1:8081"

CMD ["./fwatchdog"]