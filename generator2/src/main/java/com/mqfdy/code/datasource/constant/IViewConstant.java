package com.mqfdy.code.datasource.constant;

import java.awt.Toolkit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public interface IViewConstant {
	
	
	public static final int TYPE_DATASOURCE = 1; 
	
	public static final int TYPE_REPOSITORY = 2; 
	
	public static final int TYPE_SCHEMAS = 3; 
	
	public static final int TYPE_USER = 4;
	
	public static final int TYPE_TABLE_FOLDER = 5;
	
	public static final int TYPE_TABLE_NODE = 6;
	
	public static final int TYPE_COLUMNS_FOLDER = 7; 
	
	public static final int TYPE_COLUMNS_NODE = 8; 
	
	public static final int TYPE_CONSTRAINT_FOLDER = 9;
	
	public static final int TYPE_PRIMARY_KEY = 10; 
	
	public static final int TYPE_REFERENCE_KEY = 11;
	
	public static final int TYPE_SEQUENCES_FOLDER = 12;
	
	public static final int TYPE_SEQUENCES_NODE = 13;
	
	public static final int TYPE_VIEWS_FOLDER = 14;
	
	public static final int TYPE_VIEWS_NODE = 15;
	 
	public static final int TYPE_TEMP = 16;
	
	public static final int TYPE_UN_VISABLE = 17;
	
	public static final int TYPE_PROJECTS = 18;
	
	public static final int TYPE_DIRECTORY = 19;
	
	public static final int TYPE_OM_FILE = 20;
	
	
	public static final int TYPE_NO_PK_TABLE = 21;
	public static final int TYPE_MULTI_TABLE = 22;
	
	public static final int TYPE_DUPLICATE_NAME = 23;
	public static final int TYPE_BUSINESSCLASS = 24;
	public static final int TYPE_PROPERTY_NODE = 25;
	public static final int TYPE_PACKAGE = 26;
	
	public static final int TYPE_PRIMARY_COLUMN = 27; 
	
	public static final int TYPE_REFERENCE_COLUMN = 28;
	
	public static final int TYPE_PRIMARY_PROPERTY = 29; 
	
	public static final int TYPE_RELATION = 30;
	public static final int TYPE_BUSINESSCLASS_ERROR = 31;
	public static final int TYPE_OPERATION_ERROR = 32;
	public static final int TYPE_PRIMARYKEY_ERROR = 33;
	public static final int TYPE_PROPERTY_NODE_ERROR = 35;
	public static final int TYPE_BUSINESSCLASS_CONFLICT = 34;
	
	public static final int EXPAND_LEVEL_1 = 1; 
	public static final int EXPAND_LEVEL_2 = 2; 
	public static final int EXPAND_LEVEL_3 = 3; 
	public static final int EXPAND_LEVEL_4 = 4; 
	
	public static final String COLUMNS_FOLDER = "Columns";
	public static final String DATASOURCE = "datasource"; 
	public static final String SCHEMAS = "schemas"; 
	public static final String PRIMARY_KEY = "primary_key"; 
	public static final String REFERENCE_KEY = "reference_key"; 
	public static final String CONSTRAINT_FOLDER = "Constraints"; 
	public static final String REPOSITORY = "repository"; 
	public static final String TABLES  = "tables"; 
	public static final String USER = "user";
	public static final String SEQUENCES = "sequences";
	public static final String VIEWS = "views";
	public static final String TEMP = "temp";
	
	public static final Color BLACK_COLOR = Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
	public static final Color WHITE_COLOR = Display.getCurrent().getSystemColor(SWT.COLOR_WHITE);
	public static final Color GRAY_COLOR = Display.getCurrent().getSystemColor(SWT.COLOR_GRAY);
	public static final Color RED_COLOR = Display.getCurrent().getSystemColor(SWT.COLOR_RED);
	
	public static final int DIALOG_WIDTH_PROPORTION = 37;
	public static final int DIALOG_HEIGHT_PROPORTION = 65;
	public static final int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
	public static final int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;


}
