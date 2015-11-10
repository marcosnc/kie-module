/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.module.kie.examples;

import org.mule.module.kie.KjarBuilder;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class SimpleBuilder {

    // Note: This class must be under the test folder because the KJarBuilder class is a test class in the core project
    //       and the mule-core dependency is added as "provided", so if we move the class to main scope the IOUtils
    //       class is not found.

    // Test using: curl http://localhost:8081/fireRules/8

    public static void main(String[] args) {
        // TODO: create the whole mule app using this builder

        try {
            createKJar();
            runScript();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createKJar() throws IOException {
        File kJar = new File("/Users/marcosnunezcortes/mnc/prjs/kie/mule-module-kie/examples/src/main/resources/01-simple/mule-app/01-simple-resources.jar");
        kJar.delete();
        kJar = KjarBuilder.create(
                kJar.getAbsolutePath(),
                "kbase1",
                "ksession1",
                new String[]{"listMultiplication.drl"});
        System.out.println("k-Jar file: " + kJar.getAbsolutePath());
    }

    private static void runScript() throws IOException, InterruptedException {
//        ProcessBuilder pb = new ProcessBuilder("/Users/marcosnunezcortes/mnc/prjs/kie/mule-module-kie/examples/src/main/resources/01-simple/build.sh", "myArg1", "myArg2");
//        Map<String, String> env = pb.environment();
//        env.put("VAR1", "myValue");
//        env.remove("OTHERVAR");
//        env.put("VAR2", env.get("VAR1") + "suffix");
//        pb.directory(new File("myDir"));
//        Process p = pb.start();

        ProcessBuilder pb = new ProcessBuilder("/Users/marcosnunezcortes/mnc/prjs/kie/mule-module-kie/examples/src/main/resources/01-simple/build.sh");
        final Process p = pb.start();
        Thread output = new Thread(new Runnable() {
            @Override
            public void run() {
                int c;
                try {
                    while( (c=p.getInputStream().read())!=-1) {
                        System.out.print((char) c);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        output.start();
        System.out.println("Script error code: " + p.waitFor());
        output.join();
    }


}
