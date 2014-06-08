package org.dannil.simpletexteditor.event;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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
	
	public String[] openFile(Shell shell) throws IOException {
        FileDialog fd = new FileDialog(shell, SWT.OPEN);
        fd.setText(this.languageBundle.getString("open.file"));
        fd.setFilterPath("C:/Users/Daniel/Desktop");
        fd.setFilterNames(this.FILTER_NAMES);
        fd.setFilterExtensions(this.FILTER_EXT);
        String path = fd.open();
        if (path != null) {
        	File file = new File(path);
        	BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file.toString()), "utf-8"));
         
        	String content = "";
        	String line = "";
        	int i = 0;
        	while ((line = br.readLine()) != null) {
        		if (i > 0) {
        			content += System.getProperty("line.separator");
        		}
        		i++;
        		content += line;
        	}
        	
        	br.close();
        	
        	String[] returnValues = new String[2];
        	returnValues[0] = path;
        	returnValues[1] = content;
        	
        	return returnValues;
        }
        return null;
        //System.out.println("File non-existant.");
		//return "";
	}
	
	public boolean saveFileAs(Shell shell, String content) throws IOException {
        FileDialog fd = new FileDialog(shell, SWT.SAVE);
        fd.setText(this.languageBundle.getString("save.file.as"));
        fd.setFilterPath("C:/Users/Daniel/Desktop");
        fd.setFilterNames(this.FILTER_NAMES);
        fd.setFilterExtensions(this.FILTER_EXT);
		String path = fd.open();
		if (path != null) {
			File file = new File(path);
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			output.write(content);
			output.close();
		}
		return false;
	}
	
	public boolean saveFileAs(Shell shell, String fileName, String fileExtension, String content) throws IOException {
        FileDialog fd = new FileDialog(shell, SWT.SAVE);
        fd.setText(this.languageBundle.getString("save.file.as"));
        fd.setFilterPath("C:/Users/Daniel/Desktop");
        fd.setFilterNames(this.FILTER_NAMES);
        fd.setFilterExtensions(this.FILTER_EXT);
        System.out.println(fileName);
        fd.setFileName(fileName);
        
        for (int i = 0; i < this.FILTER_EXT.length; i++) {
        	//System.out.println("Stored ext: " + this.FILTER_EXT[i]);
        	//System.out.println("Supplied ext: " + ("*." + fileExtension));
        	if (this.FILTER_EXT[i].contains(("*." + fileExtension).toLowerCase())) {
                fd.setFilterIndex(i);
        	}
        }
        //System.out.println(fd.getFilterIndex());
        
		String path = fd.open();
		if (path != null) {
			File file = new File(path);
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			output.write(content);
			output.close();
		}
		return false;
	}
	
}
