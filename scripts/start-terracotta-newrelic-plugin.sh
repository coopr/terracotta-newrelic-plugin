#!/bin/sh

#
# All content copyright Terracotta, Inc., unless otherwise indicated. All rights reserved.
#

JAVA_OPTS="${JAVA_OPTS} -Xms128m -Xmx512m"
TC_HOME="@terracotta_home@"

# OS specific support.  $var _must_ be set to either true or false.
cygwin=false
case "`uname`" in
CYGWIN*) cygwin=true;;
esac

if test \! -d "${JAVA_HOME}"; then
echo "$0: the JAVA_HOME environment variable is not defined correctly"
exit 2
fi

PLUGIN_DIR=`dirname "$0"`/..

CLASSPATH="${PLUGIN_DIR}/libs/terracotta-newrelic-plugin-1.0.0.jar:${PLUGIN_DIR}/libs/metrics_publish-1.2.2.jar:${PLUGIN_DIR}/libs/terracotta-jmxremote-3.6-runtime-1.0.0.jar:${TC_HOME}/ehcache/lib/ehcache-core-ee-2.6.7.jar:${TC_HOME}/lib/tc.jar:${TC_HOME}/lib/slf4j-api-1.6.1.jar:${TC_HOME}/lib/slf4j-log4j12-1.6.1.jar"

# For Cygwin, convert paths to Windows before invoking java
if $cygwin; then
[ -n "$PLUGIN_DIR" ] && PLUGIN_DIR=`cygpath -d "$PLUGIN_DIR"`
fi

echo ${CLASSPATH}

exec "${JAVA_HOME}/bin/java" \
${JAVA_OPTS} -Dlog4j.configuration="file:${PLUGIN_DIR}/config/log4j.properties" -Dnewrelic.platform.config.dir="${PLUGIN_DIR}/config" \
-cp ${CLASSPATH} \
com.newrelic.plugins.terracotta.TCL2MonLauncher "$@"