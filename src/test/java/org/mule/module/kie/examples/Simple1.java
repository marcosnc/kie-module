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

public class Simple1 {
    public static void main(String[] args) {
        try {
            File kJar = new File("/Users/marcosnunezcortes/mnc/prjs/kie/mule-module-kie/src/test/resources/examples/simple-1/simple-1-resources.jar");
            kJar.delete();
            kJar = KjarBuilder.create(
                    kJar.getAbsolutePath(),
                    "kbase1",
                    "ksession1",
                    new String[]{"listMultiplication.drl"});
            System.out.println("k-Jar file: " + kJar.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
