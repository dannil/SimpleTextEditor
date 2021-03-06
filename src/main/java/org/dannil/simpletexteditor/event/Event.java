package org.dannil.simpletexteditor.event;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ResourceBundle;

import org.dannil.simpletexteditor.model.Document;
import org.dannil.simpletexteditor.utility.LanguageUtility;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.common.io.Files;

final public class Event {
	
	private final String[] FILTER_NAMES = { "Text (*.txt)", "HTML (*.html, *.xhtml)" };
	private final String[] FILTER_EXT = { "*.txt", "*.html;*.xhtml"/*"*.doc", ".rtf", "*.*"*/ };
	
	private final ResourceBundle languageBundle;
	
	public Event() {
		this.languageBundle = LanguageUtility.getDefault();
	}
	
	public String[] openFile(Shell shell) throws IOException {
        final FileDialog fd = new FileDialog(shell, SWT.OPEN);
        fd.setText(LanguageUtility.getStringWithCheck("mainview.open.file", this.languageBundle));
        fd.setFilterPath("C:/Users/Daniel/Desktop");
        fd.setFilterNames(this.FILTER_NAMES);
        fd.setFilterExtensions(this.FILTER_EXT);
        final String path = fd.open();
        if (path != null) {
        	final FileInputStream fis = new FileInputStream(path);
        	final String content = CharStreams.toString(new InputStreamReader(fis, "UTF-8"));
        	
        	final String[] returnValues = new String[2];
        	returnValues[0] = path;
        	returnValues[1] = content;
        	
        	return returnValues;
        }
        return null;
        //System.out.println("File non-existant.");
		//return "";
	}
	
	public boolean saveFileAs(Shell shell, String content) throws IOException {
		final FileDialog fd = new FileDialog(shell, SWT.SAVE);
        fd.setText(LanguageUtility.getStringWithCheck("mainview.save.file.as", this.languageBundle));
        fd.setFilterPath("C:/Users/Daniel/Desktop");
        fd.setFilterNames(this.FILTER_NAMES);
        fd.setFilterExtensions(this.FILTER_EXT);
        final String path = fd.open();
		if (path != null) {
			final File file = new File(path);
			Files.write(content, file, Charsets.UTF_8);
			
			/*@SuppressWarnings("resource")
			final BufferedWriter output = new BufferedWriter(new FileWriter(file));
			System.out.println("Writing file...");
			output.write(content);
			output.close();*/
			
			return true;
		}
		return false;
	}
	
	public boolean saveFileAs(Shell shell, Document document) throws IOException {
		final FileDialog fd = new FileDialog(shell, SWT.SAVE);
        fd.setText(LanguageUtility.getStringWithCheck("mainview.save.file.as", this.languageBundle));
        fd.setFilterPath("C:/Users/Daniel/Desktop");
        fd.setFilterNames(this.FILTER_NAMES);
        fd.setFilterExtensions(this.FILTER_EXT);
        System.out.println(document.getPath());
        fd.setFileName(document.getPath());
        
        for (int i = 0; i < this.FILTER_EXT.length; i++) {
        	//System.out.println("Stored ext: " + this.FILTER_EXT[i]);
        	//System.out.println("Supplied ext: " + ("*." + fileExtension));
        	if (this.FILTER_EXT[i].contains(("*." + document.getExtension()).toLowerCase())) {
                fd.setFilterIndex(i);
        	}
        }
        //System.out.println(fd.getFilterIndex());
        
        final String path = fd.open();
		if (path != null) {
			File file = new File(path);
			Files.write(document.getContent(), file, Charsets.UTF_8);
			
			return true;
		}
		return false;
	}
	
}
