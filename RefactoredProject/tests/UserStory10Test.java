import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import controller.LatexEditorController;
import model.Document;
import model.DocumentManager;
import view.MainWindow;

class UserStory10Test {
	private DocumentManager documentManager;
	private LatexEditorController latexEditorController;
	private MainWindow mainWindow;
	private final String latexString = "\\chapter{This is a chapter.} \n" + 
										"\n" + 
										"\\section{This is a section.}\n" + 
										"\n" + 
										"\\subsection{This is a subsection.} \n" + 
										"\n" + 
										"\\subsubsection{This is a subsubsection.} \n" + 
										"\n" + 
										"\\begin{itemize}\n" + 
										"\\item item1\n" + 
										"\\item item2\n" + 
										"\\end{itemize} \n" + 
										"\n" + 
										"\\begin{enumerate}\n" + 
										"\\item item3\n" + 
										"\\item item4\n" + 
										"\\end{enumerate} \n" + 
										"\n" + 
										"\\begin{table}\n" + 
										"\\caption{....}\\label{...}\n" + 
										"\\begin{tabular}{|c|c|c|}\n" + 
										" \\hline\n" + 
										"cell1 &cell2&cell3\\\\\n" + 
										"cell4 &cell5&cell6\\\\\n" + 
										"cell7 &cell8&cell9\\\\\n" + 
										" \\hline\n" + 
										"\\end{tabular}\n" + 
										"\\end{table} \n" + 
										"\n" + 
										"\\begin{figure}\n" + 
										"\\includegraphics[width=400,height=400]{testImage.jpg}\n" + 
										"\\caption{This is an image.}\\label{...}\n" + 
										"\\end{figure} \n" + 
										"";
	private final String htmlString = "<!DOCTYPE html>\n" + 
										"<html lang=\"en\">\n" + 
										"<head>\n" + 
										"    <meta charset=\"UTF-8\">\n" + 
										"    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" + 
										"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" + 
										"    <title>Document</title>\n" + 
										"</head>\n" + 
										"<body>\n" + 
										"<h1>This is a chapter.</h1>\n" + 
										"<h1>This is a section.</h1>\n" + 
										"<h2>This is a subsection.</h2>\n" + 
										"<h3>This is a subsubsection.</h3>\n" + 
										"<ul>\n" + 
										"	<li>item1</li>\n" + 
										"	<li>item2</li>\n" + 
										"</ul>\n" + 
										"<ol>\n" + 
										"	<li>item3</li>\n" + 
										"	<li>item4</li>\n" + 
										"</ol>\n" + 
										"<table>\n" + 
										"	<tr>\n" + 
										"		<th>cell1 </th>\n" + 
										"		<th>cell2</th>\n" + 
										"		<th>cell3</th>\n" + 
										"	</tr>\n" + 
										"	<tr>\n" + 
										"		<th>cell4 </th>\n" + 
										"		<th>cell5</th>\n" + 
										"		<th>cell6</th>\n" + 
										"	</tr>\n" + 
										"	<tr>\n" + 
										"		<th>cell7 </th>\n" + 
										"		<th>cell8</th>\n" + 
										"		<th>cell9</th>\n" + 
										"	</tr>\n" + 
										"</table>\n" + 
										"<img src='testImage.jpg' alt='This is an image.' width='400' height='400'>\n" + 
										"</body>\n" +
										"</html>";

	@BeforeEach
	void setup() {
		documentManager = DocumentManager.getInstance();
		latexEditorController = new LatexEditorController();
		documentManager.setCurrentDocument(new Document("emptyTemplate"));
		latexEditorController.enact("create");
		mainWindow = new MainWindow(latexEditorController);
	}
	
	@Test
	void testSaveAsHTMLCommand() {
		mainWindow.setCurrentMainWindowText(latexString);
		latexEditorController.enact("edit");
		mainWindow.setFilename("userStory10Test.txt");
		latexEditorController.enact("saveAsHTML");
		try {
			String actualHtmlString = readFileAsString("userStory10Test.txt");
			assertEquals(htmlString, actualHtmlString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String readFileAsString(String fileName) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(fileName));
		String fileData = "";
		while (scanner.hasNextLine())
			fileData += scanner.nextLine() + "\n";
		return fileData.trim();
	}
}
