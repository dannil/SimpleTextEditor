package org.dannil.simpletexteditor;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.dannil.simpletexteditor.controller.MainController;
import org.dannil.simpletexteditor.model.Document;
import org.dannil.simpletexteditor.utility.LanguageUtility;
import org.eclipse.swt.custom.ExtendedModifyEvent;
import org.eclipse.swt.custom.ExtendedModifyListener;
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

public final class MainView {

	protected final ResourceBundle languageBundle;
	
	protected final MainController applicationController;
	
	protected Shell shlMain;
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
		
		this.contentList.add(this.tabIndex, "");
		this.fileNameList.add(this.tabIndex, "");
		this.isFileSavedList.add(this.tabIndex, true);
	}
	
	
	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		this.shlMain.open();
		this.shlMain.layout();
		while (!this.shlMain.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		System.out.println(MainView.this.isFileSavedList.get(this.tabIndex));
		
		this.shlMain = new Shell();
		this.shlMain.setSize(960, 540);
		this.shlMain.setText(LanguageUtility.getStringWithCheck("app.name", this.languageBundle));
		this.shlMain.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		// Top Menu
		Menu mnuMain = new Menu(this.shlMain, SWT.BAR);
		this.shlMain.setMenuBar(mnuMain);
		
		// Cascading MenuItem File
		MenuItem mntmFile = new MenuItem(mnuMain, SWT.CASCADE);
		mntmFile.setText(LanguageUtility.getStringWithCheck("mainview.file", this.languageBundle));
		
		// Menu File
		Menu mnFile = new Menu(mntmFile);
		mntmFile.setMenu(mnFile);

		// MenuItem New
		MenuItem mndtmNew = new MenuItem(mnFile, SWT.PUSH);
		mndtmNew.setText(LanguageUtility.getStringWithCheck("mainview.new", this.languageBundle) + "\tCtrl+N");
		mndtmNew.setAccelerator(SWT.MOD1 + 'N');
		Image imgNew = new Image(this.shlMain.getDisplay(), getClass().getResourceAsStream("/images/new.png"));
		mndtmNew.setImage(imgNew);
		mndtmNew.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				System.out.println("New selected");
				System.out.println("Is file saved? : " + MainView.this.isFileSavedList.get(MainView.this.tabIndex));
				System.out.println(MainView.this.fileNameList.get(MainView.this.tabIndex));
				if (!MainView.this.isFileSavedList.get(MainView.this.tabIndex)) {
			        System.out.println("Entering if-statement");
					MessageBox messageBox = new MessageBox(MainView.this.shlMain, SWT.YES | SWT.NO);
			        messageBox.setMessage(LanguageUtility.getStringWithCheck("mainview.unsaved.changes", MainView.this.languageBundle));
			        messageBox.setText(LanguageUtility.getStringWithCheck("mainview.new.file", MainView.this.languageBundle));
			        int response = messageBox.open();
			        
			        if (response == SWT.NO) {
			        	System.out.println("Response was NO");
			        	Document document = new Document(MainView.this.fileNameList.get(MainView.this.tabIndex), MainView.this.txtEditField.getText());
			        	boolean success = MainView.this.applicationController.saveFileAs(MainView.this.shlMain, document);
			        	if (success) {
			        		MainView.this.isFileSavedList.set(MainView.this.tabIndex, true);
			        	}
			        } else if (response == SWT.YES) {
			        	System.out.println("Response was YES");
			        	MainView.this.contentList.set(MainView.this.tabIndex, "");
			        	System.out.println(MainView.this.contentList.get(MainView.this.tabIndex));
			        	MainView.this.isFileSavedList.set(MainView.this.tabIndex, true);
			        	MainView.this.txtEditField.setText(MainView.this.contentList.get(MainView.this.tabIndex));
			        }
				}
			}
		});
		
		// MenuItem Open
		MenuItem mndtmOpen = new MenuItem(mnFile, SWT.PUSH);
		mndtmOpen.setText(LanguageUtility.getStringWithCheck("mainview.open", this.languageBundle) + "\tCtrl+O");
		mndtmOpen.setAccelerator(SWT.MOD1 + 'O');
		Image imgOpen = new Image(this.shlMain.getDisplay(), getClass().getResourceAsStream("/images/open.png"));
		mndtmOpen.setImage(imgOpen);
		mndtmOpen.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				System.out.println("Open selected");
				
				String[] values = MainView.this.applicationController.openFile(MainView.this.shlMain);
				if (values != null) {
					MainView.this.contentList.set(MainView.this.tabIndex, values[1]);
					MainView.this.fileNameList.set(MainView.this.tabIndex, values[0]);
					MainView.this.isFileSavedList.set(MainView.this.tabIndex, true);
					
					MainView.this.txtEditField.setText(MainView.this.contentList.get(MainView.this.tabIndex));
					
					MainView.this.shlMain.setText(LanguageUtility.getStringWithCheck("app.name", MainView.this.languageBundle) + " - " + Files.getNameWithoutExtension(MainView.this.fileNameList.get(MainView.this.tabIndex)) + "." + Files.getFileExtension(MainView.this.fileNameList.get(MainView.this.tabIndex)));
					
					System.out.println(MainView.this.contentList.get(MainView.this.tabIndex));
				}
			}
		});
		
		// MenuItem Save
		MenuItem mndtmSave = new MenuItem(mnFile, SWT.PUSH);
		mndtmSave.setText(LanguageUtility.getStringWithCheck("mainview.save", this.languageBundle) + "\tCtrl+S");
		mndtmSave.setAccelerator(SWT.MOD1 + 'S');
		Image imgSave = new Image(this.shlMain.getDisplay(), getClass().getResourceAsStream("/images/save.png"));
		mndtmSave.setImage(imgSave);
		mndtmSave.addListener(SWT.Selection, new Listener () {
			@Override
			public void handleEvent(Event event) {
				System.out.println("Save selected");
				if (MainView.this.isFileSavedList.get(MainView.this.tabIndex)) {
					// Do not prompt user as file is already on the file system
				} else {
					// Prompt user to save a new file
				}
			}
		});
		
		// MenuItem SaveAs
		MenuItem mndtmSaveAs = new MenuItem(mnFile, SWT.PUSH);
		mndtmSaveAs.setText(LanguageUtility.getStringWithCheck("mainview.save.as", this.languageBundle) + "\tCtrl+Alt+S");
		mndtmSaveAs.setAccelerator(SWT.MOD1 + SWT.MOD3 + 'S');
		//Image imgSave = new Image(this.shell.getDisplay(), getClass().getResourceAsStream("/images/saveas.png"));
		//mntmSaveAs.setImage(imgSave);
		mndtmSaveAs.addListener(SWT.Selection, new Listener () {
			@Override
			public void handleEvent(Event event) {
				System.out.println("Save as selected");
				System.out.println(MainView.this.isFileSavedList.get(MainView.this.tabIndex));
				System.out.println(MainView.this.contentList.get(MainView.this.tabIndex));
				
				boolean success = false;
				String content = MainView.this.contentList.get(MainView.this.tabIndex);
				if (MainView.this.isFileSavedList.get(MainView.this.tabIndex)) {
					System.out.println("Entering if-statement");
					Document document = new Document(MainView.this.fileNameList.get(MainView.this.tabIndex), content);
					success = MainView.this.applicationController.saveFileAs(MainView.this.shlMain, document);
				} else {
					System.out.println("Entering else-statement");
					success = MainView.this.applicationController.saveFileAs(MainView.this.shlMain, content);
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
		MenuItem mndtmExit = new MenuItem(mnFile, SWT.PUSH);
		mndtmExit.setText(LanguageUtility.getStringWithCheck("mainview.exit", this.languageBundle) + "\tAlt+F4");
		mndtmExit.setAccelerator(SWT.MOD3 + SWT.F4);
		Image imgExit = new Image(this.shlMain.getDisplay(), this.getClass().getResourceAsStream("/images/exit.png"));
		mndtmExit.setImage(imgExit);
		mndtmExit.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				System.out.println("Exit selected");
				MainView.this.shlMain.getDisplay().dispose();
			}
		});
		
		// Cascading MenuItem Edit
		MenuItem mntmEdit = new MenuItem(mnuMain, SWT.CASCADE);
		mntmEdit.setText(LanguageUtility.getStringWithCheck("mainview.edit", this.languageBundle));
		
		// Menu Edit
		Menu mnEdit = new Menu(mntmEdit);
		mntmEdit.setMenu(mnEdit);
		
		// MenuItem Undo
		MenuItem mndtmUndo = new MenuItem(mnEdit, SWT.PUSH);
		mndtmUndo.setText(LanguageUtility.getStringWithCheck("mainview.undo", this.languageBundle) + "\tCtrl+Z");
		mndtmUndo.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				System.out.println("Undo selected");
			}
		});
		
		// Cascading MenuItem Settings
		MenuItem mntmSettings = new MenuItem(mnuMain, SWT.CASCADE);
		mntmSettings.setText(LanguageUtility.getStringWithCheck("mainview.settings", this.languageBundle));
		
		// Menu Settings
		Menu mnSettings = new Menu(mntmSettings);
		mntmSettings.setMenu(mnSettings);
		
		// MenuItem Settings
		MenuItem mndtmSettings = new MenuItem(mnSettings, SWT.NONE);
		mndtmSettings.setText(LanguageUtility.getStringWithCheck("mainview.settings", this.languageBundle));
		mndtmSettings.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				System.out.println("Settings selected");
				SettingsView settingsView = new SettingsView();
				settingsView.open();
			}
		});
		
		// Text EditField
		this.txtEditField = new StyledText(this.shlMain, SWT.WRAP | SWT.MULTI | SWT.V_SCROLL);
		this.txtEditField.setTopMargin(4);
		this.txtEditField.setBottomMargin(4);
		this.txtEditField.setRightMargin(4);
		this.txtEditField.setLeftMargin(4);
		this.txtEditField.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		this.txtEditField.setAlwaysShowScrollBars(false);
		
		this.txtEditField.addExtendedModifyListener(new ExtendedModifyListener() {
			@Override
			public void modifyText(ExtendedModifyEvent event) {
				System.out.println("Text changed");
				String content = MainView.this.txtEditField.getText();
				if (!content.equals("")) {
					MainView.this.isFileSavedList.set(MainView.this.tabIndex, false);
				}
				MainView.this.contentList.set(MainView.this.tabIndex, MainView.this.txtEditField.getText());
			}
		});
	}
}
