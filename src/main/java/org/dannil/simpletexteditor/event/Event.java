package org.dannil.simpletexteditor.event;

import java.io.File;
import java.util.ResourceBundle;

import org.dannil.simpletexteditor.utility.LanguageUtility;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

public class Event {
	
	//ResourceBundle languageBundle;
	
	public Event() {
		//languageBundle = LanguageUtility.getDefault();
	}
	
	public String openFile(Shell shell) {
        FileDialog fd = new FileDialog(shell, SWT.OPEN);
        fd.setText("Open");
        fd.setFilterPath("C:/");
        String[] filterExt = { "*.txt", /*"*.doc", ".rtf", "*.*"*/ };
        fd.setFilterExtensions(filterExt);
        String selected = fd.open();
        //System.out.println(selected);
        if (selected != null) {
            // Append all the selected files. Since getFileNames() returns only 
            // the names, and not the path, prepend the path, normalizing
            // if necessary
            StringBuffer buf = new StringBuffer();
            String[] files = fd.getFileNames();
            for (int i = 0, n = files.length; i < n; i++) {
            	buf.append(fd.getFilterPath());
            	if (buf.charAt(buf.length() - 1) != File.separatorChar) {
            		buf.append(File.separatorChar);
            	}
            	buf.append(files[i]);
            	buf.append(" ");
            }
            return buf.toString();
        }
		return "";
	}
	
}
