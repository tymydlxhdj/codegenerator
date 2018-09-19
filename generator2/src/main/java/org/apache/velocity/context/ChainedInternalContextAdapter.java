package org.apache.velocity.context;

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

import java.util.List;

import org.apache.velocity.app.event.EventCartridge;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.util.introspection.IntrospectionCacheData;

// TODO: Auto-generated Javadoc
/**
 * This is an abstract internal-use-only context implementation to be
 * used as a subclass for other internal-use-only contexts that wrap
 * other internal-use-only contexts.
 *
 * We use this context to make it easier to chain an existing context
 * as part of a new context implementation.  It just delegates everything
 * to the inner/parent context. Subclasses then only need to override
 * the methods relevant to them.
 *
 * @author Nathan Bubna
 * @version $Id: ChainedInternalContextAdapter.java 685724 2008-08-13 23:12:12Z nbubna $
 * @since 1.6
 */
public abstract class ChainedInternalContextAdapter implements InternalContextAdapter
{
    
    /** the parent context. */
    protected InternalContextAdapter innerContext = null;
    
    /**
	 * CTOR, wraps an ICA.
	 *
	 * @param inner
	 *            context
	 */
    public ChainedInternalContextAdapter(InternalContextAdapter inner)
    {
        innerContext = inner;
    }
    
    /**
	 * Return the inner / user context.
	 *
	 * @author mqfdy
	 * @return The inner / user context.
	 * @Date 2018-9-3 11:38:29
	 */
    public Context getInternalUserContext()
    {
        return innerContext.getInternalUserContext();
    }

    /**
	 * Gets the base context.
	 *
	 * @return the base context
	 * @see org.apache.velocity.context.InternalWrapperContext#getBaseContext()
	 */
    public InternalContextAdapter getBaseContext()
    {
        return innerContext.getBaseContext();
    }

    /**
	 * Retrieves from parent context.
	 *
	 * @author mqfdy
	 * @param key
	 *            name of item to get
	 * @return stored object or null
	 * @Date 2018-9-3 11:38:29
	 */
    public Object get(String key)
    {
        return innerContext.get(key);
    }

    /**
	 * Put method also stores values in parent context.
	 *
	 * @author mqfdy
	 * @param key
	 *            name of item to set
	 * @param value
	 *            object to set to key
	 * @return old stored object
	 * @Date 2018-9-3 11:38:29
	 */
    public Object put(String key, Object value)
    {
        /*
         * just put in the local context
         */
        return innerContext.put(key, value);
    }

    /**
	 * Contains key.
	 *
	 * @param key
	 *            the key
	 * @return true, if successful
	 * @see org.apache.velocity.context.Context#containsKey(java.lang.Object)
	 */
    public boolean containsKey(Object key)
    {
        return innerContext.containsKey(key);
    }

    /**
	 * Gets the keys.
	 *
	 * @return the keys
	 * @see org.apache.velocity.context.Context#getKeys()
	 */
    public Object[] getKeys()
    {
        return innerContext.getKeys();
    }

    /**
	 * Removes the.
	 *
	 * @param key
	 *            the key
	 * @return the object
	 * @see org.apache.velocity.context.Context#remove(java.lang.Object)
	 */
    public Object remove(Object key)
    {
        return innerContext.remove(key);
    }

    /**
	 * Push current template name.
	 *
	 * @param s
	 *            the s
	 * @see org.apache.velocity.context.InternalHousekeepingContext#pushCurrentTemplateName(java.lang.String)
	 */
    public void pushCurrentTemplateName(String s)
    {
        innerContext.pushCurrentTemplateName(s);
    }

    /**
	 * Pop current template name.
	 *
	 * @see org.apache.velocity.context.InternalHousekeepingContext#popCurrentTemplateName()
	 */
    public void popCurrentTemplateName()
    {
        innerContext.popCurrentTemplateName();
    }

    /**
	 * Gets the current template name.
	 *
	 * @return the current template name
	 * @see org.apache.velocity.context.InternalHousekeepingContext#getCurrentTemplateName()
	 */
    public String getCurrentTemplateName()
    {
        return innerContext.getCurrentTemplateName();
    }

    /**
	 * Gets the template name stack.
	 *
	 * @return the template name stack
	 * @see org.apache.velocity.context.InternalHousekeepingContext#getTemplateNameStack()
	 */
    public Object[] getTemplateNameStack()
    {
        return innerContext.getTemplateNameStack();
    }

    /**
	 * Push current macro name.
	 *
	 * @param s
	 *            the s
	 * @see org.apache.velocity.context.InternalHousekeepingContext#pushCurrentMacroName(java.lang.String)
	 */
    public void pushCurrentMacroName(String s)
    {
        innerContext.pushCurrentMacroName(s);
    }

    /**
	 * Pop current macro name.
	 *
	 * @see org.apache.velocity.context.InternalHousekeepingContext#popCurrentMacroName()
	 */
    public void popCurrentMacroName()
    {
        innerContext.popCurrentMacroName();
    }

    /**
	 * Gets the current macro name.
	 *
	 * @return the current macro name
	 * @see org.apache.velocity.context.InternalHousekeepingContext#getCurrentMacroName()
	 */
    public String getCurrentMacroName()
    {
        return innerContext.getCurrentMacroName();
    }

    /**
	 * Gets the current macro call depth.
	 *
	 * @return the current macro call depth
	 * @see org.apache.velocity.context.InternalHousekeepingContext#getCurrentMacroCallDepth()
	 */
    public int getCurrentMacroCallDepth()
    {
        return innerContext.getCurrentMacroCallDepth();
    }

    /**
	 * Gets the macro name stack.
	 *
	 * @return the macro name stack
	 * @see org.apache.velocity.context.InternalHousekeepingContext#getMacroNameStack()
	 */
    public Object[] getMacroNameStack()
    {
        return innerContext.getMacroNameStack();
    }

    /**
	 * Icache get.
	 *
	 * @param key
	 *            the key
	 * @return the introspection cache data
	 * @see org.apache.velocity.context.InternalHousekeepingContext#icacheGet(java.lang.Object)
	 */
    public IntrospectionCacheData icacheGet(Object key)
    {
        return innerContext.icacheGet(key);
    }

    /**
	 * Local put.
	 *
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 * @return the object
	 * @see org.apache.velocity.context.InternalWrapperContext#localPut(java.lang.String,java.lang.Object)
	 */
    public Object localPut(final String key, final Object value)
    {
        return innerContext.put(key, value);
    }

    /**
	 * Icache put.
	 *
	 * @param key
	 *            the key
	 * @param o
	 *            the o
	 * @see org.apache.velocity.context.InternalHousekeepingContext#icachePut(java.lang.Object,
	 *      org.apache.velocity.util.introspection.IntrospectionCacheData)
	 */
    public void icachePut(Object key, IntrospectionCacheData o)
    {
        innerContext.icachePut(key, o);
    }

    /**
	 * Sets the macro libraries.
	 *
	 * @param macroLibraries
	 *            the new macro libraries
	 * @see org.apache.velocity.context.InternalHousekeepingContext#setMacroLibraries(List)
	 */
    public void setMacroLibraries(List macroLibraries)
    {
        innerContext.setMacroLibraries(macroLibraries);
    }
    
    /**
	 * Gets the macro libraries.
	 *
	 * @return the macro libraries
	 * @see org.apache.velocity.context.InternalHousekeepingContext#getMacroLibraries()
	 */
    public List getMacroLibraries()
    {
        return innerContext.getMacroLibraries();
    }

    /**
	 * Attach event cartridge.
	 *
	 * @param ec
	 *            the ec
	 * @return the event cartridge
	 * @see org.apache.velocity.context.InternalEventContext#attachEventCartridge(org.apache.velocity.app.event.EventCartridge)
	 */
    public EventCartridge attachEventCartridge(EventCartridge ec)
    {
        return innerContext.attachEventCartridge(ec);
    }

    /**
	 * Gets the event cartridge.
	 *
	 * @return the event cartridge
	 * @see org.apache.velocity.context.InternalEventContext#getEventCartridge()
	 */
    public EventCartridge getEventCartridge()
    {
        return innerContext.getEventCartridge();
    }


    /**
	 * Sets the current resource.
	 *
	 * @param r
	 *            the new current resource
	 * @see org.apache.velocity.context.InternalHousekeepingContext#setCurrentResource(org.apache.velocity.runtime.resource.Resource)
	 */
    public void setCurrentResource(Resource r)
    {
        innerContext.setCurrentResource(r);
    }

    /**
	 * Gets the current resource.
	 *
	 * @return the current resource
	 * @see org.apache.velocity.context.InternalHousekeepingContext#getCurrentResource()
	 */
    public Resource getCurrentResource()
    {
        return innerContext.getCurrentResource();
    }
}
