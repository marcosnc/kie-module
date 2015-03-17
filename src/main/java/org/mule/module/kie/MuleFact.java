/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.module.kie;

import org.kie.api.runtime.rule.FactHandle;

public class MuleFact<T>
{
    private FactHandle handle;
    private T fact;

    public static <T> MuleFact<T> newFact(FactHandle handle, T fact)
    {
        return new MuleFact(handle, fact);
    }

    public MuleFact(FactHandle handle, T fact)
    {
        this.handle = handle;
        this.fact = fact;
    }

    public FactHandle getHandle()
    {
        return handle;
    }

    public void setHandle(FactHandle handle)
    {
        this.handle = handle;
    }

    public T getFact()
    {
        return fact;
    }

}
