package org.apache.velocity.runtime.parser.node;

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
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.exception.TemplateInitException;
import org.apache.velocity.runtime.Renderable;
import org.apache.velocity.runtime.parser.Token;

// TODO: Auto-generated Javadoc
/**
 *  This file describes the interface between the Velocity code
 *  and the JavaCC generated code.
 *
 * @author <a href="mailto:hps@intermeta.de">Henning P. Schmiedehausen</a>
 * @version $Id: Node.java 737539 2009-01-25 17:24:29Z nbubna $
 */

public interface Node extends Renderable
{
    
    /**
	 * This method is called after the node has been made the current node. It
	 * indicates that child nodes can now be added to it.
	 *
	 * @author mqfdy
	 * @Date 2018-9-3 11:38:33
	 */
    public void jjtOpen();

    /**
	 * This method is called after all the child nodes have been added.
	 *
	 * @author mqfdy
	 * @Date 2018-9-3 11:38:33
	 */
    public void jjtClose();

    /**
	 * This pair of methods are used to inform the node of its parent.
	 *
	 * @author mqfdy
	 * @param n
	 *            the n
	 * @Date 2018-09-03 09:00
	 */
    public void jjtSetParent(Node n);

    /**
	 * Jjt get parent.
	 *
	 * @author mqfdy
	 * @return The node parent.
	 * @Date 2018-09-03 09:00
	 */
    public Node jjtGetParent();

    /**
	 * This method tells the node to add its argument to the node's list of
	 * children.
	 *
	 * @author mqfdy
	 * @param n
	 *            the n
	 * @param i
	 *            the i
	 * @Date 2018-09-03 09:00
	 */
    public void jjtAddChild(Node n, int i);

    /**
	 * This method returns a child node. The children are numbered from zero,
	 * left to right.
	 *
	 * @author mqfdy
	 * @param i
	 *            the i
	 * @return A child node.
	 * @Date 2018-09-03 09:00
	 */
    public Node jjtGetChild(int i);

    /**
	 * Return the number of children the node has.
	 *
	 * @author mqfdy
	 * @return The number of children of this node.
	 * @Date 2018-9-3 11:38:33
	 */
    public int jjtGetNumChildren();

    /**
	 * Jjt accept.
	 *
	 * @author mqfdy
	 * @param visitor
	 *            the visitor
	 * @param data
	 *            the data
	 * @return The Node execution result object.
	 * @Date 2018-09-03 09:00
	 */
    public Object jjtAccept(ParserVisitor visitor, Object data);

    /*
     * ========================================================================
     *
     * The following methods are not generated automatically be the Parser but
     * added manually to be used by Velocity.
     *
     * ========================================================================
     */

    /**
	 * Children accept.
	 *
	 * @param visitor
	 *            the visitor
	 * @param data
	 *            the data
	 * @return The node execution result.
	 * @see #jjtAccept(ParserVisitor, Object)
	 */
    public Object childrenAccept(ParserVisitor visitor, Object data);

    /**
	 * Gets the first token.
	 *
	 * @author mqfdy
	 * @return The first token.
	 * @Date 2018-09-03 09:00
	 */
    public Token getFirstToken();
    
    /**
	 * Gets the last token.
	 *
	 * @author mqfdy
	 * @return The last token.
	 * @Date 2018-09-03 09:00
	 */
    public Token getLastToken();
    
    /**
	 * Gets the type.
	 *
	 * @author mqfdy
	 * @return The NodeType.
	 * @Date 2018-09-03 09:00
	 */
    public int getType();

    /**
	 * Inits the.
	 *
	 * @author mqfdy
	 * @param context
	 *            the context
	 * @param data
	 *            the data
	 * @return The init result.
	 * @throws TemplateInitException
	 *             the template init exception
	 * @Date 2018-09-03 09:00
	 */
    public Object init( InternalContextAdapter context, Object data) throws TemplateInitException;

    /**
	 * Evaluate.
	 *
	 * @author mqfdy
	 * @param context
	 *            the context
	 * @return The evaluation result.
	 * @throws MethodInvocationException
	 *             the method invocation exception
	 * @Date 2018-09-03 09:00
	 */
    public boolean evaluate( InternalContextAdapter context)
        throws MethodInvocationException;

    /**
	 * Value.
	 *
	 * @author mqfdy
	 * @param context
	 *            the context
	 * @return The node value.
	 * @throws MethodInvocationException
	 *             the method invocation exception
	 * @Date 2018-09-03 09:00
	 */
    public Object value( InternalContextAdapter context)
        throws MethodInvocationException;

    /**
	 * Render.
	 *
	 * @author mqfdy
	 * @param context
	 *            the context
	 * @param writer
	 *            the writer
	 * @return True if the node rendered successfully.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws MethodInvocationException
	 *             the method invocation exception
	 * @throws ParseErrorException
	 *             the parse error exception
	 * @throws ResourceNotFoundException
	 *             the resource not found exception
	 * @Date 2018-9-3 11:38:33
	 */
    public boolean render( InternalContextAdapter context, Writer writer)
        throws IOException,MethodInvocationException, ParseErrorException, ResourceNotFoundException;

    /**
	 * Execute.
	 *
	 * @author mqfdy
	 * @param o
	 *            the o
	 * @param context
	 *            the context
	 * @return The execution result.
	 * @throws MethodInvocationException
	 *             the method invocation exception
	 * @Date 2018-09-03 09:00
	 */
    public Object execute(Object o, InternalContextAdapter context)
      throws MethodInvocationException;

    /**
	 * Sets the info.
	 *
	 * @author mqfdy
	 * @param info
	 *            the new info
	 * @Date 2018-09-03 09:00
	 */
    public void setInfo(int info);

    /**
	 * Gets the info.
	 *
	 * @author mqfdy
	 * @return The current node info.
	 * @Date 2018-09-03 09:00
	 */
    public int getInfo();

    /**
	 * Literal.
	 *
	 * @author mqfdy
	 * @return A literal.
	 * @Date 2018-09-03 09:00
	 */
    public String literal();

    /**
	 * Mark the node as invalid.
	 *
	 * @author mqfdy
	 * @Date 2018-9-3 11:38:33
	 */
    public void setInvalid();

    /**
	 * Checks if is invalid.
	 *
	 * @author mqfdy
	 * @return True if the node is invalid.
	 * @Date 2018-09-03 09:00
	 */
    public boolean isInvalid();

    /**
	 * Gets the line.
	 *
	 * @author mqfdy
	 * @return The current line position.
	 * @Date 2018-09-03 09:00
	 */
    public int getLine();

    /**
	 * Gets the column.
	 *
	 * @author mqfdy
	 * @return The current column position.
	 * @Date 2018-09-03 09:00
	 */
    public int getColumn();
    
    /**
	 * Gets the template name.
	 *
	 * @author mqfdy
	 * @return the file name of the template
	 * @Date 2018-09-03 09:00
	 */
    public String getTemplateName();
}
