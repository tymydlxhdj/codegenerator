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

public class ShareModelNodeCommand extends Command {
	// 选中的图形EditPart
		List<AbstractGraphicalEditPart> parts = new ArrayList<AbstractGraphicalEditPart>();// BusinessModelUtil.getBusinessModelDiagramEditor().getViewer().getSelectedEditParts();
		// 选中的图形对应的关联关系的EditPart
		List<AbstractGraphicalEditPart> conParts = new ArrayList<AbstractGraphicalEditPart>();// BusinessModelUtil.getBusinessModelDiagramEditor().getViewer().getSelectedEditParts();
		// 选中的图形及其所对应的关联关系的EditPart
		private List<AbstractGraphicalEditPart> allParts = new ArrayList<AbstractGraphicalEditPart>();

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

		@Override
		public boolean canExecute() {
			
			return true;
		}

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

		@Override
		public boolean canUndo() {
			return false;
		}

		public List<AbstractGraphicalEditPart> getParts() {
			return parts;
		}

		public void setParts(List<AbstractGraphicalEditPart> parts) {
			this.parts = parts;
		}

		public List<AbstractGraphicalEditPart> getAllParts() {
			return allParts;
		}

		public void setAllParts(List<AbstractGraphicalEditPart> allParts) {
			this.allParts = allParts;
		}
		
		
}