package org.apache.velocity.runtime.resource.util;

// TODO: Auto-generated Javadoc
/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

/**
 * Wrapper for Strings containing templates, allowing to add additional meta
 * data like timestamps.
 *
 * @author <a href="mailto:eelco.hillenius@openedge.nl">Eelco Hillenius</a>
 * @author <a href="mailto:henning@apache.org">Henning P. Schmiedehausen</a>
 * @version $Id: StringResource.java 685685 2008-08-13 21:43:27Z nbubna $
 * @since 1.5
 */
public final class StringResource
{
    
    /** template body. */
    private String body;
    
    /** encoding. */
    private String encoding;

    /** last modified ts. */
    private long lastModified;

    /**
	 * convenience constructor; sets body to 'body' and sets lastModified to
	 * now.
	 *
	 * @param body
	 *            the body
	 * @param encoding
	 *            the encoding
	 */
    public StringResource(final String body, final String encoding)
    {
        setBody(body);
        setEncoding(encoding);
    }

    /**
	 * Sets the template body.
	 *
	 * @author mqfdy
	 * @return String containing the template body.
	 * @Date 2018-9-3 11:38:31
	 */
    public String getBody()
    {
        return body;
    }

    /**
	 * Returns the modification date of the template.
	 *
	 * @author mqfdy
	 * @return Modification date in milliseconds.
	 * @Date 2018-9-3 11:38:31
	 */
    public long getLastModified()
    {
        return lastModified;
    }

    /**
	 * Sets a new value for the template body.
	 *
	 * @author mqfdy
	 * @param body
	 *            New body value
	 * @Date 2018-9-3 11:38:31
	 */
    public void setBody(final String body)
    {
        this.body = body;
        this.lastModified = System.currentTimeMillis();
    }

    /**
	 * Changes the last modified parameter.
	 *
	 * @author mqfdy
	 * @param lastModified
	 *            The modification time in millis.
	 * @Date 2018-9-3 11:38:31
	 */
    public void setLastModified(final long lastModified)
    {
        this.lastModified = lastModified;
    }

    /**
	 * Returns the encoding of this String resource.
	 *
	 * @author mqfdy
	 * @return The encoding of this String resource.
	 * @Date 2018-9-3 11:38:31
	 */
    public String getEncoding() {
        return this.encoding;
    }

    /**
	 * Sets the encoding of this string resource.
	 *
	 * @author mqfdy
	 * @param encoding
	 *            The new encoding of this resource.
	 * @Date 2018-9-3 11:38:31
	 */
    public void setEncoding(final String encoding)
    {
        this.encoding = encoding;
    }
}
