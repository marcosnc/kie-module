/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.module.kie;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.mule.tck.junit4.AbstractMuleTestCase;
import org.mule.util.IOUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.junit.Test;

public class KjarBuilderTestCase extends AbstractMuleTestCase
{

    private static final String KJAR_FILE_NAME = "resources2.jar";

    private static final String RESOURCE_1 = "rule-3-times.drl";
    private static final String RESOURCE_2 = "hello-world.bpmn2";

    private static final String KMODULE_XML_RESOURCE = "src/main/resources/META-INF/kmodule.xml";
    private static final String EXPECTED_KMODULE_XML =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<kmodule xmlns=\"http://jboss.org/kie/6.0.0/kmodule\">\n" +
            "    <kbase name=\"kbase1\" default=\"true\">\n" +
            "        <ksession name=\"ksession1\" default=\"true\"/>\n" +
            "    </kbase>\n" +
            "</kmodule>";

    @Test
    public void createDefaultKJar() throws Exception
    {
        File kJar = KjarBuilder.create(KJAR_FILE_NAME, "kbase1", "ksession1", RESOURCE_1, RESOURCE_2);

        assertThat(kJar.exists(), is(true));
        assertThat(kJar.getAbsolutePath(), is(this.getClass().getResource("/" + KJAR_FILE_NAME).getFile()));

        String[] expectedEntriesNames = new String[]{
                "META-INF/MANIFEST.MF",
                KMODULE_XML_RESOURCE,
                "src/main/resources/" + RESOURCE_1,
                "src/main/resources/" + RESOURCE_2};

        JarFile jarFile = new JarFile(kJar, true);
        assertThat(getEntriesNames(jarFile), containsInAnyOrder(expectedEntriesNames));

        String kModuleContents = IOUtils.toString(jarFile.getInputStream(jarFile.getEntry(KMODULE_XML_RESOURCE)));
        assertThat(kModuleContents, is(EXPECTED_KMODULE_XML));
    }

    private List<String> getEntriesNames(JarFile jarFile)
    {
        List names = new ArrayList();
        Enumeration<JarEntry> jarEntries = jarFile.entries();
        while(jarEntries.hasMoreElements())
        {
            names.add(jarEntries.nextElement().getName());
        }
        return names;
    }

}
