package org.apache.velocity.runtime.log;

import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.util.introspection.Info;

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
 * Convenient wrapper for LogChute functions. This implements
 * the RuntimeLogger methods (and then some).  It is hoped that
 * use of this will fully replace use of the RuntimeLogger.
 *
 * @author <a href="mailto:nbubna@apache.org">Nathan Bubna</a>
 * @version $Id: Log.java 724804 2008-12-09 18:17:08Z nbubna $
 * @since 1.5
 */
public class Log
{

    /** The chute. */
    private LogChute chute;

    /**
     * Creates a new Log that wraps a HoldingLogChute.
     */
    public Log()
    {
        setLogChute(new HoldingLogChute());
    }

    /**
	 * Creates a new Log that wraps the specified LogChute.
	 *
	 * @param chute
	 *            the chute
	 */
    public Log(final LogChute chute)
    {
        setLogChute(chute);
    }

    /**
	 * Updates the LogChute wrapped by this Log instance.
	 *
	 * @author mqfdy
	 * @param chute
	 *            The new value for the log chute.
	 * @Date 2018-9-3 11:38:37
	 */
    protected void setLogChute(final LogChute chute)
    {
        if (chute == null)
        {
            throw new NullPointerException("The LogChute cannot be set to null!");
        }
        this.chute = chute;
    }

    /**
	 * Returns the LogChute wrapped by this Log instance.
	 *
	 * @author mqfdy
	 * @return The LogChute wrapped by this Log instance.
	 * @Date 2018-9-3 11:38:37
	 */
    protected LogChute getLogChute()
    {
        return this.chute;
    }

    /**
	 * Log.
	 *
	 * @author mqfdy
	 * @param level
	 *            the level
	 * @param message
	 *            the message
	 * @Date 2018-09-03 09:00
	 */
    protected void log(int level, Object message)
    {
        getLogChute().log(level, String.valueOf(message));
    }

    /**
	 * Log.
	 *
	 * @author mqfdy
	 * @param level
	 *            the level
	 * @param message
	 *            the message
	 * @param t
	 *            the t
	 * @Date 2018-09-03 09:00
	 */
    protected void log(int level, Object message, Throwable t)
    {
        getLogChute().log(level, String.valueOf(message), t);
    }

    /**
	 * Returns true if trace level messages will be printed by the LogChute.
	 *
	 * @author mqfdy
	 * @return If trace level messages will be printed by the LogChute.
	 * @Date 2018-9-3 11:38:37
	 */
    public boolean isTraceEnabled()
    {
        return getLogChute().isLevelEnabled(LogChute.TRACE_ID);
    }

    /**
	 * Log a trace message.
	 *
	 * @author mqfdy
	 * @param message
	 *            the message
	 * @Date 2018-09-03 09:00
	 */
    public void trace(Object message)
    {
        log(LogChute.TRACE_ID, message);
    }

    /**
	 * Log a trace message and accompanying Throwable.
	 *
	 * @author mqfdy
	 * @param message
	 *            the message
	 * @param t
	 *            the t
	 * @Date 2018-09-03 09:00
	 */
    public void trace(Object message, Throwable t)
    {
        log(LogChute.TRACE_ID, message, t);
    }

    /**
	 * Returns true if debug level messages will be printed by the LogChute.
	 *
	 * @author mqfdy
	 * @return True if debug level messages will be printed by the LogChute.
	 * @Date 2018-9-3 11:38:37
	 */
    public boolean isDebugEnabled()
    {
        return getLogChute().isLevelEnabled(LogChute.DEBUG_ID);
    }

    /**
	 * Log a debug message.
	 *
	 * @author mqfdy
	 * @param message
	 *            the message
	 * @Date 2018-09-03 09:00
	 */
    public void debug(Object message)
    {
        log(LogChute.DEBUG_ID, message);
    }

    /**
	 * Log a debug message and accompanying Throwable.
	 *
	 * @author mqfdy
	 * @param message
	 *            the message
	 * @param t
	 *            the t
	 * @Date 2018-09-03 09:00
	 */
    public void debug(Object message, Throwable t)
    {
        log(LogChute.DEBUG_ID, message, t);
    }

    /**
	 * Returns true if info level messages will be printed by the LogChute.
	 *
	 * @author mqfdy
	 * @return True if info level messages will be printed by the LogChute.
	 * @Date 2018-9-3 11:38:37
	 */
    public boolean isInfoEnabled()
    {
        return getLogChute().isLevelEnabled(LogChute.INFO_ID);
    }

    /**
	 * Log an info message.
	 *
	 * @author mqfdy
	 * @param message
	 *            the message
	 * @Date 2018-09-03 09:00
	 */
    public void info(Object message)
    {
        log(LogChute.INFO_ID, message);
    }

    /**
	 * Log an info message and accompanying Throwable.
	 *
	 * @author mqfdy
	 * @param message
	 *            the message
	 * @param t
	 *            the t
	 * @Date 2018-09-03 09:00
	 */
    public void info(Object message, Throwable t)
    {
        log(LogChute.INFO_ID, message, t);
    }

    /**
	 * Returns true if warn level messages will be printed by the LogChute.
	 *
	 * @author mqfdy
	 * @return True if warn level messages will be printed by the LogChute.
	 * @Date 2018-9-3 11:38:37
	 */
    public boolean isWarnEnabled()
    {
        return getLogChute().isLevelEnabled(LogChute.WARN_ID);
    }

    /**
	 * Log a warning message.
	 *
	 * @author mqfdy
	 * @param message
	 *            the message
	 * @Date 2018-09-03 09:00
	 */
    public void warn(Object message)
    {
        log(LogChute.WARN_ID, message);
    }

    /**
	 * Log a warning message and accompanying Throwable.
	 *
	 * @author mqfdy
	 * @param message
	 *            the message
	 * @param t
	 *            the t
	 * @Date 2018-09-03 09:00
	 */
    public void warn(Object message, Throwable t)
    {
        log(LogChute.WARN_ID, message, t);
    }

    /**
	 * Returns true if error level messages will be printed by the LogChute.
	 *
	 * @author mqfdy
	 * @return True if error level messages will be printed by the LogChute.
	 * @Date 2018-9-3 11:38:37
	 */
    public boolean isErrorEnabled()
    {
        return getLogChute().isLevelEnabled(LogChute.ERROR_ID);
    }

    /**
	 * Log an error message.
	 *
	 * @author mqfdy
	 * @param message
	 *            the message
	 * @Date 2018-09-03 09:00
	 */
    public void error(Object message)
    {
        log(LogChute.ERROR_ID, message);
    }

    /**
	 * Log an error message and accompanying Throwable.
	 *
	 * @author mqfdy
	 * @param message
	 *            the message
	 * @param t
	 *            the t
	 * @Date 2018-09-03 09:00
	 */
    public void error(Object message, Throwable t)
    {
        log(LogChute.ERROR_ID, message, t);
    }
    
    /**
	 * Creates a string that formats the template filename with line number and
	 * column of the given Directive. We use this routine to provide a cosistent
	 * format for displaying file errors.
	 *
	 * @author mqfdy
	 * @param directive
	 *            the directive
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
    public static final String formatFileString(Directive directive)
    {
      return formatFileString(directive.getTemplateName(), directive.getLine(), directive.getColumn());      
    }

    /**
	 * Creates a string that formats the template filename with line number and
	 * column of the given Node. We use this routine to provide a cosistent
	 * format for displaying file errors.
	 *
	 * @author mqfdy
	 * @param node
	 *            the node
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
    public static final String formatFileString(Node node)
    {
      return formatFileString(node.getTemplateName(), node.getLine(), node.getColumn());      
    }
    
    /**
	 * Simply creates a string that formats the template filename with line
	 * number and column. We use this routine to provide a cosistent format for
	 * displaying file errors.
	 *
	 * @author mqfdy
	 * @param info
	 *            the info
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
    public static final String formatFileString(Info info)
    {
        return formatFileString(info.getTemplateName(), info.getLine(), info.getColumn());
    }
    
    /**
	 * Simply creates a string that formats the template filename with line
	 * number and column. We use this routine to provide a cosistent format for
	 * displaying file errors.
	 *
	 * @author mqfdy
	 * @param template
	 *            File name of template, can be null
	 * @param linenum
	 *            Line number within the file
	 * @param colnum
	 *            Column number withing the file at linenum
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
    public static final String formatFileString(String template, int linenum, int colnum)
    {
        if (template == null || template.equals(""))
        {
            template = "<unknown template>";
        }
        return template + "[line " + linenum + ", column " + colnum + "]";
    }
}
