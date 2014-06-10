package org.dannil.simpletexteditor;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.dannil.simpletexteditor.controller.MainController;
import org.dannil.simpletexteditor.utility.LanguageUtility;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.wb.swt.SWTResourceManager;

import com.google.common.io.Files;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public final class MainView {

	private final ResourceBundle languageBundle;
	
	protected final MainController applicationController;
	
	protected Shell shell;
	protected StyledText txtEditField;
	
	protected List<String> contentList;
	protected List<Boolean> isFileSavedList;
	protected List<String> fileNameList;
	
	protected int tabIndex;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainView window = new MainView();
			window.open();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Constructor
	 */
	public MainView() {
		this.languageBundle = LanguageUtility.getDefault();
		this.applicationController = new MainController();
		this.contentList = new ArrayList<String>();
		this.fileNameList = new ArrayList<String>();
		this.isFileSavedList = new ArrayList<Boolean>();
		
		this.tabIndex = 0;
		
		this.contentList.add(tabIndex, "");
		this.fileNameList.add(tabIndex, "");
		this.isFileSavedList.add(tabIndex, false);
	}
	
	
	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		this.shell.open();
		this.shell.layout();
		while (!this.shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		this.shell = new Shell();
		this.shell.setSize(960, 540);
		this.shell.setText("SimpleTextEditor");
		this.shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		// Top Menu
		Menu menu = new Menu(this.shell, SWT.BAR);
		this.shell.setMenuBar(menu);
		
		// Cascading MenuItem File
		MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText(this.languageBundle.getString("file"));
		
		// Menu File
		Menu mnFile = new Menu(mntmFile);
		mntmFile.setMenu(mnFile);

		// MenuItem New
		MenuItem mntmNew = new MenuItem(mnFile, SWT.PUSH);
		mntmNew.setText(this.languageBundle.getString("new") + "\tCtrl+N");
		mntmNew.setAccelerator(SWT.MOD1 + 'N');
		Image imgNew = new Image(this.shell.getDisplay(), getClass().getResourceAsStream("/images/new.png"));
		mntmNew.setImage(imgNew);
		mntmNew.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				System.out.println("New selected");
				System.out.println(isFileSavedList.get(tabIndex));
				System.out.println(fileNameList.get(tabIndex));
				if (!isFileSavedList.get(tabIndex) && !fileNameList.get(tabIndex).equals("")) {
			        System.out.println("Entering if-statement");
					MessageBox messageBox = new MessageBox(shell, SWT.YES | SWT.NO);
			        messageBox.setMessage(languageBundle.getString("unsaved.changes"));
			        messageBox.setText(languageBundle.getString("new.file"));
			        int response = messageBox.open();
			        
			        boolean success;
			        if (response == SWT.NO) {
			        	success = MainView.this.applicationController.saveFileAs(MainView.this.shell, MainView.this.fileNameList.get(tabIndex), Files.getFileExtension(MainView.this.fileNameList.get(tabIndex)), MainView.this.txtEditField.getText());
			        } else if (response == SWT.YES) {
			        	contentList.set(tabIndex, "");
			        }
				} else {
					txtEditField.setText(contentList.get(tabIndex));
				}
			}
		});
		
		// MenuItem Open
		MenuItem mntmOpen = new MenuItem(mnFile, SWT.PUSH);
		mntmOpen.setText(this.languageBundle.getString("open") + "\tCtrl+O");
		mntmOpen.setAccelerator(SWT.MOD1 + 'O');
		Image imgOpen = new Image(this.shell.getDisplay(), getClass().getResourceAsStream("/images/open.png"));
		mntmOpen.setImage(imgOpen);
		mntmOpen.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				System.out.println("Open selected");
				
				String[] values = MainView.this.applicationController.openFile(MainView.this.shell);
				if (values != null) {
					MainView.this.contentList.set(tabIndex, values[1]);
					MainView.this.fileNameList.set(tabIndex, values[0]);
					MainView.this.isFileSavedList.set(tabIndex, true);
					
					MainView.this.txtEditField.setText(contentList.get(tabIndex));
					System.out.print(contentList.get(tabIndex));
				}
			}
		});
		
		// MenuItem Save
		MenuItem mntmSave = new MenuItem(mnFile, SWT.PUSH);
		mntmSave.setText(this.languageBundle.getString("save") + "\tCtrl+S");
		mntmSave.setAccelerator(SWT.MOD1 + 'S');
		Image imgSave = new Image(this.shell.getDisplay(), getClass().getResourceAsStream("/images/save.png"));
		mntmSave.setImage(imgSave);
		mntmSave.addListener(SWT.Selection, new Listener () {
			@Override
			public void handleEvent(Event event) {
				System.out.println("Save selected");
				if (MainView.this.isFileSavedList.get(tabIndex)) {
					// Do not prompt user as file is already on the file system
				} else {
					// Prompt user to save a new file
				}
			}
		});
		
		// MenuItem SaveAs
		MenuItem mntmSaveAs = new MenuItem(mnFile, SWT.PUSH);
		mntmSaveAs.setText(this.languageBundle.getString("save.as") + "\tCtrl+Alt+S");
		mntmSaveAs.setAccelerator(SWT.MOD1 + SWT.MOD3 + 'S');
		//Image imgSave = new Image(this.shell.getDisplay(), getClass().getResourceAsStream("/images/saveas.png"));
		//mntmSaveAs.setImage(imgSave);
		mntmSaveAs.addListener(SWT.Selection, new Listener () {
			@Override
			public void handleEvent(Event event) {
				System.out.println("Save as selected");
				System.out.println(MainView.this.isFileSavedList.get(tabIndex));
				boolean success = false;
				if (MainView.this.isFileSavedList.get(tabIndex)) {
					System.out.println("Entering if-statement");
					success = MainView.this.applicationController.saveFileAs(MainView.this.shell, MainView.this.fileNameList.get(tabIndex), Files.getFileExtension(MainView.this.fileNameList.get(tabIndex)), MainView.this.contentList.get(tabIndex));
				} else {
					System.out.println("Entering else-statement");
					success = MainView.this.applicationController.saveFileAs(MainView.this.shell, MainView.this.contentList.get(tabIndex));
				}
				
				if (success) {
					System.out.println("File saved!");
				} else {
					System.out.println("Something went wrong.");
				}
			}
		});
		
		// Separator between Save As and Exit
	    new MenuItem(mnFile, SWT.SEPARATOR);
		
	    // MenuItem Exit
		MenuItem mntmExit = new MenuItem(mnFile, SWT.PUSH);
		mntmExit.setText(this.languageBundle.getString("exit") + "\tAlt+F4");
		//mntmExit.setAccelerator(SWT.MOD3 + 'F4');
		Image imgExit = new Image(this.shell.getDisplay(), this.getClass().getResourceAsStream("/images/exit.png"));
		mntmExit.setImage(imgExit);
		mntmExit.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				System.out.println("Exit selected");
				MainView.this.shell.getDisplay().dispose();
			}
		});
		
		// Cascading MenuItem Edit
		MenuItem mntmEdit = new MenuItem(menu, SWT.CASCADE);
		mntmEdit.setText(this.languageBundle.getString("edit"));
		
		// Menu Edit
		Menu mnEdit = new Menu(mntmEdit);
		mntmEdit.setMenu(mnEdit);
		
		// MenuItem Undo
		MenuItem mntmUndo = new MenuItem(mnEdit, SWT.PUSH);
		mntmUndo.setText(this.languageBundle.getString("undo") + "\tCtrl+Z");
		mntmUndo.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				System.out.println("Undo selected");
			}
		});
		
		// Text EditField
		this.txtEditField = new StyledText(this.shell, SWT.WRAP | SWT.MULTI | SWT.V_SCROLL);
		this.txtEditField.setTopMargin(4);
		this.txtEditField.setBottomMargin(4);
		this.txtEditField.setRightMargin(4);
		this.txtEditField.setLeftMargin(4);
		this.txtEditField.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		this.txtEditField.setAlwaysShowScrollBars(false);
	}
}
