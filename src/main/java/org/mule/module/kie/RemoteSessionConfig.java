package org.mule.module.kie;

import org.kie.api.runtime.KieSession;

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
        return null;
    }
}
