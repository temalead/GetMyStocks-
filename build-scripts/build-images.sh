#!/bin/bash

function build_image() {
  JAR_FILE=$1
  APP_NAME=$2

  docker build -f ./build-scripts/docker/Dockerfile \
    --build-arg JAR_FILE=${JAR_FILE} \
    -t nikulinme/${APP_NAME}:latest .
}

APP_VERSION=0.0.1-SNAPSHOT

cd ..

mvn clean package -DskipTests

build_image ./stock_service/target/stock_service-${APP_VERSION}.jar stock_service
build_image ./telegram_service/target/telegram_service-${APP_VERSION}.jar telegram_service
