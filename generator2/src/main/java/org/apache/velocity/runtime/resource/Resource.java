package org.apache.velocity.runtime.resource;

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

import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.RuntimeConstants;

import org.apache.velocity.runtime.resource.loader.ResourceLoader;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.exception.ParseErrorException;

// TODO: Auto-generated Javadoc
/**
 * This class represent a general text resource that
 * may have been retrieved from any number of possible
 * sources.
 *
 * @author <a href="mailto:jvanzyl@apache.org">Jason van Zyl</a>
 * @author <a href="mailto:geirm@optonline.net">Geir Magnusson Jr.</a>
 * @version $Id: Resource.java 729843 2008-12-29 09:06:57Z byron $
 */
public abstract class Resource
{
    
    /** The rsvc. */
    protected RuntimeServices rsvc = null;

    /**
     * The template loader that initially loaded the input
     * stream for this template, and knows how to check the
     * source of the input stream for modification.
     */
    protected ResourceLoader resourceLoader;

    /**
     * The number of milliseconds in a minute, used to calculate the
     * check interval.
     */
    protected static final long MILLIS_PER_SECOND =  1000;

    /**
     * How often the file modification time is checked (in seconds).
     */
    protected long modificationCheckInterval = 0;

    /**
     * The file modification time (in milliseconds) for the cached template.
     */
    protected long lastModified = 0;

    /**
     * The next time the file modification time will be checked (in
     * milliseconds).
     */
    protected long nextCheck = 0;

    /** Name of the resource. */
    protected String name;

    /** Character encoding of this resource. */
    protected String encoding = RuntimeConstants.ENCODING_DEFAULT;

    /** Resource might require ancillary storage of some kind. */
    protected Object data = null;

    /** Resource type (RESOURCE_TEMPLATE or RESOURCE_CONTENT). */
    protected int type;

    /**
	 * Default constructor.
	 */
    public Resource()
    {
    }

    /**
	 * Sets the runtime services.
	 *
	 * @author mqfdy
	 * @param rs
	 *            the new runtime services
	 * @Date 2018-09-03 09:00
	 */
    public void setRuntimeServices( RuntimeServices rs )
    {
        rsvc = rs;
    }

    /**
	 * Perform any subsequent processing that might need to be done by a
	 * resource. In the case of a template the actual parsing of the input
	 * stream needs to be performed.
	 *
	 * @author mqfdy
	 * @return Whether the resource could be processed successfully. For a
	 *         {@link org.apache.velocity.Template} or
	 *         {@link org.apache.velocity.runtime.resource.ContentResource},
	 *         this indicates whether the resource could be read.
	 * @exception ResourceNotFoundException
	 *                Similar in semantics as returning <code>false</code>.
	 * @throws ParseErrorException
	 *             the parse error exception
	 * @Date 2018-09-03 09:00
	 */
    public abstract boolean process()
        throws ResourceNotFoundException, ParseErrorException;

    /**
	 * Checks if is source modified.
	 *
	 * @author mqfdy
	 * @return True if source has been modified.
	 * @Date 2018-09-03 09:00
	 */
    public boolean isSourceModified()
    {
        return resourceLoader.isSourceModified(this);
    }

    /**
	 * Set the modification check interval.
	 *
	 * @author mqfdy
	 * @param modificationCheckInterval
	 *            The interval (in seconds).
	 * @Date 2018-9-3 11:38:32
	 */
    public void setModificationCheckInterval(long modificationCheckInterval)
    {
        this.modificationCheckInterval = modificationCheckInterval;
    }

    /**
	 * Is it time to check to see if the resource source has been updated?.
	 *
	 * @author mqfdy
	 * @return True if resource must be checked.
	 * @Date 2018-9-3 11:38:32
	 */
    public boolean requiresChecking()
    {
        /*
         *  short circuit this if modificationCheckInterval == 0
         *  as this means "don't check"
         */

        if (modificationCheckInterval <= 0 )
        {
           return false;
        }

        /*
         *  see if we need to check now
         */

        return ( System.currentTimeMillis() >= nextCheck );
    }

    /**
	 * 'Touch' this template and thereby resetting the nextCheck field.
	 *
	 * @author mqfdy
	 * @Date 2018-9-3 11:38:32
	 */
    public void touch()
    {
        nextCheck = System.currentTimeMillis() + ( MILLIS_PER_SECOND *  modificationCheckInterval);
    }

    /**
	 * Set the name of this resource, for example test.vm.
	 *
	 * @author mqfdy
	 * @param name
	 *            the new name
	 * @Date 2018-09-03 09:00
	 */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
	 * Get the name of this template.
	 *
	 * @author mqfdy
	 * @return The name of this template.
	 * @Date 2018-9-3 11:38:32
	 */
    public String getName()
    {
        return name;
    }

    /**
	 * set the encoding of this resource for example, "ISO-8859-1".
	 *
	 * @author mqfdy
	 * @param encoding
	 *            the new encoding
	 * @Date 2018-09-03 09:00
	 */
    public void setEncoding( String encoding )
    {
        this.encoding = encoding;
    }

    /**
	 * get the encoding of this resource for example, "ISO-8859-1".
	 *
	 * @author mqfdy
	 * @return The encoding of this resource.
	 * @Date 2018-9-3 11:38:32
	 */
    public String getEncoding()
    {
        return encoding;
    }


    /**
	 * Return the lastModifed time of this resource.
	 *
	 * @author mqfdy
	 * @return The lastModifed time of this resource.
	 * @Date 2018-9-3 11:38:32
	 */
    public long getLastModified()
    {
        return lastModified;
    }

    /**
	 * Set the last modified time for this resource.
	 *
	 * @author mqfdy
	 * @param lastModified
	 *            the new last modified
	 * @Date 2018-09-03 09:00
	 */
    public void setLastModified(long lastModified)
    {
        this.lastModified = lastModified;
    }

    /**
	 * Return the template loader that pulled in the template stream.
	 *
	 * @author mqfdy
	 * @return The resource loader for this resource.
	 * @Date 2018-9-3 11:38:32
	 */
    public ResourceLoader getResourceLoader()
    {
        return resourceLoader;
    }

    /**
	 * Set the template loader for this template. Set when the Runtime
	 * determines where this template came from the list of possible sources.
	 *
	 * @author mqfdy
	 * @param resourceLoader
	 *            the new resource loader
	 * @Date 2018-09-03 09:00
	 */
    public void setResourceLoader(ResourceLoader resourceLoader)
    {
        this.resourceLoader = resourceLoader;
    }

    /**
	 * Set arbitrary data object that might be used by the resource.
	 *
	 * @author mqfdy
	 * @param data
	 *            the new data
	 * @Date 2018-09-03 09:00
	 */
    public void setData(Object data)
    {
        this.data = data;
    }

    /**
	 * Get arbitrary data object that might be used by the resource.
	 *
	 * @author mqfdy
	 * @return The data object for this resource.
	 * @Date 2018-9-3 11:38:32
	 */
    public Object getData()
    {
        return data;
    }
    
    /**
	 * Sets the type of this Resource (RESOURCE_TEMPLATE or RESOURCE_CONTENT).
	 *
	 * @param type
	 *            the new type
	 * @since 1.6
	 */
    public void setType(int type)
    {
        this.type = type;
    }
    
    /**
	 * Gets the type.
	 *
	 * @return type code of the Resource
	 * @since 1.6
	 */
    public int getType()
    {
        return type;
    }
}
