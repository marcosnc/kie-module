/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.module.kie.local;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.mule.api.MuleEvent;
import org.mule.construct.Flow;

import org.junit.Test;
import org.kie.api.runtime.process.ProcessInstance;

public class StartProcessTestCase extends KieLocalFunctionalTestCase
{

    @Override
    protected String getConfigFile()
    {
        return "local/start-process-config.xml";
    }

    @Override
    protected String[] getKieResources()
    {
        return new String[]{"hello-world.bpmn2"};
    }

    @Test
    public void startProcess() throws Exception
    {
        Flow flow = (Flow) getFlowConstruct("startProcess");
        MuleEvent event = getTestEvent(TEST_MESSAGE);

        event = flow.process(event);

        assertThat(event.getMessage().getPayload(), instanceOf(ProcessInstance.class));
        assertThat(((ProcessInstance)event.getMessage().getPayload()).getId(), is(1L));
        assertThat(((ProcessInstance)event.getMessage().getPayload()).getState(), is(ProcessInstance.STATE_COMPLETED));
    }

}
