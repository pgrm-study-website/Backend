#!/usr/bin/env bash

REPOSITORY=/home/backend
PROJECT_NAME=spring_deploy

echo "> Build 파일 복사"

cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo "> 현재 구동 중인 스프링 어플리케이션 pid 확인"

CURRENT_PID=$(pgrep -f jar)

echo "> 현재 구동 중인 스프링 어플리케이션 pid : $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
  echo "> 현재 구동 중인 스프링 어플리케이션이 없으므로 종료하지 않습니다";
else
  echo "> sudo kill -9 $CURRENT_PID";
  sudo kill -9 $CURRENT_PID;
  sleep 5;
fi

echo "> 새 어플리케이션 배포"

JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

echo "> JAR_NAME에 실행권한(755) 추가"

sudo chmod 755 $JAR_NAME

echo "> $JAR_NAME 실행"

sudo nohup java -jar $REPOSITORY/$JAR_NAME > $REPOSITORY/nohup.out 2>&1 &

ps ax | grep jar
