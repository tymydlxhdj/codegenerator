/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package com.mqfdy.code.springboot.utilities;

/**
 * This interface allows clients to control how a command is executed
 * (e.g. dispatching the command to the UI thread).
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface CommandExecutor {

	/**
	 * Execute the specified command.
	 */
	void execute(Command command);


	/**
	 * Straightforward implementation of the command executor interface
	 * that simply executes the command without any sort of enhancement.
	 */
	final class Default implements CommandExecutor {
		public static final CommandExecutor INSTANCE = new Default();
		public static CommandExecutor instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Default() {
			super();
		}
		public void execute(Command command) {
			command.execute();
		}
		@Override
		public String toString() {
			return "CommandExecutor.Default";
		}
	}

}
