#!/bin/zsh
java -server -XX:+UseG1GC -Xms1g Xmx1g -XX:MaxGCPauseMillis=500 -XX:+PrintGCTimeStamp -jar qiuqiu-admin.jar >/dev/null 2>&1 &
#java -Djavax.net.debug=ssl -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8888 -jar springboot-1.0.jar
