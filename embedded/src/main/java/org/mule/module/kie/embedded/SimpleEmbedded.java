/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.module.kie.embedded;

import org.mule.api.MuleContext;
import org.mule.config.spring.SpringXmlConfigurationBuilder;
import org.mule.context.DefaultMuleContextFactory;
import org.mule.module.kie.Constants;

public class SimpleEmbedded {

    public static void main(String[] args) {
        MuleContext muleContext = null;
        try {
            DefaultMuleContextFactory muleContextFactory = new DefaultMuleContextFactory();
            System.setProperty("app.home", Constants.DEFAULT_KIE_PROJECT_HOME + "/" + "examples/src/main/resources/01-simple/mule-app");
            SpringXmlConfigurationBuilder configBuilder = new SpringXmlConfigurationBuilder("01-simple/mule-app/mule-config.xml");
            muleContext = muleContextFactory.createMuleContext(configBuilder);
            muleContext.start();
            System.out.println("Mule app started and receiving requests on localhost:8081 for 60 seconds...");

            Thread.sleep(60000);

            System.out.println("Stopping mule app...");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                muleContext.stop();
                muleContext.dispose();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
