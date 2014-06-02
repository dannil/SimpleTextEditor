package org.dannil.simpletexteditor.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.dannil.simpletexteditor.event.MenuEvent;
import org.eclipse.swt.widgets.Shell;

public final class ApplicationController {

	MenuEvent event;
	
	public ApplicationController() {
		this.event = new MenuEvent();
	}
	
	public String openFile(Shell shell) {
		try {
			return this.event.openFile(shell);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}
