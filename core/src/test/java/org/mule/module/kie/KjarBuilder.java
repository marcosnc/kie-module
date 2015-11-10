/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.module.kie;

import org.mule.util.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public class KjarBuilder
{

    private static final String RESOURCE_DIR = "src/main/resources/";

    private static final String KMODULE_XML_RESOURCE = RESOURCE_DIR + "META-INF/kmodule.xml";

    private static final String KBASE_NAME_MARKER = "##KBASE-NAME##";
    private static final String KSESSION_NAME_MARKER = "##KSESSION-NAME##";

    private static final String DEFAULT_KMODULE_XML =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
        "<kmodule xmlns=\"http://jboss.org/kie/6.0.0/kmodule\">\n" +
        "    <kbase name=\"" + KBASE_NAME_MARKER + "\" default=\"true\">\n" +
        "        <ksession name=\"" + KSESSION_NAME_MARKER + "\" default=\"true\"/>\n" +
        "    </kbase>\n" +
        "</kmodule>";

    private static final int BUFFER_SIZE = 1024;

    public static File create(String kJarName, String kBaseName, String kSessionName, String ... resources) throws IOException
    {
        String kModuleXml = DEFAULT_KMODULE_XML
                .replace(KBASE_NAME_MARKER, kBaseName)
                .replace(KSESSION_NAME_MARKER, kSessionName);

        Manifest manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");

        File kJarFile = kJarName.startsWith("/") ? new File(kJarName) : new File(KjarBuilder.class.getResource("/").getFile(), kJarName);
        try (JarOutputStream target = new JarOutputStream(new FileOutputStream(kJarFile), manifest))
        {
            target.putNextEntry(new JarEntry(KMODULE_XML_RESOURCE));
            target.write(kModuleXml.getBytes());

            byte buffer[] = new byte[BUFFER_SIZE];
            for(String resource : resources)
            {
                target.putNextEntry(new JarEntry(RESOURCE_DIR + resource));
                try( InputStream in = IOUtils.getResourceAsStream(resource, KjarBuilder.class))
                {
                    for( int nRead;
                         (nRead = in.read(buffer, 0, buffer.length)) > 0;
                         target.write(buffer, 0, nRead));
                }
            }
        }
        return kJarFile;
    }

}
