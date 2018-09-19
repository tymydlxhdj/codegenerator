package com.mqfdy.code.designer.editor.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.ui.actions.Clipboard;

import com.mqfdy.code.designer.editor.part.AnnotationEditPart;
import com.mqfdy.code.designer.editor.part.BusinessClassEditPart;
import com.mqfdy.code.designer.editor.part.EnumerationEditPart;
import com.mqfdy.code.designer.editor.part.ReferenceObjectEditPart;
import com.mqfdy.code.model.IModelElement;

// TODO: Auto-generated Javadoc
/**
 * The Class ShareModelNodeCommand.
 *
 * @author mqfdy
 */
public class ShareModelNodeCommand extends Command {
	
	/** The parts. */
	// 选中的图形EditPart
		List<AbstractGraphicalEditPart> parts = new ArrayList<AbstractGraphicalEditPart>();// BusinessModelUtil.getBusinessModelDiagramEditor().getViewer().getSelectedEditParts();
		
		/** The con parts. */
		// 选中的图形对应的关联关系的EditPart
		List<AbstractGraphicalEditPart> conParts = new ArrayList<AbstractGraphicalEditPart>();// BusinessModelUtil.getBusinessModelDiagramEditor().getViewer().getSelectedEditParts();
		
		/** The all parts. */
		// 选中的图形及其所对应的关联关系的EditPart
		private List<AbstractGraphicalEditPart> allParts = new ArrayList<AbstractGraphicalEditPart>();

		/**
		 * Instantiates a new share model node command.
		 *
		 * @param selectedObjects
		 *            the selected objects
		 */
		public ShareModelNodeCommand(List<AbstractGraphicalEditPart> selectedObjects) {
			for (AbstractGraphicalEditPart part : selectedObjects) {
				if (part instanceof BusinessClassEditPart
						|| part instanceof EnumerationEditPart
						|| part instanceof AnnotationEditPart)//ReferenceObjectEditPart)
					parts.add(part);
				if(part instanceof ReferenceObjectEditPart || (part instanceof BusinessClassEditPart && IModelElement.STEREOTYPE_BUILDIN.equals(((BusinessClassEditPart) part).getBusinessClass().getStereotype()))){
					parts.clear();
					break;
				}
			}
		}

		/**
		 * @return
		 */
		@Override
		public boolean canExecute() {
			
			return true;
		}

		/**
		 * 
		 */
		@Override
		public void execute() {
			if (canExecute()) {
			List<AbstractGraphicalEditPart> sources = new ArrayList<AbstractGraphicalEditPart>();// BusinessModelUtil.getBusinessModelDiagramEditor().getViewer().getSelectedEditParts();
				List<AbstractGraphicalEditPart> targets = new ArrayList<AbstractGraphicalEditPart>();// BusinessModelUtil.getBusinessModelDiagramEditor().getViewer().getSelectedEditParts();
				for (AbstractGraphicalEditPart part : parts) {
					if (part instanceof BusinessClassEditPart
					// &&
					// !BusinessClass.STEREOTYPE_BUILDIN.equals(((BusinessClassEditPart)
					// part).getBusinessClass().getStereotype())
					) {
					sources.addAll(part.getSourceConnections());
						targets.addAll(part.getTargetConnections());
						allParts.add(part);
					} else if (part instanceof ReferenceObjectEditPart) {
						sources.addAll(part.getSourceConnections());
						targets.addAll(part.getTargetConnections());
						allParts.add(part);
					}else if (part instanceof EnumerationEditPart) {
					sources.addAll(part.getSourceConnections());
						targets.addAll(part.getTargetConnections());
						allParts.add(part);
					}else if (part instanceof AnnotationEditPart) {
					sources.addAll(part.getSourceConnections());
					targets.addAll(part.getTargetConnections());
						allParts.add(part);
					}
				}
				for (AbstractGraphicalEditPart part : sources) {
					if (targets.contains(part))
						conParts.add(part);
				}

				allParts.addAll(conParts);
				if (allParts.size() > 0) {
					Clipboard.getDefault().setContents(allParts);
				}
			}
		}

		/**
		 * @return
		 */
		@Override
		public boolean canUndo() {
			return false;
		}

		/**
		 * Gets the parts.
		 *
		 * @author mqfdy
		 * @return the parts
		 * @Date 2018-09-03 09:00
		 */
		public List<AbstractGraphicalEditPart> getParts() {
			return parts;
		}

		/**
		 * Sets the parts.
		 *
		 * @author mqfdy
		 * @param parts
		 *            the new parts
		 * @Date 2018-09-03 09:00
		 */
		public void setParts(List<AbstractGraphicalEditPart> parts) {
			this.parts = parts;
		}

		/**
		 * Gets the all parts.
		 *
		 * @author mqfdy
		 * @return the all parts
		 * @Date 2018-09-03 09:00
		 */
		public List<AbstractGraphicalEditPart> getAllParts() {
			return allParts;
		}

		/**
		 * Sets the all parts.
		 *
		 * @author mqfdy
		 * @param allParts
		 *            the new all parts
		 * @Date 2018-09-03 09:00
		 */
		public void setAllParts(List<AbstractGraphicalEditPart> allParts) {
			this.allParts = allParts;
		}
		
		
}
