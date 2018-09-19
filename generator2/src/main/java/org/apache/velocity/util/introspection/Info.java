package org.apache.velocity.util.introspection;

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

import org.apache.velocity.runtime.log.Log;
import org.apache.velocity.runtime.parser.node.Node;

// TODO: Auto-generated Javadoc
/**
 * Little class to carry in info such as template name, line and column for
 * information error reporting from the uberspector implementations.
 *
 * @author <a href="mailto:geirm@optonline.net">Geir Magnusson Jr.</a>
 * @version $Id: Info.java 733416 2009-01-11 05:26:52Z byron $
 */
public class Info
{
    
    /** The line. */
    private int line;
    
    /** The column. */
    private int column;
    
    /** The template name. */
    private String templateName;

    /**
	 * Instantiates a new info.
	 *
	 * @param source
	 *            Usually a template name.
	 * @param line
	 *            The line number from <code>source</code>.
	 * @param column
	 *            The column number from <code>source</code>.
	 */
    public Info(String source, int line, int column)
    {
        this.templateName = source;
        this.line = line;
        this.column = column;
    }

    /**
	 * Instantiates a new info.
	 *
	 * @param node
	 *            the node
	 */
    public Info(Node node)
    {
      this(node.getTemplateName(), node.getLine(), node.getColumn());
    }
    
    /**
     * Force callers to set the location information.
     */
    private Info()
    {
    }
    
    /**
	 * Gets the template name.
	 *
	 * @author mqfdy
	 * @return The template name.
	 * @Date 2018-9-3 11:38:35
	 */
    public String getTemplateName()
    {
        return templateName;
    }

    /**
	 * Gets the line.
	 *
	 * @author mqfdy
	 * @return The line number.
	 * @Date 2018-9-3 11:38:35
	 */
    public int getLine()
    {
        return line;
    }

    /**
	 * Gets the column.
	 *
	 * @author mqfdy
	 * @return The column number.
	 * @Date 2018-9-3 11:38:35
	 */
    public int getColumn()
    {
        return column;
    }

    /**
     * Formats a textual representation of this object as <code>SOURCE
     * [line X, column Y]</code>.
     *
     * @return String representing this object.
     * @since 1.5
     */
    public String toString()
    {
        return Log.formatFileString(getTemplateName(), getLine(), getColumn());
    }
}
