@echo off

rem All content copyright Terracotta, Inc., unless otherwise indicated. All rights reserved.

setlocal

if not defined JAVA_HOME (
  echo Environment variable JAVA_HOME needs to be set
  exit \b 1
)

set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_COMMAND="%JAVA_HOME%\bin\java"
set JAVA_OPTS=-Xms128m -Xmx512m -Djavax.net.ssl.trustStore=geotrust.jks -Djavax.net.ssl.trustStorePassword=password

:START_TCSERVER
%JAVA_COMMAND% %JAVA_OPTS% -jar tc-nr-plugin-###com.terracotta.nrplugin.version###.jar

exit \b %ERRORLEVEL%
endlocal
