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
import org.mule.module.kie.processors.UpsertFact;
import org.mule.module.kie.LocalSessionConfig;
import org.mule.module.kie.RemoteSessionConfig;
import org.mule.module.kie.processors.SetGlobal;
import org.mule.module.kie.processors.StartProcess;

/**
 * Registers a Bean Definition Parsers for the "kie" namespace.
 */
public class KieNamespaceHandler extends AbstractMuleNamespaceHandler
{

    public void init()
    {
        registerBeanDefinitionParser("local-session", new OrphanDefinitionParser(LocalSessionConfig.class, true));
        registerBeanDefinitionParser("remote-session", new OrphanDefinitionParser(RemoteSessionConfig.class, true));
        registerBeanDefinitionParser("start", new MessageProcessorDefinitionParser(StartProcess.class));
        registerBeanDefinitionParser("insert", new MessageProcessorDefinitionParser(UpsertFact.class));
        registerBeanDefinitionParser("set-global", new MessageProcessorDefinitionParser(SetGlobal.class));
    }
    
}

