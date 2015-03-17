/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.module.kie;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.mule.api.MuleEvent;
import org.mule.construct.Flow;
import org.mule.tck.junit4.FunctionalTestCase;

import java.util.Arrays;

import org.junit.Test;

public class InsertFactTestCase extends FunctionalTestCase
{

    @Override
    protected String getConfigFile()
    {
        return "kie-local-config-insert.xml";
    }

    @Test
    public void insertFactFiringRulesInUpsert() throws Exception
    {
        doTest("insertFactFiringRulesInUpsert");
    }

    @Test
    public void insertFactFiringRulesAfterUpsert() throws Exception
    {
        doTest("insertFactFiringRulesAfterUpsert");
    }

    private void doTest(String flowName) throws Exception
    {
        int inData = 2;
        String outData = Arrays.toString(new int[] {0 * inData, 1 * inData, 2 * inData});

        MuleEvent event = ((Flow) getFlowConstruct(flowName)).process(getTestEvent(inData));

        assertThat(event.getFlowVariable("globalList").toString(), is(outData));
    }

}
