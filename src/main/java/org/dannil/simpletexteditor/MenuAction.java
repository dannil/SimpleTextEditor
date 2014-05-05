package org.dannil.simpletexteditor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JTextArea;

public class MenuAction {
	
	String textAreaContent;
	String appName;
	String appTitle;
	String fileName;
	String filePath;
	boolean existingFile;
	
	public MenuAction() {
		System.out.println(App.applicationName);
	}
	
	public void actionNewActionListener() {
		appTitle = App.applicationName + " - *new";
		textAreaContent = "";
		existingFile = false;
	}
	
	public void actionOpenActionListener() {
		JFileChooser openFile = new JFileChooser();
        int returnVal = openFile.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
        	try {
        		fileName = openFile.getSelectedFile().getName();
        		filePath = openFile.getSelectedFile().getCanonicalPath();
        		BufferedReader br = new BufferedReader(new FileReader(openFile.getSelectedFile()));
        		StringBuilder sb = new StringBuilder();
        		String line = br.readLine();
        		while(line != null) {
        			sb.append(line);
        			sb.append("\n");
        			line = br.readLine();
        		}
        		if(sb.length() != 0) {
        			sb.setLength(sb.length() - 1);
        		}
        		textAreaContent = sb.toString();
        		br.close();
        		existingFile = true;
        	} catch (IOException e) {
        		e.printStackTrace();
        	}
    		appTitle = App.applicationName + " - " + filePath;
        } else if(returnVal == JFileChooser.CANCEL_OPTION) {
        	if(existingFile) {
        		appTitle = App.applicationName + " - " + filePath;
        	} else {
            	appTitle = App.applicationName;
        	}
        }
	}
	
	public void actionSaveActionListener(JTextArea area) {
		textAreaContent = area.getText();
		if(!existingFile) {
			existingFile = !existingFile;
    		JFileChooser saveFile = new JFileChooser();
            int returnVal = saveFile.showSaveDialog(null);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
				try {
            		BufferedWriter bw = new BufferedWriter(new FileWriter(saveFile.getSelectedFile()));
            		bw.write(textAreaContent);
            		bw.close();
	        		filePath = saveFile.getSelectedFile().getCanonicalPath();
				} catch (IOException e) {
					e.printStackTrace();
				}
        		appTitle = appTitle + " - " + filePath;
            }
		} else {
			try {
        		BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
        		bw.write(textAreaContent);
        		bw.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getAppName() {
		return appName;
	}
	
	public String getAppTitle() {
		return appTitle;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public String getTextAreaContent() {
		return textAreaContent;
	}
	
	public boolean getExistingFile() {
		return existingFile;
	}
	
}