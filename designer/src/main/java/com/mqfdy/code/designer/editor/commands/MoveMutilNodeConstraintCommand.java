package com.mqfdy.code.designer.editor.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.commands.Command;

import com.mqfdy.code.designer.editor.part.NodeEditPart;

// TODO: Auto-generated Javadoc
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

	/** The node edit part list. */
	private List<NodeEditPart> nodeEditPartList;
	
	/** The cmd list. */
	private List<MoveNodeConstraintCommand> cmdList = new ArrayList<MoveNodeConstraintCommand>();
	
	/** The type. */
	private int type;

	/**
	 * Instantiates a new move mutil node constraint command.
	 *
	 * @param nodeEditPartList
	 *            the node edit part list
	 * @param type
	 *            the type
	 */
	public MoveMutilNodeConstraintCommand(List<NodeEditPart> nodeEditPartList,
			int type) {

		setLabel("change bounds");
		this.type = type;
		if (nodeEditPartList == null) {
			throw new IllegalArgumentException();
		}
		this.nodeEditPartList = nodeEditPartList;
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
		for (NodeEditPart node : nodeEditPartList) {
			cmdList.add(new MoveNodeConstraintCommand(node, type));
		}
		redo();
	}

	/**
	 * 
	 */
	@Override
	public void redo() {
		for (MoveNodeConstraintCommand cmd : cmdList)
			cmd.execute();
	}

	/**
	 * 
	 */
	@Override
	public void undo() {
		for (MoveNodeConstraintCommand cmd : cmdList)
			cmd.undo();
	}

	/**
	 * @return
	 */
	@Override
	public boolean canUndo() {
		return true;
	}

}
