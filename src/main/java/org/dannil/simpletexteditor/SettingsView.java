package org.dannil.simpletexteditor;

import java.util.ResourceBundle;

import org.dannil.simpletexteditor.utility.LanguageUtility;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

public final class SettingsView {

	protected final ResourceBundle languageBundle;
	
	protected Shell shlSettings;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	/*
	public static void main(String[] args) {
		try {
			SettingsView window = new SettingsView();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Constructor
	 */
	public SettingsView() {
		this.languageBundle = LanguageUtility.getDefault();
	}
	
	/**
	 * Open the window.
	 * @wbp.parser.entryPoint
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		this.shlSettings.open();
		this.shlSettings.layout();
		while (!this.shlSettings.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		this.shlSettings = new Shell(SWT.CLOSE | SWT.TITLE | SWT.APPLICATION_MODAL);
		this.shlSettings.setSize(540, 440);
		this.shlSettings.setText(this.languageBundle.getString("settingsview.settings"));
		this.shlSettings.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		TabFolder tbfSettings = new TabFolder(this.shlSettings, SWT.FILL);
		
		TabItem tbtmGeneral = new TabItem(tbfSettings, SWT.NONE);
		tbtmGeneral.setText(this.languageBundle.getString("settingsview.general"));
		
		TabItem tbtmFonts = new TabItem(tbfSettings, SWT.NONE);
		tbtmFonts.setText(this.languageBundle.getString("settingsview.fonts"));
	}
}
