package org.dannil.simpletexteditor.event;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ResourceBundle;

import org.dannil.simpletexteditor.utility.LanguageUtility;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

public class Event {
	
	private final String[] FILTER_NAMES = { "Text (*.txt)", "HTML (*.html, *.xhtml)" };
	private final String[] FILTER_EXT = { "*.txt", "*.html;*.xhtml"/*"*.doc", ".rtf", "*.*"*/ };
	
	ResourceBundle languageBundle;
	
	public Event() {
		this.languageBundle = LanguageUtility.getDefault();
	}
	
	public String openFile(Shell shell) throws IOException {
        FileDialog fd = new FileDialog(shell, SWT.OPEN);
        fd.setText(this.languageBundle.getString("open.file"));
        fd.setFilterPath("C:/Users/Daniel/Desktop");
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
        return "";
        //System.out.println("File non-existant.");
		//return "";
	}
	
}
