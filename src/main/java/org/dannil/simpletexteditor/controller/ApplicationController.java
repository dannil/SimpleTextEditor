package org.dannil.simpletexteditor.controller;

import java.io.IOException;

import org.dannil.simpletexteditor.event.Event;
import org.eclipse.swt.widgets.Shell;

public final class ApplicationController {

	Event event;
	
	public ApplicationController() {
		this.event = new Event();
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
