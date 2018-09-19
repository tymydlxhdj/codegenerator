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

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.runtime.parser.Parser;

// TODO: Auto-generated Javadoc
/**
 * The Class ASTTrue.
 *
 * @author mqfdy
 */
public class ASTTrue extends SimpleNode
{
    
    /** The value. */
    private static Boolean value = Boolean.TRUE;

    /**
	 * Instantiates a new AST true.
	 *
	 * @param id
	 *            the id
	 */
    public ASTTrue(int id)
    {
        super(id);
    }

    /**
	 * Instantiates a new AST true.
	 *
	 * @param p
	 *            the p
	 * @param id
	 *            the id
	 */
    public ASTTrue(Parser p, int id)
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
	 * Evaluate.
	 *
	 * @param context
	 *            the context
	 * @return true, if successful
	 * @see org.apache.velocity.runtime.parser.node.SimpleNode#evaluate(org.apache.velocity.context.InternalContextAdapter)
	 */
    public boolean evaluate(InternalContextAdapter context)
    {
        return true;
    }

    /**
	 * Value.
	 *
	 * @param context
	 *            the context
	 * @return the object
	 * @see org.apache.velocity.runtime.parser.node.SimpleNode#value(org.apache.velocity.context.InternalContextAdapter)
	 */
    public Object value(InternalContextAdapter context)
    {
        return value;
    }
}
