package org.dannil.simpletexteditor;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

class LogTextPanel extends JPanel {

    private final JTextArea textArea;

    public LogTextPanel() {
        super(new GridLayout(1, 1));

        textArea = new JTextArea();
        add(new JScrollPane(textArea));
    }

    public void append(String text) {
        textArea.append(text);
    }

    public String getText() {
        return textArea.getText();
    }
}