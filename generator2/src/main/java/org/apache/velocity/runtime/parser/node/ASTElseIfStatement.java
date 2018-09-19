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
import org.apache.velocity.runtime.parser.Parser;

// TODO: Auto-generated Javadoc
/**
 * This class is responsible for handling the ElseIf VTL control statement.
 *
 * Please look at the Parser.jjt file which is
 * what controls the generation of this class.
 *
 * @author <a href="mailto:jvanzyl@apache.org">Jason van Zyl</a>
 * @author <a href="mailto:geirm@optonline.net">Geir Magnusson Jr.</a>
 * @version $Id: ASTElseIfStatement.java 517553 2007-03-13 06:09:58Z wglass $
*/
public class ASTElseIfStatement extends SimpleNode
{
    
    /**
	 * Instantiates a new AST else if statement.
	 *
	 * @param id
	 *            the id
	 */
    public ASTElseIfStatement(int id)
    {
        super(id);
    }

    /**
	 * Instantiates a new AST else if statement.
	 *
	 * @param p
	 *            the p
	 * @param id
	 *            the id
	 */
    public ASTElseIfStatement(Parser p, int id)
    {
        super(p, id);
    }

    /**
	 * Jjt accept.
	 *
	 * @param visitor
	 *            the visitor
	 * @param data
	 *            the data
	 * @return the object
	 * @see org.apache.velocity.runtime.parser.node.SimpleNode#jjtAccept(org.apache.velocity.runtime.parser.node.ParserVisitor,
	 *      java.lang.Object)
	 */
    public Object jjtAccept(ParserVisitor visitor, Object data)
    {
        return visitor.visit(this, data);
    }

    /**
	 * An ASTElseStatement is true if the expression it contains evaluates to
	 * true. Expressions know how to evaluate themselves, so we do that here and
	 * return the value back to ASTIfStatement where this node was originally
	 * asked to evaluate itself.
	 *
	 * @author mqfdy
	 * @param context
	 *            the context
	 * @return True if all childs are true.
	 * @throws MethodInvocationException
	 *             the method invocation exception
	 * @Date 2018-9-3 11:38:29
	 */
    public boolean evaluate ( InternalContextAdapter context)
        throws MethodInvocationException
    {
        return jjtGetChild(0).evaluate(context);
    }

    /**
	 * Render.
	 *
	 * @param context
	 *            the context
	 * @param writer
	 *            the writer
	 * @return true, if successful
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws MethodInvocationException
	 *             the method invocation exception
	 * @throws ResourceNotFoundException
	 *             the resource not found exception
	 * @throws ParseErrorException
	 *             the parse error exception
	 * @see org.apache.velocity.runtime.parser.node.SimpleNode#render(org.apache.velocity.context.InternalContextAdapter,
	 *      java.io.Writer)
	 */
    public boolean render( InternalContextAdapter context, Writer writer)
        throws IOException,MethodInvocationException,
        	ResourceNotFoundException, ParseErrorException
    {
        return jjtGetChild(1).render( context, writer );
    }
}
