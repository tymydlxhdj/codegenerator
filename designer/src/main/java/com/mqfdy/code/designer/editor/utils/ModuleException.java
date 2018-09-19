/* Sotower - Sotower is a UML Plugin for Eclipse
 *
 * Copyright (C) 2005-2006 Sotower-Group, sotower-dev@lists.binaervarianz.de
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

/*
 * Created on 03.12.2005
 *
 */
package com.mqfdy.code.designer.editor.utils;

// TODO: Auto-generated Javadoc
/**
 * 自定义异常类.
 *
 * @author mqfdy
 */
public abstract class ModuleException extends RuntimeException {

	/** The Constant serialVersionUID. */
	protected static final long serialVersionUID = -3055245629659580974L;

	/** The Constant SEVERITY_ERROR. */
	// Constants
	public static final int SEVERITY_ERROR = 101;

	/** The Constant SEVERITY_INFO. */
	public static final int SEVERITY_INFO = 102;

	/** The Constant LOGGING_ON. */
	public static final int LOGGING_ON = 201;

	/** The Constant LOGGING_OFF. */
	public static final int LOGGING_OFF = 202;

	/** The Constant logstatus. */
	public static final int logstatus = LOGGING_ON;

}
