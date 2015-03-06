/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.module.kie;

import org.mule.api.MuleEvent;
import org.mule.construct.Flow;
import org.mule.tck.junit4.FunctionalTestCase;

import org.junit.Test;

public class InsertFactTestCase extends FunctionalTestCase
{

    @Override
    protected String getConfigFile()
    {
        return "kie-local-config-insert.xml";
    }

    @Test
    public void insertFact() throws Exception
    {
        Flow flow = (Flow) getFlowConstruct("insertFact");
        MuleEvent event = getTestEvent(2);

        event = flow.process(event);

        System.out.println(event.getMessage().getPayloadAsString());
    }


}
