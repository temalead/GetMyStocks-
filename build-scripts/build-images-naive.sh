#!/bin/bash

function build_basic() {
  JAR_FILE=$1
  APP_NAME=$2

  docker build -f ./build-scripts/docker/Dockerfile \
    --build-arg JAR_FILE=${JAR_FILE} \
    -t nikulinme/${APP_NAME}:latest \
    -t nikulinme/${APP_NAME}:naive .
}

APP_VERSION=0.0.1-SNAPSHOT

cd ..

mvn clean package -DskipTests

build_basic ./stock_service/target/stock_service-${APP_VERSION}.jar stock_service
build_basic ./telegram_service/target/telegram_service-${APP_VERSION}.jar telegram_service
