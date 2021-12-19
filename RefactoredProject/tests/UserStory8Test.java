import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import controller.LatexEditorController;
import model.Document;
import model.DocumentManager;
import view.MainWindow;

class UserStory8Test {
	private DocumentManager documentManager;
	private LatexEditorController latexEditorController;
	private MainWindow mainWindow;
	private final String reportTemplate = "\\documentclass[11pt,a4paper]{report}\n\n"+

											"\\begin{document}\n"+
											"\\title{Report Template: How to Structure a LaTeX Document}\n"+
											"\\author{Author1 \\and Author2 \\and ...}\n"+
											"\\date{\\today}\n"+
											"\\maketitle\n\n"+
						
											"\\begin{abstract}\n"+
											"Your abstract goes here...\n"+
											"...\n"+
											"\\end{abstract}\n\n"+
						
											"\\chapter{Introduction}\n"+
											"\\section{Section Title 1}\n"+
											"\\section{Section Title 2}\n"+
											"\\section{Section Title.....}\n\n"+
						
											"\\chapter{....}\n\n"+
						
											"\\chapter{Conclusion}\n\n\n"+
						
						
											"\\chapter*{References}\n\n"+
						
											"\\end{document}\n";

	@BeforeEach
	void setup() {
		documentManager = DocumentManager.getInstance();
		latexEditorController = new LatexEditorController();
		documentManager.setCurrentDocument(new Document("reportTemplate"));
		latexEditorController.enact("create");
		mainWindow = new MainWindow(latexEditorController);
	}
	
	@Test
	void testSaveCommand() throws FileNotFoundException {		
		mainWindow.setFilename("userStory8Test.tex");
		latexEditorController.enact("save");
		String fileData;
		try {
			fileData = readFileAsString("userStory8Test.tex");
			assertEquals(reportTemplate, fileData);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private String readFileAsString(String fileName) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(fileName));
		String fileData = "";
		while (scanner.hasNextLine())
			fileData += scanner.nextLine() + "\n";
		return fileData;
	}
}
