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
 * Handles multiplication<br><br>
 *
 * Please look at the Parser.jjt file which is
 * what controls the generation of this class.
 *
 * @author <a href="mailto:wglass@forio.com">Will Glass-Husain</a>
 * @author <a href="mailto:pero@antaramusic.de">Peter Romianowski</a>
 * @author <a href="mailto:jvanzyl@apache.org">Jason van Zyl</a>
 * @author <a href="mailto:geirm@optonline.net">Geir Magnusson Jr.</a>
 * @version $Id: ASTMulNode.java 691048 2008-09-01 20:26:11Z nbubna $
 */
public class ASTMulNode extends ASTMathNode
{
    
    /**
	 * Instantiates a new AST mul node.
	 *
	 * @param id
	 *            the id
	 */
    public ASTMulNode(int id)
    {
        super(id);
    }

    /**
	 * Instantiates a new AST mul node.
	 *
	 * @param p
	 *            the p
	 * @param id
	 *            the id
	 */
    public ASTMulNode(Parser p, int id)
    {
        super(p, id);
    }

    /**
	 * Perform.
	 *
	 * @param left
	 *            the left
	 * @param right
	 *            the right
	 * @param context
	 *            the context
	 * @return ASTMulNode
	 * @see org.apache.velocity.runtime.parser.node.ASTMathNode#perform(java.lang.Number,
	 *      java.lang.Number,
	 *      org.apache.velocity.context.InternalContextAdapter)
	 */
    public Number perform(Number left, Number right, InternalContextAdapter context)
    {
        return MathUtils.multiply(left, right);
    }
}




