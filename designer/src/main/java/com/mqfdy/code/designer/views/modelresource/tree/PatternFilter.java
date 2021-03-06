package com.mqfdy.code.designer.views.modelresource.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.ui.internal.misc.StringMatcher;

import com.ibm.icu.text.BreakIterator;
import com.mqfdy.code.designer.dialogs.widget.ModelFilterDialog;
import com.mqfdy.code.designer.editor.BusinessModelEditorPlugin;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.BusinessOperation;
import com.mqfdy.code.model.EnumElement;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.SolidifyPackage;

// TODO: Auto-generated Javadoc
/**
 * 改写org.eclipse.ui.dialogs.PatternFilter
 * 改变方法的可见性（setUseCache）及模糊查询（setPattern）
 * 改写isElementVisible方法
 * @author mqfdy
 *
 */
public class PatternFilter extends ViewerFilter {
	
	/** The cache. */
	/*
	 * Cache of filtered elements in the tree
	 */
	private Map cache = new HashMap();

	/** The found any cache. */
	/*
	 * Maps parent elements to TRUE or FALSE
	 */
	private Map foundAnyCache = new HashMap();

	/** The use cache. */
	private boolean useCache = false;

	/**
	 * Whether to include a leading wildcard for all provided patterns. A
	 * trailing wildcard is always included.
	 */
	private boolean includeLeadingWildcard = false;

	/**
	 * The string pattern matcher used for this pattern filter.
	 */
	private StringMatcher matcher;

	/** The use early return if matcher is null. */
	private boolean useEarlyReturnIfMatcherIsNull = true;

	/** The empty. */
	private static Object[] EMPTY = new Object[0];

	/**
	 * Filter.
	 *
	 * @author mqfdy
	 * @param viewer
	 *            the viewer
	 * @param parent
	 *            the parent
	 * @param elements
	 *            the elements
	 * @return the object[]
	 * @Date 2018-09-03 09:00
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ViewerFilter#filter(org.eclipse.jface.viewers
	 * .Viewer, java.lang.Object, java.lang.Object[])
	 */
	public final Object[] filter(Viewer viewer, Object parent, Object[] elements) {
		// we don't want to optimize if we've extended the filter ... this
		// needs to be addressed in 3.4
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=186404
		if (matcher == null && useEarlyReturnIfMatcherIsNull) {
			return elements;
		}

		if (!useCache) {
			return super.filter(viewer, parent, elements);
		}

		Object[] filtered = (Object[]) cache.get(parent);
		if (filtered == null) {
			Boolean foundAny = (Boolean) foundAnyCache.get(parent);
			if (foundAny != null && !foundAny.booleanValue()) {
				filtered = EMPTY;
			} else {
				filtered = super.filter(viewer, parent, elements);
			}
			cache.put(parent, filtered);
		}
		return filtered;
	}

	/**
	 * Returns true if any of the elements makes it through the filter. This
	 * method uses caching if enabled; the computation is done in
	 * computeAnyVisible.
	 *
	 * @author mqfdy
	 * @param viewer
	 *            the viewer
	 * @param parent
	 *            the parent
	 * @param elements
	 *            the elements (must not be an empty array)
	 * @return true if any of the elements makes it through the filter.
	 * @Date 2018-09-03 09:00
	 */
	private boolean isAnyVisible(Viewer viewer, Object parent, Object[] elements) {
		if (matcher == null) {
			return true;
		}

		if (!useCache) {
			return computeAnyVisible(viewer, elements);
		}

		Object[] filtered = (Object[]) cache.get(parent);
		if (filtered != null) {
			return filtered.length > 0;
		}
		Boolean foundAny = (Boolean) foundAnyCache.get(parent);
		if (foundAny == null) {
			foundAny = computeAnyVisible(viewer, elements) ? Boolean.TRUE
					: Boolean.FALSE;
			foundAnyCache.put(parent, foundAny);
		}
		return foundAny.booleanValue();
	}

	/**
	 * Returns true if any of the elements makes it through the filter.
	 *
	 * @author mqfdy
	 * @param viewer
	 *            the viewer
	 * @param elements
	 *            the elements to test
	 * @return <code>true</code> if any of the elements makes it through the
	 *         filter
	 * @Date 2018-09-03 09:00
	 */
	private boolean computeAnyVisible(Viewer viewer, Object[] elements) {
		boolean elementFound = false;
		for (int i = 0; i < elements.length && !elementFound; i++) {
			Object element = elements[i];
			elementFound = isElementVisible(viewer, element);
		}
		return elementFound;
	}

	/**
	 * Select.
	 *
	 * @author mqfdy
	 * @param viewer
	 *            the viewer
	 * @param parentElement
	 *            the parent element
	 * @param element
	 *            the element
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers
	 * .Viewer, java.lang.Object, java.lang.Object)
	 */
	public final boolean select(Viewer viewer, Object parentElement,
			Object element) {
		return isElementVisible(viewer, element);
	}

	/**
	 * Sets whether a leading wildcard should be attached to each pattern
	 * string.
	 *
	 * @author mqfdy
	 * @param includeLeadingWildcard
	 *            Whether a leading wildcard should be added.
	 * @Date 2018-09-03 09:00
	 */
	public final void setIncludeLeadingWildcard(
			final boolean includeLeadingWildcard) {
		this.includeLeadingWildcard = includeLeadingWildcard;
	}

	/**
	 * The pattern string for which this filter should select elements in the
	 * viewer.
	 *
	 * @author mqfdy
	 * @param patternString
	 *            the new pattern
	 * @Date 2018-09-03 09:00
	 */
	public void setPattern(String patternString) {
		// these 2 strings allow the PatternFilter to be extended in
		// 3.3 - https://bugs.eclipse.org/bugs/show_bug.cgi?id=186404
		if ("org.eclipse.ui.keys.optimization.true".equals(patternString)) { //$NON-NLS-1$
			useEarlyReturnIfMatcherIsNull = true;
			return;
		} else if ("org.eclipse.ui.keys.optimization.false".equals(patternString)) { //$NON-NLS-1$
			useEarlyReturnIfMatcherIsNull = false;
			return;
		}
		clearCaches();
		if (patternString == null || patternString.equals("")) { //$NON-NLS-1$
			matcher = null;
		} else {
			String pattern = "*" + patternString + "*"; //$NON-NLS-1$
			if (includeLeadingWildcard) {
				pattern = "*" + pattern; //$NON-NLS-1$
			}
			matcher = new StringMatcher(pattern, true, false);
		}
	}

	/**
	 * Clears the caches used for optimizing this filter. Needs to be called
	 * whenever the tree content changes.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	/* package */void clearCaches() {
		cache.clear();
		foundAnyCache.clear();
	}

	/**
	 * Answers whether the given String matches the pattern.
	 *
	 * @author mqfdy
	 * @param string
	 *            the String to test
	 * @return whether the string matches the pattern
	 * @Date 2018-09-03 09:00
	 */
	private boolean match(String string) {
		if (matcher == null) {
			return true;
		}
		return matcher.match(string);
	}

	/**
	 * Answers whether the given element is a valid selection in the filtered
	 * tree. For example, if a tree has items that are categorized, the category
	 * itself may not be a valid selection since it is used merely to organize
	 * the elements.
	 *
	 * @author mqfdy
	 * @param element
	 *            the element
	 * @return true if this element is eligible for automatic selection
	 * @Date 2018-09-03 09:00
	 */
	public boolean isElementSelectable(Object element) {
		return element != null;
	}

	/**
	 * Answers whether the given element in the given viewer matches the filter
	 * pattern. This is a default implementation that will show a leaf element
	 * in the tree based on whether the provided filter text matches the text of
	 * the given element's text, or that of it's children (if the element has
	 * any).
	 * 
	 * Subclasses may override this method.
	 *
	 * @author mqfdy
	 * @param viewer
	 *            the tree viewer in which the element resides
	 * @param element
	 *            the element in the tree to check for a match
	 * @return true if the element matches the filter pattern
	 * @Date 2018-09-03 09:00
	 */
	public boolean isElementVisible(Viewer viewer, Object element) {
		// add
		boolean isIncludeProperty = false;
		boolean isIncludeOperation = false;
		boolean isIncludeEnumElement = false;
		IPreferenceStore store = BusinessModelEditorPlugin.getDefault().getPreferenceStore();
		isIncludeProperty = store.getBoolean(ModelFilterDialog.ISINCLUDEPROPERTY);
		
		isIncludeOperation = store.getBoolean(ModelFilterDialog.ISINCLUDEOPERATION);
		
		isIncludeEnumElement = store.getBoolean(ModelFilterDialog.ISINCLUDEENUMELEMENT);
		
		if(!isIncludeProperty){
			if((element instanceof SolidifyPackage && ((SolidifyPackage)element).getDisplayName().equals("属性")) 
					|| element instanceof Property){
				return false;
			}
		}
		if(!isIncludeOperation){
			if((element instanceof SolidifyPackage && ((SolidifyPackage)element).getDisplayName().equals("操作")) 
					|| element instanceof BusinessOperation){
				return false;
			}
		}
		if(!isIncludeEnumElement){
			if(element instanceof EnumElement){
				return false;
			}
		}
//		if(!isinclude){
//			if((element instanceof SolidifyPackage && 
//					(/*((SolidifyPackage)element).getDisplayName().equals("业务实体") || 
//					((SolidifyPackage)element).getDisplayName().equals("关联关系") || */
//					((SolidifyPackage)element).getDisplayName().equals("属性") || 
//					((SolidifyPackage)element).getDisplayName().equals("操作"))) 
//					|| element instanceof Property
//					|| element instanceof BusinessOperation
//					|| element instanceof EnumElement){
//				
//				return false;
//			}
//		}
//		else if((element instanceof SolidifyPackage && 
//				(/*((SolidifyPackage)element).getDisplayName().equals("业务实体") || 
//				((SolidifyPackage)element).getDisplayName().equals("关联关系") || */
////				((SolidifyPackage)element).getDisplayName().equals("属性") || 
//				((SolidifyPackage)element).getDisplayName().equals("操作"))) 
////				|| element instanceof Property
////				|| element instanceof BusinessOperation
//				|| element instanceof EnumElement){
//			
//			return false;
//		}
		return isParentMatch(viewer, element) || isLeafMatch(viewer, element);
	}

	/**
	 * Check if the parent (category) is a match to the filter text. The default
	 * behavior returns true if the element has at least one child element that
	 * is a match with the filter text.
	 * 
	 * Subclasses may override this method.
	 *
	 * @author mqfdy
	 * @param viewer
	 *            the viewer that contains the element
	 * @param element
	 *            the tree element to check
	 * @return true if the given element has children that matches the filter
	 *         text
	 * @Date 2018-09-03 09:00
	 */
	protected boolean isParentMatch(Viewer viewer, Object element) {
		Object[] children = ((ITreeContentProvider) ((AbstractTreeViewer) viewer)
				.getContentProvider()).getChildren(element);

		if ((children != null) && (children.length > 0)) {
			return isAnyVisible(viewer, element, children);
		}
		return false;
	}

	/**
	 * Check if the current (leaf) element is a match with the filter text. The
	 * default behavior checks that the label of the element is a match.
	 * 
	 * Subclasses should override this method.
	 *
	 * @author mqfdy
	 * @param viewer
	 *            the viewer that contains the element
	 * @param element
	 *            the tree element to check
	 * @return true if the given element's label matches the filter text
	 * @Date 2018-09-03 09:00
	 */
	protected boolean isLeafMatch(Viewer viewer, Object element) {
		String labelText = ((ILabelProvider) ((StructuredViewer) viewer)
				.getLabelProvider()).getText(element);
		String name = ((AbstractModelElement) element).getName();
		if (labelText == null && name == null) {
			return false;
		}
		return wordMatches(labelText) || wordMatches(name);
	}

	/**
	 * Take the given filter text and break it down into words using a
	 * BreakIterator.
	 *
	 * @author mqfdy
	 * @param text
	 *            the text
	 * @return an array of words
	 * @Date 2018-09-03 09:00
	 */
	private String[] getWords(String text) {
		List words = new ArrayList();
		// Break the text up into words, separating based on whitespace and
		// common punctuation.
		// Previously used String.split(..., "\\W"), where "\W" is a regular
		// expression (see the Javadoc for class Pattern).
		// Need to avoid both String.split and regular expressions, in order to
		// compile against JCL Foundation (bug 80053).
		// Also need to do this in an NL-sensitive way. The use of BreakIterator
		// was suggested in bug 90579.
		BreakIterator iter = BreakIterator.getWordInstance();
		iter.setText(text);
		int i = iter.first();
		while (i != java.text.BreakIterator.DONE && i < text.length()) {
			int j = iter.following(i);
			if (j == java.text.BreakIterator.DONE) {
				j = text.length();
			}
			// match the word
			if (Character.isLetterOrDigit(text.charAt(i))) {
				String word = text.substring(i, j);
				words.add(word);
			}
			i = j;
		}
		return (String[]) words.toArray(new String[words.size()]);
	}

	/**
	 * Return whether or not if any of the words in text satisfy the match
	 * critera.
	 *
	 * @author mqfdy
	 * @param text
	 *            the text to match
	 * @return boolean <code>true</code> if one of the words in text satisifes
	 *         the match criteria.
	 * @Date 2018-09-03 09:00
	 */
	protected boolean wordMatches(String text) {
		if (text == null) {
			return false;
		}

		// If the whole text matches we are all set
		if (match(text)) {
			return true;
		}

		// Otherwise check if any of the words of the text matches
		String[] words = getWords(text);
		for (int i = 0; i < words.length; i++) {
			String word = words[i];
			if (match(word)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Can be called by the filtered tree to turn on caching.
	 *
	 * @author mqfdy
	 * @param useCache
	 *            The useCache to set.
	 * @Date 2018-09-03 09:00
	 */
	void setUseCache(boolean useCache) {
		this.useCache = useCache;
	}
}
