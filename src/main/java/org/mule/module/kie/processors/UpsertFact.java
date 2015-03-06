package org.mule.module.kie.processors;

import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.processor.MessageProcessor;
import org.mule.processor.AbstractInterceptingMessageProcessor;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.runtime.rule.FactHandle;

public class UpsertFact extends AbstractKieMessageProcessor
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

                FactHandle factHandle = kieSession.insert(event.getMessage().getPayload());

                kieSession.update(factHandle, event.getMessage().getPayload());
                kieSession.delete(factHandle);
                kieSession.fireAllRules();

                ProcessInstance instance = kieSession.startProcess(null);


                return event;
            }
        };
    }

}
