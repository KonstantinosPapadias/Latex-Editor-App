import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import controller.LatexEditorController;
import model.Document;
import model.DocumentManager;
import view.MainWindow;

class UserStory3Test {
	private DocumentManager documentManager;
	private LatexEditorController latexEditorController;
	private MainWindow mainWindow;
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
	
	@BeforeEach
	void setup() {
		documentManager = DocumentManager.getInstance();
		latexEditorController = new LatexEditorController();
		documentManager.setCurrentDocument(new Document("emptyTemplate"));
		latexEditorController.enact("create");
		mainWindow = new MainWindow(latexEditorController);
	}

	@Test
	void testAddLatexCommand() {
		for (Map.Entry<String, String> command : LATEX_COMMANDS.entrySet()) {
			String commandType = command.getKey();
			String commandContents = command.getValue();
			String expectedDocumentContents = commandContents + documentManager.getCurrentDocument().getContents();
			mainWindow.setCurrentLatexCommandType(commandType);
			latexEditorController.enact("addLatex");
			assertEquals(expectedDocumentContents, documentManager.getCurrentDocument().getContents());
		}
	}
}
