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

import java.math.BigDecimal;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.TemplateInitException;
import org.apache.velocity.runtime.parser.Parser;


// TODO: Auto-generated Javadoc
/**
 * Handles floating point numbers.  The value will be either a Double
 * or a BigDecimal.
 *
 * @author <a href="mailto:wglass@forio.com">Will Glass-Husain</a>
 * @since 1.5
 */
public class ASTFloatingPointLiteral extends SimpleNode
{

    /** The value. */
    // This may be of type Double or BigDecimal
    private Number value = null;

    /**
	 * Instantiates a new AST floating point literal.
	 *
	 * @param id
	 *            the id
	 */
    public ASTFloatingPointLiteral(int id)
    {
        super(id);
    }

    /**
	 * Instantiates a new AST floating point literal.
	 *
	 * @param p
	 *            the p
	 * @param id
	 *            the id
	 */
    public ASTFloatingPointLiteral(Parser p, int id)
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
	 * Initialization method - doesn't do much but do the object creation. We
	 * only need to do it once.
	 *
	 * @author mqfdy
	 * @param context
	 *            the context
	 * @param data
	 *            the data
	 * @return The data object.
	 * @throws TemplateInitException
	 *             the template init exception
	 * @Date 2018-9-3 11:38:37
	 */
    public Object init( InternalContextAdapter context, Object data)
        throws TemplateInitException
    {
        /*
         *  init the tree correctly
         */

        super.init( context, data );

        /**
         * Determine the size of the item and make it a Double or BigDecimal as appropriate.
         */
         String str = getFirstToken().image;
         try
         {
             value = new Double( str );

         } catch ( NumberFormatException E1 )
         {

            // if there's still an Exception it will propogate out
            value = new BigDecimal( str );

        }

        return data;
    }

    /**
	 * Value.
	 *
	 * @param context
	 *            the context
	 * @return the object
	 * @see org.apache.velocity.runtime.parser.node.SimpleNode#value(org.apache.velocity.context.InternalContextAdapter)
	 */
    public Object value( InternalContextAdapter context)
    {
        return value;
    }


}
