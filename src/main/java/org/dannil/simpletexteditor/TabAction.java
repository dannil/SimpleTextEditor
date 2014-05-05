package org.dannil.simpletexteditor;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class TabAction {
	
	public TabAction() {
		
	}
	
	public void addNewTab(String name, JTabbedPane pane) {
		JPanel p = new JPanel();
		pane.add(name, p);
	}
	
}
