/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.module.kie.local;

import org.mule.module.kie.KjarBuilder;
import org.mule.tck.junit4.FunctionalTestCase;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;

public abstract class KieLocalFunctionalTestCase extends FunctionalTestCase
{

    protected static final String DEFAULT_KJAR_FILE_NAME = "kie-resources.jar";
    protected static final String DEFAULT_KBASE_NAME = "kbase1";
    protected static final String DEFAULT_KSESSION_NAME = "ksession1";

    protected File kJar;

    @Before
    public void createKJar() throws IOException
    {
        kJar = KjarBuilder.create(getKjarFileName(), getKbaseName(), getKSessionName(), getKieResources());
    }

    @After
    public void removeKJar() throws IOException
    {
        kJar.delete();
    }

    protected abstract String[] getKieResources();

    protected String getKjarFileName()
    {
        return DEFAULT_KJAR_FILE_NAME;
    }

    protected String getKbaseName()
    {
        return DEFAULT_KBASE_NAME;
    }

    protected String getKSessionName()
    {
        return DEFAULT_KSESSION_NAME;
    }

}
