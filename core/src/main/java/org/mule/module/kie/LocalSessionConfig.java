/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.module.kie;

import org.mule.api.lifecycle.Disposable;
import org.mule.util.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.Message;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeEnvironment;
import org.kie.api.runtime.manager.RuntimeEnvironmentBuilder;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.manager.RuntimeManagerFactory;
import org.kie.api.runtime.manager.audit.AuditService;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.TaskSummary;
import org.kie.internal.runtime.manager.context.EmptyContext;

/**
 *
 */
public class LocalSessionConfig extends SessionConfig implements Disposable
{

    private String kieSessionName;
    private String kieBaseName;
    private String resources;

    public String getKieSessionName()
    {
        return kieSessionName;
    }

    public void setKieSessionName(String kieSessionName)
    {
        this.kieSessionName = kieSessionName;
    }

    public String getKieBaseName()
    {
        return kieBaseName;
    }

    public void setKieBaseName(String kieBaseName)
    {
        this.kieBaseName = kieBaseName;
    }

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
        KieSession kieSession = null;

        // in file system
        try {
            kieSession = createKieSession_fileSystem();
        } catch (Exception e) {
            // TODO: use logger
            //e.printStackTrace();
        }

        // in class path by gav
        if (kieSession==null) {
            try {
                if (getKieSessionName()==null || getKieSessionName().isEmpty()) {
                    // TODO: para evitar que esto demore, crear la kbase y guardarla
                    //  kieBase = KieServices.Factory.get().getKieClasspathContainer().newKieBase();
                    //  kieBase.newKieSession()
                    kieSession = KieServices.Factory.get().getKieClasspathContainer().newKieSession();
                } else {
                    kieSession = KieServices.Factory.get().getKieClasspathContainer().newKieSession(getKieSessionName());
                }
            } catch (Exception e) {
                // TODO: use logger
                //e.printStackTrace();
            }
        }

        if (kieSession==null) {
            // using kie-ci
            try {
                String[] parts = getResources().split(":");
                ReleaseId releaseId = KieServices.Factory.get().newReleaseId(parts[0], parts[1], parts[2]);
                if (getKieSessionName()==null || getKieSessionName().isEmpty()) {
                    kieSession = KieServices.Factory.get().newKieContainer(releaseId).newKieSession();
                } else {
                    kieSession = KieServices.Factory.get().newKieContainer(releaseId).newKieSession(getKieSessionName());
                }
            } catch (Exception e) {
                // TODO: use logger
                //e.printStackTrace();
            }
        }

        return kieSession;
    }


    //@Override
    protected KieSession createKieSession_fileSystem()
    {
        buildResources();

        KieContainer kieContainer = KieServices.Factory.get().newKieContainer(getReleaseId());

        if (getKieSessionName() != null)
        {
            return kieContainer.newKieSession(getKieSessionName());
        }

        KieBase kieBase = getKieBaseName() != null ? kieContainer.getKieBase(getKieBaseName()) : kieContainer.getKieBase();
        return kieBase.newKieSession();
    }

    private void buildResources()
    {
        // TODO: improve the way resources files are located.
        //       Following cases should be supported:
        //       - test cases (currently using "/<resources-file>")
        //       - running applications should be able to use files in their current directory (currently you can use "apps/<app-name>/<resources-file>")
        //----------------------------------------
        File rootFile = null;
        try {
            String resourcesPath = FileUtils.getResourcePath(getResources(), this.getClass());
            if (resourcesPath==null) {
                resourcesPath = this.getClass().getResource(getResources()).getFile();
            }
            rootFile = new File(resourcesPath);
            if (!rootFile.exists()) {
                throw new FileNotFoundException("Resources file does not exist: " + resourcesPath);
            }

        } catch (IOException e) {
            e.printStackTrace();
            rootFile = new File(this.getClass().getResource(getResources()).getFile());
        }
        //----------------------------------------

        KieBuilder kieBuilder = KieServices.Factory.get().newKieBuilder(rootFile);
        kieBuilder.buildAll();

        assert (kieBuilder.getResults().getMessages(Message.Level.ERROR).size() == 0);
    }

    private ReleaseId getReleaseId()
    {
        return KieServices.Factory.get().getRepository().getDefaultReleaseId();
        //return KieServices.Factory.get().newReleaseId(groupId, artifactId, version);
    }

    protected void createKieSession2()
    {
        RuntimeEnvironment runtimeEnvironment = RuntimeEnvironmentBuilder.Factory.get().newEmptyBuilder().get();
        // runtime manager es thread safe
        // runtime manager por artefacto (set de recursos)
        // runtime manager tiene una única sesión y un único servicio de tareas
        RuntimeManager runtimeManager = RuntimeManagerFactory.Factory.get().newPerProcessInstanceRuntimeManager(runtimeEnvironment);
        RuntimeEngine engine = runtimeManager.getRuntimeEngine(EmptyContext.get());
        KieSession kieSession = engine.getKieSession();
        TaskService taskService = engine.getTaskService();
        List<TaskSummary> tasks = taskService.getTasksAssignedAsPotentialOwner("mary", "en-UK");
        AuditService auditService = engine.getAuditService();
        auditService.findProcessInstances();

        // TODO Probar esto
        RuntimeEnvironment runtimeEnvironment2 = RuntimeEnvironmentBuilder.Factory.get().newClasspathKmoduleDefaultBuilder(getKieBaseName(), getKieSessionName()).get();

        // Otro caso de uso es completar tareas manuales desde mule (****)

        // Ver de compartir los datasources en los runtimes locales

        // En los remotos está el concepto de deploy unit

    }

    @Override
    public void dispose()
    {
        if (kieSession != null)
        {
            kieSession.dispose();
            kieSession = null;
        }
    }

}
