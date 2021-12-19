package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextPane;

import controller.LatexEditorController;
import model.Document;
import model.DocumentManager;
import model.VersionsManager;

import javax.swing.JEditorPane;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JCheckBoxMenuItem;

public class MainWindow {

	private JFrame frame;
	private JEditorPane editorPane = new JEditorPane();
	private LatexEditorController latexEditorController;
	private String currentMainWindowText;
	private String filename;
	private String currentLatexCommandType;
	private static MainWindow mainWindowInstance = null;
	
	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the application.
	 * @param latexEditorView 
	 */
	public MainWindow(LatexEditorController latexEditorController) {
		this.latexEditorController = latexEditorController;
		mainWindowInstance = this;
		initialize();
		frame.setVisible(true);
	}
	
	public static MainWindow getInstance() {
		// The main window is already created with the constructor, 
		// this method is used to provide a global access point to 
		// the command classes.
		return mainWindowInstance;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		DocumentManager documentManager = DocumentManager.getInstance();
		VersionsManager versionsManager = VersionsManager.getInstance();
		
		frame = new JFrame();
		frame.setBounds(100, 100, 823, 566);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 805, 26);
		frame.getContentPane().add(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNewFile = new JMenuItem("New file");
		mntmNewFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ChooseTemplate chooseTemplate = new ChooseTemplate(latexEditorController, "main");
				frame.dispose();
			}
		});
		mnFile.add(mntmNewFile);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCurrentMainWindowText(editorPane.getText());
				latexEditorController.enact("edit");
			}
		});
		mnFile.add(mntmSave);
		JMenuItem addChapter = new JMenuItem("Add chapter");
		JMenu mnCommands = new JMenu("Commands");
		JMenuItem mntmLoadFile = new JMenuItem("Load file");
		mntmLoadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser filechooser = new JFileChooser();
				
				int option = filechooser.showOpenDialog(null);
				if (option == JFileChooser.APPROVE_OPTION) {
					filename = filechooser.getSelectedFile().toString();
					setFilename(filename);
					latexEditorController.enact("load");
					mnCommands.setEnabled(true);
					addChapter.setEnabled(true);
					if (documentManager.getCurrentDocument().getType().equals("letterTemplate")) {
						mnCommands.setEnabled(false);
					}
					if(documentManager.getCurrentDocument().getType().equals("articleTemplate")) {
						addChapter.setEnabled(false);
					}
					editorPane.setText(documentManager.getCurrentDocument().getContents());
				}
			}
		});
		mnFile.add(mntmLoadFile);
		
		JMenuItem loadFromHTMLMenuItem = new JMenuItem("Load from HTML");
		loadFromHTMLMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser filechooser = new JFileChooser();
				
				int option = filechooser.showOpenDialog(null);
				if (option == JFileChooser.APPROVE_OPTION) {
					filename = filechooser.getSelectedFile().toString();
					setFilename(filename);
					latexEditorController.enact("loadFromHTML");
					editorPane.setText(documentManager.getCurrentDocument().getContents());
				}
			}
		});
		mnFile.add(loadFromHTMLMenuItem);
		
		JMenuItem mntmSaveFile = new JMenuItem("Save file");
		mntmSaveFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser filechooser = new JFileChooser();
				int option = filechooser.showSaveDialog(null);
				if(option == JFileChooser.APPROVE_OPTION) {
					String filename = filechooser.getSelectedFile().toString();
					if(filename.endsWith(".tex") == false) {
						filename = filename+".tex";
					}
					setFilename(filename);
					latexEditorController.enact("save");
				}
				
			}
		});
		mnFile.add(mntmSaveFile);
		
		JMenuItem saveAsHTMLMenuItem = new JMenuItem("Save as HTML");
		saveAsHTMLMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser filechooser = new JFileChooser();
				int option = filechooser.showSaveDialog(null);
				if(option == JFileChooser.APPROVE_OPTION) {
					String filename = filechooser.getSelectedFile().toString();
					setFilename(filename);
					latexEditorController.enact("saveAsHTML");
				}
				
			}
		});
		mnFile.add(saveAsHTMLMenuItem);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		mnFile.add(mntmExit);
		
		
		menuBar.add(mnCommands);
		if(documentManager.getCurrentDocument().getType().equals("letterTemplate")) {
			mnCommands.setEnabled(false);
		}
		
		addChapter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setCurrentLatexCommandType("chapter");
				latexEditorController.enact("addLatex");
			}
		});
		mnCommands.add(addChapter);
		if(documentManager.getCurrentDocument().getType().equals("articleTemplate")) {
			addChapter.setEnabled(false);
		}
		
		JMenu addSection = new JMenu("Add Section");
		mnCommands.add(addSection);
		
		JMenuItem mntmAddSection = new JMenuItem("Add section");
		mntmAddSection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCurrentLatexCommandType("section");
				latexEditorController.enact("addLatex");
			}
		});
		addSection.add(mntmAddSection);
		
		JMenuItem mntmAddSubsection = new JMenuItem("Add subsection");
		mntmAddSubsection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCurrentLatexCommandType("subsection");
				latexEditorController.enact("addLatex");
			}
		});
		addSection.add(mntmAddSubsection);
		
		JMenuItem mntmAddSubsubsection = new JMenuItem("Add subsubsection");
		mntmAddSubsubsection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCurrentLatexCommandType("subsubsection");
				latexEditorController.enact("addLatex");
			}
		});
		addSection.add(mntmAddSubsubsection);
		
		JMenu addEnumerationList = new JMenu("Add enumeration list");
		mnCommands.add(addEnumerationList);
		
		JMenuItem mntmItemize = new JMenuItem("Itemize");
		mntmItemize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCurrentLatexCommandType("itemize");
				latexEditorController.enact("addLatex");
			}
		});
		addEnumerationList.add(mntmItemize);
		
		JMenuItem mntmEnumerate = new JMenuItem("Enumerate");
		mntmEnumerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCurrentLatexCommandType("enumerate");
				latexEditorController.enact("addLatex");
			}
		});
		addEnumerationList.add(mntmEnumerate);
		
		JMenuItem addTable = new JMenuItem("Add table");
		addTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCurrentLatexCommandType("table");
				latexEditorController.enact("addLatex");
			}
		});
		mnCommands.add(addTable);
		
		JMenuItem addFigure = new JMenuItem("Add figure");
		addFigure.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCurrentLatexCommandType("figure");
				latexEditorController.enact("addLatex");
			}
		});
		mnCommands.add(addFigure);
		
		JMenu mnStrategy = new JMenu("Strategy");
		menuBar.add(mnStrategy);
		
		JMenu mnEnable = new JMenu("Enable");
		mnStrategy.add(mnEnable);
		
		JCheckBoxMenuItem menuVolatile = new JCheckBoxMenuItem("Volatile");
		JCheckBoxMenuItem menuStable = new JCheckBoxMenuItem("Stable");
		menuStable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				versionsManager.setStrategyType("stable");
				if (versionsManager.isEnabled() == false) {
					latexEditorController.enact("enableVersionsManagement");
				} else {
					latexEditorController.enact("changeVersionsStrategy");
				}
				menuVolatile.setSelected(false);
				menuStable.setEnabled(false);
				menuVolatile.setEnabled(true);
			}
		});

		menuVolatile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				versionsManager.setStrategyType("volatile");
				if (versionsManager.isEnabled() == false) {
					latexEditorController.enact("enableVersionsManagement");
				} else {
					latexEditorController.enact("changeVersionsStrategy");
				}
				menuStable.setSelected(false);
				menuVolatile.setEnabled(false);
				menuStable.setEnabled(true);
			}
		});
		mnEnable.add(menuVolatile);
		
		mnEnable.add(menuStable);
		
		JMenuItem mntmDisable = new JMenuItem("Disable");
		mntmDisable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				latexEditorController.enact("disableVersionsManagement");
			}
		});
		mnStrategy.add(mntmDisable);
		
		JMenuItem mntmRollback = new JMenuItem("Rollback");
		mntmRollback.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				latexEditorController.enact("rollbackToPreviousVersion");
				Document currentDocument = documentManager.getCurrentDocument();
				editorPane.setText(currentDocument.getContents());
			}
		});
		mnStrategy.add(mntmRollback);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 39, 783, 467);
		frame.getContentPane().add(scrollPane);
		scrollPane.setViewportView(editorPane);
		
		editorPane.setText(documentManager.getCurrentDocument().getContents());
	}


	public String getCurrentMainWindowText() {
		return currentMainWindowText;
	}


	public void setCurrentMainWindowText(String currentMainWindowText) {
		this.currentMainWindowText = currentMainWindowText;
	}


	public String getFilename() {
		return filename;
	}


	public void setFilename(String filename) {
		this.filename = filename;
	}


	public String getCurrentLatexCommandType() {
		return currentLatexCommandType;
	}


	public void setCurrentLatexCommandType(String currentLatexCommandType) {
		this.currentLatexCommandType = currentLatexCommandType;
	}


	public JEditorPane getEditorPane() {
		return editorPane;
	}


	public void setEditorPane(JEditorPane editorPane) {
		this.editorPane = editorPane;
	}
}
