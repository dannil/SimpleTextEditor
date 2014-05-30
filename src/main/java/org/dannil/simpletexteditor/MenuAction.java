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
		this.appTitle = App.applicationName + " - *new";
		this.textAreaContent = "";
		this.existingFile = false;
	}
	
	public void actionOpenActionListener() {
		JFileChooser openFile = new JFileChooser();
        int returnVal = openFile.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
        	try {
        		this.fileName = openFile.getSelectedFile().getName();
        		this.filePath = openFile.getSelectedFile().getCanonicalPath();
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
        		this.textAreaContent = sb.toString();
        		br.close();
        		this.existingFile = true;
        	} catch (IOException e) {
        		e.printStackTrace();
        	}
    		this.appTitle = App.applicationName + " - " + this.filePath;
        } else if(returnVal == JFileChooser.CANCEL_OPTION) {
        	if(this.existingFile) {
        		this.appTitle = App.applicationName + " - " + this.filePath;
        	} else {
            	this.appTitle = App.applicationName;
        	}
        }
	}
	
	public void actionSaveActionListener(JTextArea area) {
		this.textAreaContent = area.getText();
		if(!this.existingFile) {
			this.existingFile = !this.existingFile;
    		JFileChooser saveFile = new JFileChooser();
            int returnVal = saveFile.showSaveDialog(null);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
				try {
            		BufferedWriter bw = new BufferedWriter(new FileWriter(saveFile.getSelectedFile()));
            		bw.write(this.textAreaContent);
            		bw.close();
	        		this.filePath = saveFile.getSelectedFile().getCanonicalPath();
				} catch (IOException e) {
					e.printStackTrace();
				}
        		this.appTitle = this.appTitle + " - " + this.filePath;
            }
		} else {
			try {
        		BufferedWriter bw = new BufferedWriter(new FileWriter(this.filePath));
        		bw.write(this.textAreaContent);
        		bw.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getAppName() {
		return this.appName;
	}
	
	public String getAppTitle() {
		return this.appTitle;
	}
	
	public String getFileName() {
		return this.fileName;
	}
	
	public String getFilePath() {
		return this.filePath;
	}
	
	public String getTextAreaContent() {
		return this.textAreaContent;
	}
	
	public boolean getExistingFile() {
		return this.existingFile;
	}
	
}