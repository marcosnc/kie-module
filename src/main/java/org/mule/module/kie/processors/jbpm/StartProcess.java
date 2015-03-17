/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.module.kie.processors.jbpm;

import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.processor.MessageProcessor;
import org.mule.module.kie.processors.AbstractKieMessageProcessor;

import java.util.Map;

import org.kie.api.runtime.process.ProcessInstance;


public class StartProcess extends AbstractKieMessageProcessor
{

    private String process;

    @Override
    protected MessageProcessor createMessageProcessor() throws MuleException
    {
        return new MessageProcessor()
        {

            @Override
            public MuleEvent process(MuleEvent event) throws MuleException
            {
                ProcessInstance pi = getSession().getKieSession().startProcess(getProcess(), getSource(event));

                setTarget(event, pi);

                return event;
            }
        };
    }

    public String getProcess()
    {
        return process;
    }

    public void setProcess(String process)
    {
        this.process = process;
    }

    protected Map<String, Object> getSource(MuleEvent event)
    {
        Object source = event.getMessage().getPayload(); // TODO: read from other event parts accordingly to configuration
        if (source instanceof Map)
        {
            return (Map)source;
        }
        return null;
    }

    protected void setTarget(MuleEvent event, ProcessInstance pi)
    {
        event.getMessage().setPayload(pi); // TODO: write to other event part accordingly to configuration
    }

}