package com.mqfdy.bom.project;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

/**
 * @author mqfdy
 */
public class BOMProjectNature implements IProjectNature {  
	
	public static final String NATURE_ID = "com.mqfdy.bom.project.nature";
	
    private IProject project;  
      
    public void configure() throws CoreException {  
    }  
  
    public void deconfigure() throws CoreException {  
    }  
  
    public IProject getProject() {  
        return project;  
    }  
  
    public void setProject(IProject project) {  
        this.project = project;  
    }
    
	public static boolean hasNature(IProject p) {
		try {
			return p!=null && p.isAccessible() && p.hasNature(NATURE_ID);
		} catch (CoreException e) {
			Activator.log(e);
		}
		return false;
	}
} 