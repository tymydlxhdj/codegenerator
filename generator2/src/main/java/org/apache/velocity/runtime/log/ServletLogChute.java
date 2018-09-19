package org.apache.velocity.runtime.log;

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

import java.io.StringWriter;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import org.apache.velocity.runtime.RuntimeServices;

// TODO: Auto-generated Javadoc
/**
 * Simple wrapper for the servlet log.  This passes Velocity log
 * messages to ServletContext.log(String).  You may configure the
 * level of output in your velocity.properties by adding the
 * "runtime.log.logsystem.servlet.level" property with one of the
 * following values: error, warn, info, debug, or trace.  The default
 * is trace.
 *
 * @author <a href="mailto:geirm@apache.org">Geir Magnusson Jr.</a>
 * @author Nathan Bubna
 * @version $Revision: 730039 $ $Date: 2008-12-29 19:53:19 -0800 (Mon, 29 Dec 2008) $
 * @since 1.6
 */
public class ServletLogChute implements LogChute
{
    
    /** The Constant RUNTIME_LOG_LEVEL_KEY. */
    public static final String RUNTIME_LOG_LEVEL_KEY = 
        "runtime.log.logsystem.servlet.level";

    /** The enabled. */
    private int enabled = TRACE_ID;

    /** The servlet context. */
    protected ServletContext servletContext = null;

    /** The Constant PREFIX. */
    public static final String PREFIX = " Velocity ";

    /**
     * Construct a simple logger for a servlet environment.
     * <br>
     * NOTE: this class expects that the ServletContext has already
     *       been placed in the runtime's application attributes
     *       under its full class name (i.e. "javax.servlet.ServletContext").
     */
    public ServletLogChute()
    {
    }

    /**
	 * init().
	 *
	 * @author mqfdy
	 * @param rs
	 *            the rs
	 * @throws Exception
	 *             the exception
	 * @throws IllegalStateException
	 *             if the ServletContext is not available in the application
	 *             attributes under the appropriate key.
	 * @Date 2018-9-3 11:38:34
	 */
    public void init(RuntimeServices rs) throws Exception
    {
        Object obj = rs.getApplicationAttribute(ServletContext.class.getName());
        if (obj == null)
        {
            throw new UnsupportedOperationException("Could not retrieve ServletContext from application attributes");
        }
        servletContext = (ServletContext)obj;

        // look for a level config property
        String level = (String)rs.getProperty(RUNTIME_LOG_LEVEL_KEY);
        if (level != null)
        {
            // and set it accordingly
            setEnabledLevel(toLevel(level));
        }
    }

    /**
	 * To level.
	 *
	 * @author mqfdy
	 * @param level
	 *            the level
	 * @return the int
	 * @Date 2018-09-03 09:00
	 */
    protected int toLevel(String level) {
        if (level.equalsIgnoreCase("debug"))
        {
            return DEBUG_ID;
        }
        else if (level.equalsIgnoreCase("info"))
        {
            return INFO_ID;
        }
        else if (level.equalsIgnoreCase("warn"))
        {
            return WARN_ID;
        }
        else if (level.equalsIgnoreCase("error"))
        {
            return ERROR_ID;
        }
        else
        {
            return TRACE_ID;
        }
    }

    /**
	 * Set the minimum level at which messages will be printed.
	 *
	 * @author mqfdy
	 * @param level
	 *            the new enabled level
	 * @Date 2018-09-03 09:00
	 */
    public void setEnabledLevel(int level)
    {
        this.enabled = level;
    }

    /**
	 * Returns the current minimum level at which messages will be printed.
	 *
	 * @author mqfdy
	 * @return the enabled level
	 * @Date 2018-09-03 09:00
	 */
    public int getEnabledLevel()
    {
        return this.enabled;
    }

    /**
	 * This will return true if the specified level is equal to or higher than
	 * the level this LogChute is enabled for.
	 *
	 * @author mqfdy
	 * @param level
	 *            the level
	 * @return true, if is level enabled
	 * @Date 2018-9-3 11:38:34
	 */
    public boolean isLevelEnabled(int level)
    {
        return (level >= this.enabled);
    }

    /**
	 * Send a log message from Velocity.
	 *
	 * @author mqfdy
	 * @param level
	 *            the level
	 * @param message
	 *            the message
	 * @Date 2018-9-3 11:38:34
	 */
    public void log(int level, String message)
    {
        if (!isLevelEnabled(level))
        {
            return;
        }

        switch (level)
        {
            case WARN_ID:
                servletContext.log(PREFIX + WARN_PREFIX + message);
                break;
            case INFO_ID:
                servletContext.log(PREFIX + INFO_PREFIX + message);
                break;
            case DEBUG_ID:
                servletContext.log(PREFIX + DEBUG_PREFIX + message);
                break;
            case TRACE_ID:
                servletContext.log(PREFIX + TRACE_PREFIX + message);
                break;
            case ERROR_ID:
                servletContext.log(PREFIX + ERROR_PREFIX + message);
                break;
            default:
                servletContext.log(PREFIX + " : " + message);
                break;
        }
    }

    /**
     * @see org.apache.velocity.runtime.log.LogChute#log(int, java.lang.String, java.lang.Throwable)
     * @param level
     * @param message
     * @param t ServletLogChute
     */
    public void log(int level, String message, Throwable t)
    {
        if (!isLevelEnabled(level))
        {
            return;
        }

        message += " - "+t.toString();
        if (level >= ERROR_ID)
        {
            StringWriter sw = new StringWriter();
            t.printStackTrace(new PrintWriter(sw));
            message += "\n" + sw.toString();
        }

        log(level, message);
    }

}
