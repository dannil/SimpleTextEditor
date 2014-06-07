package org.dannil.simpletexteditor.controller;

import java.io.IOException;

import org.dannil.simpletexteditor.event.Event;
import org.eclipse.swt.widgets.Shell;

public final class MainController {

	Event event;
	
	public MainController() {
		this.event = new Event();
	}
	
	public String[] openFile(Shell shell) {
		try {
			return this.event.openFile(shell);
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public boolean saveFileAs(Shell shell, String content) {
		try {
			return this.event.saveFileAs(shell, content);
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public boolean saveFileAs(Shell shell, String fileName, String fileExtension, String content) {
		try {
			return this.event.saveFileAs(shell, fileName, fileExtension, content);
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
}
