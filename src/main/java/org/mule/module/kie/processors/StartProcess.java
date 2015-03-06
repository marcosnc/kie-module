/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.kie.processors;

import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.processor.MessageProcessor;
import org.mule.processor.AbstractInterceptingMessageProcessor;

import org.kie.api.runtime.process.ProcessInstance;


public class StartProcess extends AbstractKieMessageProcessor
{

    private String process;

    @Override
    protected MessageProcessor createMessageProcessor() throws MuleException
    {
        return new AbstractInterceptingMessageProcessor()
        {

            @Override
            public MuleEvent process(MuleEvent event) throws MuleException
            {
                System.out.println("= session name ====>   " + getSession().getName() + "   " + event.getId());
                //ProcessInstance pi = getSession().getKieSession().createProcessInstance(getProcess(), null);
                ProcessInstance pi = getSession().getKieSession().startProcess(getProcess());
                System.out.println(pi.getId());

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
}