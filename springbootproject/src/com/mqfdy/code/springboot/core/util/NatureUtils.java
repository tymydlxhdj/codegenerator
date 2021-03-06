package com.mqfdy.code.springboot.core.util;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

// TODO: Auto-generated Javadoc
/**
 * The Class NatureUtils.
 *
 * @author lenovo
 */
public class NatureUtils {

	/**
	 * Make sure a project has a number of required natures. If the natures are
	 * not yet present, add them now.
	 *
	 * @author mqfdy
	 * @param project
	 *            the project
	 * @param mon
	 *            the mon
	 * @param reqNatures
	 *            the req natures
	 * @throws CoreException
	 *             the core exception
	 * @Date 2018-09-03 09:00
	 */
	public static void ensure(IProject project, IProgressMonitor mon, String... reqNatures) throws CoreException {
		IProjectDescription desc = project.getDescription();
		String[] oldNaturesArr = desc.getNatureIds();
		Set<String> natures = new LinkedHashSet<String>();
		for (String n : reqNatures) {
			natures.add(n);
		}
		for (String n : oldNaturesArr) {
			natures.add(n);
		}
		if (natures.size()>oldNaturesArr.length) {
			//Some natures got added
			desc.setNatureIds(natures.toArray(new String[natures.size()]));
			project.setDescription(desc, mon);
		} else {
			//No new natures added, but need to set it to force desired ordering
			desc.setNatureIds(natures.toArray(new String[natures.size()]));
			project.setDescription(desc, IResource.AVOID_NATURE_CONFIG, mon);
		}
	}

	/**
	 * Removes a nature from a project. This does nothing if the project doesn't
	 * have the nature.
	 *
	 * @author mqfdy
	 * @param project
	 *            the project
	 * @param natureId
	 *            the nature id
	 * @param mon
	 *            the mon
	 * @throws CoreException
	 *             the core exception
	 * @Date 2018-09-03 09:00
	 */
	public static void remove(IProject project, String natureId, IProgressMonitor mon) throws CoreException {
		IProjectDescription desc = project.getDescription();
		String[] oldNaturesArr = desc.getNatureIds();
		Set<String> natures = new LinkedHashSet<String>();
		for (String n : oldNaturesArr) {
			if (!n.equals(natureId)) {
				natures.add(n);
			}
		}
		if (natures.size()!=oldNaturesArr.length) {
			//Something removed
			desc.setNatureIds(natures.toArray(new String[natures.size()]));
			project.setDescription(desc, mon);
		}
		
	}

}
