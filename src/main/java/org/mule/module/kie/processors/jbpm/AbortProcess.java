/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.module.kie.processors.jbpm;

import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.MuleRuntimeException;
import org.mule.api.processor.MessageProcessor;
import org.mule.config.i18n.MessageFactory;
import org.mule.module.kie.processors.AbstractKieMessageProcessor;

import org.kie.api.runtime.process.ProcessInstance;


public class AbortProcess extends AbstractKieMessageProcessor
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
                getSession().getKieSession().abortProcessInstance(getSource(event));

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

    protected long getSource(MuleEvent event)
    {
        Object source = event.getMessage().getPayload(); // TODO: read from other event parts accordingly to configuration
        if (source instanceof Number)
        {
            return ((Number)source).longValue();
        }
        if (source instanceof ProcessInstance)
        {
            return ((ProcessInstance)source).getId();
        }
        throw new MuleRuntimeException(MessageFactory.createStaticMessage("Error getting process id. Expected long or ProcessInstance, current: " + source.getClass()));
    }

}