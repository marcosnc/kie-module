/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.module.kie;

import org.mule.api.MuleRuntimeException;
import org.mule.config.i18n.MessageFactory;

import java.net.URL;

import org.kie.api.runtime.KieSession;
import org.kie.remote.client.api.RemoteRuntimeEngineFactory;

/**
 *
 */
public class RemoteSessionConfig  extends SessionConfig
{
    private String deploymentId;
    private String url;
    private String user;
    private String pass;

    public String getDeploymentId()
    {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId)
    {
        this.deploymentId = deploymentId;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public String getPass()
    {
        return pass;
    }

    public void setPass(String pass)
    {
        this.pass = pass;
    }

    @Override
    protected KieSession createKieSession()
    {
        try
        {
            return RemoteRuntimeEngineFactory.newRestBuilder()
                    .addDeploymentId(deploymentId)
                    .addUrl(new URL(url))
                    .addUserName(user)
                    .addPassword(pass)
                    .build().getKieSession();
        }
        catch(Exception e)
        {
            throw new MuleRuntimeException(MessageFactory.createStaticMessage("Error creating KIE remote session."), e);
        }
    }
}
