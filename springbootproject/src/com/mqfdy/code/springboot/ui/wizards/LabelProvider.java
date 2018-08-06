package com.mqfdy.code.springboot.ui.wizards;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * Base class containing common functionality useful to implement label providers.
 * 
 * @author lenovo
 */
public abstract class LabelProvider extends BaseLabelProvider {

	protected Image getImage(String location) {
		InputStream imageStream = getClass().getClassLoader().getResourceAsStream(location);
		try {
			return new Image(null, imageStream);
		} finally {
			try {
				imageStream.close();
			} catch (IOException e) {
			}
		}
	}

}
