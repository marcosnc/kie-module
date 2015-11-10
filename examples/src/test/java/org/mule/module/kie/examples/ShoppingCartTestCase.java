/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.module.kie.examples;

import org.drools.workshop.model.Item;
import org.junit.Test;
import org.mule.construct.Flow;
import org.mule.module.kie.local.KieLocalFunctionalTestCase;

public class ShoppingCartTestCase extends KieLocalFunctionalTestCase
{

    @Override
    protected String getConfigFile()
    {
        return "02-shopping-cart/shopping-cart.xml";
    }

    @Override
    protected String[] getKieResources()
    {
        return new String[]{"02-shopping-cart/shopping-cart.drl"};
    }

    @Test
    public void addTooManyItems() throws Exception
    {
        Flow flow = (Flow) getFlowConstruct("testFlow");
        flow.process(getTestEvent(new Item("item-1", 10)));
        flow.process(getTestEvent(new Item("item-2", 20)));
        flow.process(getTestEvent(new Item("item-3", 30)));
        flow.process(getTestEvent(new Item("item-4", 40)));
    }



}
