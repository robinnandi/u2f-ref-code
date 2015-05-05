#! /bin/bash

SRC_DIR="java/src/"
BIN_DIR="bin/"
LIB_DIR="libs/"

PACKAGES=(
"com.google.u2f"
"com.google.u2f.client"
"com.google.u2f.client.impl"
"com.google.u2f.codec"
"com.google.u2f.key"
"com.google.u2f.key.impl"
"com.google.u2f.key.messages"
"com.google.u2f.server"
"com.google.u2f.server.data"
"com.google.u2f.server.impl"
"com.google.u2f.server.messages"
"com.google.u2f.tools.httpserver"
"com.google.u2f.tools.httpserver.servlets"
)
#echo ${PACKAGES[*]}
packagepath() {
  # replace . with / to get package path
  local var="$1"
  echo $var | tr . /
}

THE_CLASSPATH=
for i in `ls $LIB_DIR*.jar`
  do
  THE_CLASSPATH=${THE_CLASSPATH}:${i}
done
#echo $THE_CLASSPATH

mkdir -p $BIN_DIR
for PACKAGE in ${PACKAGES[*]}
  do
  PACKAGE_DIR=$(packagepath $PACKAGE)
  echo "Compiling $PACKAGE ..."
  for file in `ls $SRC_DIR$PACKAGE_DIR/*.java`
    do
    echo "================================================"
    echo "javac -classpath '.:${THE_CLASSPATH}' -sourcepath $SRC_DIR $file -d $BIN_DIR"
    javac -classpath ".:${THE_CLASSPATH}" -sourcepath $SRC_DIR $file -d $BIN_DIR
  done  
  echo "================================================"
done
