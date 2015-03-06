package org.mule.module.kie;

import java.io.File;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 *
 */
public class LocalSessionConfig extends SessionConfig
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
}
