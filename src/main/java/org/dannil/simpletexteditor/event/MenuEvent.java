package org.dannil.simpletexteditor.event;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.Scanner;

import org.dannil.simpletexteditor.utility.LanguageUtility;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

public class MenuEvent {
	
	private final String[] FILTER_NAMES = { "Text (*.txt)", "HTML (*.html, *.xhtml)" };
	private final String[] FILTER_EXT = { "*.txt", "*.html;*.xhtml"/*"*.doc", ".rtf", "*.*"*/ };
	
	//ResourceBundle languageBundle;
	
	public MenuEvent() {
		//languageBundle = LanguageUtility.getDefault();
	}
	
	public String openFile(Shell shell) throws IOException {
        FileDialog fd = new FileDialog(shell, SWT.OPEN);
        fd.setText("Open file");
        fd.setFilterPath("C:/");
        fd.setFilterNames(this.FILTER_NAMES);
        fd.setFilterExtensions(this.FILTER_EXT);
        String path = fd.open();
        if (path != null) {
        	File file = new File(path);
        	BufferedReader br = new BufferedReader(new FileReader(file));
         
        	String content = "";
        	String line = null;
        	while ((line = br.readLine()) != null) {
        		content += line;
    			content += System.getProperty("line.separator");
        	}
        	
        	br.close();
        	
        	return content;
        }
        System.out.println("File non-existant.");
		return "";
	}
	
}
