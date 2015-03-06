package org.mule.module.kie.processors;

import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.processor.MessageProcessor;
import org.mule.processor.AbstractInterceptingMessageProcessor;

import org.kie.api.runtime.KieSession;

public class DeleteFact extends AbstractKieMessageProcessor
{

    @Override
    protected MessageProcessor createMessageProcessor() throws MuleException
    {
        return new AbstractInterceptingMessageProcessor()
        {

            @Override
            public MuleEvent process(MuleEvent event) throws MuleException
            {
                KieSession kieSession = getSession().getKieSession();

                return event;
            }
        };
    }

}
