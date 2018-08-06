package com.mqfdy.code.designer.console;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleFactory;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import com.mqfdy.code.designer.editor.BusinessModelEditorPlugin;
import com.mqfdy.code.designer.preferences.ModelPreferencePage;

public class ConsoleFactory implements IConsoleFactory {

	private static MessageConsole console = new MessageConsole("模型驱动", null);
	static boolean exists = false;

	/**
	 * 描述:打开控制台
	 * */
	public void openConsole() {
		showConsole();
	}

	/** */
	/**
	 * 描述:显示控制台
	 * */
	private static void showConsole() {
		Display display = Display.getCurrent();
		if (console != null) {
			console.setFont(display.getSystemFont());
			// 得到默认控制台管理器
			IConsoleManager manager = ConsolePlugin.getDefault()
					.getConsoleManager();

			// 得到所有的控制台实例
			IConsole[] existing = manager.getConsoles();
			exists = false;
			// 新创建的MessageConsole实例不存在就加入到控制台管理器，并显示出来
			for (int i = 0; i < existing.length; i++) {
				if (console == existing[i])
					exists = true;
			}
			if (!exists) {
				manager.addConsoles(new IConsole[] { console });
			}

			// console.activate();

		}
	}

	/**
	 * 描述:关闭控制台
	 */
	public static void closeConsole() {
		IConsoleManager manager = ConsolePlugin.getDefault()
				.getConsoleManager();
		if (console != null) {
			manager.removeConsoles(new IConsole[] { console });
		}

	}

	/**
	 * 获取控制台
	 * 
	 * @return
	 */
	public static MessageConsole getConsole() {

		showConsole();
		return console;
	}

	/**
	 * 校验模型时，根据首选项配置决定向控制台打印一条信息，并激活控制台。
	 * 
	 * @param message
	 * @param activate
	 *            是否激活控制台
	 * @param error
	 *            是否错误信息
	 */
	public static void printToConsole(String message, boolean activate,
			boolean error) {
		IPreferenceStore store = BusinessModelEditorPlugin.getDefault()
				.getPreferenceStore();
		// String print = store.getString(ModelPreferencePage.CONSOLE_PRINT);
		if (/* print.equals("")|| */store
				.getBoolean(ModelPreferencePage.CONSOLE_PRINT)) {
			MessageConsoleStream printer = ConsoleFactory.getConsole()
					.newMessageStream();
			Display display = Display.getCurrent();
			if (error)
				printer.setColor(display.getSystemColor(SWT.COLOR_RED));
			printer.setActivateOnWrite(activate);
			printer.println(message);
		} else
			return;
	}
	/**
	 * 生成代码时向控制台打印一条信息，并激活控制台。
	 * 
	 * @param message
	 * @param activate
	 *            是否激活控制台
	 * @param error
	 *            是否错误信息
	 */
	public static void printToConsoleGenerate(String message, boolean activate,
			boolean error) {
		MessageConsoleStream printer = ConsoleFactory.getConsole()
				.newMessageStream();
		Display display = Display.getCurrent();
		if (error)
			printer.setColor(display.getSystemColor(SWT.COLOR_RED));
		printer.setActivateOnWrite(activate);
		printer.println(message);
	}
}
