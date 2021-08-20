#!/bin/zsh
## 64位server启动命令
java -d64 -XX:+UseG1GC -Xm4g -Xmx4g -Xss512k -XX:MaxGCPauseMillis=200 -XX:+DisableExplicitGC -XX:+UseStringDeduplication -jar qiuqiu-admin-0.0.1-SNAPSHOT.jar >/dev/null 2>&1 &

## 64位server启动命令(包含gc日志)
java -d64 -XX:+UseG1GC -Xms4g -Xmx4g -Xss512k -XX:MaxGCPauseMillis=200 -XX:+DisableExplicitGC -XX:+UseStringDeduplication -XX:+PrintGCTimeStamps -XX:+PrintGCDetails -Xloggc:/tmp/gc/qiuqiu-admin-gc.log -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=50 -XX:GCLogFileSize=10m -jar qiuqiu-admin-0.0.1-SNAPSHOT.jar >/dev/null 2>&1 &

## 64位server启动命令(包含gc日志与出现heap out of memory error时候打印dump文件)
java -d64 -XX:+UseG1GC -Xms4g -Xmx4g -Xss512k -XX:MaxGCPauseMillis=200 -XX:+DisableExplicitGC -XX:+UseStringDeduplication -XX:+ParallelRefProcEnabled -XX:+PrintGCTimeStamps -XX:+PrintGCDetails -Xloggc:/tmp/gc/qiuqiu-admin-gc.log -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=50 -XX:GCLogFileSize=10m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp/gc/gc_heap.dump -jar qiuqiu-admin-0.0.1-SNAPSHOT.jar >/dev/null 2>&1 &

## 32位server启动命令，如果堆内存小于3g优先使用32位的"just in time compiler",并且jdk版本也需要是32位的才能生效
## 如果jdk版本是64位的 -server依旧运行的是64位的"just in time compiler"
java -server -XX:+UseG1GC -Xms1g -Xmx1g -XX:MaxGCPauseMillis=200 -XX:+PrintGCTimeStamps -jar qiuqiu-admin-0.0.1-SNAPSHOT.jar >/dev/null 2>&1 &

#java -Djavax.net.debug=ssl -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8888 -jar springboot-1.0.jar
