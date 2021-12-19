import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import controller.LatexEditorController;
import model.Document;
import model.DocumentManager;
import view.MainWindow;

class UserStory2Test {
	private DocumentManager documentManager;
	private LatexEditorController latexEditorController;
	private MainWindow mainWindow;
	
	@BeforeEach
	void setup() {
		documentManager = DocumentManager.getInstance();
		latexEditorController = new LatexEditorController();
		documentManager.setCurrentDocument(new Document("emptyTemplate"));
		latexEditorController.enact("create");
		mainWindow = new MainWindow(latexEditorController);
	}
	
	@Test
	void testEditCommand() {
		mainWindow.setCurrentMainWindowText("edited text");
		latexEditorController.enact("edit");
		assertEquals("edited text", documentManager.getCurrentDocument().getContents());
	}
}