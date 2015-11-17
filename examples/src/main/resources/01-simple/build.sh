#! /bin/bash

export KIE_PROJECT_HOME=$1
export MULE_HOME=$2

# Build KIE project
cd $KIE_PROJECT_HOME
mvn clean install

# Copy KIE jars to app's lib dir
mkdir examples/src/main/resources/01-simple/mule-app/lib
cp core/target/mule-module-kie-3.8.0-SNAPSHOT.jar examples/src/main/resources/01-simple/mule-app/lib
cp core/target/mule-module-kie-3.8.0-SNAPSHOT-tests.jar examples/src/main/resources/01-simple/mule-app/lib

# Create app zip file
rm examples/src/main/resources/01-simple/build-output/01-simple.zip
cd $KIE_PROJECT_HOME/examples/src/main/resources/01-simple/mule-app
mkdir ../build-output
zip -r ../build-output/01-simple.zip *

# Copy app zip file to mule's app dir
rm -R $MULE_HOME/apps/01-simple*
cp $KIE_PROJECT_HOME/examples/src/main/resources/01-simple/build-output/01-simple.zip $MULE_HOME/apps

# Remove lib files to avoid using git space
rm $KIE_PROJECT_HOME/examples/src/main/resources/01-simple/mule-app/lib/*
