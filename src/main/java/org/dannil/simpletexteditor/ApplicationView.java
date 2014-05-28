package org.dannil.simpletexteditor;

import org.dannil.simpletexteditor.controller.ApplicationController;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ApplicationView {

	private ApplicationController applicationController;
	
	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
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
	public void open()
	{
		Display display = Display.getDefault();
		createContents();
		this.shell.open();
		this.shell.layout();
		while (!this.shell.isDisposed())
		{
			if (!display.readAndDispatch())
			{
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents()
	{
		this.shell = new Shell();
		this.shell.setSize(450, 300);
		this.shell.setText("Simple Text Editor");

	}

}
