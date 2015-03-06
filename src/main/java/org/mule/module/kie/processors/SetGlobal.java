package org.mule.module.kie.processors;

import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.processor.MessageProcessor;
import org.mule.processor.AbstractInterceptingMessageProcessor;

import org.kie.api.runtime.KieSession;

public class SetGlobal extends AbstractKieMessageProcessor
{
    private String name;
    private String value;

    @Override
    protected MessageProcessor createMessageProcessor() throws MuleException
    {
        return new AbstractInterceptingMessageProcessor()
        {

            @Override
            public MuleEvent process(MuleEvent event) throws MuleException
            {
                KieSession kieSession = getSession().getKieSession();
                Object resolvedValue = event.getMuleContext().getExpressionManager().evaluate(getValue(), event);
                kieSession.setGlobal(getName(), resolvedValue);
                return event;
            }
        };
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getValue() { return value; }

    public void setValue(String value)
    {
        this.value = value;
    }

}
