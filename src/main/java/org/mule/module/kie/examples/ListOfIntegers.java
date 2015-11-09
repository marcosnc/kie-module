/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.module.kie.examples;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "listOfIntegers")
@XmlAccessorType(XmlAccessType.FIELD)
public class ListOfIntegers
{
    @XmlElement(name = "integers")
    private List<Integer> integers = new ArrayList<>();

    public List<Integer> getIntegers()
    {
        return integers;
    }

    public void setIntegers(List<Integer> integers)
    {
        this.integers = integers;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Integer integer : integers) {
            sb.append(integer).append(", ");
        }
        if (sb.length()>2) {
            sb.setLength(sb.length()-2);
        }
        sb.append("}");
        return sb.toString();
    }
}
