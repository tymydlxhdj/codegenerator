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
import org.apache.velocity.runtime.parser.Token;

// TODO: Auto-generated Javadoc
/**
 *  Represents all comments...
 *
 *  @author <a href="mailto:geirm@apache.org">Geir Magnusson Jr.</a>
 *  @version $Id: ASTComment.java 731266 2009-01-04 15:11:20Z byron $
 */
public class ASTComment extends SimpleNode
{
    
    /** The Constant ZILCH. */
    private static final char[] ZILCH = "".toCharArray();

    /** The carr. */
    private char[] carr;

    /**
	 * Instantiates a new AST comment.
	 *
	 * @param id
	 *            the id
	 */
    public ASTComment(int id)
    {
        super(id);
    }

    /**
	 * Instantiates a new AST comment.
	 *
	 * @param p
	 *            the p
	 * @param id
	 *            the id
	 */
    public ASTComment(Parser p, int id)
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
	 * We need to make sure we catch any of the dreaded MORE tokens.
	 *
	 * @author mqfdy
	 * @param context
	 *            the context
	 * @param data
	 *            the data
	 * @return The data object.
	 * @Date 2018-9-3 11:38:24
	 */
    public Object init(InternalContextAdapter context, Object data)
    {
        Token t = getFirstToken();

        int loc1 = t.image.indexOf("##");
        int loc2 = t.image.indexOf("#*");

        if (loc1 == -1 && loc2 == -1)
        {
            carr = ZILCH;
        }
        else
        {
            carr = t.image.substring(0, (loc1 == -1) ? loc2 : loc1).toCharArray();
        }

        return data;
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
	 * @throws ParseErrorException
	 *             the parse error exception
	 * @throws ResourceNotFoundException
	 *             the resource not found exception
	 * @see org.apache.velocity.runtime.parser.node.SimpleNode#render(org.apache.velocity.context.InternalContextAdapter,
	 *      java.io.Writer)
	 */
    public boolean render( InternalContextAdapter context, Writer writer)
        throws IOException, MethodInvocationException, ParseErrorException, ResourceNotFoundException
    {
        writer.write(carr);
        return true;
    }

}
