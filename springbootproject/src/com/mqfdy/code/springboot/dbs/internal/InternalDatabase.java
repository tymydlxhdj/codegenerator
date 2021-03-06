/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package com.mqfdy.code.springboot.dbs.internal;

import com.mqfdy.code.springboot.dbs.Database;

/**
 * "internal" database
 */
interface InternalDatabase extends Database {
	DTPCatalogWrapper getDefaultCatalog();
	void dispose();
}
