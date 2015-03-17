/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.module.kie.processors.drools;

import static org.mule.module.kie.MuleFact.newFact;
import org.mule.api.MuleEvent;
import org.mule.module.kie.MuleFact;
import org.mule.module.kie.processors.AbstractKieMessageProcessor;

public abstract class AbstractFactMessageProcessor extends AbstractKieMessageProcessor
{

    // TODO: allow to handle iterator and iterable as source and target

    protected MuleFact getSource(MuleEvent event)
    {
        Object source = event.getMessage().getPayload(); // TODO: read from other event parts accordingly to configuration
        if (source instanceof MuleFact)
        {
            return (MuleFact)source;
        }
        return newFact(null, source);
    }

    protected void setTarget(MuleEvent event, MuleFact muleFact)
    {
        event.getMessage().setPayload(muleFact); // TODO: write to other event part accordingly to configuration
    }

}
