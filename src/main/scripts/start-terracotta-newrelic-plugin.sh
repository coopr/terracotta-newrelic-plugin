#!/bin/sh

#
# All content copyright Terracotta, Inc., unless otherwise indicated. All rights reserved.
#

JAVA_OPTS="${JAVA_OPTS} -Xms128m -Xmx512m"

# OS specific support.  $var _must_ be set to either true or false.
cygwin=false
case "`uname`" in
CYGWIN*) cygwin=true;;
esac

if test \! -d "${JAVA_HOME}"; then
echo "$0: the JAVA_HOME environment variable is not defined correctly"
exit 2
fi

JAVA_OPTS="${JAVA_OPTS} -Xms128m -Xmx512m -Djavax.net.ssl.trustStore=conf/geotrust.jks -Djavax.net.ssl.trustStorePassword=password

exec "${JAVA_HOME}/bin/java" \
${JAVA_OPTS} \
-Djava.library.path=bin -cp "lib/*:conf" com.terracotta.nrplugin.app.Main "$@"