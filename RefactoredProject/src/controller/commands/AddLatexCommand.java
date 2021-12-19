package controller.commands;

import java.util.Map;
import javax.swing.JEditorPane;
import model.DocumentManager;
import model.VersionsManager;
import view.MainWindow;

public class AddLatexCommand implements Command  {
	private static Map<String, String> LATEX_COMMANDS = Map.of(
			"chapter", 
			"\n\\chapter{...}"+"\n",
			
			"section",
			"\n\\section{...}"+"\n",
			
			"subsection",
			"\n\\subsection{...}"+"\n",
			
			"subsubsection",
			"\n\\subsubsection{...}"+"\n",
			
			"enumerate",
			"\\begin{enumerate}\n"+
			"\\item ...\n"+
			"\\item ...\n"+
			"\\end{enumerate}\n",
			
			"itemize",
			"\\begin{itemize}\n"+
			"\\item ...\n"+
			"\\item ...\n"+
			"\\end{itemize}\n",
			
			"table",
			"\\begin{table}\n"+
			"\\caption{....}\\label{...}\n"+
			"\\begin{tabular}{|c|c|c|}\n"+
			"\\hline\n"+
			"... &...&...\\\\\n"+
			"... &...&...\\\\\n"+
			"... &...&...\\\\\n"+
			"\\hline\n"+
			"\\end{tabular}\n"+
			"\\end{table}\n",
			
			"figure",
			"\\begin{figure}\n"+
			"\\includegraphics[width=...,height=...]{...}\n"+
			"\\caption{....}\\label{...}\n"+
			"\\end{figure}\n"
		);
	
	public AddLatexCommand() {
		
	}

	@Override
	public void execute() {
		DocumentManager documentManager = DocumentManager.getInstance();
		VersionsManager versionsManager = VersionsManager.getInstance();
		String currentMainWindowText;
		editCurrentMainWindowTextWithLatexCommand();
		currentMainWindowText = MainWindow.getInstance().getCurrentMainWindowText();
		if(versionsManager.isEnabled()) {
			createNewDocumentVersion(documentManager, versionsManager);
		}
		documentManager.getCurrentDocument().setContents(currentMainWindowText);
	}
	
	private void editCurrentMainWindowTextWithLatexCommand() {
		MainWindow mainWindow = MainWindow.getInstance();
		String type = mainWindow.getCurrentLatexCommandType();
		JEditorPane editorPane = mainWindow.getEditorPane();
		String contents = editorPane.getText();
		String before = contents.substring(0, editorPane.getCaretPosition());
		String after = contents.substring(editorPane.getCaretPosition());
		String commandContents = LATEX_COMMANDS.get(type);
		contents = before + commandContents + after;
		mainWindow.setCurrentMainWindowText(contents);
		editorPane.setText(contents);
	}
	
	private void createNewDocumentVersion(DocumentManager documentManager, VersionsManager versionsManager) {
		versionsManager.getStrategy().putVersion(documentManager.getCurrentDocument());
		documentManager.getCurrentDocument().changeVersion();
	}
}
