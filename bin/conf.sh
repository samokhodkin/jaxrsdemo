#!/bin/sh

[ -n "$CONF_DONE" ] && return
export CONF_DONE=true

export PID_FILE=app.pid

MAINJARS=`echo ../java/release/*.jar | tr ' ' ':'`
export CLASSPATH=$MAINJARS:`find ../java/lib -name '*.jar' | paste -s -d ':'`

export RUN_CMD='java -cp '$CLASSPATH' bwf.jaxrsdemo.app.Main'
