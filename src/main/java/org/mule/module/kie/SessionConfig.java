/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.module.kie;


import org.mule.api.MuleContext;
import org.mule.api.context.MuleContextAware;

import org.kie.api.runtime.KieSession;

public abstract class SessionConfig implements MuleContextAware
{

    protected MuleContext muleContext;
    protected String name;
    protected String base;
    protected KieSession kieSession;

    @Override
    public void setMuleContext(MuleContext muleContext)
    {
        this.muleContext = muleContext;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getBase()
    {
        return base;
    }

    public void setBase(String base)
    {
        this.base = base;
    }

    public KieSession getKieSession()
    {
        if(kieSession==null)
        {
            kieSession = createKieSession();
        }
        return kieSession;
    }

    protected abstract KieSession createKieSession();

}
