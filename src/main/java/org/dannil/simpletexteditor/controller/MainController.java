package org.dannil.simpletexteditor.controller;

import java.io.IOException;

import org.dannil.simpletexteditor.event.Event;
import org.dannil.simpletexteditor.model.Document;
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
	
	public boolean saveFileAs(Shell shell, Document document) {
		try {
			return this.event.saveFileAs(shell, document);
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
}
