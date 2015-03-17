/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.module.kie;

import org.mule.api.lifecycle.Disposable;

import java.io.File;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 *
 */
public class LocalSessionConfig extends SessionConfig implements Disposable
{

    private String resources;

    public String getResources()
    {
        return resources;
    }

    public void setResources(String resources)
    {
        this.resources = resources;
    }

    @Override
    protected KieSession createKieSession()
    {
        KieServices kieServices = KieServices.Factory.get();
        //File rootFile = FileUtils.newFile(getResources());
        File rootFile = new File(this.getClass().getResource(getResources()).getFile());
        KieBuilder kieBuilder = kieServices.newKieBuilder(rootFile);
        kieBuilder.buildAll();
        KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());

        if( getName()!=null )
        {
            return kieContainer.newKieSession(getName());
        }

        KieBase kieBase =  getBase()!=null ? kieContainer.getKieBase(getBase()) : kieContainer.getKieBase();
        return kieBase.newKieSession();
    }

    @Override
    public void dispose()
    {
        if (kieSession!=null)
        {
            kieSession.dispose();
            kieSession = null;
        }
    }

}
