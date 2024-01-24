#!/bin/sh
#------------------------------------------------------
#  사용법 
#  인자 $1 서비스명 
#  인자 $2 active profile 
#  인자 $3 실행 포트, profile에 설정이 있으면 
#  인자 $4 빌드된 어플리케이션 파일 
#  인자 $5 명령어  
#  
#  - 시작 
#  pvs.sh pvs-dev dev 80 PVS-2.0.1.QR8.jar start
#   
#  - 중지 
#  pvs.sh pvs-dev dev 80 PVS-2.0.1.QR8.jar stop
#
#  - 새로 시작 
#  pvs.sh pvs-dev dev 80 PVS-2.0.1.QR8.jar restart
#-----------------------------------------------------
SERVICE_NAME=$1 #서비스 명
OPER_SERVICE=$2 # active profile 개발 dev, 운영 prod,로컬 개발 local
PORT_NUMBER=$3  #어플리케이션 서버 포트 번호 
ACTION_LIBS=$4 # 빌드된 라이브러리 압축 파일
COMMAND=$5  # 명령어 

PID_PATH_NAME=/tmp/pvs-pid-$PORT_NUMBER

case $COMMAND in
    start)
        echo "Starting $SERVICE_NAME ..."
        if [ ! -f $PID_PATH_NAME ]; then
            nohup java -jar -Dspring.profiles.active="$OPER_SERVICE" -Dspring.config.use-legacy-processing=true -Dserver.port="$PORT_NUMBER" -server -Xms1024M -Xmx1024M -XX:MetaspaceSize=512M -XX:MaxMetaspaceSize=512M -XX:+HeapDumpOnOutOfMemoryError \
            -XX:+PrintGC -verbose:gc -XX:+UseG1GC -XX:+DisableExplicitGC -XX:+UseStringDeduplication \
            build/libs/"$ACTION_LIBS"  2>&1 & echo $! > $PID_PATH_NAME

            PID=$(cat $PID_PATH_NAME);
            echo "$SERVICE_NAME started ...[$PID]"
        else
            PID=$(cat $PID_PATH_NAME);
            echo "$SERVICE_NAME is already running ...[$PID]"
        fi
    ;;
    stop)
        if [ -f $PID_PATH_NAME ]; then
            PID=$(cat $PID_PATH_NAME);
            echo "$SERVICE_NAME stopping ...[$PID]"
            kill -9 $PID;
            echo "$SERVICE_NAME stopped ..."
            rm $PID_PATH_NAME
        else
            echo "$SERVICE_NAME [$PID] is not running ..."
        fi
    ;;
    restart)
        if [ -f "$PID_PATH_NAME" ]; then
            PID=$(cat $PID_PATH_NAME);
            echo "$SERVICE_NAME stopping ...[$PID]";
            kill -9 $PID;
            echo "$SERVICE_NAME stopped ...[$PID]";
            rm "$PID_PATH_NAME"
            sleep 5
        else
            echo "$SERVICE_NAME is not running ..."
        fi
        echo "$SERVICE_NAME starting ..."
        nohup java -jar -Dspring.profiles.active="$OPER_SERVICE" -Dspring.config.use-legacy-processing=true -Dserver.port="$PORT_NUMBER" -server -Xms1024M -Xmx1024M -XX:MetaspaceSize=512M -XX:MaxMetaspaceSize=512M -XX:+HeapDumpOnOutOfMemoryError \
        -XX:+PrintGC -verbose:gc -XX:+UseG1GC -XX:+DisableExplicitGC -XX:+UseStringDeduplication \
        build/libs/"$ACTION_LIBS"  2>&1 & echo $! > "$PID_PATH_NAME"

        PID=$(cat "$PID_PATH_NAME");
        echo "$SERVICE_NAME started ...[$PID]"
    ;;
esac