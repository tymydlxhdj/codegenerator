package org.apache.velocity.runtime.directive;

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

import java.io.IOException;
import java.io.Writer;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.TemplateInitException;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.parser.node.Node;

// TODO: Auto-generated Javadoc
/**
 * A very simple directive that leverages the Node.literal()
 * to grab the literal rendition of a node. We basically
 * grab the literal value on init(), then repeatedly use
 * that during render().  <em>This is deprecated and will be
 * removed in Velocity 2.0; please use #[[unparsed content]]#
 * instead.</em>
 *
 * @author <a href="mailto:jvanzyl@apache.org">Jason van Zyl</a>
 * @version $Id: Literal.java 746438 2009-02-21 05:41:24Z nbubna $
 * @deprecated Use the #[[unparsed content]]# syntax instead.
 */
public class Literal extends Directive
{
    
    /** The literal text. */
    String literalText;

    /**
	 * Return name of this directive.
	 *
	 * @author mqfdy
	 * @return The name of this directive.
	 * @Date 2018-9-3 11:38:39
	 */
    public String getName()
    {
        return "literal";
    }

    /**
	 * Return type of this directive.
	 *
	 * @author mqfdy
	 * @return The type of this directive.
	 * @Date 2018-9-3 11:38:39
	 */
    public int getType()
    {
        return BLOCK;
    }

    /**
	 * Since there is no processing of content, there is never a need for an
	 * internal scope.
	 *
	 * @author mqfdy
	 * @return true, if is scope provided
	 * @Date 2018-9-3 11:38:39
	 */
    public boolean isScopeProvided()
    {
        return false;
    }

    /**
	 * Store the literal rendition of a node using the Node.literal().
	 *
	 * @author mqfdy
	 * @param rs
	 *            the rs
	 * @param context
	 *            the context
	 * @param node
	 *            the node
	 * @throws TemplateInitException
	 *             the template init exception
	 * @Date 2018-9-3 11:38:39
	 */
    public void init(RuntimeServices rs, InternalContextAdapter context,
                     Node node)
        throws TemplateInitException
    {
        super.init( rs, context, node );

        literalText = node.jjtGetChild(0).literal();
    }

    /**
	 * Throw the literal rendition of the block between #literal()/#end into the
	 * writer.
	 *
	 * @author mqfdy
	 * @param context
	 *            the context
	 * @param writer
	 *            the writer
	 * @param node
	 *            the node
	 * @return True if the directive rendered successfully.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @Date 2018-9-3 11:38:39
	 */
    public boolean render( InternalContextAdapter context,
                           Writer writer, Node node)
        throws IOException
    {
        writer.write(literalText);
        return true;
    }
}
