/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.module.kie.examples;

import org.mule.api.registry.RegistrationException;
import org.mule.construct.Flow;
import org.mule.module.kie.SessionConfig;
import org.mule.tck.junit4.FunctionalTestCase;

import org.drools.workshop.model.Item;
import org.junit.Test;
import org.kie.api.runtime.Channel;

public class ShoppingCartKjarTestCase extends FunctionalTestCase
{

    @Override
    protected String getConfigFile()
    {
        return "shopping-cart-kjar-config.xml";
    }

    @Test
    public void addTooManyItems() throws Exception
    {
        registerLoggingChannel("outboundChannel");

        Flow flow = (Flow) getFlowConstruct("testFlow");
        flow.process(getTestEvent(new Item("item-1", 10)));
        flow.process(getTestEvent(new Item("item-2", 20)));
        flow.process(getTestEvent(new Item("item-3", 30)));
        flow.process(getTestEvent(new Item("item-4", 40)));
    }

    private void registerLoggingChannel(String channelName) throws RegistrationException {
        // TODO: implement register channel as an operation, creating a wrapper that allows to call any mule endpoint
        SessionConfig session = muleContext.getRegistry().lookupObject(SessionConfig.class);
        if (!session.getKieSession().getChannels().containsKey(channelName)) {
            session.getKieSession().registerChannel(channelName, new Channel() {
                @Override
                public void send(Object o) {
                    //muleContext.getClient().dispatch(...);
                    logger.error("Logging from Mule: " + o.toString());
                }
            });
        }
    }

}
