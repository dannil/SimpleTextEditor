package org.dannil.simpletexteditor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.undo.UndoManager;

public class App extends JFrame {
	
	private static final long serialVersionUID = 1L;
	public static final String applicationName = "Simple Text Editor";
	String lorem = "Integer auctor nisi ut neque consectetur, eu congue tortor pharetra. Integer lectus nunc, dignissim vel sem quis, lacinia malesuada magna. " +
					"Praesent sagittis vulputate viverra. Maecenas lobortis, tellus faucibus consequat bibendum, tellus magna fringilla neque," +
					"quis scelerisque tortor tortor quis enim. Vivamus tempus nisi non ante fringilla iaculis. Mauris egestas tincidunt elit, ut facilisis purus laoreet auctor. " +
					"Integer sodales auctor metus ut lobortis. Nam bibendum porta venenatis. Morbi eget magna ut nibh mattis pulvinar eu vitae ipsum. Proin convallis leo ac augue " +
					"euismod, vitae iaculis elit commodo. Vivamus malesuada tristique volutpat. Praesent elit elit, ornare a erat vel, lobortis condimentum est. Nulla pretium " +
					"malesuada justo ut tristique. Sed iaculis lacus ultrices, lobortis nibh a, convallis dui. Fusce varius orci ultrices elit gravida auctor. ";
	
	MenuAction action;
	JMenuBar menuBar;
    JToolBar menuToolBar;
	JTabbedPane tabbedPane;
	JTextArea textArea;
	JTextArea[] textArray;
	UndoManager undoManager;
	int activeTab;
	int totalTabCount;
	
	public App() {
		initUI();
	}
	
	public final void initUI() {
		setLayout(new BorderLayout());
		
		//Declare OO objects that's used through the document
		action = new MenuAction();
		menuBar = new JMenuBar();
		menuToolBar = new JToolBar();
		tabbedPane = new JTabbedPane();
		textArea = new JTextArea();
		textArray = new JTextArea[100];
		undoManager = new UndoManager();
		
		for(int i = 0; i < textArray.length; i++)
		{
		    textArray[i] = new JTextArea();
		}
		
		totalTabCount = 0;
		
		boolean crossPlatform = true;
		if(crossPlatform) {
			try {
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		//Build File menu and its options
		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		
        JMenuItem fileNew = new JMenuItem("New");
        fileNew.setToolTipText("Create a new document");
        fileNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        fileNew.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		actionNew();
        	}
        });
        
        JMenuItem fileOpen = new JMenuItem("Open");
        fileOpen.setToolTipText("Open an existing document");
        fileOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        fileOpen.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		actionOpen();
        	}
        });

        JMenuItem fileSave = new JMenuItem("Save");
        fileSave.setToolTipText("Save the current document");
        fileSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        fileSave.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		actionSave();
        	}
        });
		
        JMenuItem fileExit = new JMenuItem("Exit");
        fileExit.setToolTipText("Exit application");
        fileExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        fileExit.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        //Build Edit menu and its options
		JMenu edit = new JMenu("Edit");
		file.setMnemonic(KeyEvent.VK_E);
		
        JMenuItem editUndo = new JMenuItem("Undo");
        editUndo.setToolTipText("Undo your latest edit");
        editUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        editUndo.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
	            if(undoManager.canUndo()) {
	            	undoManager.undo();
	            }
        	}
        });
        
        JMenuItem editRedo = new JMenuItem("Redo");
        editRedo.setToolTipText("Redo your latest edit");
        editRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        editRedo.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
	            if (undoManager.canRedo()) {
	                undoManager.redo();
	            }
        	}
        });
        
        //Icon action toolbar
        ImageIcon iconNew = new ImageIcon(getClass().getResource("images/new.png"));
		ImageIcon iconOpen = new ImageIcon(getClass().getResource("images/open.png"));
		ImageIcon iconSave = new ImageIcon(getClass().getResource("images/save.png"));
		ImageIcon iconExit = new ImageIcon(getClass().getResource("images/exit.png"));
        
		JButton btnNew = new JButton(iconNew);
        btnNew.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		actionNew();
        	}
        });
		
		JButton btnOpen = new JButton(iconOpen);
        btnOpen.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		actionOpen();
        	}
        });
		
		JButton btnSave = new JButton(iconSave);
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
        		/*tabbedPane.removeTabAt(3);
        		for(int i = tabbedPane.getTabCount(); i >= 3; i--) {
        			textArray[i] = textArray[i - 1];
        		}*/
            }
        });
		
		//Tab bar
		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				activeTab = tabbedPane.getSelectedIndex();
				System.out.println(activeTab + " - " + textArray[activeTab].getText());
			}
		});
		
		//Text area
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				textArray[activeTab].requestFocus();
			}
		});
		
		Document doc = textArray[activeTab].getDocument();
		doc.addUndoableEditListener(new UndoableEditListener() {
		    @Override
		    public void undoableEditHappened(UndoableEditEvent e) {
		        //System.out.println("Add edit");
		        undoManager.addEdit(e.getEdit());
		    }
		});

		InputMap im = this.textArray[activeTab].getInputMap(JComponent.WHEN_FOCUSED);
		ActionMap am = this.textArray[activeTab].getActionMap();

		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "Undo");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "Redo");

		am.put("Undo", new AbstractAction() {
			@Override
		    public void actionPerformed(ActionEvent e) {
	            if (undoManager.canUndo()) {
	                undoManager.undo();
	            }
		    }
		});
		am.put("Redo", new AbstractAction() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
	            if (undoManager.canRedo()) {
	                undoManager.redo();
	            }
		    }
		});
		
		// --- ADD COMPONENTS TO MAIN JFRAME --- //
		
		//Toolbars; action menu and tabs options
		JPanel toolBarPanel = new JPanel();
		toolBarPanel.setLayout(new BoxLayout(toolBarPanel, BoxLayout.Y_AXIS));
		
		menuToolBar.setFloatable(false);
		menuToolBar.add(btnNew, BorderLayout.WEST);
		menuToolBar.add(btnOpen, BorderLayout.WEST);
		menuToolBar.add(btnSave, BorderLayout.WEST);
		menuToolBar.setAlignmentX(0);

		//ImageIcon iconDocument = new ImageIcon(getClass().getResource("/images/doc.png"));
		
        tabbedPane.addTab("*new", createInnerTextArea("This is the first tab.", textArray[0]));
        
		tabbedPane.setAlignmentX(0);
		
		toolBarPanel.add(menuToolBar);
		
		add(toolBarPanel, BorderLayout.NORTH);
		
        //File menu option
		file.add(fileNew);
		file.add(fileOpen);
		file.add(fileSave);
		file.addSeparator();
		file.add(fileExit);
		
		//Edit menu option
		edit.add(editUndo);
		edit.add(editRedo);
		
		//Menubar
		menuBar.add(file);
		menuBar.add(edit);
		
		setJMenuBar(menuBar);
		
		add(tabbedPane);
		
		//General JFrame options
        setTitle(applicationName + " - *new");
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResource("/images/icon32.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setIconImage(image);
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int)(size.getWidth() / 1.7), (int)(size.getHeight() / 1.4));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public JScrollPane createInnerTextArea(String text, JTextArea area) {
        area.setLayout(new GridLayout(1, 1));
        area.setEditable(true);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setMargin(new Insets(1, 1, 1, 1));
        area.requestFocusInWindow();
        
		JScrollPane scroll = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        return scroll;
    }
    
    public void actionNew() {
		action.actionNewActionListener();
		setTitle(action.getAppTitle());
		tabbedPane.addTab("*new", createInnerTextArea("", textArray[tabbedPane.getTabCount()]));
		tabbedPane.setTitleAt(tabbedPane.getTabCount() - 1, "*new");
		tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
		textArray[activeTab].setText("ID: " + tabbedPane.getSelectedIndex());
		textArray[activeTab].requestFocus();
		//textArray[activeTab].setText(action.getTextAreaContent());
    }
    
    public void actionOpen() {
		action.actionOpenActionListener();
		setTitle(action.getAppTitle());
		tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), action.getFileName());
		textArray[activeTab].setText(action.getTextAreaContent());
		textArray[activeTab].requestFocus();
    }
    
    public void actionSave() {
		action.actionSaveActionListener(textArray[activeTab]);
		setTitle(action.getAppTitle());
		textArray[activeTab].requestFocus();
    }
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                App app = new App();
                app.setVisible(true);
            }
        });
	}

}