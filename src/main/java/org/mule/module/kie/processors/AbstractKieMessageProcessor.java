/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.kie.processors;

import org.mule.api.MuleContext;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.context.MuleContextAware;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.registry.RegistrationException;
import org.mule.config.i18n.CoreMessages;
import org.mule.module.kie.SessionConfig;


public abstract class AbstractKieMessageProcessor implements MessageProcessor, Initialisable, MuleContextAware
{

    private MuleContext muleContext;
    private SessionConfig session;

    private MessageProcessor messageProcessor;

    @Override
    public void initialise() throws InitialisationException
    {
        initializeConfiguration();

        try
        {
            messageProcessor = createMessageProcessor();
        }
        catch (MuleException e)
        {
            throw new InitialisationException(e, this);
        }
    }


    @Override
    public MuleEvent process(MuleEvent event) throws MuleException
    {
        return messageProcessor.process(event);
    }


    private void initializeConfiguration() throws InitialisationException
    {
        if (session == null)
        {
            try
            {
                session = muleContext.getRegistry().lookupObject(SessionConfig.class);
                if (session == null)
                {
                    throw new InitialisationException(CoreMessages.createStaticMessage("No session defined for the kie processor. " +
                                                                                       "Add a local-session or a remote-session element."), this);
                }
            }
            catch (RegistrationException e)
            {
                throw new InitialisationException(e, this);
            }
        }
    }

    protected abstract MessageProcessor createMessageProcessor() throws MuleException;

    @Override
    public void setMuleContext(MuleContext muleContext)
    {
        this.muleContext = muleContext;
    }

    public SessionConfig getSession()
    {
        return session;
    }

}