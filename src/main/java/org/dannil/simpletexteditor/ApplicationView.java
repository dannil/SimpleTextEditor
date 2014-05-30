package org.dannil.simpletexteditor;

import java.io.InputStream;

import javax.swing.ImageIcon;

import org.dannil.simpletexteditor.controller.ApplicationController;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Label;

public final class ApplicationView {

	private final ApplicationController applicationController;
	
	protected Shell shell;
	private Text txtMainEditField;

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
		this.shell.setSize(700, 451);
		this.shell.setText("Simple Text Editor");
		this.shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Menu menu = new Menu(this.shell, SWT.BAR);
		this.shell.setMenuBar(menu);
		
		MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText("File");
		
		Menu mnFile = new Menu(mntmFile);
		mntmFile.setMenu(mnFile);

		MenuItem mntmNew = new MenuItem(mnFile, SWT.PUSH);
		mntmNew.setText("New");
		Image imgNew = new Image(this.shell.getDisplay(), getClass().getResourceAsStream("/images/new.png"));
		mntmNew.setImage(imgNew);
		
		MenuItem mntmOpen = new MenuItem(mnFile, SWT.PUSH);
		mntmOpen.setText("Open");
		Image imgOpen = new Image(this.shell.getDisplay(), getClass().getResourceAsStream("/images/open.png"));
		mntmOpen.setImage(imgOpen);
		
		MenuItem mntmSave = new MenuItem(mnFile, SWT.PUSH);
		mntmSave.setText("Save");
		Image imgSave = new Image(this.shell.getDisplay(), getClass().getResourceAsStream("/images/save.png"));
		mntmSave.setImage(imgSave);
		
	    new MenuItem(mnFile, SWT.SEPARATOR);
		
		MenuItem mntmExit = new MenuItem(mnFile, SWT.PUSH);
		mntmExit.setText("Exit");
		Image imgExit = new Image(this.shell.getDisplay(), this.getClass().getResourceAsStream("/images/exit.png"));
		mntmExit.setImage(imgExit);
		
		this.txtMainEditField = new Text(this.shell, SWT.BORDER);

		
		//force new build
	}
}
