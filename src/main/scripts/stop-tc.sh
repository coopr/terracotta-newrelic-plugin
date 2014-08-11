#!/bin/sh

TC_HOME=/opt/terracotta/bigmemory-max-4.1.3
sh $TC_HOME/server/bin/stop-tc-server.sh
sh $YC_HOME/tools/management-console/bin/stop-tmc.sh