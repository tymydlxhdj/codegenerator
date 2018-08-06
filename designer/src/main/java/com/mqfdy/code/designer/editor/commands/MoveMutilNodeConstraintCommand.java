package com.mqfdy.code.designer.editor.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.commands.Command;

import com.mqfdy.code.designer.editor.part.NodeEditPart;

/**
 * 设置节点的位置
 * 
 * 包括图形被移动时和形状大小改变时的位置
 * 
 * Command to move or resize a node.
 * 
 * @author mqfdy
 * 
 */
public class MoveMutilNodeConstraintCommand extends Command {

	private List<NodeEditPart> nodeEditPartList;
	private List<MoveNodeConstraintCommand> cmdList = new ArrayList<MoveNodeConstraintCommand>();
	private int type;

	public MoveMutilNodeConstraintCommand(List<NodeEditPart> nodeEditPartList,
			int type) {

		setLabel("change bounds");
		this.type = type;
		if (nodeEditPartList == null) {
			throw new IllegalArgumentException();
		}
		this.nodeEditPartList = nodeEditPartList;
	}

	@Override
	public boolean canExecute() {
		return true;
	}

	@Override
	public void execute() {
		for (NodeEditPart node : nodeEditPartList) {
			cmdList.add(new MoveNodeConstraintCommand(node, type));
		}
		redo();
	}

	@Override
	public void redo() {
		for (MoveNodeConstraintCommand cmd : cmdList)
			cmd.execute();
	}

	@Override
	public void undo() {
		for (MoveNodeConstraintCommand cmd : cmdList)
			cmd.undo();
	}

	@Override
	public boolean canUndo() {
		return true;
	}

}
