package org.dannil.simpletexteditor;

import org.dannil.simpletexteditor.controller.ApplicationController;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.wb.swt.SWTResourceManager;

public final class ApplicationView {

	//private final ResourceBundle languageBundle;
	
	final ApplicationController applicationController;
	
	protected Shell shell;
	protected Text txtEditField;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ApplicationView window = new ApplicationView();
			window.open();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Constructor
	 */
	public ApplicationView() {
		//this.languageBundle = LanguageUtility.getDefault();
		this.applicationController = new ApplicationController();
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
		this.shell.setSize(700, 450);
		this.shell.setText("SimpleTextEditor");
		this.shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		// Top Menu
		Menu menu = new Menu(this.shell, SWT.BAR);
		this.shell.setMenuBar(menu);
		
		// Cascading MenuItem File
		MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText("File");
		
		// Menu File
		Menu mnFile = new Menu(mntmFile);
		mntmFile.setMenu(mnFile);

		// MenuItem New
		MenuItem mntmNew = new MenuItem(mnFile, SWT.PUSH);
		mntmNew.setText("New\tCtrl+N");
		mntmNew.setAccelerator(SWT.MOD1 + 'N');
		Image imgNew = new Image(this.shell.getDisplay(), getClass().getResourceAsStream("/images/new.png"));
		mntmNew.setImage(imgNew);
		mntmNew.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				System.out.println("New selected");
			}
		});
		
		// MenuItem Open
		MenuItem mntmOpen = new MenuItem(mnFile, SWT.PUSH);
		mntmOpen.setText("Open\tCtrl+O");
		mntmOpen.setAccelerator(SWT.MOD1 + 'O');
		Image imgOpen = new Image(this.shell.getDisplay(), getClass().getResourceAsStream("/images/open.png"));
		mntmOpen.setImage(imgOpen);
		mntmOpen.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				System.out.println("Open selected");
				String file;
				ApplicationView.this.txtEditField.setText(file = ApplicationView.this.applicationController.openFile(ApplicationView.this.shell));
				System.out.println(file);
			}
		});
		
		// MenuItem Save
		MenuItem mntmSave = new MenuItem(mnFile, SWT.PUSH);
		mntmSave.setText("Save\tCtrl+S");
		mntmSave.setAccelerator(SWT.MOD1 + 'S');
		Image imgSave = new Image(this.shell.getDisplay(), getClass().getResourceAsStream("/images/save.png"));
		mntmSave.setImage(imgSave);
		
		// Separator between Save and Exit
	    new MenuItem(mnFile, SWT.SEPARATOR);
		
	    // MenuItem Exit
		MenuItem mntmExit = new MenuItem(mnFile, SWT.PUSH);
		mntmExit.setText("Exit\tAlt+F4");
		//mntmExit.setAccelerator(SWT.MOD3 + 'F4');
		Image imgExit = new Image(this.shell.getDisplay(), this.getClass().getResourceAsStream("/images/exit.png"));
		mntmExit.setImage(imgExit);
		mntmExit.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				ApplicationView.this.shell.getDisplay().dispose();
			}
		});
		
		// Cascading MenuItem Edit
		MenuItem mntmEdit = new MenuItem(menu, SWT.CASCADE);
		mntmEdit.setText("Edit");
		
		// Menu Edit
		Menu mnEdit = new Menu(mntmEdit);
		mntmEdit.setMenu(mnEdit);
		
		// MenuItem Undo
		MenuItem mntmUndo = new MenuItem(mnEdit, SWT.PUSH);
		mntmUndo.setText("Undo");
		
		this.txtEditField = new Text(this.shell, SWT.WRAP | SWT.MULTI);
		this.txtEditField.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
	}
}
