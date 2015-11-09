/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.module.kie.local;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.mule.api.MuleEvent;
import org.mule.construct.Flow;
import org.mule.module.kie.examples.ListOfIntegers;
import org.mule.module.kie.examples.ScalarMultiplier;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * Insert a fact using a custom type, in this case an instance of {@link ScalarMultiplier}
 */
public class InsertFactCustomTestCase extends KieLocalFunctionalTestCase
{

    @Override
    protected String getConfigFile()
    {
        return "local/insert-fact-custom-config.xml";
    }

    @Override
    protected String[] getKieResources()
    {
        return new String[]{"listMultiplication.drl"};
    }

    @Test
    public void insert() throws Exception
    {
        ScalarMultiplier multiplier = new ScalarMultiplier(2);

        List<Integer> outData = Arrays.asList(
                0 * multiplier.getValue(),
                1 * multiplier.getValue(),
                2 * multiplier.getValue(),
                3 * multiplier.getValue(),
                4 * multiplier.getValue());

        MuleEvent event = ((Flow) getFlowConstruct("insertFact")).process(getTestEvent(multiplier));

        assertThat(((ListOfIntegers)event.getFlowVariable("globalList")).getIntegers(), is(outData));
    }

    @Test
    public void insertIterative() throws Exception
    {
        ScalarMultiplier multiplier = new ScalarMultiplier(3);
        ((Flow) getFlowConstruct("insertFactIterative")).process(getTestEvent(multiplier));
    }

}
