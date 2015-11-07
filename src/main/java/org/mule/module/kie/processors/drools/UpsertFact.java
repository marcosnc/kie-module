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
import org.mule.module.kie.MuleFact;

import org.kie.api.runtime.KieSession;

public class UpsertFact extends AbstractFactMessageProcessor
{

    private boolean fireRules;

    @Override
    protected MessageProcessor createMessageProcessor() throws MuleException
    {
        return new MessageProcessor()
        {

            @Override
            public MuleEvent process(MuleEvent event) throws MuleException
            {
                KieSession kieSession = getSession().getKieSession();

                MuleFact muleFact = getSource(event);
                if (muleFact.getHandle()==null)
                {
                    muleFact.setHandle(kieSession.insert(muleFact.getFact()));
                    // Para la conexion remota usar batch command
                }
                else
                {
                    kieSession.update(muleFact.getHandle(), muleFact.getFact());
                }

                if (fireRules)
                {
                    kieSession.fireAllRules();
                }

                setTarget(event, muleFact);

                return event;
            }
        };
    }

    public boolean isFireRules()
    {
        return fireRules;
    }

    public void setFireRules(boolean fireRules)
    {
        this.fireRules = fireRules;
    }
}
