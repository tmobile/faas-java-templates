FROM maven:3.5-jdk-8-alpine as builder

ENV APP_HOME=/root/vertx/
RUN mkdir -p $APP_HOME/function

WORKDIR $APP_HOME/function

COPY function/ .

RUN mvn clean install

RUN apk --no-cache add curl \
    && echo "Pulling watchdog binary from Github." \
    && curl -sSLf https://github.com/openfaas-incubator/of-watchdog/releases/download/0.2.1/of-watchdog > /usr/bin/fwatchdog \
    && chmod +x /usr/bin/fwatchdog \
    && apk del curl --no-cache

FROM openjdk:8-alpine

RUN apk --no-cache add ca-certificates

COPY --from=builder $APP_HOME/function/target/*.jar app.jar
COPY --from=builder /usr/bin/fwatchdog .

ENV fprocess="java -jar app.jar"
ENV mode="http"
ENV upstream_url="http://127.0.0.1:8081"

CMD ["./fwatchdog"]

