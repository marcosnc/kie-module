/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.module.kie.config;

import org.mule.config.spring.handlers.AbstractMuleNamespaceHandler;
import org.mule.config.spring.parsers.generic.OrphanDefinitionParser;
import org.mule.config.spring.parsers.specific.MessageProcessorDefinitionParser;
import org.mule.module.kie.LocalSessionConfig;
import org.mule.module.kie.RemoteSessionConfig;
import org.mule.module.kie.processors.drools.DeleteFact;
import org.mule.module.kie.processors.drools.FireRules;
import org.mule.module.kie.processors.drools.GetGlobal;
import org.mule.module.kie.processors.drools.SetGlobal;
import org.mule.module.kie.processors.drools.UpsertFact;
import org.mule.module.kie.processors.jbpm.AbortProcess;
import org.mule.module.kie.processors.jbpm.StartProcess;

/**
 * Registers a Bean Definition Parsers for the "kie" namespace.
 */
public class KieNamespaceHandler extends AbstractMuleNamespaceHandler
{

    public void init()
    {
        // Session
        registerBeanDefinitionParser("local-session", new OrphanDefinitionParser(LocalSessionConfig.class, true));
        registerBeanDefinitionParser("remote-session", new OrphanDefinitionParser(RemoteSessionConfig.class, true));

        // jBPM
        registerBeanDefinitionParser("start", new MessageProcessorDefinitionParser(StartProcess.class));
        registerBeanDefinitionParser("abort", new MessageProcessorDefinitionParser(AbortProcess.class));
        /*
        // TODO: Crear tarea manual nueva
        registerBeanDefinitionParser("new-task", new MessageProcessorDefinitionParser(NewTask.class));
        // TODO: Completar una tarea
        registerBeanDefinitionParser("complete-task", new MessageProcessorDefinitionParser(CompleteTask.class));
        // TODO: pedir tareas de un usuario
        registerBeanDefinitionParser("get-tasks", new MessageProcessorDefinitionParser(getTasks.class));
        // TODO: Enviar señales a un proceso
        registerBeanDefinitionParser("signal", new MessageProcessorDefinitionParser(Signal.class));
        // TODO: WorkItemHandler -> para llamar desde jBpm a mule
        */

        // Drools
        registerBeanDefinitionParser("set-global", new MessageProcessorDefinitionParser(SetGlobal.class));
        registerBeanDefinitionParser("get-global", new MessageProcessorDefinitionParser(GetGlobal.class));
        registerBeanDefinitionParser("upsert", new MessageProcessorDefinitionParser(UpsertFact.class));
        registerBeanDefinitionParser("delete", new MessageProcessorDefinitionParser(DeleteFact.class));
        registerBeanDefinitionParser("fire-rules", new MessageProcessorDefinitionParser(FireRules.class));
        /*
        // TODO: static query
        registerBeanDefinitionParser("static-query", new MessageProcessorDefinitionParser(StaticQuery.class));
        // TODO: live query, tipo push notifications, registrar un callback
        registerBeanDefinitionParser("live-query", new MessageProcessorDefinitionParser(LiveQuery.class));
        // TODO: Explorar comandos batch
        */
    }

    // probar https://github.com/droolsjbpm/jbpm-playground
    // Customer Relationships web service
    // https://github.com/droolsjbpm/jbpm-playground/tree/master/customer-relationships-workitems/src/main/java/org/jbpm/customer/services

    /*
    - desacoplar el acceso al jar de recursos de la app usando kie ci
    pasar el artifact id en:
    KieContainer kieContainer = KieServices.Factory.get().newKieContainer(getReleaseId());
    Con esto se logra tener un repositorio versionable de artifacts
    */

}

