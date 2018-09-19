package org.apache.velocity.app.event.implement;

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

import org.apache.oro.text.perl.MalformedPerl5PatternException;
import org.apache.oro.text.perl.Perl5Util;
import org.apache.velocity.app.event.ReferenceInsertionEventHandler;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.util.RuntimeServicesAware;
import org.apache.velocity.util.StringUtils;

// TODO: Auto-generated Javadoc
/**
 * Base class for escaping references.  To use it, override the following methods:
 * <DL>
 * <DT><code>String escape(String text)</code></DT>
 * <DD>escape the provided text</DD>
 * <DT><code>String getMatchAttribute()</code></DT>
 * <DD>retrieve the configuration attribute used to match references (see below)</DD>
 * </DL>
 *
 * <P>By default, all references are escaped.  However, by setting the match attribute
 * in the configuration file to a regular expression, users can specify which references
 * to escape.  For example the following configuration property tells the EscapeSqlReference
 * event handler to only escape references that start with "sql".
 * (e.g. <code>$sql</code>, <code>$sql.toString(),</code>, etc).
 *
 * <PRE>
 * <CODE>eventhandler.escape.sql.match = /sql.*<!-- -->/
 * </CODE>
 * </PRE>
 * <!-- note: ignore empty HTML comment above - breaks up star slash avoiding javadoc end -->
 *
 * Regular expressions should follow the "Perl5" format used by the ORO regular expression
 * library.  More info is at
 * <a href="http://jakarta.apache.org/oro/api/org/apache/oro/text/perl/package-summary.html">http://jakarta.apache.org/oro/api/org/apache/oro/text/perl/package-summary.html</a>.
 *
 * @author <a href="mailto:wglass@forio.com">Will Glass-Husain </a>
 * @version $Id: EscapeReference.java 685685 2008-08-13 21:43:27Z nbubna $
 * @since 1.5
 */
public abstract class EscapeReference implements ReferenceInsertionEventHandler,RuntimeServicesAware {


    /** The perl. */
    private Perl5Util perl = new Perl5Util();

    /** The rs. */
    private RuntimeServices rs;

    /** The match reg exp. */
    private String matchRegExp = null;

    /**
	 * Escape the given text. Override this in a subclass to do the actual
	 * escaping.
	 *
	 * @author mqfdy
	 * @param text
	 *            the text to escape
	 * @return the escaped text
	 * @Date 2018-9-3 11:38:28
	 */
    protected abstract String escape(Object text);

    /**
	 * Specify the configuration attribute that specifies the regular
	 * expression. Ideally should be in a form
	 * 
	 * <pre>
	 * <code>eventhandler.escape.XYZ.match</code>
	 * </pre>
	 * 
	 * <p>
	 * where <code>XYZ</code> is the type of escaping being done.
	 *
	 * @author mqfdy
	 * @return configuration attribute
	 * @Date 2018-9-3 11:38:28
	 */
    protected abstract String getMatchAttribute();

    /**
	 * Escape the provided text if it matches the configured regular expression.
	 *
	 * @author mqfdy
	 * @param reference
	 *            the reference
	 * @param value
	 *            the value
	 * @return Escaped text.
	 * @Date 2018-9-3 11:38:28
	 */
    public Object referenceInsert(String reference, Object value)
    {
        if(value == null)
        {
            return value;
        }

        if (matchRegExp == null)
        {
            return escape(value);
        }

        else if (perl.match(matchRegExp,reference))
        {
            return escape(value);
        }

        else
        {
            return value;
        }
    }

    /**
	 * Called automatically when event cartridge is initialized.
	 *
	 * @author mqfdy
	 * @param rs
	 *            instance of RuntimeServices
	 * @Date 2018-9-3 11:38:28
	 */
    public void setRuntimeServices(RuntimeServices rs)
    {
        this.rs = rs;

        /**
         * Get the regular expression pattern.
         */
        matchRegExp = StringUtils.nullTrim(rs.getConfiguration().getString(getMatchAttribute()));
        if ((matchRegExp != null) && (matchRegExp.length() == 0))
        {
            matchRegExp = null;
        }

        /**
         * Test the regular expression for a well formed pattern
         */
        if (matchRegExp != null)
        {
            try
            {
                perl.match(matchRegExp,"");
            }
            catch (MalformedPerl5PatternException E)
            {
                rs.getLog().error("Invalid regular expression '" + matchRegExp
                                  + "'.  No escaping will be performed.", E);
                matchRegExp = null;
            }
        }

    }

    /**
	 * Retrieve a reference to RuntimeServices. Use this for checking additional
	 * configuration properties.
	 *
	 * @author mqfdy
	 * @return The current runtime services object.
	 * @Date 2018-9-3 11:38:28
	 */
    protected RuntimeServices getRuntimeServices()
    {
        return rs;
    }

}
