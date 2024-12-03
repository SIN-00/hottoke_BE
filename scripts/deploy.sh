#!/bin/bash

REPOSITORY=/home/ubuntu  #1
PROJECT_NAME=hotketok_server

cd $REPOSITORY/$PROJECT_NAME/  #2

echo "> 프로젝트 build 시작"
./gradlew build --exclude-task test    #4

echo "> 홈 디렉토리로 이동"
cd $REPOSITORY

echo "> Build 파일 복사"
cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/  #5

echo "> 현재 구동중인 애플리케이션 pid 확인"
CURRENT_PID=$(sudo lsof -ti:8080)     #6

echo "현재 구동중인 애플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then            #7
        echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
    echo "> kill -9 $CURRENT_PID"
    sudo kill -9 $CURRENT_PID
    # 포트가 해제될 때까지 대기
    while lsof -ti:8080 >/dev/null; do
        echo "> 포트 8080 해제 대기 중..."
        sleep 1
    done
        echo "> 포트 8080 해제 완료"
        sleep 5
fi

echo "> 새 애플리케이션 배포"

JAR_NAME=$(ls -tr $REPOSITORY/ | grep jar | tail -n 1)    #8

echo "> JAR Name : $JAR_NAME"

sudo nohup java -Duser.timezone=Asia/Seoul -jar $REPOSITORY/$JAR_NAME 2>&1 &      #9
#화이팅