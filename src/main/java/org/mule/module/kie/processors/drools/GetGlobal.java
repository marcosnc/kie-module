/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.module.kie.processors.drools;

import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.processor.MessageProcessor;
import org.mule.module.kie.processors.AbstractKieMessageProcessor;

import org.kie.api.runtime.KieSession;

public class GetGlobal extends AbstractKieMessageProcessor
{
    private String globalName;

    @Override
    protected MessageProcessor createMessageProcessor() throws MuleException
    {
        return new MessageProcessor()
        {

            @Override
            public MuleEvent process(MuleEvent event) throws MuleException
            {
                KieSession kieSession = getSession().getKieSession();
                event.getMessage().setPayload(kieSession.getGlobal(getGlobalName()));
                return event;
            }
        };
    }

    public String getGlobalName() { return globalName; }

    public void setGlobalName(String globalName) { this.globalName = globalName; }

}
