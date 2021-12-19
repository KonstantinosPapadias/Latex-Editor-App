import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import controller.LatexEditorController;
import model.Document;
import model.DocumentManager;
import model.VersionsManager;
import view.MainWindow;

class UserStory5Test {
	private final int NUMBER_OF_EDITIONS = 2;
	private final String[] TEXT_EDITIONS = {"1 time edited text", "2 times edited text"};
	private final String[] EXPECTED_DOCUMENT_CONTENTS = {"", "1 time edited text"};
	private DocumentManager documentManager;
	private VersionsManager versionsManager;
	private LatexEditorController latexEditorController;
	private MainWindow mainWindow;
	
	@BeforeEach
	void setup() {
		documentManager = DocumentManager.getInstance();
		versionsManager = VersionsManager.getInstance();
		latexEditorController = new LatexEditorController();
		documentManager.setCurrentDocument(new Document("emptyTemplate"));
		latexEditorController.enact("create");
		mainWindow = new MainWindow(latexEditorController);
	}
	
	@Test
	void testChangeVersionStrategyCommand() {
		versionsManager.setStrategyType("volatile");
		latexEditorController.enact("enableVersionsManagement");
		
		versionsManager.setStrategyType("stable");
		latexEditorController.enact("changeVersionsStrategy");
		editDocument2Times();
		checkCorrectDocumentHistory();
		
		versionsManager.setStrategyType("volatile");
		latexEditorController.enact("changeVersionsStrategy");
		editDocument2Times();
		checkCorrectDocumentHistory();
	}
	
	private void editDocument2Times() {
		for (String text : TEXT_EDITIONS) {
			mainWindow.setCurrentMainWindowText(text);
			latexEditorController.enact("edit");
		}
	}
	
	private void checkCorrectDocumentHistory() {
		List<Document> documentHistory = versionsManager.getStrategy().getEntireHistory();
		for (int i=0; i<NUMBER_OF_EDITIONS; i++) {
			assertEquals(EXPECTED_DOCUMENT_CONTENTS[i], documentHistory.get(i).getContents().trim());
		}
	}
}