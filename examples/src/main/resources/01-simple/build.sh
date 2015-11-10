#! /bin/bash

export KIE_PROJECT_HOME=/Users/marcosnunezcortes/mnc/prjs/kie/mule-module-kie
export MULE_HOME=/Users/marcosnunezcortes/mnc/mulas/mule-enterprise-standalone-3.8.0-SNAPSHOT

cd $KIE_PROJECT_HOME
mvn clean install
mkdir examples/src/main/resources/01-simple/mule-app/lib
cp core/target/mule-module-kie-3.8.0-SNAPSHOT.jar examples/src/main/resources/01-simple/mule-app/lib
cp core/target/mule-module-kie-3.8.0-SNAPSHOT-tests.jar examples/src/main/resources/01-simple/mule-app/lib
rm examples/src/main/resources/01-simple/build-output/01-simple.zip
cd $KIE_PROJECT_HOME/examples/src/main/resources/01-simple/mule-app
mkdir ../build-output
zip -r ../build-output/01-simple.zip *
rm -R $MULE_HOME/apps/01-simple*
cp $KIE_PROJECT_HOME/examples/src/main/resources/01-simple/build-output/01-simple.zip $MULE_HOME/apps
