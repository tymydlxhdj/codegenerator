package com.mqfdy.code.springboot.ui.wizards;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

import com.mqfdy.code.security.util.SecurityTestUtil;
import com.mqfdy.code.springboot.core.MicroProjectPlugin;
import com.mqfdy.code.springboot.core.util.OsUtils;

/**
 * @author lenovo
 */
public class ImportWizardPageOne extends WizardPage {

	private static final ImageDescriptor WIZBAN_IMAGE = ImageDescriptor
			.createFromURL(ImportWizardPageOne.class.getClassLoader()
					.getResource("icons/wizban/boot_wizard.png"));

	/**
	 * Key to store / retrieve root folder input history from GradlePreferences.
	 */
	private static final String ROOT_FOLDER_HISTORY_KEY = ImportWizardPageOne.class
			.getName() + ".RF_HIST";
	private static final String LAST_TIME_BROWER_KEY = ImportWizardPageOne.class
			.getName() + ".RF_BOTTON";
	private static final String[] DEFAULT_ROOT_FOLDER_HISTORY = new String[] {};
	private static final int MAX_ROOT_FOLDER_HISTORY = 10;

	private Combo rootFolderText;
	private Button browseButton;
	private CheckboxTreeViewer projectSelectionTree;
	private List<File> pjs = new ArrayList<File>();
	private Object[] fs = null;
	String directory = getBrowerButtonFromSettings();

	public ImportWizardPageOne() {
		super("gradleImportWizardPage1", "Import Springboot Project",
				WIZBAN_IMAGE);
	}

	public void createControl(Composite parent) {

		Composite page = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		layout.marginHeight = 1;
		layout.marginWidth = 1;
		page.setLayout(layout);

		createRootFolderSection(page);
		createProjectSelectionGroup(page);

		setControl(page);
		checkPageComplete();
		checkRootFolder();
	}

	/**
	 * Create the import source selection widget
	 */
	protected void createProjectSelectionGroup(Composite parent) {

		projectSelectionTree = new CheckboxTreeViewer(parent, SWT.BORDER);

		projectSelectionTree.setSorter(new ViewerSorter());

		Tree tree = projectSelectionTree.getTree();
		tree.setLayoutData(new GridData(GridData.FILL_BOTH));

		tree.setLayout(new GridLayout(2, true));
		// headers of views tables
		tree.setHeaderVisible(true);
		TreeColumn column1 = new TreeColumn(tree, SWT.LEFT);
		column1.setText("Project");
		TreeColumn column2 = new TreeColumn(tree, SWT.LEFT);
		column2.setText("Description");
		column1.pack();
		column2.pack();

		projectSelectionTree
				.setContentProvider(new ProjectTreeContentProvider());
		projectSelectionTree
				.setLabelProvider(new ProjectTreeLabelProviderWithDescription(
						true));

		projectSelectionTree.setInput(null); // pass a non-null that will be
												// ignored

		GridDataFactory.fillDefaults().grab(true, true).minSize(0, 200)
				.applyTo(projectSelectionTree.getControl());

		Composite buttonRow = new Composite(parent, SWT.NONE);
		buttonRow.setLayout(new GridLayout(3, true));

		Button selectAllButton = new Button(buttonRow, SWT.PUSH);
		selectAllButton.setText("Select All");

		Button deselectAllButton = new Button(buttonRow, SWT.PUSH);
		deselectAllButton.setText("Deselect All");

		GridDataFactory grabHor = GridDataFactory.fillDefaults().grab(true,
				false);
		grabHor.applyTo(selectAllButton);
		grabHor.applyTo(deselectAllButton);

		selectAllButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectAllProjects(true);
			}
		});
		deselectAllButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectAllProjects(false);
			}
		});
	}

	private void selectAllProjects(boolean select) {
		Object input = projectSelectionTree.getInput();
		if (input != null) {
			for (Object rootElement : (List<?>) input) {
				projectSelectionTree.setSubtreeChecked(rootElement, select);
			}
		}
		checkPageComplete();
		// save checked
		saveImportOptions();
	}

	protected void checkPageComplete() {
		setPageComplete(getErrorMessage() == null);
	}

	private boolean hasSelectedProjects() {
		if (projectSelectionTree != null) {
			Object[] selectedProjects = projectSelectionTree
					.getCheckedElements();
			return selectedProjects != null && selectedProjects.length > 0;
		}
		return false;
	}

	private void createRootFolderSection(Composite page) {
		GridDataFactory grabHorizontal = GridDataFactory.fillDefaults().grab(
				true, false);

		GridLayout layout = new GridLayout(4, false);
		Composite composite = new Composite(page, SWT.NONE);
		composite.setLayout(layout);

		Label label = new Label(composite, SWT.NONE);
		label.setText("folder:");
		rootFolderText = new Combo(composite, SWT.DROP_DOWN);

		browseButton = new Button(composite, SWT.PUSH);
		browseButton.setText("Browse...");

		browseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				File file = openFileDialog(directory);
				if (file != null) {
					rootFolderText.setText(file.toString());
				}
			}
		});

		String[] rootFolderHistory = getRootFolderHistory();
		if (rootFolderHistory.length > 0) {
			rootFolderText.setItems(rootFolderHistory);
			rootFolderText.select(rootFolderHistory.length - 1);
		}

		rootFolderText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				checkRootFolder();
			}
		});

		grabHorizontal.applyTo(composite);
		grabHorizontal.applyTo(rootFolderText);
	}

	private void updateLoadButton(List<File> rfs) {
		projectSelectionTree.setInput(rfs);
	}

	private String[] getRootFolderHistory() {
		String history = MicroProjectPlugin
				.getDefault()
				.getPreferenceStore()
				.getString(ROOT_FOLDER_HISTORY_KEY);
		if(history != null && history.length() > 0){
			String [] historyStrings = history.split(",");
			return historyStrings;
		}
		return DEFAULT_ROOT_FOLDER_HISTORY;
	}

	private void checkRootFolder() {
		final File rf = getRootFolder();
		clearErrorMessage();
		if (rf == null) {
			setErrorMessage("Specify the root folder of your project to import");
		} else if (!rf.exists()) {
			setErrorMessage("The root folder doesn't exist");
		} else if (checkCurrentOrChildrenfolders(rf).size() == 0) {
			setErrorMessage("Could not find 'yaml' or 'build.gradle' files in "
					+ rf.getName() + " directory and sub directory.");
		} else {
			checkPageComplete();
		}
		updateLoadButton(pjs);
	}

	private void clearErrorMessage() {
		setPageComplete(false);
		setErrorMessage(null);
	}

	private List<File> checkCurrentOrChildrenfolders(File rf) {
		pjs.clear();
		if (hasBuildGradeFile(rf) && hasYamlFile(rf)) {
			pjs.add(rf);
			return pjs;
		}
		File[] files = rf.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return new File(dir, name).isDirectory();
			}
		});
		for (File file : files) {
			boolean bool = hasBuildGradeFile(file) && hasYamlFile(file);
			if (bool)
				pjs.add(file);
		}
		return pjs;
	}

	private boolean hasBuildGradeFile(File rf) {
		File buildFile = new File(rf, "build.gradle");
		return buildFile.exists();
	}

	private boolean hasYamlFile(File rf) {
		boolean check = false;
		String[] files = rf.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				File file = new File(dir, name);
				return file.isFile();
			}
		});
		for (String name : files) {
			if (name.toLowerCase().endsWith("yaml".toLowerCase())
					|| name.toLowerCase().endsWith("yml".toLowerCase())) {
				check = true;
				break;
			}
		}
		return check;
	}

	private File openFileDialog(String initialSelection) {
		DirectoryDialog fileDialog = new DirectoryDialog(getShell(), SWT.OPEN);
		fileDialog.setFilterPath(initialSelection);
		String file = fileDialog.open();
		if (file != null) {
			return new File(file);
		}
		return null;
	}

	public File getRootFolder() {
		String text = rootFolderText.getText();
		if (text != null && !("".equals(text.trim()))) {
			return new File(text);
		}
		return null;
	}

	private void saveImportOptions() {
		Object[] elements = projectSelectionTree.getCheckedElements();
		fs = elements;
	}

	public Object[] getImportElements() {
		return fs;
	}

	/**
	 * Called by our wizard when its about to finish (i.e. user has pressed
	 * finish button).
	 */
	public void wizardAboutToFinish() {
		addRootFolderToHistory();
		addBrowerButtonToSettings();
		ensureGradleRunning();
	}

	// create file (./gradle/gradle.properties),because of Gradle running
	// org.gradle.jvmargs=-Xmx512m

	
	
	private void ensureGradleRunning() {
		String path = OsUtils.getUserHome() + File.separator + ".gradle/gradle.properties";
		if(!SecurityTestUtil.validateFilePath(path)) {
			return;
		}
		File file = new File(path);
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		entrue(file);
	}

	private void entrue(File file){
		Properties prop = new Properties();
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(file);
			fos = new FileOutputStream(file);
			prop.load(fis);
			prop.setProperty("org.gradle.jvmargs", "-Xmx512m");
			prop.store(fos, "org.gradle.jvmargs");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(fis != null){
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(fos != null){
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Ensure that the current contents of the root folder text box is added to
	 * the history.
	 */
	private void addRootFolderToHistory() {
		String[] history = getRootFolderHistory();
		LinkedHashSet<String> historySet = new LinkedHashSet<String>(
				Arrays.asList(history));
		if (historySet.size() >= MAX_ROOT_FOLDER_HISTORY) {
			historySet.remove(history[0]);
		}
		historySet.add(rootFolderText.getText());
		
		String historyPre = "";
		Iterator<String> iterator = historySet.iterator();
		while (iterator.hasNext()) {
			historyPre += iterator.next() + ",";
		}
		if(historyPre.length() > 0){
			historyPre = historyPre.substring(0, historyPre.length() - 1);
		}
		MicroProjectPlugin
				.getDefault()
				.getPreferenceStore().putValue(ROOT_FOLDER_HISTORY_KEY,
						historyPre);
	}

	/**
	 * Ensure that the current contents of the brower button text box is added
	 * to the settings.
	 */
	private void addBrowerButtonToSettings() {
		MicroProjectPlugin
		.getDefault()
		.getPreferenceStore()
				.putValue(LAST_TIME_BROWER_KEY, rootFolderText.getText());
	}

	private String getBrowerButtonFromSettings() {
		return MicroProjectPlugin
				.getDefault()
				.getPreferenceStore()
				.getString(LAST_TIME_BROWER_KEY);
	}

}
