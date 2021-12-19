import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import controller.LatexEditorController;
import model.Document;
import model.DocumentManager;
import view.MainWindow;

class UserStory11Test {
	private DocumentManager documentManager;
	private LatexEditorController latexEditorController;
	private MainWindow mainWindow;
	private final String expectedLatexDocument = "\\section{This is a chapter.}\n" + 
													"\n" + 
													"\\section{This is a section.}\n" + 
													"\n" + 
													"\\subsection{This is a subsection.}\n" + 
													"\n" + 
													"\\subsubsection{This is a subsubsection.}\n" + 
													"\n" + 
													"\\begin{itemize}\n" + 
													"\\item item1\n" + 
													"\\item item2\n" + 
													"\\end{itemize}\n" + 
													"\n" + 
													"\\begin{enumerate}\n" + 
													"\\item item3\n" + 
													"\\item item4\n" + 
													"\\end{enumerate}\n" + 
													"\n" + 
													"\\begin{table}\n" + 
													"\\caption{}\\label{}\n" + 
													"\\begin{tabular}{|c|c|c|}\n" + 
													"\\hline\n" + 
													"cell1&cell2&cell3\\\\\n" + 
													"cell4&cell5&cell6\\\\\n" + 
													"cell7&cell8&cell9\\\\\n" + 
													"\\hline\n" + 
													"\\end{tabular}\n" + 
													"\\end{table}\n" + 
													"\n" + 
													"\\begin{figure}\n" + 
													"\\includegraphics[width=400,height=400]{testImage.jpg}\n" + 
													"\\caption{This is an image.}\\label{}\n" + 
													"\\end{figure}";
	
	@BeforeEach
	void setup() {
		documentManager = DocumentManager.getInstance();
		latexEditorController = new LatexEditorController();
		documentManager.setCurrentDocument(new Document("emptyTemplate"));
		latexEditorController.enact("create");
		mainWindow = new MainWindow(latexEditorController);
	}

	@Test
	void testLoadFromHTMLCommand() {
		mainWindow.setFilename("userStory10Test.txt");
		latexEditorController.enact("loadFromHTML");
		assertEquals(expectedLatexDocument, documentManager.getCurrentDocument().getContents());
	}
}
