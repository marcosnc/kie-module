#! /bin/bash

cd ~/mnc/prjs/kie/mule-module-kie
mvn clean install -DskipTests
cp target/mule-module-kie-3.8.0-SNAPSHOT.jar src/test/resources/examples/simple-1/lib
rm src/test/resources/examples/simple-1.zip
cd ~/mnc/prjs/kie/mule-module-kie/src/test/resources/examples/simple-1
zip -r ../simple-1.zip *
rm -R /Users/marcosnunezcortes/mnc/mulas/mule-enterprise-standalone-3.8.0-SNAPSHOT/apps/simple-1*
cp ~/mnc/prjs/kie/mule-module-kie/src/test/resources/examples/simple-1.zip ~/mnc/mulas/mule-enterprise-standalone-3.8.0-SNAPSHOT/apps
