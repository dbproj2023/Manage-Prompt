#!/bin/bash
RESOURCES_LOCATION="/home/ubuntu/resources/"
PROJECT_ROOT="/home/ubuntu/build"
JAR_FILE="$PROJECT_ROOT/build/libs/dbproj-builded.jar"

APP_LOG="$PROJECT_ROOT/application.log"
ERROR_LOG="$PROJECT_ROOT/error.log"
DEPLOY_LOG="$PROJECT_ROOT/deploy.log"

TIME_NOW=$(date +%c)

# resource 파일 이동
cd $RESOURCES_LOCATION
cp ./ ../build/src/main/resources/

cd $PROJECT_ROOT

# gradlew에 권한 부여
chmod +x gradlew

# gradlew 빌드
./gradlew clean build

# jar 파일 실행
echo "$TIME_NOW > $JAR_FILE 파일 실행" >> $DEPLOY_LOG
nohup java -jar $JAR_FILE > $APP_LOG 2> $ERROR_LOG &

CURRENT_PID=$(pgrep -f $JAR_FILE)
echo "$TIME_NOW > 실행된 프로세스 아이디 $CURRENT_PID 입니다." >> $DEPLOY_LOG